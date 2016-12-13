
you can add google location single update on button click
this code works in an activity and as shown, in a fragment
my project specific lingo:
RootScope.getLocalContext() =  CONTEXT
App.showAlert('msg') =  AlertDialog with message


public class GetLocationFragment extends Fragment{

    final int ACCESS_LOCATION_CODE = 11;
    final int PLAY_SERVICES_CODE = 77;

    GoogleApiClient mGoogleApiClient;
    LocationRequest mLocationRequest;
    Location mCurrentLocation;
    LocationListener mLocationListener;

    GoogleApiClient.ConnectionCallbacks mGoogleApiClientConnected;
    GoogleApiClient.OnConnectionFailedListener mGoogleApiClientConnectionFailed;
    boolean permissionGranted = false;

    Button btnLocation;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_location, container, false);

        btnLocation = (Button) view.findViewById(R.id.btnLocation);

        btnLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // get current location
                getLocation();
            }
        });

        mGoogleApiClientConnected = new GoogleApiClient.ConnectionCallbacks() {
            @Override
            public void onConnected(@Nullable Bundle bundle) {
                // request location update
                startLocationRequest();
            }
            @Override
            public void onConnectionSuspended(int i) {
            }
        };

        mGoogleApiClientConnectionFailed = new GoogleApiClient.OnConnectionFailedListener() {
            @Override
            public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                App.showAlert("Error getting location. Please try again.");
            }
        };

        mLocationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                mCurrentLocation = location;
                if(mCurrentLocation != null){
                    double lat = mCurrentLocation.getLatitude();
                    double lng = mCurrentLocation.getLongitude();
                    Log.e("__latlng",lat+","+lng);
                    // stop location updates after successful lat lng
                    stopLocationRequest();
                }else{
                    App.showAlert("Error getting location. Please try again.");
                }
            }
        };

        return view;
    }

    private void getLocation(){
        // check location permission granted
        if(permissionGranted || checkLocationPermission()){
            // check google play services enabled
            if(checkPlayServices()){
                // build google api client
                buildGoogleApiClient();
            }
        }
    }

    private boolean checkLocationPermission(){
        // check if location permission granted or ask for permission
        if(ContextCompat.checkSelfPermission(RootScope.getLocalContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, ACCESS_LOCATION_CODE);
            return false;
        }
        return true;
    }

    private boolean checkPlayServices(){
        GoogleApiAvailability googleAPI = GoogleApiAvailability.getInstance();
        int result = googleAPI.isGooglePlayServicesAvailable(RootScope.getLocalContext());
        if(result != ConnectionResult.SUCCESS){
            if(googleAPI.isUserResolvableError(result)){
                googleAPI.getErrorDialog(getActivity(), result, PLAY_SERVICES_CODE).show();
            }
            return false;
        }
        return true;
    }

    protected synchronized void buildGoogleApiClient() {
        // create google api client
        mGoogleApiClient = new GoogleApiClient.Builder(RootScope.getLocalContext())
                .addConnectionCallbacks(mGoogleApiClientConnected)
                .addOnConnectionFailedListener(mGoogleApiClientConnectionFailed)
                .addApi(LocationServices.API)
                .build();
        // start google api client
        mGoogleApiClient.connect();
    }

    private void startLocationRequest() {
        // create location request
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(10000);
        if(mGoogleApiClient != null && mGoogleApiClient.isConnected() && mLocationListener != null) {
            try {
                // start location updates
                LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, mLocationListener);
            } catch (SecurityException e) {
                e.printStackTrace();
            }
        }
    }

    private void stopLocationRequest(){
        // stop location updates and stop google api client
        if(mGoogleApiClient != null && mGoogleApiClient.isConnected() && mLocationListener != null){
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, mLocationListener);
            mGoogleApiClient.disconnect();

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        boolean found = false;
        switch(requestCode){
            case ACCESS_LOCATION_CODE:
                // check permission request results
                found = true;
                if(grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    permissionGranted = true;
                    getLocation();
                }else{
                    permissionGranted = false;
                }
                break;
        }
        if(!found){
            App.showAlert("Error granting permissions. Please try again");
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case PLAY_SERVICES_CODE:
                // check play services request results
                if(resultCode == RESULT_OK){
                    getLocation();
                }
                break;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if(mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }

    @Override
    public void onStop() {
        stopLocationRequest();
        super.onStop();
    }

    @Override
    public void onPause() {
        stopLocationRequest();
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            startLocationRequest();
        }
    }
}

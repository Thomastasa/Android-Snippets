View viewToAnimate;

// offset view to prepare for animation
ViewAnimation.setOffsetTop(viewToAnimate)

// animate view into center
ViewAnimation.slideToCenter(viewToAnimate, new SimpleCallback() {
    @Override
    public void done() {
      // DO SOMETHING ON ANIMATION COMPLETE
    }
});

// animate view out towards the bottom
ViewAnimation.slideOutBot(viewToAnimate, new SimpleCallback() {
    @Override
    public void done() {
      // DO SOMETHING ON ANIMATION COMPLETE
    }
});
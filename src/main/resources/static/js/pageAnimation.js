const fadeOutPage = () => {
    if (!window.AnimationEvent) { return; }
    fader.classList.add('fade-out');
  }
  
  // Run the animation once the page finishes loading
  window.addEventListener("load", fadeOutPage)
 
  
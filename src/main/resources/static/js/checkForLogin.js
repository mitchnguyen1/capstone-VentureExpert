function checkUserId() {
    const cookieArr = document.cookie.split(";");
    const userId = cookieArr[0].split("=")[1];
    if (userId !== undefined) {
      window.location.replace("http://54.187.96.6:8080/itinerary.html");
    }
    console.log(userId);
  }

  // Check user ID on page load and page show
  window.addEventListener("load", checkUserId);
  window.addEventListener("pageshow", checkUserId);
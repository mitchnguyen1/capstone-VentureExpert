//Grab cookie and user ID from cookie
const cookieArr = document.cookie.split(";");
const userId = cookieArr[0].split("=")[1];
const itinerary_id = cookieArr[1].split("=")[1]
console.log(itinerary_id)
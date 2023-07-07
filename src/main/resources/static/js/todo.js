//Grab cookie and user ID from cookie
const cookieArr = document.cookie.split(";");
const userId = cookieArr[0].split("=")[1];
const itinerary_id = cookieArr[1].split("=")[1];

//itinerary card elements
const itinTitle = document.getElementById("title")
const itinDate = document.getElementById("date")
const itinLocation = document.getElementById("location")

//Data for requests
const baseUrl = "http://localhost:8080/api/v1";
const headers = {
  "Content-type": "application/json",
};



//async function to get all itinery from user
async function getItinerary(itinerary_id) {
  try {
    const response = await fetch(`${baseUrl}/itinerary/findByUser/${userId}`, {
      method: "GET",
      headers: headers,
    });
    const data = await response.json();
    let currItin;
    data.forEach((item) => {
      if (item.itinerary_id == itinerary_id) {
        currItin = item;
      }
    });
    displayItinerary(currItin)
  } catch (err) {
    console.log(err.message);
  }
}

//function to display the seleted itinerary 
function displayItinerary(itin){
    //handle date format
    let start = itin.start
      .split("-")
      .slice(1)
      .concat(itin.start.split("-")[0])
      .join("-");
    let end = itin.end
      .split("-")
      .slice(1)
      .concat(itin.end.split("-")[0])
      .join("-");
    itinTitle.innerHTML = itin.title
    itinDate.innerHTML = `${start} - ${end}`
    itinLocation.innerHTML = `${itin.city}, ${itin.state}`
}

getItinerary(itinerary_id);

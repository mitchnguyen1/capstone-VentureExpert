//Grab cookie and user ID from cookie
const cookieArr = document.cookie.split(";");
const userId = cookieArr[0].split("=")[1];
const itinerary_id = cookieArr[1].split("=")[1];

//itinerary card elements
const itinTitle = document.getElementById("title");
const itinDate = document.getElementById("date");
const itinLocation = document.getElementById("location");

//add todo elements
const addTodoButton = document.getElementById("addCardText");
const mainElement = document.querySelector("main");
const closeTodobutton = document.getElementById("close");

//modal elements
const modal = document.querySelector(".myModal");
const modalContent = document.querySelector(".modal-content");
const modalBanner = document.getElementById("modalTitle");
const modalForm = document.getElementById("form");
const modalTitle = document.getElementById("modalTitleInput");
const modalAddress = document.getElementById("modalAddressInput");
const modalStart = document.getElementById("modalStartInput");
const modalEnd = document.getElementById("modalEndInput");
const modalCity = document.getElementById("modalCityInput");
const modalState = document.getElementById("modalStateInput");
const modalZip = document.getElementById("modalZipcodeInput");
const modalDate = document.getElementById("modalDateInput");
const modalCost = document.getElementById("modalCostInput");

//todo display elements
const todoSection = document.querySelector(".todo");

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
    displayItinerary(currItin);
  } catch (err) {
    console.log(err.message);
  }
}

//function to display the seleted itinerary
function displayItinerary(itin) {
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
  itinTitle.innerHTML = itin.title;
  itinDate.innerHTML = `${start} - ${end}`;
  itinLocation.innerHTML = `${itin.city}, ${itin.state}`;
}

//function to get all todos by itinerary
async function getTodos(itineraryId) {
  try {
    const response = await fetch(
      `${baseUrl}/todo/findByItinerary/${itineraryId}`,
      {
        method: "GET",
        headers: headers,
      }
    );
    const data = await response.json();
    displayCards(data);
  } catch (err) {
    console.log(err.message);
  }
}

//function to display each todo card
function displayCards(data) {
  //create add card
  todoSection.innerHTML = "";

  //creating the add itinerary card
  const addItinerary = document.createElement("div");
  addItinerary.classList.add("card", "add");

  // Create the anchor element
  const anchorElement = document.createElement("a");
  anchorElement.href = "#";

  // Create the button elementand add event listener
  const addButton = document.createElement("button");
  addButton.type = "button";
  addButton.id = "addCardText";
  addButton.textContent = "+ Add Itinerary";
  addButton.addEventListener("click", displayAddTodo);

  anchorElement.appendChild(addButton);
  addItinerary.appendChild(anchorElement);

  todoSection.appendChild(addItinerary);

  data.forEach((todo) => {
    const todoItems = Object.entries(todo).map(([key, value]) => ({ key, value }));


    
    //create the card and heading
    let itinCard = document.createElement("div")
    itinCard.classList.add("itinCard", "card")

    let heading = document.createElement("h3")
    heading.classList.add("card-header")
    heading.innerHTML = todo.title;

    itinCard.appendChild(heading)

    //create list and make for loop
    let list = document.createElement("div")
    list.classList.add("list")

    todoItems.forEach(item =>{
        if(item.value == null){
            return;
        }
        if(item.key == "itinerary_id" || item.key == "complete" ||item.key == "todo_id" ||item.key == "location_id"){
            return;
        }

        let listDiv = document.createElement("div")
        listDiv.classList.add("list-item-container")

        let label = document.createElement("label")
        const newLabel = item.key.charAt(0).toUpperCase() + item.key.slice(1);
        label.innerHTML = newLabel
        listDiv.appendChild(label)

        let para = document.createElement("p")
        para.classList.add("list-item")
        para.innerText = item.value;
        listDiv.appendChild(para)

        list.appendChild(listDiv)
    })

    itinCard.appendChild(list)
    todoSection.appendChild(itinCard)

  });
}

//function to display add modal
function displayAddTodo(e) {
  e.preventDefault();
  mainElement.style.display = "none";
  modal.style.display = "block";
}

//function to hide add modal
function closeAddTodo(e) {
  e.preventDefault();
  mainElement.style.display = "block";
  modal.style.display = "none";
}

//function to submit a new todo
async function addTodo(e) {
  e.preventDefault();
  const body = {
    itineraryId: itinerary_id,
    title: modalTitle.value,
    address: modalAddress.value,
    city: modalCity.value,
    state: modalState.value,
    zipcode: modalZip.value,
    date: modalDate.value,
    start: `${modalStart.value}:00`,
    end: `${modalEnd.value}:00`,
    cost: modalCost.value,
    complete: false,
  };
  try {
    const response = await fetch(`${baseUrl}/todo/add`, {
      method: "POST",
      body: JSON.stringify(body),
      headers: headers,
    });
    if (response.status === 200) {
      closeAddTodo(e);
      getTodos(itinerary_id);
    }
  } catch (err) {
    console.log(err.message);
  }
}

getItinerary(itinerary_id);
getTodos(itinerary_id);
addTodoButton.addEventListener("click", displayAddTodo);
closeTodobutton.addEventListener("click", closeAddTodo);
modalForm.addEventListener("submit", addTodo);

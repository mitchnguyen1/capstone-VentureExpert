//Grab cookie and user ID from cookie
const cookieArr = document.cookie.split("=");
const userId = cookieArr[1];
// Retrieve the itinerary_id from the URL parameters
const urlParams = new URLSearchParams(window.location.search);
const itinerary_id = urlParams.get("itinerary_id");

//itinerary card elements
const itinTitle = document.getElementById("title");
const itinDate = document.getElementById("date");
const itinLocation = document.getElementById("location");
const totalCost = document.getElementById("totalCost");

//add todo/done elements
const addTodoButton = document.getElementById("addCardText");
const mainElement = document.querySelector("main");
const closeTodobutton = document.getElementById("close");
const todoSection = document.querySelector(".todo");
const doneSection = document.querySelector(".done");
const topmap = document.querySelector(".top")
//Grab element for user's name
const nameTextBox = document.getElementById("fullName");
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
let startDate = new Date();
//Data for requests
const baseUrl = "http://34.213.142.116:8080/api/v1";
const headers = {
  "Content-type": "application/json",
};

if (userId == undefined) {
  console.log(userId);
  window.location.replace("http://34.213.142.116:8080/index.html");
}

//Get User's info
const getUser = async (userId) => {
  await fetch(`${baseUrl}/users/findById/${userId}`, {
    method: "GET",
    headers: headers,
  })
    .then((response) => response.text())
    .then((data) => displayName(data))
    .catch((err) => console.log(err.message));
};





//render the default map
var map = L.map("mapid").setView([0, 0], 15);

//add a tile layer using openstreetmap
L.tileLayer("https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png", {
  attribution:
    '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors',
}).addTo(map);

//define a provider object for geosearch
const provider = new GeoSearch.OpenStreetMapProvider();
//define settings for the provider
const searchControl = new GeoSearch.GeoSearchControl({
  provider: provider,
  showMarker: true,
  draggable:true
});
//add search function to map
map.addControl(searchControl);
//handle search results and reset the view to results
map.on("geosearch/showlocation", function (e) {
  console.log(e); // Log the event object

  // Check if the location object and its properties exist before trying to use them
  if (e.location && e.location.lat && e.location.lng) {
    map.setView(e.location, 20);
  } else {
    console.error("Invalid location data:", e.location);
  }
});




//display user's name
const displayName = (data) => {
  nameTextBox.innerHTML = "";
  if (data.length >= 10) {
    let words = data.split(" ");
    nameTextBox.innerHTML = ", " + words[0] + "!";
  } else {
    nameTextBox.innerHTML = ", " + data + "!";
  }
};

//logout function
function handleLogout() {
  let c = document.cookie.split(";");
  for (let i in c) {
    document.cookie =
      /^[^=]+/.exec(c[i])[0] + "=;expires=Thu, 01 Jan 1970 00:00:00 GMT";
  }
  window.location.replace("http://34.213.142.116:8080/index.html");
}
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

// Function to display the selected itinerary
function displayItinerary(itin) {
  startDate = new Date(itin.start);
  // Handle date format
  let start = itin.start
    .split("-")
    .slice(1)
    .concat(itin.start.split("-")[0])
    .join("/");
  let end = itin.end
    .split("-")
    .slice(1)
    .concat(itin.end.split("-")[0])
    .join("/");
  itinTitle.innerHTML = itin.title;
  itinDate.innerHTML = `${start} - ${end}`;
  itinLocation.innerHTML = `${itin.city}, ${itin.state}`;
  
  // Search for the city and set the map view
  provider.search({ query: `${itin.city}` }).then(function (result) {
    let city = result[0];
    map.setView([city.y, city.x], 10);
  });
}

//dislay total cost of the trip
function displayCost(data) {
  totalCost.innerHTML = "";
  let total = 0;
  data.forEach((item) => {
    total += item.cost;
  });
  totalCost.innerHTML = `$${total}`;
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
    displayCost(data);
    createCards(data);
  } catch (err) {
    console.log(err.message);
  }
}

//diplay cards to the todo section
function displayTodoSection(cards) {
  //create add card
  todoSection.innerHTML = "";

  //creating the add itinerary card
  const addItinerary = document.createElement("div");
  addItinerary.classList.add("cards", "add");

  // Create the anchor element
  const anchorElement = document.createElement("a");
  anchorElement.href = "#";

  // Create the button elementand add event listener
  const addButton = document.createElement("button");
  addButton.type = "button";
  addButton.id = "addCardText";
  addButton.textContent = "+ Add Activity";
  addButton.addEventListener("click", displayModal);

  anchorElement.appendChild(addButton);
  addItinerary.appendChild(anchorElement);

  todoSection.appendChild(addItinerary);

  if (cards != null) {
    cards.forEach((card) => {
      todoSection.appendChild(card);
    });
  }
  // GSAP animation for cards
  gsap.set(".card", { opacity: 0, y: 100 });
  ScrollTrigger.batch(".card", {
    onEnter: (batch) => gsap.to(batch, { opacity: 1, y: 0, stagger: 0.15 }),
    onLeave: (batch) => gsap.to(batch, { y: 100, immediateRender: false }),
    onEnterBack: (batch) => gsap.to(batch, { opacity: 1, y: 0, stagger: 0.15 }),
    onLeaveBack: (batch) => gsap.to(batch, { y: 100, immediateRender: false }),

    start: "top 90%",
    end: "bottom 10%",
  });
}

function displayDoneSection(cards) {
  doneSection.innerHTML = "";
  if (cards != null) {
    cards.forEach((card) => {
      doneSection.appendChild(card);
    });
  }
  // GSAP animation for cards
  gsap.set(".card", { opacity: 0, y: 100 });
  ScrollTrigger.batch(".card", {
    onEnter: (batch) => gsap.to(batch, { opacity: 1, y: 0, stagger: 0.15 }),
    onLeave: (batch) => gsap.to(batch, { opacity: 0, y: 100 }),
    onEnterBack: (batch) => gsap.to(batch, { opacity: 1, y: 0, stagger: 0.15 }),
    onLeaveBack: (batch) => gsap.to(batch, { opacity: 0, y: 100 }),

    start: "top 90%",
    end: "bottom 10%",
  });
}

//function to create each todo card
function createCards(data) {
  let todoCards = [];
  let doneCards = [];
  data.forEach((todo) => {
    const todoItems = Object.entries(todo).map(([key, value]) => ({
      key,
      value,
    }));
    // Sort the todoItems array based on specific keys
    todoItems.sort((a, b) => {
      const keysOrder = [
        "address",
        "city",
        "state",
        "zipcode",
        "date",
        "start",
        "end",
        "cost",
      ];
      const indexA = keysOrder.indexOf(a.key);
      const indexB = keysOrder.indexOf(b.key);
      return indexA - indexB;
    });
    //create the card and heading
    let itinCard = document.createElement("div");
    itinCard.classList.add("itinCard", "card");

    let heading = document.createElement("h3");
    heading.classList.add("card-header");
    heading.innerHTML = todo.title;

    itinCard.appendChild(heading);

    //create list and make for loop
    let list = document.createElement("div");
    list.id = todo.todo_id;
    list.classList.add("list");

    todoItems.forEach((item) => {
      if (item.value == null) {
        return;
      }
      if (
        item.key == "itinerary_id" ||
        item.key == "complete" ||
        item.key == "todo_id" ||
        item.key == "location_id" ||
        item.key == "title"
      ) {
        return;
      }

      let listDiv = document.createElement("div");
      listDiv.classList.add("list-item-container");

      let label = document.createElement("label");
      const newLabel = item.key.charAt(0).toUpperCase() + item.key.slice(1);
      label.innerHTML = newLabel;
      listDiv.appendChild(label);

      let para = document.createElement("p");
      para.classList.add("list-item");
      if (item.key == "cost") {
        para.innerText = `$${item.value}`;
      } else {
        para.innerText = item.value;
      }
      listDiv.appendChild(para);

      list.appendChild(listDiv);
    });

    itinCard.appendChild(list);
    // Create the buttons div
    const buttonsDiv = document.createElement("div");
    buttonsDiv.classList.add("buttons");
    buttonsDiv.id = todo.todo_id;

    //Create the complete checkbox
    const completeCheckbox = document.createElement("button");
    completeCheckbox.type = "button";
    completeCheckbox.classList.add(`${todo.complete}`, "complete-button");
    completeCheckbox.textContent = "Complete?";

    completeCheckbox.addEventListener("click", handleComplete);

    // Create the edit button
    const editButton = document.createElement("button");
    editButton.type = "button";
    editButton.classList.add("edit-button");
    editButton.textContent = "Edit";

    editButton.addEventListener("click", handleEdit);

    // Create the delete button
    const deleteButton = document.createElement("button");
    deleteButton.type = "button";
    deleteButton.classList.add("delete-button");
    deleteButton.textContent = "Delete";

    deleteButton.addEventListener("click", handleDelete);

    // Append the buttons to the buttons div
    buttonsDiv.appendChild(completeCheckbox);
    buttonsDiv.appendChild(editButton);
    buttonsDiv.appendChild(deleteButton);

    itinCard.appendChild(buttonsDiv);

    //handle if to display in todo or done
    const completeItem = todoItems.find((item) => item.key === "complete");
    if (completeItem.value == false) {
      todoCards.push(itinCard);
    } else {
      doneCards.push(itinCard);
    }

    //plot pins
    plotPin(todo.address,todo.city,todo.state, todo.zipcode,todo.title)
  });

  displayTodoSection(todoCards);
  displayDoneSection(doneCards);
}

//function to display add modal
function displayModal(e) {
  e.preventDefault();
  let formattedStartDate = startDate.toISOString().split("T")[0];

  modalDate.value = formattedStartDate;
  mainElement.style.display = "none";
  topmap.style.display = "none";
  modal.style.display = "block";
}

//function to hide add modal
function closeModal(e) {
  e.preventDefault();
  modalTitle.value = "";
  modalDate.value = "";
  modalStart.value = "";
  modalEnd.value = "";
  modalAddress.value = "";
  modalCity.value = "";
  modalState.value = "";
  modalZip.value = "";
  modalCost.value = "";
  modalContent.id = "";
  mainElement.style.display = "block";
  modal.style.display = "none";
  topmap.style.display = "flex";
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
      closeModal(e);
      getTodos(itinerary_id);
    }
  } catch (err) {
    console.log(err.message);
  }
}


//function to plot each todo as pin
function plotPin(address,city,state,zipcode, title) {
  provider.search({ query: `${address}, ${city},${state} ${zipcode}` }).then(function (result) {
    let city = result[0];

    // Add a marker at the city location
    const marker = L.marker([city.y, city.x], { draggable: true }).addTo(map);

    // Optional: Add a popup to the marker with the title name
    marker.bindPopup(title).openPopup();
  });
}


//itinerary buttons functions
async function handleEdit(e) {
  let todo_id = e.target.parentNode.id;
  displayModal(e);
  await fetch(`${baseUrl}/todo/getById/${todo_id}`, {
    method: "GET",
    headers: headers,
  })
    .then((response) => response.json())
    .then((data) => {
      let currItin = data[0];
      modalTitle.value = currItin.title;
      modalDate.value = currItin.date;
      modalStart.value = currItin.start;
      modalEnd.value = currItin.end;
      modalAddress.value = currItin.address;
      modalCity.value = currItin.city;
      modalState.value = currItin.state;
      modalZip.value = currItin.zipcode;
      modalCost.value = currItin.cost;
      modalContent.id = todo_id;
    })
    .catch((err) => console.log(err.message));
}

//handle submit for edit
async function handleEditSubmission(e) {
  e.preventDefault();
  let body = {
    todo_id: modalContent.id,
    itinerary_id: itinerary_id,
    date: modalDate.value,
    title: modalTitle.value,
    start: modalStart.value,
    end: modalEnd.value,
    address: modalAddress.value,
    cost: modalCost.value,
    city: modalCity.value,
    state: modalState.value,
    zipcode: modalZip.value,
    complete: false,
  };
  await fetch(`${baseUrl}/todo/update/`, {
    method: "PUT",
    body: JSON.stringify(body),
    headers: headers,
  })
    .then(() => {
      closeModal(e);
      getTodos(itinerary_id);
    })
    .catch((err) => console.log(err.message));
}
//handle delete todo
async function handleDelete(e) {
  e.preventDefault();
  let todo_id = e.target.parentNode.id;
  await fetch(`${baseUrl}/todo/delete/${todo_id}`, {
    method: "DELETE",
    headers: headers,
  }).catch((err) => console.log(err.message));

  return getTodos(itinerary_id);
}

//update complete
async function handleComplete(e) {
  e.preventDefault();
  let complete = e.target.classList[0];
  let id = e.target.parentNode.id;
  if (complete === "true") {
    e.target.classList[0] = "false";
    complete = false;
  } else {
    e.target.classList[0] = "true";
    complete = true;
  }
  await fetch(
    `${baseUrl}/todo/updateComplete/?todo_id=${id}&complete=${complete}`,
    {
      method: "PUT",
      headers: headers,
    }
  );
  getTodos(itinerary_id);
}

//function to switch from edit or add for modal
function modalType(e) {
  e.preventDefault();
  if (modalContent.id != "") {
    handleEditSubmission(e);
  } else {
    addTodo(e);
  }
}

//function to handle autofill end time when start change
function endDateAutofill(e) {
  modalEnd.value = e.target.value;
}

getUser(userId);
getItinerary(itinerary_id);
getTodos(itinerary_id);
addTodoButton.addEventListener("click", displayModal);
closeTodobutton.addEventListener("click", closeModal);
modalForm.addEventListener("submit", modalType);
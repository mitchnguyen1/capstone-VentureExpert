//Grab cookie and user ID from cookie
const cookieArr = document.cookie.split(";");
const userId = cookieArr[0].split("=")[1];

//logout element
const logoutButton = document.querySelector(".logout-button");

//Grab element for user's name
const nameTextBox = document.getElementById("fullName");

//Modal elements
const modal = document.getElementById("myModal");
const modalContent = document.querySelector(".modal-content");
const modalBanner = document.getElementById("modalTitle");
const itinerary = document.querySelector(".itinerary");
const addModalButton = document.getElementById("addCardText");
const closeModalButton = document.getElementById("close");
const modalForm = document.getElementById("form");
const modalTitle = document.getElementById("modalTitleInput");
const modalStart = document.getElementById("modalStartInput");
const modalEnd = document.getElementById("modalEndInput");
const modalCity = document.getElementById("modalCityInput");
const modalState = document.getElementById("modalStateInput");

//Element for adding cards
const addCardSection = document.querySelector(".addCards");

//Data for requests
const baseUrl = "http://localhost:8080/api/v1";
const headers = {
  "Content-type": "application/json",
};

// console.log(userId);
if (userId == undefined) {
  console.log(userId);
  window.location.replace("http://localhost:8080/index.html");
}
//logout function
function handleLogout() {
  let c = document.cookie.split(";");
  for (let i in c) {
    document.cookie =
      /^[^=]+/.exec(c[i])[0] + "=;expires=Thu, 01 Jan 1970 00:00:00 GMT";
  }
  window.location.replace("http://localhost:8080/index.html");
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

//display user's name
const displayName = (data) => {
  nameTextBox.innerHTML = "";
  if(data.length >= 10){
    let words = data.split(" ")
    nameTextBox.innerHTML = ", " + words[0] + "!";
  }else{
    nameTextBox.innerHTML = ", " + data + "!";
  }
};

//display modal to add itinerary
const displayModal = (e) => {
  e.preventDefault();
  modalTitle.value = "";
  modalStart.value = "";
  modalEnd.value = "";
  modalCity.value = "";
  modalState.value = "";
  itinerary.style.display = "none";
  modal.style.display = "block";
  modalContent.id = "";
};

//close modal
const closeModal = () => {
  modal.style.display = "none";
  itinerary.style.display = "flex";
  modalBanner.innerHTML = "Add Itinerary";
};

//function for submitting itinerary to add
const submitNewItinerary = async (e) => {
  e.preventDefault();
  const body = {
    userId: userId,
    title: modalTitle.value,
    start: modalStart.value,
    end: modalEnd.value,
    city: modalCity.value,
    state: modalState.value,
  };

  const response = await fetch(`${baseUrl}/itinerary/add`, {
    method: "POST",
    body: JSON.stringify(body),
    headers: headers,
  }).catch((err) => console.log(err.message));

  if (response.status === 200) {
    closeModal();
    getItinerary(userId);
  }
};

//async function to get all itinery from user
async function getItinerary(userId) {
  await fetch(`${baseUrl}/itinerary/findByUser/${userId}`, {
    method: "GET",
    headers: headers,
  })
    .then((response) => response.json())
    .then((data) => displayCards(data))
    .catch((err) => console.log(err.message));
}

//function to display cards
function displayCards(data) {
  addCardSection.innerHTML = "";

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
  addButton.addEventListener("click", displayModal);

  anchorElement.appendChild(addButton);
  addItinerary.appendChild(anchorElement);

  addCardSection.appendChild(addItinerary);

  //display each itinerary
  // Iterate through the data array and create a card for each itinerary
  for (let i = 0; i < data.length; i++) {
    const itinerary = data[i];
    //handle date format
    let start = data[i].start
      .split("-")
      .slice(1)
      .concat(data[i].start.split("-")[0])
      .join("-");
    let end = data[i].end
      .split("-")
      .slice(1)
      .concat(data[i].end.split("-")[0])
      .join("-");

    // Create the card element
    const card = document.createElement("div");
    card.classList.add("card");

    // Create the title input box div
    const titleInputBox = document.createElement("div");
    titleInputBox.classList.add("input-box");

    // Create the title label element
    const titleLabel = document.createElement("label");
    titleLabel.setAttribute("for", "title");
    titleLabel.textContent = "Title";

    // Create the title element
    const titleElement = document.createElement("p");
    titleElement.id = "title";
    titleElement.textContent = itinerary.title;

    // Append the title label and title element to the title input box div
    titleInputBox.appendChild(titleLabel);
    titleInputBox.appendChild(titleElement);

    // Create the date input box div
    const dateInputBox = document.createElement("div");
    dateInputBox.classList.add("input-box");

    // Create the date label element
    const dateLabel = document.createElement("label");
    dateLabel.setAttribute("for", "date");
    dateLabel.textContent = "Date";

    // Create the date element
    const dateElement = document.createElement("p");
    dateElement.id = "date";
    dateElement.textContent = `${start} - ${end}`;

    // Append the date label and date element to the date input box div
    dateInputBox.appendChild(dateLabel);
    dateInputBox.appendChild(dateElement);

    // Create the location input box div
    const locationInputBox = document.createElement("div");
    locationInputBox.classList.add("input-box");

    // Create the location label element
    const locationLabel = document.createElement("label");
    locationLabel.setAttribute("for", "location");
    locationLabel.textContent = "Location";

    // Create the location element
    const locationElement = document.createElement("p");
    locationElement.id = "location";
    locationElement.textContent = `${itinerary.city}, ${itinerary.state} `;

    // Append the location label and location element to the location input box div
    locationInputBox.appendChild(locationLabel);
    locationInputBox.appendChild(locationElement);

    // Create the buttons div
    const buttonsDiv = document.createElement("div");
    buttonsDiv.classList.add("buttons");
    buttonsDiv.id = itinerary.itinerary_id;

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

    // Create the view button
    const viewButton = document.createElement("button");
    viewButton.type = "button";
    viewButton.classList.add("view-button");
    viewButton.textContent = "View";

    viewButton.addEventListener("click", handleView);

    // Append the buttons to the buttons div
    buttonsDiv.appendChild(editButton);
    buttonsDiv.appendChild(deleteButton);
    buttonsDiv.appendChild(viewButton);

    // Append the input box divs and buttons div to the card
    card.appendChild(titleInputBox);
    card.appendChild(dateInputBox);
    card.appendChild(locationInputBox);
    card.appendChild(buttonsDiv);

    // Append the card to the container
    addCardSection.appendChild(card);
  }
}

//itinerary buttons functions
async function handleEdit(e) {
  let itinerary_id = e.target.parentNode.id;
  modalBanner.innerHTML = "Edit Itinerary";
  displayModal(e);
  await fetch(`${baseUrl}/itinerary/findByUser/${userId}`, {
    method: "GET",
    headers: headers,
  })
    .then((response) => response.json())
    .then((data) => {
      let currItin;
      data.forEach((item) => {
        if (item.itinerary_id == itinerary_id) {
          currItin = item;
        }
      });
      modalTitle.value = currItin.title;
      modalStart.value = currItin.start;
      modalEnd.value = currItin.end;
      modalCity.value = currItin.city;
      modalState.value = currItin.state;
      modalContent.id = itinerary_id;
    })
    .catch((err) => console.log(err.message));
}

//handle submit for edit
async function handleEditSubmission(e) {
  e.preventDefault();
  let body = {
    itineraryId: modalContent.id,
    title: modalTitle.value,
    start: modalStart.value,
    end: modalEnd.value,
    city: modalCity.value,
    state: modalState.value,
  };
  await fetch(`${baseUrl}/itinerary/update`, {
    method: "PUT",
    body: JSON.stringify(body),
    headers: headers,
  })
    .then(() => {
      closeModal();
      getItinerary(userId);
    })
    .catch((err) => console.log(err.message));
}

async function handleDelete(e) {
    e.preventDefault()
    let itinerary_id = e.target.parentNode.id;
    await fetch(`${baseUrl}/itinerary/delete/${itinerary_id}`, {
        method: "DELETE",
        headers: headers,
      }).catch((err) => console.log(err.message));
    
    return getItinerary(userId);
}

function handleView(e) {
  e.preventDefault();
  let itinerary_id = e.target.parentNode.id;
  window.location.replace(`http://localhost:8080/todo.html?itinerary_id=${itinerary_id}`);
}


//function to switch from edit or add for modal
function modalType(e) {
  e.preventDefault();
  if (modalContent.id != "") {
    handleEditSubmission(e);
  } else {
    submitNewItinerary(e);
  }
}

getUser(userId);
getItinerary(userId);
logoutButton.addEventListener("click", handleLogout);
addModalButton.addEventListener("click", displayModal);
closeModalButton.addEventListener("click", closeModal);
modalForm.addEventListener("submit", modalType);


const username = document.getElementById("username");
const password = document.getElementById("password");
const form = document.getElementById("form");
const error = document.getElementById("error");

const headers = {
  "Content-Type": "application/json",
};

const baseUrl = "http://localhost:8080/api/v1/users";



//Login function
const handleSubmit = async (e) => {
  e.preventDefault();
  const body = {
    username: username.value,
    password: password.value,
  };

  const response = await fetch(`${baseUrl}/login`, {
    method: "POST",
    body: JSON.stringify(body),
    headers: headers,
  }).catch((err) => console.log(err.message));

  const responseArr = await response.json();
  if (response.status === 200) {
    let responseString = responseArr[0];
    let firstFour = responseString.substring(0, 4);

    if (firstFour == "http") {
      document.cookie = `userId=${responseArr[1]}`;
      window.location.replace(responseArr[0]);
    } else {
      displayError(responseString);
    }
  }
};

//Display Errors on page
function displayError(response) {
  error.innerHTML = "";
  let message = document.createElement("h2");
  message.innerHTML = response;
  error.appendChild(message);
  error.style.display = "flex";
}
form.addEventListener("submit", handleSubmit);

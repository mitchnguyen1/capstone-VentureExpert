//Grab cookie and user ID from cookie
const cookieArr = document.cookie.split("=");
const userId = cookieArr[1];

console.log(userId) //18 for test 1

//Get User's info
const getNotes = async (userId) => {
    await fetch(`${baseUrl}/notesByUser/${userId}`, {
      method: "GET",
      headers: headers,
    })
      .then((response) => response.json())
      .then((data) => createNoteCards(data))
      .catch((err) => console.log(err.message));
  };
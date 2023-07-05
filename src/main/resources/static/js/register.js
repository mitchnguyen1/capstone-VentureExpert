const fullName = document.getElementById("fullName")
const username = document.getElementById("username")
const password = document.getElementById("password")
const form = document.getElementById("form")

const headers = {
    'Content-Type':'application/json'
}

const baseUrl = 'http://localhost:8080/api/v1/users'

const handleSubmit = async(e) =>{
    e.preventDefault();
    const body = {
        fullName: fullName.value,
        username: username.value,
        password: password.value
    }
    console.log(body)
    const response = await fetch(`${baseUrl}/register`,{
        method:"POST",
        body: JSON.stringify(body),
        headers: headers
    })
    .catch(err => console.log(err.message))

    const responseArr = await response.json()
    if(response.status === 200){
        window.location.replace(responseArr[0])
    }
}

form.addEventListener("submit",handleSubmit)
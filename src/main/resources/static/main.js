
let tableUsers = [];
let currentUser = "";
fetch("http://localhost:8080/api/users").then(
    res => {
        res.json().then(
            data => {
                if (data.length > 0) {
                    data.forEach((user) => {
                        tableUsers.push(user)
                    })
                    console.log(tableUsers);
                    showUsers(tableUsers);
                }
            }
        )
    })

fetch("http://localhost:8080/api/users/current").then(
    res => {
        res.json().then(
            data => {
                if (data != null) {
                    currentUser = data;
                    console.log(currentUser);
                    showOneUser(currentUser);
                }
            }
        )
    })

function showUsers(event) {
    let temp = "";
    console.log(event);
    event.forEach((user) => {
        temp += "<tr>"
        temp += "<td>" + user.id + "</td>"
        temp += "<td>" + user.firstName + "</td>"
        temp += "<td>" + user.lastName + "</td>"
        temp += "<td>" + user.email + "</td>"
        temp += "<td>" + user.password + "</td>"
        temp += "<td>" + getRoles(user.roles) + "</td>"
        temp += "<td>" + `<a href="/api/users/${user.id}" class="btn btn-primary" id="edit">Edit</a>` + "</td>"
        temp += "<td>" + `<a href="/api/users/${user.id}" class="btn btn-danger" id="delete">Delete</a>` + "</td>"
        temp += "</tr>"
    })
    document.getElementById("allUsersBody").innerHTML = temp;
}
function showOneUser(event) {

    let temp = "";
    console.log(event);
        temp += "<tr>"
        temp += "<td>" + event.id + "</td>"
        temp += "<td>" + event.firstName + "</td>"
        temp += "<td>" + event.lastName + "</td>"
        temp += "<td>" + event.email + "</td>"
        temp += "<td>" + event.password + "</td>"
        temp += "<td>" + getRoles(event.roles) + "</td>"
        temp += "</tr>"
    document.getElementById("oneUserBody").innerHTML = temp;
}

function getRoles(list) {
    let userRoles = [];
    for (let role of list) {
        if (role.name === "ROLE_ADMIN") {
            userRoles.push("ADMIN");
        }
        if (role.name === "ROLE_USER") {
            userRoles.push("USER");
        }
    }
    return userRoles.join(" , ");
}
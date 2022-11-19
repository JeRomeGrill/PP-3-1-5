
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

function rolesUser(event) {
    let rolesAdmin = {};
    let rolesUser = {};
    let roles = [];
    let allRoles = [];
    let sel = document.querySelector(event);
    for (let i = 0, n = sel.options.length; i < n; i++) {
        if (sel.options[i].selected) {
            roles.push(sel.options[i].value);
        }
    }
    if (roles.includes("ROLE_ADMIN")) {
        rolesAdmin["id"] = 2;
        rolesAdmin["name"] = "ROLE_ADMIN";
        allRoles.push(rolesAdmin);
    }
    if (roles.includes("ROLE_USER")) {
        rolesUser["id"] = 1;
        rolesUser["name"] = "ROLE_USER";
        allRoles.push(rolesUser);
    }
    return allRoles;
}
// Для добавления пользователя
// $('.addNewUser').click(function () {
//     $('#home-tab').trigger('click');
//     let user = [];
//     let formData = $("#newUser").serializeArray();
//     formData.forEach((value, key) => user[key] = value);
//     // user["roles"] = rolesUser("#roles");
//     $.ajax({
//         type: 'POST',
//         url: '/api/users',
//         data: user,
//         timeout: 100,
//         headers: {
//             'Content-Type': 'application/json; charset=UTF-8',
//         },
//         success: function () {
//
//             $('.newUser')[0].reset();
//             showUsers();
//         }
//     })
// });
document.querySelector('#newUser').addEventListener('submit', (e) => {
    e.preventDefault();
    let newUserForm = new FormData(e.target);
    let user = {};
    newUserForm.forEach((value, key) => user[key] = value);
    user["roles"] = rolesUser("#roles");
    let request = new Request(e.target.action, {
        method: 'POST',
        body: JSON.stringify(user),
        headers: {
            'Content-Type': 'application/json',
        },
    });

    fetch(request).then(
        res => {
            res.json().then(
                newUser => {
                    tableUsers.push(newUser);
                    showUsers(tableUsers);
                })
        });

});

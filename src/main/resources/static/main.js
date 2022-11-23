
let tableUsers = [];
let currentUser = "";
let deleteModal = new bootstrap.Modal(document.getElementById('deleteModal'));
// let editModal = new bootstrap.Modal(document.getElementById('editModal'));
let request = new Request("http://localhost:8080/api/users", {
    method: 'GET',
    headers: {
        'Content-Type': 'application/json',
    },
});
fetch(request).then(
    res => {
        res.json().then(
            data => {
                if (data.length > 0) {
                    data.forEach((user) => {
                        if (user.id != null) {
                            tableUsers.push(user)
                        }
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
        temp += "<td>" + `<a onclick='showDeleteModal(${user.id})' class="btn btn-danger" id="deleteButton" >Delete</a>` + "</td>"
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
        if (role === 2 || role.id === 2) {
            userRoles.push("ADMIN");
        }
        if (role === 1 || role.id === 1) {
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

document.getElementById('newUser').addEventListener('submit', addNewUser);
function addNewUser (e) {
    e.preventDefault();
    let newUserForm = new FormData(e.target);
    let user = {};
    newUserForm.forEach((value, key) => user[key] = value);
    user["roles"] = rolesUser("#roles");
    let request = new Request("http://localhost:8080/api/users", {
        method: 'POST',
        body: JSON.stringify(user),
        headers: {
            'Content-Type': 'application/json',
        },
    });

    fetch(request).
    then( res => res.json()).
    then(newUser => {
        tableUsers.push(newUser);
        showUsers(tableUsers);}
    );
    const triggerEl = document.querySelector('#v-pills-tabContent button[data-bs-target="#nav-home"]')
    bootstrap.Tab.getInstance(triggerEl).show() // Select tab by name
}








function submitFormDeleteUser(id) {
    let request = new Request('http://localhost:8080/api/users/' + id, {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json',
        },
    });
    fetch(request).then(
        response => response.json())
        .then(deleteUser => {
            tableUsers = tableUsers.filter(function (user) {
                return user !== deleteUser;
            })
            showUsers(tableUsers);
            deleteModal.hide();
        });
}



function showDeleteModal(id){
    let request = new Request("http://localhost:8080/api/users/"+ id, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
        },
    });

    fetch(request).
    then( res => res.json()).
    then(deleteUser => {
        console.log(deleteUser);
        document.getElementById('firstNameDel').setAttribute('value',deleteUser.firstName);
        document.getElementById('lastNameDel').setAttribute('value',deleteUser.lastName);
        document.getElementById('emailDel').setAttribute('value',deleteUser.email);
        document.getElementById('passwordDel').setAttribute('value',deleteUser.password);
        if (getRoles(deleteUser.roles).includes("USER") && getRoles(deleteUser.roles).includes("ADMIN")){
            document.getElementById('rolesDel1').setAttribute('selected', 'true');
            document.getElementById('rolesDel2').setAttribute('selected', 'true');
        }
        else if (getRoles(deleteUser.roles).includes("USER")) {
            document.getElementById('rolesDel1').setAttribute('selected', 'true');
        }
        else if (getRoles(deleteUser.roles).includes("ADMIN")){
            document.getElementById('rolesDel2').setAttribute('selected', 'true');
        }
        deleteModal.show();
        }
    );

    document.getElementById('deleteUser').addEventListener('submit', function (){
        submitFormDeleteUser(id);
    } );
}




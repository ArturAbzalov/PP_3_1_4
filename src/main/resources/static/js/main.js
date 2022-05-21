const url = 'http://localhost:8080/api/users'
const editUserForm = document.querySelector('#modalEdit')
let currentUserId = null;
const deleteUserForm = document.querySelector('#modalDelete')
const addUserForm = document.querySelector('#addUser')
const addName = document.getElementById('name3')
const addLastName = document.getElementById('lastname3')
const addAge = document.getElementById('age3')
const addPassword = document.getElementById('password3')
const addRoles = document.getElementById('roles3')
const addEmail = document.getElementById('email3')

const on = (element, event, selector, handler) => {
    element.addEventListener(event, e => {
        if (e.target.closest(selector)) {
            handler(e)
        }
    })
}


function getAllUsers() {
    fetch(url)
        .then(res => res.json())
        .then(users => {
            let temp = '';
            users.forEach(function (user) {
                temp += `
                  <tr>
                        <td>${user.id}</td>
                        <td>${user.username}</td>
                        <td>${user.first_name}</td>
                        <td>${user.age}</td>
                        <td>${user.email}</td>
                        <td>${user.roles.map(role => role.name === 'ROLE_USER' ? 'USER' : 'ADMIN')}</td>
                      
                  <td>
                       <button type="button" data-userid="${user.id}" data-action="edit" class="btn btn-info"
                        data-toggle="modal" data-target="modal" id="edit-user" data-id="${user.id}">Edit</button>
                   </td>
                   <td>
                       <button type="button" class="btn btn-danger" id="delete-user" data-action="delete"
                       data-id="${user.id}" data-target="modal">Delete</button>
                        </td>
                  </tr>`;
            });
            document.querySelector('#allUsers').innerHTML = temp;
        });
}

//вызываем написанную функцию
getAllUsers()

function refreshTable() {
    let table = document.querySelector('#allUsers')
    while (table.rows.length > 1) {
        table.deleteRow(1)
    }
    setTimeout(getAllUsers, 200);
}

///////////ADD user/////////////////////

addUserForm.addEventListener('submit', (e) => {
    e.preventDefault();
    fetch(url, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            username: addName.value,
            first_name: addLastName.value,
            age: addAge.value,
            password: addPassword.value,
            email: addEmail.value,
            roles: [
                addRoles.value
            ]
        })
    })
        .then(res => res.json())
        .then(() => {
        document.getElementById("newUserForm").reset();
        alert('Пользователь добавлен')
    })
    refreshTable()
});


//////////// EDIT user////////////////

on(document, 'click', '#edit-user', e => {
    const userInfo = e.target.parentNode.parentNode
    document.querySelector('#id0').value = userInfo.children[0].innerHTML
    document.getElementById('name0').value = userInfo.children[1].innerHTML
    document.getElementById('lastName0').value = userInfo.children[2].innerHTML
    document.getElementById('age0').value = userInfo.children[3].innerHTML
    document.getElementById('email0').value = userInfo.children[4].innerHTML
    $("#modalEdit").modal("show")

})


editUserForm.addEventListener('submit', (e) => {
    e.preventDefault();
    fetch(url, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            id: document.getElementById('id0').value,
            username: document.getElementById('name0').value,
            first_name: document.getElementById('lastName0').value,
            age: document.getElementById('age0').value,
            password: document.getElementById('password0').value,
            email: document.getElementById("email0").value,
            roles: [
                document.getElementById('roles0').value
            ]
        })
    }).then()


    $("#modalEdit").modal("hide")
    refreshTable()
})

/////////Delete user/////////////////////////////////

deleteUserForm.addEventListener('submit', (e) => {
    console.log(e.target.parentNode.parentNode)
    e.preventDefault();
    e.stopPropagation();
    fetch('http://localhost:8080/api/users/' + currentUserId, {
        method: 'DELETE'
    })
        .then()

    $("#modalDelete").modal("hide")
    refreshTable()
})

on(document, 'click', '#delete-user', e => {
    const fila2 = e.target.parentNode.parentNode
    currentUserId = fila2.children[0].innerHTML

    document.getElementById('id2').value = fila2.children[0].innerHTML
    document.getElementById('name2').value = fila2.children[1].innerHTML
    document.getElementById('lastName2').value = fila2.children[2].innerHTML
    document.getElementById('age2').value = fila2.children[3].innerHTML
    document.getElementById('email2').value = fila2.children[4].innerHTML
    $("#modalDelete").modal("show")
})


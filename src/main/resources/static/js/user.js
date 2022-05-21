const urlUser = 'http://localhost:8080/api/user'
let loggedInUser = document.querySelector('#UserInfo');
let loggedUser = document.querySelector('#nabBarUser')

fetch(urlUser)
    .then(res => res.json())
    .then(data => {
        loggedUser.innerHTML = `
                
                <span class="align-middle mr-1">${data.email} with roles: ${data.roles.map(role => role.name === 'ROLE_USER' ? 'USER' : 'ADMIN')} </span>
                `;
        loggedInUser.innerHTML = `
                                <td>${data.id}</td>
                                <td>${data.username}</td>
                                <td>${data.first_name}</td>
                                <td>${data.age}</td>
                                <td>${data.email}</td>
                                <td>${data.roles.map(role => role.name === 'ROLE_USER' ? 'USER' : 'ADMIN')}</td>
                                `;
    })
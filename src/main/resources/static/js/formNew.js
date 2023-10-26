let formNew = document.forms["formNew"];

createNewUser();

function createNewUser() {
    formNew.addEventListener("submit", ev => {
        ev.preventDefault();

        //приводим роли к виду java для отправки в БД
        let rolesForNewUser = [];
        for (let i = 0; i < formNew.roles.options.length; i++) {
            if (formNew.roles.options[i].selected)
                rolesForNewUser.push({
                    id: formNew.roles.options[i].value,
                    role: "ROLE_" + formNew.roles.options[i].text
                });
        }

        fetch("http://localhost:8080/api/admin/users/", {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                username: formNew.username.value,
                lastname: formNew.lastname.value,
                age: formNew.age.value,
                email: formNew.email.value,
                password: formNew.password.value,
                roles: rolesForNewUser
            })
        }).then(() => {
            formNew.reset();
            getAllUsers();
            $('#usersTable').click(); //клик по кнопке Users Table

        });
    });
}

//Приведение загруженных ролей в формате java к виду JS. Их просто грузим сразу, как появляется форма
function loadRolesForNewUser() {
    let selectAdd = document.getElementById("create-roles");

    selectAdd.innerHTML = "";

    fetch("http://localhost:8080/api/admin/roles")
        .then(res => res.json())
        .then(data => {
            data.forEach(role => {
                let option = document.createElement("option");
                option.value = role.id;
                option.text = role.name.toString().replace('ROLE_', '');
                selectAdd.appendChild(option);
            });
        })
        .catch(error => console.error(error));
}

window.addEventListener("load", loadRolesForNewUser);


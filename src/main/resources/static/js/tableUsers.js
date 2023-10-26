const URLTableUsers = 'http://localhost:8080/api/admin/users/';

getAllUsers();

function getAllUsers() {
    fetch(URLTableUsers)
        .then(function (response) {
            return response.json();
        })
        .then(function (users) {
            let dataOfUsers = '';
            let rolesString = ''; // Здесь будет результат функции rolesToString

            const tableUsers = document.getElementById('tableUsers');

            for (let user of users) {

                rolesString = rolesToString(user.roles);

                dataOfUsers += `<tr>
                        <td>${user.id}</td>
                        <td>${user.username}</td>
                        <td>${user.lastname}</td>
                        <td>${user.age}</td>
                        <td>${user.email}</td>
                        <td>${rolesString}</td>


                        <td>
                          <button type="button"
                          class="btn btn-info"
                          data-bs-toogle="modal"
                          data-bs-target="#editModal"
                          onclick="editModal(${user.id})">
                                Edit
                            </button>
                        </td>


                        <td>
                            <button type="button" 
                            class="btn btn-danger" 
                            data-toggle="modal" 
                            data-target="#deleteModal" 
                            onclick="deleteModal(${user.id})">
                                Delete
                            </button>
                        </td>
                    </tr>`;
            }
            tableUsers.innerHTML = dataOfUsers;
        })
}

function rolesToString(roles) {
    let rolesString = '';
    for (const element of roles) {
        rolesString += (element.name.toString().replace('ROLE_', '') + ', ');
    }
    rolesString = rolesString.substring(0, rolesString.length - 2); // -2, чтобы не показывать последнюю запятую с пробелом
    return rolesString;
}
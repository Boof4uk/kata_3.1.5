const URLNavbarAdmin = 'http://localhost:8080/api/admin/showAccount/';
const navbarBrandAdmin = document.getElementById('navbarBrandAdmin');//Элемент, где будет роль и почта текущего юзера
const tableUserAdmin = document.getElementById('tableAdmin');//Элемент, где будет таблица текущего юзера

function getCurrentAdmin() {
    fetch(URLNavbarAdmin)
        .then((res) => res.json())
        .then((userAdmin) => {

            let rolesStringAdmin = rolesToStringForAdmin(userAdmin.roles);
            let data = '';

            data += `<tr>
            <td>${userAdmin.id}</td>
            <td>${userAdmin.username}</td>
            <td>${userAdmin.lastname}</td>
            <td>${userAdmin.age}</td>
            <td>${userAdmin.email}</td>
            <td>${rolesStringAdmin}</td>
            </tr>`;
            tableUserAdmin.innerHTML = data;
            navbarBrandAdmin.innerHTML = `<b><span>${userAdmin.email}</span></b>
                             <span>with roles:</span>
                             <span>${rolesStringAdmin}</span>`;
        });
}

getCurrentAdmin()

function rolesToStringForAdmin(roles) {
    let rolesString = '';

    for (const element of roles) {
        rolesString += (element.name.toString().replace('ROLE_', '') + ', ');
    }
    rolesString = rolesString.substring(0, rolesString.length - 2);
    return rolesString;
}


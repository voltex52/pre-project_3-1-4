document.addEventListener("DOMContentLoaded", function () {
    let modalForEdit = new bootstrap.Modal(document.getElementById("modalForEdit"));
    let modalForDelete = new bootstrap.Modal(document.getElementById("modalForDelete"));

    document.getElementById("modalForEdit").addEventListener("show.bs.modal", function (event) {
        let trToEdit = event.relatedTarget.parentNode.parentNode.parentNode;
        let url = event.relatedTarget.parentNode.action;
        fetch(url)
            .then(response => {
                return response.json()
            })
            .then(user => {
                document.getElementById("edit_input_id").value = user.id;
                document.getElementById("edit_input_firstname").value = user.firstname;
                document.getElementById("edit_input_lastname").value = user.lastname;
                document.getElementById("edit_input_age").value = user.age;
                document.getElementById("edit_input_username").value = user.username;
                document.getElementById("edit_input_password").value = user.password;
                let selectRoles = document.getElementById("edit_input_userRoles");
                for (let i = 0; i < selectRoles.length; ++i) {
                    selectRoles[i].selected = false;
                }
            })
            .catch(error => console.log(error));
        let div = document.getElementById("edit_modal-footer");
        let button = document.createElement("button");
        let textNode = document.createTextNode("Close");

        button.setAttribute("type", "button");
        button.setAttribute("data-bs-dismiss", "modal");
        button.className = "btn btn-secondary";
        button.appendChild(textNode);
        div.appendChild(button);

        button = document.createElement("button");
        textNode = document.createTextNode("Edit");
        button.setAttribute("type", "submit");
        button.className = "btn btn-primary";

        button.addEventListener("click", function (event) {
            event.preventDefault();
            let url = "http://localhost:8080/admin/users";
            let data = new FormData();

            data.append("id", document.getElementById("edit_input_id").value);
            data.append("firstname", document.getElementById("edit_input_firstname").value);
            data.append("lastname", document.getElementById("edit_input_lastname").value);
            data.append("age", document.getElementById("edit_input_age").value);
            data.append("username", document.getElementById("edit_input_username").value);
            data.append("password", document.getElementById("edit_input_password").value);

            let selectRoles = document.getElementById("edit_input_userRoles");
            for (let i = 0; i < selectRoles.length; ++i) {
                if (selectRoles[i].selected) {
                    data.append("edit_input_userRoles", selectRoles[i].value);
                    break;
                }
            }

            fetch(url, {
                method: "PATCH",
                body: data
            })
                .then(response => {
                    return response.json()
                })
                .then(user => {
                    trToEdit.getElementsByClassName("td_firstname")[0].innerText = user.firstname;
                    trToEdit.getElementsByClassName("td_lastname")[0].innerText = user.lastname;
                    trToEdit.getElementsByClassName("td_age")[0].innerText = user.age;
                    trToEdit.getElementsByClassName("td_username")[0].innerText = user.username;

                    let td = document.createElement("td");
                    let str = "";
                    for (let i = 0; i < user.userRoles.length; ++i) {
                        str += user.userRoles[i].authority.replace("ROLE_", "");
                        if (i + 1 < user.userRoles.length) {
                            str += " ";
                        }
                    }
                    let textNode = document.createTextNode(str);
                    td.appendChild(textNode);
                    trToEdit.getElementsByClassName("td_userRoles")[0].innerHTML = "";
                    trToEdit.getElementsByClassName("td_userRoles")[0].appendChild(td);
                })
                .catch();
            modalForEdit.hide();
        });
        button.appendChild(textNode);
        div.appendChild(button);
    });

    document.getElementById("modalForEdit").addEventListener("hide.bs.modal", function () {
        document.getElementById("edit_modal-footer").innerHTML = "";
    });

    document.getElementById("modalForDelete").addEventListener("show.bs.modal", function (event) {
        let trToRemove = event.relatedTarget.parentNode.parentNode.parentNode;
        let url = event.relatedTarget.parentNode.action;

        fetch(url)
            .then(response => {
                return response.json()
            })
            .then(user => {
                document.getElementById("delete_input_id").value = user.id;
                document.getElementById("delete_input_firstname").value = user.firstname;
                document.getElementById("delete_input_lastname").value = user.lastname;
                document.getElementById("delete_input_age").value = user.age;
                document.getElementById("delete_input_username").value = user.username;
                let select = document.getElementById("delete_input_userRoles");
                select.innerHTML = "";
                let option, textNode;

                select.setAttribute("size", user.userRoles.length);
                for (let i = 0; i < user.userRoles.length; ++i) {
                    option = document.createElement("option");
                    textNode = document.createTextNode(user.userRoles[i].authority.replace("ROLE_", ""));
                    option.appendChild(textNode);
                    select.appendChild(option);
                }
            })
            .catch(error => console.log(error));

        let div = document.getElementById("modal-footer");
        let button = document.createElement("button");
        let textNode = document.createTextNode("Close");

        button.setAttribute("type", "button");
        button.setAttribute("data-bs-dismiss", "modal");
        button.className = "btn btn-secondary";
        button.appendChild(textNode);
        div.appendChild(button);

        button = document.createElement("button");
        textNode = document.createTextNode("Delete");
        button.setAttribute("type", "submit");
        button.className = "btn btn-danger";

        button.addEventListener("click", function (event) {
            event.preventDefault();
            let url = "http://localhost:8080/admin/users/" + document.getElementById("delete_input_id").value;
            fetch(url, {method: "DELETE"})
                .then()
                .catch()
            trToRemove.remove();
            modalForDelete.hide();
        });
        button.appendChild(textNode);
        div.appendChild(button);
    });

    document.getElementById("modalForDelete").addEventListener("hide.bs.modal", function () {
        document.getElementById("modal-footer").innerHTML = "";
    });
});

function funcAddNewUserButton() {
    event.preventDefault();

    let url = "http://localhost:8080/admin/users";
    let tbody = document.getElementById("tbody_user_list");
    let data = new FormData();

    data.append("firstname", document.getElementById("add_input_firstname").value);
    data.append("lastname", document.getElementById("add_input_lastname").value);
    data.append("age", document.getElementById("add_input_age").value);
    data.append("username", document.getElementById("add_input_username").value);
    data.append("password", document.getElementById("add_input_password").value);
    data.append("add_input_userRoles", document.getElementById("add_input_userRoles").value);

    fetch(url, {
        method: "POST",
        body: data
    })
        .then(response => {
            return response.json()
        })
        .then(user => {
            let tr = document.createElement("tr");
            let td;
            let textNode, form, button;

            textNode = document.createTextNode(user.id);
            td = document.createElement("td");
            td.classList.add("td_id");
            td.appendChild(textNode);
            tr.appendChild(td);

            textNode = document.createTextNode(user.firstname);
            td = document.createElement("td");
            td.classList.add("td_firstname");
            td.appendChild(textNode);
            tr.appendChild(td);

            textNode = document.createTextNode(user.lastname);
            td = document.createElement("td");
            td.classList.add("td_lastname");
            td.appendChild(textNode);
            tr.appendChild(td);

            textNode = document.createTextNode(user.age);
            td = document.createElement("td");
            td.classList.add("td_age");
            td.appendChild(textNode);
            tr.appendChild(td);

            textNode = document.createTextNode(user.username);
            td = document.createElement("td");
            td.classList.add("td_username");
            td.appendChild(textNode);
            tr.appendChild(td);

            td = document.createElement("td");
            td.classList.add("td_userRoles");
            let str = "";
            for (let i = 0; i < user.userRoles.length; ++i){
                str += user.userRoles[i].authority.replace("ROLE_", "");
                if (i + 1 < user.userRoles.length) {
                    str += " ";
                }
            }
            textNode = document.createTextNode(str);
            td.appendChild(textNode);
            tr.appendChild(td);

            textNode = document.createTextNode("Edit");
            td = document.createElement("td");
            button = document.createElement("button");
            form = document.createElement("form");

            form.setAttribute("role", "form");
            form.setAttribute("method", "PATCH");
            form.setAttribute("action", "/admin/users/" + user.id);

            button.className = "btn btn-info";
            button.setAttribute("type", "button");
            button.setAttribute("data-bs-toggle", "modal");
            button.setAttribute("data-bs-target", "#modalForEdit");

            button.appendChild(textNode);
            form.appendChild(button);
            td.appendChild(form);
            tr.appendChild(td);


            textNode = document.createTextNode("Delete");
            td = document.createElement("td");
            button = document.createElement("button");
            form = document.createElement("form");

            form.setAttribute("role", "form");
            form.setAttribute("method", "DELETE");
            form.setAttribute("action", "/admin/users/" + user.id);

            button.className = "btn btn-danger";
            button.setAttribute("type", "button");
            button.setAttribute("data-bs-toggle", "modal");
            button.setAttribute("data-bs-target", "#modalForDelete");

            button.appendChild(textNode);
            form.appendChild(button);
            td.appendChild(form);
            tr.appendChild(td);
            tbody.append(tr);
        })
        .catch();

    document.getElementById("add_new_user_form").reset();
    document.getElementById("userlist-tab").click();
}
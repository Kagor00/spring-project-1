function delete_task(task_id) {
    let url = "/" + task_id;
    $.ajax({
        url: url,
        type: "DELETE",
        success: function () {
            window.location.reload();
        },
        error: function(xhr, status, error) {
            console.error("Error deleting task:", status, error);
        }
    });
}

function edit_task(task_id) {
    // Видалити кнопку видалення
    let identifier_delete = "#delete_" + task_id;
    $(identifier_delete).remove();

    // Додати кнопку Save
    let identifier_edit = "#edit_" + task_id;
    let save_tag = "<button id='save_" + task_id + "'>Save</button>";
    $(identifier_edit).html(save_tag);

    // Прив’язати обробник подій до кнопки Save
    $(identifier_edit).attr("onclick", "update_task(" + task_id + ")");

    // Отримати рядок таблиці, в якому потрібно редагувати
    let current_tr_element = $(identifier_edit).closest('tr');

    // Оновити поле опису
    let td_description = current_tr_element.find('td').eq(1);
    let current_description = td_description.text().trim();
    td_description.html("<input id='input_description_" + task_id + "' type='text' value='" + current_description + "'>");

    // Оновити статус
    let td_status = current_tr_element.find('td').eq(2);
    let current_status = td_status.text().trim();
    td_status.html(getDropdownStatusHtml(task_id));
    $("#select_status_" + task_id).val(current_status).change();
}

function getDropdownStatusHtml(task_id) {
    let status_id = "select_status_" + task_id;
    return "<label for='status'></label>"
        + "<select id='" + status_id + "' name='status'>"
        + "<option value='IN_PROGRESS'>IN_PROGRESS</option>"
        + "<option value='DONE'>DONE</option>"
        + "<option value='PAUSED'>PAUSED</option>"
        + "</select>";
}

function update_task(task_id) {
    let url = "/" + task_id;
    let value_description = $("#input_description_" + task_id).val();
    let value_status = $("#select_status_" + task_id).val();

    $.ajax({
        url: url,
        type: "PUT",
        dataType: "json",
        contentType: "application/json;charset=UTF-8",
        data: JSON.stringify({ "description": value_description, "status": value_status }),
        success: function(response) {
            console.log("Task updated successfully:", response);
            // Оновлення сторінки
            window.location.reload(true);
        },
        error: function(xhr, status, error) {
            console.error("Error updating task:", status, error);
            console.log("Response Text:", xhr.responseText); // Для налагодження
        }
    });
}



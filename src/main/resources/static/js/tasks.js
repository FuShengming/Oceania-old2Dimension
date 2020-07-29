$(document).ready(function () {

    $("#task-item").on("click", function () {

    });

    getTasksByUserId()

})

let getTasksByUserId = function () {
    let userId = localStorage['userId'];
    if (userId === undefined) window.location.href = "/login";

    $.ajax({
        type: "post",
        url: "/group/getUserTask/",
        headers: {"Authorization": $.cookie('token')},
        dataType: "json",
        contentType: 'application/json',
        data: JSON.stringify({
            'userId': userId,
            'groupId': 1 // todo: groupId
        }),
        timeout: 100000,
        success: function (data) {
            if (data.success) {
                let h = "";
                let tasks = data.content['tasks'];
                let num = 0;
                tasks.forEach(function (task) {
                    // console.log(task);
                    num += 1
                    h += `<tr class="clickable-row" task-id="${task.id}" data-toggle="modal" data-target="#taskModal" id="task-item-` + task.name + `">
                                <td>${task.name}</td>
                                <td>${task.state === 0 ? "unfinished" : "finished"}</td>
                                <td>${task.label}</td>
                                <td>${task.startDate === null ? "--" : new Date(Date.parse(task.startDate)).toLocaleString("en")}</td>
                                <td>${task.endDate === null ? "--" : new Date(Date.parse(task.endDate)).toLocaleString("en")}</td>
                            </tr>`
                });
                $("#code-tb").html(h);
                $(".clickable-row").on('click', function (e) {
                    let taskId = e['currentTarget']['attributes'][1]['value']
                    for (let i = 0; i < tasks.length; i++) {
                        if (taskId == tasks[i].id) {
                            var task = tasks[i]
                            break;
                        }
                    }
                    $("#task-modal-label").text(task.name)
                    $("#modal-status").text(task.state === 0 ? "unfinished" : "finished")
                    $("#modal-label").text(task.label)
                    $("#modal-desc").text(task.description)
                    $("#modal-start").text(task.startDate === null ? "--" : new Date(Date.parse(task.startDate)).toLocaleString("en"))
                    $("#modal-end").text(task.endDate === null ? "--" : new Date(Date.parse(task.endDate)).toLocaleString("en"))

                    let assignments = data.content['assignments'];
                    let userIds = []
                    let usrStr = ""
                    for (let i = 0; i < assignments.length; i++) {
                        if (assignments[i].userId !== userId && assignments[i].taskId == taskId) {
                            userIds.push(assignments[i].userId);
                            usrStr += assignments[i].userId + ", "
                        }
                    }
                    $("#modal-coworkers").text(usrStr.slice(0, -2)) // todo: username

                    console.log(task)
                });

                // $(".rmv").on('click', function () {
                //     let codeId = $(this).attr("code-id");
                //     if (confirm("Sure to remove this project permanently?")) {
                //         $.ajax({
                //             type: "post",
                //             url: "/code/delete",
                //             headers: {"Authorization": $.cookie('token')},
                //             dataType: "json",
                //             contentType: 'application/json',
                //             data: JSON.stringify({
                //                 "userId": userId,
                //                 "codeId": codeId,
                //             }),
                //             success: function (data) {
                //                 if (data.success) {
                //                     getCodesByUserId();
                //                 } else {
                //                     console.log(data.message);
                //                 }
                //             },
                //             error: function (err) {
                //                 console.log(err);
                //             }
                //         });
                //     }
                // });
            } else {
                console.log(data.message);
            }
        },
        error: function (err) {
            console.log(err);
        }
    });
};

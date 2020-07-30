$(function () {
    let userId = localStorage['userId'];
    if (userId === undefined) window.location.href = "/login";
    console.log(userId);

    let is_leader = true; // todo: delete
    if (is_leader) {
        $("#task-title-button").prepend(`
            <button class="btn btn-primary mb-2 mr-2" data-toggle="modal" data-target="#task-modify-modal"
                    id="task-create">
                <span class="fa fa-plus-square mr-2"></span>New
            </button>
        `)
    }

    $("#task-search").on('click', function () {
        let input = $("#task-search").val()

    })

    $("#task-modify").on('click', function () {

    })

    $("#task-create").on('click', function () {
        $("#task-modify-modal-label").text("New Task");
    })

    $("#task-modal-submit").on('click', function () {
        let str = checkInput();
        if (str.length !== 0) {
            $("#task-create-error").text(str + " can't be null!")
        }
        else {
            let upString = "{"
            $.ajax({
                type: "post",
                url: "/group/createTask",
                headers: {"Authorization": $.cookie('token')},
                dataType: "json",
                contentType: 'application/json',
                data: JSON.stringify()
            })
        }
    })

    $(".form-control").bind('focus', function () {
        $("#task-create-error").text("")
    })

    let get_group_list = function () {
        $.ajax({
            type: "get",
            url: "/group/getAllGroups/" + userId,
            headers: {"Authorization": $.cookie('token')},
            dataType: "json",
            contentType: 'application/json',
            success: function (data) {
                if (data.success) {
                    // console.log("success");
                    let h = "";
                    data.content.forEach(function (e) {
                        console.log(e);
                        h += "<div class=\"chat_list card m-2\" group_id='" + e.id + "'>\n" +
                            "<div class=\"card-body\">\n" +
                            "<h5 class=\"card-title\">" + e.name + "</h5>\n" +
                            "</div>\n" +
                            "</div>\n";
                    });
                    console.log(h);
                    $("#all_groups").html(h);
                    $(".chat_list").on('click', function (e) {
                        console.log(e);
                        $("#all_groups").children().removeClass("active_chat");
                        $(e.currentTarget).addClass("active_chat");
                        let groupId = $(".active_chat").attr("group_id")

                        $.ajax({
                            type: "post",
                            url: "/group/getUserTask/",
                            headers: {"Authorization": $.cookie('token')},
                            dataType: "json",
                            contentType: 'application/json',
                            data: JSON.stringify({
                                'userId': userId,
                                'groupId': groupId
                            }),
                            timeout: 100000,
                            success: function (data) {
                                if (data.success) {
                                    let h = "";
                                    let tasks = data.content['tasks'];
                                    let num = 0;
                                    tasks.forEach(function (task) {
                                        num += 1
                                        h += `<tr class="clickable-row" task-id="${task.id}" data-toggle="modal" data-target="#taskModal" id="task-item-` + task.name + `">
                                <td>${task.name}</td>
                                <td>${task.state === 0 ? "unfinished" : "finished"}</td>
                                <td>${task.label}</td>
                                <td>${task.startDate === null ? "--" : new Date(Date.parse(task.startDate)).toLocaleString("en")}</td>
                                <td>${task.endDate === null ? "--" : new Date(Date.parse(task.endDate)).toLocaleString("en")}</td>
                            </tr>`
                                    });
                                    $("#task-tb").html(h);
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


                                        if (is_leader) {
                                            $("#task-button").addClass("justify-content-between").html(`
                                                <div class="justify-content-flex-start">
                                                    <button type="button" class="btn btn-outline-danger" id="task-delete">Delete</button>
                                                    <button type="button" class="btn btn-outline-primary" id="task-modify">Modify</button>
                                                </div>
                                                <div class="justify-content-flex-end">
                                                    <button type="button" class="btn btn-outline-secondary" data-dismiss="modal">Close</button>
                                                    <button type="button" class="btn btn-success" id="task-complete">Complete</button>
                                                </div>
                                            `)
                                        }
                                        else {
                                            $("#task-button").removeClass("justify-content-between").html(`
                                                <div class="justify-content-flex-end" style="float: right">
                                                    <button type="button" class="btn btn-outline-secondary" data-dismiss="modal">Close</button>
                                                    <button type="button" class="btn btn-success" id="task-complete">Complete</button>
                                                </div>
                                            `)
                                        }
                                        // let assignments = data.content['assignments'];
                                        // let userIds = []
                                        // let url = ""
                                        // let sp = "?id="
                                        // for (let i = 0; i < assignments.length; i++) {
                                        //     if (assignments[i].userId != userId && assignments[i].taskId == taskId) {
                                        //         userIds.push(assignments[i].userId);
                                        //         url += sp + assignments[i].userId;
                                        //         sp = "&id="
                                        //     }
                                        // }
                                        // console.log(url)

                                        // $.ajax({
                                        //     type: "get",
                                        //     url: "/user/getById",
                                        //     headers: {"Authorization": $.cookie('token')},
                                        // })

                                        // $("#modal-coworkers").text(usrStr.slice(0, -2) === "" ? "--" : usrStr.slice(0, -2)) // todo: username

                                        // console.log(task)
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
                    });
                } else {
                    console.log(data.message);
                }
            },
            error: function (err) {
                console.log(err);
            }
        })
    };
    get_group_list();

    $("#create_submit").on('click', function () {
        $.ajax({
            type: "post",
            url: "/group/createGroup",
            headers: {"Authorization": $.cookie('token')},
            dataType: "json",
            contentType: 'application/json',
            data: JSON.stringify({
                creatorId: userId,
                name: $("#name-input").val(),
            }),
            success: function (data) {
                if (data.success) {
                    console.log("success");
                    $("#createModal").modal('hide');
                } else {
                    console.log(data.message);
                }
            },
            error: function (err) {
                console.log(err);
            }
        })
    });
});

let checkInput = function () {
    let str = ""
    let sp = ""
    if ($("#task-create-label").val() === undefined || $("#task-create-label").val().length === 0) {
        str += sp + "label"
        sp = ", "
    }
    if ($("#task-create-name").val() === undefined || $("#task-create-name").val().length === 0) {
        str += sp + "name"
        sp = ", "
    }
    if ($("#task-create-desc").val() === undefined || $("#task-create-desc").val().length === 0) {
        str += sp + "description"
        sp = ", "
    }
    if ($("#task-create-start-time").val() === undefined || $("#task-create-start-time").val().length === 0) {
        str += sp + "start time"
        sp = ", "
    }
    if ($("#task-create-end-time").val() === undefined || $("#task-create-end-time").val().length === 0) {
        str += sp + "end time"
        sp = ", "
    }
    return str;
}
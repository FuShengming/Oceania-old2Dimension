let uuid = function () {
    let s = [];
    let hexDigits = "0123456789abcdef";
    for (let i = 0; i < 36; i++) {
        s[i] = hexDigits.substr(Math.floor(Math.random() * 0x10), 1);
    }
    s[14] = "4";  // bits 12-15 of the time_hi_and_version field to 0010
    s[19] = hexDigits.substr((s[19] & 0x3) | 0x8, 1);  // bits 6-7 of the clock_seq_hi_and_reserved to 01
    s[8] = s[13] = s[18] = s[23] = "-";

    return s.join("");
};
Dropzone.autoDiscover = false;
Dropzone.prototype._getParamName = function (n) {
    if (typeof this.options.paramName === "function") {
        return this.options.paramName(n);
    } else {
        return "" + this.options.paramName;
    }
};

$(function () {
    let userId = localStorage['userId'];
    if (userId === undefined) window.location.href = "/login";
    let group_members = [];

    let chat_with_id = userId;

    $("#task-delete").on('click', function () {
        let taskId = $("#task-delete").attr("task-id")
        $.ajax({
            type: "get",
            url: "/group/deleteTask/" + taskId,
            headers: {"Authorization": $.cookie('token')},
            dataType: "json",
            success: function (data) {
                $("#taskModal").modal("hide")
                getUserTasks()
            },
            error: function (err) {
                console.log(err);
            }
        })
    })

    let taskInit = function () {
        $("#task-create").on('click', function () {
            $("#task-create-user-name").html("");
            $.ajax({
                type: "get",
                url: "/group/getGroupMemberNames/" + group_id,
                headers: {"Authorization": $.cookie('token')},
                dataType: "json",
                success: function (data) {
                    if (data.success) {
                        let users = data.content;
                        let h = ""
                        for (let i = 0; i < users.length; i++) {
                            h += `<option value ="${users[i].name}" id="${"user-" + users[i].id}">${users[i].name}</option>`
                        }
                        $("#task-create-user-name").html(h);
                    }
                },
                error: function (err) {
                    console.log(err);
                }
            })
        });
    }

    $("#task-complete").on('click', function (e) {
        let taskId = $("#task-complete").attr("task-id")
        $.ajax({
            type: "get",
            url: "/group/task/complete/" + taskId,
            headers: {"Authorization": $.cookie('token')},
            dataType: "json",

            success: function (data) {
                $("#taskModal").modal("hide")
                getUserTasks()
            },
            error: function (err) {
                console.log(err);
            }
        })
    });

    $("#task-modal-submit").on('click', function () {
        let str = checkInput();
        if (str.length !== 0) {
            $("#task-create-error").text(str + " can't be null!")
        } else {
            $.ajax({
                type: "post",
                url: "/group/createTask",
                headers: {"Authorization": $.cookie('token')},
                dataType: "json",
                contentType: 'application/json',
                data: JSON.stringify({
                    "groupId": group_id,
                    "state": 0,
                    "name": $("#task-create-name").val(),
                    "label": $("#task-create-label").val(),
                    "description": $("#task-create-desc").val(),
                    "startDate": $("#task-create-start-time").val(),
                    "endDate": $("#task-create-end-time").val()
                }),
                success: function (data1) {
                    if (data1.success) {
                        $.ajax({
                            type: "post",
                            url: "/group/deliverTaskForOneMember",
                            headers: {"Authorization": $.cookie('token')},
                            dataType: "json",
                            contentType: 'application/json',
                            data: JSON.stringify({
                                "groupId": group_id,
                                "taskId": data1.content.id,
                                "userId": $("#task-create-user-name option:selected").attr("id").split("-")[1]
                            }),
                            success: function (data) {
                                $("#task-modify-modal").modal('hide');
                                getUserTasks();
                            },
                            error: function (err) {
                                console.log(err);
                            }
                        });
                    }
                },
                error: function (err) {
                    console.log(err);
                }
            })
        }
    });

    $(".form-control").bind('focus', function () {
        $("#task-create-error").text("")
    });


    $("#cancel-btn").on('click', function () {
        if (confirm("Are you sure to cancel uploading? Your files will not be saved.")) {
            $.ajax({
                type: "post",
                url: "/upload/cancel",
                headers: {"Authorization": $.cookie('token')},
                dataType: "json",
                contentType: 'application/json',
                data: JSON.stringify({
                    'userId': userId,
                    'uuid': localStorage["f-uuid"]
                }),
                success: function (data) {
                    if (data.success) {
                        $("#uploadModal").modal('hide');
                        localStorage.removeItem("f-uuid");
                    } else {
                        console.log(data.message);
                    }
                },
                error: function (err) {
                    console.log(err);
                }
            });
        }
    });
    $("#cont-1").on('click', function () {
        let sel_event = new CustomEvent('next.m.2', {detail: {step: 2}});
        window.dispatchEvent(sel_event);
    });
    $("#cont-2").on('click', function () {
        let sel_event = new CustomEvent('next.m.3', {detail: {step: 3}});
        window.dispatchEvent(sel_event);
        $.ajax({
            type: "post",
            url: "/upload/group/analyze",
            headers: {"Authorization": $.cookie('token')},
            dataType: "json",
            contentType: 'application/json',
            data: JSON.stringify({
                'groupId': group_id,
                'uuid': localStorage["f-uuid"]
            }),
            timeout: 100000,
            success: function (data) {
                if (data.success) {
                    $("#analyze-state").html("<i class=\"fa fa-check-circle fa-3x fa-fw\"></i>\n" +
                        "<span>Success!</span>");
                    $("#cont-3").prop('disabled', false);
                } else {
                    $("#analyze-state").html("<i class=\"fa fa-times-circle fa-3x fa-fw\"></i>\n" +
                        "<span>Analyze failed. Please try again Later.</span>\n" +
                        "<span>Error: " + data.message + "</span>");
                }
            },
            error: function (err) {
                console.log(err);
            }
        });

    });
    $("#cont-3").on('click', function () {
        $(window).unbind('beforeunload');
        localStorage.removeItem("f-uuid");
        location.reload();
    });
    $("#upload-btn").on("click", function () {
        $(window).bind('beforeunload', function () {
            // localStorage.removeItem("codeId");
            return true;
        });

        if (localStorage["f-uuid"] === undefined)
            localStorage.setItem("f-uuid", uuid());

        let jarDz = new Dropzone("div#jar-upload", {
            url: '/upload/jar',
            method: 'post',
            acceptedFiles: ".jar",
            params: {
                'userId': userId,
                'uuid': localStorage["f-uuid"]
            },
            headers: {"Authorization": $.cookie('token')},
            maxFiles: 1,
            init: function () {
                this.on("success", function () {
                    $("#cont-1").prop('disabled', false);
                })
            }
        });

        let codeDZ = new Dropzone("div#codeDrop", {
            url: '/upload/code',
            method: "post",
            maxFilesize: 1,
            addRemoveLinks: true,
            acceptedFiles: ".java",
            createImageThumbnails: false,
            timeout: 0,
            uploadMultiple: true,
            parallelUploads: 500,
            params: {
                'userId': userId,
                'uuid': localStorage["f-uuid"]
            },
            renameFile: function (file) {
                // console.log(file, file.webkitRelativePath);
                return file.webkitRelativePath;
            },
            init: function () {
                this.hiddenFileInput.setAttribute("type", "file");
                this.hiddenFileInput.setAttribute("webkitdirectory", true);
                this.on("success", function () {
                    $("#cont-2").prop('disabled', false);
                })
            }
            // accept: function (file, done) {
            //     console.log(file);
            //     if (file.type === '.java') done();
            // }
        });
    });

    let getCodesByGroupId = function () {
        $.ajax({
            type: "get",
            url: "/group/getGroupCodeList/" + group_id,
            headers: {"Authorization": $.cookie('token')},
            success: function (data) {
                if (data.success) {
                    let h = "";
                    let codes = data.content;
                    console.log(data);
                    $("#p-count").text(codes.length);
                    codes.forEach(function (code) {
                        console.log(code);
                        h += `<tr class="clickable-row" code-id="${code.codeId}">
                                <td>${code.codeName}</td>
                                <td>${code.date === null ? "Never" : new Date(Date.parse(code.date)).toLocaleString("en")}</td>
                                <td class="td-btn">
                                    <button class="btn btn-light btn-setting px-0" type="button" id="dropdownMenuButton"
                                            data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                        <i class="fa fa-ellipsis-h setting-icon"></i>
                                    </button>
                                    <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
                                        <a class="dropdown-item code-stats" href="#" code-id="${code.codeId}" id="project-statistics">Statistics</a>
                                        <a class="dropdown-item modify-name" href="#" code-id="${code.codeId}" id="project-modify">Modify Name</a>
                                        <a class="dropdown-item rmv" href="#" code-id="${code.codeId}">Remove</a>
                                    </div>
                                </td>
                            </tr>`
                    });
                    $("#code-tb").html(h);
                    $(".clickable-row").on('click', function (e) {
                        if (!$(e.target).hasClass("btn-setting") &&
                            !$(e.target).hasClass("setting-icon") &&
                            !$(e.target).hasClass("dropdown-menu") &&
                            !$(e.target).hasClass("dropdown-item")) {
                            console.log('click', $(this).attr("code-id"));
                            // localStorage.setItem('codeId', $(this).attr("code-id"));
                            window.location.href = "/graph?code=" + $(this).attr("code-id") + "&group=1";
                        }
                    });
                    $(".rmv").on('click', function () {
                        let codeId = $(this).attr("code-id");
                        if (confirm("Sure to remove this project permanently?")) {
                            $.ajax({
                                type: "post",
                                url: "/group/deleteCode",
                                headers: {"Authorization": $.cookie('token')},
                                dataType: "json",
                                contentType: 'application/json',
                                data: JSON.stringify({
                                    "groupId": group_id,
                                    "codeId": codeId,
                                }),
                                success: function (data) {
                                    if (data.success) {
                                        getCodesByGroupId();
                                    } else {
                                        console.log(data.message);
                                    }
                                },
                                error: function (err) {
                                    console.log(err);
                                }
                            });
                        }
                    });
                    $(".modify-name").on('click', function () {
                        $("#name-input").val("");
                        let codeId = $(this).attr("code-id");
                        $("#name-submit").on('click', function () {
                            $.ajax({
                                type: "post",
                                url: "/code/modifyName",
                                headers: {"Authorization": $.cookie('token')},
                                dataType: "json",
                                contentType: 'application/json',
                                data: JSON.stringify({
                                    "userId": 1,
                                    "codeId": codeId,
                                    "name": $("#code-name-input").val()
                                }),
                                success: function (data) {
                                    if (data.success) {
                                        getCodesByGroupId();
                                        $("#nameModal").modal('hide');
                                    } else {
                                        console.log(data.message);
                                    }
                                },
                                error: function (err) {
                                    console.log(err);
                                }
                            });
                        });
                        $("#nameModal").modal('show');
                    })
                    $(".code-stats").on('click', function () {
                        console.log("sssss")
                        let codeId = $(this).attr("code-id");
                        $.ajax({
                            type: "post",
                            url: "/group/getCodeStatistics",
                            headers: {"Authorization": $.cookie('token')},
                            dataType: "json",
                            contentType: 'application/json',
                            data: JSON.stringify({
                                "groupId": group_id,
                                "codeId": codeId
                            }),
                            success: function (data) {
                                if (data.success) {
                                    let h = "";
                                    let domainLabel = data.content['domainLabel'];
                                    let edgeLabel = data.content['edgeLabel'];
                                    let vertexLabel = data.content['vertexLabel'];
                                    for (let ditem in domainLabel) {
                                        let uid = ditem.split(" ")[0];
                                        let uname = ditem.split(" ")[1];

                                        h += `<tr>
                                                <td>${uid}</td>
                                                <td>${uname}</td>
                                                <td>${domainLabel[ditem]}</td>
                                                <td>${edgeLabel[ditem]}</td>
                                                <td>${vertexLabel[ditem]}</td>
                                            </tr>`
                                    }
                                    $("#statistics-info").html(h);
                                } else {
                                    console.log(data.message);
                                }
                            },
                            error: function (err) {
                                console.log(err);
                            }
                        });
                        $("#statistics-modal").modal('show');
                    })
                } else {
                    console.log(data.message);
                }
            },

            error: function (err) {
                console.log(err);
            }
        });
    };

    let getAnnouncement = function () {
        $.ajax({
            type: "post",
            url: "/group/getGroupAnnouncements",
            headers: {"Authorization": $.cookie('token')},
            dataType: "json",
            contentType: 'application/json',
            data: JSON.stringify({
                groupId: group_id,
                userId: userId,
            }),
            success: function (data) {
                if (data.success) {
                    // console.log("success");
                    let h = "";
                    data.content.forEach(function (e) {
                        // console.log(e);
                        h = "<div class=\"card m-2\">\n" +
                            "<div class=\"card-body m-0\">\n" +
                            "<h4 class=\"card-title\">" + e.announcement.title + "</h4>\n" +
                            "<h5 class=\"card-subtitle\">" + new Date(Date.parse(e.announcement.releaseDate)).toLocaleString("en") + "</h5>\n" +
                            "<p class=\"card-text\">" + e.announcement.content + "</p>\n" +
                            "</div>\n" +
                            "</div>\n" + h;
                        $.ajax({
                            type: "post",
                            url: "/group/readAnnouncement",
                            headers: {"Authorization": $.cookie('token')},
                            dataType: "json",
                            contentType: 'application/json',
                            data: JSON.stringify({
                                userId: userId,
                                announcementId: e.announcement.id
                            }),
                            success: function (data) {
                                if (data.success) {
                                    console.log("success");
                                } else {
                                    console.log(data.message);
                                }
                            },
                            error: function (err) {
                                console.log(err);
                            }
                        });
                    });
                    $("#announcement_list").html(h);
                } else {
                    console.log(data.message);
                }
            },
            error: function (err) {
                console.log(err);
            }
        });
    };

    let getUserTasks = function () {
        $("#task-my-tasks").tab("show");
        $.ajax({
            type: "post",
            url: "/group/getUserTask/",
            headers: {"Authorization": $.cookie('token')},
            dataType: "json",
            contentType: 'application/json',
            data: JSON.stringify({
                'userId': parseInt(userId),
                'groupId': group_id
            }),
            timeout: 100000,
            success: function (data) {
                if (data.success) {
                    let h = "";
                    let tasks = data.content['tasks'];
                    let num = 0;
                    tasks.forEach(function (task) {
                        num += 1
                        h += `<tr class="task-clickable-row" task-id="${task.id}" data-toggle="modal" data-target="#taskModal" id="task-item-` + task.name + `">
                            <td>${task.name}</td>
                            <td>${task.state === 0 ? "unfinished" : "finished"}</td>
                            <td>${task.label}</td>
                            <td>${task.startDate === null ? "--" : new Date(Date.parse(task.startDate)).toLocaleString("en")}</td>
                            <td>${task.endDate === null ? "--" : new Date(Date.parse(task.endDate)).toLocaleString("en")}</td>
                        </tr>`
                    });
                    $("#task-tb").html("").html(h);
                    $(".task-clickable-row").on('click', function (e) {
                        let taskId = e['currentTarget']['attributes'][1]['value']
                        for (let i = 0; i < tasks.length; i++) {
                            if (taskId == tasks[i].id) {
                                var task = tasks[i]
                                break;
                            }
                        }
                        $("#task-complete").attr("task-id", taskId)
                        $("#task-delete").attr("task-id", taskId)
                        $("#task-modal-label").text(task.name)
                        $("#modal-status").text(task.state === 0 ? "unfinished" : "finished")
                        $("#modal-label").text(task.label)
                        $("#modal-desc").text(task.description)
                        $("#modal-start").text(task.startDate === null ? "--" : new Date(Date.parse(task.startDate)).toLocaleString("en"))
                        $("#modal-end").text(task.endDate === null ? "--" : new Date(Date.parse(task.endDate)).toLocaleString("en"))

                        if (task.state === 0) {
                            if ($("#task-complete").length === 0) {
                                $("#task-close").after(`<button type="button" class="btn btn-success" id="task-complete">Complete</button>`)
                            }
                        } else {
                            $("#task-complete").remove()
                        }

                        if (is_leader) {
                            $("#task-button").addClass("justify-content-between");
                            if ($("#task-btn1").length === 0) {
                                $("#task-btn2").before(`<div class="justify-content-flex-start">
                                                <button type="button" class="btn btn-outline-danger" id="task-delete">Delete</button>
                                            </div>`)
                            }
                        } else {
                            $("#task-button").removeClass("justify-content-between");
                            $("#task-btn1").remove()
                        }
                    });
                } else {
                    console.log(data.message);
                }
            },
            error: function (err) {
                console.log(err);
            }
        });
    }

    let getAllTasks = function () {
        $.ajax({
            type: "get",
            url: "/group/getAllTask/" + group_id,
            headers: {"Authorization": $.cookie('token')},
            dataType: "json",
            success: function (data) {
                if (data.success) {
                    let h = "";
                    let tasks = data.content['tasks'];
                    let assignment = data.content['assignments'];
                    let is_valid = false;
                    for (let i = 0; i < tasks.length; i++) {
                        for (let j = 0; j < assignment.length; j++) {
                            if (assignment[j]["taskId"] === tasks[i]["id"]) {
                                var c_id = assignment[j]["userId"];
                                is_valid = true;
                                break;
                            }
                        }
                        if (!is_valid) {
                            continue;
                        }
                        for (let j = 0; j < group_members.length; j++) {
                            if (group_members[j]["id"] == c_id) {
                                var c_name = group_members[j]["name"];
                                break;
                            }
                            else if (group_members[j]["id"] == userId) {
                                is_valid = false;
                                break;
                            }
                        }
                        if (!is_valid) {
                            continue;
                        }
                        h += `<tr class="task-clickable-row" task-id="${tasks[i].id}" data-toggle="modal" data-target="#taskModal" id="all-task-item-${tasks[i].name}">
                            <td>${tasks[i].name}</td>
                            <td>${c_name}</td>
                            <td>${tasks[i].state === 0 ? "unfinished" : "finished"}</td>
                            <td>${tasks[i].label}</td>
                            <td>${tasks[i].startDate === null ? "--" : new Date(Date.parse(tasks[i].startDate)).toLocaleString("en")}</td>
                            <td>${tasks[i].endDate === null ? "--" : new Date(Date.parse(tasks[i].endDate)).toLocaleString("en")}</td>
                        </tr>`;
                    }
                    $("#all-task-tb").html("").html(h);
                    $(".task-clickable-row").on('click', function (e) {
                        let taskId = e['currentTarget']['attributes'][1]['value']
                        for (let i = 0; i < tasks.length; i++) {
                            if (taskId == tasks[i].id) {
                                var task = tasks[i]
                                break;
                            }
                        }
                        $("#task-complete").attr("task-id", taskId)
                        $("#task-delete").attr("task-id", taskId)
                        $("#task-modal-label").text(task.name)
                        $("#modal-status").text(task.state === 0 ? "unfinished" : "finished")
                        $("#modal-label").text(task.label)
                        $("#modal-desc").text(task.description)
                        $("#modal-start").text(task.startDate === null ? "--" : new Date(Date.parse(task.startDate)).toLocaleString("en"))
                        $("#modal-end").text(task.endDate === null ? "--" : new Date(Date.parse(task.endDate)).toLocaleString("en"))

                        if (task.state === 0 ) {
                            if ($("#task-complete").length === 0) {
                                $("#task-close").after(`<button type="button" class="btn btn-success" id="task-complete">Complete</button>`)
                            }
                        } else {
                            $("#task-complete").remove()
                        }

                        if (is_leader) {
                            $("#task-button").addClass("justify-content-between");
                            if ($("#task-btn1").length === 0) {
                                $("#task-btn2").before(`<div class="justify-content-flex-start">
                                                <button type="button" class="btn btn-outline-danger" id="task-delete">Delete</button>
                                            </div>`)
                            }
                        } else {
                            $("#task-button").removeClass("justify-content-between");
                            $("#task-btn1").remove()
                        }
                    });
                } else {
                    console.log(data.message);
                }
            },
            error: function (err) {
                console.log(err);
            }
        });
    }

    let initAllTasks = function () {
        $("#task-all-tasks").on('click', function () {
            getAllTasks();
        });
    }

    let get_msg = function (id2) {
        let msg = [];
        $.ajax({
            type: "post",
            url: "/chat/getChattingRecords",
            headers: {"Authorization": $.cookie('token')},
            dataType: "json",
            contentType: 'application/json',
            data: "[" + id2 + "," + userId + "]",
            success: function (data) {
                data.content.forEach(function (m) {
                    msg.push({in: true, content: m.content, date: m.sendDate, msg_id: m.id});
                });
                $.ajax({
                    type: "post",
                    url: "/chat/getChattingRecords",
                    headers: {"Authorization": $.cookie('token')},
                    dataType: "json",
                    contentType: 'application/json',
                    data: "[" + userId + "," + id2 + "]",
                    success: function (data) {
                        data.content.forEach(function (m) {
                            msg.push({in: false, content: m.content, date: m.sendDate});
                        });
                        console.log(msg);
                        render_msg(msg);
                    },
                    error: function (err) {
                        console.log(err);
                    }
                });
            },
            error: function (err) {
                console.log(err);
            }
        });
    };

    let render_msg = function (msgs) {
        let h = "";
        let read_msg = [];
        msgs.sort(function (a, b) {
            return a.date < b.date ? -1 : 1;
        });
        msgs.forEach(function (msg) {
            if (msg.in) {
                h += `
                <div class="incoming_msg">
                    <div class="received_msg">
                        <div class="received_withd_msg">
                            <p>${msg.content}</p>
                            <span class="time_date">${new Date(Date.parse(msg.date)).toLocaleString("en")}</span>
                        </div>
                    </div>
                </div>`;
                read_msg.push(msg.msg_id);
            } else {
                h += `
                <div class="outgoing_msg">
                    <div class="sent_msg">
                        <p>${msg.content}</p>
                        <span class="time_date">${new Date(Date.parse(msg.date)).toLocaleString("en")}</span>
                    </div>
                </div>`;
            }
        });
        $.ajax({
            type: "post",
            url: "/chat/readMessages",
            headers: {"Authorization": $.cookie('token')},
            dataType: "json",
            contentType: 'application/json',
            data: JSON.stringify({
                userId: userId,
                messageIds: read_msg
            }),
            success: function (data) {
                console.log(data);
            },
            error: function (err) {
                console.log(err);
            }
        });
        $("#msg-list").html(h);
        $("#msg-list").scrollTop($("#msg-list")[0].scrollHeight);
    };

    let click_chat = function (e) {
        $("#m-list").children().removeClass("active_chat");
        $(e.currentTarget).addClass("active_chat");
        chat_with_id = Number($(e.currentTarget).attr("u_id"));
        get_msg(chat_with_id);
        $("#no_chat").hide();
        $("#with_chat").show();
    };

    let view_group = function (e) {
        // console.log(e);
        $("#all_groups").children().removeClass("active_chat");
        $("#all-task-tb").html("");
        $(e.currentTarget).addClass("active_chat");
        let groupId = Number($(e.currentTarget).attr("groupId"));
        group_id = groupId;
        group_members = [];
        console.log(groupId);

        chat_with_id = userId;
        $("#no_chat").show();
        $("#with_chat").hide();

        $.ajax({
            type: "get",
            url: "/group/getAllGroups/" + userId,
            headers: {"Authorization": $.cookie('token')},
            dataType: "json",
            contentType: 'application/json',
            success: function (data) {
                if (data.success) {
                    data.content.forEach(function (e) {
                        if (e.id === groupId) {
                            $("#g-description").text(e.description);
                            $("#edit-name-input").val(e.name);
                            $("#edit-description-input").val(e.description);
                        }
                    });
                } else {
                    console.log(data.message);
                }
            },
            error: function (err) {
                console.log(err);
            }
        });
        $.ajax({
            type: "get",
            url: "/group/getGroupMember/" + groupId,
            headers: {"Authorization": $.cookie('token')},
            dataType: "json",
            contentType: 'application/json',
            success: function (data) {
                if (data.success) {
                    // console.log("success");
                    let member_list = data.content;
                    let isLeader = false;

                    let leader_id = 0;
                    $("#m-list").html("");
                    let h = "";
                    member_list.forEach(function (e) {
                        if (e.userId !== Number(userId)) {
                            console.log(e.userId, userId);
                            $.ajax({
                                type: "get",
                                url: "/user/getById?id=" + e.userId,
                                headers: {"Authorization": $.cookie('token')},
                                dataType: "json",
                                contentType: 'application/json',
                                success: function (data) {
                                    if (data.success) {
                                        console.log(data.content);
                                        group_members.push({"id": e["userId"], "name": data.content[0].name});
                                        h += `
                            <div class="member_list card m-2" u_id="${e.userId}">
                                <div class="card-body">
                                    <h5 class="card-title">${data.content[0].name}</h5>
                                    <h6 class="card-subtitle mb-2"></h6>
                                    <p class="card-text"></p>
                                </div>
                            </div>`;
                                        $("#m-list").html(h);
                                        $(".member_list").on('click', click_chat);
                                    } else {
                                        console.log(data.message);
                                    }
                                },
                                error: function (err) {
                                    console.log(err);
                                }
                            });
                        }
                        else {
                            $.ajax({
                                type: "get",
                                url: "/user/getById?id=" + e.userId,
                                headers: {"Authorization": $.cookie('token')},
                                dataType: "json",
                                contentType: 'application/json',
                                success: function (data) {
                                    if (data.success) {
                                        group_members.push({"id": e["userId"], "name": data.content[0].name});
                                    } else {
                                        console.log(data.message);
                                    }
                                },
                                error: function (err) {
                                    console.log(err);
                                }
                            });
                        }

                        if (e.isLeader) {
                            isLeader = e.userId == userId;
                            leader_id = e.userId;
                        }
                        is_leader = isLeader;
                    });
                    // console.log(group_members)

                    console.log(leader_id, userId);
                    if (leader_id != userId) {
                        $("#announce-btn").hide();
                        $("#upload-btn").hide();
                        $("#copy-btn").hide();
                        $("#edit-btn").hide();
                        $("#project-statistics").remove();
                    } else {
                        $("#announce-btn").show();
                        $("#upload-btn").show();
                        $("#copy-btn").show();
                        $("#edit-btn").show();
                        if (!$("#project-statistics").length) {
                            $("#project-modify").before(`
                                        <a class="dropdown-item code-stats" href="#" id="project-statistics">Statistics</a>`
                            );

                        }
                    }

                    console.log(is_leader)
                    if (is_leader && $("#task-create").length === 0) {
                        $("#task-title-button").html(`
                        <button class="btn btn-primary mb-2" data-toggle="modal" data-target="#task-modify-modal"
                                id="task-create">
                            <span class="fa fa-plus-square mr-2"></span>New
                        </button>
                    `)
                        taskInit();
                    } else if (!is_leader) {
                        $("#task-create").remove();
                    }

                    if (is_leader && $("#task-all-tasks").length === 0) {
                        $("#all-task-title").html(`<a class="nav-link" id="task-all-tasks" data-toggle="pill" href="#all-task" role="tab"
                                           aria-controls="v-pills-all-task" aria-selected="false">All Tasks</a>
                        `)
                        initAllTasks();
                    }
                    else if (!is_leader) {
                        $("#task-all-tasks").remove();
                    }

                    let leader_name = "";
                    $.ajax({
                        type: "get",
                        url: "/user/getById?id=" + leader_id,
                        headers: {"Authorization": $.cookie('token')},
                        dataType: "json",
                        contentType: 'application/json',
                        success: function (data) {
                            if (data.success) {
                                console.log(data.content);
                                leader_name = data.content[0].name;
                                $("#g-owner").text(leader_name);
                            } else {
                                console.log(data.message);
                            }
                        },
                        error: function (err) {
                            console.log(err);
                        }
                    });
                    $("#m-count").text(member_list.length);
                } else {
                    console.log(data.message);
                }
            },
            error: function (err) {
                console.log(err);
            }
        });
        getAnnouncement();
        getUserTasks();
        getCodesByGroupId();
    };
    let get_group_list = function () {
        $.ajax({
            type: "get",
            url: "/group/getAllGroups/" + userId,
            headers: {"Authorization": $.cookie('token')},
            dataType: "json",
            contentType: 'application/json',
            success: function (data) {
                if (!data.success) {
                    console.log(data.message);
                } else {
                    // console.log("success");
                    let h = "";
                    data.content.forEach(function (e) {
                        console.log(e);
                        h += "<div class=\"chat_list card m-2\" groupId='" + e.id + "'>\n" +
                            "<div class=\"card-body\">\n" +
                            "<h5 class=\"card-title\">" + e.name + "</h5>\n" +
                            "</div>\n" +
                            "</div>\n";
                    });
                    console.log(h);
                    if (h.length > 0) {
                        $("#all_groups").html(h);
                        $(".chat_list").on('click', view_group);
                        $(".chat_list").first().trigger("click");
                        $(".group_content").show();
                    } else {
                        $(".group_content").hide();
                    }

                }
            },
            error: function (err) {
                console.log(err);
            }
        });
    };
    get_group_list();

    $("#copy-btn").on('click', function () {
        $.ajax({
            type: "get",
            url: "/code/getCodesByUserId/" + userId.toString(),
            headers: {"Authorization": $.cookie('token')},
            success: function (data) {
                if (data.success) {
                    let h = "";
                    let codes = data.content;
                    codes.forEach(function (code) {
                        console.log(code);
                        h += `<option codeId="${code.codeId}">${code.codeName}</option>`
                    });
                    $("#personal_projects").html(h);
                } else {
                    console.log(data.message);
                }
            },
            error: function (err) {
                console.log(err);
            }
        })
    });

    $("#copy-submit").on('click', function () {
        $.ajax({
            type: "post",
            url: "/group/addCode",
            headers: {"Authorization": $.cookie('token')},
            dataType: "json",
            contentType: 'application/json',
            data: JSON.stringify({
                groupId: group_id,
                codeId: $("#personal_projects").find("option:selected").attr("codeId")
            }),
            success: function (data) {
                if (data.success) {
                    getCodesByGroupId();
                    $("#copyModal").modal('hide');
                } else {
                    console.log(data.message);
                }
            }
            ,
            error: function (err) {
                console.log(err);
            }
        })
    });

    $("#invite_submit").on('click', function () {
        $.ajax({
            type: "get",
            url: "/group/findUser/" + $("#username-input").val(),
            headers: {"Authorization": $.cookie('token')},
            success: function (data) {
                if (data.success) {
                    let id = data.content.id;
                    $.ajax({
                        type: "post",
                        url: "/group/inviteUser",
                        headers: {"Authorization": $.cookie('token')},
                        dataType: "json",
                        contentType: 'application/json',
                        data: JSON.stringify({
                            groupId: group_id,
                            userId: id,
                            inviterId: userId
                        }),
                        success: function (data) {
                            if (data.success) {
                                console.log("success");
                                alert("Send invitation successfully");
                                $("#inviteModal").modal('hide');
                            } else {
                                alert(data.message);
                                console.log(data.message);
                            }
                        },
                        error: function (err) {
                            console.log(err);
                        }
                    });
                } else {
                    alert("Can't find such user.")
                    console.log(data.message);
                }
            },
            error: function (err) {
                console.log(err);
            }
        })
    });

    $("#a_publish_submit").on('click', function () {
        $.ajax({
            type: "post",
            url: "/group/releaseAnnouncement",
            headers: {"Authorization": $.cookie('token')},
            dataType: "json",
            contentType: 'application/json',
            data: JSON.stringify({
                title: $("#a-title-input").val(),
                content: $("#a-content-input").val(),
                groupId: group_id,
                releaseDate: new Date()
            }),
            success: function (data) {
                if (data.success) {
                    console.log("success");
                    getAnnouncement();
                    $("#announceModal").modal('hide');
                } else {
                    console.log(data.message);
                }
            },
            error: function (err) {
                console.log(err);
            }
        });
    });
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
                description: $("#description-input").val()
            }),
            success: function (data) {
                if (data.success) {
                    console.log("success");
                    get_group_list();
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
    $("#edit_submit").on('click', function () {
        $.ajax({
            type: "post",
            url: "/group/modifyGroupInfo",
            headers: {"Authorization": $.cookie('token')},
            dataType: "json",
            contentType: 'application/json',
            data: JSON.stringify({
                userId: userId,
                group: {
                    id: group_id,
                    name: $("#edit-name-input").val(),
                    description: $("#edit-description-input").val()
                }
            }),
            success: function (data) {
                if (data.success) {
                    console.log("success");
                    get_group_list();
                    $("#editModal").modal('hide');
                } else {
                    console.log(data.message);
                }
            },
            error: function (err) {
                console.log(err);
            }
        });
    });

    $("#send-btn").on('click', function () {
        $.ajax({
            type: "post",
            url: "/chat/sendMessage",
            headers: {"Authorization": $.cookie('token')},
            dataType: "json",
            contentType: 'application/json',
            data: JSON.stringify({
                senderId: userId,
                recipientId: chat_with_id,
                sendDate: new Date(),
                content: $("#msg-input").val()
            }),
            success: function (data) {
                if (data.success) {
                    console.log("success");
                    console.log(data);
                    $("#msg-input").val("");
                    get_msg(chat_with_id);
                } else {
                    alert(data.message);
                    console.log(data.message);
                }
            },
            error: function (err) {
                console.log(err);
            }
        });
    });

    $("#msg-input").bind('keypress', function (e) {
        if (e.keyCode === 13) {
            $("#send-btn").trigger('click');
        }
    });

    $("#quit-btn").on('click', function () {
        if (confirm("Are you sure to quit this group?")) {
            $.ajax({
                type: "post",
                url: "/group/quitGroup",
                headers: {"Authorization": $.cookie('token')},
                dataType: "json",
                contentType: 'application/json',
                data: JSON.stringify({
                    groupId: group_id,
                    userId: userId,
                }),
                success: function (data) {
                    if (data.success) {
                        console.log("success");
                        window.location.reload();
                    } else {
                        console.log(data.message);
                    }
                },
                error: function (err) {
                    console.log(err);
                }
            });
        }
    });

    let m_socket = null;
    let openMSocket = function () {
        if (typeof (WebSocket) == "undefined") {
            console.log("Can't Support WebSocket");
        } else {
            let socketUrl = "ws://old2dimension.cn/websocket/chat/" + userId;
            m_socket = new WebSocket(socketUrl);
            m_socket.onopen = function () {
                console.log("websocket is on.")
            };
            m_socket.onmessage = function (msg) {
                console.log(msg.data, chat_with_id, userId);
                if (chat_with_id != userId) {
                    get_msg(chat_with_id);
                }
            };
            m_socket.onclose = function () {
                console.log("websocket is off.");
            };
            //发生了错误事件
            m_socket.onerror = function () {
                console.log("websocket occurs an error.");
            }
        }
    };
    openMSocket();

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
};

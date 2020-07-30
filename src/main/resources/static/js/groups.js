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
    console.log(userId);
    let group_id = null;
    let is_leader = false;


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
                                        <a class="dropdown-item modify-name" href="#" code-id="${code.codeId}">Modify Name</a>
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
                } else {
                    console.log(data.message);
                }
            },
            error: function (err) {
                console.log(err);
            }
        });
    };

    let view_group = function (e) {
        // console.log(e);
        $("#all_groups").children().removeClass("active_chat");
        $(e.currentTarget).addClass("active_chat");
        let groupId = Number($(e.currentTarget).attr("groupId"));
        group_id = groupId;
        console.log(groupId);

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
                    member_list.forEach(function (e) {
                        if (e.isLeader) {
                            isLeader = e.userId === userId;
                            leader_id = e.userId;
                        }
                        is_leader = isLeader;
                    });
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
        $.ajax({
            type: "post",
            url: "/group/getGroupAnnouncements",
            headers: {"Authorization": $.cookie('token')},
            dataType: "json",
            contentType: 'application/json',
            data: JSON.stringify({
                groupId: groupId,
                userId: userId,
            }),
            success: function (data) {
                if (data.success) {
                    // console.log("success");
                    let h = "";
                    data.content.forEach(function (e) {
                        // console.log(e);
                        h += "<div class=\"card m-2\">\n" +
                            "<div class=\"card-body m-0\">\n" +
                            "<h4 class=\"card-title\">" + e.announcement.title + "</h4>\n" +
                            "<h5 class=\"card-subtitle\">" + new Date(Date.parse(e.announcement.releaseDate)).toLocaleString("en") + "</h5>\n" +
                            "<p class=\"card-text\">" + e.announcement.content + "</p>\n" +
                            "</div>\n" +
                            "</div>\n";
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
                    } else {

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
        })
    });
});
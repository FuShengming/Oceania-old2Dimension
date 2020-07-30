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
            url: "/upload/analyze",
            headers: {"Authorization": $.cookie('token')},
            dataType: "json",
            contentType: 'application/json',
            data: JSON.stringify({
                'userId': userId,
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

    let view_group = function (e) {
        // console.log(e);
        $("#all_groups").children().removeClass("active_chat");
        $(e.currentTarget).addClass("active_chat");
        let group_id = Number($(e.currentTarget).attr("group_id"));
        console.log(group_id);
        $.ajax({
            type: "get",
            url: "/group/getAllGroups/" + userId,
            headers: {"Authorization": $.cookie('token')},
            dataType: "json",
            contentType: 'application/json',
            success: function (data) {
                if (data.success) {
                    data.content.forEach(function (e) {
                        if (e.id === group_id) {
                            $("#g-description").text(e.description);
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
            url: "/group/getGroupMember/" + group_id,
            headers: {"Authorization": $.cookie('token')},
            dataType: "json",
            contentType: 'application/json',
            success: function (data) {
                if (data.success) {
                    // console.log("success");
                    let member_list = data.content;
                    let is_leader = false;
                    let leader_id = 0;
                    member_list.forEach(function (e) {
                        if (e.isLeader) {
                            is_leader = e.userId === userId;
                            leader_id = e.userId;
                        }
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
                groupId: group_id,
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
                        h += "<div class=\"chat_list card m-2\" group_id='" + e.id + "'>\n" +
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
                groupId: 1,
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
        })
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
$(function () {
    let userId = localStorage['userId'];
    if (userId === undefined) window.location.href = "/login";
    console.log(userId);
    let group_id = null;
    let is_leader = false;
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
                    $("#g-owner").text(leader_id);
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
                    $(".chat_list").on('click', view_group);
                } else {
                    console.log(data.message);
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
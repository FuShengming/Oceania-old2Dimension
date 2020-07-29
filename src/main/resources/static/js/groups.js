$(function () {
    let userId = localStorage['userId'];
    if (userId === undefined) window.location.href = "/login";
    console.log(userId);
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
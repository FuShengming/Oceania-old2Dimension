$(function () {
    let userId = localStorage['userId'];
    if (userId === undefined) window.location.href = "/login";
    console.log(userId);

    let iv_socket = null;
    let openIVSocket = function () {
        if (typeof (WebSocket) == "undefined") {
            console.log("Can't Support WebSocket");
        } else {
            let socketUrl = "ws://old2dimension.cn/websocket/invitation/" + userId;
            iv_socket = new WebSocket(socketUrl);
            iv_socket.onopen = function () {
                console.log("websocket is on.")
            };
            iv_socket.onmessage = function (msg) {
                $("#iv-count").text(msg.data);
                if (Number(msg.data) > 0) {
                    $("#iv-count").show();
                } else {
                    $("#iv-count").hide();
                }
            };
            iv_socket.onclose = function () {
                console.log("websocket is off.");
            };
            //发生了错误事件
            iv_socket.onerror = function () {
                console.log("websocket occurs an error.");
            }
        }
    };
    openIVSocket();

    let anc_socket = null;
    let openAncSocket = function () {
        if (typeof (WebSocket) == "undefined") {
            console.log("Can't Support WebSocket");
        } else {
            let socketUrl = "ws://old2dimension.cn/websocket/announcement/" + userId;
            anc_socket = new WebSocket(socketUrl);
            anc_socket.onopen = function () {
                console.log("websocket is on.")
            };
            anc_socket.onmessage = function (msg) {
                $("#anc-count").text(msg.data);
                if (Number(msg.data) > 0) {
                    $("#anc-count").show();
                } else {
                    $("#anc-count").hide();
                }
            };
            anc_socket.onclose = function () {
                console.log("websocket is off.");
            };
            //发生了错误事件
            anc_socket.onerror = function () {
                console.log("websocket occurs an error.");
            }
        }
    };
    openAncSocket();

    // let m_socket = null;
    // let openMSocket = function () {
    //     if (typeof (WebSocket) == "undefined") {
    //         console.log("Can't Support WebSocket");
    //     } else {
    //         let socketUrl = "ws://old2dimension.cn/websocket/chat/" + userId;
    //         m_socket = new WebSocket(socketUrl);
    //         m_socket.onopen = function () {
    //             console.log("websocket is on.")
    //         };
    //         m_socket.onmessage = function (msg) {
    //             $("#m-count").text(msg.data);
    //             if (Number(msg.data) > 0) {
    //                 $("#m-count").show();
    //             } else {
    //                 $("#m-count").hide();
    //             }
    //         };
    //         m_socket.onclose = function () {
    //             console.log("websocket is off.");
    //         };
    //         //发生了错误事件
    //         m_socket.onerror = function () {
    //             console.log("websocket occurs an error.");
    //         }
    //     }
    // };
    // openMSocket();

    let set_invitation_btn = function () {
        $(".iv-refuse").on('click', function (e) {
            console.log("iv-refuse");
            $.ajax({
                type: "post",
                url: "/group/ignoreInvitation",
                headers: {"Authorization": $.cookie('token')},
                dataType: "json",
                contentType: 'application/json',
                data: JSON.stringify({
                    invitationId: $(e.currentTarget).attr("i_id"),
                    userId: userId
                }),
                success: function (data) {
                    if (data.success) {
                        get_invitation();
                    } else {
                        console.log(data.message);
                    }
                },
                error: function (err) {
                    console.log(err);
                }
            });
        });
        $(".iv-agree").on('click', function (e) {
            console.log("iv-refuse");
            $.ajax({
                type: "post",
                url: "/group/joinGroup",
                headers: {"Authorization": $.cookie('token')},
                dataType: "json",
                contentType: 'application/json',
                data: JSON.stringify({
                    id: $(e.currentTarget).attr("i_id"),
                    groupId: $(e.currentTarget).attr("group_id"),
                    userId: userId
                }),
                success: function (data) {
                    if (data.success) {
                        get_invitation();
                    } else {
                        console.log(data.message);
                    }
                },
                error: function (err) {
                    console.log(err);
                }
            });
        });
    };
    let get_invitation = function () {
        $.ajax({
            type: "get",
            url: "/group/getUserInvitations/" + userId,
            headers: {"Authorization": $.cookie('token')},
            dataType: "json",
            contentType: 'application/json',
            success: function (data) {
                if (data.success) {
                    let h = "";
                    data.content.forEach(function (e) {
                        let inviter = "";
                        let group_name = "";
                        $.ajax({
                            type: "get",
                            url: "/user/getById?id=" + e.inviterId,
                            headers: {"Authorization": $.cookie('token')},
                            dataType: "json",
                            contentType: 'application/json',
                            success: function (data) {
                                if (data.success) {
                                    console.log(data.content);
                                    inviter = data.content[0].name;
                                    $.ajax({
                                        type: "get",
                                        url: "/group/getGroupName/" + e.groupId,
                                        headers: {"Authorization": $.cookie('token')},
                                        dataType: "json",
                                        contentType: 'application/json',
                                        success: function (data) {
                                            if (data.success) {
                                                group_name = data.content;
                                                h += `
            <div class="card m-2">
                <div class="card-body m-0 h5">
                    <div>${inviter} invite you to join in group ${group_name}.</div>
                    <div class="my-2" style="text-align: right">` + (e.state === 0 ? `
                        <button class="btn btn-primary iv-agree" i_id="${e.id}" group_id="${e.groupId}">Agree</button>
                        <button class="btn btn-danger iv-refuse" i_id="${e.id}">Refuse</button>` :
                                                    (e.state === 1 ? `<p>Agreed</p>` : `<p>Refused</p>`)) + `
                    </div>
                </div>
            </div>
`;
                                                $("#invitation-container").html(h);
                                                set_invitation_btn();
                                            } else {
                                                console.log(data.message);
                                            }
                                        },
                                        error: function (err) {
                                            console.log(err);
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
                    });

                } else {
                    console.log(data.message);
                }
            },
            error: function (err) {
                console.log(err);
            }
        });
    };

    get_invitation();

    let get_announcement = function () {
        $.ajax({
            type: "get",
            url: "/group/getUnreadAnnouncements/" + userId,
            headers: {"Authorization": $.cookie('token')},
            dataType: "json",
            contentType: 'application/json',
            success: function (data) {
                if (data.success) {
                    let h = "";
                    data.content.forEach(function (e) {
                        h += `
            <div class="card m-2">
                <div class="card-header m-0">
                    ${e.groupName}
                </div>
                <div class="card-body m-0">
                    <h5 class="card-title">${e.announcement.title}</h5>
                    <h6 class="card-subtitle mb-2">${new Date(Date.parse(e.announcement.releaseDate)).toLocaleString("en")}</h6>
                    <p class="card-text">${e.announcement.content}</p>
                </div>
            </div>`;
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
                    $("#announcement-container").html(h);

                } else {
                    console.log(data.message);
                }
            },
            error: function (err) {
                console.log(err);
            }
        });
    };

    $("#invitation-tab").on('click', function () {
        get_invitation();
    });

    $("#announcement-tab").on('click', function () {
        get_announcement();
    });
});
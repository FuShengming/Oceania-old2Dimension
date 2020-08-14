$(document).ready(function () {
    let userId = localStorage['userId'];
    let iv_count = 0;
    let anc_count = 0;
    let m_count = 0;
    if (userId !== undefined && userId !== 1) {
        let iv_socket = null;
        let openIVSocket = function () {
            if (typeof (WebSocket) == "undefined") {
                console.log("Can't Support WebSocket");
            } else {
                let socketUrl = "ws://old2dimension.cn/websocket/invitation/" + userId + "/";
                iv_socket = new WebSocket(socketUrl);
                iv_socket.onopen = function () {
                    console.log("websocket is on.")
                };
                iv_socket.onmessage = function (msg) {
                    iv_count = Number(msg.data);
                    show_cnt();
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
                    anc_count = Number(msg.data);
                    show_cnt();
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
                    m_count = Number(msg.data);
                    show_cnt();
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
    }

    let show_cnt = function () {
        let sum = iv_count + anc_count + m_count;
        $("#n-count").text(sum);
        if (sum > 0) {
            $("#n-count").show();
        } else {
            $("#n-count").hide();
        }
    };

    $("#sign-out-btn").on('click', function () {
        if (confirm("是否要退出登录？")) {
            $.ajax({
                url: "/logout",
                type: "POST",
                success: function (res) {
                    localStorage.removeItem("userId");
                    $(window).attr("location", "/index")
                },
                error: function (res) {
                    console.log(res)
                }
            })
        }
    })
});
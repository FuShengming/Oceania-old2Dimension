$(function () {
    let userId = localStorage['userId'];
    if (userId === undefined) window.location.href = "/login";
    console.log(userId);

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
                                    h += `
            <div class="card m-2">
                <div class="card-body m-0 h5">
                    <div>${inviter} invite you to join in group ${e.groupId}.</div>
                    <div class="my-2" style="text-align: right">
                        <button class="btn btn-primary iv-agree" i-id="${e.id}">Agree</button>
                        <button class="btn btn-danger iv-refuse" i-id="${e.id}">Refuse</button>
                    </div>
                </div>
            </div>
`;
                                    $("#invitation-container").html(h);
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
});
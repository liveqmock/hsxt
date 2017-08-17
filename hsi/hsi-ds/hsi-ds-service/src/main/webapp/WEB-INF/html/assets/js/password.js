var appId = -1;
var envId = -1;
var version = "";
getSession();

// 提交
$("#item_submit").on("click", function (e) {
    $("#error").addClass("hide");
    var oldpass = $("#oldpass").val();
    var newpass = $("#newpass").val();
    var confirmpass = $("#confirmpass").val();

    // 验证
    if (!oldpass || !newpass || !confirmpass) {
        $("#error").removeClass("hide");
        $("#error").html("表单不能为空或填写格式错误！");
        return;
    }
    if(newpass!=confirmpass){
        $("#error").removeClass("hide");
        $("#error").html("两次输入的新密码不一致！");
        return;
    }
    $.ajax({
        type: "POST",
        dataType:"json",
        url: "api/account/password",
        data: {
            "oldpass": oldpass,
            "newpass": newpass,
            "confirmpass": confirmpass
        }
    }).done(function (data) {
        $("#error").removeClass("hide");
        if (data.success === "true") {
            $("#error").html(data.result);
        } else {
            Util.input.whiteError($("#error"), data);
        }
    });
});

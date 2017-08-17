define(["text!commTpl/frame/header.html", "homeSrc/home/home","homeDat/login/login"], function (tpl, home,loginAjax) {
    //加载页头

    var loginInfo = comm.getLoginInfo();
    var data = {
        comLan:comm.lang('common')
    };
    if(loginInfo) {
        data.loginInfo = loginInfo;
    }

    $('#header').html(_.template(tpl,data ));

    $(".header-logo").click(function () {
        location.href = "index.html";
    });

    $("#headerHome").click(function () {
        home.showPage();
    });

    //登出
    $('#liExit').click(function () {
        loginAjax.signOut(function () {
            location.href = "index.html";
        });
    });


    return null;
});

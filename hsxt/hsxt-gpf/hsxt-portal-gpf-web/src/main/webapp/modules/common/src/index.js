define(['text!commTpl/index.html'], function (indexTpl) {
    return {
        showPage: function () {
            $(document.body).html(indexTpl);
            require(["commSrc/frame/header", "commSrc/frame/sideBar",
                    "commSrc/frame/nav", "commSrc/frame/footer", "homeSrc/home/home","commLan"],
                function (header, sideBar, nav, footer, home) {
                    home.showPage();
                    nav.setTabs();
                });
        }
    }
}); 

 
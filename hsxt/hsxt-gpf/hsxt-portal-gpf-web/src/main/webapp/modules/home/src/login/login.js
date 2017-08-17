define(['text!homeTpl/login/login.html',
    'homeDat/login/login',
    'css!homeCss/login.css',
    'homeLan'
], function (loginTpl, loginAjax) {
    var login =  {
        showPage: function () {
            if ($('#tableLogin').length) {
                $('#tableLogin').remove();
            }
            $(document.body).empty().html(loginTpl);

            var bodyHeight = document.documentElement.clientHeight;

            $('#logon_box').height(bodyHeight);

            login.keydown();

            //获取验证码地址
            var imageUrl = comm.domainList[Config.domain] + comm.UrlList['paintImage'];
            //初始化加载验证码
            $('#validCode_image').attr('src', imageUrl + '?tm=' + Math.random());
            //登录
            $('#login_btn').click(function (e) {
                if (login.validate()) {
                    var userInfo = $('#login_optForm').serializeJson();
                    loginAjax.signIn(function (loginInfo) {
                        //将登录信息设置到缓存中
                        comm.setCache("home", "loginInfo", loginInfo);
                        //跳转到主页面
                        require(["commSrc/index"],
                            function (index) {
                                index.showPage();
                            });
                    }, userInfo,function () {
                        $('#exchange_validCode_image').trigger('click');
                    });
                }
            });
            //刷新验证码
            $('#exchange_validCode_image').click(function () {
                $('#validCode_image').attr('src', imageUrl + '?tm=' + Math.random());
            });
        },
        validate: function () {
            return comm.valid({
                formID: '#login_optForm',
                top: -8,
                left: 180,
                rules: {
                    loginUser: {
                        required: true
                    },
                    loginPwd: {
                        required: true
                    },
                    validCode: {
                        required: true
                    }
                },
                messages: {
                    loginUser: {
                        required: '必填'
                    },
                    loginPwd: {
                        required: '必填'
                    },
                    validCode: {
                        required: '必填'
                    }
                }
            });
        },
        keydown:function () {
            $(document).bind('keydown',function(evt){
                var ev = evt || window.event;
                if(ev.keyCode == 13){
                    $('#login_btn').trigger('click');
                }
            });

        }
    };
    return login;
}); 


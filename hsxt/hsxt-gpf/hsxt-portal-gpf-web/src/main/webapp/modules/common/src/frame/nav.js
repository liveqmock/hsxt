define(["text!commTpl/frame/nav.html"], function (tpl) {
    //加载中间菜单
    //$('#menu-tab').html(_.template(tpl  ,  comm.lang('common')   ) );
    /*
     *  设置左边坚标签功能
     *  设置td宽度
     *  设置tab box的宽度
     */
    return {
        setSideMenu: function () {
            //$('#menu-box).parent().css("width", $('#menu-box').width() );
            if ($(window).width() - $('#menu-box').width() - 40 > 1100) {
                //$('#navTab').css('width',  $(window).width() -$('#menu-box').width()-40 );
            }

            //$('table .main-content').css("width", $(window).width() -$('#menu-box').width()-40 );
            //设置左边菜单的显示和隐藏
            $('#sideTab').click(function () {
                if ($('#mainLeftTd').hasClass('none')) {
                    //设置键头方向
                    $('#sideTab >i').removeClass('sideTab_arrow_right');
                    $('#sideTab >i').addClass('sideTab_arrow_left');

                    $('#mainLeftTd').removeClass('none');
                    //获取左td宽度
                    var leftTdWidth = $('#mainLeftTd').width();

                    $('#main-tab').parent().css({width: $('#header').width() - leftTdWidth - 21});
                    //设置右内容宽度
                    $('#main-content').css("width", $('#header').width() - leftTdWidth - 41);

                    //设置tab content宽度
                    //$('#navTab').css('width', $('#header').width()  -leftTdWidth-41  );
                    //$('#main-content').css("width", $('#header').width()  -leftTdWidth -41  );

                } else {
                    //设置键头方向
                    $('#sideTab >i').addClass('sideTab_arrow_right');
                    $('#sideTab >i').removeClass('sideTab_arrow_left');

                    $('#mainLeftTd').addClass('none');
                    //$('#menu-box').parent().css('width','0px');
                    $('#main-tab').parent().css({width: $('#header').width() - 21});
                    $('#main-content').css("width", $('#header').width() - 41);

                    //设置tab content宽度
                    //$('#navTab').css('width', $('.main-content').width() );
                    //$('#main-content').css("width", $('#navTab').width()   );

                }
                //设置分页table的宽度
                //$('.bsgridPagingOutTab').css('width', $('.bsgridPagingOutTab').prev().width() );


            });
            //设置左菜单项宽度
            var thisWidth = $('#menu-box')[0].scrollWidth;
            $('#menu-box >h1').css('width', thisWidth - 35);
            $('#menu-box h2').css('width', thisWidth - 35);
            $('#menu-box li').css('width', thisWidth - 50);


        },

        setSideTab: function () {
            //重设sideTab位置
            var self = this;

            $('#sideTab').css('top', ($(window).height() / 2 - 70 + $(window).scrollTop() ) + 'px');
            $(window).resize(function () {
                //console.log('resizing.....  ' + $('#mainLeftTd').hasClass('none') == true ? 0 : 200);
                //菜单高度
                $('#menu-box').css({height: $(window).height() - 70});
                //重设侧关闭按钮
                //$('#sideTab').css('top',($(window).height()/2-70+$(window).scrollTop())+'px');
                //标签菜单宽度
                $('#nav-tab-box').css({width: $('#header').width() - ( $('#mainLeftTd').hasClass('none') == true ? 0 : 200 ) - 21, 'min-width': $('#header').width() - ( $('#mainLeftTd').hasClass('none') == true ? 0 : 200 ) - 21});
                //内容宽高
                $('#main-content').css({width: $('#header').width() - ( $('#mainLeftTd').hasClass('none') == true ? 0 : 200 ) - 41, 'height': $(window).height() - 70 - 41 - 30, 'min-height': $(window).height() - 70 - 41 - 30});
                //right td高
                $('#mainRightTd').css({'height': $(window).height() - 70 - 41 - 30, 'min-height': $(window).height() - 70 - 41 - 30});


            });

            $('#header').resize(function () {
                //console.log('resizing.....  ' + $('#mainLeftTd').hasClass('none') == true ? 0 : 200);
                //菜单高度
                $('#menu-box').css({height: $(window).height() - 70});
                //重设侧关闭按钮
                //$('#sideTab').css('top',($(window).height()/2-70+$(window).scrollTop())+'px');
                //标签菜单宽度
                $('#nav-tab-box').css({width: $('#header').width() - ( $('#mainLeftTd').hasClass('none') == true ? 0 : 200 ) - 21, 'min-width': $('#header').width() - ( $('#mainLeftTd').hasClass('none') == true ? 0 : 200 ) - 21});
                //内容宽高
                $('#main-content').css({width: $('#header').width() - ( $('#mainLeftTd').hasClass('none') == true ? 0 : 200 ) - 41, 'height': $(window).height() - 70 - 41 - 30, 'min-height': $(window).height() - 70 - 41 - 30});
                //right td高
                $('#mainRightTd').css({'height': $(window).height() - 70 - 41 - 30, 'min-height': $(window).height() - 70 - 41 - 30});


            });

            /*
             //滚动条，重设sideTab 位置
             var scrollFlag = false ;
             $(window).scroll(function(){
             if (scrollFlag) {return ;};
             scrollFlag = true ;
             setTimeout(function(){
             $('#sideTab').animate({'top' : ($(window).scrollTop()+ $(window).height()/2-50)  +'px' },'fast' ,'linear' ,function(){
             scrollFlag = false ;
             }) ;
             },10);

             });
             */
        },

        setTabs: function () {

            this.setSideMenu();
            //滚动提示
            $('#main-tab').mouseover(function (e) {
                if ($('#roll_box').hasClass('none')) {
                    $('#roll_box').removeClass('none');
                }
            });
            $('#main-tab').mouseleave(function (e) {
                if (!$('#roll_box').hasClass('none')) {
                    $('#roll_box').addClass('none');
                }
            });


            //绑定ul标签动作

            $('#nav-tab-box').data('move', 0);
            $('#nav-tab-box').data('posX', 0);
            $('#nav-tab-box').data('left', 0);

            $('#nav-tab-box').mousedown(function (e) {
                $(this).data('move', '1');
                $(this).data('posX', e.pageX);
                $(this).data('left', $(this).find('ul').position().left);
            });

            $('#nav-tab-box').mousemove(function (e) {
                //如果ul宽度小于父div宽度，则不
                if ($(this).find('ul').width() <= $(this).width() + 100) {
                    return;
                }

                if ($(this).data('move') == "1") {
                    var dist = e.pageX - $(this).data('posX') * 1;
                    //console.log(-($(this).find('ul').width()-$(this).find('ul').parent().width() -100) )
                    if ($(this).find('ul').css('left').replace('px', '') > 0) {
                        $(this).find('ul').css('left', ( $(this).data('left') + dist ) / 2 + 'px');
                    } else {
                        $(this).find('ul').css('left', ( $(this).data('left') + dist ) + 'px');
                    }
                }
            });

            $('#nav-tab-box').bind('mouseup', function (e) {

                if ($(this).find('ul').css('left').replace('px', '') == '0') {
                    return;
                }

                $(this).data('move', 0);
                $(this).data('posX', 0);
                $(this).data('left', 0);

                if ($(this).find('ul').css('left').replace('px', '') > 0) {
                    $(this).find('ul').animate({left: '0px'}, 'fast');
                    return;
                }
                if ($(this).find('ul').css('left').replace('px', '') < -($(this).find('ul').width() - $(this).find('ul').parent().width() - 20) && $('#main-tab >li').length > 5) {
                    $(this).find('ul').animate({left: -($(this).find('ul').width() - $(this).find('ul').parent().width() - 20) + 'px'}, 'fast');
                    return;
                }
            });

            $('#nav-tab-box').bind('mouseleave', function (e) {

                if ($(this).find('ul').css('left').replace('px', '') == '0') {
                    return;
                }

                $(this).data('move', 0);
                $(this).data('posX', 0);
                $(this).data('left', 0);

                if ($(this).find('ul').css('left').replace('px', '') > 0) {
                    $(this).find('ul').animate({left: '0px'}, 'fast');
                    return;
                }
                if ($(this).find('ul').css('left').replace('px', '') < -($(this).find('ul').width() - $(this).find('ul').parent().width() - 20) && $('#main-tab >li').length > 5) {
                    $(this).find('ul').animate({left: -($(this).find('ul').width() - $(this).find('ul').parent().width() - 20) + 'px'}, 'fast');
                    return;
                }

            });

            this.setSideTab();


        }

    };
});
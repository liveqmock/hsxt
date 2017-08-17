define(["text!myHsCardTpl/headTab.html",
		"myHsCardDat/myHsCard",
		"myHsCardSrc/cardInfoBind/subHeadTab",
		"myHsCardSrc/realNameReg",
		"myHsCardSrc/realNameAuth",
		"myHsCardSrc/hsCardLossReg",
		"myHsCardSrc/hsCardDisLossReg",
		"myHsCardSrc/hsCardReapply",
		"myHsCardSrc/importantInfoChange"
	], function (tpl, myHsCard, cardInfoBind, realNameReg, realNameAuth, hsCardLossReg, hsCardDisLossReg, hsCardReapply, importantInfoChange) {
	//把参数转换成数组
	var args = [].slice.apply(arguments);
	return {
		show : function () {
			//查询互生卡状态
			myHsCard.findHsCardStatusInfoBycustId(null, function(res){
				//加载顶部导航菜单
				$('#operationsArea').html(tpl);
				//*******************切换顶部导航菜单单击事件*******************
				$('#ul_myhs_right_tab li a').click(function (e) {
					var o = $(this),
					dataId = parseInt(o.attr('data-id'), 10);
					//清除选中状态
					$('#ul_myhs_right_tab li a').removeClass('a_active');
					//切换激活状态
					o.addClass('a_active');
					//加载 卡信息绑定(子菜单)、实名注册、实名认证、互生卡挂失、互生卡解挂、互生卡补办、重要信息变更
					try{
						args[dataId + 1].show(myHsCard);
					}catch(e){}
				});
				
				//处理互生卡挂失/互生卡解挂
				if(res.data && res.data.cardStatus){
					if(res.data.cardStatus == "1"){
						$("#head_tab_5").hide();
					}else if(res.data.cardStatus == "2"){
						$("#head_tab_4").hide();
					}
				}else{
					$("#head_tab_4").hide();
					$("#head_tab_5").hide();
				}
				
				//加载第一个菜单
				$('#ul_myhs_right_tab li a:eq(0)').trigger('click');
			});
		}
	};
});
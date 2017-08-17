define(["text!commTpl/frame/nav.html", "commDat/common", "myHsCardDat/myHsCard", "safetySetDat/safetySet","commSrc/frame/index"],function(tpl, common, myHsCardAjax,safetySetAjax,index){
	
	var isCard = true ;
	
	var hs_isCard = comm.getCookie('hs_isCard');
	//非持卡人判断
	if(comm.isNotEmpty(hs_isCard) && hs_isCard =='1')
	{
		isCard = false ;
	}
	
	//加载导航菜单
	$('#nav_content').html(_.template(tpl,isCard));

	//子菜单事件集合
	var navObj = {};
	//检测检测互生卡信息
	function checkHSCard(callback){
		common.checkHSCard(function(res){
			var code = res.retCode;
			var data = res.data;
			if(code == 200 && data){
				callback();
			} else {
				//跳转登录页
				window.location.replace(comm.domainList["personWeb"]);
			}
		});
	}
	//*******************实名绑定、注册单击事件*******************
    //提示信息关闭按钮
	$("#NoticeCloseBtn").click(function(){
		$(".thickdiv").hide();
		$(".notice_box").hide();
	});
	//实名绑定按钮(取消)
	//$("#a_smbd, #a_smzc").click(function(){
	//重要信息按钮
	$("#promptContent a").live("click",function(){
		var id;
		
		$("#NoticeCloseBtn").trigger("click");
		$('#side_myHsCard').trigger('click');
		
		//获取持卡人信息
		var hs_isCard = comm.getRequestParams()['hs_isCard'];	
		
		if(comm.isNotEmpty(hs_isCard) && hs_isCard =='1') {	//非持卡人
			
			//触发一级菜单单击事件
			$('#side_accountManagement').trigger('click');
			
			id = {
				'mobileBind' : ['#ul_myhs_right_tab a[data-id="3"]'],		//手机绑定
				'emailBind' : ['#ul_myhs_right_tab a[data-id="4"]'],		//邮箱绑定
				'bankBind' : ['#ul_myhs_right_tab a[data-id="5"]']			//跳转银行卡页面
			}[this.rel];
			
		}else{	//持卡人
			id = {
				//"realnameReg" : ['#ul_myhs_right_tab a[data-id="2"]'],
				"realnameAuth" : ['#ul_myhs_right_tab a[data-id="3"]'],
				"mobileBind" : ['#ul_myhs_right_tab a[data-id="1"]','#myhs_kxxbd a[data-id="2"]'],
				"emailBind" : ['#ul_myhs_right_tab a[data-id="1"]','#myhs_kxxbd a[data-id="3"]'],
				"bankBind" : ['#ul_myhs_right_tab a[data-id="1"]','#myhs_kxxbd a[data-id="1"]']
				}[this.rel];
		}
		
		
		if(id && id.length > 0){
			var timeEvent = setInterval(function(){
				for(var i=0;i<id.length;i++){
					var o = $(id[i]);
					if (o.length>0){
						o.trigger('click');
						if((i+1)==id.length){
							clearTimeout(timeEvent);
						}
					}
				}
				
			}, 10);
		}
	});
	//*******************顶部菜单单击事件*******************
	//积分账户、互生币账户、现金账户、投资账户
		
	$("#nav_pointAccount, #nav_hsbAccount, #nav_cashAccount, #nav_investAccount", "#hs_nav_list").click(function(){
		var o = this,mName='';
		var fckr = true ;
		var hs_isCard = comm.getCookie('hs_isCard');
		//非持卡人
		if(isCard ==false)
		{
			mName = o.id.replace('nav_', 'fckr_');
		}
		else	//持卡人
		{
			mName = o.id.replace('nav_', '');
		}
		
		checkHSCard(function(){
			//清除全部左边菜单选中样式
			$('#hs_side_list li').removeClass('li-myhs-left-list-active');
			//清除全部顶部菜单选中样式
			$('#hs_nav_list li').removeClass('li_active');
			//顶部菜单加上选中样式
			$(o).addClass('li_active');
			//加载模块js
			require([mName + "Src/headTab"],function(headTab){	 
				headTab.show();	
			})
		});
	});
	
	//更新官网购物平台链接
	//$("#shopEc").removeClass("dsn").attr('href', comm.domainList["hsecWeb"]);
	
	if($("#nav_reserver_info").length > 0){
		
		
		
		//设置预留信息
		safetySetAjax.findReservationInfo(null,function(response){
			$("#nav_reserver_info").text(comm.navNull(response.data.reservInfo));
		});
		
		//获取实名注册信息
		var jsonParam = {};
//		myHsCardAjax.getPersonInfoByResNo(jsonParam,function(response){
//			//互生用户名
//			$("#card_username").find('em').text(response.baseInfo.custName || '');
//			//取消实名绑定
//			/*if(null == response.baseInfo.custName||"" == response.baseInfo.custName){
//				$("#nav_alert_div_1, #nav_alert_div_2,#nav_alert_div_3, #nav_alert_div_4, #navTrueNameBindP").removeClass("none");
//				$("#navTrueNameRegP").addClass("none");
//			}*/
//			//用户注册
//			if(null != response.baseInfo.regStatus && 'Y' != response.baseInfo.regStatus){
//				$("#nav_alert_div_1, #nav_alert_div_2, #nav_alert_div_3, #nav_alert_div_4, #navTrueNameRegP").removeClass("none");
//				//$("#navTrueNameBindP").addClass("none");
//			}
//		})
	}
	
	//跳转至电商运营系统
	$("#shopEc").live("click",function(){
		var parma={
				"custId": comm.getCookie("custId"),
				"custName" : escape(comm.getCookie("custName")),
				"email" : comm.getCookie("email"),
				"entResNo" : comm.getCookie("entResNo"),
				"isBindBank" : comm.getCookie("isBindBank"),
				"isLocal" : comm.getCookie("isLocal"),
				"isRealnameAuth" : comm.getCookie("isRealnameAuth"),
				"isBindBank" : comm.getCookie("isBindBank"),
				"isAuthMobile" : comm.getCookie("isAuthMobile"),
				"resNo" : comm.getCookie("resNo"),
				'creType' : comm.getCookie("creType"),
				"pointNo" : comm.getCookie("pointNo"),
				"token" : comm.getCookie("token"),
				"userName" : comm.navNull(comm.getCookie("userName")),
				'lastLoginDate' : comm.getCookie("lastLoginDate"),
				'lastLoginIp' : comm.navNull(comm.getCookie("lastLoginIp")),
				'headPic' : comm.navNull(comm.getCookie("headPic")),
				'nickname' : escape(comm.navNull(comm.getCookie("nickname")))
				};
		
		window.open(comm.domainList["shopUrl"]+"?ecParams="+comm.base64Encrypt(JSON.stringify(parma)));
	});
	
	
	//jquery.validate扩展验证
	comm.addMethodValidator();
	
	//首页数据展示
	index.loadIndxData(1);
	
	return tpl;	
});
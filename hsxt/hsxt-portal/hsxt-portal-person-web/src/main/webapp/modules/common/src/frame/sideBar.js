define(["text!commTpl/frame/sideBar.html"], function (tpl) {
	
	var isCard = true ;
	
	var hs_isCard = comm.getCookie('hs_isCard');
	//非持卡人判断
	if(comm.isNotEmpty(hs_isCard) && hs_isCard =='1')
	{
		isCard = false ;
	}
	
	//加载左边导航栏
	$('#side_content').html(_.template(tpl,isCard));
	//*******************左侧菜单单击事件*******************
	//我的互生卡、积分福利、安全设置、系统服务
	$("#side_myHsCard,#side_accountManagement, #side_pointWelfare, #side_safetySet, #side_systemService", "#hs_side_list").click(function (e) {
		var o = this,mName='';
		var fckr = true ;
		//非持卡人
		if(isCard == false)
		{
			mName = o.id.replace('side_', 'fckr_');
		}
		else	//持卡人
		{
			mName = o.id.replace('side_', '');
		}
		
		//id中包含相对应的modules名
		//mName = o.id.replace('side_', '');
		//清除全部顶部菜单选中样式
		$('#hs_nav_list li').removeClass('li_active');
		//清除全部左边菜单选中样式
		$('#hs_side_list li').removeClass('li-myhs-left-list-active');
		//左边菜单加上选中样式
		$(o).addClass('li-myhs-left-list-active');
		//加载模块js
		require([mName + "Src/headTab"], function (headTab) {
			headTab.show();
		})
	});

	return tpl;
});
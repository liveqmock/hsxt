define(["text!commTpl/frame/center.html","text!commTpl/frame/centerFckr.html", "commDat/common","commSrc/frame/index"], function (tpl,fckrTpl, common,index) {
	
	//获取持卡人信息
	var hs_isCard = comm.getRequestParams()['hs_isCard'];
	//非持卡人
	if(comm.isNotEmpty(hs_isCard) && hs_isCard =='1')
	{
		//加载主框架页面
		$('#operationsArea').html(fckrTpl);
		//*******************中间菜单单击事件*******************
		//手机绑定、邮箱绑定、银行卡页面
		//$('#userBind_index, #userRegister_index, #verification_index, #phone_bind_index, #mailBind_index, #bankBind_index').click(function () {
		$('#userRegister_index, #verification_index, #phone_bind_index, #mailBind_index, #bankBind_index').click(function () {
			var id = {
				
				//手机绑定
				'phone_bind_index' : 'ul_myhs_right_tab a[data-id="3"]',
				//邮箱绑定
				'mailBind_index' : 'ul_myhs_right_tab a[data-id="4"]',
				//跳转银行卡页面
				'bankBind_index' : 'ul_myhs_right_tab a[data-id="5"]'
			}[this.id];
			//触发一级菜单单击事件
			$('#side_accountManagement').trigger('click');
			id = id.split(',');
			//设置定时器，查找二级菜单，找到则触发二级菜单单击事件
			var timeEvent = setInterval(function () {
				var o = $('#' + id[0]);
				if (o.length > 0) {
					o.trigger('click');
					clearTimeout(timeEvent);
					if(id[1]){
						var timeEvent1 = setInterval(function () {
							var o = $('#' + id[1]);
							if (o.length > 0) {
								o.trigger('click');
								clearTimeout(timeEvent1);
							}
						}, 10);
					}
				}
			}, 10);
		});

		//加载中部数据
		index.loadcentrData();
		
		//首页数据展示
		index.loadIndxData();
	}
	else
	{
		//加载主框架页面
		$('#operationsArea').html(tpl);

		//*******************中间菜单单击事件*******************
		//实名绑定、实名注册、实名认证、手机绑定、邮箱绑定、跳转银行卡页面
		//$('#userBind_index, #userRegister_index, #verification_index, #phone_bind_index, #mailBind_index, #bankBind_index').click(function () {
		$('#userRegister_index, #verification_index, #phone_bind_index, #mailBind_index, #bankBind_index').click(function () {
			var id = {
				//实名绑定
				//'userBind_index' : 'ul_myhs_right_tab a[data-id="1"]',
				//实名用户注册
//				'userRegister_index' : 'ul_myhs_right_tab a[data-id="2"]',
				//实名认证
				'verification_index' : 'ul_myhs_right_tab a[data-id="3"]',
				//手机绑定
				'phone_bind_index' : 'ul_myhs_right_tab a[data-id="1"],myhs_kxxbd a[data-id="2"]',
				//邮箱绑定
				'mailBind_index' : 'ul_myhs_right_tab a[data-id="1"],myhs_kxxbd a[data-id="3"]',
				//跳转银行卡页面
				'bankBind_index' : 'ul_myhs_right_tab a[data-id="1"]'	
			}[this.id];
			//触发一级菜单单击事件
			$('#side_myHsCard').trigger('click');
			id = id.split(',');
			//设置定时器，查找二级菜单，找到则触发二级菜单单击事件
			var timeEvent = setInterval(function () {
				var o = $('#' + id[0]);
				if (o.length > 0) {
					o.trigger('click');
					clearTimeout(timeEvent);
					if(id[1]){
						var timeEvent1 = setInterval(function () {
							var o = $('#' + id[1]);
							if (o.length > 0) {
								o.trigger('click');
								clearTimeout(timeEvent1);
							}
						}, 10);
					}
				}
			}, 10);
		});

		//加载中部数据
		index.loadcentrData();
		
		//首页数据展示
		index.loadIndxData();
	}
	
	return tpl;
});
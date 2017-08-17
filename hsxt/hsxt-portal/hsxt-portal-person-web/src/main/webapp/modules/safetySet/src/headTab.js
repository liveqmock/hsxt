define(["text!safetySetTpl/headTab.html",
		"safetySetDat/safetySet",
		"safetySetSrc/setReserveInfo",
		"safetySetSrc/setDelPassword",
		"safetySetSrc/setPasswordQuestion",
		"safetySetSrc/modifyReserveInfo",
		"safetySetSrc/modifyPassword",
		"safetySetSrc/modifyDealPassword",
		"safetySetSrc/resetDelPassword"
	], function (tpl, safetySet, setDelPassword) {
	//把参数转换成数组
	var args = [].slice.apply(arguments);
	return {
		show : function () {
			//加载顶部导航菜单
			$('#operationsArea').html(tpl);
			
			var flag = {reserveInfo:false,dealPwd:false};
			//查找安全信息设置信息
			safetySet.findSecuritySetType(function (response) {
				var data=response.data;
				if(data.reserveInfo==false)
				{
					//首次设置预留信息
					$('#setReserveInfoLi').removeClass("none");
					$('#modReserveInfoLi').addClass("none");
					flag.reserveInfo = true;
				}
				else
				{
					//已经设置预留信息，则显示修改预留信息
					$('#setReserveInfoLi').addClass("none");
					$('#modReserveInfoLi').removeClass("none");
				}
				
				if (data.dealPwdFlag == false) 
				{
					//首次设置交易密码
					$('#setDealPwdLi').removeClass("none");
					$('#modDealPwdLi,#reSetDealPwdLi').addClass("none");
					flag.dealPwd = true ;
				} 
				else 
				{
					//交易密码已经存在，则显示修改交易密码
					$('#setDealPwdLi').addClass("none");
					$('#modDealPwdLi,#reSetDealPwdLi').removeClass("none");
				}
				
				//*******************切换顶部导航菜单单击事件*******************
				$('#ul_myhs_right_tab li a').click(function (e) {
					var o = $(this),
					dataId = parseInt(o.attr('data-id'), 10);
					//清除选中状态
					$('#ul_myhs_right_tab li a').removeClass('a_active');
					//切换激活状态
					o.addClass('a_active');
					//加载 设置预留信息、设置交易密码、设置密保问题、修改预留信息、修改登录密码、修改交易密码、交易密码重置
					try{
						args[dataId + 1].show(safetySet);
					}catch(e){}
				});
				
				if(flag.reserveInfo == true )
				{
					$('#ul_myhs_right_tab li a:eq(0)').trigger('click');
				}
				else
				{
					if(flag.dealPwd == true )
					{
						$('#ul_myhs_right_tab li a:eq(1)').trigger('click');
					}
					else
					{
						$('#ul_myhs_right_tab li a:eq(2)').trigger('click');
					}
				}
				//加载第一个菜单
				//0:设置预留信息，1:设置交易密码，2:设置密保问题
				//判断是否首次设置预留信息，是则加载0，否则：判断是否首次设置交易密码，是则加载1，否则加载2
				//$('#ul_myhs_right_tab li a:eq(' + (flag.reserveInfo ? 0 : (flag.dealPwd ? 1 : 2)) + ')').trigger('click');
			
				//加载第一个菜单
				//0:设置预留信息，1:设置交易密码，2:设置密保问题
				//判断是否首次设置预留信息，是则加载0，否则：判断是否首次设置交易密码，是则加载1，否则加载2
			/*	if(comm.getCache('safeSet','resetDelPassword')){
					$('#ul_myhs_right_tab li a').eq(6).trigger('click');
					comm.delCache('safeSet','resetDelPassword');
				}
				else{
					$('#ul_myhs_right_tab li a:eq(' + (flag.reserveInfo ? 0 : (flag.dealPwd ? 1 : 2)) + ')').trigger('click');
				}*/

				if(comm.getCache('safeSet','resetDelPassword')=='1'){
					$('#ul_myhs_right_tab li a').eq(flag.dealPwd?1:6).trigger('click');
					comm.delCache('safeSet','resetDelPassword');
				}
			});
		}
	};
});
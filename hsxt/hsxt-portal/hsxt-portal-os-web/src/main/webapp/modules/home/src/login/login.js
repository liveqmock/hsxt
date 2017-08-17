define([ 'text!homeTpl/login/login.html', 'homeDat/login','homeLan' ], function(loginTpl,
		loginAjax) {
	return {
		showPage : function() {
			// 验证是否登录
			var approvalParam={
					"custId":$.cookie('custId'),
					"loginToken":$.cookie('token')};
			loginAjax.isLogin(approvalParam, function(response) {
				if(response.data == 1){
					require([ "commSrc/index" ], function(index) {
						index.showPage();
					});
				}
			});
			
			
			if ($('#tableLogin').length) {
				$('#tableLogin').remove();
			}
			$(document.body).append(loginTpl);
			 
			$('#tableLogin').css({
				width : document.body.scrollWidth,
				height : document.body.scrollHeight
			});
			
			//change valid
			$('#codeImg').click(function(){
				$('#codeImg').attr('src',  loginAjax.getValidCodeUrl()  + '?randomToken='+$('#randomToken').val()+'&date='+new Date().getTime());
			});
		
			// 获取token
			loginAjax.getToken(function(response) {
				if (response.retCode == 22000) {
					$('#randomToken').val(response.data);
					$('#codeImg').attr('src',  loginAjax.getValidCodeUrl()  + '?randomToken='+response.data+'&date='+new Date().getTime());
				} else {
					alert('请刷新页面后重试');
				}

			}, {});
			
			// 登录
			$('#btnLogin').click(function(e) {
				loginAjax.login(function(response) {
					if (response.retCode == 22000) {

						if ($('#main').length) {
							$('#tableLogin').remove();
							return;
						}
						// 登录成功
						require([ "commSrc/index" ], function(index) {
							index.showPage();
						});
						
						// 设置coockie
						$.cookie('custId', response.data.operCustId);
						$.cookie('token', response.data.token);
						$.cookie('custName', response.data.userName);
						
					}else{
					
						alert(comm.lang("loginResult")[response.retCode]);
						// 重新获取随机token
						$('#randomToken').val(response.data);
						$('#codeImg').attr('src',  loginAjax.getValidCodeUrl()  + '?randomToken='+response.data+'&date='+new Date().getTime());
					}
					

				}, {
					username : $('#username').val(),
					password : comm.encrypt($('#password').val(), $('#randomToken').val()),
					randomToken : $('#randomToken').val(),
					chkCode:$("#vcode").val()
				});

			});
			$('#tableLogin').click(function(e) {
				// alert("tableLogin");
				e.preventDefault();
				e.stopPropagation();
			});
		}
	};
});

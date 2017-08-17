define(["commSrc/encrypt/encrypt","text!loginTpl/contract.html","loginDat/login"],function(encrypt,cTpl,login){
	$("#person_img").attr("src",comm.domainList.ecportal+"/codeImg/bindCodeImg");
	var flag = true;
	
	function nav(data,callBack){
		setLoginCookie(data);
		if(callBack){
			callBack();
		}
//		if(data.reg!=null && data.reg=='N'){
//			$('#dialog').dialog({title:'温馨提示',width:600,height:100,
//				 open: function() {
//					 $('#dialog').addClass("tc mt50");
//					 $('#dialog').html("您尚未进行实名绑定，为保障您在平台的权益，请尽快完善！<a id='reg'>点我进行实名绑定。</a>");
//					
//		         },
//		         close: function(event, ui) {alert(11)}
//			});	
//		}
		$("#person").find("input").val("");
		var rtnUrl = $.getQueryString("rtnUrl");
		if(rtnUrl){
			location.href = decodeURIComponent(rtnUrl);
		}else{
			comm.navUrl("home","local.html");
		}
		
	}
	function commLogin(data){
		var confirmProtocl = data.confirmProtocl;
		var f = data.name.substring(0,1);
		if(confirmProtocl==1 || (f >0 && confirmProtocl==0 )){
			nav(data);
		}else{
			 var $this = $('#dialog');
			 $('#dialog').dialog({title:'',width:800,height:600,
				 open: function() {
					$this.html(cTpl);
					$("#contractCkb").change(function(){
						var checked = $(this).prop("checked");
						if(checked){
							$("#contract_consent").removeClass("hs_btn_un").addClass("hs_btn").bind("click",function(){
								nav(data,function(){
									login.updateConfirmProtocl(function(res) {
										
									});
								});
							});
						}else{
							$("#contract_consent").removeClass("hs_btn").addClass("hs_btn_un").unbind("click");
						}
						
						
//						$("#person").find("input").val("");
//						var rtnUrl = $.getQueryString("rtnUrl");
//						if(rtnUrl){
//							location.href = decodeURIComponent(rtnUrl);
//						}else{
//							comm.navUrl("home","local.html");
//						}
//						setLoginCookie(data);
//						
//						$('#dialog').dialog("destroy");
					});
					
					$("#contract_cancel").click(function(){
						nav(data);
						$('#dialog').dialog("destroy");
					});
		         }
			});	
		}
		
	}
	
	function validateLogin(){
		return $("#personForm").validate({
			
			//设置验证规则   
			rules: {
				"username": {
					required: true
				},
				"userpwd":{
					required: true
				},
				"vercode": {
					required: true
				}
			},
			//设置错误信息  
			messages: {
				"username": {
					required: "请输入互生卡号/手机号码"
				},
				"userpwd": {
					required: "请输入密码"
				},
				"vercode": {
					required: "请输入验证码"
				}
			},
			errorElement:"label",
			errorPlacement: function(error, element){
				$(element).attr("title",$(error).text()).tooltip({
					tooltipClass: "ui-tooltip-error",
					position:{
						my:"left+10 top+35",
						at:"left top"	
					}
				}).tooltip("open");
				$(".ui-tooltip").css("max-width","230px");
			}
		});
	}
	
	$("#personLogin").click(function(){
		var valid=validateLogin();
		if(!valid.form()){
			return;
		};
		if(flag){
			$("#personLogin").val("正在登录中...");
			flag = false;
			var $this = $(this);
			var person = $("#person");
			var name = $.trim(person.find("#username").val());
			var pwd = $.trim(person.find("#userpwd").val());
			pwd = encrypt.encrypted(pwd);
			var vercode = $.trim(person.find("#vercode").val());
			var param = {};
			param["userName"] = name;
			param["userPwd"] = pwd;
			param["loginCode"] = vercode;
			login.personLogin(param,function(response) {
				var code = response.retCode;
				var data = response.data;
				if(code==200){
					commLogin(data);
				}else if(code==209){
					$.commAlert("验证码不正确",function(){
						$("#vercode").focus().select();
						//$("#personLogin").val("登录").attr("type","submit");
					});
					changeImg($this);
				}else if(code==802){
					param["key"] = data.eckey;
					param["mid"] = data.mid;
					login.updateLogin(param,function(ulRes){
						commLogin(ulRes.data);
					});
				}else if(code == 217){
					$.commAlert("您的账号暂被锁定,请联系客服!");
					person.find("#userpwd,#vercode").val("");
					changeImg($this);
				}else if(code == 601){
					$.commAlert("用户名或密码错误!");
					person.find("#userpwd,#vercode").val("");
					changeImg($this);
				}else{
					$.commAlert("登录失败");
					person.find("#userpwd,#vercode").val("");
					changeImg($this);
				}
				flag = true;
				$("#personLogin").val("登录");
			});
		}
	});
	
	function setLoginCookie(data){
		$.cookie('hs_key',data.eckey, { path: '/',domain:comm.firstDomain()});
		$.cookie('hs_mid',data.mid, { path: '/',domain:comm.firstDomain()});
		$.cookie('hs_name',data.name, { path: '/',domain:comm.firstDomain()});
		$.cookie('hs_nick',data.nick, { path: '/',domain:comm.firstDomain()});
		$.cookie('hs_headPic',data.headPic, { path: '/',domain:comm.firstDomain()});
	}

	function chgUrl(url){     
	    var timestamp = (new Date()).valueOf();     
	    urlurl = url.substring(0,17);     
	    if((url.indexOf("&")>=0)){     
	        urlurl = url + "×tamp=" + timestamp;     
	    }else{     
	        urlurl = url + "?timestamp=" + timestamp;     
	    }     
	    return urlurl;     
	}    
	
	function changeImg(obj){     
	    var verImg = $(".verImg");     
	    var src = verImg.attr("src");   
	    verImg.attr("src",chgUrl(src));     
	}  
	// 验证码刷新
	$(".refresh").click(function(){
		changeImg($(this));
	});
	
	$('#person input').keydown(function(e){
		if(e.keyCode==13){
		   $(this).parents("li").next().find(".focus").focus();
		}
	});

	$("#register").click(function(){
		comm.navUrl("register","index.html");
	});
	
	// 忘记密码
	$("#findwpd").click(function(){
		comm.navUrl("findPassword","findPassword.html");
	});
});
define(["text!messageCenterTpl/fsxx/fsxx.html",
		"text!messageCenterTpl/fsxx/dialog.html",
		"text!messageCenterTpl/fsxx/fsxx_yl.html",
		'messageCenterDat/messageCenter',
		'xheditor_cn',
		'messageCenterLan'
		],function(fsxxTpl, dialogTpl, fsxx_ylTpl,messageCenter){
	var fsxxPage = {
		showPage : function(){
			$('#busibox').html(_.template(fsxxTpl)).append(dialogTpl);
			
			fsxxPage.initUpload_btn();
			$('#addmsg').click(function(){
				
				/*弹出框*/
				$( "#dialogTpl" ).dialog({
					title:"选择接收单位",
					width:"300",
					modal:true,
					buttons:{
						"确定":function(){
							
							var checkValue = '';
							var checkName = '';
							
							if(!($('#receivingUnit_1').hasClass('none'))){
								$('#receivingUnit_1').find('input:checked').each(function(){
									checkName += $(this).next().text() + ',';
									checkValue += $(this).val() + ',';
								});					
							}
							else if(!($('#receivingUnit_2').hasClass('none'))){
								$('#receivingUnit_2').find('input:checked').each(function(){
									checkName += $(this).next().text() + ',';
									checkValue += $(this).val() + ',';
								});		
							}
							$('#receiveCompany').val(checkName);
							$('#receiveEntValue').val(checkValue);
							$( "#dialogTpl" ).dialog( "destroy" );
							
						},
						"取消":function(){
							$( "#dialogTpl" ).dialog( "destroy" );
						}
					}
				});
				/*end*/
				
				/*消息类型单选框事件*/
				$("input[name = msgType]").click(function(e){
					var currentValue = $(e.currentTarget).val();
					var receivingUnit_1 = $("#receivingUnit_1");
					var receivingUnit_2 = $("#receivingUnit_2");
					if(currentValue == 1){
						receivingUnit_1.removeClass("none");	
						receivingUnit_2.addClass("none");
					}
					else if(currentValue == 2){
						receivingUnit_2.removeClass("none");
						receivingUnit_1.addClass("none");	
					}
				});
				/*end*/
				
			});
			
			
			
			$(":radio[name=msgType]").click(function(){
				if($(":radio[name=msgType]:checked").val()==12){
					//公开信
					$('#addmsg').show();
					$('#receiveEntValue').val("");
					$('#receiveCompany').val("");
					$('#receiveCompany').attr("readonly","readonly");
				}
				if($(":radio[name=msgType]:checked").val()==20){
					//私信
					$('#addmsg').hide();
					$('#receiveEntValue').val("");
					$('#receiveCompany').val("");
					$('#receiveCompany').removeAttr("readonly");
					/*$('#receiveCompany').unbind("keypress").bind("keypress",function(e){
						var browserName=navigator.userAgent.toLowerCase();
						if(/msie/i.test(browserName) && !/opera/.test(browserName)){
							//IE
					    }else if(/firefox/i.test(browserName)){
					        //Firefox
					    	var keycode = e.which;
							var realkey = String.fromCharCode(e.which);
							$('#receiveCompany').val($('#receiveCompany').val()+realkey);
					    }else if(/chrome/i.test(browserName) && /webkit/i.test(browserName) && /mozilla/i.test(browserName)){
					        //Chrome
					    }else if(/opera/i.test(browserName)){
					        //Opera
					    }else if(/webkit/i.test(browserName) &&!(/chrome/i.test(browserName) && /webkit/i.test(browserName) && /mozilla/i.test(browserName))){
					        //Safari
					    }else{
					        //未知浏览器
					    }
						$('#receiveCompany').val(fsxxPage.checkReceiveVal($('#receiveCompany').val()));
					});*/
				}
			});
			
			$("#btn_send").click(function(){
				if (!fsxxPage.validateData()) {
					return;
				}
				if($("input[name='msgType']:checked").val()=='20'){
					var _rec = $("#receiveEntValue").val().split(",");
					for(var i=0;i<_rec.length;i++){
						if(!fsxxPage.checkMsgReceiver($.cookie('entResNo'),_rec[i])){
							comm.error_alert(comm.lang("messageCenter")[9996]);
							return ;
						}
					}
				}
				var content = $('#msgContent').val();
				if(content == null || "" == content){
					comm.error_alert(comm.lang("messageCenter")[32702]);
					return;
				}
				//上传文件
				var uploadUrl=comm.domainList['apsWeb']+comm.UrlList["fileupload"];
				//上传文件
				comm.uploadFile(uploadUrl,["fileApply"],function(data){
					var images = data.fileApply;
					$("#images").val(images);
					var postData = {
							title : $("#msgTitle").val(),						//消息标题
							msg : content,										//消息内容
							images : images,
							summary : $("#messageDigest").val(),
							type : $("input[name='msgType']:checked").val(),	//消息类型 0公告 1通知 2私信
							level : $("input[name='msgLevel']:checked").val(),	//消息级别 1一般 2重要
							sender : $.cookie('custId'),						//发送人
							createdBy :$.cookie('custId'),
							receiptor : $("#receiveEntValue").val(), 			//接收者
							entResNo : $.cookie('pointNo'),						//发送企业互生号
							entCustId : $.cookie('entCustId'),					//发送企业客户号
							entCustName : unescape($.cookie('custEntName')), 	//发送企业名称
							isSend : true
					};
					messageCenter.sendMessage(postData,function(res){
						//$("#052200000000_subNav_052202000000").click();
						comm.yes_alert(comm.lang("messageCenter").sendSuccess);
						$("#subNav li a[menuurl='messageCenterSrc/yfxx/tab']").click();
					});
					comm.initUploadBtn(["#fileApply"],["#imgApplyImg"],107,64,1);
				},function(){});
			});
			$("#btn_save").click(function(){
				if (!fsxxPage.validateData()) {
					return;
				}
				if($("input[name='msgType']:checked").val()=='20'){
					var _rec = $("#receiveEntValue").val().split(",");
					for(var i=0;i<_rec.length;i++){
						if(!fsxxPage.checkMsgReceiver($.cookie('entResNo'),_rec[i])){
							comm.error_alert(comm.lang("messageCenter")[9996]);
							return ;
						}
					}
				}
				var content = $('#msgContent').val();
				if(content == null || "" == content){
					comm.error_alert(comm.lang("messageCenter")[32702]);
					return;
				}
				//上传文件
				var uploadUrl=comm.domainList['apsWeb']+comm.UrlList["fileupload"];
				//上传文件
				comm.uploadFile(uploadUrl,["fileApply"],function(data){
					var images = data.fileApply;
					$("#images").val(images);
					var postData = {
							title : $("#msgTitle").val(),						//消息标题
							msg : content,										//消息内容
							images : images,
							summary : $("#messageDigest").val(),
							type : $("input[name='msgType']:checked").val(),	//消息类型 0公告 1通知 2私信
							level : $("input[name='msgLevel']:checked").val(),	//消息级别 1一般 2重要
							sender : $.cookie('custId'),						//发送人
							createdBy :$.cookie('custId'),
							receiptor : $("#receiveEntValue").val(), 			//接收者
							entResNo : $.cookie('pointNo'),						//发送企业互生号
							entCustId : $.cookie('entCustId'),					//发送企业客户号
							entCustName : unescape($.cookie('custEntName')),	//发送企业名称
							isSend : false
					};
					messageCenter.sendMessage(postData,function(res){
						//$("#subNav_9_03").click();
						comm.yes_alert(comm.lang("messageCenter").saveSuccess);
						$("#xxcg").click();
						
					});
					
					comm.initUploadBtn(["#fileApply"],["#imgApplyImg"],107,64);
				},function(){});
			});
			
			/*富文本框*/
			$('#msgContent').xheditor({
				tools:"Cut,Copy,Paste,Pastetext,|,Blocktag,FontfaceFontSize,Bold,Italic,Underline,Strikethrough,|,Align,List,Outdent,Indent,|,Source,Preview",
				upLinkExt:"zip,rar,txt",
				upImgExt:"jpg,jpeg,gif,png",
				upFlashExt:"swf",
				upMediaExt:"wmv,avi,wma,mp3,mid",
				width:678,
				height:150
			});
			$(".xheBtnAbout").parent().hide();
			/*end*/
			
			$("#receiveCompany").change(function(){
				var receiveCompany = $.trim($("#receiveCompany").val());
				receiveCompany = receiveCompany.replace(/\s+/g, '');
				receiveCompany = receiveCompany.replace(/，/ig, ',');
				receiveCompany = receiveCompany.replace(/、/ig, ',');
				receiveCompany = receiveCompany.replace(/[^0-9,]*/g,''); 
				receiveCompany = receiveCompany.replace(/,$/,'');
				$("#receiveCompany").val(receiveCompany);
				$("#receiveEntValue").attr("value",$("#receiveCompany").val());
			});
			
			
			$('#fsxx_yl_btn').click(function(){
				if (!fsxxPage.validateData()) {
					return;
				}
				var postData = {
						title : $("#msgTitle").val(),						//消息标题
						msg : $('#msgContent').val(),										//消息内容
						//images : images,
						summary : $("#messageDigest").val(),
						type : $("input[name='msgType']:checked").val(),	//消息类型 0公告 1通知 2私信
						level : $("input[name='msgLevel']:checked").val(),	//消息级别 1一般 2重要
						sender : $.cookie('custId'),						//发送人
						createdBy :$.cookie('custId'),
						receiptor : $("#receiveEntValue").val(), 			//接收者
						entResNo : $.cookie('pointNo'),						//发送企业互生号
						entCustId : $.cookie('entCustId'),					//发送企业客户号
						entCustName : unescape($.cookie('custEntName')),	//发送企业名称
						isSend : false
				};
				postData.typeName = comm.lang("messageCenter").msgtype[postData.type];
				postData.levelName = comm.lang("messageCenter").msglevel[postData.level];
				postData.image = "resources/img/noImg.png";
				
				$('#fsxx_ylTpl').html(_.template(fsxx_ylTpl,postData));
				
				if($.browser.msie && ($.browser.version == '8.0' || $.browser.version == '7.0')){
					$("#fileApply").select();
					var nurl = document.selection.createRange().text;
					var dom = document.getElementById("dialog_img");
					$("#dialog_img").attr("src","resources/img/space.png");
					dom.style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod = scale)";
					dom.filters("DXImageTransform.Microsoft.AlphaImageLoader").src = nurl;
				}else{
					
					if($("#business_apply").attr("data-imgsrc")){
						$("#dialog_img").attr("src",$("#business_apply").attr("data-imgsrc"));
					}
				}
				
				fsxxPage.showTpl($('#fsxx_ylTpl'));
				comm.liActive_add($('#yl'));
				
				$('#yl_back_btn').click(function(){
					fsxxPage.showTpl($('#fsxxTpl'));
					comm.liActive($('#fsxx'), '#xg, #yl');	
				});
				
			});
			
				
		},
		initUpload_btn : function (){
			
			var btnIds = ['#fileApply'];
			var imgIds = ['#business_apply'];
			$("body").on("change", "", function(){
				for(var k in imgIds){
					comm.initTmpPicPreview(imgIds[k], $(imgIds[k]).children().first().attr('src'));
				}
			});
			
			comm.initUploadBtn(btnIds, imgIds);
		},
		checkReceiveVal	: function(str){
			var ret = "";
			for(var i=0;i<str.length;i++){
				if((str.charCodeAt(i)>44&&str.charCodeAt(i)<=57)||str.charCodeAt(i)==44){
					ret+=str.charAt(i);
				}
			}
			return ret;
		},
		checkMsgReceiver	: function(senderId,receiverId){
			return true;
			/*
			if(senderId.substr(0,5)==receiverId.substr(0,5)){
				return true;
			}else{
				return false;
			}
			*/
		},
		validateData : function(){
			jQuery.validator.addMethod('byteRangeLength', function(value, element,limitedLen) {
				var len = comm.countStrLength(value);
				if(len<=limitedLen){
					return true;
				}
				return false;
			}, "");
			return comm.valid({
				formID : '#msgForm',
				rules : {
					msgTitle : {
						required : true,
						byteRangeLength : 40
					},
					messageDigest : {
						required : true,
						maxlength : 100
					},
					receiveCompany : {
						required : true
					}
				},
				messages : {
					msgTitle : {
						required : comm.lang("messageCenter")[32701],
						byteRangeLength : "消息标题只能输入20个汉字"//"请确保输入的值在40个字节以内(一个中文字算2个字节)"//comm.lang("messageCenter")[32709]
					},
					messageDigest : {
						required : comm.lang("messageCenter")[32710],
						maxlength : comm.lang("messageCenter")[32711]
					},
					receiveCompany : {
						required : comm.lang("messageCenter")[32703]
					}
				}
			});
		},
		showTpl : function(tplObj){
			$('#fsxxTpl, #fsxx_ylTpl').addClass('none');
			tplObj.removeClass('none');
		}	
	}
	
	return fsxxPage;
	
});
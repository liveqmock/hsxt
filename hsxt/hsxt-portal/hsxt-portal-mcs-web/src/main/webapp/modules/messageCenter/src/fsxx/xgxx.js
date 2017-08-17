 define(['text!messageCenterTpl/fsxx/xgxx.html',
		 'messageCenterDat/messageCenter','text!messageCenterTpl/fsxx/fsxx_yl.html' ],function(fsxxTpl, messageCenter,fsxx_ylTpl){
	return {
		 
		showPage : function(record){
			if(record){
				$('#busibox').html(_.template(fsxxTpl,record)) ;
			}else{
				$('#busibox').html(_.template(fsxxTpl));
			}
			if(record['images']!=null&&record['images']!=''){
				comm.initPicPreview("#business_apply", record['images']);
				$("#business_apply").html("<img width='107' height='64' src='"+comm.getFsServerUrl(record['images'])+"'/>");
			}else{
				$("#business_apply").html("<img width='107' height='64' src='resources/img/noImg.gif'/>");
			}
			
			var self = this;
			self.initUpload_btn();
			
			//$('#msgContent').xheditor({height:200});
			/*富文本框*/
			$('#msgContent').xheditor({
				tools:"Cut,Copy,Paste,Pastetext,|,Blocktag,FontfaceFontSize,Bold,Italic,Underline,Strikethrough,|,Align,List,Outdent,Indent,|,Source,Preview",
				upLinkExt:"zip,rar,txt",
				upImgExt:"jpg,jpeg,gif,png",
				upFlashExt:"swf",
				upMediaExt:"wmv,avi,wma,mp3,mid",
				width:879,
				height:197
			});
			$(".xheBtnAbout").parent().hide();
			/*end*/
			

			$('#btn_xzjsdw').click(function(){
					$('#jsdw_dialog').dialog({
						 buttons:{
						 	'确定':function(){
						 		var value =  "";
						 		var name = "";
						 		$("input[name='selectReceiveEnt']:checked").each(function(){
						 			value += $(this).val()+",";
						 			name += $(this).next().text()+",";
						 		});
						 		value = value.substring(0,value.length-1);
						 		
						 		//var name = $("input[name='selectReceiveEnt']:checked").next().text();
						 		$("#receiveEntValue").attr("value",value);
						 		$("#receivingUnit").attr("value",name);
						 		$(this).dialog('destroy');
						 	},
						 	'取消':function(){
						 		
						 		$(this).dialog('destroy');
						 	}
						 }
					});
			});
			
			if(record.type==20){
				$('#btn_xzjsdw').hide();
				$('#receivingUnit').removeAttr("readonly");
				$('#receivingUnit').unbind("keyup").bind("keyup",function(){
					$('#receivingUnit').val(self.checkReceiveVal($('#receivingUnit').val()));
				});
			}
			$(":radio[name=msgType]").click(function(){
				if($(":radio[name=msgType]:checked").val()==12){
					//公开信
					$('#btn_xzjsdw').show();
					$('#receiveEntValue').val("");
					$('#receivingUnit').val("");
					$('#receivingUnit').attr("readonly","readonly");
				}
				if($(":radio[name=msgType]:checked").val()==20){
					//私信
					$('#btn_xzjsdw').hide();
					$('#receiveEntValue').val("");
					$('#receivingUnit').val("");
					$('#receivingUnit').removeAttr("readonly");
					/*$('#receivingUnit').unbind("keypress").bind("keypress",function(){
						var browserName=navigator.userAgent.toLowerCase();
						if(/msie/i.test(browserName) && !/opera/.test(browserName)){
							//IE
					    }else if(/firefox/i.test(browserName)){
					        //Firefox
					    	var keycode = e.which;
							var realkey = String.fromCharCode(e.which);
							$('#receivingUnit').val($('#receivingUnit').val()+realkey);
					    }else if(/chrome/i.test(browserName) && /webkit/i.test(browserName) && /mozilla/i.test(browserName)){
					        //Chrome
					    }else if(/opera/i.test(browserName)){
					        //Opera
					    }else if(/webkit/i.test(browserName) &&!(/chrome/i.test(browserName) && /webkit/i.test(browserName) && /mozilla/i.test(browserName))){
					        //Safari
					    }else{
					        //未知浏览器
					    }
						$('#receivingUnit').val(self.checkReceiveVal($('#receivingUnit').val()));
					});*/
				}
			});
			
			$("#btn_send").click(function(){
				if (!self.validateData()) {
					return;
				}
				if($("input[name='msgType']:checked").val()=='20'){
					var _rec = $("#receiveEntValue").val().split(",");
					for(var i=0;i<_rec.length;i++){
						if(!self.checkMsgReceiver($.cookie('entResNo'),_rec[i])){
							comm.error_alert(comm.lang("messageCenter")[9996]);
							return ;
						}
					}
				}
				var content = $('#msgContent').val();
				/*if(content == null || "" == content){
					comm.error_alert(comm.lang("messageCenter")[32702]);
					return;
				}*/
				//上传文件
				var uploadUrl=comm.domainList['mcsWeb']+comm.UrlList["fileupload"];
				//上传文件
				comm.uploadFile(uploadUrl,["fileApply"],function(data){
					var images = data.fileApply;
					$("#images").val(images);
					var postData = {
							msgId : $("#msgId").val(),
							title : $("#msgTitle").val(),						//消息标题
							summary : $("#messageDigest").val(),
							images : $("#images").val(),
							msg : content,										//消息内容
							type : $("input[name='msgType']:checked").val(),	//消息类型 0公告 1通知 2私信
							level : $("input[name='msgLevel']:checked").val(),	//消息级别 1一般 2重要
							receiptor : $("#receiveEntValue").val(), 			//接收者
							entResNo : $.cookie('pointNo'),						//发送企业互生号
							entCustId : $.cookie('entCustId'),					//发送企业客户号
							entCustName : unescape($.cookie('custEntName')), 	//发送企业名称
							updatedBy :$.cookie('custId'),
							sender : $.cookie('custId'),						//发送人
							isSend : true
					};
					messageCenter.editMessage(postData,function(res){
						//$("#040900000000_subNav_040902000000").click();
						comm.yes_alert(comm.lang("messageCenter").sendSuccess);
						$("#subNav li a[menuurl='messageCenterSrc/yfxx/tab']").click();
					});
					//comm.initUploadBtn(["#fileApply"],["#imgApplyImg"],107,64);
				},function(){});
			});
			$("#btn_save").click(function(){
				if (!self.validateData()) {
					return;
				}
				if($("input[name='msgType']:checked").val()=='20'){
					var _rec = $("#receiveEntValue").val().split(",");
					for(var i=0;i<_rec.length;i++){
						if(!self.checkMsgReceiver($.cookie('entResNo'),_rec[i])){
							comm.error_alert(comm.lang("messageCenter")[9996]);
							return ;
						}
					}
				}
				var content = $('#msgContent').val();
				/*if(content == null || "" == content){
					comm.error_alert(comm.lang("messageCenter")[32702]);
					return;
				}*/
				//上传文件
				var uploadUrl=comm.domainList['mcsWeb']+comm.UrlList["fileupload"];
				//上传文件
				comm.uploadFile(uploadUrl,["fileApply"],function(data){
					var images = data.fileApply;
					$("#images").val(images);
					var postData = {
							msgId : $("#msgId").val(),
							title : $("#msgTitle").val(),						//消息标题
							summary : $("#messageDigest").val(),
							images : images,
							msg : content,										//消息内容
							type : $("input[name='msgType']:checked").val(),	//消息类型 0公告 1通知 2私信
							level : $("input[name='msgLevel']:checked").val(),	//消息级别 1一般 2重要
							receiptor : $("#receiveEntValue").val(), 			//接收者
							entResNo : $.cookie('pointNo'),						//发送企业互生号
							entCustId : $.cookie('entCustId'),					//发送企业客户号
							entCustName : unescape($.cookie('custEntName')), 	//发送企业名称
							updatedBy :$.cookie('custId'),
							sender : $.cookie('custId'),						//发送人
							isSend : false
					};
					messageCenter.editMessage(postData,function(res){
						//$("#subNav_9_03").click();
						comm.yes_alert(comm.lang("messageCenter").saveSuccess);
						$("#xxzx_xxcg").click();
					});
					//comm.initUploadBtn(["#fileApply"],["#imgApplyImg"],107,64);
				});
			});
			
			$("#receivingUnit").change(function(){
				var receivingUnit = $.trim($("#receivingUnit").val());
				receivingUnit = receivingUnit.replace(/\s+/g, '');
				receivingUnit = receivingUnit.replace(/，/ig, ',');
				receivingUnit = receivingUnit.replace(/、/ig, ',');
				receivingUnit = receivingUnit.replace(/[^0-9,]*/g,''); 
				receivingUnit = receivingUnit.replace(/,$/,'');
				$("#receivingUnit").val(receivingUnit);
				$("#receiveEntValue").attr("value",$("#receivingUnit").val());
				//$('#receivingUnit').val(self.checkReceiveVal($('#receivingUnit').val()));
				//$("#receiveEntValue").attr("value",$("#receivingUnit").val());
			});
			
			$('#fsxx_yl_btn').click(function(){
				if (!self.validateData()) {
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
				postData.image = "resources/img/noImg.gif";
				if($("#business_apply").attr("data-imgsrc")){
					postData.image = $("#business_apply").attr("data-imgsrc");
				}
				$('#fsxx_ylTpl').html(_.template(fsxx_ylTpl,postData));
				self.showTpl();
				
				$('#yl_back_btn').click(function(){
					$('#fsxx_ylTpl').addClass('none');
					$('#fsxxTpl').removeClass('none');
					$('#xxzx_xxyl').css('display','none').find('a').removeClass('active');
					$('#xxzx_xxcg > a').addClass('active');
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
			if(senderId.substr(0,2)==receiverId.substr(0,2)){
				return true;
			}else{
				return false;
			}
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
					msgId : {
						required : true
					},
					msgTitle : {
						required : true,
						byteRangeLength : 40
					}
				},
				messages : {
					msgId : {
						required : comm.lang("messageCenter")[32704]
					},
					msgTitle : {
						required : comm.lang("messageCenter")[32701],
						byteRangeLength : "消息标题只能输入20个汉字"//"请确保输入的值在40个字节以内(一个中文字算2个字节)"
					}
				}
			});
		},
		showLi : function(liObj){
			$('#xxzx_fsxx > a').removeClass('active');
			liObj.css('display','').find('a').addClass('active');
		},
		showTpl : function(tplObj){
			$('#fsxxTpl').addClass('none');
			$('#fsxx_ylTpl').removeClass('none');
			$('#xxzx_xxyl').css('display','').find('a').addClass('active');
			$('#xxzx_xxcg > a').removeClass('active');
		}	
	}
}); 

 
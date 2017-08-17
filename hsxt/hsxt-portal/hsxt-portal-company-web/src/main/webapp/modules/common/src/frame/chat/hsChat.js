define(["commonSrc/frame/chat/strophe",
	"commSrc/frame/chat/companyChat",
	"commSrc/frame/chat/chat"],function(strophe, comChat, perChat){

	if($.hsChat && $.hsChat.conn && $.hsChat.conn.connected){
		return $.hsChat;
	}
	
	/* 
	 * 互动功能插件  
	 * 
	 */
	$.hsChat = function(options){
		var opts = $.extend({}, $.hsChat.defaults, options);
	};
	
	//设置缺省值
	$.hsChat.defaults = {
		xmpp : comm.domainList.xmpp,
//		xmppProtocol : comm.removeNull(comm.domainList.xmppProtocol)==""?"ws":comm.domainList.xmppProtocol,
		name: $.cookie('custName')+"",
		operName: comm.getCookie("operName")+"",
		id  : $.cookie('custId')+"",
		type: '1',
		key : $.cookie('token'),
		domain: '@im.gy.com',
		uid : 'w_e_'+$.cookie('custId')+ '@im.gy.com/web_im',
		operCustId : 'w_e_'+$.cookie('custId'),
		mid : $.cookie('custId')+',1,'+$.cookie('token')+',' +$.cookie('pointNo')
	};
	
	/**************************************公共部分**************************************/
	
	$.hsChat.comLastMsgTime = "";//内部最后消息发送或者接收时间
	$.hsChat.perLastMsgTime = {};//消费者最后消息发送或者接收时间
	$.hsChat.lastRecMsgFlag = false;//是否已经提示过离线消息
	$.hsChat.lastRecMsgTime = new Date().getTime();//最后接收消息时间
	
	//替换特殊字符
	$.hsChat.clearSpecWord = function(chatContent) {
		chatContent = chatContent.replace(/\&#216;/img , ' \r\n');
		chatContent = chatContent.replace(/&lt;br &gt;/ig, '\r\n');
		chatContent = chatContent.replace(/&amp;lt;br &amp;gt;/ig, '\r\n');
		chatContent = chatContent.replace(/@font-face{[^\}]*}/img , '');
		chatContent = chatContent.replace(/@page{[^\}]*}/img , '');
		chatContent = chatContent.replace(/h[\d]{[^\}]*}/img , '');
		chatContent = chatContent.replace(/&lt;o\:p&gt;([^\:]*)&lt;\/o\:p&gt;/ig,"$1");
	    chatContent = chatContent.replace(/<o\:p><\/o\:p>/ig,"");
		chatContent = chatContent.replace(/p\.[a-z0-9\:]{1,200}{[^\}]*}/img , '');
		chatContent = chatContent.replace(/span\.[a-z0-9:]{1,1000}{[^\}]*}/img , '');
		chatContent = chatContent.replace(/div[\.\ ][a-z0-9\:]{1,200}{[^\}]*}/img , '');
		chatContent = chatContent.replace(/@page[\.\ ][a-z0-9\:]{1,200}{[^\}]*}/img , '');
		chatContent = chatContent.replace(/@list[\.\ ][a-z0-9\:]{1,200}{[^\}]*}/img , '');
		chatContent = chatContent.replace(/class="[^"]+"/ig, '');			  
	    chatContent = chatContent.replace(/class\="[^"]+"/gi, '');		  
	    chatContent = chatContent.replace(/<h1.*?>(.*?)<\/h1>/ig,"$1");		   
	    chatContent = chatContent.replace(/<h2.*?>(.*?)<\/h2>/ig,"$1");		   
	    chatContent = chatContent.replace(/<h3.*?>(.*?)<\/h3>/ig,"$1");		   
	    chatContent = chatContent.replace(/<h4.*?>(.*?)<\/h4>/ig,"$1");		   
	    chatContent = chatContent.replace(/<h5.*?>(.*?)<\/h5>/ig,"$1");		   
	    chatContent = chatContent.replace(/<h6.*?>(.*?)<\/h6>/ig,"$1");
	    chatContent = chatContent.replace(/<div.*?>(.*?)<\/div>/ig,"\\r\\n$1");		   
	    chatContent = chatContent.replace(/<span.*?>(.*?)<\/span>/ig,"$1");	
	    chatContent = chatContent.replace(/<p.*?>(.*?)<\/p>/ig,"$1");		   
	    chatContent = chatContent.replace(/<i.*?>(.*?)<\/i>/ig,"$1");
	    chatContent = chatContent.replace(/<[a-z]\:[a-z]>(.*?)<\/[a-z]\:[a-z]>/ig,"$1");    
	    chatContent = chatContent.replace(/<strong.*?>(.*?)<\/strong>/ig,"$1");		   
	    chatContent = chatContent.replace(/<a.*?>(.*?)<\/a>/ig,"$1");	
	    chatContent = chatContent.replace(/&nbsp;/ig, ' ');
		chatContent = chatContent.replace(/↵/ig,"\\r\\n");
		chatContent = chatContent.replace(/<br>/img,"\\r\\n");
	    chatContent = chatContent.replace(/style\="[^"]+"/gi, '');
	    return chatContent;
	};
	
	/**
	 * 初始化图片div,查看大图
	 * @param objId 对象ID
	 * @param fileId 文件ID
	 * @param title 抬头（可选参数，默认图片查看）
	 * @param divId 绑定的Div（可选参数，默认showImage50）
	 * @param width 预览窗口宽度（可选参数，默认800）
	 * @param height 预览窗口高度（可选参数，默认600）
	 */
	$.hsChat.initPicPreview = function(objId, url, title, divId, width, height) {
		title = !title?"查看大图":title;
		divId = !divId?"#showImage50":divId;
		logId = divId+"Div";
		$(objId).click(function(e){
			var buttons={};
			 buttons['关闭'] =function(){
				$(this).dialog("destroy");
			};
			if (null != url && "" != url) {
				var objImg = $.hsChat.autoResizeImage(width, height);
				$(logId).html('<img alt="图片加载失败..." src="'+url+'" id="showImage50" width="100%" height="100%">');
				$(logId).dialog({
					title : title,
					width : objImg.width+26,
					height : objImg.height+100,
					modal : true,
					buttons : buttons
				});
				$(logId).css("width", "auto");
				$(logId).css("height", "auto");
			}
		});
	}
	
	/**
	 * 控制图标大小，按比例缩放
	 * @param nailsW 缩略图宽度
	 * @param nailsHeight 缩略图高度
	 */
	$.hsChat.autoResizeImage = function(nailsW, nailsH){
		var maxHeight = document.body.clientHeight-100;//最大高度
		nailsW = parseInt(nailsW);
		nailsH = parseInt(nailsH);
		if(nailsW == 300){
			nailsW = nailsW/2;
			nailsH = nailsH/2;
		}
		var ratio = 1.25;
		if(nailsW >= nailsH){//宽图
			ratio = 5;
		}else{//长图
			if(nailsH >= maxHeight){
				ratio = maxHeight/nailsH;
			}else{
				if(nailsH <= maxHeight/2){
					ratio = 2;
				}
			}
		}
		return {height:nailsH*ratio, width:nailsW*ratio};
	}
	
	/**
     * 接收消息回调方法
     */
	 function onMessage(msg) {
		//消息毫秒时间戳
		var msgid = msg.getAttribute('id');
		var to = msg.getAttribute('to');
		var from = msg.getAttribute('from');
		var type = msg.getAttribute('type');
		var elems = msg.getElementsByTagName('body');
		var isOffLineMessage = msg.getElementsByTagName('delay').length > 0;//是否为离线消息
		
		//发送回执信息
		var request = msg.getElementsByTagName('request');

		//用户名
		var fromID = from.substring(from.indexOf("0"),from.indexOf("0") + from.indexOf("0",11));
		var userIcon = "";//用户头像
		var nickName = "";//用户昵称
		var flag = true;
		
		if (elems.length > 0) {
			var body = elems[0];
			var msgJson = Strophe.getText(body);
			var newstr=msgJson.replace(/&quot;/g,"'").replace(/\n/ig, " ");
			var jsonobj = eval('('+newstr+')');
			var content = jsonobj.msg_content;
			userIcon = comm.removeNull(jsonobj.msg_icon);
			if(userIcon != "" && userIcon.indexOf("http") == -1){
				userIcon = comm.domainList.fsServerUrl+"/"+userIcon;
			}
			nickName = comm.removeNull(jsonobj.msg_note);
		}
		
		if(request.length > 0){
			var id = request[0].getElementsByTagName("id");
			var receipt = Strophe.xmlElement('receipt');
			receipt.setAttribute("xmlns","gy:abnormal:offline");
			receipt.appendChild(Strophe.xmlElement("id",Strophe.getText(id[0])));
			receipt.appendChild(Strophe.xmlElement("sender",$.hsChat.defaults.uid));
			var reply = $msg({from: $.hsChat.defaults.uid, type: 'chat'}).cnode(receipt);
		    $.hsChat.conn.send(reply.tree());
		}
			
		//2.即时聊天消息
		if(jsonobj.msg_type == 2){
			
			//及时消息播放提示音（规则:距离收到上次消息5秒以上）
			if(!isOffLineMessage){
				if((new Date().getTime()-$.hsChat.lastRecMsgTime)/1000 > 5){
					$.hsChat.playMedia();
				}
				$.hsChat.lastRecMsgTime = new Date().getTime();
			}else{//离线消息播放提示音（规则:所有离线只提示一次）
				if(!$.hsChat.lastRecMsgFlag){
					$.hsChat.lastRecMsgFlag = true;
					$.hsChat.playMedia();
				}
			}
			
			//文本消息
			if(jsonobj.msg_code == "00" ){
				content = $.hsChat.Replace_img(content, jsonobj);
				content = content.replace(/&lt;/g,"<");
				content = content.replace(/&gt;/g,">");
				content = content.replace(/&amp;/g, "&");
				content = content.replace(/<\/div>*|<br><\/div>*/gi,"<br>");
				content = content.replace(/<div>*/gi,"");
			}
			//图片消息
			if(jsonobj.msg_code == "10"){
				
			}
			var terminalType = from.substring(0, from.split("@")[0].lastIndexOf("_"));
			var formUserId = $.hsChat.getCustId(from);
			//操作员内部聊天
			if(terminalType == "w_e" || terminalType == "m_e"){
				if($.hsChat.toUserId == formUserId && !$("#companyChatBox").hasClass("none")){//是当前窗口
					if(!isOffLineMessage){
						var dateTimeStr = $.hsChat.getTimeStr($.hsChat.getCurrTime(), $.hsChat.comLastMsgTime);
						$.hsChat.comLastMsgTime = $.hsChat.getCurrTime();
						$.hsChat.appendComTime(dateTimeStr);
						if(jsonobj.msg_code == "00"){
							$.hsChat.showComMsgByID(content, true, jsonobj.msg_code);
							$.hsChat.dealComRecvMessage(formUserId, content, true, jsonobj.msg_code, msgid);
						}else{
							jsonobj.objId = "img_"+msgid; 
							$.hsChat.showComMsgByID(jsonobj, true, jsonobj.msg_code);
							$.hsChat.dealComRecvMessage(formUserId, content, true, jsonobj.msg_code, msgid);
						}
					}
				}else{//非当前窗口
					if(jsonobj.msg_code == "00"){
						$.hsChat.dealComRecvMessage(formUserId, content, false, jsonobj.msg_code, msgid);
					}else{
						jsonobj.objId = "img_"+msgid;
						$.hsChat.dealComRecvMessage(formUserId, content, false, jsonobj.msg_code, msgid);
					}
				}
			}else{//操作员与消费者聊天
				var isCurrent = $('#chatBox_area').children('div[id="div_' + formUserId + '"]').length == 1;//是否已经打开与当前消费者窗口
				if(isCurrent){//是当前窗口
					if(!isOffLineMessage){
						var dateTimeStr = $.hsChat.getTimeStr($.hsChat.getCurrTime(), $.hsChat.perLastMsgTime[formUserId]);
						$.hsChat.perLastMsgTime[formUserId] = $.hsChat.getCurrTime();
						$.hsChat.appendPerTime(dateTimeStr, formUserId);
						if(jsonobj.msg_code == "00"){
							$.hsChat.showPerMsgByID(content, true, jsonobj.msg_code, formUserId, false);
							$.hsChat.dealPerRecvMessage(formUserId, content, true, jsonobj.msg_code, null, userIcon, msgid);
						}else{
							jsonobj.objId = "img_"+msgid;
							$.hsChat.showPerMsgByID(jsonobj, true, jsonobj.msg_code, formUserId, false);
							$.hsChat.dealPerRecvMessage(formUserId, content, true, jsonobj.msg_code, null, userIcon, msgid);
						}
					}
				}else{//非当前窗口
					var person = {};
					if(to.indexOf("nc") > -1 || from.indexOf("nc") > -1){
						person.perType = "w_nc_";//非持卡人
						person.userName = $.hsChat.getNickerName(nickName);
					}else{
						person.perType = "w_c_";//持卡人
						person.userName = $.hsChat.getPersonName(formUserId, nickName);
					}
					person.realName = person.userName;
					person.userImg = userIcon;
					if(jsonobj.msg_code == "00"){
						$.hsChat.dealPerRecvMessage(formUserId, content, false, jsonobj.msg_code, person, userIcon, msgid);
					}else{
						jsonobj.objId = "img_"+msgid;
						$.hsChat.dealPerRecvMessage(formUserId, content, false, jsonobj.msg_code, person, userIcon, msgid);
					}
				}
			}
		}
		return true;
	}
	
	/*连接到xmpp服务器
	Status.ERROR 0--错误
	Status.CONNECTING  1--正在创建连接
	Status.CONNFAIL  2--连接创建失败
	Status.AUTHENTICATING  3--正在验证
	Status.AUTHFAIL  4--验证失败
	Status.CONNECTED  5--连接创建成功
	Status.DISCONNECTED 6--连接已关闭
	Status.DISCONNECTING  7--连接正在关闭
	*/
	$.hsChat.connect = function(onConnect){
		//建立连接
//		$.hsChat.conn = new Strophe.Connection($.hsChat.defaults.xmpp);
//		$.hsChat.conn = new Strophe.Connection($.hsChat.defaults.xmpp, {protocol: $.hsChat.defaults.xmppProtocol});
		//连接参数 用户 ，密码(id+',1,'+tookenkey)
		
		//$.hsChat.conn.rawInput = function (data) { console.log('RECV: ' + data); }; 
		//$.hsChat.conn.rawOutput = function (data) { console.log('SEND: ' + data); };
		
//		$.hsChat.conn.connect($.hsChat.defaults.uid ,$.hsChat.defaults.mid, onConnect || function(status){
//			if (status == Strophe.Status.CONNECTED) {//连接成功
//				$.hsChat.conn.addHandler(onMessage, null, 'message', null, null, null);
//				$.hsChat.conn.send($pres().tree());
//			}else if(status== Strophe.Status.DISCONNECTED){//连接断开，重新连接
//				$.hsChat.conn.connect($.hsChat.defaults.uid, $.hsChat.defaults.mid, arguments.callee);
//			}else if (status == Strophe.Status.CONNFAIL || status == Strophe.Status.AUTHFAIL){
//				if(isClickChat == 1){
//					alert("连接消息中心失败！");
//				}
//			}
//		}); 
	}
	
	/**替换特定的文字为指定表情*/
	$.hsChat.Replace_img = function(content, jsonobj){
		//替换标签图片
		var reg = new RegExp("\\[(.| )+?\\]", "igm");
		var arr = content.match(reg);
		if(arr != null){
			for(i=0;i<arr.length;i++){
				var s = content.indexOf(arr[i]);
				imgnum = content.substr(s, 5);
				content = content.replace(imgnum, "<img src=\"resources/img/biaoqing/"+imgnum.substr(1, 3)+".png\">");
			}
			return content;
		}else{
			if(jsonobj){
				return jsonobj.msg_content;
			}else{
				return content;
			}
		}
	};
	
	/**替换表情为指定的文字格式*/
	$.hsChat.Replace_Code = function(content){
		var reg = new RegExp('<img src="resources/img/biaoqing/', "igm");
		var arr = content.match(reg);
		if(arr != null){
			var oldStr = "";
			var newStr = "";
			for(i=0;i<arr.length;i++){
				var s = content.indexOf(arr[i]);
				oldStr = content.substr(s, 42);
				newStr = "["+oldStr.toLowerCase().replace('<img src="resources/img/biaoqing/', "").replace('.png">', "")+"]";
				content = content.replace(oldStr, newStr);
			}
		}
		return content;
	};
	
	/**窗口置前*/
	$.hsChat.showFocusBox = function(boxTpl){
		$('#chatBox, #companyChatBox, #xtxx_box').css('z-index', '98');
		boxTpl.css('z-index', '99');
	}
	
	/**绑定窗口置前*/
	$('#chatBox, #companyChatBox').live('click', function(){
		$.hsChat.showFocusBox($(this));
		if( $(this).attr('id') == 'chatBox'){
			perChat.bindingEvents();
		}else{
			comChat.bindingEvents();
		}
	});
	
	/*获取客户号*/
	$.hsChat.getCustId = function(idString){
		try {
			var tempStr = idString.split("@")[0];
			return tempStr.substring(tempStr.lastIndexOf('_')+1, tempStr.length);
		} catch(e){
			return "";
		}
	};
	
	/*获取客户名称*/
	$.hsChat.getPersonName = function(name, nickName){
		if(nickName){
			return nickName;
		}
		var tempStr = comm.removeNull(name);
		if(tempStr.length > 11){
			return tempStr.substring(0, 11);
		}
		return tempStr;
	};
	
	/**************************************企业级部分**************************************/
	
	$.hsChat.totalMsgCount = 0;//未读消息总条数
	
	//初始化事件 
	$.hsChat.initComEvent = function(){
		var self = this;
		//导航按钮事件
		$('#chat_nav_toolbar >div:eq(0) ').on('click', function(){ 
			if($("#chat_list_box").is(":hidden")){
				$("#chat_list_box, #chat_list_sidebar").show();
				$("#chat_list_sidebar").animate({"right" : 0}, 400);
			} 
		});
		
		//关闭消息菜单列表
		$("#chat_list_close").click(function(){
			$("#chat_list_sidebar").animate({"right" : -302 + "px"}, 400, function(){
				$("#chat_list_box").hide();
				//$("#person_show_btn").bind('click', contactList_fun);
			});
		});
		
		//切换列表标签
		$('#chat_person_list_content >div:eq(0)').on('click','a' ,function(e){
			e.preventDefault();
			e.stopPropagation();
			if ( $(this).hasClass('msg-chat') && !$(this).hasClass('act') ){
				$(this).addClass('act').siblings().removeClass('act');
				$('#chat_menu_msg_list >ul:eq(0)').removeClass('none');
				$('#chat_menu_msg_list >ul:eq(1)').removeClass('none');
				$('#chat_menu_msg_list >ul:eq(2)').addClass('none');
			} else if ( $(this).hasClass('msg-company') && !$(this).hasClass('act') ){
				$(this).addClass('act').siblings().removeClass('act');
				$('#chat_menu_msg_list >ul:eq(0)').addClass('none');
				$('#chat_menu_msg_list >ul:eq(1)').addClass('none');
				$('#chat_menu_msg_list >ul:eq(2)').removeClass('none');
			}
		});
		
		//系统消息，订单消息，订阅消息，服务消息
		$('#chat_menu_msg_list >ul:eq(0)').on('click','li',function(){
			var id= $(this).attr('data-id'); 
			switch(id){
				case '1':
					$('#chat_msg_list_title').text('系统消息');										
					break;
				case '2':
					$('#chat_msg_list_title').text('订单消息');
					break;	
				case '3':
					$('#chat_msg_list_title').text('订阅消息');
					break;
				case '4':
					$('#chat_msg_list_title').text('服务消息');
					break;
			}
			$('#chat_msg_list_title').attr('data-id',id);
			$('#chat_person_list_content').addClass('none');
			$('#chat_menu_msg_list_content').removeClass('none'); 
		});
		
		//返回消息主菜单列表
		$('#chat_back_msg_list').click(function(){			
			$('#chat_person_list_content').removeClass('none');
			$('#chat_menu_msg_list_content').addClass('none');
		});
		
		//企业级聊天-发送消息事件
		$('#chat_pop_msg_box_btn').click(function(){
			var content = $.trim($('#companyChatBox').find('.tool_talkinput_textarea').html());
			if (!content){
				return;
			}
//			content = $('<p>'+$.hsChat.Replace_Code(content)+'</p>').text();
			content = $.hsChat.Replace_Code(content);
			$.hsChat.sendComMessage(content, false, "00");
        });
		
		$(document).keydown(function(e){
			if(e.ctrlKey && e.keyCode == 13){
				$('#chat_pop_msg_box_btn').click();
			}
		});
	};
	
	/**企业级聊天--消息发送*/
	$.hsChat.sendComMessage = function(content, type, msgCode){
		var obj = {};
		var ptettr = null;
		var dateTimeStr = $.hsChat.getTimeStr($.hsChat.getCurrTime(), $.hsChat.comLastMsgTime);
		$.hsChat.comLastMsgTime = $.hsChat.getCurrTime();
		$.hsChat.appendComTime(dateTimeStr);
		if(msgCode == "10"){
			obj.sub_msg_code = "10101";
			obj.msg_type = "2";
			obj.msg_code = "10";
			obj.msg_content = content.tfsServerUrl+content.bigImgUrl;
			obj.msg_imageNailsUrl = content.tfsServerUrl+content.smallImgUrl;
			obj.msg_imageNails_width = content.smallImageWidth;
			obj.msg_imageNails_height = content.smallImageHeight;
			obj.msg_icon = "";
			obj.msg_note = comm.removeNull($.hsChat.defaults.operName);
			obj.msg_vshopId = "vShopId";
			var image = {};
			image.msg_content = content.tfsServerUrl+content.bigImgUrl;
			image.msg_imageNailsUrl = content.tfsServerUrl+content.smallImgUrl;
			image.bigImageWidth = content.bigImageWidth;
			image.bigImageHeight = content.bigImageHeight;
			image.msg_imageNails_width = content.smallImageWidth;
			image.msg_imageNails_height = content.smallImageHeight;
			image.objId = "img_"+content.smallImgUrl.replace(/\./ig, "");
			$.hsChat.showComMsgByID(image, false, msgCode);
			ptettr = JSON.stringify(obj);
		}else{
			content = $.trim(comm.valueReplace($.hsChat.clearSpecWord(content)));
			obj.sub_msg_code = "10101";
			obj.msg_type = "2";
			obj.msg_code = "00";
			obj.msg_content = "msgContent";
			obj.msg_icon = "";
			obj.msg_note = comm.removeNull($.hsChat.defaults.operName);
			obj.msg_vshopId = "vShopId";
			$.hsChat.showComMsgByID(content, false, msgCode);
			ptettr = JSON.stringify(obj).replace("msgContent", content);
		}
		var reply = $msg({to: "w_e_"+$.hsChat.toUserId+'@im.gy.com', from:  $.hsChat.defaults.uid , type: 'chat'}).cnode(Strophe.xmlElement('body', '' ,ptettr));
		$.hsChat.conn.send(reply.tree());
		$('#companyChatBox').find('.tool_talkinput_textarea').text('');
		$.hsChat.dealComSendMessage(content, $.hsChat.toUserId, msgCode);
	}
	
	//企业级聊天--显示消息 type:true 接受到的消息，false自己发送的消息
	$.hsChat.showComMsgByID = function(msg, type, msgCode){
		var divCont = '';
		var msgObj = null;
		if(msgCode == 10){
			msgObj = msg;
			var wd = parseInt(msgObj.msg_imageNails_width);
			var ht = parseInt(msgObj.msg_imageNails_height);
			if(wd == 300){
				wd = wd/2;
				ht = ht/2;
			}
			msg = '<img id='+msgObj.objId+' src='+msgObj.msg_imageNailsUrl+' height='+ht+' width='+wd+'/>';
		}else{
			msg = $.hsChat.Replace_img(msg, null);
			msg = msg.replace(/\\r\\n/igm, '<br>').replace(/\r\n/igm, '<br>');
			msg = msg.replace(/&lt;o\:p&gt;([^\:]*)&lt;\/o\:p&gt;/ig, '$1');
			msg = msg.replace(/&lt;!--EndFragment--&gt;/ig, '');
		}
		msg = $.hsChat.Replace_img(msg, null);
		if (type){
			divCont = '<div class="clearfix talk_history_list"> \
				<span class="fl"><img src="resources/img/ico-chat4.jpg" width="44"></span> \
				 <span class="fl ml10 pr"> <i class="i_ico chat_arrow_left"></i> \
				<div class="chat_box_gray_company">'+ msg +'</div> \
				</span> </div>';
			
			//console.log('-------------------RECV: ' + msg);
		} else {
			divCont = '<div class="clearfix talk_history_list"> \
				<span class="fr"><img src="resources/img/ico-chat4.jpg" width="44"></span> \
				<span class="fr mr10 pr"> <i class="i_ico chat_arrow_right"></i> \
					<div class="chat_box_blue_company">'+ msg +'</div> \
					</span> </div>';
					
			//console.log('-------------------SEND: ' + msg);
		}
		//添加到消息内容
		$('#companyChatBox .talk_history_company').append(divCont);
		
		if(msgObj != null){
			var nailsImageWidth = !msgObj.msg_imageNails_width?null:msgObj.msg_imageNails_width;
			var nailsImageHeight = !msgObj.msg_imageNails_height?null:msgObj.msg_imageNails_height;
			$.hsChat.initPicPreview("#"+msgObj.objId, msgObj.msg_content, null, null, nailsImageWidth, nailsImageHeight);
		}
		
		$.hsChat.comMsgBoxScroll();
	};
	
	//企业级聊天--滚动消息
	$.hsChat.comMsgBoxScroll = function() {
		var scroll = false;
		//滚动显示消息
		if (!scroll){
			scroll = true;
			$('#companyChatBox .talk_history_company').animate({scrollTop: $('#companyChatBox .talk_history_company').scrollTop()+1500},0,function(){
				scroll = false;
			}); 
		} 
	}
	
	/**企业级聊天--处理接收消息记录*/
	$.hsChat.dealComRecvMessage = function(formUserId, content, isCurrent, msgCode, msgid){
		if(msgCode != "10"){
			content = content.replace(/\\r\\n/igm, ' ');
		}
		var userList = comm.getCache("chatCache", "userList");
		var tempList = [];
		tempList[0] = null;
		$.each(userList, function(key, user){
			if(user.operCustId == formUserId){
				tempList[0] = user;
				tempList[0].lastTime = $.hsChat.getRecvDate(msgid);
				tempList[0].msgContent = (msgCode == "10")?"[图片]":content;
				if(!isCurrent){
					$.hsChat.addComMessageCount();
					tempList[0].msgCount = (comm.removeNull(user.msgCount)==""?0:user.msgCount)+1;
				}
			}else{
				tempList.push(user);
			}
		});
		comm.delCache("chatCache", "userList");
		comm.setCache("chatCache", "userList", tempList);
		$.hsChat.comSearch(tempList);
	}
	
	/**企业级聊天--处理发送消息记录*/
	$.hsChat.dealComSendMessage= function(content, toUserId, msgCode){
		content = (msgCode == "10")?"[图片]":$.hsChat.Replace_img(content, null);
		content = content.replace(/\\r\\n/igm, ' ');
		var userList = comm.getCache("chatCache", "userList");
		var tempList = [];
		tempList[0] = null;
		$.each(userList, function(key, user){
			if(user.operCustId == toUserId){
				tempList[0] = user;
				tempList[0].lastTime = $.hsChat.getCurrDate();
				tempList[0].msgContent = content;
			}else{
				tempList.push(user);
			}
		});
		comm.delCache("chatCache", "userList");
		comm.setCache("chatCache", "userList", tempList);
		$.hsChat.comSearch(tempList);
	}
	
	//企业级聊天--查找用户
	$.hsChat.findComUserById = function(userList, usid, tsid){
		for(var key in userList){
			var user = userList[key];
			if(user.operCustId == usid || user.operCustId == tsid){
				comm.arrayRemove(userList, key);
				return user;
			}
		}
		return null;
	}
	
	//企业级聊天--加载搜索结果
	$.hsChat.comSearch = function(userList){
		$("#qytxl_ul").html("");
		$.each(userList, function(key, user){
			var liId = "li_"+user.operCustId;
			var dataId = user.operCustId+"_"+$.trim(user.userName);
			var userLi="<li id="+liId+" data-id="+dataId+"><dl>";
			userLi+="<dt><img src=\"resources/img/ico-chat4.jpg\"/></dt>";
			if(user.msgCount){
				userLi+="<span class=\"count\" style=\"font-size:12px;\">"+(user.msgCount>99?"99+":user.msgCount)+"</span>";
				userLi+="<span class=\"hide_count\" style=\"display:none;\">"+user.msgCount+"</span>";
			}
			userLi+="<dd class=\"name\"><span class=\"hd-name-limit\">"+user.realName+"</span></dd>";
			if(user.msgContent){
				userLi+="<dd><span class=\"hd-txt-limit\">"+user.msgContent+"</span></dd>";
			}else{
				userLi+="<dd><span class=\"hd-txt-limit\"></span></dd>";
			}
			userLi+="<dd class=\"time\">"+comm.removeNull(user.lastTime)+"</dd>";
			userLi+="</dl></li>";
			$("#qytxl_ul").append(userLi);
		});
		$.hsChat.bindComChat();
	}
	
	//企业级聊天--企业通讯录聊天窗口操作事件
	$.hsChat.bindComChat = function(){
		$('#qytxl_ul').find('li').click(function(){
			var user = {};
			user.realName = $(this).find('.name').text();
			user.userImg = $(this).find('dt img').attr('src');
			user.userName = $(this).attr('data-id').split("_")[1];
			user.userCustId = $(this).attr('data-id').split("_")[0];
			comChat.showChatBox(user);
			var unReadMsgCount = comm.removeNull($(this).find('.hide_count').html());
			$.hsChat.showComHistory(user, unReadMsgCount, $(this));
			var userList = comm.getCache("chatCache", "userList");
			$.each(userList, function(key, user_){
				if(user_.operCustId == user.userCustId){
					user_.msgCount = 0;
				}
			});
			$.hsChat.subComMessageCount(unReadMsgCount);
			$.hsChat.showFocusBox($("#companyChatBox"));
		});
	}
	
	//企业通讯录聊天窗口操作事件（搜索）
	$.hsChat.comChatClick = function(custId){
		var liObj = $("#"+"li_"+custId);
		if(!liObj){
			return;
		}
		var user = {};
		user.realName = liObj.find('.name').text();
		user.userImg = liObj.find('dt img').attr('src');
		user.userName = liObj.attr('data-id').split("_")[1];
		user.userCustId = liObj.attr('data-id').split("_")[0];
		comChat.showChatBox(user);
		var unReadMsgCount = comm.removeNull($(this).find('.hide_count').text());
		$.hsChat.showHistory(user, unReadMsgCount, liObj);
		var userList = comm.getCache("chatCache", "userList");
		$.each(userList, function(key, user_){
			if(user_.operCustId == user.userCustId){
				user_.msgCount = 0;
			}
		});
		$.hsChat.subComMessageCount(unReadMsgCount);
		$.hsChat.showFocusBox($("#companyChatBox"));
	}
	
	//企业级聊天--增加未读消息数量
	$.hsChat.addComMessageCount = function(){
		$.hsChat.totalMsgCount++;
		if($.hsChat.totalMsgCount > 0){
			if($.hsChat.totalMsgCount > 99){
				$("#com_msg_count").html("企业通讯录(99+)");
			}else{
				$("#com_msg_count").html("企业通讯录("+$.hsChat.totalMsgCount+")");
			}
		}else{
			$("#com_msg_count").html("企业通讯录");
		}
	};
	
	//企业级聊天--减少未读消息数量
	$.hsChat.subComMessageCount = function(msgCount){
		$.hsChat.totalMsgCount = $.hsChat.totalMsgCount-(comm.removeNull(msgCount) == ""?0:msgCount);
		if($.hsChat.totalMsgCount > 0){
			if($.hsChat.totalMsgCount > 99){
				$("#com_msg_count").html("企业通讯录(99+)");
			}else{
				$("#com_msg_count").html("企业通讯录("+$.hsChat.totalMsgCount+")");
			}
		}else{
			$("#com_msg_count").html("企业通讯录");
		}
	};
	
	//企业级聊天--查找聊天记录
	$.hsChat.showComHistory = function(user, unReadMsgCount, liObj){
		var pageSize = 50;
		if(unReadMsgCount && parseInt(unReadMsgCount) > pageSize){
			pageSize = unReadMsgCount;
		}
		var array = comm.getMonthSE();
		var params = {};
		params.channelType = "1";
		params.custId = comm.getRequestParams().custId;
		params.entResNo = comm.getRequestParams().pointNo;
		params.loginToken = comm.getRequestParams().token;
		params.data = {
			"fromUid":params.custId,
			"toUid":user.userCustId,
			"messageKey":"",
			"dateQueryType":"M",
			"pageNum":"1",
			"pageSize":pageSize,
			"beginDate":array[0],
			"endDate":array[1]
		};
		var json = JSON.stringify(params);
		comm.Request({url:"queryMessageRecordList", domain:"xmppBservice"},{
			data:json,
			type:'POST',
			dataType:"json",
			async:false,
			success:function(response){
				if(response.retCode == 200){
					$.each(response.rows.reverse(), function(key, msg){
						var dateTimeStr = null;
						if(key == 0){
							dateTimeStr = response.rows[0].dateTimeStr;
						}else{
							dateTimeStr = $.hsChat.getTimeStr(response.rows[key].dateTimeStr, response.rows[key-1].dateTimeStr);
						}
						$.hsChat.appendComTime(dateTimeStr);
						$.hsChat.onComMessage(msg);
					});
					if(response.rows && response.rows.length > 0){
						$.hsChat.comLastMsgTime = response.rows[response.rows.length-1].dateTimeStr;
						$.hsChat.comMsgBoxScroll();
					}else{
						$.hsChat.comLastMsgTime = $.hsChat.getCurrTime();
					}
					liObj.find(".count").hide();
					liObj.find(".count").html("");
					liObj.find(".hide_count").html("");
				}else{
					$.hsChat.comLastMsgTime = $.hsChat.getCurrTime();
					comm.error_alert("获取消息失败...");
				}
			},
			error: function(){
				$.hsChat.comLastMsgTime = $.hsChat.getCurrTime();
				liObj.find(".count").hide();
				liObj.find(".count").html("");
				liObj.find(".hide_count").html("");
				comm.error_alert("获取消息失败...");
			}
		});
	}
	
	//获取消息时间
	$.hsChat.getTimeStr = function(staTimeStr, endTimeStr){
		var sta = Date.parse(new Date(staTimeStr))/1000;
		var end = Date.parse(new Date(endTimeStr))/1000;
		if((sta*1-end*1) > 20){
			return staTimeStr;
		}
		return null;
	}
	
	//显示消息时间
	$.hsChat.appendComTime = function(dateTimeStr){
		if(dateTimeStr){
			var array = dateTimeStr.split(" ");
			var dateTime = "";
			if($.hsChat.getYMDTime() == array[0]){
				dateTime = array[1].split(":")[0]+":"+array[1].split(":")[1];
			}else if($.hsChat.getYMDTime(-1) == array[0]){
				dateTime = "昨天";
			}else if($.hsChat.getYMDTime(-2) == array[0]){
				dateTime = "前天";
			}else{
				dateTime = array[0].replace("-", "年").replace("-", "月")+"日";
			}
			var divCont = "<div class=\"talk_history_list tc cl-gray\">"+dateTime+"</div>";
			$('#companyChatBox .talk_history_company').append(divCont);
		}
	}
	
	//企业级聊天--查询最近联系人
	$.hsChat.getComRecentContactsMsgList = function(pageNum, pageSize, callBack){
		var params = {};
		params.channelType = "1";
		params.custId = comm.getRequestParams().custId;
		params.entResNo = comm.getRequestParams().pointNo;
		params.loginToken = comm.getRequestParams().token;
		params.data = {
			"toUid":params.custId,
			"ifOperator":"Y",
			"targetUserType":"e",
			"pageNum":pageNum,
			"pageSize":pageSize
		};
		var json = JSON.stringify(params);
		comm.Request({url:"queryRecentContactsMsgList", domain:"xmppBservice"},{
			data:json,
			type:'POST',
			dataType:"json",
			async:false,
			success:function(response){
				if(response.retCode == 200){
					callBack(response.rows);
				}else{
					console.log("获取企业级最近联系人失败...");
					callBack(null);
				}
			},
			error: function(){
				console.log("获取企业级最近联系人失败...");
				callBack(null);
			}
		});
	}
	
	/**
     * 企业级聊天--接收消息回调方法
     */
	 $.hsChat.onComMessage = function(msg) {
		var to = msg.toUid.substring(msg.toUid.lastIndexOf('_')+1, msg.toUid.length);
		var from = msg.fromUid.substring(msg.fromUid.lastIndexOf('_')+1, msg.fromUid.length);
		var content = msg.message;
		content = content.replace(/&lt;/g,"<");
		content = content.replace(/&gt;/g,">");
		content = content.replace(/&amp;/g, "&");
		content = content.replace(/<\/div>*|<br><\/div>*/gi,"<br>");
		content = content.replace(/<div>*/gi,"");
		content = content.replace(/&quot;/g,"'").replace(/\n/ig, " ");
		if(msg.messageCode == "00" ){
			content = $.hsChat.getRealContent(content);
			$.hsChat.showComMsgByID(content, from != $.hsChat.defaults.id, msg.messageCode);
		}
		//图片消息
		if(msg.messageCode == "10"){
			content = eval("("+content+")");
			var imageUrl = content.msg_content;
			content.objId = "img_"+imageUrl.substring(imageUrl.lastIndexOf('/')+1, imageUrl.length).replace(/\./ig, "");
			$.hsChat.showComMsgByID(content, from != $.hsChat.defaults.id, msg.messageCode);
		}
	}
	
	/**************************************消费者部分**************************************/
	
	$.hsChat.perPageSize = 7;//最近联系人一页显示数量
	$.hsChat.perCurPage = 1;//最近联系人当前页码
	$.hsChat.hisPageSize = 50;//历史消息加载消息数量
	
	/**消费者聊天--消息发送*/
	$.hsChat.sendPerMessage = function(content, type, msgCode, perType){
		var obj = {};
		var ptettr = null;
		var curCustId = $.hsChat.getCurrentCustId();
		var dateTimeStr = $.hsChat.getTimeStr($.hsChat.getCurrTime(), $.hsChat.perLastMsgTime[curCustId]);
		$.hsChat.perLastMsgTime[curCustId] = $.hsChat.getCurrTime();
		$.hsChat.appendPerTime(dateTimeStr, curCustId);
		if(msgCode == "10"){
			obj.sub_msg_code = "10101";
			obj.msg_type = "2";
			obj.msg_code = "10";
			obj.msg_content = content.tfsServerUrl+content.bigImgUrl;
			obj.msg_imageNailsUrl = content.tfsServerUrl+content.smallImgUrl;
			obj.msg_imageNails_width = content.smallImageWidth;
			obj.msg_imageNails_height = content.smallImageHeight;
			obj.msg_icon = $.hsChat.getCompanyIcon();
			obj.msg_note = comm.removeNull(comm.getRequestParams().custEntName);
			obj.msg_vshopId = "";
			var image = {};
			image.msg_content = content.tfsServerUrl+content.bigImgUrl;
			image.msg_imageNailsUrl = content.tfsServerUrl+content.smallImgUrl;
			image.bigImageWidth = content.bigImageWidth;
			image.bigImageHeight = content.bigImageHeight;
			image.msg_imageNails_width = content.smallImageWidth;
			image.msg_imageNails_height = content.smallImageHeight;
			image.objId = "img_"+content.smallImgUrl.replace(/\./ig, "");
			$.hsChat.showPerMsgByID(image, false, msgCode, null, false);
			ptettr = JSON.stringify(obj);
		}else{
			content = $.trim(comm.valueReplace($.hsChat.clearSpecWord(content)));
			obj.sub_msg_code = "10101";
			obj.msg_type = "2";
			obj.msg_code = "00";
			obj.msg_content = "msgContent";
			obj.msg_icon = $.hsChat.getCompanyIcon();
			obj.msg_note = comm.removeNull(comm.getRequestParams().custEntName);
			obj.msg_vshopId = "";
			$.hsChat.showPerMsgByID(content, false, msgCode, null, false);
			ptettr = JSON.stringify(obj).replace("msgContent", content);
		}
		var reply = $msg({to: perType+curCustId+"@im.gy.com", from:  $.hsChat.defaults.uid , type: 'chat'}).cnode(Strophe.xmlElement('body', '' , ptettr));
		$.hsChat.conn.send(reply.tree());
		$.hsChat.getCurrentBoxObj().find('.tool_talkinput_textarea').text('');
		$.hsChat.dealPerSendMessage(content, curCustId, msgCode);
	}
	
	//消费者聊天--显示消息 type:true 接受到的消息，false自己发送的消息
	$.hsChat.showPerMsgByID = function(msg, type, msgCode, formUserId, isHistroy){
		var divCont = '';
		var msgObj = null;
		if(msgCode == 10){
			msgObj = msg;
			var wd = parseInt(msgObj.msg_imageNails_width);
			var ht = parseInt(msgObj.msg_imageNails_height);
			if(wd == 300){
				wd = wd/2;
				ht = ht/2;
			}
			msg = '<img id='+msgObj.objId+' src='+msgObj.msg_imageNailsUrl+' height='+ht+' width='+wd+'/>';
		}else{
			msg = $.hsChat.Replace_img(msg, null);
			msg = msg.replace(/\\r\\n/igm, '<br>').replace(/\r\n/igm, '<br>');
			msg = msg.replace(/&lt;o\:p&gt;([^\:]*)&lt;\/o\:p&gt;/ig, '$1');
			msg = msg.replace(/&lt;!--EndFragment--&gt;/ig, '');
		}
		msg = $.hsChat.Replace_img(msg, null);

		if (type){
			var userIcon = $('#chatBox_area').children('div[id="div_' + formUserId + '"]').find('.chat_title_img').find("img").attr("src");
			divCont = '<div class="clearfix talk_history_list"> \
				<span class="fl"><img src="'+userIcon+'" width="44" height="44"></span> \
				 <span class="fl ml10 pr"> <i class="i_ico chat_arrow_left"></i> \
				<div class="chat_box_gray_company">'+ msg +'</div> \
				</span> </div>';
			
//			console.log('-------------------RECV: ' + msg);
		} else {
			divCont = '<div class="clearfix talk_history_list"> \
				<span class="fr"><img src="resources/img/ico-chat4.jpg" width="44"></span> \
				<span class="fr mr10 pr"> <i class="i_ico chat_arrow_right"></i> \
					<div class="chat_box_blue_company">'+ msg +'</div> \
					</span> </div>';
					
//			console.log('-------------------SEND: ' + msg);
		}
		
		//添加到消息内容
		if (formUserId) {
			if(!isHistroy){
				$('#li_'+formUserId).addClass("highLight_bg");
			}
			$('#chatBox_area').children('div[id="div_' + formUserId + '"]').find('.talk_history').append(divCont);
		} else {
			$.hsChat.getCurrentBoxObj().find('.talk_history').append(divCont);
		}
		
		if(msgObj != null){
			var nailsImageWidth = !msgObj.msg_imageNails_width?null:msgObj.msg_imageNails_width;
			var nailsImageHeight = !msgObj.msg_imageNails_height?null:msgObj.msg_imageNails_height;
			$.hsChat.initPicPreview("#"+msgObj.objId, msgObj.msg_content, null, null, nailsImageWidth, nailsImageHeight);
		}
		
		$.hsChat.perMsgBoxScroll(formUserId);
	};
	
	//消费者聊天--滚动消息
	$.hsChat.perMsgBoxScroll = function(formUserId) {
		var scroll = false;
		//滚动显示消息
		if (!scroll){
			scroll = true;
			var curObj = null;
			if(formUserId){
				curObj = $('#chatBox_area').children('div[id="div_' + formUserId + '"]').find(".talk_history");
			}else{
				curObj = $.hsChat.getCurrentBoxObj().find(".talk_history");
			}
			curObj.animate({scrollTop: curObj.scrollTop()+1500},0,function(){
				scroll = false;
			}); 
		} 
	}
	
	/**消费者聊天--处理接收消息记录*/
	$.hsChat.dealPerRecvMessage = function(formUserId, content, isCurrent, msgCode, person_, userImg, msgid){
		if(msgCode != "10"){
			content = content.replace(/\\r\\n/igm, ' ');
		}
		var personList = comm.getCache("chatCache", "personList");
		var tempList = [];
		tempList[0] = null;
		$.each(personList, function(key, person){
			if(person.userCustId == formUserId){
				tempList[0] = person;
				tempList[0].userImg = userImg;
				tempList[0].lastTime = $.hsChat.getRecvDate(msgid);
				tempList[0].msgContent = (msgCode == "10")?"[图片]":content;
				if(isCurrent){
					tempList[0].msgCount = null;
				}else{
					$.hsChat.addMessageCount(formUserId);
					tempList[0].msgCount = $.hsChat.getMessageCount(formUserId);
				}
			}else{
				tempList.push(person);
			}
		});
		if(tempList[0] == null && !isCurrent){
			$.hsChat.addMessageCount(formUserId);
			person_.userCustId = formUserId;
			person_.lastTime = $.hsChat.getRecvDate(msgid);
			person_.msgContent = (msgCode == "10")?"[图片]":content;
			person_.msgCount = $.hsChat.getMessageCount(formUserId);
			tempList[0] = person_;
			if(tempList.length > $.hsChat.perPageSize){
				tempList.pop();
			}
			comm.delCache("chatCache", "personList");
			comm.setCache("chatCache", "personList", tempList);
			$.hsChat.perSearch(tempList);
		}else{
			$.hsChat.perSearch(personList);
		}
	};
	
	/**消费者聊天--处理发送消息记录*/
	$.hsChat.dealPerSendMessage = function(content, toUserId, msgCode){
		content = (msgCode == "10")?"[图片]":$.hsChat.Replace_img(content, null);
		content = content.replace(/\\r\\n/igm, ' ');
		var personList = comm.getCache("chatCache", "personList");
		var tempList = [];
		tempList[0] = null;
		$.each(personList, function(key, person){
			if(person.userCustId == toUserId){
				tempList[0] = person;
				tempList[0].msgCount = 0;
				tempList[0].lastTime = $.hsChat.getCurrDate();
				tempList[0].msgContent = content;
			}else{
				tempList.push(person);
			}
		});
		comm.delCache("chatCache", "personList");
		comm.setCache("chatCache", "personList", tempList);
		$.hsChat.perSearch(tempList);
	}
	
	//消费者聊天--查找用户
	$.hsChat.findPerUserById = function(personList, usid, tsid){
		for(var key in personList){
			var person = personList[key];
			if(person.operCustId == usid || person.operCustId == tsid){
				comm.arrayRemove(personList, key);
				return person;
			}
		}
		return null;
	}
	
	//消费者聊天--加载搜索结果
	$.hsChat.perSearch = function(personList){
		$("#zxxx_ul").html("");
		$.each(personList, function(key, person){
			var dataId = person.userCustId+"_"+$.trim(person.userName);
			var userLi="<li data-id="+dataId+"><dl>";
			if(comm.removeNull(person.userImg) != ""){
				userLi+="<dt><img width=44 height=44 src=\""+person.userImg+"\"/></dt>";
			}else{
				userLi+="<dt><img width=44 height=44 src=\"resources/img/ico-chat4.jpg\"/></dt>";
			}
			if(person.msgCount){
				userLi+="<span class=\"count\" style=\"font-size:9px;\">"+(person.msgCount>99?"99+":person.msgCount)+"</span>";
				userLi+="<span class=\"hide_count\" style=\"display:none;\">"+person.msgCount+"</span>";
			}
			userLi+="<dd class=\"name\"><span class=\"hd-name-limit\">"+person.realName+"</span></dd>";
			if(person.msgContent){
				userLi+="<dd><span class=\"hd-txt-limit\">"+person.msgContent+"</span></dd>";
			}else{
				userLi+="<dd><span class=\"hd-txt-limit\"></span></dd>";
			}
			userLi+="<dd class=\"perType\" style=\"display:none;\">"+person.perType+"</dd>";
			userLi+="<dd class=\"time\">"+comm.removeNull(person.lastTime)+"</dd>";
			userLi+="</dl></li>";
			$("#zxxx_ul").append(userLi);
		});
		$.hsChat.bindPerChat();
	}
	
	//消费者聊天--企业通讯录聊天窗口操作事件
	$.hsChat.bindPerChat = function(){
		$('#zxxx_ul').find('li').click(function(){
			var person = {};
			person.realName = $(this).find('.name').text();
			person.userImg = $(this).find('dt img').attr('src');
			person.userName = $(this).attr('data-id').split("_")[1];
			person.userCustId = $(this).attr('data-id').split("_")[0];
			person.hsCardId = $.hsChat.getPersonName(person.userCustId);
			person.perType = $(this).find('.perType').text();
			perChat.showChatBox(person);
			var unReadMsgCount = comm.removeNull($(this).find('.hide_count').html());
			$.hsChat.showPerHistory(person, unReadMsgCount, $(this));
			var personList = comm.getCache("chatCache", "personList");
			$.each(personList, function(key, person_){
				if(person_.userCustId == person.userCustId){
					$.hsChat.subMessageCount(person.userCustId, $.hsChat.getMessageCount(person.userCustId));
					person_.msgCount = 0;
				}
			});
			var msgCount = $.hsChat.getMessageCount("totalCount");
			if(msgCount > 0){
				$("#chat-count").show();
				$("#chat-count").html(msgCount > 99?"99+":msgCount);
			}else{
				$("#chat-count").hide();
			}
			$.hsChat.showFocusBox($("#chatBox"));
		});
	}
	
	//消费者聊天--获取未读消息数量
	$.hsChat.getMessageCount = function(userCustId){
		var messageCount = comm.getCache("chatCache", "messageCount");
		return messageCount[userCustId];
	};
	
	//消费者聊天--增加未读消息数量
	$.hsChat.addMessageCount = function(userCustId){
		var messageCount = comm.getCache("chatCache", "messageCount");
		if(!messageCount[userCustId]){
			messageCount[userCustId] = 0;
		}
		messageCount[userCustId] = messageCount[userCustId]+1;
		messageCount["totalCount"] = messageCount["totalCount"]+1;
		$("#chat-count").show();
		$("#chat-count").html(messageCount["totalCount"] > 99?"99+":messageCount["totalCount"]);
	};
	
	//消费者聊天--减少未读消息数量
	$.hsChat.subMessageCount = function(userCustId, msgCount){
		var messageCount = comm.getCache("chatCache", "messageCount");
		if(!messageCount[userCustId]){
			messageCount[userCustId] = 0;
		}
		messageCount[userCustId] = 0;
		messageCount["totalCount"] = messageCount["totalCount"]-(comm.removeNull(msgCount) == ""?0:msgCount);
	};
	
	//消费者聊天--查找聊天记录
	$.hsChat.showPerHistory = function(person, unReadMsgCount, liObj){
		var pageSize = $.hsChat.hisPageSize;
		if(unReadMsgCount && parseInt(unReadMsgCount) > pageSize){
			pageSize = unReadMsgCount;
		}
		var array = comm.getMonthSE();
		var params = {};
		params.channelType = "1";
		params.custId = comm.getRequestParams().custId;
		params.entResNo = comm.getRequestParams().pointNo;
		params.loginToken = comm.getRequestParams().token;
		params.data = {
			"fromUid":params.custId,
			"toUid":person.userCustId,
			"messageKey":"",
			"dateQueryType":"M",
			"pageNum":"1",
			"pageSize":pageSize,
			"beginDate":array[0],
			"endDate":array[1]
		};
		var json = JSON.stringify(params);
		comm.Request({url:"queryMessageRecordList", domain:"xmppBservice"},{
			data:json,
			type:'POST',
			dataType:"json",
			async:false,
			success:function(response){
				if(response.retCode == 200){
					$.each(response.rows.reverse(), function(key, msg){
						var dateTimeStr = null;
						if(key == 0){
							dateTimeStr = response.rows[0].dateTimeStr;
						}else{
							dateTimeStr = $.hsChat.getTimeStr(response.rows[key].dateTimeStr, response.rows[key-1].dateTimeStr);
						}
						$.hsChat.appendPerTime(dateTimeStr, person.userCustId);
						$.hsChat.onPerMessage(msg);
					});
					if(response.rows && response.rows.length > 0){
						$.hsChat.perLastMsgTime[person.userCustId] = response.rows[response.rows.length-1].dateTimeStr;
						$.hsChat.perMsgBoxScroll();
					}else{
						$.hsChat.perLastMsgTime[person.userCustId] = $.hsChat.getCurrTime();
					}
					liObj.find(".count").hide();
					liObj.find(".count").html("");
					liObj.find(".hide_count").html("");
				}else{
					$.hsChat.perLastMsgTime[person.userCustId] = $.hsChat.getCurrTime();
					comm.error_alert("获取消息失败...");
				}
			},
			error: function(){
				$.hsChat.perLastMsgTime[person.userCustId] = $.hsChat.getCurrTime();
				liObj.find(".count").hide();
				liObj.find(".count").html("");
				liObj.find(".hide_count").html("");
				comm.error_alert("获取消息失败...");
			}
		});
	};
	
	//显示消息时间
	$.hsChat.appendPerTime = function(dateTimeStr, curCustId){
		if(dateTimeStr){
			var array = dateTimeStr.split(" ");
			var dateTime = "";
			if($.hsChat.getYMDTime() == array[0]){
				dateTime = array[1].split(":")[0]+":"+array[1].split(":")[1];
			}else if($.hsChat.getYMDTime(-1) == array[0]){
				dateTime = "昨天";
			}else if($.hsChat.getYMDTime(-2) == array[0]){
				dateTime = "前天";
			}else{
				dateTime = array[0].replace("-", "年").replace("-", "月")+"日 "+dateTime;
			}
			var divCont = "<div class=\"talk_history_list tc cl-gray\">"+dateTime+"</div>";
			$('#chatBox_area').children('div[id="div_' + curCustId + '"]').find(".talk_history").append(divCont);
		}
	}
	
	//消费者聊天--查询最近联系人
	$.hsChat.getPerRecentContactsMsgList = function(pageNum, callBack){
		var params = {};
		params.channelType = "1";
		params.custId = comm.getRequestParams().custId;
		params.loginToken = comm.getRequestParams().token;
		params.data = {
			"toUid":params.custId,
			"ifOperator":"Y",
			"targetUserType":"c",
			"pageNum":pageNum,
			"pageSize":$.hsChat.perPageSize
		};
		var json = JSON.stringify(params);
		comm.Request({url:"queryRecentContactsMsgList", domain:"xmppBservice"},{
			data:json,
			type:'POST',
			dataType:"json",
			async:false,
			success:function(response){
				if(response.retCode == 200){
					$.hsChat.perCurPage = pageNum;//分页成功后将当前页码存储
					callBack(response.rows, response.totalRows);
				}else{
					console.log("获取消费者最近联系人失败...");
					callBack(null);
				}
			},
			error: function(){
				console.log("获取消费者最近联系人失败...");
				callBack(null);
			}
		});
	};
	
	/**
     * 消费者聊天--接收消息回调方法
     */
	 $.hsChat.onPerMessage = function(msg) {
		var to = msg.toUid.substring(msg.toUid.lastIndexOf('_')+1, msg.toUid.length);
		var from = msg.fromUid.substring(msg.fromUid.lastIndexOf('_')+1, msg.fromUid.length);
		var content = msg.message;
		content = content.replace(/&lt;/g,"<");
		content = content.replace(/&gt;/g,">");
		content = content.replace(/&amp;/g, "&");
		content = content.replace(/<\/div>*|<br><\/div>*/gi,"<br>");
		content = content.replace(/<div>*/gi,"");
		content = content.replace(/&quot;/g,"'").replace(/\n/ig, " ");
		var fromId = from;
		var toId = to.substring(to.lastIndexOf('_')+1, to.length);
		if($.hsChat.defaults.id == from){
			fromId = to;
		}
		if(msg.messageCode == "00" ){
			content = $.hsChat.getRealContent(content);
			$.hsChat.showPerMsgByID(content, $.hsChat.defaults.id != from, msg.messageCode, fromId, true);
		}
		//图片消息
		if(msg.messageCode == "10"){
			content = eval("("+content+")");
			var imageUrl = content.msg_content;
			content.objId = "img_"+imageUrl.substring(imageUrl.lastIndexOf('/')+1, imageUrl.length).replace(/\./ig, "");
			$.hsChat.showPerMsgByID(content, $.hsChat.defaults.id != from, msg.messageCode, fromId, true);
		}
	};
	
	/*
	 * 取当前日期
	 */
	$.hsChat.getCurrDate = function() {
		var date = new Date();
		var hour = date.getHours();
		var minute = date.getMinutes();
		return ((hour<10)?("0"+hour):hour)+':'+((minute<10)?("0"+minute):minute);
	};
	
	/*
	 * 取当前日期
	 */
	$.hsChat.getYMDTime = function(days) {
		var date = new Date();
		if(days){
			date = new Date(date.getTime()+days*24*60*60*1000);
		}
		var Y = date.getFullYear();
		var M = (date.getMonth()+1 < 10 ? '0'+(date.getMonth()+1) : date.getMonth()+1);
		var D = (date.getDate() < 10 ? '0'+(date.getDate()) : date.getDate());
		return Y+"-"+M+"-"+D;
	};
	
	/*
	 * 取当前日期
	 */
	$.hsChat.getCurrTime = function() {
		var date = new Date();
		var Y = date.getFullYear();
		var M = (date.getMonth()+1 < 10 ? '0'+(date.getMonth()+1) : date.getMonth()+1);
		var D = (date.getDate() < 10 ? '0'+(date.getDate()) : date.getDate());
		var hour = date.getHours();
		var minute = date.getMinutes();
		var second = date.getSeconds();
		return Y+"-"+M+"-"+D+" "+((hour<10)?("0"+hour):hour)+':'+((minute<10)?("0"+minute):minute)+':'+((second<10)?("0"+second):second);
	};
	
	/*
	 * 取发送时间
	 */
	$.hsChat.getRecvDate = function(time) {
		var date = new Date(time*1);
		var Y = date.getFullYear();
		var M = (date.getMonth()+1 < 10 ? '0'+(date.getMonth()+1) : date.getMonth()+1);
		var D = (date.getDate() < 10 ? '0'+(date.getDate()) : date.getDate());
		var h = date.getHours();
		var m = date.getMinutes();
		var date_ = Y+"-"+M+"-"+D;
		if($.hsChat.getYMDTime() == date_){
			return ((h<10)?("0"+h):h)+':'+((m<10)?("0"+m):m);
		}else if($.hsChat.getYMDTime(-1) == date_){
			return "昨天";
		}else if($.hsChat.getYMDTime(-2) == date_){
			return "前天";
		}else{
			return date_;
		}
	};
	
	/*消费者聊天--获取当前窗口的div对象*/
	$.hsChat.getCurrentBoxObj = function(){
		return $('#chatBox_area').children('div[class="currentBox"]');
	};
	
	/*消费者聊天--获取当前窗口的客户ID*/
	$.hsChat.getCurrentCustId = function(){
		return $('#chatBox_area').children('div[class="currentBox"]').attr("id").replace("div_", "");
	};
	
	/*消费者聊天--企业图片*/
	$.hsChat.getCompanyIcon = function(){
		return comm.removeNull(comm.getCache("chatCache", "companyIcon"));
	};
	
	/*消费者聊天--获取非持卡人昵称*/
	$.hsChat.getNickerName = function(name){
		if(!name || name.length != 11){
			return comm.removeNull(name);
		}
		var mobileReg = /^0?1[1-9]\d{9}$/;
		if(mobileReg.test(name)){
			return name.substring(0, 3)+"****"+name.substring(7, 11);
		}
		return name;
	};
	
	/*解析消息内容*/
	$.hsChat.getRealContent = function(objson){
		objson = objson.replace(/[\r\n]/g, "\\r\\n");
		try{
			return eval("("+objson+")").msg_content;
		}catch(e){
			return "";
		}
	};
	
	/**
	* 播放提示音
	*/
	$.hsChat.playMedia = function(){
		var href = window.location.href;
		var mp3Url = href.substring(0, href.lastIndexOf('/') + 1)+"resources/media/tada.mp3";
		$("#playMedia").html("<embed src='"+mp3Url+"' width=0 height=0 loop=false>");
	};
	
	$.hsChat.connect();

	return $.hsChat.conn;
});	
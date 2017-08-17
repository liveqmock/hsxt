define(["commonSrc/frame/chat/strophe",
	"commSrc/frame/chat/companyChat"],function(strophe, companyChat){

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
	
	$.hsChat.totalMsgCount = 0;//未读消息总数
	$.hsChat.lastMsgTime = "";//最后消息发送或者接收时间
	$.hsChat.lastRecMsgFlag = false;//是否已经提示过离线消息
	$.hsChat.lastRecMsgTime = new Date().getTime();//最后接收消息时间
	
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
	
	//初始化事件 
	$.hsChat.initEvent = function(){
		
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
		
		//发送消息事件
		$('#chat_pop_msg_box_btn').click(function(){
			var content = $.trim($('#companyChatBox').find('.tool_talkinput_textarea').html());
			if (!content){
				return;
			}
//			content = $('<p>'+$.hsChat.Replace_Code(content)+'</p>').text();
			content = $.hsChat.Replace_Code(content);
			$.hsChat.sendMessage(content, false, "00");
        });
		
		$(document).keydown(function(e){
			if(e.ctrlKey && e.keyCode == 13){
				$('#chat_pop_msg_box_btn').click();
			}
		});
	};
	
	/**消息发送*/
	$.hsChat.sendMessage = function(content, type, msgCode){
		var obj = {};
		var ptettr = null;
		var dateTimeStr = $.hsChat.getTimeStr($.hsChat.getCurrTime(), $.hsChat.lastMsgTime);
		$.hsChat.lastMsgTime = $.hsChat.getCurrTime();
		$.hsChat.appendTime(dateTimeStr);
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
			obj.msg_vshopId = "";
			var image = {};
			image.msg_content = content.tfsServerUrl+content.bigImgUrl;
			image.msg_imageNailsUrl = content.tfsServerUrl+content.smallImgUrl;
			image.bigImageWidth = content.bigImageWidth;
			image.bigImageHeight = content.bigImageHeight;
			image.msg_imageNails_width = content.smallImageWidth;
			image.msg_imageNails_height = content.smallImageHeight;
			image.objId = "img_"+content.smallImgUrl.replace(/\./ig, "");
			$.hsChat.showMsgByID(image, false, msgCode);
			ptettr = JSON.stringify(obj);
		}else{
			content = $.trim(comm.valueReplace($.hsChat.clearSpecWord(content)));
			obj.sub_msg_code = "10101";
			obj.msg_type = "2";
			obj.msg_code = "00";
			obj.msg_content = "msgContent";
			obj.msg_icon = "";
			obj.msg_note = comm.removeNull($.hsChat.defaults.operName);
			obj.msg_vshopId = "";
			$.hsChat.showMsgByID(content, false, msgCode);
			ptettr = JSON.stringify(obj).replace("msgContent", content);
		}
		var reply = $msg({to: "w_e_"+$.hsChat.toUserId+'@im.gy.com', from:  $.hsChat.defaults.uid , type: 'chat'}).cnode(Strophe.xmlElement('body', '' ,ptettr));
		$.hsChat.conn.send(reply.tree());
		$('#companyChatBox').find('.tool_talkinput_textarea').text('');
		$.hsChat.dealSendMessage(content, $.hsChat.toUserId, msgCode);
	}
	
	//显示消息 type:true 接受到的消息，false自己发送的消息
	$.hsChat.showMsgByID = function(msg, type, msgCode){
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
		
		$.hsChat.msgBoxScroll();
	};
	
	//滚动显示消息
	$.hsChat.msgBoxScroll = function() {
		var scroll = false;
		if (!scroll){
			scroll = true;
			$('#companyChatBox .talk_history_company').animate({scrollTop: $('#companyChatBox .talk_history_company').scrollTop()+1000 },0,function(){
				scroll = false;
			}); 
		} 
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
		var nickName = "";
		var flag = true;
		var talk_li = null;
		var pos = 0;
		
		if (elems.length > 0) {
			var body = elems[0];
			var msgJson = Strophe.getText(body);
			var newstr=msgJson.replace(/&quot;/g,"'").replace(/\n/ig, " ");
			var jsonobj=eval('('+newstr+')');
			var content = jsonobj.msg_content;
			nickName = jsonobj.msg_note;
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
			if(jsonobj.msg_code == 10){
			
			}
			//截取WEB端移动端公众号
			var formUserId = from.split("@")[0].replace("w_e_", "");
			if($.hsChat.toUserId == formUserId && !$("#companyChatBox").hasClass("none")){//是当前窗口
				if(!isOffLineMessage){
					var dateTimeStr = $.hsChat.getTimeStr($.hsChat.getCurrTime(), $.hsChat.lastMsgTime);
					$.hsChat.lastMsgTime = $.hsChat.getCurrTime();
					$.hsChat.appendTime(dateTimeStr);
					if(jsonobj.msg_code == "00"){
						$.hsChat.showMsgByID(content, true, jsonobj.msg_code);
						$.hsChat.dealRecvMessage(formUserId, content, true, jsonobj.msg_code, msgid);
					}else{
						jsonobj.objId = "img_"+msgid;
						$.hsChat.showMsgByID(jsonobj, true, jsonobj.msg_code);
						$.hsChat.dealRecvMessage(formUserId, content, true, jsonobj.msg_code, msgid);
					}
				}
			}else{//非当前窗口
				if(jsonobj.msg_code == "00"){
					$.hsChat.dealRecvMessage(formUserId, content, false, jsonobj.msg_code, msgid);
				}else{
					jsonobj.objId = "img_"+msgid;
					$.hsChat.dealRecvMessage(formUserId, content, false, jsonobj.msg_code, msgid);
				}
			}
		}
		return true;
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
	
	/**处理接收消息记录*/
	$.hsChat.dealRecvMessage = function(formUserId, content, isCurrent, msgCode, msgid){
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
					$.hsChat.addMessageCount();
					tempList[0].msgCount = (comm.removeNull(user.msgCount)==""?0:user.msgCount)+1;
				}
			}else{
				tempList.push(user);
			}
		});
		comm.delCache("chatCache", "userList");
		comm.setCache("chatCache", "userList", tempList);
		$.hsChat.search(tempList);
	}
	
	/**处理发送消息记录*/
	$.hsChat.dealSendMessage= function(content, toUserId, msgCode){
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
		$.hsChat.search(tempList);
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
		$.hsChat.conn = new Strophe.Connection($.hsChat.defaults.xmpp);
//		$.hsChat.conn = new Strophe.Connection($.hsChat.defaults.xmpp, {protocol: $.hsChat.defaults.xmppProtocol});
		//连接参数 用户 ，密码(id+',1,'+tookenkey)
		
		//$.hsChat.conn.rawInput = function (data) { console.log('RECV: ' + data); }; 
		//$.hsChat.conn.rawOutput = function (data) { console.log('SEND: ' + data); };
		
		$.hsChat.conn.connect($.hsChat.defaults.uid ,$.hsChat.defaults.mid, onConnect || function(status){
			if (status == Strophe.Status.CONNECTED) {//连接成功
				$.hsChat.conn.addHandler(onMessage, null, 'message', null, null, null);
				$.hsChat.conn.send($pres().tree()); 
			}else if(status== Strophe.Status.DISCONNECTED){//连接断开，重新连接
				$.hsChat.conn.connect($.hsChat.defaults.uid, $.hsChat.defaults.mid, arguments.callee);
			}else if (status == Strophe.Status.CONNFAIL || status == Strophe.Status.AUTHFAIL){
				if(isClickChat == 1){
					alert("连接消息中心失败！");
				}
			}
		}); 
	}
	
	//查找用户
	$.hsChat.findUserById = function(userList, usid, tsid){
		for(var key in userList){
			var user = userList[key];
			if(user.operCustId == usid || user.operCustId == tsid){
				comm.arrayRemove(userList, key);
				return user;
			}
		}
		return null;
	}
	
	//加载搜索结果
	$.hsChat.search = function(userList){
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
		$.hsChat.bindChat();
	}
	
	//企业通讯录聊天窗口操作事件
	$.hsChat.bindChat = function(){
		$('#qytxl_ul').find('li').click(function(){
			var user = {};
			user.realName = $(this).find('.name').text();
			user.userImg = $(this).find('dt img').attr('src');
			user.userName = $(this).attr('data-id').split("_")[1];
			user.userCustId = $(this).attr('data-id').split("_")[0];
			companyChat.showChatBox(user);
			var unReadMsgCount = comm.removeNull($(this).find('.hide_count').text());
			$.hsChat.showHistory(user, unReadMsgCount, $(this));
			var userList = comm.getCache("chatCache", "userList");
			$.each(userList, function(key, user_){
				if(user_.operCustId == user.userCustId){
					user_.msgCount = 0;
				}
			});
			$.hsChat.subMessageCount(unReadMsgCount);
			$(this).find(".count").hide();
		});
	}
	
	//企业通讯录聊天窗口操作事件（搜索）
	$.hsChat.chatClick = function(custId){
		var liObj = $("#"+"li_"+custId);
		if(!liObj){
			return;
		}
		var user = {};
		user.realName = liObj.find('.name').text();
		user.userImg = liObj.find('dt img').attr('src');
		user.userName = liObj.attr('data-id').split("_")[1];
		user.userCustId = liObj.attr('data-id').split("_")[0];
		companyChat.showChatBox(user);
		var unReadMsgCount = comm.removeNull($(this).find('.hide_count').text());
		$.hsChat.showHistory(user, unReadMsgCount, liObj);
		var userList = comm.getCache("chatCache", "userList");
		$.each(userList, function(key, user_){
			if(user_.operCustId == user.userCustId){
				user_.msgCount = 0;
			}
		});
		$.hsChat.subMessageCount(unReadMsgCount);
		$(liObj).find(".count").hide();
	}
	
	//企业级聊天--增加未读消息数量
	$.hsChat.addMessageCount = function(){
		$.hsChat.totalMsgCount++;
		if($.hsChat.totalMsgCount > 0){
			$("#unread_msg_count").html("企业通讯录("+($.hsChat.totalMsgCount>99?"99+":$.hsChat.totalMsgCount)+")");
		}else{
			$("#unread_msg_count").html("企业通讯录");
		}
	};
	
	//企业级聊天--减少未读消息数量
	$.hsChat.subMessageCount = function(msgCount){
		$.hsChat.totalMsgCount = $.hsChat.totalMsgCount-(comm.removeNull(msgCount) == ""?0:msgCount);
		if($.hsChat.totalMsgCount > 0){
			$("#unread_msg_count").html("企业通讯录("+($.hsChat.totalMsgCount>99?"99+":$.hsChat.totalMsgCount)+")");
		}else{
			$("#unread_msg_count").html("企业通讯录");
		}
	};
	
	//查找聊天记录
	$.hsChat.showHistory = function(user, unReadMsgCount, liObj){
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
						$.hsChat.appendTime(dateTimeStr);
						$.hsChat.onSMessage(msg);
					});
					if(response.rows && response.rows.length > 0){
						$.hsChat.lastMsgTime = response.rows[response.rows.length-1].dateTimeStr;
						$.hsChat.msgBoxScroll();
					}else{
						$.hsChat.lastMsgTime = $.hsChat.getCurrTime();
					}
					liObj.find(".count").hide();
					liObj.find(".count").html("");
					liObj.find(".hide_count").html("");
				}else{
					$.hsChat.lastMsgTime = $.hsChat.getCurrTime();
					comm.error_alert("获取消息失败...");
				}
			},
			error: function(){
				$.hsChat.lastMsgTime = $.hsChat.getCurrTime();
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
	$.hsChat.appendTime = function(dateTimeStr){
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
	
	//查询最近联系人
	$.hsChat.getRecentContactsMsgList = function(ifOperator, pageNum, pageSize, callBack){
		var params = {};
		params.channelType = "1";
		params.custId = comm.getRequestParams().custId;
		params.entResNo = comm.getRequestParams().pointNo;
		params.loginToken = comm.getRequestParams().token;
		params.data = {
			"toUid":params.custId,
			"ifOperator":ifOperator,
			"pageNum":pageNum,
			"pageSize":pageSize,
			"targetUserType":"e"
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
					console.log("获取最近联系人失败...");
					callBack(null);
				}
			},
			error: function(){
				console.log("获取最近联系人失败...");
				callBack(null);
			}
		});
	}
	
	
	/**
     * 接收消息回调方法
     */
	 $.hsChat.onSMessage = function(msg) {
		var to = msg.toUid.substring(msg.toUid.lastIndexOf('_')+1, msg.toUid.length);
		var from = msg.fromUid.substring(msg.fromUid.lastIndexOf('_')+1, msg.fromUid.length);
		var content = msg.message;
		content = content.replace(/&lt;/g,"<");
		content = content.replace(/&gt;/g,">");
		content = content.replace(/&amp;/g, "&");
		content = content.replace(/<\/div>*|<br><\/div>*/gi, "<br>");
		content = content.replace(/<div>*/gi,"");
		content = content.replace(/&quot;/g,"'").replace(/\n/ig, " ");
		//文本消息
		if(msg.messageCode == "00" ){
			content = $.hsChat.getRealContent(content);
			$.hsChat.showMsgByID(content, from != $.hsChat.defaults.id, msg.messageCode);
		}
		//图片消息
		if(msg.messageCode == "10"){
			content = eval("("+content+")");
			var imageUrl = content.msg_content;
			content.objId = "img_"+imageUrl.substring(imageUrl.lastIndexOf('/')+1, imageUrl.length).replace(/\./ig, "");
			$.hsChat.showMsgByID(content, from != $.hsChat.defaults.id, msg.messageCode);
		}
	}
	
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
    
	//层级
	$("#companyChatBox").click(function(){
		$(this).css({"z-index":99});
		$("#xtxx_box").css({"z-index":98});
		$(".hs_newslist").css({"z-index":98});
	});
	
	$(".hs_newslist").click(function(){
		$(this).css({"z-index":99});
		$("#xtxx_box").css({"z-index":98});
		$("#companyChatBox").css({"z-index":98});
	});
	
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
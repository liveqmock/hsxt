define(["commSrc/frame/chat/xtxx"], function(xtxx){
	return pushService = {
		showPage : function(){
			pushService.msgId = 100;//测试用
			pushService.isTest = false;//测试用
			pushService.interval = 30;//轮询时间间隔，单位s
			pushService.localStorageKeyName = "comPushMsg";//localStorage主键名称
			pushService.orderMaxCount = 500;//订单消息最大存储数量
			pushService.systemMaxCount = 500;//系统消息最大存储数量
			pushService.serviceMaxCount = 500;//服务消息最大存储数量
			pushService.systemOpenList = {};//打开的消息列表
			pushService.userId = comm.getRequestParams().custId;//操作员客户号
			pushService.entResNo = comm.getRequestParams().entResNo;//企业互生号
			pushService.getMessage();
		},
		/**
		* 构建PushMsg对象
		*/
		getPushMsg : function(){
			var pushMsg = {};
			pushMsg.custId = pushService.userId;//当前登陆客户号
			pushMsg.systemTotal = 0;//系统消息总数量
			pushMsg.orderTotal = 0;//订单消息总数量
			pushMsg.serviceTotal = 0;//服务消息总数量
			pushMsg.orderCount = 0;//订单消息未读数量
			pushMsg.systemCount = 0;//系统消息未读数量
			pushMsg.serviceCount = 0;//服务消息未读数量
			pushMsg.orderList = {};//订单消息列表
			pushMsg.systemList = {};//系统消息列表
			pushMsg.serviceList = {};//服务消息列表
			pushService.pushMsg = pushMsg;
			pushService.putMessage();
		},
		/**
		* 初始化未读消息--需要从cookie里取
		*/
		getMessage : function(){
			var msgString = localStorage.getItem(pushService.localStorageKeyName);
			if(!msgString){
				pushService.getPushMsg();
			}else{
				var pushMsg = eval("("+msgString+")");
				if(pushMsg.custId == pushService.userId){
					if(pushMsg.systemCount > 0){
						$("#unread_system_msg_count").show();
						$("#unread_system_msg_count").html(pushMsg.systemCount>99?"99+":pushMsg.systemCount);
					}
					if(pushMsg.serviceCount > 0){
						$("#unread_service_msg_count").show();
						$("#unread_service_msg_count").html(pushMsg.serviceCount>99?"99+":pushMsg.serviceCount);
					}
					if(pushMsg.orderCount > 0){
						$("#unread_order_msg_count").show();
						$("#unread_order_msg_count").html(pushMsg.orderCount>99?"99+":pushMsg.orderCount);
					}
					pushService.pushMsg = pushMsg;
					$.each(pushMsg.systemList, function(key, msg){
						li = $("#system_example").html();
						li = li.replace("li-id", "system_li_"+msg.msgId);
						li = li.replace("liClassName", msg.isRead?"done":"");
						li = li.replace("msgIcon", comm.getNameByEnumId(msg.msgType, comm.lang("common").msgIcon));
						li = li.replace("subject", msg.subject);
						li = li.replace("msgContent", pushService.getMsgContent(msg.content));
						li = li.replace("msgTime", pushService.getStringTime(msg.time));
						$("#ul_system").prepend(li);
					});
					$.each(pushMsg.serviceList, function(key, msg){
						li = $("#service_example").html();
						li = li.replace("li-id", "service_li_"+msg.msgId);
						li = li.replace("liClassName", msg.isRead?"done":"");
						li = li.replace("ddClassName", "state");
						li = li.replace("subject", msg.subject);
						li = li.replace("msgContent", msg.content);
						li = li.replace("msgTime", pushService.getStringTime(msg.time));
						$("#ul_service").prepend(li);
					});
					$.each(pushMsg.orderList, function(key, msg){
						li = $("#order_example").html();
						li = li.replace("li-id", "order_li_"+msg.msgId);
						li = li.replace("liClassName", msg.isRead?"done":"");
						li = li.replace("ddClassName", "state");
						li = li.replace("subject", msg.subject);
						li = li.replace("msgContent", msg.content);
						li = li.replace("msgTime", pushService.getStringTime(msg.time));
						$("#ul_order").prepend(li);
					});
				}else{
					pushService.getPushMsg();
				}
			}
			pushService.login();
			pushService.bindLiClick();
		},
		/**
		* 初始化未读消息--需要从cookie里取
		*/
		putMessage : function(){
			localStorage.setItem(pushService.localStorageKeyName, comm.removeNull(JSON.stringify(pushService.pushMsg)));
		},
		/**
		* 鉴权
		* @param params 参数信息
		*/
		login : function(){
			if(pushService.isTest){
				//启动轮询
				window.setInterval(function(){
					pushService.msgId = pushService.msgId+1;
					var msg_content = "{\"msgStyle\":\"101\",\"msgid\":\"110120160505164650000000\",\"pageUrl\":\"http://192.168.229.27:8080/hsi-fs-server/fs/download/F00AgKUZq1XR1TLbBTLRb1T\",\"realPicUrl\":\"\",\"smallPicUrl\":\"\",\"summary\":\"推送消息\"}";
					var msg1 = {msgId:pushService.msgId, msgType:2, msg_content:'订单25815828558855交易成功，买家已完成付款', msg_subject:'【举报'+pushService.msgId+'】', time:'20160421'};
					var msg2 = {msgId:pushService.msgId, msgType:2, msg_content:'订单25815828558855交易成功，买家已完成付款', msg_subject:'【交易成功'+pushService.msgId+'】', time:'20160421'};
					var msg3 = {msgId:(pushService.msgId+100), msgType:1003, msg_content:msg_content, msg_subject:'服务公司消息', time:'20160421'};
					var msg4 = {msgId:(pushService.msgId+200), msgType:1004, msg_content:msg_content, msg_subject:'管理公司消息', time:'20160421'};
					var msg5 = {msgId:(pushService.msgId+300), msgType:1002, msg_content:msg_content, msg_subject:'地区平台消息', time:'20160421'};
					var msg6 = {msgId:(pushService.msgId+400), msgType:2531, msg_content:"您已成功开通外卖服务.", msg_subject:'开通外卖服务', time:'20160421'};
					var msg7 = {msgId:(pushService.msgId+500), msgType:2532, msg_content:"每月扣取月租费20元.", msg_subject:'每月扣取月租费(测试长度)', time:'20160421'};
					var data = {serveMsgList:[msg1], entOrderMsgList:[msg2], entHsMsgList:[msg3, msg4, msg5, msg6, msg7]};
					pushService.dealMessage(data);
				}, pushService.interval*1000);
			}else{
				var params = {};
				params.userId = pushService.userId;
				params.loginToken = comm.getRequestParams().token;
				params.entResNo = pushService.entResNo;
				pushService.ajaxRequest("login/login", params, function(res){
					if(res){
						//拉取离线
						pushService.getOfflinePushMsgList(res);
						//启动轮询
						window.setInterval(function(){
							pushService.getOnlinePushMsgList();
						}, pushService.interval*1000);
					}
				});
			}
		},
		/**
		* 拉取离线消息
		*/
		getOfflinePushMsgList : function(data){
			if(data.data){
				pushService.dealMessage(data.data);
			}
		},
		/**
		* 拉取在线消息
		*/
		getOnlinePushMsgList : function(){
			var params = {userId : pushService.userId, entResNo : pushService.entResNo, token : comm.getRequestParams().token};
			pushService.ajaxRequest("login/getOnlinePushMsg", params, function(data){
				if(data && data.data){
					pushService.dealMessage(data.data);
				}
			});
		},
		/**
		* 处理消息
		* @param data 消息
		*/
		dealMessage : function(data){
			if((data.entHsMsgList && data.entHsMsgList.length>0) || (data.serveMsgList && data.serveMsgList.length>0) || (data.entOrderMsgList && data.entOrderMsgList.length>0)){
				pushService.playMedia();
			}
			var li;
			if(data.entHsMsgList){
				var entHsMsgList = data.entHsMsgList.reverse();
				$.each(entHsMsgList, function(key, msg){
					//如果本次拉取消息数量大于最大存储消息数量，则后面的消息不处理
					if(key == (pushService.serviceMaxCount-1)){
						return false;
					}
					if(!pushService.pushMsg.systemList[msg.msgId]){
						li = $("#system_example").html();
						li = li.replace("li-id", "system_li_"+msg.msgId);
						li = li.replace("liClassName", "");
						li = li.replace("msgIcon", comm.getNameByEnumId(msg.msgType, comm.lang("common").msgIcon));
						li = li.replace("subject", msg.msg_subject);
						li = li.replace("msgContent", pushService.getMsgContent(msg.msg_content));
						li = li.replace("msgTime", pushService.getStringTime(msg.time));
						$("#ul_system").prepend(li);
						pushService.pushMsg.systemTotal+=1;
						pushService.pushMsg.systemList[msg.msgId] = {msgId:msg.msgId, msgType:msg.msgType, content:msg.msg_content, subject:msg.msg_subject, time:msg.time, isRead:false};
						pushService.addMessageCount("system");
					}
				});
			}
			if(data.serveMsgList){
				var serveMsgList = data.serveMsgList.reverse();
				$.each(serveMsgList, function(key, msg){
					//如果本次拉取消息数量大于最大存储消息数量，则后面的消息不处理
					if(key == (pushService.serviceMaxCount-1)){
						return false;
					}
					if(!pushService.pushMsg.serviceList[msg.msgId]){
						li = $("#service_example").html();
						li = li.replace("li-id", "service_li_"+msg.msgId);
						li = li.replace("liClassName", "");
						li = li.replace("ddClassName", "state");
						li = li.replace("subject", msg.msg_subject);
						li = li.replace("msgContent", msg.msg_content);
						li = li.replace("msgTime", pushService.getStringTime(msg.time));
						$("#ul_service").prepend(li);
						pushService.pushMsg.serviceTotal+=1;
						pushService.pushMsg.serviceList[msg.msgId] = {msgId:msg.msgId, msgType:msg.msgType, content:msg.msg_content, subject:msg.msg_subject, time:msg.time, isRead:false};
						pushService.addMessageCount("service");
					}
				});
			}
			if(data.entOrderMsgList){
				var entOrderMsgList = data.entOrderMsgList.reverse();
				$.each(entOrderMsgList, function(key, msg){
					//如果本次拉取消息数量大于最大存储消息数量，则后面的消息不处理
					if(key == (pushService.orderMaxCount-1)){
						return false;
					}
					if(!pushService.pushMsg.orderList[msg.msgId]){
						li = $("#order_example").html();
						li = li.replace("li-id", "order_li_"+msg.msgId);
						li = li.replace("liClassName", "");
						li = li.replace("ddClassName", "state");
						li = li.replace("subject", msg.msg_subject);
						li = li.replace("msgContent", msg.msg_content);
						li = li.replace("msgTime", pushService.getStringTime(msg.time));
						$("#ul_order").prepend(li);
						pushService.pushMsg.orderTotal+=1;
						pushService.pushMsg.orderList[msg.msgId] = {msgId:msg.msgId, msgType:msg.msgType, content:msg.msg_content, subject:msg.msg_subject, time:msg.time, isRead:false};
						pushService.addMessageCount("order");
					}
				});
			}
			pushService.bindLiClick();
		},
		/**
		* 公共请求
		* @param urlStr 请求地址
		* @param params 参数信息
		* @param callBack 回调函数
		*/
		ajaxRequest : function(urlStr, params, callBack){
			comm.Request({url:urlStr, domain:'xmppTs'},{
				data: params,
				type:'POST',
				dataType:"json",
				success:function(response){
					//非空验证
					if (response.retCode == 200) {
						callBack(response);
					} else {
						callBack(null);
					}
				},
				error: function(){
					callBack(null);
				}
			});
		},
		/**
		* 增加未读消息数量
		* @param urlStr 
		*/
		addMessageCount : function(msgType){
			if(msgType == "system"){
				pushService.pushMsg.systemCount = pushService.pushMsg.systemCount+1;
				//判断消息是否已经超过最大条数，如果超过则剔除距离现在最久的一条消息
				if(pushService.pushMsg.systemTotal == (pushService.systemMaxCount+1)){
					pushService.pushMsg.systemTotal = pushService.systemMaxCount;
					var lastLi = $('#ul_system').find('li:last');
					var msgId = pushService.getMsgId(lastLi, "system");
					if(!pushService.pushMsg.systemList[msgId].isRead){
						pushService.pushMsg.systemCount = pushService.pushMsg.systemCount-1;
					}
					lastLi.remove();
					delete pushService.pushMsg.systemList[msgId];
				}
				if(pushService.pushMsg.systemCount == 0){
					$("#unread_system_msg_count").hide();
				}else{
					$("#unread_system_msg_count").show();
					$("#unread_system_msg_count").html(pushService.pushMsg.systemCount>99?"99+":pushService.pushMsg.systemCount);
				}
				
			}else if(msgType == "service"){
				pushService.pushMsg.serviceCount = pushService.pushMsg.serviceCount+1;
				//判断消息是否已经超过最大条数，如果超过则剔除距离现在最久的一条消息
				if(pushService.pushMsg.serviceTotal == (pushService.serviceMaxCount+1)){
					pushService.pushMsg.serviceTotal = pushService.serviceMaxCount;
					var lastLi = $('#ul_service').find('li:last');
					var msgId = pushService.getMsgId(lastLi, "service");
					if(!pushService.pushMsg.serviceList[msgId].isRead){
						pushService.pushMsg.serviceCount = pushService.pushMsg.serviceCount-1;
					}
					lastLi.remove();
					delete pushService.pushMsg.serviceList[msgId];
				}
				if(pushService.pushMsg.serviceCount == 0){
					$("#unread_service_msg_count").hide();
				}else{
					$("#unread_service_msg_count").show();
					$("#unread_service_msg_count").html(pushService.pushMsg.serviceCount>99?"99+":pushService.pushMsg.serviceCount);
				}
			}else if(msgType == "order"){
				pushService.pushMsg.orderCount = pushService.pushMsg.orderCount+1;
				//判断消息是否已经超过最大条数，如果超过则剔除距离现在最久的一条消息
				if(pushService.pushMsg.orderTotal == (pushService.orderMaxCount+1)){
					pushService.pushMsg.orderTotal = pushService.orderMaxCount;
					var lastLi = $('#ul_order').find('li:last');
					var msgId = pushService.getMsgId(lastLi, "order");
					if(!pushService.pushMsg.orderList[msgId].isRead){
						pushService.pushMsg.orderCount = pushService.pushMsg.orderCount-1;
					}
					lastLi.remove();
					delete pushService.pushMsg.orderList[msgId];
				}
				if(pushService.pushMsg.orderCount == 0){
					$("#unread_order_msg_count").hide();
				}else{
					$("#unread_order_msg_count").show();
					$("#unread_order_msg_count").html(pushService.pushMsg.orderCount>99?"99+":pushService.pushMsg.orderCount);
				}
			}
			pushService.putMessage();
		},
		/**
		* 减少未读消息数量
		* @param urlStr 
		*/
		subMessageCount : function(msgType, msgId){
			if(msgType == "system"){
				pushService.pushMsg.systemCount = pushService.pushMsg.systemCount-1;
				pushService.pushMsg.systemList[msgId].isRead = true;
				if(pushService.pushMsg.systemCount == 0){
					$("#unread_system_msg_count").hide();
				}else{
					$("#unread_system_msg_count").show();
					$("#unread_system_msg_count").html(pushService.pushMsg.systemCount>99?"99+":pushService.pushMsg.systemCount);
				}
			}else if(msgType == "service"){
				pushService.pushMsg.serviceCount = pushService.pushMsg.serviceCount-1;
				pushService.pushMsg.serviceList[msgId].isRead = true;
				if(pushService.pushMsg.serviceCount == 0){
					$("#unread_service_msg_count").hide();
				}else{
					$("#unread_service_msg_count").show();
					$("#unread_service_msg_count").html(pushService.pushMsg.serviceCount>99?"99+":pushService.pushMsg.serviceCount);
				}
			}else if(msgType == "order"){
				pushService.pushMsg.orderCount = pushService.pushMsg.orderCount-1;
				pushService.pushMsg.orderList[msgId].isRead = true;
				if(pushService.pushMsg.orderCount == 0){
					$("#unread_order_msg_count").hide();
				}else{
					$("#unread_order_msg_count").show();
					$("#unread_order_msg_count").html(pushService.pushMsg.orderCount>99?"99+":pushService.pushMsg.orderCount);
				}
			}
			pushService.putMessage();
		},
		/**
		* 绑定点击事件
		*/
		bindLiClick : function(){
			$('#ul_order').find('li').unbind("click");
			$('#ul_system').find('li').unbind("click");
			$('#ul_service').find('li').unbind("click");
			$('#ul_system').find('li').click(function(){
				var msgId = pushService.getMsgId($(this), "system");
				var delId = null;
				var msg = pushService.pushMsg.systemList[msgId];
				//初始化
				if (!$("#msg-pv-tab").html() || $("#msg-pv-tab").html() == "") {
					pushService.systemOpenList = {};
					pushService.systemOpenList["msgIds"] = [msgId];
					pushService.systemOpenList[msgId] = pushService.pushMsg.systemList[msgId];
					xtxx.showXtxxBox(msg, true, delId);
				} else {//已经打开
					if(!pushService.systemOpenList[msgId]){
						pushService.systemOpenList["msgIds"].push(msgId);
						pushService.systemOpenList[msgId] = pushService.pushMsg.systemList[msgId];
						if(pushService.systemOpenList["msgIds"].length == 11){
							 delId = pushService.systemOpenList["msgIds"][0];
							 pushService.systemOpenList["msgIds"].splice(0, 1);
							 delete pushService.systemOpenList[delId];
						}
					}
					xtxx.showXtxxBox(msg, false, delId);
				}
				$.hsChat.showFocusBox($('#xtxx_box'));
				if(!$(this).hasClass("done")){
					$(this).addClass("done");
					pushService.subMessageCount("system", msgId, "system");
				}
			});
			$('#ul_order').find('li').click(function(){
				if(!$(this).hasClass("done")){
					$(this).addClass("done");
					pushService.subMessageCount("order", pushService.getMsgId($(this), "order"));
				}
			});
			$('#ul_service').find('li').click(function(){
				if(!$(this).hasClass("done")){
					$(this).addClass("done");
					pushService.subMessageCount("service", pushService.getMsgId($(this), "service"));
				}
			});
		},	
		/**
		* 获取MsgId
		* @param obj Li对象
		*/
		getMsgId : function(obj, msgType){
			return $(obj).attr("id").replace(msgType+"_li_", "");
		},
		/**
		* 格式化消息时间
		* @param time 时间 
		*/
		getStringTime : function(time){
			if(time){
				var str = time.substring(0, 4)+"-"+time.substring(4, 6)+"-"+time.substring(6, 8);
				if(pushService.getYMDTime() == str){
					return time.substring(8, 10)+":"+time.substring(10, 12);
				}else if(pushService.getYMDTime(-1) == str){
					return "昨天";
				}else if(pushService.getYMDTime(-2) == str){
					return "前天";
				}else{
					return str;
				}
			}
			return comm.removeNull(time);
		},
		/**
		 * 取当前日期
		 */
		getYMDTime : function(days) {
			var date = new Date();
			if(days){
				date = new Date(date.getTime()+days*24*60*60*1000);
			}
			var Y = date.getFullYear();
			var M = (date.getMonth()+1 < 10 ? '0'+(date.getMonth()+1) : date.getMonth()+1);
			var D = (date.getDate() < 10 ? '0'+(date.getDate()) : date.getDate());
			return Y+"-"+M+"-"+D;
		},
		/**
		* 格式化消息时间
		* @param s 时间 
		*/
		getFullTime : function(s){
			if(s){
				return s.substring(0, 4)+"-"+s.substring(4, 6)+"-"+s.substring(6, 8)+" "+s.substring(8, 10)+":"+s.substring(10, 12)+":"+s.substring(12, 14);
			}
			return "";
		},
		/**
		* 播放提示音
		*/
		playMedia : function(){
			var href = window.location.href;
			var mp3Url = href.substring(0, href.lastIndexOf('/') + 1)+"resources/media/tada.mp3";
			$("#playMedia").html("<embed src='"+mp3Url+"' width=0 height=0 loop=false>");
		},
		/**
		* 获取消息内容
		* @param content 消息内容
		*/
		getMsgContent : function(content){
			try{
				return eval("("+content+")").summary;
			}catch(e){
				return content;
			}
		}
	}	
});
define([
        "text!commTpl/frame/rightBar.html",
		"hsec_tablePointSrc/strophe/strophe",
		"hsec_tablePointDat/strophe/stropheDat",
		"commSrc/frame/HuDong/orderXmpp",
		"commSrc/frame/HuDong/stationXmpp",
		"commSrc/frame/HuDong/consultationXmpp",
		"commSrc/frame/HuDong/CMLink",
		"commSrc/frame/HuDong/publicTool",
		"localStorage"
		],function(rightBarTpl,stropheJs,stropheAjax,orderXmppJs,stationXmppJs,consultationXmppJs,CMLink,publicTool){   
	var BOSH_SERVICE = 'http://xmpp.prod.gyist.com:5280/xmpp-httpbind';
	 //登录互动的uid 
	 var uuid;
	 //var uuid = 'w_nc_18923729275@im.gy.com';
	 //登录互动密码
	 var mid;
	//系统默认头像url
	 var txUrl = "resources/img/defaultheadimg.png";
	 var logo = "resources/img/corporate_logo_01.png";
	 //登录企业信息
    var param = {};
    //im服务器虚拟主机名
    param["virtualName"] = '@im.gy.com';
    $('#huDongMain').draggable();
	$('#huDongMain2').draggable();
	var rightBar = {
		removeBar : function(){
			//移除rightBar组件自带的class

			var serviceItem = $(".serviceItem");
			var serviceItemClass = serviceItem.attr("class");
			var itemClassArr = new Array();
			itemClassArr = serviceItemClass.split(" ");
			for(var i = 0; i <= itemClassArr.length; i++){
				if(itemClassArr[i] != "serviceItem"){
					serviceItem.removeClass(itemClassArr[i]);
				}
			}
			serviceItem.children("h3").removeClass();
			serviceItem.children("h3").children("span").remove();

			serviceItem.children("h3").bind({
				mouseover:function(){$(this).removeClass();},
				click:function(){
					$(this).removeClass();
					removeDivClass();
				},
				focus:function(){
					$(this).removeClass();
					removeDivClass();
				}
			});

			function removeDivClass(){
				var serviceItemDivClass = serviceItem.children("div").attr("class");
				var itemDivClassArr = new Array();
				itemDivClassArr = serviceItemDivClass.split(" ");
				for(var i = 0; i <= itemDivClassArr.length; i++){
					if(itemDivClassArr[i] != "tabDiv_h"){
					serviceItem.children("div").removeClass(itemDivClassArr[i]);
					}
				}
			}
			
			removeDivClass();

			//end
			
			$("#huDongMain").on("click",function(){
				$(this).css({"z-index":Number($("#huDongMain2").css('z-index'))+1}); 
			})
			$("#huDongMain2").on("click",function(){
				$(this).css({"z-index":Number($("#huDongMain").css('z-index'))+1});
			})
			
			$(document).keydown(function(e){
				if(e.keyCode==13){
					if($("#goto_btn").length > 0){
						$("#goto_btn").click(); //处理事件
					}
				}
			});
		},
		init: function() {
			if(comm.getCache("scs","cache")==null){
				/*stropheAjax.loadCache(function(data){
					if(data.code>1){
						stropheAjax.i_alert(data.msg);
					}else{
						comm.setCache("scs","cache",data.cache);
						rightBar.userInit();
					}
				});*/
			}else{
				rightBar.userInit();
			}
		},
		startConnection : function() {
				//建立连接
				 comm.connection = new Strophe.Connection(BOSH_SERVICE);
				// comm.connection = new Strophe.Connection("http://192.168.1.129:5280/xmpp-httpbind" );
				//登录(admin0@im02.dev.gyist.com--用户uid，123--用户密码)
				 comm.connection.connect(uuid, mid,rightBar.onConnect);
		},
		onConnect : function(status){
			   var selfFn = arguments.callee;
				//连接成功
			   if (status == Strophe.Status.CONNECTED) {
				 //alert('连接成功');
					 comm.connection.addHandler(rightBar.onMessage, null, 'message', null, null, null);
					 comm.connection.send($pres().tree());
				} else if (status == Strophe.Status.DISCONNECTED) {
					//debugger
					//alert('连接断开');
					//连接断开，重新连接
					 comm.connection.connect(uuid, mid, selfFn);
				} else if (status == Strophe.Status.CONNECTING) {
					//alert('连接中');
				} else if (status == Strophe.Status.CONNFAIL || status == Strophe.Status.AUTHFAIL) {
					//alert('连接失败');
				}
		},
		userInit : function(){
			
			if($("#service").length < 1){
				return;
			}else{
				$( ".serviceItem" ).accordion();//申缩菜单效果			
				$('.listScroll').jScrollPane();//滚动条
				window.onbeforeunload=function (){
						$.cookie('huDongBool', 0);
				}
				autoHeight();
				$(window).resize(function(){
					autoHeight();
				})
				function autoHeight(){
					var huDongTopHeight = ($(window).height() - $("#huDongDivParents").height())/2;
					$("#huDongZhong").css("padding-top",huDongTopHeight);
					var huDongHeight = $(window).height() - 275;
					$(".serviceItem div").css("height",huDongHeight);
					var huDongHeight2 = $(window).height() - 165;
					$(".addressBook div").css("height",huDongHeight2);
				}
			}
	
		    $("#service").html(rightBarTpl);
		    $( ".serviceItem" ).accordion();//申缩菜单效果			
			$('.listScroll').jScrollPane();//滚动条
			rightBar.removeBar();
			
			//检查登录用户
			if(comm.getCache("scs","user")==null){
				/*stropheAjax.getLoginUser(function(data){
					comm.setCache("scs","user",data.user);
					rightBar.headMarket();
				});*/
			}else{
				rightBar.headMarket();
			} 
		},
		headMarket : function(){

			uuid = "w_e_"+comm.getCache("scs","user").resNo+"_"+comm.getCache("scs","user").loginId+"@im.gy.com/web_im";
			mid = $.cookie("scs_mid");
			BOSH_SERVICE=comm.getCache("scs","cache").bosh_service;
			param["uName"] = comm.getCache("scs","user").corpName;
			param["tfsUrl"] = comm.getCache('scs','cache').tfs_url;
			param["morenPic"] = txUrl;
			param["marketId"] = comm.getCache("scs","user").resNo+"_"+comm.getCache("scs","user").loginId;
			param["uuid"] =  "w_e_"+comm.getCache("scs","user").resNo+"_"+comm.getCache("scs","user").loginId+"@im.gy.com/web_im";
			param["headPic"] = logo;
			store.set("marketInfoMain",param);
			rightBar.startConnection();
			orderXmppJs.init();
			stationXmppJs.init();
			consultationXmppJs.init();
			CMLink.leftClickLink();
			publicTool.xiaoxitishi();
			rightBar.fuwuInfo();
		},
		fuwuInfo : function(){
			$("#right_name").html(comm.getCache("scs","user").corpName);
			
			var corpType=comm.getCache("scs","user").corpType;
			if(corpType=='M')corpType='管理公司';
			else if(corpType=='S')corpType='服务公司';
			else if(corpType=='T')corpType='托管企业';
			else if(corpType=='B')corpType='成员企业';
			else if(corpType=='P')corpType='个人';
			
			$("#right_type").html(corpType);
			$("#right_regDate").text(new Date(comm.getCache("scs","user").regDate).format());
			$("#right_payDate").text(comm.getCache("scs","user").payDate);
		},
		requestTo : function(request,to){
			try{
				if(request != null && request != ""){
					if(request[0].getAttribute('xmlns') == "gy:abnormal:offline"){
						var reId = Strophe.getText(request[0].getElementsByTagName('id')[0]);
						var id = Strophe.xmlElement('id','',reId);
						var sender = Strophe.xmlElement('sender','',to);
						var re = Strophe.xmlElement('receipt');
						re.setAttribute("xmlns","gy:abnormal:offline"); 
						re.appendChild(id);
						re.appendChild(sender);
						var reply = $msg({
						}).cnode(re);
					    comm.connection.send(reply.tree());
					}
				}
			}catch(e){}
		},
		onMessage: function(msg) {
			var msgid =null;
		 try{
			msgid = msg.getAttribute('id');
			var to = msg.getAttribute('to');
			var from = msg.getAttribute('from');
			var type = msg.getAttribute('type');
			var elems = msg.getElementsByTagName('body');
			var request = msg.getElementsByTagName('request');
			rightBar.requestTo(request,to);
			//用户名
			var name = from.substring(0, from.indexOf("@"));
			var jsonStr = null;
			if (type == "chat" && elems.length > 0) {
				var body = elems[0];
				var msgJson = Strophe.getText(body);
				var newstr4 = msgJson.replace(/&quot;/g, "\"").replace("\n"," ");
				var newstr5 = newstr4.replace(/&apos;/g, "'");
				try {
					jsonStr = eval('(' + newstr5 + ')');
				} catch (e) {
				}
			}else{
				try {
						jsonStr = eval('(' + elems[0].innerHTML + ')');
					if(jsonStr == null || jsonStr == "" || jsonStr == "undefined"){
						jsonStr = eval('(' + elems[0].text + ')');	
					}
				} catch (e) {
					
				}
			}
			var marketInfoMain = store.get("marketInfoMain");
			//咨询消息
			try{
				if(null != jsonStr.sub_msg_code && "" != jsonStr.sub_msg_code){
					jsonStr.sub_msg_code = jsonStr.sub_msg_code +"";
				}
			}catch(e){
				return true;
			}
			if ((jsonStr.msg_type == 2 && jsonStr.msg_code == 201) || (jsonStr.msg_type == 2 && (jsonStr.msg_code == 00 || jsonStr.msg_code == 10 || jsonStr.msg_code == 12))) {
				jsonStr.msg_content = publicTool.biaoqingchuli(jsonStr.msg_content);
				var fromName = name.split("_");
				var frName = fromName[fromName.length-1];
				if(fromName.length > 3){
					frName = fromName[fromName.length-2]+"_"+fromName[fromName.length-1];;
				}
				
				jsonStr["fromId"] = frName;
				jsonStr["fromTo"] = name;
				jsonStr["time"] = msgid;
				jsonStr["newStr"] = 1;
				if(jsonStr.msg_icon == null || jsonStr.msg_icon == ""){
					jsonStr["msg_icon"] = txUrl;
				}else if(jsonStr.msg_icon.indexOf("http://") < 0){
					jsonStr["msg_icon"] = marketInfoMain["tfsUrl"]+jsonStr.msg_icon;
				}else{
					jsonStr["msg_icon"] = jsonStr.msg_icon;
				}
				var consultationList = store.get("consultationList");
				if(consultationList != null && consultationList != "" && consultationList != undefined){
					var bool = 0;
					$.each(consultationList,function(k,v){
						if(v.fromId == frName && v.fromMarketId == marketInfoMain.marketId){
							$.each(v.content,function(k2,v2){
								v2.newStr = 0;
							})
							v.content.push(jsonStr);
							bool = 1;
							return false;
						}
					})
					if(bool != 1){
						var param = {};
						var contentList = new Array();
						contentList.push(jsonStr);
						param["fromId"] = frName;
						param["fromTo"] = name;
						param["fromMarketId"] = marketInfoMain.marketId;
						param["content"] = contentList;
						consultationList.push(param);
					}
					store.set("consultationList",consultationList);
				}else{
					var param = {};
					var contentList = new Array();
					var contentObj = new Array();
					contentList.push(jsonStr);
					param["fromId"] = frName;
					param["fromTo"] = name;
					param["fromMarketId"] = marketInfoMain.marketId;
					param["content"] = contentList;
					contentObj.push(param);
					store.set("consultationList",contentObj);
				}
				consultationXmppJs.newContent(jsonStr);
			}
			
			//推送消息，订单
			if(jsonStr.msg_type == 1 && jsonStr.msg_code == 202){
				publicTool.newStr(1);
				var paramOrder = {};
				paramOrder["orderTimeId"] = "time:"+msgid+",randomId:"+ publicTool.getRandom(9999999);
				paramOrder["time"] = msgid;
				paramOrder["newStr"] = 1;
				paramOrder["orderInfo"] = jsonStr;
				var orderList = store.get("orderList");
				if(orderList != null && orderList != "" && orderList != undefined){
					var bool = 0;
					$.each(orderList,function(k,v){
						if(v.codeId == jsonStr.sub_msg_code && v.fromMarketId == marketInfoMain.marketId){
							$.each(v.orderList,function(k2,v2){
								v2.newStr = 0;
							})
							bool = 1;
							v.orderList.push(paramOrder);
							return;
						}
					})
					if(bool != 1){
						var param = {};
						var contentList = new Array();
						contentList.push(paramOrder);
						param["codeId"] = jsonStr.sub_msg_code;
						param["fromMarketId"] = marketInfoMain.marketId;
						param["orderList"] = contentList;
						orderList.push(param);
					}
					store.set("orderList",orderList);
				}else{
					var orderCodeList = new Array();
					var orderList = new Array();
					orderList.push(paramOrder);
					var param = {};
					param["codeId"] = jsonStr.sub_msg_code;
					param["fromMarketId"] = marketInfoMain.marketId;
					param["orderList"] = orderList;
					orderCodeList.push(param);
					store.set("orderList",orderCodeList);
				}
				orderXmppJs.newOrder(paramOrder);
			}
			
			//推送消息，站内消息
			if (jsonStr.msg_type == 1 && jsonStr.msg_code == 203) {
				publicTool.newStr(2);
				var paramStation = {};
				paramStation["time"] = msgid;
				paramStation["oneNum"] = msgid + rightBar.getRandom(999999);
				paramStation["newStr"] = 1;
				paramStation["stationInfo"] = jsonStr;
				var stationList = store.get("stationList");
				if(stationList != null && stationList != "" && stationList != undefined){
					var bool = 0;
					$.each(stationList,function(k,v){
						if(v.codeId == jsonStr.sub_msg_code && v.fromMarketId == marketInfoMain.marketId){
							$.each(v.stationList,function(k2,v2){
								v2.newStr = 0;
							})
							bool = 1;
							v.stationList.push(paramStation);
							return;
						}
					})
					if(bool != 1){
						var station2List = new Array();
						station2List.push(paramStation);
						var param = {};
						param["codeId"] = jsonStr.sub_msg_code;
						param["fromMarketId"] = marketInfoMain.marketId;
						param["stationList"] = station2List;
						stationList.push(param);
					}
					store.set("stationList",stationList);
				}else{
					var stationCodeList = new Array();
					var stationList = new Array();
					stationList.push(paramStation);
					var param = {};
					param["codeId"] = jsonStr.sub_msg_code;
					param["fromMarketId"] = marketInfoMain.marketId;
					param["stationList"] = stationList;
					stationCodeList.push(param);
					store.set("stationList",stationCodeList);
				}
				stationXmppJs.newStation(paramStation);
			
			}	
		 }catch(e){
			 var consultationList = store.get("consultationList");
			 var orderList = store.get("orderList");
			 var stationList = store.get("stationList");
			try{ 
			 if(consultationList != null && consultationList.length > 0){
					$.each(consultationList,function(k,v){
							var contentList = new Array();
							$.each(v.content,function(k2,v2){
								if((msgid - 604800000) < v2.time){
									contentList.push(v2);
								}
							})
							v.content.length = 0;
							v.content.push(contentList);
					})
			 }
			 if(orderList != null && orderList.length > 0){
					$.each(orderList,function(k,v){
							var contentList = new Array();
							$.each(v.content,function(k2,v2){
								if((msgid - 604800000) < v2.time){
									contentList.push(v2);
								}
							})
							v.content.length = 0;
							v.content.push(contentList);
					})
			 }
			 if(stationList != null && stationList.length > 0){
					$.each(stationList,function(k,v){
							var contentList = new Array();
							$.each(v.content,function(k2,v2){
								if((msgid - 604800000) < v2.time){
									contentList.push(v2);
								}
							})
							v.content.length = 0;
							v.content.push(contentList);
					})
			 }
			}catch(e){ 
				 if(consultationList != null && consultationList.length > 0){
						$.each(consultationList,function(k,v){
								var contentList = new Array();
								$.each(v.content,function(k2,v2){
									if((msgid - 86400000) < v2.time){
										contentList.push(v2);
									}
								})
								v.content.length = 0;
								v.content.push(contentList);
						})
				 }
				 if(orderList != null && orderList.length > 0){
						$.each(orderList,function(k,v){
								var contentList = new Array();
								$.each(v.content,function(k2,v2){
									if((msgid - 86400000) < v2.time){
										contentList.push(v2);
									}
								})
								v.content.length = 0;
								v.content.push(contentList);
						})
				 }
				 if(stationList != null && stationList.length > 0){
						$.each(stationList,function(k,v){
								var contentList = new Array();
								$.each(v.content,function(k2,v2){
									if((msgid - 86400000) < v2.time){
										contentList.push(v2);
									}
								})
								v.content.length = 0;
								v.content.push(contentList);
						})
				 }
			}
			 return true;
		 }
			//必须返回,要不然连接断开
			return true;
		},
		getRandom : function(n){
	        return Math.floor(Math.random()*n+1);
	    }
	}
	return rightBar;
})
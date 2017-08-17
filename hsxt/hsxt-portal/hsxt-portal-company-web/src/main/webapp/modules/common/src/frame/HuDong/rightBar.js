define(["text!commTpl/frame/rightBar.html",
		"hsec_tablePointSrc/strophe/strophe",
		"commonDat/frame/companyInfo",
		"commonSrc/frame/HuDong/orderXmpp",
		"commonSrc/frame/HuDong/stationXmpp",
		"commonSrc/frame/HuDong/consultationXmpp",
		"commonSrc/frame/HuDong/marketXmpp",
		"commonSrc/frame/HuDong/CMLink",
		"commonSrc/frame/HuDong/publicTool",
		"commonSrc/frame/HuDong/newStrRefresh",
		"commSrc/frame/index",
		"localStorage"
		],function(rightBarTpl,stropheJs,companyAjax,orderXmppJs,stationXmppJs,consultationXmppJs,marketXmppJs,CMLink,publicTool,newStrRefresh,index){   
   
	 //登录互动的uid 
	 var uuid;
	 //var uuid = 'w_nc_18923729275@im.gy.com';
	 //登录用户头像url
	 var uimgUrl;
	 //登录互动密码
	 var mid;
	//系统默认头像url
	 var txUrl = "resources/img/defaultheadimg.png";
	 //登录企业信息
	 var marketObj;
	 var vShopId;
    var param = {};
    var gab = null;
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
			    $("#service").html(rightBarTpl);
			    $( ".serviceItem" ).accordion();//申缩菜单效果			
				$('.listScroll').jScrollPane();//滚动条
				rightBar.removeBar();
				rightBar.headMarket(function(){
					uuid = "w_e_"+marketObj.ResNo+"_"+marketObj.userName+"@im.gy.com/web_im";
					mid = $.cookie("MID");
//					mid = $.cookie("token");
					param["uName"] = marketObj.uName;
					param["userName"] = marketObj.userName;
					param["tfsUrl"] = marketObj.httpUrl;
					param["morenPic"] = txUrl;
					param["headPic"] = uimgUrl;
					param["marketId"] = marketObj.ResNo+"_"+marketObj.userName;
					param["uuid"] =  "w_e_"+marketObj.ResNo+"_"+marketObj.userName+"@im.gy.com/web_im";
					param["vShopId"] = vShopId;
					store.set("marketInfoMain",param);
					rightBar.startConnection();
					orderXmppJs.init();
					stationXmppJs.init();
					marketXmppJs.init();
					consultationXmppJs.init();
					CMLink.leftClickLink();
					rightBar.rightBarClick();
					publicTool.xiaoxitishi();
					newStrRefresh.refreshStr();
				});
				//加载首页
				index.loadIndxData();
		},
		startConnection : function() {
			return null;
			/** modify by zhucy 2016-02-22 注释互动xmpp 链接
		    var jid = store.get('JID', null);
		    var sid = store.get('SID', null);
		    comm.connection = new Strophe.Connection(comm.domainList.xmpp);
		    if ((jid != null) && (sid != null) && (jid == uuid)) {
		    	gab = comm.connection;
		    	comm.connection.attach(jid, sid, gab._proto.rid,rightBar.onConnect);
		    }
		    else {
		    	comm.connection.connect(uuid, mid,rightBar.onConnect);
		    }
			**/
		},
		onConnect : function(status){
			   var selfFn = arguments.callee;
				//连接成功
			   if (status == Strophe.Status.CONNECTED) {
				 //alert('连接成功');
					 comm.connection.addHandler(rightBar.onMessage, null, 'message', null, null, null);
					 comm.connection.send($pres().tree());
					 gab = comm.connection;
					 if (gab != null) {
					        	store.set('JID', gab.jid);
					        	store.set('SID', gab._proto.sid);
					    } else {
					    	    store.set('JID', null);
						        store.set('SID', null);
					    }
				} else if (status == Strophe.Status.DISCONNECTED) {
					//debugger
					//alert('连接断开');
					//连接断开，重新连接
					 comm.connection.connect(uuid, mid, selfFn);
				} else if (status == Strophe.Status.CONNECTING) {
					//alert('连接中');
				} else if (status == Strophe.Status.CONNFAIL || status == Strophe.Status.AUTHFAIL) {
					//alert('连接失败');
				} else if (status == Strophe.Status.ATTACHED) {
					 comm.connection.addHandler(rightBar.onMessage, null, 'message', null, null, null);
				}
		},
		headMarket : function(callback){
			companyAjax.getCompanyInfo(null,function(r){
				if(r.retCode == 200){
					marketObj = r.data;
					var ttURL = marketObj.img;
					if(ttURL == '' || ttURL == null || ttURL == "null"){
						uimgUrl = txUrl;
					}else if(ttURL.indexOf("http://") < 0){
						uimgUrl = marketObj.httpUrl + marketObj.img;
					}else{
						uimgUrl = marketObj.img;
					}
					//给rightbar.html页面添加网上商城信息
					if(null != marketObj.logo && '' != marketObj.logo){
						$('#rightBar_companyPic').attr("src",marketObj.httpUrl + marketObj.logo);
					}else{
						//修改企业网上商城默认显示的图片 modify by zhucy 2016-05-28
						$('#rightBar_companyPic').attr("src","resources/img/store_noimg.gif");
					}
					//当企业用户名过长时处理 zhanghh 2016-01-18 添加注释。
					if(marketObj.companyName.length >= 12){
						$('#rightBar_companyName').html(marketObj.companyName.substring(0,11)+"...");
					}else{
						$('#rightBar_companyName').html(marketObj.companyName);
					}
					if(marketObj.companyType == 'B'){
						$('#rightBar_companyType').html("成员企业");
					}else{
						$('#rightBar_companyType').html("托管企业");
					}
					if("" != marketObj.createTime){
						try{
							$('#rightBar_companyCreateTime').html(marketObj.createTime.split(" ")[0])	
						}catch(e){}
					}
					companyAjax.getVirtualShop(null,function(r){
						var obj = r.data;
					    if(null!=obj.virtualShop&&''!=obj.virtualShop){
					    	vShopId = obj.virtualShop.strId;
					    }else{
					    	vShopId = "";
					    }
					    callback(true);
					});
				}//end retCode if;
		  })
		},
		rightBarClick : function(){
			$(".rightTab").on("click",function(){
					$(".rightTab").removeClass("cur")
					$(this).addClass("cur");
				if($(this).hasClass("myFriend")){
					$(".serviceItem").show();
					$(".addressBook").hide();
				}
				if($(this).hasClass("tourist")){
					$(".serviceItem").hide();
					$(".addressBook").show();
				}
			})
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
			if ((jsonStr.msg_type == 2 && jsonStr.msg_code == 201) || (jsonStr.msg_type == 2 && (jsonStr.msg_code == 00 || jsonStr.msg_code == 10 || jsonStr.msg_code == 12) && name.indexOf("e_") < 0)) {
				jsonStr.msg_content = publicTool.biaoqingchuli(jsonStr.msg_content);
				var fromName = name.split("_");
				var frName = fromName[fromName.length-1];
				jsonStr["fromId"] = frName;
				jsonStr["fromTo"] = name;
				jsonStr["time"] = msgid;
				jsonStr["newStr"] = 1;
				if(jsonStr.msg_icon == null || jsonStr.msg_icon == ""){
					jsonStr["msg_icon"] = txUrl;
				}else if(jsonStr.msg_icon.indexOf("http://") < 0 && jsonStr.msg_icon != null && jsonStr.msg_icon != ""){
					jsonStr["msg_icon"] = marketInfoMain["tfsUrl"]+jsonStr.msg_icon;
				}else{
					if(jsonStr.msg_icon != null && jsonStr.msg_icon != ""){
						jsonStr["msg_icon"] = jsonStr.msg_icon;
					}else{
						jsonStr["msg_icon"] = txUrl;
					}
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
							v.fromHeadImg = jsonStr["msg_icon"];
							return false;
						}
					})
					if(bool != 1){
						var param = {};
						var contentList = new Array();
						contentList.push(jsonStr);
						param["fromId"] = frName;
						param["fromHeadImg"] = jsonStr["msg_icon"];
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
					param["fromHeadImg"] = jsonStr["msg_icon"];
					param["fromTo"] = name;
					param["fromMarketId"] = marketInfoMain.marketId;
					param["content"] = contentList;
					contentObj.push(param);
					store.set("consultationList",contentObj);
				}
				jsonStr["fromHeadImg"] = jsonStr["msg_icon"];
				consultationXmppJs.newContent(jsonStr);
			}
			
			//推送消息，订单
			if(jsonStr.msg_type == 1 && (jsonStr.msg_code == 202 || jsonStr.msg_code == 204)){
				
				function orderJson(jsonStr){	
					var paramOrder = {};
					paramOrder["orderTimeId"] = "time:"+msgid+",orderId:"+ jsonStr.msg_id;
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
				
				publicTool.newStr(1);
				if(null != jsonStr.msg_repast_type && "" != jsonStr.msg_repast_type && null != jsonStr.msg_sale_network && "" != jsonStr.msg_sale_network){
					var shopId = jsonStr.msg_sale_network.split(",")[0];
					companyAjax.shopFoodInfoHuDong("shopId="+shopId,function(r){
						jsonStr["shop_name_addr"] = r.data.name+"【"+r.data.addAll+"】";
						orderJson(jsonStr);
					})
				}else{
						jsonStr["msg_sale_network"] = "";
						jsonStr["shop_name_addr"] = "";
						jsonStr["msg_repast_type"] = "";
						orderJson(jsonStr);
				}

		}
			
			//推送消息，站内消息
			if (jsonStr.msg_type == 1 && jsonStr.msg_code == 203) {
				publicTool.newStr(2);
				var paramStation = {};
				paramStation["time"] = msgid;
				paramStation["oneNum"] = msgid + rightBar.getRandom(9999);
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
			//企业通讯录
			if (jsonStr.msg_type == 2 && (jsonStr.msg_code == 00 || jsonStr.msg_code == 10 || jsonStr.msg_code == 12)) {
				if(name.indexOf("e_") > 0){
					if(!$(".serviceNav").find(".tourist").find("span").hasClass("red")){
						$(".tourist").find("span").addClass("red");
					}
					var fromName = name.split("_");
					var frName = fromName[fromName.length-2]+"_"+fromName[fromName.length-1];
					jsonStr.msg_content = publicTool.biaoqingchuli(jsonStr.msg_content);
					jsonStr["frName"] = frName;
					jsonStr["fromId"] = name;
					jsonStr["time"] = msgid;
					jsonStr["newStr"] = 1;
					if(jsonStr.msg_icon == null || jsonStr.msg_icon == ""){
						jsonStr["msg_icon"] = txUrl;
					}else if(jsonStr.msg_icon.indexOf("http://") < 0 && jsonStr.msg_icon != null && jsonStr.msg_icon != "" && jsonStr.msg_icon != txUrl){
						jsonStr["msg_icon"] = marketInfoMain["tfsUrl"]+jsonStr.msg_icon;
					}else{
						if(jsonStr.msg_icon != null && jsonStr.msg_icon != ""){
							jsonStr["msg_icon"] = jsonStr.msg_icon;
						}else{
							jsonStr["msg_icon"] = txUrl;
						}
					}
					var contentList = store.get("contentList");
					if(contentList != null && contentList != "" && contentList != undefined){
						var bool = 0;
						$.each(contentList,function(k,v){
							if(v.fromId == frName && v.fromMarketId == marketInfoMain.marketId){
								$.each(v.content,function(k2,v2){
									v2.newStr = 0;
								})
								bool = 1;
								v.fromType = name;
								v.content.push(jsonStr);
								return false;
							}
						})
						
						if(bool != 1){
							var param = {};
							var contentObj = new Array();
							contentObj.push(jsonStr);
							param["fromId"] = frName;
							param["fromType"] = name;
							param["fromMarketId"] = marketInfoMain.marketId;
							param["content"] = contentObj;
							contentList.push(param);
						}
						store.set("contentList",contentList);
					}else{
						var param = {};
						var contentList = new Array();
						var contentObj = new Array();
						contentList.push(jsonStr);
						param["fromId"] = frName;
						param["fromType"] = name;
						param["fromMarketId"] = marketInfoMain.marketId;
						param["content"] = contentList;
						contentObj.push(param);
						store.set("contentList",contentObj);
					}
					marketXmppJs.newContent(jsonStr);
				}
			}
		 }catch(e){
			 var consultationList = store.get("consultationList");
			 var orderList = store.get("orderList");
			 var stationList = store.get("stationList");
			 var contentList = store.get("contentList");
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
			 if(contentList != null && contentList.length > 0){
					$.each(contentList,function(k,v){
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
				 if(contentList != null && contentList.length > 0){
						$.each(contentList,function(k,v){
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
define(["hsec_tablePointDat/strophe/strophe",
        "text!commTpl/HuDong/marketTpl/marketMini.html",
        "text!commTpl/HuDong/marketTpl/marketInfo.html",
        "text!commTpl/HuDong/marketTpl/marketInfoMiddle.html",
        "text!commTpl/HuDong/marketTpl/marketInfoImgMiddle.html",
        "text!commTpl/HuDong/marketTpl/marketInfoMiddleFrom.html",
        "text!commTpl/HuDong/marketTpl/marketInfoRight.html",
        "hsec_tablePointDat/upload/upload",
        "commonSrc/frame/HuDong/publicTool",
        "hsec_tablePointSrc/tablePoint"], function(stropheAjax,marketMiniTpl,marketInfoTpl,marketInfoMiddleTpl,marketInfoImgMiddleTpl,marketInfoMiddleFromTpl,marketInfoRightTpl,uploadAjax,publicTool,tablePoint) {
			//企业信息集合存储
			var marketList = new Array();
			//企业前缀
			var formHead = "";
	var marketXmpp = {
			init : function(){
				var CompanyContacts = store.get("CompanyContacts");
				var marketInfoMain = store.get("marketInfoMain");
				var contentList = store.get("contentList");
				stropheAjax.getCompanyContacts(function(response){
					marketList.length = 0;
					if(response.retCode != 200){
						if(CompanyContacts != null && CompanyContacts !=""){
							if(CompanyContacts.marketId == marketInfoMain.marketId){
								response = CompanyContacts.response;
							}
						}
					}else{
						var CompanyObj = {};
						CompanyObj["response"] = response;
						CompanyObj["marketId"] = marketInfoMain.marketId;
						store.set("CompanyContacts",CompanyObj);
					}
					//遍历渲染表格 Add by zhanghh 2016-01-18 添加注释。
					$.each(response.data,function(k,v){
						var param = {};
						param["time"] = "";
						param["msg_content"] = "";
						param["newStrBool"] = 0;
						if(contentList != null && contentList != "" && contentList != undefined){
							$.each(contentList,function(k2,v2){
								//Modifly by zhanghh 2016-01-18 
								//resourceNo -> entResNo ;accountNo -> userName :
								//注释： 1,把原来的resourceNo替换成现在的 entResNo
								//      2,把原来的accountNo替换成现在的 userName
								if((formHead+v.entResNo+"_"+v.userName) == v2.fromId && v2.fromMarketId == marketInfoMain.marketId){
									$.each(v2.content,function(k3,v3){
										if(v3.newStr == -1){
											param["time"] = v3.time;
											param["msg_content"] = v3.msg_content;
											param["msg_code"] = v3.msg_code;
											return;
										}
										if(v3.newStr == 1){
											param["time"] = v3.time;
											param["msg_content"] = v3.msg_content;
											param["msg_code"] = v3.msg_code;
											param["newStrBool"] = 1;
											return;
										}
									})
									return false;
								}
							})
						}
						//Modifly by zhanghh 2016-01-18
						param["accountNo"] = formHead+v.entResNo+"_"+v.userName;
						//param["name"] = v.name;
						param["name"] = v.realName;//Modifly by zhanghh 2016-01-18
						if(v.headPic != null && v.headPic != ""){
							if(v.headPic.indexOf("http://") < 0){
								param["headPic"] = marketInfoMain["tfsUrl"] + v.headPic;
							}else{
								param["headPic"] = v.headPic;	
							}
						}else{
							param["headPic"] = marketInfoMain["morenPic"];
						}
						param["tfsUrl"] = v.headPic;
						param["resourceNo"] = v.entResNo;//Modifly by zhanghh 2016-01-18
						marketList.push(param);
					})
					var param = {};
					param["marketList"] = marketList;
					param["DatetimeNew"] = publicTool.DatetimeNew;
					var html = _.template(marketMiniTpl,param);
					$("#txmsg").find("ul").eq(0).html(html);
					marketXmpp.marketClick();
					
					if($("#txmsg").find(".count").length > 0){
						$(".tourist").find("span").addClass("red");
					}else{
						$(".tourist").find("span").removeClass("red");
					}
				})
			},
			newContent : function(content){
				content["NowtimeNew"] = publicTool.NowtimeNew;
				if($("#hd_msg_content").length > 0){
					if($("#hd_msg_content").attr("data-id") == content.frName){
						var html = _.template(marketInfoMiddleFromTpl,content);
						$("#txjilu").find("ul").eq(0).append(html);
						marketXmpp.scrollHeight();
					}
						$.each($("#txmsg").find("ul>li"),function(k,v){
							if($(v).attr("data-id") == content.frName){
								if(content.msg_code == 10){
									$(v).find(".newContent").html("<span>图片消息</span>"+"<span></span>");
								}else{
									$(v).find(".newContent").html("<span style='width: 120px' class='classtext'>"+content.msg_content+"</span><span></span>");
								}
								$(v).find(".newContent").find("span").eq(1).addClass("count");
								return false;
							}
						})
					
				}else{
					$.each($("#txmsg").find("ul>li"),function(k,v){
						if($(v).attr("data-id") == content.frName){
							if(content.msg_code == 10){
								$(v).find(".newContent").html("<span>图片消息</span>"+"<span></span>");
							}else{
								$(v).find(".newContent").html("<span style='width: 120px' class='classtext'>"+content.msg_content+"</span><span></span>");
							}
							$(v).find(".newContent").find("span").eq(1).addClass("count");
							return false;
						}
					})
				}
			},
			//绑定事件；描述zhanghh 2016-01-18
			marketClick : function(){
				//清空聊天记录
				$("#huDongMain2").on("click",".talk_history_cl",function(){
					var thisId = $(this).parents("#hd_msg_content").attr("data-id");
					var marketInfoMain = store.get("marketInfoMain");
					var contentList = store.get("contentList");
						if(contentList != null && contentList != "" && contentList != undefined){
							$.each(contentList,function(k2,v2){
								if(thisId == v2.fromId && v2.fromMarketId == marketInfoMain.marketId){
									v2.content.length = 0;
									store.set("contentList",contentList);
									$("#txjilu>ul").html("");
									return false;
								}
							})
						}
				})
				//企业通讯录列表点击事件。
				$("#txmsg").on("click","ul>li",function(){
					$(this).find(".newContent").find("span").removeClass("count");
					if($("#txmsg").find(".count").length > 0){
						$(".tourist").find("span").addClass("red");
					}else{
						$(".tourist").find("span").removeClass("red");
					}
					var num = $(this).attr("data-id");
					var leftHtml = null;
					if($("#hd_leftMenu")){
						$("#hd_leftMenu").find("ul").eq(0).find("li").removeClass("hd_left_cur");
						$.each($("#hd_leftMenu").find("ul").eq(0).find("li"),function(k,v){
							if($(v).attr("data-id") == num){
								$(v).remove();
							}
						})
						leftHtml = $("#hd_leftMenu").find("ul").eq(0).html();
					}
					
					var contentList = null;
					contentList = store.get("contentList");
					var marketInfoMain = store.get("marketInfoMain");
					var param = null;
					var content = null;
					$.each(marketList,function(k,v){
						if(v.accountNo == num){
							param = v;
							return false;
						}
					})
					$.each(contentList != null?contentList :"",function(k,v){
						if(v.fromId == num && v.fromMarketId == marketInfoMain.marketId){
							content = v;
							$.each(v.content,function(k2,v2){
								if(v2.newStr == 1){
									v2.newStr = -1;
									return;
								}
							})
							return false;
						}
					})
					store.set("contentList",contentList);
					param["num"] = num;
					param["NowtimeNew"] = publicTool.NowtimeNew;
					param["content"] = content;
					var html = _.template(marketInfoTpl,param);
					$("#huDongMain2").html(html);
					$("#hd_leftMenu").find("ul").eq(0).append(leftHtml);
					marketXmpp.txClick();
					marketXmpp.leftMarketInfoClick();
					marketXmpp.scrollHeight();
				})
			},
			leftLick : function($this){
				$($this).parents("ul").find("li").removeClass("hd_left_cur");
				$($this).parent().addClass("hd_left_cur");
				var marketInfoMain = store.get("marketInfoMain");
				var num = $($this).parent().attr("data-id");
				var contentList = null;
				contentList = store.get("contentList");
				var param = null;
				var content = null;
				$.each(marketList,function(k,v){
					if(v.accountNo == num){
						param = v;
						return false;
					}
				})
				$.each(contentList != null?contentList :"",function(k,v){
					if(v.fromId == num && v.fromMarketId == marketInfoMain.marketId){
						content = v;
						return false;
					}
				})
				param["num"] = num;
				param["NowtimeNew"] = publicTool.NowtimeNew;
				param["content"] = content;
				var html = _.template(marketInfoRightTpl,param);
				$("#hd_msg_content").html(html);
				$("#hd_msg_content").attr("data-id",num);
				marketXmpp.txClick();
				marketXmpp.scrollHeight();
			},
			leftMarketInfoClick : function(){
				$("#hd_leftMenu").on("click","ul>li>i",function(){
						var thisLi = $(this).parent();
						var thisDiv = $(this).parents("#hd_leftMenu");
						if($(thisLi).hasClass("hd_left_cur")){
							 var siblings =	$(thisLi).siblings().eq(0);
							 var index = $(siblings).index();
							if($(thisDiv).find("ul>li").length > 1){
							 $.each($(siblings).parents("#hd_leftMenu").find("ul>li>p"),function(k,v){
								 if(index == k){
									 $(thisLi).remove();
									 $(v).trigger("click");
									 return false;
								 }
							 })
							}else{
								$("#huDongMain2").html("");
							}
						}else{
							$(thisLi).remove();
						}
				})
			},
			txClick : function(){
				
				$("#huDongMain2").on("click",".gbck",function(){
					$("#huDongMain2").attr("style","position: absolute;top: 25%;left: 28%;z-index: 99");
					$("#huDongMain2").html("");
				})
				
				$("#biaoqingLi").on("click",".biaoqing_btn",function(){
					if($(".biaoqing_box").is(":hidden")){
						$(".biaoqing_box").show();
					}else{
						$(".biaoqing_box").hide();
					}
				})
					/*图片点击放大*/
				$('#txjilu').on('click',"img",function(){
								var arr = new Array();
								$.each($(this).parent().find("img"),function(k,v){
									arr.push($(v).attr("src"));
								})
								tablePoint.imgBig(arr,this);
				});
				
				$("#biaoqing_box").on("click",".i_biaoqing",function(){
					var imgid = $(this).attr("imgid");
					if(imgid.length == 1){
						imgid = "00"+imgid;
					}
					if(imgid.length == 2){
						imgid = "0"+imgid;
					}
					imgid = "["+imgid+"]";
					$("#inputStr").val($("#inputStr").val()+imgid);
					$(".biaoqing_box").hide();
				})
				
				$('#imgUploadDiv').off('change', '.hd_inputFile').on('change', '.hd_inputFile', function(e) {	
						uploadAjax.upload("imgHuDong",function(response) {
								 var urlimg,imgName;
								 var msg_imageNailsUrl;
								 var msg_imageNails_height = "150";
								 var msg_imageNails_width = "150";
								try{
									 urlimg = response.tfs;
									 var pics = eval("("+response.pics+")");
									 imgName = pics[0].source[0].name;
									 imgName = urlimg+imgName;
									 msg_imageNailsUrl = urlimg + pics[0].mobile[0].name;
								   if(imgName == null || imgName == "" || imgName == undefined){
									   alert("图片上传失败。图片不能大于1M.");
									   return false;
								   }
								}catch(e){
									   alert("图片上传失败。图片不能大于1M.");
									   return false;
								}
							   var marketInfoMain = store.get("marketInfoMain");
							   var contentList = store.get("contentList");
							   var dataId = $("#hd_msg_content").attr("data-id");
								var coList = new Array();
								if(contentList != null && contentList != "" && contentList != undefined){
									$.each(contentList,function(k,v){
										if(v.fromId == dataId && v.fromMarketId == marketInfoMain.marketId){
											coList.push(v);
										}
									})
								}
								 var coId = null;
								if(coList.length < 1){
									coId = "w_e_"+dataId;
								}else{
									coId = coList[coList.length - 1].fromType;
								}
							   
								var toId = coId+marketInfoMain["virtualName"];
								var timestamp = new Date().getTime();
								var uName;
								if(marketInfoMain["uName"] != null && marketInfoMain["uName"] != ""){
									uName = marketInfoMain["uName"];
								}else{
									uName = marketInfoMain["userName"];
								}
								var ptettr = "{\"msg_type\": \"2\",\"sub_msg_code\": \"10101\",\"msg_code\": \"10\",\"msg_note\":\"" + uName + "\", \"msg_icon\":\"" + marketInfoMain["headPic"] + "\",\"msg_content\": \"" + imgName + "\",\"msg_imageNailsUrl\": \"" + msg_imageNailsUrl + "\",\"msg_imageNails_height\": \"" + msg_imageNails_height + "\",\"msg_imageNails_width\": \"" + msg_imageNails_width + "\",\"msg_vshopId\": \"" + marketInfoMain["vShopId"] + "\"}";
								
								var laiyuan = Strophe.xmlElement('id','',marketInfoMain["marketId"]+timestamp+publicTool.getRandom(999999));
								var re = Strophe.xmlElement('request');
								re.setAttribute("xmlns","gy:abnormal:offline"); 
								re.appendChild(laiyuan);
								var body = Strophe.xmlElement('body','',ptettr);
								var reply = $msg({
								to: toId,
								from: marketInfoMain["uuid"],
								id: timestamp,
								type: 'chat'
								});
								reply.nodeTree.appendChild(re);
								reply.nodeTree.appendChild(body);
							    comm.connection.send(reply.tree());
							    
							    var dataObj=eval("("+ptettr+")");
							    dataObj["fromId"] = marketInfoMain["uuid"].split("@")[0];
							    dataObj["time"] = timestamp;
								if(contentList != null && contentList != "" && contentList != undefined){
									var boolthis = 0;
									$.each(contentList,function(k,v){
										if(v.fromId == dataId && v.fromMarketId == marketInfoMain.marketId){
											v.content.push(dataObj);
											boolthis = 1;
											return false;
										}
									})
									if(boolthis == 0){
										var param = {};
										var content = new Array();
										var contentObj = new Array();
										content.push(dataObj);
										param["fromId"] = dataId;
										param["fromType"] = coId;
										param["fromMarketId"] = marketInfoMain.marketId;
										param["content"] = content;
										contentList.push(param);
									}
									store.set("contentList",contentList);
								}else{
									var param = {};
									var contentList = new Array();
									var contentObj = new Array();
									contentList.push(dataObj);
									param["fromId"] = dataId;
									param["fromType"] = coId;
									param["fromMarketId"] = marketInfoMain.marketId;
									param["content"] = contentList;
									contentObj.push(param);
									store.set("contentList",contentObj);
								}
							    
							var news = {};
								news["inputImg"] = imgName;
								news["timestamp"] = publicTool.NowtimeNew(timestamp);
								news["headPic"] = marketInfoMain["headPic"];
								var html = _.template(marketInfoImgMiddleTpl,news);
								$("#txjilu").find("ul").eq(0).append(html);
								$("#inputStr").val("");
								marketXmpp.scrollHeight();
						})
				})
				
				$("#goto_btn").unbind("click").on("click",function(){
					var dataId = $("#hd_msg_content").attr("data-id");
					var marketInfoMain = store.get("marketInfoMain");
					var contentList = store.get("contentList");
					var coList = new Array();
					if(contentList != null && contentList != "" && contentList != undefined){
						$.each(contentList,function(k,v){
							if(v.fromId == dataId && v.fromMarketId == marketInfoMain.marketId){
								coList.push(v);
							}
						})
					}
					 var coId = null;
					if(coList.length < 1){
						coId = "w_e_"+dataId;
					}else{
						coId = coList[coList.length - 1].fromType;
					}
					
					var toId = coId+marketInfoMain["virtualName"];
					var timestamp = new Date().getTime();
					var inputValue = $("#inputStr").val();
					//inputValue = inputValue.replace(/\n/g,"<br/>");
					inputValue = $.trim(publicTool.valueReplace(inputValue)); 
					var queryParam = {"needCheckStr":inputValue};
					if(inputValue != null && $.trim(inputValue) != ""){
					stropheAjax.isContainSensitiveWord(queryParam,function(response){
						if(response.retCode=="206"){
							comm.i_alert("包含违禁字:"+response.data);
						}else{
							var marketInfoMain = store.get("marketInfoMain");
							var contentList = store.get("contentList");
							var uName;
							if(marketInfoMain["uName"] != null && marketInfoMain["uName"] != ""){
								uName = marketInfoMain["uName"];
							}else{
								uName = marketInfoMain["userName"];
							}
							var ptettr = "{\"msg_type\": \"2\",\"sub_msg_code\": \"10101\",\"msg_code\": \"00\",\"msg_note\":\"" + uName + "\", \"msg_icon\":\"" + marketInfoMain["headPic"] + "\",\"msg_content\": \"" + inputValue + "\",\"msg_vshopId\": \"" + marketInfoMain["vShopId"] + "\"}";
							
							var laiyuan = Strophe.xmlElement('id','',marketInfoMain["marketId"]+timestamp+publicTool.getRandom(999999));
							var re = Strophe.xmlElement('request');
							re.setAttribute("xmlns","gy:abnormal:offline"); 
							re.appendChild(laiyuan);
							var body = Strophe.xmlElement('body','',ptettr);
							var reply = $msg({
							to: toId,
							from: marketInfoMain["uuid"],
							id: timestamp,
							type: 'chat'
							});
							reply.nodeTree.appendChild(re);
							reply.nodeTree.appendChild(body);
						    comm.connection.send(reply.tree());
						    
						    var dataObj=eval("("+ptettr+")");
						    inputValue = publicTool.biaoqingchuli(inputValue);
						    dataObj.msg_content = inputValue;
						    dataObj["fromId"] = marketInfoMain["uuid"].split("@")[0];
						    dataObj["time"] = timestamp;
							if(contentList != null && contentList != "" && contentList != undefined){
								var boolthis = 0;
								$.each(contentList,function(k,v){
									if(v.fromId == dataId && v.fromMarketId == marketInfoMain.marketId){
										v.content.push(dataObj);
										boolthis = 1;
										return false;
									}
								})
								if(boolthis == 0){
									var param = {};
									var content = new Array();
									var contentObj = new Array();
									content.push(dataObj);
									param["fromId"] = dataId;
									param["fromType"] = coId;
									param["fromMarketId"] = marketInfoMain.marketId;
									param["content"] = content;
									contentList.push(param);
								}
								store.set("contentList",contentList);
							}else{
								var param = {};
								var contentList = new Array();
								var contentObj = new Array();
								contentList.push(dataObj);
								param["fromId"] = dataId;
								param["fromType"] = coId;
								param["fromMarketId"] = marketInfoMain.marketId;
								param["content"] = contentList;
								contentObj.push(param);
								store.set("contentList",contentObj);
							}
							
				 			
						var news = {};
							news["inputValue"] = inputValue;
							news["timestamp"] = publicTool.NowtimeNew(timestamp);
							news["headPic"] = marketInfoMain["headPic"];
							var html = _.template(marketInfoMiddleTpl,news);
							$("#txjilu").find("ul").eq(0).append(html);
							$("#inputStr").val("");
							marketXmpp.scrollHeight();
							}
					})}
				})
				
				
			},
			scrollHeight : function(){
				if($('#txjilu').length > 0){
					$('#txjilu').prop('scrollTop',$("#txjilu")[0].scrollHeight);
				}
			}
			
	}
	return marketXmpp;
})
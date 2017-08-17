define(["text!commTpl/HuDong/consultationTpl/consultationMini.html",
        "text!commTpl/HuDong/consultationTpl/consultationInfo.html",
        "text!commTpl/HuDong/consultationTpl/consultationMiddle.html",
        "text!commTpl/HuDong/consultationTpl/consultationMiddleFrom.html",
        "text!commTpl/HuDong/consultationTpl/consultationInfoImgMiddle.html",
        "text!commTpl/HuDong/consultationTpl/consultationInfoRight.html",
        "text!commTpl/HuDong/consultationTpl/consultationStrOld.html",
        "commSrc/frame/HuDong/publicTool"], function(consultationMiniTpl,consultationInfoTpl,consultationMiddleTpl,consultationMiddleFromTpl,consultationInfoImgMiddleTpl,consultationRightTpl,consultationStrOldTpl,publicToolJs) {
	var consultationXmpp = {
			init : function(){
				var consultationList = null;
				var listParam = new Array();
				consultationList = store.get("consultationList");
				var marketInfoMain = store.get("marketInfoMain");
				if(consultationList != null && consultationList != ""){
					$.each(consultationList,function(k,v){
						if(v.fromMarketId == marketInfoMain.marketId){
							listParam.push(v);
						}
					})
					if(listParam.length > 0){	
						var consultationParam = {};
						consultationParam["consultationList"] = listParam;
						consultationParam["DatetimeNew"] = publicToolJs.DatetimeNew;
						var html = _.template(consultationMiniTpl,consultationParam);
						$("#zxmsg").find("ul").html(html);
					}	
				}
				consultationXmpp.consultationClick();
			},
			newContent : function(content){
				if($("#hd_msg_content").attr("data-id") == content.fromId){
					content.newStr= -1;
				}
				var contentList = new Array();
				contentList.push(content);
				var param = {};
				param["fromId"] = content.fromId;
				param["content"] = contentList;
				var list = new Array();
				list.push(param);
				var consultationParam = {};
				consultationParam["consultationList"] = list;
				consultationParam["DatetimeNew"] = publicToolJs.DatetimeNew;
				var html = _.template(consultationMiniTpl,consultationParam);
				$.each($("#zxmsg ul li"),function(k,v){
					if($(v).attr("data-id") == content.fromId){
						$(v).remove();
						return false;
					}
				})
				$("#zxmsg").find("ul").append(html);
				if($("#hd_msg_content").attr("data-id") == content.fromId){
					var consultationList = store.get("consultationList");
					var marketInfoMain = store.get("marketInfoMain");
					$.each(consultationList != null?consultationList :"",function(k,v){
						if(v.fromId == content.fromId && v.fromMarketId == marketInfoMain.marketId){
							$.each(v.content,function(k2,v2){
								if(v2.newStr == 1){
									v2.newStr = -1;
									return;
								}
							})
							return;
						}
					})
					store.set("consultationList",consultationList);
					param["NowtimeNew"] = publicToolJs.NowtimeNew;
					param["content"] = content;
					var html = _.template(consultationMiddleFromTpl,param);
					$("#txjilu").find("ul").eq(0).append(html);
				}
				consultationXmpp.scrollHeight();
				publicToolJs.newStr(0);
			},
			consultationClick : function(){
				$("#zxmsg").on("click","ul>li",function(){
					publicToolJs.removeCount(this);
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
					
					var marketInfoMain = store.get("marketInfoMain");
					var consultationList = null;
					consultationList = store.get("consultationList");
					var param = {};
					var content = null;
					var headPic = null;
					$.each(consultationList != null?consultationList :"",function(k,v){
						if(v.fromId == num && v.fromMarketId == marketInfoMain.marketId){
							content = v;
							$.each(content.content,function(k2,v2){
								if(v2.newStr == 1 || v2.newStr == -1){
									headPic = v2.msg_icon;
									v2.newStr = -1;
									return;
								}
							})
							return false;
						}
					})
					store.set("consultationList",consultationList);
					param["num"] = num;
					param["headPic"] = headPic;
					param["NowtimeNew"] = publicToolJs.NowtimeNew;
					param["content"] = content;
					var html = _.template(consultationInfoTpl,param);
					$("#huDongMain2").html(html);
					$("#hd_leftMenu").find("ul").eq(0).append(leftHtml);
					consultationXmpp.scrollHeight();
					consultationXmpp.txClick();
					consultationXmpp.leftconsultationClick();
				})
			},
			leftLick : function($this){

				$($this).parents("ul").find("li").removeClass("hd_left_cur");
				$($this).parent().addClass("hd_left_cur");
				var marketInfoMain = store.get("marketInfoMain");
				var num = $($this).parent().attr("data-id");
				var consultationList = null;
				consultationList = store.get("consultationList");
				var param = {};
				var content = null;
				var picHead = null;
				$.each(consultationList != null?consultationList :"",function(k,v){
					if(v.fromId == num && v.fromMarketId == marketInfoMain.marketId){
						content = v;
						$.each(v.content,function(k2,v2){
							if(v2.newStr == 1 || v2.newStr == -1){
								picHead = v2.msg_icon;
								return false;
							}
						})
						return false;
					}
				})
				param["num"] = num;
				param["NowtimeNew"] = publicToolJs.NowtimeNew;
				param["picHead"] = picHead;
				param["content"] = content;
				var html = _.template(consultationRightTpl,param);
				$("#hd_msg_content").attr("data-id",num);
				$("#hd_msg_content").html(html);
				consultationXmpp.txClick();
				consultationXmpp.scrollHeight();
			
			},
			leftconsultationClick : function(){
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
//				//查看聊天记录
//				$(".talk_history_cl").unbind("click").on("click",function(){
//					var dataid = $("#hd_msg_content").attr("data-id");
//					var consultationList = null;
//					var param ={};
//					param["dataid"] = dataid;
//					param["NowtimeNew"] = publicToolJs.NowtimeNew;
//					consultationList = store.get("consultationList");
//					$.each(consultationList,function(k,v){
//						if(v.fromId == dataid){
//							param["contentList"] = v.content;
//							return;
//						  }
//					   })
//					var html = _.template(consultationStrOldTpl,param);
//					$("#txjilu").find("ul").eq(0).append(html);
//					$(this).unbind("click");
//				})
				
					$('#imgUploadDiv').off('change', '.hd_inputFile').on('change', '.hd_inputFile', function(e) {	

				})
				
				var gotoTime = 0;
				$("#goto_btn").unbind("click").on("click",function(){
					try{
					if(gotoTime == 0){
						gotoTime = 1;
					}else{
						alert("操作过快，请稍后!");
						return false;
					}
					var dataId = $(this).attr("data-id");
					var marketInfoMain = store.get("marketInfoMain");
					var consultationList = store.get("consultationList");
					var coList = new Array();
					$.each(consultationList,function(k,v){
						if(v.fromId == dataId && v.fromMarketId == marketInfoMain.marketId){
							coList.push(v);
						}
					})
				    var coId = coList[coList.length - 1].fromTo;
					var toId = coId+marketInfoMain["virtualName"];
					var timestamp = new Date().getTime();
					var inputValue = $("#inputStr").val();
					inputValue = inputValue.replace(/\n/g,"<br/>");  
					inputValue = $.trim(publicToolJs.valueReplace(inputValue)); 
					var queryParam = {"needCheckStr":inputValue};
					if(inputValue != null && $.trim(inputValue) != ""){
					var ptettr = "{\"msg_type\": \"2\",\"msg_code\": \"00\",\"msg_note\":\"" + marketInfoMain["uName"] + "\", \"msg_icon\":\"" + marketInfoMain["headPic"] + "\",\"msg_content\": \"" + inputValue + "\"}";
					var laiyuan = Strophe.xmlElement('id','',marketInfoMain["marketId"]+timestamp+publicToolJs.getRandom(999999));
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
				    var dataObj=null;
				    dataObj = eval("("+ptettr+")");
				    inputValue = publicToolJs.biaoqingchuli(inputValue);
				    dataObj.msg_content = inputValue;
				    dataObj["fromId"] = marketInfoMain["marketId"];
				    dataObj["time"] = timestamp;
					if(consultationList != null && consultationList != "" && consultationList != undefined){
						$.each(consultationList,function(k,v){
							if(v.fromId == dataId && v.fromMarketId == marketInfoMain.marketId){
								v.content.push(dataObj);
								return false;
							}
						})
						store.set("consultationList",consultationList);
					}else{
						var param = {};
						var contentList = new Array();
						var contentObj = new Array();
						contentList.push(dataObj);
						param["fromId"] = dataId;
						param["content"] = contentList;
						contentObj.push(param);
						store.set("consultationList",contentObj);
					
					}
				    
				var news = {};
					news["inputValue"] = inputValue;
					news["timestamp"] = publicToolJs.NowtimeNew(timestamp);
					news["headPic"] = marketInfoMain["headPic"] !="" && marketInfoMain["headPic"] != null ? marketInfoMain["headPic"] : marketInfoMain["morenPic"];
					var html = _.template(consultationMiddleTpl,news);
					$("#txjilu").find("ul").eq(0).append(html);
					$("#inputStr").val("");
					consultationXmpp.scrollHeight();
						}
						setTimeout(function(){gotoTime = 0;},2000);
					}catch(e){
						setTimeout(function(){gotoTime = 0;},2000);
					}	
				})
			},
			scrollHeight : function(){
				if($('#txjilu').length > 0){
					$('#txjilu').prop('scrollTop',$("#txjilu")[0].scrollHeight);
				}
			}
	}
	return consultationXmpp;
})
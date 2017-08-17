define(["commonSrc/frame/HuDong/publicTool",
		"commonSrc/frame/HuDong/orderXmpp",
		"commonSrc/frame/HuDong/stationXmpp",
		"commonSrc/frame/HuDong/consultationXmpp",
		"commonSrc/frame/HuDong/marketXmpp",
		 "text!commTpl/HuDong/newStrTpl/marketInfo.html", 
		 "text!commTpl/HuDong/newStrTpl/consultationInfo.html"
		],function(publicTool,orderXmpp,stationXmpp,consultationXmpp,marketXmpp,marketInfoTpl,consultationInfoTpl){   
	var newStr = {
			 refreshStr : function() {
					setInterval(newStr.jsXmpp,1500);
					setInterval(function(){
						marketXmpp.init();
						orderXmpp.init();
						stationXmpp.init();
						consultationXmpp.init();
					},60*1000);
				},
				jsXmpp : function(){
					var hdLength = $("#hd_msg_content").length;
						if(hdLength > 0){
							var marketInfoMain = store.get("marketInfoMain");
							var contentList = store.get("contentList");
							var consultationList = store.get("consultationList");
							var hdId = $("#hd_msg_content").attr("data-id");
							var param = {};
							var conList = new Array();
							var txLength = $("#txjilu > ul > li").length;
							var bool = 0;
							if(contentList != null && contentList.length > 0 && $(".hd_msg_content").attr("type") == "qy"){
								$.each(contentList != null?contentList :"",function(k,v){
									if(v.fromId == hdId && v.fromMarketId == marketInfoMain.marketId){
										if(txLength == v.content.length){
											bool = 1;
											return false;
										}
										$.each(v.content != null ? v.content:'',function(k2,v2){
											if((txLength-1) < k2){
												conList.push(v2);
											}
										})
										param["content"] = conList;
										return false;
									}
								})
								if(bool != 1 && conList.length > 0){
									param["num"] = hdId;
									param["NowtimeNew"] = publicTool.NowtimeNew;
									var html = _.template(marketInfoTpl,param);
									$("#txjilu").find("ul").eq(0).append($.trim(html));
									if($('#txjilu').length > 0){
										$('#txjilu').prop('scrollTop',$("#txjilu")[0].scrollHeight+300);
									}
								}
							}
							
							if(consultationList != null && consultationList.length > 0 && $(".hd_msg_content").attr("type") == "js"){
								conList.length = 0;
								$.each(consultationList != null?consultationList :"",function(k,v){
									if(v.fromId == hdId && v.fromMarketId == marketInfoMain.marketId){
										if(txLength == v.content.length){
											bool = 1;
											return false;
										}
										$.each(v.content != null ? v.content:'',function(k2,v2){
											if((txLength-1) < k2){
												conList.push(v2);
											}
										})
										param["content"] = conList;
										return false;
									}
								})
								if(bool != 1 && conList.length > 0){
									param["num"] = hdId;
									param["NowtimeNew"] = publicTool.NowtimeNew;
									var html = _.template(consultationInfoTpl,param);
									$("#txjilu").find("ul").eq(0).append($.trim(html));
									if($('#txjilu').length > 0){
										$('#txjilu').prop('scrollTop',$("#txjilu")[0].scrollHeight+300);
									}
								}
							}	
					}
				
				}
	}
	return newStr;
})
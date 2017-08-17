define(["text!commTpl/HuDong/stationTpl/stationMini.html",
        "text!commTpl/HuDong/stationTpl/stationInfo.html",
        "text!commTpl/HuDong/stationTpl/stationLeft.html",
        "text!commTpl/HuDong/stationTpl/stationMiddle.html",
        "text!commTpl/HuDong/stationTpl/stationInfoRight.html",
        "commonSrc/frame/HuDong/publicTool"
        ], function(stationMiniTpl,stationInfoTpl,stationLeftTpl,stationMiddleTpl,stationInfoRightTpl,publicToolJs) {
	var stationXmpp = {
			init : function(){
				stationXmpp.stationClick();
				var stationList = store.get("stationList");
				var marketInfoMain = store.get("marketInfoMain");
				var listParam = new Array();
				if(stationList != null && stationList != "" && stationList != undefined){
					$.each(stationList,function(k,v){
						if(v.fromMarketId == marketInfoMain.marketId){
							listParam.push(v);
						}
					})
					if(listParam.length > 0){
						var stationParam = {};
						stationParam["stationList"] = listParam;
						stationParam["stationType"] = stationXmpp.stationType;
						stationParam["DatetimeNew"] = publicToolJs.DatetimeNew;
						var html = _.template(stationMiniTpl,stationParam);
						$("#nbmsg").find("ul").html(html);
					}
				}
			},
			newStation : function(paramStation){
				var contentList = new Array();
				var list = new Array();
				list.push(paramStation);
				var param = {};
				param["codeId"] = paramStation.stationInfo.sub_msg_code;
				param["stationList"] = list;
				contentList.push(param);
				var param = {};
				param["stationList"] = contentList;
				param["DatetimeNew"] = publicToolJs.DatetimeNew;
				param["stationType"] = stationXmpp.stationType;
				var html = _.template(stationMiniTpl,param);
				$.each($("#nbmsg ul li"),function(k,v){
					if($(v).attr("data-id") == paramStation.stationInfo.sub_msg_code){
						$(v).remove();
						return false;
					}
				})
				$("#nbmsg").find("ul").append(html);
			},
			stationClick : function(){
				 	//点击显示事件
					$("#nbmsg").on("click","ul>li",function(){
						publicToolJs.removeCount(this);
						var param = {};
						var stationList = store.get("stationList");
						var marketInfoMain = store.get("marketInfoMain");
						var stationThis = new Array();
						var thisCodeId = $(this).attr("data-id");
							$.each(stationList,function(k,v){
								if(v.codeId == thisCodeId && v.fromMarketId == marketInfoMain.marketId){
									stationThis.push(v);
									$.each(v.stationList,function(k2,v2){
										if(v2.newStr == 1){
											v2.newStr = -1;
											return;
										}
									})
									return;
								}
							})
							store.set("stationList",stationList);
							param["stationId"] = $(this).attr("station-id");
							param["stationList"] = stationThis;
							param["codeId"] = thisCodeId;
							param["NowtimeNew"] = publicToolJs.NowtimeNew;
							param["stationType"] = stationXmpp.stationType;
						var html = _.template(stationInfoTpl,param);
						$("#huDongMain").html(html);
						stationXmpp.stationRightClick();
						stationXmpp.stationLeftClick();
						stationXmpp.stationTopClick();
						stationXmpp.fenye();
					})
					
					
			 },
			 stationTopClick : function(){
				$("#huDongMain").on("click",".gbck",function(){
					$("#huDongMain").attr("style","position: absolute;top: 30%;left: 25%;z-index: 99");
					$("#huDongMain").html("");
				}) 
			 },
			 stationRightClick : function(){
				 $("#rightHtml").on("click",".hd_hslt_list>li",function(){
					 $(this).find("b").removeClass("unread");
					 var stationid = $(this).attr("station-id");
					 var bool = 0;
					 $.each($("#hd_leftScr ul li"),function(k,v){
						 if($(v).attr("station-id") == stationid){
							 bool = 1;
							 return;
						 }
					 })
					 if(bool == 1){
						 return;
					 }
					 $("#hd_leftScr ul li").removeClass("hd_left_cur");
					 var stationId = $(this).attr("station-id");
					 var stationList = store.get("stationList");
					 var station2List = null;
					 var coId = $("#hd_msg_contentTS").attr("code-id");
					 var stationParam = {};
					 stationParam["stationId"] = stationId;
					 	$.each(stationList,function(k,v){
					 		if(v.codeId == coId){
					 			station2List = v;
					 			return;
					 		}
					 	})
						stationParam["stationList"] = station2List;
					 	stationParam["NowtimeNew"] = publicToolJs.NowtimeNew;
						var html = _.template(stationMiddleTpl,stationParam);
						$(".con_warp").html(html);
						var html = _.template(stationLeftTpl,stationParam);
						$("#hd_leftScr ul").append(html);
				 
				 
				 })
			 },
			 stationLeftClick : function(){
				 $("#hd_leftScr").on("click","ul>li>i",function(){
					 if($(this).parents("ul").find("li").length < 2){
						 $("#huDongMain").html("");
					 }else{
						 if($(this).parents("li").hasClass("hd_left_cur")){
							 var nodesiblings = $(this).parents("li").siblings()[0];
							 $(nodesiblings).addClass("hd_left_cur");
							 $(nodesiblings).find("p").trigger("click");
						 }
						 $(this).parents("li").remove();
					 } 
					 stationXmpp.stationInfoColorLeftRight();
				 })
				$("#hd_leftScr").on("click","ul>li>p",function(){
					 $("#hd_leftScr ul li").removeClass("hd_left_cur");
					 $(this).parent().addClass("hd_left_cur");
					 var stationId = $(this).parent().attr("station-id");
					 var stationList = store.get("stationList");
					 var coId = $("#hd_msg_contentTS").attr("code-id");
					 var stationParam = {};
					 stationParam["stationId"] = stationId;
					 	$.each(stationList,function(k,v){
					 		if(v.codeId == coId){
					 			stationList = v;
					 			return;
					 		}
					 	})
						stationParam["stationList"] = stationList;
					 	stationParam["NowtimeNew"] = publicToolJs.NowtimeNew;
					 var html = _.template(stationMiddleTpl,stationParam);
					 $(".con_warp").html(html);
				 })
			 },
			 fenye : function(){
				 $("#rightHtml").on("change",".fenye",function(){
					 var stationList = store.get("stationList");
					 var marketInfoMain = store.get("marketInfoMain");
					 var $this = $(this).val();
					 var thisCodeId = $("#hd_msg_contentTS").attr("code-id");
					 var station2List = new Array();
					 $.each(stationList,function(k,v){
						 if(v.codeId == thisCodeId && v.fromMarketId == marketInfoMain.marketId){
							 var stationList = v.stationList;
							 var stations = new Array();
							 for(var i = stationList.length - 1;i>=0;i--){
								 stations.push(stationList[i]);
							 }
						 $.each(stations,function(k2,v2){
							 if(($this - 1)*5 <= k2 && k2 < $this*5){
								 station2List.push(v2);
							 }
						 })
						 return;
					 }
				 })
				 var param = {};
					 param["stationList"] = station2List;
					 param["NowtimeNew"] = publicToolJs.NowtimeNew;
					 var html = _.template(stationInfoRightTpl,param);
					 $(".hd_hslt_list").html(html);
					 stationXmpp.stationInfoColorLeftRight();
			  })
			 },
			 stationInfoColorLeftRight : function(){
				 $.each($("#rightHtml .hd_hslt_list li"),function(k,v){
					 var bool = 0;
					$.each($("#hd_leftScr ul li"),function(k2,v2){
						if($(v2).attr("station-id") == $(v).attr("station-id")){
							$(v).find("b").removeClass("unread");
							bool = 1;
							return;
						}
					}) 
					if(bool != 1){
							$(v).find("b").addClass("unread");
					}
				 }) 
			 },
			 stationType : function(type){
					var stationName;
					switch(type)
					{
					case "20301":
						stationName = "互生消息";
					      break;
					case "20302":
						stationName = "业务消息";
						  break;
					case "20303":
						stationName = "服务公司消息";
					  	  break;
					case "20304":
						stationName = "管理公司消息";
						  break;
					}
					return stationName;
				 }
	}
	return stationXmpp;
})
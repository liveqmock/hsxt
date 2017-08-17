define(["text!hsec_goodslistTpl/goodHead.html"
        ,"text!hsec_goodslistTpl/goodsCheckHistory.html"
        ,"text!hsec_goodslistTpl/info.html"
        ,"hsec_goodslistDat/goodslistdat"
        ,"hsec_goodslistSrc/goodFood"
        ,"hsec_tablePointSrc/tablePoint"]
        ,function(tpl,tpl2,tpl3,ajaxRequest,goodFoodJs,tablePoint){
    var currentPageIndex = 1;
    var pageSize = 20;
    var type = 1;
    var optData = new Array();
    var requestType = type;
    var param = {"type":type,"requestType":requestType,"currentPageIndex":currentPageIndex,"eachPageSize":pageSize};
	var goodslist = {
		//初始化ComboxList :: add by zhanghh 20160224
		selectList : function(){
			var param={"pid":"-1"};
				CategorySelect(param,$("#pCategory"));
				$("#pCategory").change(function(){
					if($(this).attr("optionvalue")!=null&&$(this).attr("optionvalue")!=""){
						$("#sCategory").val("");
						var param={"pid":$(this).attr("optionvalue")};
						CategorySelect(param,$("#sCategory"));
					}
				});
				function CategorySelect(param,select){
					ajaxRequest.getCategorySelectData(param,function(response){
						if (response.retCode == "200") {
							var opts = new Array();
							opts[0] = {name:"-请选择-",value:""};
							$.each(response.data,function(key,value){
								opts[key+1] = {name:value.categoryName,value:value.id}
							});
							$(select).selectList({
								options:opts,
								width:115
							});
						}
					});
				}
				$("#status").selectList({options:[{name:'待审核',value:'1',selected:true}]});
		},
		//tab事件
		tabClick : function(){
			$("#tab01").click(function(){
				$(this).children('a').addClass("active").parent().siblings().children().removeClass("active");
				var param={"type":1};
				param.eachPageSize = pageSize;
				param["requestType"] = 1;
				type = param.type;
				$(".alone_row").show();
				//$("#status").html("<option value='1'>待审核</option>");
				$("#status").selectList({options:[{name:'待审核',value:'1',selected:true}]});
				goodslist.bindData(param);
			});
			$("#tab02").click(function(){
				$(this).children('a').addClass("active").parent().siblings().children().removeClass("active");
				var param={"type":2};
				param.eachPageSize = pageSize;
				param["requestType"] = 2;
				type = param.type;
				$(".alone_row").hide();
				//$("#status").html("<option value=''>全部</option><option value='1' >未处理</option><option value='2'>已处理</option>");
				$("#status").selectList({options:[{name:"全部",value:"",selected:true},{name:"未处理",value:"1"},{name:"已处理",value:"2"}]});
				goodslist.bindData(param);
			});
			$("#tab03").click(function(){
				$(this).children('a').addClass("active").parent().siblings().children().removeClass("active");
				var param={"type":3};
				param.eachPageSize = pageSize;
				param["requestType"] = 3;
				type = param.type;
				$(".alone_row").hide();
				//$("#status").html("<option value='-1'>审核失败</option>");
				$("#status").selectList({options:[{name:"审核失败",value:"-1",selected:true}]});
				goodslist.bindData(param);
			});
			$("#tab04").click(function(){
				$(this).children('a').addClass("active").parent().siblings().children().removeClass("active");
				var param={"type":4};
				param.eachPageSize = pageSize;
				param["requestType"] = 4;
				type = param.type;
				$(".alone_row").hide();
				//$("#status").html("<option value='3'>已上架</option>");
				$("#status").selectList({options:[{name:"已上架",value:"3",selected:true}]});
				goodslist.bindData(param);
			});
			$("#tab05").click(function(){
				$(this).children('a').addClass("active").parent().siblings().children().removeClass("active");
				var param={"type":5};
				param.eachPageSize = pageSize;
				param["requestType"] = 5;
				type = param.type;
				$(".alone_row").hide();
				//$("#status").html("<option value='-2'>已下架</option>");
				$("#status").selectList({options:[{name:"已下架",value:"-2",selected:true}]});
				goodslist.bindData(param);
			});
		},
		//数据初始化
		initData : function(){
			$("#busibox").html(_.template(tpl,{"type":param.type}));
			//$("#status").html("<option value='1'>待审核</option>");
			goodslist.selectList();
			goodslist.bindData(param);
			goodslist.tabClick();
			$("#tab01").trigger("click");//第一次加载时触发一下，不然分页无效  add by zhanghh 20160512
			//全选/反选
			$("#selectAll").change(function(){
				var isChedked=$("#selectAll").get(0).checked;
					$("[name='checkbox']").each(function(){
						if(isChedked){
							$(this).get(0).checked=true;
						}else{
							$(this).get(0).checked=false;
						}
				    })
			});
		},
		bindData:function(param){
		   gridObj = $.fn.bsgrid.init('goodlist',{
				url:{url:comm.UrlList['getItemManager'],domain:'scs'},
				//pageSizeSelect: true,
                pageSize: pageSize,
                otherParames:$.param(param),
                stripeRows: true,  //行色彩分隔 
				displayBlankRows: false,   //显示空白行
				lineWrap:false,//自动换行
				operate: {
					add : function(record, rowIndex, colIndex, options){
						if(colIndex==0){return '<input type="checkbox" name="checkbox" itemId="'+record.id+'" vShopId="'+record.virtualShopId+'" />'}
						if(colIndex==1){return _.template(tpl3,record)}
						if(colIndex==2){return record.salerId}
						if(colIndex==3){return record.virtualShopName}
						if(colIndex==4){return record.brandName}
						if(colIndex==5){return '<span class="fb red">'+record.lowPrice.toFixed(2)+' ~ '+record.highPrice.toFixed(2)+'</span>'}
						if(colIndex==6){return '<span class="fb blue">'+record.lowAuction.toFixed(2)+' ~ '+record.highAuction.toFixed(2)+'</span>'}
						if(colIndex==7){return record.statusName}
						if(colIndex==8){
							sTpl = '<a href="javascript:void(0)" data-url="'+'\?'+record.itemPreviewUrl+'" status="'+record.status+'" class="ckspxq" data-id="'+rowIndex+'">查看商品详情</a>';
						if(type==1){
                   			sTpl += '| <a href="javascript:void(0)" class="review" data-id="'+rowIndex+'">审核</a>';
                  		  } 
                  		if(type==1 || type==3 || type==4){ 
		                  	sTpl += '| <a href="javascript:void(0)" class="review_record" data-id="'+rowIndex+'">查看审核记录</a>';
		                  }
		                  if(type==4){
		                	sTpl += '| <a href="javascript:void(0)" class="soldOff" data-id="'+rowIndex+'">商品下架</a>';//正常下架
		                  }
		                  if(requestType==2){
		                  	if(record.status==3){
			                  sTpl += '| <a href="javascript:void(0)" class="soldOut" data-id="'+rowIndex+'">商品下架</a>'//举报下架
			                  }else if(record.status=="-2"){
			                  	sTpl += '| <a href="javascript:void(0)" style="color: black;" data-id="'+rowIndex+'">商品已下架</a>'
			                  }else if(record.status=="-3"){
			                  	sTpl += '| <a href="javascript:void(0)" style="color: black;" data-id="'+rowIndex+'">商品违规下架</a>'
			                  }else if(record.status=="-4"){
			                  	sTpl += '| <a href="javascript:void(0)" style="color: black;" data-id="'+rowIndex+'">商品已删除</a>'
			                  }
		                  }
							return sTpl;
						}
					}
				},
				complete : function(e,o){
					var other = eval("("+e.responseText+")").orther;
					if(other){
						type = other.type;
						requestType = other.requestType;
						httphtml = other.itemDomianUrl;
					}
					//添加超链接路径
					$(".ckspxq").each(function(){
						var src = $(this).attr("data-url");
						$(this).attr("url",httphtml+src);
					});	
					$(".soldOut").each(function(i){
						//$(this).attr("complainid",other.complainIDList[i].id);
					});
					//查看商品详情
					$(".ckspxq").click(function(){
						window.open($(this).attr("url"));
					});
					goodslist.showCheckDetail();
					goodslist.soldOffClick();
					goodslist.soldOnClick();
					goodslist.reviewClick();
					goodslist.batchCheckClick();
					goodslist.batchCheckNoClick();
					goodslist.queryHeadClick();
				}
			});
		},
		queryHeadClick:function(){
			//编号输入验证
			$("#salerId").on('focus',function(){
				$(this).on('keyup',function(){
					var num = $(this).val();
					var p1=/^[\d]+$/;
					if(!p1.test(num)){
						$(this).val("");
					}
				})
			});
			$('#itemSearchBtn').unbind().on('click',function(){
				var param = {
					"salerId":$("#salerId").val(),
					"brandName":$("#brandName").val(),
					"categoryParentId":$("#pCategory").attr("optionvalue"),
					"categoryId":$("#sCategory").attr("optionvalue"),
					"type":type,
					"requestType":type,
					"status":$("#status").attr("optionvalue")};
				goodslist.bindData(param);
			});
		},
		//查看审核记录
		showCheckDetail:function(){
			$('.review_record').unbind().on('click',function(){
				var paramObj = gridObj.getRecord($(this).attr("data-id"));
				var param={"itemId":paramObj.id};
				ajaxRequest.itemCheckHistory(param,function(response){
					if(response.retCode==200){
						var html = _.template(tpl2, response);
						$('#review_record').html(html);
						$("#review_record").dialog({
							show: true,
							modal: true,
							title:"审核记录",
							width:600,
							buttons: {					  
							  '关闭': function() {$( this ).dialog( "close" );}		
							}
						 });
					}else if(response.retCode==201){
						tablePoint.tablePoint("对不起，请求 失败");
					}else if(response.retCode==212){
						tablePoint.tablePoint("登录已过期，请重新登录，谢谢");
					}
				}) 
			});
		},
		//商品正常下架事件
		soldOffClick : function(){
			$('.soldOff').unbind().on('click',function(){
				var paramObj = gridObj.getRecord($(this).attr("data-id"));
				var itemId=paramObj.id;
				var virtualshopid=paramObj.virtualShopId;
				$("#msgDiv").dialog({
					show: true,
					modal: true,
					title:"正常下架",
					width:350,
					open : function() { $("#msgDiv").html("<center style='margin-top:20px;color:red;font-size:14px;'>确定要下架商品吗？</center>"); },
					buttons : {
						"下架":function(){
						ajaxRequest.itemPutOff(itemId,virtualshopid,function(response){
							if(response.retCode==200){
								//goodslist.bindData(param);
								tablePoint.tablePoint("下架成功，商品已下架");
								var param={"type":4};
								goodslist.bindData(param);
								//	goodslist.initData();
							}else if(response.retCode==201){
								tablePoint.tablePoint("下架失败");
							}else if(response.retCode==212){
								tablePoint.tablePoint("登录已过期，请重新登录，谢谢");
							}
						});
						$("#msgDiv").dialog("destroy");
						},
						"取消":function(){
							$(this).dialog("destroy");
						}
					}
				});
			});
		},
		//商品正常上架事件
		soldOnClick : function(){
			$(".soldOn").click(function(){
				var paramObj = gridObj.getRecord($(this).attr("data-id"));
				var itemId=paramObj.id;
				var virtualshopid=paramObj.virtualShopId;
				ajaxRequest.itemPutOn(itemId,virtualshopid,function(response){
					if(response.retCode==200){
						goodslist.bindData(param);
						tablePoint.tablePoint("上架成功，商品已上架");
					}else if(response.retCode==201){
						tablePoint.tablePoint("上架失败");
					}else if(response.retCode==212){
						tablePoint.tablePoint("登录已过期，请重新登录，谢谢");
					}
				});
			});
		},
		//举报下架
		soldOutClick : function(){
			$(".soldOut").unbind.on('click',function(){
				var paramObj = gridObj.getRecord($(this).attr("data-id"));
				var itemId=paramObj.id;
				var virtualshopid=paramObj.virtualShopId;
				var complainid=$(this).attr("complainid");
				 $("#soldOut").dialog({
						show: true,
						modal: true,
						title:"商品下架",
						width:350,
						buttons: {
							"保存":function(){
								var remark=$(this).find("textarea[id='soldOut']").val();
								ajaxRequest.soldOut(itemId,virtualshopid,remark,complainid,function(response){
									if(response.retCode==200){
										goodslist.bindData(itemSearchParam);
										tablePoint.tablePoint("处理成功，商品已下架");
									}else if(response.retCode==201){
										tablePoint.tablePoint("处理失败");
									}else if(response.retCode==212){
										tablePoint.tablePoint("登录已过期，请重新登录，谢谢");
									}
									
								})
								$("#soldOut").dialog("destroy");
							},
							"取消":function(){
								$(this).dialog("destroy");
							}
						}		
				 })
			});
		},
		//商品审核事件
		reviewClick : function(){
			$('.review').unbind().on('click',function(){
				var paramObj = gridObj.getRecord($(this).attr("data-id"));
				var type;
				$(".tabList").children().each(function(i){
					var a_class=$(this).children().attr("class");
					if(a_class=="active"){
						type=i+1;
					}
				});
				var itemId=paramObj.id;
				var virtualshopid=paramObj.virtualShopId;
				$("#review").find("textarea").val("")
				 $("#review").dialog({
					show: true,
					modal: true,
					title:"审核商品",
					width:350,
					closeIcon:true,
					buttons: {					  
					  '审核通过': function() {
						  var remark=$(this).find("textarea").val();
						  if(remark.length>=100){
							  alert("审核说明内容限制为100文字");
							  return;
						  }
						  var param={"itemId":itemId,"virtualShopId":virtualshopid,"remark":remark,"checkResult":1};
						  $(this).dialog("close");
							ajaxRequest.itemCheck(param,function(response){
								if(response.retCode==200){
									tablePoint.tablePoint("审核成功，商品已上架");
									var param={"type":type};
									//goodslist.bindData(param);
									goodslist.initData();
								}else if(response.retCode==201){
									tablePoint.tablePoint("审核失败");
								}else if(response.retCode==212){
									tablePoint.tablePoint("登录已过期，请重新登录，谢谢");
								}else if(response.retCode==12315){
									tablePoint.tablePoint("申请已取消");
								}
							})
					  },
					  '审核不通过': function() {
						  var remark=$(this).find("textarea").val();
						  var param={"itemId":itemId,"virtualShopId":virtualshopid,"remark":remark,"checkResult":0};
						  $(this).dialog("close");
						ajaxRequest.itemCheck(param,function(response){
							if(response.retCode==200){
								var param={"type":type};
								//goodslist.bindData(param);//页面reload
								goodslist.initData();
							}else if(response.retCode==201){
								tablePoint.tablePoint("审核不通过失败");
							}else if(response.retCode==212){
								tablePoint.tablePoint("登录已过期，请重新登录，谢谢");
							}
						})
					  }		
					}
				 });
			});
		},
		//批量审核通过弹框
		batchCheckClick : function(){
			$( "#tg" ).click(function(){
				var params=[];
				var hasChecked=false;
				$("[name='checkbox']").each(function(){
					if($(this).get(0).checked){
						hasChecked=true;
						var itemId=$(this).attr("itemId");
						var vShopId=$(this).attr("vShopId");
						var param={"itemId":itemId,"virtualShopId":vShopId,"checkResult":1};
						params.push(param);
					}
			    });
				//判断选中的是哪个tab
				var type;
				$(".tabList").children().each(function(i){
					var a_class=$(this).children().attr("class");
					if(a_class=="active"){
						type=i+1;
					}
				});
				if(hasChecked){
					$(".tg").dialog({
						show: true,
						modal: true,
						title:"批量审核通过提醒",
						width:400,
						buttons: {
							'确认': function() {
								ajaxRequest.bitchItemCheck("itemCheckJson="+JSON.stringify(params),function(response){
									if(response.retCode==200){
										tablePoint.tablePoint("审核成功，商品已上架");
										var param={"type":type};
										//goodslist.bindData(param);//页面reload
										goodslist.initData();
									}else if(response.retCode==201){
										tablePoint.tablePoint("审核失败");
									}else if(response.retCode==212){
										tablePoint.tablePoint("登录已过期，请重新登录，谢谢");
									}
								})
								$(".tg").dialog("destroy");
							},
							'取消': function() {$(".tg").dialog("destroy");}
						}
					});
				}else{
					$(".ts").dialog({
						show: true,
						modal: true,
						title:"提示",
						width:400,
						buttons: {
							'关闭': function() {$(".ts").dialog("destroy");}
						}
					});
				}
			});
		},
		//批量审核不通过弹框
		batchCheckNoClick:function(){
			$( "#btg" ).click(function() {
				var params=[];
				var hasChecked=false;
				$("[name='checkbox']").each(function(){
					if($(this).get(0).checked){
						hasChecked=true;
						var itemId=$(this).attr("itemId");
						var vShopId=$(this).attr("vShopId");
						var param={"itemId":itemId,"virtualShopId":vShopId,"checkResult":0};
						params.push(param);
					}
			    });
				//判断选中的是哪个tab
				var type;
				$(".tabList").children().each(function(i){
					var a_class=$(this).children().attr("class");
					if(a_class=="active"){
						type=i+1;
					}
				});
				if(hasChecked){
					$(".btg").dialog({
						show: true,
						modal: true,
						title:"批量审核不通过提醒",
						width:400,
						buttons: {
							'确认': function() {
								ajaxRequest.bitchItemCheck("itemCheckJson="+JSON.stringify(params),function(response){
									if(response.retCode==200){
										var param={"type":type};
										//goodslist.bindData(param);//页面reload
										goodslist.initData();
									}else if(response.retCode==201){
										tablePoint.tablePoint("审核失败");
									}else if(response.retCode==212){
										tablePoint.tablePoint("登录已过期，请重新登录，谢谢");
									}
								})
								$(".btg").dialog("destroy");
							},
							'取消': function() {$(".btg").dialog("destroy");}
						}
					});
				}else{
					$(".ts").dialog({
						show: true,
						modal: true,
						title:"提示",
						width:400,
						buttons: {
							'关闭': function() {$(".ts").dialog("destroy");}
						}
					});
				}
			});
		}
	};
	return goodslist;
});
define(["hsec_goodsFoodDat/goodsFood"
		,"hsec_goodsFoodSrc/category"
		,"hsec_tablePointSrc/tablePoint"
		,"text!hsec_goodsFoodTpl/foodHead.html"
		,"text!hsec_goodsFoodTpl/info.html"],function(ajaxRequest,categoryJs,tablePoint,headHtml,infoHtml){
    var _virtualShopId = "";
    var gridObj;
    var currentPageIndex = 1;
    var param = {"firstRecordIndex":currentPageIndex,"eachPageSize":10};
	var goodsFood = {
		//初始化ComboxList :: add by zhanghh 20160223
		selectList : function(){
			$("#selectStatus").selectList({
				options:[
                    {name:'全部',value:''},
					{name:'已上架',value:'3'},
					{name:'已下架',value:'0'}
				]
			});
		},
		//构建查询参数
		queryParam : function(){
			param.id = $("#itemId").val();
			param.name = $("#itemName").val();
			param.categoryId = $("#categoryId").val();
			param.status = $("#selectStatus").attr("optionvalue");
			param.companyResourceNo = $("#companyResourceNo").val();
			return param;
		},
		bindData : function(){
			$('#busibox').html(headHtml);
			var param = goodsFood.queryParam();
				goodsFood.initTableData(param);
				goodsFood.bindSelectData();
				goodsFood.initComboxClick();
				goodsFood.selectList();
		},
		//菜品分类加载
		bindSelectData : function() {
			ajaxRequest.queryCategory({}, function(response) {
				$.each(response.data, function(key, val) {
                    $("#foodItemType").append('<option value="' + val.id + '">' + val.categoryName + '</option>');
                });
			})
		},
		initComboxClick : function(){
			$("#gArrow,#categoryName").click(function(){
				categoryJs.bindData(function(){
					//var offset = $("#categoryName").offset();
					//$(".goodsCatalogue").attr("style","left:"+offset.left+"px;top:"+(offset.top+33)+"px")
				});
			});
		},
		//初始化表格
		initTableData : function(param){
			gridObj = $.fn.bsgrid.init('goodsfood',{
				url:{url:comm.UrlList['listItemInfoFood'],domain:'scs'},
				otherParames:$.param(param),
				pageSizeSelect: true,
				pageSize:10,
				stripeRows:true,//隔行变色
				displayBlankRows:false,//是否显示空白行
				operate:{
					add : function(record, rowIndex, colIndex, options){
						_virtualShopId = record.virtualShopId;
						if(colIndex==0){return _.template(infoHtml, record) }
						if(colIndex==1){
							var taste = record.taste;
							var str = "";
							try{
								if($.trim(taste)!="" && taste!=undefined){
									taste = eval("("+taste+")");
									$.each(taste,function(key,t){
										str += t.value+" ";
									});
								}
							}catch(e){
								if($.trim(taste)!="" && taste!=undefined){
									str+=taste;
								}
							}
							return str;
						}
						if(colIndex==2){
							if(record.price != null && record.price != ""){
				               		return '<span class="fb red">'+record.price.toFixed(2)+'</span>'
				               	}else{
				               		var priceArr = new Array();
				               			priceArr = record.priceRegion.split(",");
				               		if(priceArr.length>1){
				               		return '<span class="fb red">'+priceArr[0]+'~'+priceArr[1]+'</span>'
				               	}else{
				               		return '<span class="fb red">'+record.priceRegion+'</span>'
				               	} 
				            }
						}
						if(colIndex==3){
							if(record.ishaspv!=1){
								if(record.pv != null && record.pv != ""){
					           		return '<span class="fb blue">'+record.pv.toFixed(2) +'</span>'	
					            }else{
					            	var pvArr = new Array();
					            		pvArr = record.pvRegion.split(",");
					            	if(pvArr.length >1){
					       				return '<span class="fb blue">'+pvArr[0]+'~'+pvArr[1]+'</span>'
						            }else{
						            	return '<span class="fb blue">'+record.pvRegion +'</span>'
						            }
					            }
							}
						}
						if(colIndex==4){ return record.createTime }
						if(colIndex==5){
							if(record.status==0){
								return '<span>已下架</span>'
							}else if(record.status==3){
								return '<span>已上架</span>'
							}
						}
						if(colIndex==6){
							sTpl = '';//'<a href="#" class="caozuo lh24 delsingleItem" data-id="'+rowIndex+'">删除</a>'
							/*if(record.status=='0'){
						     	sTpl += '	|	<a  href="#" class="caozuo lh24 putaway" data-id="'+rowIndex+'">上架</a>'
						    }*/
						    if(record.status=='3'){//	|	
						        sTpl += '<a  href="#" class="caozuo lh24 soldout" data-id="'+rowIndex+'">下架</a>'
						    }
					     	return sTpl;
					     }
					}
				},
				complete:function(o,e,c){
					var other = eval("("+o.responseText+")").orther;
						if(other!=null){
							var https = other.httpUri;
							$(".imgSrc").each(function(){
								var src = $(this).attr("data-src");
								var ishaspv = $(this).attr("data-ishaspv");
								if(ishaspv!=1 && src){
									$(this).attr("src",https+src);
								}else{
									$(this).attr("src","resources/img/item_df.png");
								}
							});
						}
					goodsFood.bindSearchClick();//查询
					goodsFood.checkAllClick();//全选
					//goodsFood.deleteSingleClick();//单个删除
					//goodsFood.deleteBatchClick();//批量删除
					//goodsFood.batchPutawayClick();//批量上架
					goodsFood.batchSoldOutClick();//批量下架
					//goodsFood.singlePutawayClick();//上架
					goodsFood.singleSoldOutClick();//下架
				}	
			 });
			//自定义分页栏按钮
			var buttonHtml = '<td style="text-align: left;">';
	        buttonHtml += '<table><tr>';
	        buttonHtml += '<td><div style="float:left">全选</div></td>';
	        buttonHtml += '<td><div class="std_checkboxqx"><input name="" type="checkbox" value="" id="all"/></div></td>';
	        //buttonHtml += '<td><div id="batchDeletData" class="splb_sclb"><a href="javascript:;">批量删除</a></div></td>';
	        //buttonHtml += '<td><div id="batchPutOn" class="splb_sclb" data-virtual-shop-id="'+_virtualShopId+'"><a href="javascript:;">批量上架</a></div></td>';
	        buttonHtml += '<td><div id="batchPutOff" class="splb_sclb" data-virtual-shop-id="'+_virtualShopId+'"><a href="javascript:;">批量下架</a></div></td>';
	        buttonHtml += '</tr></table>';
	        buttonHtml += '</td>';
	        $('#' + gridObj.options.pagingOutTabId + ' tr:eq(0)').prepend(buttonHtml);
		},
		//查询事件
		bindSearchClick : function(){
			//编号输入验证
			$("#itemId,#companyResourceNo").on('focus',function(){
				$(this).on('keyup',function(){
					var num = $(this).val();
					var p1=/^[\d]+$/;
					if(!p1.test(num)){
						$(this).val("");
					}
				})
			});
			$("#searchItem").unbind().on("click",function(){
				var param = goodsFood.queryParam();
				goodsFood.initTableData(param);
			});
		},
		//全选事件
		checkAllClick : function(){
			//全选
			tablePoint.checkBoxAll($("#all"),$(".std_checkbox input[type='checkbox']"));
		},
		//单个删除
		deleteSingleClick : function(){
			$(".delsingleItem").unbind().on("click",function(){
				var paramObj = gridObj.getRecord($(this).attr("data-id"));
				var itemId = paramObj.id;
				var itemStatus = paramObj.status;
				var param = {"itemId":itemId};
				tablePoint.tablePoint("确定要删除吗？",function(){
					ajaxRequest.deleteItemInfoFood(param,function(response){
						if(response.retCode == 200){
							$("#searchItem").click();
						}
					});
				});
			});
		},
		//批量删除
		deleteBatchClick : function(){
			$("#batchDeletData").unbind().on("click",function(){
				var itemIds = "";
				$("input[name='itemDataCheckBox']:checked").each(function(){
					itemIds += $(this).attr("value") +",";
				});
				itemIds = itemIds.substring(0,itemIds.length - 1);//去掉最后一个逗号
				var param = {"itemIds":itemIds};
				if($("input[name='itemDataCheckBox']:checked").length <= 0){
					tablePoint.tablePoint("请选择商品");
				}else{
					ajaxRequest.deleteItemFoodByIds(param,function(response){
						if(response.retCode == 200){
							$("#searchItem").click();
						}
					});
				}
			});
		},
		//单个上架
		singlePutawayClick : function(){
			$(".putaway").unbind().on("click",function(){
				var paramObj = gridObj.getRecord($(this).attr("data-id"));
				var itemId = paramObj.id;
				var itemStatus = paramObj.status;
				var virtualShopId = paramObj.virtualShopId;
				var param = {"itemId":itemId,"virtualShopId":virtualShopId};
				ajaxRequest.itemPutOnFood(param,function(response){
					if(response.retCode == 200){
						tablePoint.tablePoint(response.msg,function(){
							$("#searchItem").click();
						});
					}else{
						tablePoint.tablePoint(response.msg);
					}
				});
			});
		},
		//单个下架
		singleSoldOutClick :function(){
			$(".soldout").unbind().on("click",function(){
				var paramObj = gridObj.getRecord($(this).attr("data-id"));
				var itemId = paramObj.id;
				var itemStatus = paramObj.status;
				var virtualShopId = paramObj.virtualShopId;
				var param = {"itemId":itemId,"virtualShopId":virtualShopId};
				$("#msgBox").dialog({
					show: true,
					modal: true,
					title:"菜品下架",
					width:350,
					open : function() { $("#msgBox").html("<center style='margin-top:20px;color:red;font-size:14px;'>确定要下架菜品吗？</center>"); },
					buttons : {
						"下架":function(){
						ajaxRequest.itemPutOffFood(param,function(response){
							if(response.retCode == 200){
								tablePoint.tablePoint('设置菜品下架成功！',function(){
									$("#searchItem").click();
								});
							}else{
								tablePoint.tablePoint('设置菜品下架失败！');
							}
						});
						$("#msgBox").dialog("destroy");
						},
						"取消":function(){
							$(this).dialog("destroy");
						}
					}
				});
			});
		},
		//批量上架
		batchPutawayClick : function(){
			$("#batchPutOn").unbind().on("click",function(){
				var itemIds = "";
				var flag = false;
				$("input[name='itemDataCheckBox']:checked").each(function(){
					itemIds += $(this).attr("value") +",";
					 if($(this).attr("status")!='0'){
						  flag = true; 
					  }
				});
				itemIds = itemIds.substring(0,itemIds.length - 1);//去掉最后一个逗号
				var virtualShopId = $(this).attr("data-virtual-shop-id");
				var param = {"virtualShopId":virtualShopId,"itemIds":itemIds};
				if($("input[name='itemDataCheckBox']:checked").length <= 0){
					tablePoint.tablePoint("请选择商品");
				}else{
				  if(flag){
					  tablePoint.tablePoint("所选择记录中含有不能批量上架的记录！")
				  } else { 
					ajaxRequest.batchItemPutOn(param,function(response){
						if(response.retCode == 200){
						tablePoint.tablePoint(response.msg,function(){
							$("#searchItem").click();
							});
						}else{
							tablePoint.tablePoint(response.msg);
						}
					});
					}
				}
			});
		},
		//批量下架
		batchSoldOutClick : function(){
			$("#batchPutOff").unbind().on("click",function(){
				var itemIds = "";
				var flag = false;
				$("input[name='itemDataCheckBox']:checked").each(function(){
					itemIds += $(this).attr("value") +",";
					if($(this).attr("status")!='3'){
						  flag = true; 
					  }
				});
				itemIds = itemIds.substring(0,itemIds.length - 1);//去掉最后一个逗号
				var virtualShopId = $(this).attr("data-virtual-shop-id");
				var param = {"virtualShopId":virtualShopId,"itemIds":itemIds};
				if($("input[name='itemDataCheckBox']:checked").length <= 0){
					tablePoint.tablePoint("请选择商品");
				}else{
					if(flag){
					  	tablePoint.tablePoint("所选择记录中含有不能批量下架的记录！");
					  } else {
						ajaxRequest.batchItemPutOff(param,function(response){
							if(response.retCode == 200){
							tablePoint.tablePoint('批量下架菜品成功！',function(){
								$("#searchItem").click();
								});
							}else{
								tablePoint.tablePoint('批量下架菜品失败！');
							}
						});
					}
				}
			});
		}
		
	};
	return goodsFood;
});
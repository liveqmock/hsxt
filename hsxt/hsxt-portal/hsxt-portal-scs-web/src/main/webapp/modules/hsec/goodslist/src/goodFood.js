define(["hsec_tablePointSrc/tablePoint"
		,"hsec_goodslistDat/goodslistdat"
		,"hsec_goodslistSrc/category"
        ,"text!hsec_goodslistTpl/food/foodHead.html"
        ,"text!hsec_goodslistTpl/food/info.html"]
        ,function(tablePoint,ajaxRequest,categoryJs,foodHead,info){
    var _virtualShopId = "";
    var gridObj;
    var currentPageIndex = 1;
    var param = {"firstRecordIndex":currentPageIndex,"eachPageSize":10};
	var goodFood = {
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
			return param;
		},
		//绑定数据
		bindGoodFoodData : function(){
			$("#busibox").html(foodHead);
			goodFood.selectList();
			var param = goodFood.queryParam();
			goodFood.initTableData(param);
			goodFood.bindSelectData();
			goodFood.initComboxClick();
			goodFood.bindSearchClick();
		},
		//菜品分类加载
		bindSelectData : function() {
			ajaxRequest.queryCategory({}, function(response) {
				$.each(response.data, function(key, val) {
                    $("#foodItemType").append('<option value="' + val.id + '">' + val.categoryName + '</option>');
                });
			})
		},
		//combox Click
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
			gridObj = $.fn.bsgrid.init('goodfood',{
				url:{url:comm.UrlList['listItemInfoFood'],domain:'scs'},
				pageSizeSelect: true,
                pageSize: 10,
                otherParames:$.param(param),
                stripeRows: true,  //行色彩分隔 
				displayBlankRows: false,   //显示空白行
				operate: {
					add : function(record, rowIndex, colIndex, options){
						var data = gridObj.getRecord(rowIndex);
						_virtualShopId = data.virtualShopId;
						if(colIndex==0){ return _.template(info, data);}
						if(colIndex==1){ 
							var str = "";
							// add by liaoc, 20160429, 添加异常捕获机制,解决data.taste数据异常导致的问题
							try{
								// add by liaoc, 20160426, 添加非空判断，解决页面JS报 SyntaxError:Unexpected token的问题
								if(data.taste != null && data.taste != ""){
									var kw = eval("("+data.taste+")");
									$.each(kw, function(k,v){
										str += v.value+",";
									});
								}
							}catch(e){
								if(data.taste != null && data.taste != ""){
									str += data.taste;
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
				               		return '<span class="fb red">'+record.priceRegion +'</span>'
				               	} 
				            }
						}
						if(colIndex==3){
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
						if(colIndex==4){ return data.createTime;}
						if(colIndex==5){ 
							if(data.status==0){
								return '<span>已下架</span>'
						    }else if(data.status==3){
								return '<span>已上架</span>'
							}
						}
						if(colIndex==6){
							 sTpl = '';//'<a href="#" class="caozuo lh24 delsingleItem" data-id="'+rowIndex+'">删除</a>';
							 /*if(data.status=='0'){
						        sTpl += '	|	<a  href="#" class="caozuo lh24 putaway" data-id="'+rowIndex+'">上架</a>';
						      }*/
						     if(data.status=='3'){//	|	
						       sTpl += '<a  href="#" class="caozuo lh24 soldout" data-id="'+rowIndex+'">下架</a>';
						      }
						   return sTpl;
						}
					}
				},
				complete : function(e,o){
					var other = eval("("+e.responseText+")").orther;
					if(other){
						https = other.httpUri;
					}
					$(".imgsrc").each(function(){
						var src = $(this).attr("data-src");
						$(this).attr("src",https+src);
					});	//添加图片
					goodFood.checkAllClick();//全选
					//goodFood.deleteSingleClick();//单个删除
					//goodFood.deleteBatchClick();//批量删除
					//goodFood.batchPutawayClick();//批量上架
					goodFood.batchSoldOutClick();//批量下架
					//goodFood.singlePutawayClick();//上架
					goodFood.singleSoldOutClick();//下架
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
			$("#itemId").on('focus',function(){
				$(this).on('keyup',function(){
					var num = $(this).val();
					var p1=/^[\d]+$/;
					if(!p1.test(num)){
						$(this).val("");
					}
				})
			});
			$("#searchItem").unbind().on("click",function(){
				var param = goodFood.queryParam();
				goodFood.initTableData(param);
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
				var param = {"itemId":paramObj.id};
				tablePoint.tablePoint("确定要删除吗？",function(){
					ajaxRequest.deleteItemInfoFood(param,function(response){
						if(response.retCode == 200){
							$("#searchItem").click();
						}
					});
				});//modifly by zhanghh 20160223
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
				var param = {"itemId":paramObj.id,"virtualShopId":paramObj.virtualShopId};
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
				var param = {"itemId":paramObj.id,"virtualShopId":paramObj.virtualShopId};
				ajaxRequest.itemPutOffFood(param,function(response){
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
	
	return goodFood;
});
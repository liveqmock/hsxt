
define([ "text!hsec_shippingListTpl/shippingList.html"
        ,"text!hsec_shippingListTpl/tab.html"
        ,"text!hsec_shippingListTpl/gridMain.html"
        ,"text!hsec_shippingListTpl/shippingList/shippingTable.html"
        ,"hsec_shippingListDat/shippingList"
        ,"hsec_shippingListSrc/shippingAdd/shippingAdd"
        ,"hsec_shippingListSrc/shippingDelete/shippingDelete"
        ,"hsec_shippingListSrc/shippingUpdate/shippingUpdate"
        ,"hsec_tablePointSrc/tablePoint"], function(tpl,tabTpl,gridMainTpl,tpl2,ajax,ajax2,ajax3,ajax4,tablePoint) {
	
	var paramList;
	var param = {"currentPageIndex":1};
	var shop = {
		queryParam : function(currentPageIndex) {
			param = {"eachPageSize":20};
			//param["currentPageIndex"] = currentPageIndex;
			return param;
		},
		bindData : function() {
			//$('.operationsArea').html(tpl);
			//shop.bindTable();
			$("#busibox").html(_.template(gridMainTpl));
			shop.loadBsGridData(shop.queryParam(1));
		},
		//加载数据
		loadBsGridData : function(queryParam){
			var gridObj;
			$(function(){
				gridObj = $.fn.bsgrid.init('storageGridMain',{
					url:{url:comm.UrlList["listShipping"],domain:"sportal"},//comm.domainList["sportal"]+comm.UrlList["listShipping"],
					pageSizeSelect:true,
					pageSize:20,
					otherParames:queryParam,
					stripeRows: true,  //行色彩分隔
					displayBlankRows: false,   //显示空白行
					//rowHoverColor:true,//划过行变色
					operate:{
						add:function(record, rowIndex, colIndex, options){
							var sTpl = "";
							var id = record.id;
							var vShopId = record.vShopId;
							var name = record.name;
							var logisticName = record.logisticName;
							var type = record.type;
							var charge = record.charge;//comm.formatMoneyNumber
							var postage = record.postage;
							var remark = record.remark;
							switch(colIndex){
								case 0 :
									sTpl = name;
									break;
								case 1 :
									sTpl = logisticName;
									break;
								case 2 :
									if(type==0){
										sTpl = '包邮';
									}else if(type==1){
										sTpl = '固定运费';
									}else if(type==2){
										sTpl = '阶梯运费';
									}
									break;
								case 3 :
									sTpl = comm.formatMoneyNumber(charge);
									break;
								case 4 :
									sTpl = comm.formatMoneyNumber(postage);
									break;
								case 5 :
									sTpl = remark;
									break;
								case 6 :
									sTpl = '<a href="javascript:void(0)" class="dlg3" shippid="'+id+'">修改</a> | <a class="dlg1" href="javascript:void(0)" shippid="'+id+'" vshopid="'+vShopId+'">删除</a>';
									break;
								default :
									break;
							}//end switch;
							return sTpl;
						}//end add;
					},//end operate;
					complete:function(e,o){
						//ajax请求完成后加载
						paramList = eval("("+o.responseText+")");
						shop.bindClick();
					}//end complete;
				});//end gridObj;
			});//end $;
		},
		bindTable : function(){
			// 读取集合
//				tablePoint.bindJiazai("#typeHide", true);
//			ajax.listShipping(param, function(response) {
//				tablePoint.bindJiazai("#typeHide", false);
//				    paramList=response;
//					var table = _.template(tpl2, response);
//					$('#tableList').html(table);
//					$("#table1").DataTable({
//						"scrollY":"310px",//垂直限制高度，需根据页面当前情况进行设置
//						"scrollCollapse": true,//垂直限制高度
//						"bFilter" : false, 
//						"bPaginate": false,
//						"bLengthChange" : false, 
//						"bSort":false,
//						"sDom" : '<""l>t<"F"fp>',
//						"oLanguage" : {//改变语言
//							"sZeroRecords" : "没有找到符合条件的数据"
//						}
//
//					});
//					shop.bindClick();
//				});
		},
		bindClick : function() {
			
//			//分页
//		    $(".pageOrder").unbind().on('click',function(){
//		    	var num = $(this).attr("page");
//		    	if(num == '' || num == null){
//		    		return false;
//		    	}
//		    	shop.queryParam(num);
//		    	shop.bindTable();
//		    });
//		    //分页输入
//	    	   $('#pageInput').unbind().on('keypress',function(event){
//	            if(event.keyCode == "13")    
//	            {
//	            	var num = $(this).val();
//	            	var totalPage = Number($(this).attr("totalPage"));
//		            	if(num > totalPage  || num <= 0 || isNaN(num)){
//		            		tablePoint.tablePoint("输入范围错误!请再次输入!");
//		            		return false;
//		            	}else{
//		            		shop.queryParam(num);
//		            		shop.bindTable();
//		            	}
//	            }
//	        });
			
			// 运费列表添加
			$(".dlg2").unbind().on('click',function(){
				ajax2.dlg2Click(ajax,function(retCode){
					var returnCode;
					if(retCode==200){
						returnCode = "运费模板添加成功!";
					}else{
						returnCode = "运费模板添加失败!请稍后重试!";
					}
					tablePoint.tablePoint(returnCode,function(){
						shop.bindTable();
					});
				});
				});
			
			// 运费列表删除
			$(".dlg1").unbind().on('click',function(){
				ajax3.dlg1Click(ajax,this,function(retCode){
						if(retCode==200){
							shop.bindTable();
						}	
					});
				});
			
			// 运费列表修改
			$(".dlg3").unbind().on('click',function(){
				ajax4.dlg3Click(ajax,this,paramList,function(retCode){
					var returnCode;
					if(retCode==200){
						returnCode = "运费模板修改成功!";
					}else{
						returnCode = "运费模板修改失败!请稍后重试!";
					}
						tablePoint.tablePoint(returnCode,function(){
							shop.bindTable();
						});
					});
				});
			
			$("#cbox").unbind().on('change',function(){
				if ($("#cbox").attr("checked")) {

					$("#cbox").attr("checked", false);
					console.log(false);
				} else {
					$("#cbox").attr("checked", true);
					$("#postageId").hide();
					$("#chargeId").attr("disabled", true);
				}

			});
		}

	}
	

	
	return shop;
});

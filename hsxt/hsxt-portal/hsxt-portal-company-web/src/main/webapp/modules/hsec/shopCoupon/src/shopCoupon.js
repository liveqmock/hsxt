define(["hsec_shopCouponDat/shopCouponDat"
        ,"text!hsec_shopCouponTpl/couponRelationShop.html"
        ,"text!hsec_shopCouponTpl/couponRelationShopList.html"
        ,"hsec_tablePointSrc/tablePoint"
        ,"text!hsec_shopCouponTpl/tab.html"
        ,"text!hsec_shopCouponTpl/gridMain.html"
        ,"text!hsec_shopCouponTpl/couponRelationSet.html"],function(ajaxRequest,couponRelationShop,
        		couponRelationShopList,tablePoint,tabTpl,gridMainTpl,couponRelationShopSet){
    var param={"currentPageIndex":1};
	var shopCoupon ={
		queryParm : function(num){
			param={"eachPageSize":20};
			//param = {"currentPageIndex":num!=null?num:1};
			return param;
		},
		//初始化主入口
		bindData : function(){
			//电子消费劵列表
			//$('.operationsArea').html(couponRelationShop);
			$("#busibox").html(_.template(gridMainTpl));
			shopCoupon.loadBsGridData(shopCoupon.queryParm(1));
			//shopCoupon.bindTable(param);
		},
		//加载数据表格
		loadBsGridData : function(queryParam){
			var gridObj;
			$(function(){
				gridObj = $.fn.bsgrid.init('shopCouponGridMain',{
					url:{url:comm.UrlList["listShopCouponPartition"],domain:"sportal"},//comm.domainList["sportal"]+comm.UrlList["listShopCouponPartition"],
					pageSizeSelect: true,
	                pageSize: 20,
	                otherParames:queryParam,
	                stripeRows: true,  //行色彩分隔 
					displayBlankRows: false,   //显示空白行
					operate:{
						/**
						 * record:数据源
						 * rowIndex：行
						 * colIndex：列
						 * options：
						 */
						add : function(record, rowIndex, colIndex, options){
							var sTpl = "";
							var shopCouponId = record.shopCouponId;
							var id = record.id;
							var useAmount = record.useAmount;
							var useNumber = record.useNumber;
							var couponName = record.couponName;
							var faceValue = record.faceValue;
							switch(colIndex){
								case 0:
									sTpl = "每满"+useAmount+"元使用"+useNumber+"张 ";
									break;
								case 1:
									sTpl = couponName;
									break;
								case 2:
									sTpl = faceValue;
									break;
								case 3:
									sTpl = '<a class="removeCouponRelation" shopCouponId="'+shopCouponId+'" partionId="'+id+'">删除</a>';
									break;
								default:
									break;
							}//end switch;
							return sTpl;
						}//end add;
					},
					complete : function(e,o){
						//查看抵扣劵关联的实体店
						shopCoupon.relationShop();
					}//end complete;
				});//end gridObj;
			});//end $;
		},
		bindTable : function(param){
			//电子消费劵列表
//			tablePoint.bindJiazai("#typeHide",true);
//			ajaxRequest.listShopCouponPartition(param,function(response){
//				tablePoint.bindJiazai("#typeHide",false);	
//				var html = _.template(couponRelationShopList, response.data);
//				$('#couponList').html(html);				
//				shopCoupon.relationShop();
//			})
		},
		//查看抵扣劵关联的实体店
		relationShop:function(){
//			//分页
//		    $(".pageOrder").unbind().on('click',function(){
//		    	var num = $(this).attr("page");
//		    	if(num == '' || num == null){
//		    		return false;
//		    	}
//		    	var param = shopCoupon.queryParm(num);
//		    	shopCoupon.bindTable(param);
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
//		            		var param = shopCoupon.queryParm(num);
//		            		shopCoupon.bindTable(param);
//		            	}
//	            }
//	        });
			
	    	//新增关联   
    		$('#add_crs').unbind().on('click',function(){
    			//发送请求获得下拉框的内容
    			ajaxRequest.listCoupon({},function(response){
    				if(response.retCode == 200){
    					var html = _.template(couponRelationShopSet, response.data);
        				$('#coupon_view').html(html);		       				
        				$( "#coupon_view" ).dialog({
        					show: true,
        					modal: true,
        					title:"新增设置",
        					width:550,
        					//height:300,
        					buttons: {					  
        					  '保存': function(){       									
    							var addCouponPartitonParam={};
    							addCouponPartitonParam.couponId=$("select[name='coupon_crs'] option:selected").val();
    							addCouponPartitonParam.couponName=$("select[name='coupon_crs'] option:selected").attr("couponName");
    							addCouponPartitonParam.faceValue=$("select[name='coupon_crs'] option:selected").attr("faceValue");   					
    							addCouponPartitonParam.useAmount=$("select[name='useAmount_crs'] option:selected").val();
    							addCouponPartitonParam.useNumber=$("select[name='useNumber_crs'] option:selected").val();
    							
    							if(addCouponPartitonParam.useAmount=="-请选择-"){
    								tablePoint.tablePoint("请选择金额!");
    								return false;
    							}
    							if(addCouponPartitonParam.useNumber=="-请选择-"){
    								tablePoint.tablePoint("请选择数量!");
    								return false;
    							}
    							if(addCouponPartitonParam.couponName==null){
    								tablePoint.tablePoint("请选抵扣券!");
    								return false;
    							}	
	    						 ajaxRequest.addCouponRule(addCouponPartitonParam,function(response){
        		    				if(response.retCode == 200){
        		    					$('#coupon_view').empty();	
        		    					$("#coupon_view").dialog( "close" );	
        		    					//页面刷新
        		    					var param = shopCoupon.queryParm(1);
        								shopCoupon.loadBsGridData(param);        		    				        		    					
        		    				}else if(response.retCode == 203){
        		    					tablePoint.tablePoint("价格区间已经设置!");
        		    				}else{
        		    					tablePoint.tablePoint("请求错误请重试!");
        		    				}
		    		    		});    
    						    //$('#coupon_view').empty();	
	    						 },
        					   '关闭' : function(){
	        						  //shopCoupon.bindTable();  
        							$('#coupon_view').empty();	
        						   	$("#coupon_view").dialog("close");	
        						  }	
        					}
        				 });    					
    				}else{
    					tablePoint.tablePoint("请求错误请重试!");
    				}  			   				
    			})   						
    		});
    		//删除事件
			$('.removeCouponRelation').unbind().on('click',function(){
				var obj = $(this);
				var param={};
//				param.shopCouponPartitionIds= $(this).parents("tr").attr("partionId");	//多个用,隔开
//				param.shopCouponIds=$(this).parents("tr").attr("shopCouponId");			//多个用,隔开
				// Modified by zhanghh @date:2015-12-16
				param.shopCouponPartitionIds= $(this).attr("partionId");	//多个用,隔开
				param.shopCouponIds=$(this).attr("shopCouponId");			//多个用,隔开
				$('#dlg0').dialog({
					title:"提示信息",
					width : "400",
					open : function(){
						$(this).html("<p id='table-point'>是否删除规则?</p>");
					},
					buttons : {
						'确定' : function() {				
							ajaxRequest.delCouponRule(param,function(response){
								if(response.retCode == 200){
									$("#dlg0").dialog("destroy");	
									//页面刷新
    		    					var param = shopCoupon.queryParm(1);
    								//shopCoupon.bindTable(param);
    		    					shopCoupon.loadBsGridData(param);
								}else{
									tablePoint.tablePoint("删除规则失败!");
								}
							})
						},
						'取消' : function() {
							$(this).dialog("destroy");							
						}
					}
				});	
			});
		}		
	}
	return shopCoupon;
});
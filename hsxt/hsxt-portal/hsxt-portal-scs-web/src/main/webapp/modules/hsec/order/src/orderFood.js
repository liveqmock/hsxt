define(["hsec_tablePointSrc/tablePoint"
		,"hsec_orderDat/orderdat"
        ,"text!hsec_orderTpl/food/orderFoodMain.html"
        ,"text!hsec_orderTpl/food/orderFoodDetail.html"
        ,"hsec_tablePointSrc/function"],function(tablePoint,ajaxRequest,orderFoodMain,orderFoodDetail){
   var param = {"firstRecordIndex":1};
	var orderFood = {
		//初始化ComboxList :: add by zhanghh 20160223
		selectList : function(){
			$("#repastForm").selectList({
				options:[{name:'请选择',value:''},{name:'店内',value:'1'},{name:'外卖',value:'2'},{name:'自提',value:'3'}]
			});
			$("#orderStatusLi").hide();
		},
		//查询参数
		queryParam : function(){
				param.orderId = $("#orderId").val();
				param.companyNo = $("#companyNo").val();
				param.startTime = $("#startTime1").val();
				param.endTime = $("#endTime1").val();
				param.buyerAccountNo = $("#resourceNo").val();
				param.shopName = $("#shopName").val();
				param.repastForm = $("#repastForm").attr("optionvalue");//val();
				param.orderStatusStr = $("#orderStatus").attr("optionvalue");
			return param;
		},
		//绑定餐饮数据
		bindOrderFoodData : function(){
			$("#busibox").html(orderFoodMain);
			//loadDatePicker("#startTime1","#endTime1");
			orderFood.selectList();
			/**日期控件**/
			$("#startTime1").datepicker({
				changeMonth : true,
				changeYear : true,
				dateFormat : "yy-mm-dd",
				maxDate : comm.getCurrDate(),
				onClose : function(selectedDate){
					$("#endTime1").datepicker("option", "minDate", selectedDate);
				}
			});
			$("#endTime1").datepicker({
				changeMonth : true,
				changeYear : true,
				dateFormat : "yy-mm-dd",
				maxDate : comm.getCurrDate(),
				onClose : function(selectedDate){
					$("#startTime1").datepicker("option", "maxDate", selectedDate);
				}
			});
			/**日期控件**/
			var param = orderFood.queryParam();	
			orderFood.initTableData(param);
		},
		//绑定表格数据
		initTableData : function(param){
			gridObj = $.fn.bsgrid.init('tableList',{
				url:{url:comm.UrlList["queryOrderFoods"],domain:"scs"},
				pageSizeSelect: true,
                pageSize: 20,
                otherParames:$.param(param),
                stripeRows: true,  //行色彩分隔 
				displayBlankRows: false,   //显示空白行
				operate: {
					add:function(record, rowIndex, colIndex, options){
						if(colIndex==0){return record.orderId}
						if(colIndex==1){return record.createTime}
						if(colIndex==2){return record.companyResourceNo}
						if(colIndex==3){return record.companyName}
						if(colIndex==4){return record.shopName}
						//配送费（add by zhanghh 20160519）
						var l = record.amountLogistic || 0;
						if(colIndex==5){return '<span class="red">'+(record.amountTotal+l).toFixed(2)+'</span>'}
						if(colIndex==6){return '<span class="blue">'+record.pointsTotal.toFixed(2)+'</span>'}
						if(colIndex==7){
							if(record.repastForm=='1'){ 
          						return '<span>店内就餐</span>'
			          		}else if(record.repastForm=='2'){
			          			return '<span>送餐</span>'
			          		}else if(record.repastForm=='3'){
			          			return '<span>自提</span>'
			          		}else{
			          			return '<span>其他</span>'
			          		}
						}
						if(colIndex==8){
							if(record.payType=='1'){
			          			return '<span>现金</span>'
			          		}else if(record.payType=='3'){
			          			return '<span>互生币支付</span>'
			          		}else if(record.payType=='4'){
			          			return '<span>网银支付</span>'
			          		}else{
			          			return '<span>其他</span>'
			          		}
						}
						if(colIndex==9){
							if(record.repastForm=='1'){
				          		if(record.orderStatus=='0'){
									return '<span>等待客户付定金</span>'
								} else if(record.orderStatus=='-2'){
						 			//return '<span>企业下单</span>'					 		
				               	} else if(record.orderStatus=='1'||record.orderStatus=='-3'){
						 			return '<span>待确认</span>'
						   		} else  if(record.orderStatus=='2' || record.orderStatus=='9') {
						   			return '<span>待用餐</span>'
						   		} else  if(record.orderStatus=='6') {
						   			return '<span>用餐中未打单</span>'
						   		} else  if(record.orderStatus=='7') {
						   			return '<span>已打单待结账</span>'
						   		} else  if(record.orderStatus=='4') {
						   			return '<span>已结账</span>'
						   		} else  if(record.orderStatus=='10') {
									//return '<span>消费者取消待企业确认</span>	'						
								} else  if(record.orderStatus=='99'||record.orderStatus=='-1'){
									return '<span>已取消</span>'
						   		}  		
				          	}else if(record.repastForm=='2'){
				          		 if(record.orderStatus=='8'){
						 			return '<span>待接单</span>'
						   		} else if(record.orderStatus=='2' ) {
						   			return '<span>备餐中待送餐</span>'
						   		} else if(record.orderStatus=='11') {
						   			return '<span>配送中待收货</span>'				   				   		
						   		} else if(record.orderStatus=='4') {
						   			return '<span>确认收货订单完成</span>'
						   		} else if(record.orderStatus=='10') {
									return '<span>消费者取消确认</span>'
								} else if(record.orderStatus=='99'||record.orderStatus=='-1'){
									return '<span>已取消	</span>'					
						   		}  
				          	}else if(record.repastForm=='3'){
				          		  if(record.orderStatus=='8'){
						 			return '<span>待接单</span>'	
						   		} else if(record.orderStatus=='2' ) {
						   			return '<span>待自提	</span>'				   	
						   		} else if(record.orderStatus=='4') {
						   			return '<span>已自提订单完成</span>'				   	
						   		} else if(record.orderStatus=='10') {
									return '<span>消费者申请取消，待企业确认</span>'												   		
					   		    } else if(record.orderStatus=='99'||record.orderStatus=='-1'){
									return '<span>已取消	</span>'								
						   		}     
				          	}
						}
						if(colIndex==10){return '<a href="javascript:void(0)" class="btn_search_detail" data-id="'+rowIndex+'">查看详情</a>'}
					}
				},
				complete : function(o,e){
					orderFood.bindSearchFoodClick();
					orderFood.bindSearchDetailClick();
					orderFood.bindOrderTypeComboxGangedClick();
				}
			});
		},
		//查询事件
		bindSearchFoodClick : function(){
			//编号输入验证
			$("#orderId,#companyNo,#resourceNo").on('focus',function(){
				$(this).on('keyup',function(){
					var num = $(this).val();
					var p1=/^[\d]+$/;
					if(!p1.test(num)){
						$(this).val("");
					}
				})
			});
			$("#orderListSearch").unbind("click").on("click",function(){
				var param = orderFood.queryParam();
				orderFood.initTableData(param);
			});
		},
		//查看详情
		bindSearchDetailClick : function(){
			$(".btn_search_detail").unbind().on("click",function(){
				var paramObj = gridObj.getRecord($(this).attr("data-id"));
				var orderId = paramObj.orderId; //$(this).closest("tr").attr("data-order-id");
				var userId = paramObj.userId; //$(this).parents("tr").attr("data-user-id");
				var param = {"orderId":orderId,"userId":userId};
					ajaxRequest.queryOrderFoodsDetail(param,function(response){
						if(response.retCode==200){//height: 811px; width: 1380px;
								var html = _.template(orderFoodDetail, response);
								$("#showFoodDetail").dialog({title:"订单详情",width:1000,
									open:function(){
										$(this).html(html);
										$(".ui-dialog").css("top",100);
									},
									buttons:{
										"关闭":function(){
											$(this).dialog("destroy");
										}
									}
								});
							}else if(response.retCode==201){
								tablePoint.tablePoint("对不起，请求失败");
							}else if(response.retCode==212){
								tablePoint.tablePoint("登录已过期，请重新登录，谢谢");
							}
					});
				});
		},
		//绑定订单类型联动事件
		bindOrderTypeComboxGangedClick : function(){
				//var html = "";
			$("input[id='repastForm']").on('change',function(){
				//店内
				if($(this).attr("optionvalue")==1){
					$("#orderStatusLi").show();
					$("#orderStatus").selectList({
						options:[{name:'请选择',value:''},
							{name:'等待客户付定金',value:'0'},
							//{name:'企业下单',value:'-2'},
							{name:'待确认',value:'1,-3'},
							{name:'待用餐',value:'2,9'},
							{name:'用餐中未打单',value:'6'},
							{name:'已打单待结账',value:'7'},
							{name:'已结账',value:'4'},
							//{name:'消费者取消待企业确认',value:'10'},
							{name:'已取消',value:'-1,99'}]
					});
				}else if($(this).attr("optionvalue")==2){
					//外卖
					$("#orderStatusLi").show();
					$("#orderStatus").selectList({
						options:[{name:'请选择',value:''},
							{name:'待接单',value:'8'},
							{name:'备餐中待送餐',value:'2'},
							{name:'配送中待收货',value:'11'},
							{name:'确认收货订单完成',value:'4'},
							{name:'消费者取消确认',value:'10'},
							{name:'已取消',value:'-1,99'}]
					});
				}else if($(this).attr("optionvalue")==3){
					$("#orderStatusLi").show();
					//自提
					$("#orderStatus").selectList({
						options:[{name:'请选择',value:''},
							{name:'待接单',value:'8'},
							{name:'待自提',value:'2'},
							{name:'已自提订单完成',value:'4'},
							{name:'消费者取消确认',value:'10'},
							{name:'已取消',value:'-1,99'}]
					});
				}else{
					$("#orderStatusLi").hide();
				}
			});
		}
	}	
	return orderFood;
});
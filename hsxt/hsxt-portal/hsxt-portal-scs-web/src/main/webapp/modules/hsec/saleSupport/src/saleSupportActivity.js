define(["text!hsec_saleSupportTpl/sale_support.html"
		,"hsec_tablePointSrc/tablePoint"
		,"hsec_saleSupportDat/saleSupportActivity"
        ], function(tpl,tablePoint,ajaxRequest){
	
	//初始化参数(页面第一次加载时将根据此配置展现)
	var showTypeInit = "tabApplyed";//默认选择的Tab
	/*var statusesInit = [{"statusTag":null, "statusName":"全部"},
	                    {"statusTag":"0", "statusName":"提交申请"}, {"statusTag":"1", "statusName":"企业不同意"}, {"statusTag":"2", "statusName":"待企业上门取货"}, 
	                    {"statusTag":"3", "statusName":"待买家发货"}, {"statusTag":"4", "statusName":"待企业收货确认"}, {"statusTag":"5", "statusName":"退款中"}, 
	                    {"statusTag":"6", "statusName":"退/换货结束"}, {"statusTag":"7", "statusName":"退款失败"}, {"statusTag":"8", "statusName":"待企业发货-换货"},
	                    {"statusTag":"9", "statusName":"待买家确认收货-换货"}, {"statusTag":"10", "statusName":"待企业送货上门-换货"}, {"statusTag":"-1", "statusName":"取消申请"}];
	*/
	//选择的Tab(申请未处理:tabApplyed;处理中:tabProcessing;退款中:tabRefunding;已完成:tabFinished;全部:tabAll)
	var showTypeSelect = "";
 	//var paramInit = {"queryType":showTypeInit};//默认选择的Tab
	//选中的订单编号
	var orderNoSelect = "";
	//选中的售后单号
	var refIdSelect = "";
	//选中的买家昵称
	var userNickNameSelect = "";
	//选中的状态
	var statusSelect = "";
	var findPageParam={};
	var gridObj;
	//售后信息数据
	var saleSupport = {
			//初始化ComboxList :: add by zhanghh 20160224
			selectList : function(){
				$("#statusQueryParamInPage").selectList({
				options:[
                    {name:'全部',value:''},
					{name:'提交申请',value:'0'},
					{name:'企业不同意',value:'1'},
					{name:'待企业上门取货',value:'2'},
					{name:'待买家发货',value:'3'},
					{name:'待企业收货确认',value:'4'},
					{name:'退款中',value:'5'},
					{name:'退/换货结束',value:'6'},
					{name:'退款失败',value:'7'},
					{name:'待企业发货-换货',value:'8'},
					{name:'待买家确认收货-换货',value:'9'},
					{name:'待企业送货上门-换货',value:'10'},
					{name:'取消申请',value:'-1'}
				]
			});
			},
			//页面数据初始化
			bindData : function(){
				$('#busibox').html(_.template(tpl,{"showTypeSelect":showTypeSelect==""?showTypeInit:showTypeSelect}));
				var queryParam = {"queryType":showTypeSelect==""?showTypeInit:showTypeSelect}
				saleSupport.selectList();
				saleSupport.loadBsGridData(queryParam);
			},
			loadBsGridData : function(queryParam) {
				queryParam.eachPageSize = 10;
				
				gridObj = $.fn.bsgrid.init('refund_table',{
					url:{url:comm.UrlList['findSaleSupports'],domain:'scs'},
					otherParames : $.param(queryParam),
					pageSize:10,
					pageSizeSelect:true,
					displayBlankRows:false,
					lineWrap:true,
					stripeRows:true,
					rowSelectedColor:true,
					operate:{
						add : function(record, rowIndex, colIndex, options){
							if(colIndex==5){ return '<span class="red">'+record.refundAmount+'</span>' }
							if(colIndex==6){ return '<span class="blue">'+record.refundPv+'</span>' }
							if(colIndex==9){
								var returnMsg = '';
								/*if(record.saleSupportType != '仅退款'){
									returnMsg += '<a class="trackLogistic" refid="'+record.refId+'">物流跟踪</a>';
                				}*///modifly by zhanghh 20160526 Bug:25363
								if(record.saleSupportType != '仅退款' && record.status != "企业不同意" && record.status != "提交申请"){
									returnMsg += '<a class="trackLogistic" refid="'+record.refId+'">物流跟踪</a>';
                				}
								if(record.processStep == 0){
									returnMsg += '<button name="btnProcess" type="button" class="btn-search ml10" value="'+record.refId+'">处理</button>'
                				}
								return returnMsg;
							}
						}
					},
					complete:function(o, e, c){
						//【事件注册】Tab点击
						saleSupport.tabSelect();
						//物流跟踪
						saleSupport.trackLogistic();
						//【事件注册】查询
						saleSupport.dataRefresh();
						//页面加载
						saleSupport.loadInit();
						//处理事件注册
						saleSupport.processSetp();
						//处理类型
						saleSupport.selectCocombox();
					}
				});
			},
			//物流跟踪
			trackLogistic : function(){
				$(".trackLogistic").click(function(){
					var refid = $(this).attr("refid");
					var param = {};
					param["refid"] = refid;
					var w = window.open();
						ajaxRequest.getLogisticByRefid(param,function(res){
							var url = "http://www.kuaidi100.com";
							try{
								if(res.retCode == 200){
									//物流公司code
									var code = res.data.code;
									//运单号
									var logisticCode = res.data.logisticCode;
									url = url +"/chaxun?com="+code+"&nu="+logisticCode;
								}
							}catch(e){
								
							}
							w.location = url
						})
					})
			},
			//【事件注册】Tab点击
			tabSelect : function(){
				$("#ssTabs li").click(function(){
					//设置当前选中的li标识样式
					$("#ssTabs li").removeClass("cur");
					$(this).addClass("cur");
					//根据选择的Tab设置当前展示类型
					if($(this).attr("tag") == "tabApplyed"){
						//申请未处理
						showTypeSelect = "tabApplyed";
					}else if($(this).attr("tag") == "tabProcessing"){
						//处理中
						showTypeSelect = "tabProcessing";
					}else if($(this).attr("tag") == "tabRefunding"){
						//退款中
						showTypeSelect = "tabRefunding";
					}else if($(this).attr("tag") == "tabFinished"){
						//已完成
						showTypeSelect = "tabFinished";
					}else if($(this).attr("tag") == "tabAll"){
						//全部
						showTypeSelect = "tabAll";
					}
					//刷新数据
					findPageParam={};
					findPageParam["queryType"]=showTypeSelect;
					saleSupport.bindData(findPageParam);
				});
			},
			//【事件注册】查询
			dataRefresh : function(){
				//编号输入验证
				$("#orderNoQueryParamInPage,#userNickNameQueryParamInPage").on('focus',function(){
					$(this).on('keyup',function(){
						var num = $(this).val();
						var p1=/^[\d]+$/;
						if(!p1.test(num)){
							$(this).val("");
						}
					})
				});
				$("#ssQuery").unbind().on("click",function(){
					//获取查询条件
					//...订单编号
					orderNoSelect = $("#orderNoQueryParamInPage").val();
					//...售后单号
					refIdSelect = $("#refIdQueryParamInPage").val();
					//...买家昵称
					userNickNameSelect = $("#userNickNameQueryParamInPage").val();
					//...状态
					statusSelect = $("#statusQueryParamInPage").attr("optionvalue");
					
					var queryParam = {
							"queryType":showTypeSelect,
							"orderNo" : orderNoSelect,
							"refId" : refIdSelect,
							"userNickName" : userNickNameSelect}
					if(statusSelect!=""){
						queryParam["status"]=statusSelect;
					}
					//开始查询
					findPageParam={};
					findPageParam=queryParam;
					saleSupport.loadBsGridData(findPageParam);
				});
			},
			//处理事件
			processSetp : function(){
				$("button[name='btnProcess']").click(function(){
					//获取处理ID
					var refId = $(this).attr("value");
					$("#content").val("");
					$("#porpDiv").dialog({
						title:"处理信息",
						show: true,
						modal: true,
						width : "300",
						buttons:{
							'处理':function(){
								var checkType = $("#txtProcessType").val();
								var txtCheckType = $("#txtProcessType").find("option:selected").text();
								var content = $("#content").val();
								if(checkType == '' || txtCheckType =='-请选择-'){
									tablePoint.tablePoint("请选择处理类型!");
								}else if(content == ''){
									tablePoint.tablePoint("请填写处理描述!");
								}
								if(content != '' && checkType != ''){
										ajaxRequest.serviceProcComplain({"refId":refId,"processType":txtCheckType,"content":content},function(response){
											if(response.retCode == 200){
												tablePoint.tablePoint("处理成功");
												findPageParam["queryType"]="tabAll";
												saleSupport.loadBsGridData(findPageParam);
												$("#porpDiv").dialog("destroy");
											}else if(response.retCode == 201){
												tablePoint.tablePoint("对不起，请求失败");
											}else if(response.retCode == 212){
												tablePoint.tablePoint("登录已过期，请重新登录，谢谢");
											}
										});
								}
							},
							'取消':function(){
								$(this).dialog("destroy");
							}
						}
					});
				});
			},
			//页面加载
			loadInit : function(){
				//加载完成后
				$("#ssBody").ready(function(){
					//默认查看"申请未处理"
					if(showTypeSelect == ""){
						showTypeSelect = showTypeInit;
					}
					//已选中Tab,设置对应Tab样式为选中
					if(showTypeSelect == "tabApplyed"){$("#ssBody li").eq(0).addClass("cur");}
					if(showTypeSelect == "tabProcessing"){$("#ssBody li").eq(1).addClass("cur");}
					if(showTypeSelect == "tabRefunding"){$("#ssBody li").eq(2).addClass("cur");}
					if(showTypeSelect == "tabFinished"){$("#ssBody li").eq(3).addClass("cur");}
					if(showTypeSelect == "tabAll"){$("#ssBody li").eq(4).addClass("cur");}
				});
			},
			selectCocombox : function(){
				ajaxRequest.getDictionarysByType(function(response){
					var html="<option value=''>-请选择-</option>";
					$.each(response.data,function(i,vals){
						html += "<option value='"+vals.value+"'>"+vals.name+"</option>"
					})
					$("#txtProcessType").html(html);
				});
			}
	};
	
	return saleSupport;
});


define(["hsec_serviceOpenDat/serviceOpenActivity",
        "text!hsec_serviceOpenTpl/headProvisioning.html",
        "hsec_tablePointSrc/tablePoint"],  
        function(ajaxRequest,headProvisioningTpl,tablePoint){
	var gridObj;
	//商品类目数据
	var serviceOpen = {
			//初始化ComboxList :: add by zhanghh 2016-03-02
			selectList : function(){
				$("#evorimentEvaluateSelect").selectList({
					options:[
	                    {name:'全部',value:''},
						//{name:'未开通',value:'0'},
						{name:'已开通',value:'1'},
						{name:'暂停',value:'2'}
					]
				});
			},
			//构建查询参数
			queryParam : function(num){
				var param = {"currentPageIndex":num !=null ? num : 1,"eachPageSize":20};
				return param;
			},
			//页面数据初始化
			bindData : function() {
				//加载中间内容
				$("#busibox").html(headProvisioningTpl);
				serviceOpen.selectList();
				serviceOpen.initTableData(serviceOpen.queryParam(1));
			},
			//渲染表格
			initTableData : function(param){
				gridObj = $.fn.bsgrid.init('openService',{
					url:{url:comm.UrlList['serviceOpenList'],domain:'scs'},
					otherParames:$.param(param),
					pageSize:20,
					pageSizeSelect:true,
					displayBlankRows:false,
					lineWrap:true,
					stripeRows:true,
					operate:{
						add : function(record, rowIndex, colIndex, options){
							if(colIndex==2){return record.sysSer.name}
							if(colIndex==3){return record.sysSer.desc}
							if(colIndex==4){
								if(record.status=="0"){ 
					    			return '<span>未开通</span>'
					    		}else if(record.status=="1"){ 
					    			return '<span>已开通</span>'
					    		}else{ 
					    			return '<span>暂停</span>'
					    		} 
							}
						}
					},
					complete:function(o,e,c){
						serviceOpen.btnSearchClick();
					}
				});
			},
			//查询事件
			btnSearchClick : function(){
				//编号输入验证
				$("#companyNo").on('focus',function(){
					$(this).on('keyup',function(){
						var num = $(this).val();
						var p1=/^[\d]+$/;
						if(!p1.test(num)){
							$(this).val("");
						}
					})
				});
				$("#orderList_search").unbind().on("click",function(){
					var param = serviceOpen.queryParam(1);
					param.shopResourceNo = $("#companyNo").val();
					//param.companyName = $("#companyName").val();
					param.status = $("#evorimentEvaluateSelect").attr("optionvalue");
					serviceOpen.initTableData(param);
				});
			}
		};

	return serviceOpen;
});


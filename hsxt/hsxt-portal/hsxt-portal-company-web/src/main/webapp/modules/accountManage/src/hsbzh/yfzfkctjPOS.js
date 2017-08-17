define(['text!accountManageTpl/hsbzh/census/yfzfkctjPOS.html',
        'text!accountManageTpl/hsbzh/census/yfzfkctjPOS_detail.html',
        'accountManageDat/hsbzh/hsbzh'], function(tpl,detailTpl,dataMoudle){
    return yfzfkctjPOS = {
    			// 初始化页面
    			showPage : function(){
    				$('#busibox').html(tpl);
    				comm.initBeginEndTime("#search_beginDate","#search_endDate");//日期控件
    				comm.initSelect('#scpoint_dsPOS_subject', comm.lang("accountManage").channel);//终端类型
    				yfzfkctjPOS.posDeviceNo();// 获取终端编号列表
    				//查询单击事件
    				$('#scpoint_dsPOS_searchBtn').click(function(){
    					if(!comm.queryDateVaild('scpoint_dsPOS_form').form()){return;}// 查询框条件的校验
    					yfzfkctjPOS.queryPage();// 统计和列表查询
    				});
    			},
    			// 统计和列表查询
    			queryPage : function() {
    				var params={
							search_beginDate : $("#search_beginDate").val(),
							search_endDate : $("#search_endDate").val(),
							search_equipmentNo: $("#scpoint_dsPOS_equipmentNo").val(),
							search_channelType: $("#scpoint_dsPOS_subject").attr("optionvalue")||comm.lang("accountManage").channelVal
					};
    				//统计查询
					dataMoudle.reportsPointDeductionByChannelSum(params,function(res){
						var obj=res.data;
						if(obj){
							var ndetPoint = obj.ndetPoint || 0;
							var cdetPoint = obj.cdetPoint || 0;
							obj.sumPoint = (parseFloat(ndetPoint)-parseFloat(cdetPoint)).toFixed(2);
						}else{
							var obj = {};
							obj.ndetPoint = 0;
							obj.cdetPoint = 0;
							obj.sumPoint = 0.00;
						}
						$('#detailTpl').html(_.template(detailTpl,obj));
						//列表查询
						dataMoudle.reportsPointDeductionByChannel("scpoint_dsPOS_result_table",params,yfzfkctjPOS.detail);	
					});	
    			},
    			// 列表列转换
				detail: function(record, rowIndex, colIndex, options){
					if(colIndex == 1){
						return comm.lang("common").accessChannel[record["channelType"]];
					} 
					if(colIndex==3){
						return comm.formatMoneyNumber(record.ndetPoint);
					}
					if(colIndex==4){
						return comm.formatMoneyNumber(record.cdetPoint);
					}
					if(colIndex==5){
						return comm.formatMoneyNumber(parseFloat(record.ndetPoint)-parseFloat(record.cdetPoint));
					}
				},
				// 获取终端编号列表查询
				posDeviceNo : function(){
					dataMoudle.getPosDeviceNoList(null,function(res){
						var list = res.data;
						var options = [{"name":"","value":""}];
						if(list){
							$.each(list,function(n,v){
								options.push({"name":v.deviceNO,"value":v.deviceNO});
							});
						}
						$("#scpoint_dsPOS_equipmentNo").selectList({
							width:135,
							optionWidth:140,
							overflowY:'auto',
							options: options
						})
					});
				}
    	};
});
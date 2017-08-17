	define(['text!platformProxyTpl/qynfjn/qynfjn.html',
        'platformProxyDat/platformProxy',
        'platformProxyLan'], function(qynfjnTpl,dataMoudle){
	return {
		showPage : function(){
			$('#busibox').html(qynfjnTpl);	
			var json= [];	
			var gridObj = $.fn.bsgrid.init('searchTable', {	
				autoLoad: false,
				pageSizeSelect: true ,
				pageSize: 10 ,
				stripeRows: true,  //行色彩分隔 
				displayBlankRows: false ,   //显示空白行
				localData:json 
			});
			$('#query_btn').click(function(){
				var resNo=$("#companyResNo").val();
				if(comm.isEmpty(resNo)){
					comm.error_alert(comm.lang("platformProxy").pleaseInputResNo);
					return;
				}
				if(!comm.isTrustResNo(resNo)&&!comm.isServiceResNo(resNo)){
					comm.error_alert(comm.lang("platformProxy").isTorSHsResNo);
					return;
				}
				dataMoudle.companyInfor({resNo:resNo},function(res){
					var data=res.data;
					var json= [];	
					if(data!=null){
						var json= [{
						"th_1":res.data.entResNo,
						"th_2":res.data.entName,
						"th_3":res.data.contactPerson,
						"th_4":res.data.contactPhone
						}];
					}
					var gridObj = $.fn.bsgrid.init('searchTable', {				 
						pageSizeSelect: true ,
						pageSize: 10 ,
						stripeRows: true,  //行色彩分隔 
						displayBlankRows: false ,   //显示空白行
						localData:json 
					});
					var entCustId=res.data.entCustId;
					dataMoudle.queryAnnualFeeInfo({companyCustId:entCustId},function(res){
						var data=res.data;
						var currency="人民币";
						$("#arrearAmount").val(comm.formatMoneyNumberAps2(data.price));
						$("#orderHsbAmount").val(comm.formatMoneyNumberAps2(data.arrearAmount));
						$("#endDate").val(data.endDate);
						var areaDate=comm.navNull(data.areaStartDate) +' ~ '+ comm.navNull(data.areaEndDate);
						$("#areaDate").val(areaDate);
						$("#companyCustId").val(data.entCustId);
						if(data.arrearAmount>0){
						    $('#submitTpl').removeClass('none')
						}
					});
				});
			});
            $('#submitAnnualFeeForm').click(function(){
            	var params={
            			companyCustId:$("#companyCustId").val(),
            			orderHsbAmount:$("#orderHsbAmount").val()
            			   };
            	
            
            	dataMoudle.submitAnnualFeeOrder(params,function(res){
					//console.info(res);
            		comm.yes_alert(comm.lang("platformProxy").annualFee_success+res.data.order.orderNo,400);
            		 $('#submitTpl').addClass('none');
            	});
			});
		}
	}	
});
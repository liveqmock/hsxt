define(['text!accountManageTpl/jfzh/census/jfhsfp_xq.html',
		'text!accountManageTpl/jfzh/census/xfjffp_xq.html',
        "accountManageDat/jfzh/jfzh",
        'accountManageDat/accountManage'
			],function(mxcx_xq_jfhsfpTpl, xfjffp,jfzh,accountManage){
	return mxcx_xq_jfhsfp = {	
		showPage : function(whichXQ,param){
			switch(whichXQ){
			case 'jfzh_xq_xhjffp':
					jfzh.getPointAllotDetailed({"batchNo":param},function(res){
						var sumDetail=res.data;
						sumDetail.batchNo=param;
						$('#busibox_xq').html(_.template(xfjffp,sumDetail)).removeClass('none');
						$('#busibox').addClass('none');
	
						comm.liActive_add($('#jfzh_xq_xhjffp'));
						
						$("#mxcx_xq_btn_fh").on('click', function(){
							//隐藏头部菜单
							comm.liActive($('#census_xfjffp'), '#jfzh_mxcxqx, #jfzh_xq_xhjffp, #jfzh_xq_jfhsfp');
							$('#busibox').removeClass('none');
							$('#busibox_xq').addClass('none');
						});
						// 查看消费积分分配详情列表
						jfzh.getPointAllotDetailedList('xfjffp_details_table',{"search_batchNo":param},mxcx_xq_jfhsfp.detail);
					});
				break;
			case 'jfzh_xq_jfhsfp':
				accountManage.getAccOptDetailed(param,function(resp){
					if(resp){
						var obj = resp.data.data;
						if(comm.isNotEmpty(obj)){
							obj.details[0].date=comm.subStringDate(obj.details[0].date,10);
							$('#busibox_xq').html(_.template(mxcx_xq_jfhsfpTpl,obj)).removeClass('none');
							$('#busibox').addClass('none');
							
							$('#jfzh_xq_xhjffp').css('display','');
							$('#jfzh_xq_xhjffp').find('a').addClass('active');	
							$('#census_jfhsfp').find('a').removeClass('active');
							
							$("#mxcx_xq_btn_fh").on('click', function(){
								//隐藏头部菜单
								comm.liActive($('#census_jfhsfp'));
								$('#jfzh_xq_xhjffp').css('display','none');
								$('#busibox').removeClass('none');
								$('#busibox_xq').addClass('none');
							});
						}else{
							comm.yes_alert(comm.lang("accountManage").dataNotExist);
						}
					}
				});
				break;
		}		
	},
	detail : function(record, rowIndex, colIndex, options){		
		if(colIndex==3){
			var transTypeName="";
			var transType=record.transType;
			var tr=transType.charAt(2);
				if(tr=='0'){
					transTypeName="消费积分";
				}else{
					transTypeName="消费积分撤单";
				}
			 return transTypeName;	
		}else if(colIndex==4){
				 return comm.formatMoneyNumber(record.pointVal);
		}		
    }
}
}); 

define(['text!accountManageTpl/jfzh/mxcx_xq.html',
        "accountManageDat/jfzh/jfzh"],function(mxcx_xqTpl,jfzh){
	var mxcxxqFun = { 
		showPage : function(batchNo){
			jfzh.pointAllocDetail({"batchNo":batchNo},function(res){
					var sumDetail=res.data;
					sumDetail.batchNo=batchNo;
					$('#jfzh_detail').html(_.template(mxcx_xqTpl,sumDetail)) ;
					$('#jfzh_detail').removeClass('none');
					$('#busibox').addClass('none');
					comm.liActive_add($('#jfzh_xq_xhjffp'));
					jfzh.pointAllocDetailList('xfjffp_details_table',{"search_batchNo":batchNo},mxcxxqFun.detail);
					//返回按钮
					$('#mxcx_xq_back').click(function(){
						//隐藏头部菜单
						$('#jfzh_detail').addClass('none');
						$('#busibox').removeClass('none');
						$('#jfzh_xq_xhjffp').addClass("tabNone").find('a').removeClass('active');
						$('#census_xfjffp').find('a').addClass('active');
					});
					
			});	
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
	return mxcxxqFun;
}); 

 
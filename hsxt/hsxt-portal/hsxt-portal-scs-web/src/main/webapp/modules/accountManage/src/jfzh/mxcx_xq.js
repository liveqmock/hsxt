define(['text!accountManageTpl/jfzh/mxcx_xq_xhjffp.html',
		'text!accountManageTpl/jfzh/mxcx_xq_jfhsfp.html',
        "accountManageDat/jfzh/jfzh",
        "accountManageDat/accountManage",
			],function( mxcx_xq_xhjffpTpl , mxcx_xq_jfhsfpTpl ,jfzh,accountManage){
	return mxcx_xq={	 
		showPage : function(whichXQ,param){
			switch(whichXQ){
				case 'jfzh_xq_xhjffp':
					jfzh.getPointAllotDetailed({"batchNo":param},function(res){
						var sumDetail=res.data;
						sumDetail.batchNo=param;
						$('#busibox').addClass('none');
						$('#busibox_xq').removeClass('none');
						$('#busibox_xq').html(_.template(mxcx_xq_xhjffpTpl,sumDetail)) ;
						$('#jfzh_xq_xhjffp').css('display','');
						$('#jfzh_xq_xhjffp').find('a').addClass('active');	
						$('#census_xfjffp').find('a').removeClass('active');
						jfzh.getPointAllotDetailedList('xfjffp_xq_table',{"search_batchNo":param},mxcx_xq.detail);
						$('#mxcx_xq_back').on('click', function(){
							//隐藏头部菜单
							comm.liActive($('#census_xfjffp'));
							$('#jfzh_xq_xhjffp').css('display','none');
							$('#busibox').removeClass('none');
							$('#busibox_xq').addClass('none');
						});
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
	    },
	    liActive :function(liObj){		 
			$('#jfzh_xq_xhjffp').css('display','none');
			$('#jfzh_xq_jfhsfp').css('display','none');
			liObj.parent('ul').find('a').removeClass('active');
			liObj.find('a').addClass('active');	
		} 
	}
}); 

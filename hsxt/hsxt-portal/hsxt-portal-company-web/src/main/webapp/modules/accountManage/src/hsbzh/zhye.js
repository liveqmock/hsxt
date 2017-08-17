define(['text!accountManageTpl/hsbzh/zhye.html',
        'accountManageDat/accountManage',
        'accountManageLan' 
        ],function(tpl,accountManage){
	return hsbzh_zhye = {
		showPage : function(){
			this.bindData();
		},
		bindData : function(){
			accountManage.find_hsb_blance({},function(res){
				var amount = comm.isNotEmpty(res.data.circulationHsb)?res.data.circulationHsb:0;
				var response = {
					hsbAcctTotal: comm.formatMoneyNumber(res.data.circulationHsb),
					hsbHelpAcct: comm.formatMoneyNumber(res.data.directionalHsb)
				};
				  var hsbzhyetx="";
				  if(amount==0){
					    hsbzhyetx=comm.lang("accountManage").hsbzhye1;
			        }else if(amount>0&&amount<300){
			        	hsbzhyetx=comm.lang("accountManage").hsbzhye2;
			        }else if(amount>=300&&amount<500){
			        	hsbzhyetx=comm.lang("accountManage").hsbzhye3;
			        }else if(amount>=500){
			        	hsbzhyetx=comm.lang("accountManage").hsbzhye4;
			        }
				  response.hsbzhyetx=hsbzhyetx;
				  $('#busibox').html(_.template(tpl, response));	
				  //lyh,如果是成员企业无积分投资，因此不应该有慈善救助基金,2表示成员企业
				 var params=comm.getRequestParams();
				  if(params.entResType=="2"){
					 $("#hsbHelpAcct").css('display','none');
				  }
				//刷新按钮单击事件
				$('#hsbPointFlush').click(function(){
					hsbzh_zhye.bindData();
				});
			});
			
		}
	}
}); 

 
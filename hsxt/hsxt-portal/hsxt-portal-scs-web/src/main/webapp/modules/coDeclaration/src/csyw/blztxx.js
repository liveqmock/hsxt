define(['text!coDeclarationTpl/csyw/blztxx.html'], function(blztxxTpl){
	return{
		showPage : function(num){
			$('#infobox').html(_.template(blztxxTpl,{num:num}));	
			
			/*按钮事件*/			
			$('#tgqy_blztxx_cancel').triggerWith('#tgqycsyw');
			
			$('#cyqy_blztxx_cancel').triggerWith('#cyqycsyw');
			
			$('#fwgs_blztxx_cancel').triggerWith('#fwgscsyw');
			
			var json= [{
							"th_1":"资料审核",
							"th_2":"服务公司提交资料",
							"th_3":"2014-10-17 15:51:24",
							"th_4":"ABC有限公司",
							"th_5":"0001（张三）"
						},
						{
							"th_1":"资料审核",
							"th_2":"服务公司提交资料",
							"th_3":"2015-08-15 10:51:24",
							"th_4":"123有限公司",
							"th_5":"0002（李四）"
						}];	

			var gridObj;
			 
			gridObj = $.fn.bsgrid.init('searchTable', {				 
				//url : comm.domainList['local']+comm.UrlList['tgqycsyw'],
				// autoLoad: false,
				pageSizeSelect: true ,
				pageSize: 10 ,
				stripeRows: true,  //行色彩分隔 
				localData:json ,
				operate : {	
					detail : function(record, rowIndex, colIndex, options){
						var link1 =  $('<a id="'+ gridObj.getRecordIndexValue(record, 'ID') +'" >查看</a>').click(function(e) {
							//$('#dialog').dialog({autoOpen: true }) ;
							this.chaKan();
							
						}.bind(this) ) ;
						return   link1 ;
					}.bind(this)
					
				} 			
			});
			

		},
		chaKan : function(){
			/*弹出框*/
			$( "#blztxx_content" ).dialog({
				title:"企业银行帐户信息修改确认",
				modal:true,
				width:"400",
				height:"150",
				buttons:{ 
					"确定":function(){
						$( "#blztxx_content" ).dialog( "destroy" );
					},
					"取消":function(){
						 $( "#blztxx_content" ).dialog( "destroy" );
					}
				}
		
			  });
			/*end*/		
	}	
	}
});
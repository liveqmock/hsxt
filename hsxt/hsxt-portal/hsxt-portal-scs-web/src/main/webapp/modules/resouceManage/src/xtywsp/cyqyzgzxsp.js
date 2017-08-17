define(['text!resouceManageTpl/xtywsp/cyqyzgzxsp.html',
		   'text!resouceManageTpl/xtywsp/dialog/cyqyzgyzx.html',	
		   'text!resouceManageTpl/xtywsp/dialog/ddqptsp.html',
		   'text!resouceManageTpl/xtywsp/dialog/dfwgssp.html',
		   'text!resouceManageTpl/xtywsp/dialog/dqptspbh.html',
		   'text!resouceManageTpl/xtywsp/dialog/fwgsspbh.html',
		   'text!resouceManageTpl/xtywsp/dialog/sp.html'
],function( cyqyzgzxspTpl ,cyqyzgyzxTpl, ddqptspTpl, dfwgsspTpl, dqptspbhTpl, fwgsspbhTpl,spTpl ){
	return {	
		cyqyzgyzxTpl:cyqyzgyzxTpl,
		ddqptspTpl: ddqptspTpl,
		dfwgsspTpl:dfwgsspTpl,
		dqptspbhTpl:dqptspbhTpl,
		fwgsspbhTpl:fwgsspbhTpl,
		spTpl:spTpl,	
		showPage : function(){
			$('#busibox').html(_.template(cyqyzgzxspTpl)) ;
			//Todo...
		 	
		 	
		 	$('#sel_spzt').selectList({
		 		options:[
		 			{name:'待服务公司审批',value:'1'},
		 			{name:'服务公司审批驳回',value:'2'},
		 			{name:'待地区平台审批',value:'3'},
		 			{name:'地区平台审批驳回',value:'4'},
		 			{name:'成员企业资格已注销',value:'5'}
		 		]
		 	});
		 	
		 	 
		 	var testData =[
			    {qyglh:'05001000001',qymc:'北京有限公司',qydz:'天安门附近',lxrxm:'张三',lxrsj:'18888888888',zt:'待服务公司审批',ztrq:'2014-12-12'},
			 	{qyglh:'05001000001',qymc:'北京有限公司',qydz:'天安门附近',lxrxm:'张三',lxrsj:'18888888888',zt:'服务公司审批驳回',ztrq:'2014-12-12'},
			 	{qyglh:'05001000001',qymc:'北京有限公司',qydz:'天安门附近',lxrxm:'张三',lxrsj:'18888888888',zt:'待地区平台审批',ztrq:'2014-12-12'},
			 	{qyglh:'05001000001',qymc:'北京有限公司',qydz:'天安门附近',lxrxm:'张三',lxrsj:'18888888888',zt:'地区平台审批驳回',ztrq:'2014-12-12'},
			 	{qyglh:'05001000001',qymc:'北京有限公司',qydz:'天安门附近',lxrxm:'张三',lxrsj:'18888888888',zt:'成员企业资格已注销',ztrq:'2014-12-12'}
			];
			
			var gridObj , self =this;
			 
			gridObj = $.fn.bsgrid.init('tableDetail', {				 
				//url : comm.domainList['local']+ comm.UrlList["jfzhmxcx"] , 
				pageSize: 10 , 
				stripeRows: true,  //行色彩分 
				displayBlankRows: false ,   //显示空白行
			  	localData: testData,
				operate : {	
					detail : function(record, rowIndex, colIndex, options){
						var zt =  gridObj.getCellRecordValue(rowIndex,5) ;
						var link1 =  $('<a  >查看</a>').click(function(e) {				 
							//显示详情	
							switch (zt){
								case '待服务公司审批':
									$('#cyqyzgzxsp_dialog>p').html(self.dfwgsspTpl);										
									break;
								case '服务公司审批驳回':
									$('#cyqyzgzxsp_dialog>p').html(self.fwgsspbhTpl);
									break;
								case '待地区平台审批':
									$('#cyqyzgzxsp_dialog>p').html(self.ddqptspTpl);
									break ;
								case '地区平台审批驳回':
									$('#cyqyzgzxsp_dialog>p').html(self.dqptspbhTpl);
									break ;
								case '成员企业资格已注销':								 
									$('#cyqyzgzxsp_dialog>p').html(self.cyqyzgyzxTpl);
									break ;	
							} 
							$('#cyqyzgzxsp_dialog').dialog({
								title:'待服务公司审批',
								width:600 ,
								buttons:{
									'确定' : function(){
										
										
										$(this).dialog('destroy');
									} 							
								}
							}); 
						});
						return   link1 ;
					} ,
					
					add : function(record, rowIndex, colIndex, options){
						var zt =  gridObj.getCellRecordValue(rowIndex,5) ;
						if (zt!='待服务公司审批') { return '' } ;						 
						var link1 =  $('<a >审批</a>').click(function(e) {				 
							//显示详情	
							var data_status=$(this).attr('data-status');
							$('#cyqyzgzxsp_dialog>p').html(self.spTpl);
							$('#cyqyzgzxsp_dialog').dialog({
								title:'待服务公司审批',
								width:600 ,
								buttons:{
									'审批通过' : function(){
										
										
										$(this).dialog('destroy');
									},
									'审批驳回' : function(){
										
										
										$(this).dialog('destroy');
									}									
								}
							}); 
						});
						return   link1 ;
					}  
				} 			
			} );	
			
			
		 	
		 	
		 	
		 	
		 	
		 	  
		}
	}
}); 

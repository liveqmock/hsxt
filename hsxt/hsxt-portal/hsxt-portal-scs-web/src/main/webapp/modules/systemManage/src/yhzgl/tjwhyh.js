define(['text!systemManageTpl/yhzgl/tjwhyh.html' ,
	        'text!systemManageTpl/yhzgl/tjyh.html'   ],function(tjwhyhTpl,tjyhTpl ){
	return {
		 
		showPage : function(){
			$('#busibox').html(_.template(tjwhyhTpl)) ;			 
			//Todo...
		 	
		 	 
		 	 
		 	 
		 	 
		 	 
		 	 //添加用户
		 	$('#btn_tjyh').click(function(){
		 	 	$('#tjyh_dialog >p').html( _.template( tjyhTpl ) );
		 	 	$('#tjyh_dialog').dialog({width:480,closeIcon:true});		 	 	
		 	});
		 	 
		 	 
		 	 
		 	 
		 	 
		 	var testData =[
			{xh:'1',dlyhm:'John',xm:'张三',zw:'管理公司管理员'   },
			{xh:'2',dlyhm:'John',xm:'张三',zw:'管理公司管理员'   },
			{xh:'3',dlyhm:'John',xm:'张三',zw:'管理公司管理员'   },
			{xh:'4',dlyhm:'John',xm:'张三',zw:'管理公司管理员'   }			
			 ];
			
			var gridObj , self =this;
			 
			gridObj = $.fn.bsgrid.init('detailTable', {				 
				//url : comm.domainList['local']+ comm.UrlList["test1"] , 
				pageSize: 5 ,
				stripeRows: true,  //行色彩分 
				displayBlankRows: false ,   //显示空白行
			  	localData: testData ,
			  	
			  	operate: {
			  		del : function(record, rowIndex, colIndex, options){
			  			var link1 =  $('<a >删除</a>').click(function(e) {							
							//alert(gridObj.getRecordIndexValue(record, 'xh'));
							var confirmObj = {	
								imgFlag:true,
								width:400,
								content : '您确认移除选择的用户名：'+gridObj.getColumnValue(rowIndex , 'dlyhm')   +'？',	
								 													
								callOk :function(){
										//删除记录
										//gridObj.deleteRow( gridObj.getSelectedRowIndex() );
								}
							 }
							 comm.confirm(confirmObj);
							
						});
						return   link1 ;
			  		}  
			  	}
				 		
			} );
		 	 
			
				
		}
	}
}); 

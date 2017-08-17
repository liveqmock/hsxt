define(['text!systemManageTpl/yhzgl/yhzgl.html' ],function(yhzglTpl ){
	return {
		 
		showPage : function(){
			$('#busibox').html(_.template(yhzglTpl)) ;			 
			//Todo...
		 	
		 	//添加
		 	$('#btn_tjyhz').triggerWith('#xtyhgl_xgyhz');
		 	
		 	
		 	var testData =[
			{xh:'1',yhzmc:'用户组1',yhzcy:'张三',yhzms:'这是描述'  },
			{xh:'2',yhzmc:'用户组2',yhzcy:'张三',yhzms:''  },
			{xh:'3',yhzmc:'用户组3',yhzcy:'张三',yhzms:''  },
			{xh:'4',yhzmc:'用户组4',yhzcy:'张三',yhzms:''  },
			{xh:'5',yhzmc:'用户组4',yhzcy:'张三',yhzms:''  }
			];
			
			var gridObj , self =this;
			 
			gridObj = $.fn.bsgrid.init('detailTable', {				 
				//url : comm.domainList['local']+ comm.UrlList["test1"] , 
				pageSize: 5 ,
				stripeRows: true,  //行色彩分 
				displayBlankRows: false ,   //显示空白行
			  	localData: testData ,
			  	operate : {
			  		edit : function(record, rowIndex, colIndex, options){
			  			var link1 =  $('<a >修改</a>').click(function(e) {							
							//alert(gridObj.getRecordIndexValue(record, 'xh'));
							$('#xtyhgl_xgyhz').click();
							
						});
						return   link1 ;
			  		} ,
			  		del : function(record, rowIndex, colIndex, options){
			  			var link1 =  $('<a >删除</a>').click(function(e) {							
							//alert(gridObj.getRecordIndexValue(record, 'xh'));
							var confirmObj = {	
								imgFlag:true,
								width:400,
								content : '确认删除 用户组：'+gridObj.getColumnValue(rowIndex , 'yhzmc')   +'？',	
								 													
								callOk :function(){
										//删除记录
										//gridObj.deleteRow( gridObj.getSelectedRowIndex() );
								}
							 }
							 comm.confirm(confirmObj);
							
						});
						return   link1 ;
			  		} ,
			  		detail :  function(record, rowIndex, colIndex, options){
			  			var link1 =  $('<a >组员维护</a>').click(function(e) {							
							//alert(gridObj.getRecordIndexValue(record, 'xh'));
							$('#xtyhgl_tjwhzy').click();
							
						});
						return   link1 ;
			  		} 
			  		
			  	}
				 		
			} );
		 	
			
				
		}
	}
}); 

 
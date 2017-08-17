define(['text!systemManageTpl/yhzgl/xgyhz.html' ],function(xgyhzTpl ){
	return {
		 
		showPage : function(){
			$('#busibox').html(_.template(xgyhzTpl)) ;			 
			//Todo...
		 	
		 	/*
		 	var testData =[
			{xh:'1',dlyhm:'John',xm:'张三',zw:'',js:'管理公司管理员' ,ssyhz:'用户组1'  },
			{xh:'2',dlyhm:'John',xm:'张三',zw:'',js:'管理公司管理员' ,ssyhz:'用户组2'  },
			{xh:'3',dlyhm:'John',xm:'张三',zw:'',js:'管理公司管理员' ,ssyhz:'用户组3'  },
			{xh:'4',dlyhm:'John',xm:'张三',zw:'',js:'管理公司管理员' ,ssyhz:'用户组3'  }];
			
			var gridObj , self =this;
			 
			gridObj = $.fn.bsgrid.init('detailTable', {				 
				//url : comm.domainList['local']+ comm.UrlList["test1"] , 
				pageSize: 5 ,
				stripeRows: true,  //行色彩分 
				displayBlankRows: false ,   //显示空白行
			  	localData: testData 
				 		
			} );
		 	*/
			
			
			$('#btn_back').triggerWith('#xtyhgl_yhzgl');
				
		}
	}
}); 

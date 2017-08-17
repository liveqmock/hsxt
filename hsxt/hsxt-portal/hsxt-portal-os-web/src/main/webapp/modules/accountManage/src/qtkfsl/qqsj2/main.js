define(['text!accountManageTpl/qtkfsl/qqsj2/main.html' ],function(mxcxTpl ){
	return {
 
		showPage : function(tabid){
			
			//请求数据：2 模板页面及数据的加载
			//$('#main-content > div[data-contentid="'+tabid+'"]').html( mxcxTpl   ) ; 
		    
			var getData = { success : true ,
				 title :'2015学生成绩', 
				 xm : '姓名' ,
				 xb :  '性别' ,
				 km : '科目' ,
				 fs  : '分数' ,			
				 data:[
					{"xm":'张三',"xb":"男","km":"数学","fs":83 },
					{"xm":'李四',"xb":"男","km":"数学","fs":92 },
					{"xm":'王五',"xb":"男","km":"数学","fs":79 }
				] 
			};			
			$('#main-content > div[data-contentid="'+tabid+'"]').html( _.template(mxcxTpl , getData )  ) ; 
		     
		 	   	
			
		    
			
				
		}
	}
}); 

 
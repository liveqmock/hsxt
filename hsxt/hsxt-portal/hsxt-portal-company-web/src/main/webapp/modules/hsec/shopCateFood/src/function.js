	//拦截特殊字符  
	function CheckCode(t) {  
	    var Error = false;  
	    var re = /^[\u4e00-\u9fa5a-z0-9////]+$/gi;  
	    if (!re.test(t) || t.length > 15 || t.length < 1) {  
	        Error = true;  
	    }  
	    return Error;  
	}; 
	
	/**
	 *对response进行解析 
	 */
	function foodResponseParse(response){
		if(response!=null&&response.retCode==200){
			//获得展示的图片
			var listResult=response.data.QueryResult.result;
			var itemVistiUrl=response.data.itemVisitUrl;
			if(listResult==null||listResult=='')
				return response;
			$(listResult).each(function(index,dom){
				//价格展示																
				if(dom.priceRegion!=null&&dom.priceRegion!=''){
					
					var newstr=dom.priceRegion.split(",");									
					response.data.QueryResult.result[index].price=
						comm.formatMoneyNumber(newstr[0])+" ~ "+comm.formatMoneyNumber(newstr[1]);
				}else{
					response.data.QueryResult.result[index].price=
						comm.formatMoneyNumber(dom.price);
				}
				
				//积分展示
				if(dom.pvRegion!=null&&dom.pvRegion!=''){
					var newstr=dom.pvRegion.split(",");
					response.data.QueryResult.result[index].pv=	
						comm.formatMoneyNumber(newstr[0])+" ~ "+comm.formatMoneyNumber(newstr[1]);
				}else{
					response.data.QueryResult.result[index].pv=
						comm.formatMoneyNumber(dom.pv);
				}
				
				var pics=dom.pics;
				var webShowPicUrl="";
				if(pics!=null&&pics!=''){									
					var obj = jQuery.parseJSON(pics);
					if(obj[0]!=null&&obj[0]!=''&&obj[0].web!==null&&obj[0].web!=''){
						var webPic=obj[0].web;								
						$(webPic).each(function(index2,dom2){
							if(webPic.size="110*110"){
								webShowPicUrl=itemVistiUrl+"/"+dom2.name;										
								return false;	//跳出本层循环
							}
						});	
					}				
					
				}
				response.data.QueryResult.result[index].webShowPic=webShowPicUrl;	
				
			});
		}
		return response;
	};
	
	
	
   

//对response 进行解析返回response	
function parseLineLog(repastForm,response){
	var orderStatusLine=null;
	var orderStatusArray=null;	
	var orderInfos = response.data.QueryResult;	
	if(orderInfos.orderStatus=='10'||orderInfos.orderStatus=='99'||orderInfos.orderStatus=='-1'){
		//orderInfos.orderStatus=='-1' 系统过期订单
		orderStatusLine={'1,-3,8':createObject('订单提交成功','请耐心等待商家接单',0),
				'2,9':createObject('已接单','',1),
				'10':createObject('取消订单','待商家确认',2),
				'99':createObject('订单取消','',3),
				'-1':createObject('订单过期系统取消','',4)							
				};	
	}else{
		if(repastForm=='1'){		
			orderStatusLine={'1,-3':createObject('订单提交成功','请耐心等待商家接单',0),
					'2,9':createObject('商家确认接单','备餐中',1),
					'6':createObject('已开台就餐中','待打单',2),
					'7':createObject('已打单','待结账',3),
					'4':createObject('已结账订单完成','',4)							
					};							
		}
		if(repastForm=='2'){							
			 orderStatusLine={'8':createObject('订单提交成功','请耐心等待商家接单',0),
				'2':createObject('备餐中','待送餐',1),
				'11':createObject('送餐中','待消费者确认收货',2),
				'4':createObject('消费者确认收货订单完成','',3)							
				};
			
		}						
		if(repastForm=='3'){						
			 orderStatusLine={'1,8':createObject('订单提交成功','请耐心等待商家接单',0),
				'2':createObject('商家确认接单','待自提',1),		
				'4':createObject('自提订单完成','',2)							
				};	
		}	
	}							
	orderStatusArray=new Array(orderStatusLine.length);
 	for(var obj in orderStatusLine){
 		var index=orderStatusLine[obj].index;
 		orderStatusArray[index]=orderStatusLine[obj];
	}			
 	//解析response
	var lineLogs = response.data.oprateLogList;								
	if(null!=lineLogs){
		$.each(lineLogs,function(key,el){
			var statusKey;
			if(orderInfos.orderStatus=='10'||orderInfos.orderStatus=='99'||orderInfos.orderStatus=='-1'){
				if(el.statusAfter=='1'||el.statusAfter=='-3'||el.statusAfter=='8') statusKey='1,-3,8';											
				else if(el.statusAfter=='2'||el.statusAfter=='9') statusKey='2,9';
				else statusKey=el.statusAfter;	
			}else{
				if(repastForm=='1'){
					if(el.statusAfter=='1'||el.statusAfter=='-3') statusKey='1,-3';											
					else if(el.statusAfter=='2'||el.statusAfter=='9') statusKey='2,9';
					else statusKey=el.statusAfter;		
					
				};
				if(repastForm=='2'){
					statusKey=el.statusAfter;
				};
				if(repastForm=='3'){
					if(el.statusAfter=='1'||el.statusAfter=='8') statusKey='1,8';	
					else statusKey=el.statusAfter;	
				};
				//手机端下单特殊处理
				if(el.statusAfter=='9'){
					
					if(orderStatusLine['1,-3']!= undefined){
						orderStatusLine['1,-3'].time=el.registTime;
						orderStatusLine['1,-3'].onThisStep=true;
						orderStatusLine['1,-3'].status=el.statusAfter;
						orderStatusArray[orderStatusLine['1,-3'].index]=orderStatusLine['1,-3'];
					}	
						
				};
			}
			//替换数组中的对象
			if(orderStatusLine[statusKey]!= undefined){
				orderStatusLine[statusKey].time=el.registTime;
				orderStatusLine[statusKey].onThisStep=true;
				orderStatusLine[statusKey].status=el.statusAfter;
				orderStatusArray[orderStatusLine[statusKey].index]=orderStatusLine[statusKey];
			}	
		});
		
		//取消订单 开始	
		//多余步骤过滤
		if(orderInfos.orderStatus=='10'||orderInfos.orderStatus=='99'||orderInfos.orderStatus=='-1'){	
//			orderStatusLine={'1,-3,8':createObject('订单提交成功','请耐心等待商家接单',0),
//					'2,9':createObject('卖家已接单','待收货',1),
//					'10':createObject('买家取消订单','待商家确认',2),
//					'99':createObject('取消订单','',3),
//					'-1':createObject('订单过期系统取消','',4)							
//					};
			var orderStatusArray2=new Array();	
			if(orderInfos.orderStatus=='10'||orderInfos.orderStatus=='99'){						
				//商家没接单的取消的订单
				if(orderStatusArray[1].onThisStep==false){				
					//orderStatusArray[3].name="订单取消"											
					$.each(orderStatusArray,function(key,el){
						if(key==0||key==3)orderStatusArray2.push(el);
					});									
				}else{
					//orderStatusArray[3].name="订单取消";
					$.each(orderStatusArray,function(key,el){
						if(key==0||key==1||key==3) orderStatusArray2.push(el);
					});
				}
			}else{
				//过期订单
				$.each(orderStatusArray,function(key,el){
					if(key==0||key==4) orderStatusArray2.push(el);
				});
			}		
			orderStatusArray=orderStatusArray2;
		}
		//取消订单 结束
	}  
	response.data.orderStatusArray=orderStatusArray;							
	return response;
}	

//订单流水对象
function createObject(name,alertMsg,index){
	var object=new Object();   
	object.name=name;
	object.time='';
	object.alertMsg=alertMsg;
	object.onThisStep=false;
	object.index=index;	//序号
	object.status=''; //状态
	return object;
}
		

/**
 *对response进行解析得到图片url 
 */
function foodResponseParse(response){
	if(response!=null&&response.retCode==200){
		//获得展示的图片
		var listResult=response.data.QueryResult.orderDetailList;
		var itemVistiUrl=response.data.itemVisitUrl;
		if(listResult==null||listResult=='')
			return response;
		$(listResult).each(function(index,dom){	
			var pics=dom.picUrl;
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
			response.data.QueryResult.orderDetailList[index].webShowPic=webShowPicUrl;	
			
		});
	}
	return response;
};
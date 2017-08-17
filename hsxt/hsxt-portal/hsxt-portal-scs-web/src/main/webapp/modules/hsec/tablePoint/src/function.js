//转换小数
function myParseFloat(obj){
	if(comm.isEmpty(obj)||isNaN(obj))
		return parseFloat('0.00');
	else 
		return parseFloat(obj);
}

function myToFixed(obj){
	var num = new Number(obj);
	var num2=parseFloat(num.toFixed(1));
	return num2.toFixed(2);
}

//检查输入为数字,且必须大于0 
function checkInputNumber(obj){
	var num = new Number(obj);
	return (!isNaN(num)&&(num>0));
}


//正整数校验
function checkInputInt(obj){
	var r = /^\+?[1-9][0-9]*$/;	//正整数校验
	return r.test(obj);
}
		
//日期插件
function loadDatePicker(beginId,endId){		
	$(beginId).datepicker(
			{	
				dateFormat: 'yy-mm-dd',
	        yearSuffix: '年', //年的后缀  
	        showMonthAfterYear:true,//是否把月放在年的后面
	        monthNames: ['一月','二月','三月','四月','五月','六月','七月','八月','九月','十月','十一月','十二月'],  
            dayNames: ['星期日','星期一','星期二','星期三','星期四','星期五','星期六'],  
            dayNamesShort: ['周日','周一','周二','周三','周四','周五','周六'],  
            dayNamesMin: ['日','一','二','三','四','五','六'],
            "maxDate":new Date(),
            onSelect:function(dateText,inst){
	        $("#endTime").datepicker("option","minDate",dateText);
	    	}
		}
	 );
	$(endId).datepicker(
	  {		
		  dateFormat: 'yy-mm-dd',
	        yearSuffix: '年', //年的后缀  
	        showMonthAfterYear:true,//是否把月放在年的后面
	        monthNames: ['一月','二月','三月','四月','五月','六月','七月','八月','九月','十月','十一月','十二月'],  
            dayNames: ['星期日','星期一','星期二','星期三','星期四','星期五','星期六'],  
            dayNamesShort: ['周日','周一','周二','周三','周四','周五','周六'],  
            dayNamesMin: ['日','一','二','三','四','五','六'],
            "maxDate":new Date(),
            onSelect:function(dateText,inst){
	        $("#beginTime").datepicker("option","maxDate",dateText);
		    }
		 });			
}	
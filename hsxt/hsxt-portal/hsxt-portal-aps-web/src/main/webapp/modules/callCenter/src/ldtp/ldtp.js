define(['text!callCenterTpl/ldtp/ldtp.html',
	'jquerydialogr'
], function(ldtpTpl){
	return {
		showPage : function(tel,resno,call_record_list){

			$('#ldtp_dlg').html(ldtpTpl);

			$('#ldtp_dlg').dialogr({
				title:"来电号码："+tel+" 积分卡号："+resno,  //设置弹屏title
				width:"1200",
				height:"800",
				autoOpen:false
			});

			//页面加载数据，包含来电号码，来电资源号，来电用户名称
			$("#latp_tel").html(tel);
			$("#ldtp_resno").val(resno);
			$("#ldtp_username").val("雷亚涛");

			/**
			 * 来电历史数据绑定
			 */
			var gridObj = $.fn.bsgrid.init('thlsjlTable', {
				//url : comm.domainList['local']+comm.UrlList['tgqycsyw'],
				// autoLoad: false,
				pageSizeSelect: true ,
				pageSize: 10 ,
				stripeRows: true,  //行色彩分隔 
				displayBlankRows: false ,   //显示空白行
				localData:call_record_list
			});

			/*end*/

			/*弹出框*/
			/*$( "#ldtp_dlg" ).dialog({
			 title:"来电号码：6433 积分卡号：06186010006",
			 width:"1200",
			 height:"800",
			 modal:true,
			 closeIcon : true,
			 buttons:{
			 "关闭":function(){
			 $( this ).dialog( "destroy" );
			 }
			 }
			 });*/



			$('#ldtp_dlg').dialogr('open');
			$('.ui-dialog ').css('border', '1px solid #b3c4ae');
			/*end*/





		},   
		/* 2016-06-14
         * 右侧拨号盘下记录来电信息(无效互生号)
         * author:taojy
         */ 
        recordUnkown: function (tel,resno) {
    	   //保留在cookie是完整的信息
           var fullRecord = tel+'-'+resno;	   
           //直接看到的是截取过的,防止用户名过长滚动条出现时内容会挤压变形
           var showRecord = (25<fullRecord.length ? fullRecord.substring(0,23)+'..' : fullRecord);
            
          	var content ="";
          	//toLocalDateString在IE中是年月日分割方式返回,google中是/分割方式返回
          	var date = new Date().toLocaleDateString();
          	var key =  $.cookie("custId")+date.replaceAll('/','').replaceAll('年','').replaceAll('月','').replaceAll('日','');
          	var value = comm.getCookie(key);
          	var record = "<li style='list-style-type:none'><span title='"+fullRecord+"'><font color='#2E8B57'>"+showRecord+"</font></span></li>";
          	
          	if(null!=value){
          		var cookieRecord = value.split(';');
          		var cookieLeng = cookieRecord.length;
          		var showLimit = 20;
          		var i= (cookieLeng< showLimit? cookieLeng : showLimit);
          		for(j=0; j<i; j++){
          			var cookieFullVal = cookieRecord[j];
          			//有效长度是25,超过上下层内容会重叠
          			var cookieShowVal = 25<cookieFullVal.length ? cookieFullVal.substring(0,23)+'..' : cookieFullVal;
          			content = content + "<li style='list-style-type:none'><span title='"+cookieFullVal+"'><font color='#2E8B57'>"+cookieShowVal+"</font></span></li>";
          		}
          		content = record+content;
          		comm.setSelfDefCookie(key,fullRecord+';'+value,1);
          	}else{
          		comm.setSelfDefCookie(key,fullRecord,1);
          		content=record;
          	}
              	
          	var callList = $("#callHistoryList");
          	callList.children().remove();
          	callList.addClass(".record_list");
          	callList.html(content);
 
         }
	}
});

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





		}
	}
});

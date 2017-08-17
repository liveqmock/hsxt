define(["text!commTpl/laidiantanping.html","commSrc/frame/top"],function(tpl,top){
	var userid=$.cookie("pointNo")+"_"+$.cookie("custName");  //用户ID
	var username=$.cookie("custName");	//登录名
	var opername=comm.getCookie("operName");	//操作者
	if(!-[1,]){
		//设置用户名
		if(Test.JS_SetUserName(userid,opername)==1){
			/*comm.alert("用户名设置成功，当前用户为："+opername);*/
		}else{
			comm.warn_alert("呼叫中心用户名设置失败，请检查此用户姓名等信息是否完整",800);
		}
	}else{
		//comm.warn_alert("当前浏览器不支持呼叫中心部分功能,如果需要测试呼叫中心相关功能</br>1.请在本机安装ocx插件，具体可咨询王占康.</br>2.请使用IE8或以上内核浏览器进行测试，如果测试其他功能请忽略",580,300);
	}


	var txtnum="";//坐席号
	var callHistory_Tab=null;
	var RemakeCardBusiness_DateTbl=null;
	var jifenfuliapplyDataTable=null;

	var ret="";	//操作结果提示
	var dialing = {
		tpl:tpl,
		initData: function(){ // 初始化

			document.getElementById("login").disabled=false;	//签入
			document.getElementById("logout").disabled=true;    //签出
			document.getElementById("setuserbusy").disabled=true;    //置忙
			document.getElementById("setuserquiet").disabled=true;    //静音
			// document.getElementById("setuserfree").disabled=true;    //空闲
			document.getElementById("trunktransferuser").disabled=true;    //转内
			document.getElementById("trunkcalltrunk").disabled=true;    //转外
			/* document.getElementById("stoptrunkcalltrunk").disabled=true;    //停止转外
			 document.getElementById("monitoruser").disabled=true;    //座席监听
			 document.getElementById("stopusercalltrunk").disabled=true;    //停止 座席外呼
			 document.getElementById("usercalltrunk").disabled=true;    //座席外呼
			 */

		},

		JSLogIn:function(){  //签入804 完成
			//userid=$('#sEntResNo').html()+"_"+$('#sUserAccount').html();
			txtnum=Test.JS_GetUserNumByMac(Test.JS_GetMac());//获取坐席号
			if(comm.isEmpty(txtnum)){
				comm.alert("请输入座席号！");
				return false;
			}
			comm.alert("签入前"+userid);
			ret = Test.JS_LogIn(userid, txtnum);

			//如果ret为1 签入成功
			if(ret=="1"){
				document.getElementById("login").disabled=true;	//签入
				document.getElementById("logout").disabled=false;    //签出
				document.getElementById("setuserbusy").disabled=false;    //置忙
				document.getElementById("setuserquiet").disabled=false;    //静音
				// document.getElementById("setuserfree").disabled=false;    //空闲
				document.getElementById("trunktransferuser").disabled=false;    //转内
				document.getElementById("trunkcalltrunk").disabled=false;    //转外
				/*	   document.getElementById("stoptrunkcalltrunk").disabled=false;    //停止转外
				 document.getElementById("monitoruser").disabled=false;    //座席监听
				 document.getElementById("stopusercalltrunk").disabled=false;    //停止 座席外呼
				 document.getElementById("usercalltrunk").disabled=false;    //座席外呼
				 */
				ret="成功！";
			}else{
				ret="失败!";
			}
			comm.alert("签入"+ret);

		},
		JS_LogOut : function(){//20141229  签出    完成

			// var txtnum=dialing.gettxtnumByMac();
			if(comm.isEmpty(txtnum)){
				comm.alert("请输入座席号！");
				return false;
			}
			ret = Test.JS_LogOut(userid, txtnum);
			if(ret=="1"){
				$("#serviceSubNavBt li").eq(2).children().text("置忙");
				$("#serviceSubNavBt li").eq(2).children().attr("id","setuserbusy");
				$("#serviceSubNavBt li").eq(2).children().attr("disabled","true");

				$("#serviceSubNavBt li").eq(5).children().text("静音");
				$("#serviceSubNavBt li").eq(5).children().attr("id","setuserquiet");
				$("#serviceSubNavBt li").eq(5).children().attr("disabled","true");

				document.getElementById("login").disabled=false;	//签入
				document.getElementById("logout").disabled=true;    //签出
				//document.getElementById("setuserbusy").disabled=true;    //置忙
				// document.getElementById("setuserquiet").disabled=true;    //静音
				//document.getElementById("setuserfree").disabled=true;    //空闲
				document.getElementById("trunktransferuser").disabled=true;    //转内
				document.getElementById("trunkcalltrunk").disabled=true;    //转外
				/* document.getElementById("stoptrunkcalltrunk").disabled=true;    //停止转外
				 document.getElementById("monitoruser").disabled=true;    //座席监听
				 document.getElementById("stopusercalltrunk").disabled=true;    //停止 座席外呼
				 document.getElementById("usercalltrunk").disabled=true;    //座席外呼
				 */
				ret="成功！";
				txtnum="";
			}else{
				ret="失败!";
			}
			comm.alert("签出"+ret);
		},
		JS_SetUserBusy:function() { //20141229    置忙   完成
			// var txtnum=dialing.gettxtnumByMac();
			if(comm.isEmpty(txtnum)){
				comm.alert("请输入座席号！");
				return false;
			}
			ret = Test.JS_SetUserBusy(txtnum);
			if(ret=="1"){
				//修改置忙  为空闲
				$("#setuserbusy").text("空闲");
				$("#setuserbusy").attr("id","setuserfree");
				document.getElementById("login").disabled=true;	//签入
				document.getElementById("logout").disabled=true;    //签出
				//document.getElementById("setuserbusy").disabled=true;    //置忙
				document.getElementById("setuserquiet").disabled=true;    //静音
				document.getElementById("setuserfree").disabled=false;    //空闲
				document.getElementById("trunktransferuser").disabled=true;    //转内
				document.getElementById("trunkcalltrunk").disabled=true;    //转外
				/* document.getElementById("stoptrunkcalltrunk").disabled=true;    //停止转外
				 document.getElementById("monitoruser").disabled=true;    //座席监听
				 document.getElementById("stopusercalltrunk").disabled=true;    //停止 座席外呼
				 document.getElementById("usercalltrunk").disabled=true;    //座席外呼
				 */
				ret="成功！";
			}else{
				ret="失败!";
			}
			comm.alert("置忙"+ret);
		},
		JS_SetUserFree:function() {  //20141229  停止静音
			// var txtnum=dialing.gettxtnumByMac();
			//alert("texnum===="+txtnum);
			if(comm.isEmpty(txtnum)){
				comm.alert("请输入座席号！");
				return false;
			}
			var zhiman= $("#serviceSubNavBt").children().find('a').eq(2).html(); //置忙 的空闲
			var jingyin=$("#serviceSubNavBt").children().find('a').eq(5).html();//静音的空闲
			var temp="";
			if(zhiman=="空闲"){
				temp="空闲";
				$("#setuserfree").text("置忙");
				$("#setuserfree").attr("id","setuserbusy");
				ret = Test.JS_SetUserFree(txtnum);
			}else if(jingyin=="通话"){
				temp="停止静音";
				$("#setuserfree").text("静音");
				$("#setuserfree").attr("id","setuserquiet");
				ret =Test.JS_SetUserTalk(txtnum);
			}
			if(ret=="1"){
				document.getElementById("login").disabled=true;	//签入
				document.getElementById("logout").disabled=false;    //签出
				document.getElementById("setuserbusy").disabled=false;    //置忙
				document.getElementById("setuserquiet").disabled=false;    //静音
				//document.getElementById("setuserfree").disabled=true;    //空闲
				document.getElementById("trunktransferuser").disabled=false;    //转内
				document.getElementById("trunkcalltrunk").disabled=false;    //转外
				/*    	   document.getElementById("stoptrunkcalltrunk").disabled=false;    //停止转外
				 document.getElementById("monitoruser").disabled=false;    //座席监听
				 document.getElementById("stopusercalltrunk").disabled=false;    //停止 座席外呼
				 document.getElementById("usercalltrunk").disabled=false;    //座席外呼
				 */
				ret="成功！";
			}else{
				document.getElementById("logout").disabled=false;		//签入
				ret="失败!";
			}
			comm.alert(temp+ret);
		},
		JS_SetUserQuiet:function(){   //20141229  静音
			// var txtnum=dialing.gettxtnumByMac();
			if(comm.isEmpty(txtnum)){
				comm.alert("请输入座席号！");
				return false;
			}
			ret = Test.JS_SetUserQuiet(txtnum);
			if(ret=="1"){
				$("#setuserquiet").text("通话");
				$("#setuserquiet").attr("id","setuserfree");
				document.getElementById("login").disabled=true;	//签入
				document.getElementById("logout").disabled=false;    //签出
				document.getElementById("setuserbusy").disabled=true;    //置忙
				// document.getElementById("setuserquiet").disabled=true;    //静音
				document.getElementById("setuserfree").disabled=false;    //空闲
				document.getElementById("trunktransferuser").disabled=true;    //转内
				document.getElementById("trunkcalltrunk").disabled=true;    //转外
				/*		    	   document.getElementById("stoptrunkcalltrunk").disabled=true;    //停止转外
				 document.getElementById("monitoruser").disabled=true;    //座席监听
				 document.getElementById("stopusercalltrunk").disabled=true;    //停止 座席外呼
				 document.getElementById("usercalltrunk").disabled=true;    //座席外呼
				 */
				ret="成功！";
			}else{
				ret="失败!";
			}
			comm.alert("静音"+ret);
		},
		JS_TrunkTransferUser:function(inputVal) {  //20141230  转内
			// var txtnum=dialing.gettxtnumByMac();
			if(comm.isEmpty(txtnum)){
				comm.alert("请输入座席号！");
				return false;
			}
			document.getElementById("login").disabled=true;	//签入
			document.getElementById("logout").disabled=false;    //签出
			document.getElementById("setuserbusy").disabled=false;    //置忙
			document.getElementById("setuserquiet").disabled=false;    //静音
			// document.getElementById("setuserfree").disabled=false;    //空闲
			document.getElementById("trunktransferuser").disabled=false;    //转内
			document.getElementById("trunkcalltrunk").disabled=false;    //转外
			ret = Test.JS_TrunkTransferUser(txtnum, inputVal);
			if(ret=="1"){
				//$("#trunktransferuser").text("停止");
				//$("#trunktransferuser").attr("id","stoptrunkcalltrunk1");
				//document.getElementById("login").disabled=true;	//签入
				//document.getElementById("logout").disabled=false;    //签出
				//document.getElementById("setuserbusy").disabled=false;    //置忙
				//document.getElementById("setuserquiet").disabled=false;    //静音
				//// document.getElementById("setuserfree").disabled=false;    //空闲
				//document.getElementById("stoptrunkcalltrunk1").disabled=false;    //转内
				//document.getElementById("trunkcalltrunk").disabled=false;    //转外
				ret="成功！";
			}else{
				ret="失败!";
			}
			comm.alert("转内"+ret);
		},
		JS_TrunkCallTrunk:function(inputVal){//20141229  转外  ， 转外线好吗规则 9加逗号隔开，如    9,13168788240
			if(comm.isEmpty(inputVal)){
				comm.alert("请输入转外号码");
				return;
			}
			comm.alert("转外号码"+dialing.getTrunkCallTrunkNumber(inputVal))
			if(comm.isEmpty(txtnum)){
				comm.alert("请输入座席号！");
				return false;
			}
			document.getElementById("login").disabled=true;	//签入
			document.getElementById("logout").disabled=false;    //签出
			document.getElementById("setuserbusy").disabled=false;    //置忙
			document.getElementById("setuserquiet").disabled=false;    //静音
			//document.getElementById("setuserfree").disabled=true;    //空闲
			document.getElementById("trunktransferuser").disabled=false;    //转内
			 document.getElementById("trunkcalltrunk").disabled=false;    //转外
			//document.getElementById("stoptrunkcalltrunk").disabled=false;    //停止
			ret = Test.JS_TrunkCallTrunk(txtnum,dialing.getTrunkCallTrunkNumber(inputVal));
			if(ret=="1"){
				//$("#trunkcalltrunk").text("停止");
				//$("#trunkcalltrunk").attr("id","stoptrunkcalltrunk");
				//document.getElementById("login").disabled=true;	//签入
				//document.getElementById("logout").disabled=false;    //签出
				//document.getElementById("setuserbusy").disabled=false;    //置忙
				//document.getElementById("setuserquiet").disabled=false;    //静音
				////document.getElementById("setuserfree").disabled=true;    //空闲
				//document.getElementById("trunktransferuser").disabled=false;    //转内
				//// document.getElementById("trunkcalltrunk").disabled=true;    //转外
				//document.getElementById("stoptrunkcalltrunk").disabled=false;    //停止
				ret="成功！";
			}else{
				ret="失败!";
			}
			comm.alert("转外"+ret);
		},
		JS_StopTrunkCallTrunk:function() { //20141229   停止转外  
			//var txtnum=dialing.gettxtnumByMac();
			if(comm.isEmpty(txtnum)){
				comm.alert("请输入座席号！");
				return false;
			}
			ret = Test.JS_StopTrunkCallTrunk(txtnum);
			if(ret=="1"){
				$("#stoptrunkcalltrunk").text("转外");
				$("#stoptrunkcalltrunk").attr("id","trunkcalltrunk");
				document.getElementById("login").disabled=true;	//签入
				document.getElementById("logout").disabled=false;    //签出
				document.getElementById("setuserbusy").disabled=false;    //置忙
				document.getElementById("setuserquiet").disabled=false;    //静音
				//document.getElementById("setuserfree").disabled=false;    //空闲
				document.getElementById("trunktransferuser").disabled=false;    //转内
				document.getElementById("trunkcalltrunk").disabled=false;    //转外
				ret="成功！";
			}else{
				ret="失败!";
			}
			comm.alert("停止转外"+ret);
		},JS_StopTrunkCallTrunk1:function() { //   停止转内  
			//var txtnum=dialing.gettxtnumByMac();
			if(comm.isEmpty(txtnum)){
				comm.alert("请输入座席号！");
				return false;
			}
			document.getElementById("login").disabled=true;	//签入
			document.getElementById("logout").disabled=false;    //签出
			document.getElementById("setuserbusy").disabled=false;    //置忙
			document.getElementById("setuserquiet").disabled=false;    //静音
			//document.getElementById("setuserfree").disabled=false;    //空闲
			document.getElementById("trunktransferuser").disabled=false;    //转内
			document.getElementById("trunkcalltrunk").disabled=false;    //转外
			//ret = Test.JS_StopTrunkCallTrunk(txtnum);
			//if(ret=="1"){
			//	//$("#stoptrunkcalltrunk1").text("转内");
			//	//$("#stoptrunkcalltrunk1").attr("id","trunktransferuser");
			//	document.getElementById("login").disabled=true;	//签入
			//	document.getElementById("logout").disabled=false;    //签出
			//	document.getElementById("setuserbusy").disabled=false;    //置忙
			//	document.getElementById("setuserquiet").disabled=false;    //静音
			//	//document.getElementById("setuserfree").disabled=false;    //空闲
			//	document.getElementById("trunktransferuser").disabled=false;    //转内
			//	document.getElementById("trunkcalltrunk").disabled=false;    //转外
			//	ret="成功！";
			//}else{
			//	ret="失败!";
			//}
			//comm.alert("停止转内"+ret);
		},
		JS_MonitorUser:function() {	//   监听坐席
			// var txtnum=dialing.gettxtnumByMac();
			if(comm.isEmpty(txtnum)){
				comm.alert("请输入座席号！");
				return false;
			}
			ret = Test.JS_MonitorUser(txtnum);
			if(ret=="1"){
				//document.getElementById("monitoruser").value="监听中";
				// document.getElementById("monitoruser").disabled=true;    //座席监听
				ret="成功！";
			}else{
				ret="失败!";
			}
			comm.alert("监听座席"+ret);
		},
		JS_UserCallTrunk:function(zwphone) { //20141229   座席外呼
			// var txtnum=dialing.gettxtnumByMac();
			if(comm.isEmpty(txtnum)){
				comm.alert("请输入座席号！");
				return false;
			}
			ret = Test.JS_UserCallTrunk(txtnum,zwphone); //zwphone="9,,18126273309" 按照规则定义参数
			if(ret=="1"){
				/* document.getElementById("login").disabled=true;	//签入
				 document.getElementById("logout").disabled=false;    //签出
				 document.getElementById("setuserbusy").disabled=true;    //置忙
				 document.getElementById("setuserquiet").disabled=true;    //静音
				 document.getElementById("setuserfree").disabled=true;    //空闲
				 document.getElementById("trunktransferuser").disabled=true;    //转内
				 document.getElementById("trunkcalltrunk").disabled=true;    //转外
				 document.getElementById("stoptrunkcalltrunk").disabled=true;    //停止转外
				 document.getElementById("monitoruser").disabled=false;    //座席监听
				 document.getElementById("stopusercalltrunk").disabled=false;    //停止 座席外呼
				 document.getElementById("usercalltrunk").disabled=true;    //座席外呼
				 */
				ret="成功！";
			}else{
				comm.callCenter_error(ret);
			}
		},
		JS_StopUserCallTrunk:function() {		//  停止外呼
			//  var txtnum=dialing.gettxtnumByMac();
			if(comm.isEmpty(txtnum)){
				comm.alert("请输入座席号！");
				return false;
			}

			ret = Test.JS_StopUserCallTrunk(txtnum);
			if(ret=="1"){/*
			 document.getElementById("login").disabled=true;	//签入
			 document.getElementById("logout").disabled=false;    //签出
			 document.getElementById("setuserbusy").disabled=false;    //置忙
			 document.getElementById("setuserquiet").disabled=false;    //静音
			 document.getElementById("setuserfree").disabled=false;    //空闲
			 document.getElementById("trunktransferuser").disabled=false;    //转内
			 document.getElementById("trunkcalltrunk").disabled=false;    //转外
			 document.getElementById("stoptrunkcalltrunk").disabled=false;    //停止转外
			 document.getElementById("monitoruser").disabled=false;    //座席监听
			 document.getElementById("stopusercalltrunk").disabled=false;    //停止 座席外呼
			 document.getElementById("usercalltrunk").disabled=false;    //座席外呼
			 ret=ret+"成功！";
			 */}else{
				ret="失败!";
			}
			comm.alert("停止座席外呼"+ret);
		},
		/**
		 * 转外或呼外 在号码前面添加 9,,
		 * @param inputVal
		 * @returns {string}
		 */
		getTrunkCallTrunkNumber:function(inputVal){
			return comm.getPrefix(inputVal);
		},
		answer:function(inputVal){   //拨号
			if(comm.isEmpty(inputVal)){
				comm.alert("请输入电话号码！");
				return;
			}
			dialing.JS_UserCallTrunk(dialing.getTrunkCallTrunkNumber(inputVal));

		}
	

	};
	//取当前登录userid
	//dialing.getUserId(dialing.getUserCache);
	return dialing;
});
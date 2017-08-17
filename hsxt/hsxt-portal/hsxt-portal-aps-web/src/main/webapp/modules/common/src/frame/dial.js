define(["text!commTpl/laidiantanping.html","commSrc/frame/top"],function(tpl,top){


	var userid="";  //用户ID
	 var txtnum="";//坐席号
	var callHistory_Tab=null;
	var RemakeCardBusiness_DateTbl=null;
	var jifenfuliapplyDataTable=null;
	 //坐席号

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
		gettxtnumByMac:function(){
			var txtnum1="";
			 //获取mac地址
			 var mac=Test.JS_GetMac();
			 //根据mac获取坐席号
				$.ajax({
					url :  comm.domainList['server']+comm.UrlList["qrygetAgentNumByHard"],
					type : 'POST',
					async:false,
					data:{"hardwareCode":mac},
					success : function(responseData){
						txtnum1=responseData.date;
					}});
		   return txtnum1;

		},
		JSLogIn:function(){  //签入804 完成
				 txtnum=dialing.gettxtnumByMac();
					if(txtnum==""||txtnum==null){
						comm.alert("请输入座席号！");
			    		return false;
			    	}
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
		 	if(txtnum==""||txtnum==null){
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
		       }else{
		    	   ret="失败!";
		       }
			comm.alert("签出"+ret);
		},	
		JS_SetUserBusy:function() { //20141229    置忙   完成
			// var txtnum=dialing.gettxtnumByMac();
		     	if(txtnum==""||txtnum==null){
					comm.alert("请输入座席号！");
		     		return false;
		     	}
			  ret = Test.JS_SetUserBusy(txtnum);
			  if(ret=="1"){
				 //修改置忙  为空闲
				  $("#setuserbusy").text("空闲");
				  $("#setuserbusy").attr("id","setuserfree");
		    	   document.getElementById("login").disabled=true;	//签入	
		    	   document.getElementById("logout").disabled=false;    //签出	
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
		JS_SetUserFree:function() {  //20141229  空闲
			// var txtnum=dialing.gettxtnumByMac();
		     	//alert("texnum===="+txtnum);
		     	if(txtnum==""||txtnum==null){
					comm.alert("请输入座席号！");
		     		return false;
		     	}
		     	  var zhiman= $("#serviceSubNavBt").children().find('button').eq(2).html(); //置忙 的空闲
				  var jingyin=$("#serviceSubNavBt").children().find('button').eq(5).html();//静音的空闲
				  if(zhiman=="空闲"){
					  $("#setuserfree").text("置忙");
					  $("#setuserfree").attr("id","setuserbusy");
					  ret = Test.JS_SetUserFree(txtnum);
				  }else if(jingyin=="空闲"){
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
			comm.alert("空闲"+ret);
		},
		JS_SetUserQuiet:function(){   //20141229  静音
			// var txtnum=dialing.gettxtnumByMac();
		     	if(txtnum==""||txtnum==null){
					comm.alert("请输入座席号！");
		     		return false;
		     	}
			  ret = Test.JS_SetUserQuiet(txtnum);
			  if(ret=="1"){
				  $("#setuserquiet").text("空闲");
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
		     if(txtnum==""||txtnum==null){
				 comm.alert("请输入座席号！");
		     		return false;
		     }
		  ret = Test.JS_TrunkTransferUser(txtnum, inputVal);
		  if(ret=="1"){
			  $("#trunktransferuser").text("停止");
			  $("#trunktransferuser").attr("id","stoptrunkcalltrunk1");
	    	   document.getElementById("login").disabled=true;	//签入	
	    	   document.getElementById("logout").disabled=false;    //签出	
	    	   document.getElementById("setuserbusy").disabled=false;    //置忙
	    	   document.getElementById("setuserquiet").disabled=false;    //静音
	    	  // document.getElementById("setuserfree").disabled=false;    //空闲
	    	   document.getElementById("stoptrunkcalltrunk1").disabled=false;    //转内
	    	   document.getElementById("trunkcalltrunk").disabled=true;    //转外
	  /*  	   document.getElementById("stoptrunkcalltrunk").disabled=true;    //停止转外
	    	   document.getElementById("monitoruser").disabled=false;    //座席监听
	    	   document.getElementById("stopusercalltrunk").disabled=true;    //停止 座席外呼
	    	   document.getElementById("usercalltrunk").disabled=true;    //座席外呼
	*/	    	   
	    	   ret="成功！";
	       }else{
	    	   ret="失败!";
	       }
			comm.alert("转内"+ret);
		},
		JS_TrunkCallTrunk:function(inputVal){//20141229  转外  ， 转外线好吗规则 9加逗号隔开，如    9,13168788240
		  //var txtnum=dialing.gettxtnumByMac();
		  if(txtnum==""||txtnum==null){
			  comm.alert("请输入座席号！");
			  return false;
		  }
		  ret = Test.JS_TrunkCallTrunk(txtnum,dialing.getTrunkCallTrunkNumber(inputVal));
		  if(ret=="1"){    	
			  $("#trunkcalltrunk").text("停止");
			  $("#trunkcalltrunk").attr("id","stoptrunkcalltrunk");
	    	   document.getElementById("login").disabled=true;	//签入	
	    	   document.getElementById("logout").disabled=true;    //签出	
	    	   document.getElementById("setuserbusy").disabled=true;    //置忙
	    	   document.getElementById("setuserquiet").disabled=true;    //静音
	    	   //document.getElementById("setuserfree").disabled=true;    //空闲
	    	   document.getElementById("trunktransferuser").disabled=true;    //转内
	    	  // document.getElementById("trunkcalltrunk").disabled=true;    //转外
	    	   document.getElementById("stoptrunkcalltrunk").disabled=false;    //停止
	/*	    	   document.getElementById("stoptrunkcalltrunk").disabled=false;    //停止转外
	    	   document.getElementById("monitoruser").disabled=false;    //座席监听
	    	   document.getElementById("stopusercalltrunk").disabled=true;    //停止 座席外呼
	    	   document.getElementById("usercalltrunk").disabled=true;    //座席外呼
	*/	    	   
	    	   ret="成功！";
	       }else{
	    	   ret="失败!";
	       }
		  comm.alert("转外"+ret);
		},
		JS_StopTrunkCallTrunk:function() { //20141229   停止转外  
			 //var txtnum=dialing.gettxtnumByMac();
			  if(txtnum==""||txtnum==null){
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
		/*	    	   document.getElementById("stoptrunkcalltrunk").disabled=true;    //停止转外
		    	   document.getElementById("monitoruser").disabled=false;    //座席监听
		    	   document.getElementById("stopusercalltrunk").disabled=false;    //停止 座席外呼
		    	   document.getElementById("usercalltrunk").disabled=false;    //座席外呼
		*/	    	   
		    	   ret="成功！";
		       }else{
		    	   ret="失败!";
		       }
			  comm.alert("停止转外"+ret);
		},JS_StopTrunkCallTrunk1:function() { //   停止转内  
			 //var txtnum=dialing.gettxtnumByMac();
			  if(txtnum==""||txtnum==null){
				  comm.alert("请输入座席号！");
				  return false;
			  }	
			 // ret = Test.JS_StopTrunkCallTrunk(txtnum);
			  ret="1";
			  if(ret=="1"){   	   
				  $("#stoptrunkcalltrunk1").text("转内");
				  $("#stoptrunkcalltrunk1").attr("id","trunktransferuser");
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
			  comm.alert("停止转内"+ret);
		},
		JS_MonitorUser:function() {	//20141229 / 20141230   监听坐席 
			 // var txtnum=dialing.gettxtnumByMac();
			  if(txtnum==""||txtnum==null){
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
			  if(txtnum==""||txtnum==null){
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
		    	   ret="失败!";
		       }
			  comm.alert("座席外呼"+ret);
		},
		JS_StopUserCallTrunk:function() {		//20141229  停止外呼   (2015-1-20未改)
			//  var txtnum=dialing.gettxtnumByMac();
			  if(txtnum==""||txtnum==null){
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
		getTrunkCallTrunkNumber:function(inputVal){ //在号码前面添加 9,,
			//var inputVal=$(".dial_input_").val();
			//var subStr=inputVal.substring(0,1);
			//var inputValSub=inputVal.substring(1,inputVal.length);
			//inputValSub="9,,"+inputValSub
				return inputVal;
		},
		showModal:function(phoneNo,s2,resNo) {
			//渲染数据
			//显示历史通话记录列表
			dialing.qryGetCallerRemarksByResNo(phoneNo, s2, resNo);
			//显示基本信息
			$.ajax({
				url :  comm.domainList['server']+comm.UrlList["qryGetBaseInfo"],
				type : 'POST',		 
				data:{"resNo":resNo,"phoneNo":phoneNo},
				success : function(responseData) {
					//移除相同的元素
					$("#recordList li").each(function(e){
						if($(this).attr("data")==phoneNo){
							$(this).remove();
						}
					});
					//保存历史及时来电 缓存中
					$("#recordList_li").after("<li data="+phoneNo+"><a>"+phoneNo+"&nbsp;&nbsp;"+responseData.date.personName+"</a></li>");
					//显示滚动条
					$('.listScroll').jScrollPane();
					//拨号记录选中高亮背景
					$("#recordList").children().off("click").click(function(){
						$(this).siblings().removeClass("record_list_bg");
						$(this).addClass("record_list_bg");
						$("#dial_input").val($("#dial_input").val()+""+$(this).attr("data"));
					});
					var strTitle="资源号";
					//根据resType 判断显示那个div 
					// 06186010001 消费者(CUSTTYPE_P)  06186010000 企业(CUSTTYPE_C) 06186000000 服务公司(CUSTTYPE_S)
					var resType=responseData.date.resType;
					switch(resType){
					case "消费者" :
						strTitle="积分卡号";
						$("#account_Div1").removeClass("none");
						$("#account_Div2").addClass("none");
						$("#account_Div3").addClass("none");
						$("#account_Div4").addClass("none");
						break;
					case "托管企业" :
						strTitle="企业管理号";
						$("#account_Div1").addClass("none");
						$("#account_Div2").removeClass("none");
						$("#account_Div3").addClass("none");
						$("#account_Div4").addClass("none");
						break;
					case "服务公司" :
						strTitle="服务公司";
						$("#account_Div1").addClass("none");
						$("#account_Div2").addClass("none");
						$("#account_Div4").addClass("none");
						$("#account_Div3").removeClass("none");
						break;
					case "成员企业" :
						strTitle="成员企业";
						$("#account_Div1").addClass("none");
						$("#account_Div2").addClass("none");
						$("#account_Div3").addClass("none");
						$("#account_Div4").removeClass("none");
						break;
					};
					//显示基本信息
					$("#phoneNoTd").text(phoneNo);
					$("#resNoTd").text(resNo);
					$("#NameTd").text(responseData.date.personName);
                    $("#autonymBinding_A").off("click").click(function(e){
                    	dialing.hideDiv(e,"autonym_Div");
                    	 $("#autonym_Div").text(responseData.date.verifyStatus=="Y"?"已绑定":"未绑定");
                    });
                    $("#emailBinding_A").off("click").click(function(e){
                    	 	dialing.hideDiv(e,"email_Div");
                        $("#email_Div").text(responseData.date.emailFlag=="Y"?"已绑定":"未绑定");
                    });	
                    $("#phoneBinding_A").off("click").click(function(e){
                   	dialing.hideDiv(e,"phone_Div");
                    	$("#phone_Div").text(responseData.date.phoneFlag=="Y"?"已绑定":"未绑定");
                    });
                    $("#bankBinding_A").off("click").click(function(e){
                    dialing.hideDiv(e,"bank_Div");
                    	$("#bank_Div").text(responseData.date.isBindBank=="Y"?"已绑定":"未绑定");
                    });
                    $("#certification_A").off("click").click(function(e){
                    	dialing.hideDiv(e,"certification_Div");
                    	 $("#certification_Div").text(responseData.date.verifyStatus=="Y"?"已办理":"未办理");
                    });
                    $("#businessStatus_A").off("click").click(function(e){
                    	dialing.hideDiv(e,"businessStatus_Div");
                    	 $("#businessStatus_Div").text(responseData.date.isAuth=="Y"?"已绑定":"未绑定");
                    });
                /*    $("#autonym_Div").text(responseData.date.verifyStatus=="Y"?"已绑定":"未绑定");
                    $("#bank_Div").text(responseData.date.isBindBank=="Y"?"已绑定":"未绑定");
                    $("#email_Div").text(responseData.date.emailFlag=="Y"?"已绑定":"未绑定");
                    $("#phone_Div").text(responseData.date.phoneFlag=="Y"?"已绑定":"未绑定");
                    $("#certification_Div").text(responseData.date.verifyStatus=="Y"?"已认证":"未认证");
                    $("#businessStatus_Div").text(responseData.date.isAuth=="Y"?"已办理":"未办理");*/
                 // 06186010001 消费者(CUSTTYPE_P)  06186010000 企业(CUSTTYPE_C) 06186000000 服务公司(CUSTTYPE_S)
                   // dialing.bindingclick("95105105","803","06186000000");
                    
                    $("#laidiantanping").dialogr({ autoOpen: false, maximized:true, title:"来电号码:"+phoneNo+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+strTitle+":"+resNo, minWidth: 500, height: 600, width: 1100 }); 
       			     $("#laidiantanping").dialogr('open');  
				}});
		},
		answer:function(inputVal){   //拨号
			if(inputVal==null||inputVal==""){
				comm.alert("请输入电话号码！");
				return;
			}
			/*
			//根据输入号码 判断是转外还是转内  803 转内 93366322525 转外
			var subStr=inputVal.substring(0,1);
			var inputValSub=inputVal.substring(1,inputVal.length);
		   if(subStr=="9"){
			   dialing.JS_TrunkCallTrunk(inputValSub);
				//alert("转外呼叫");
			}else{
				dialing.JS_TrunkTransferUser(inputVal);
				//alert("转内呼叫");
			}
		*/
			dialing.JS_UserCallTrunk(dialing.getTrunkCallTrunkNumber(inputVal));
		
		},
		bindingclick:function(phoneNo,s2,resNo){  //点击事件 初始化
			//移除事件
		    $("#popup_div_A a").each(function(){
			    $(this).unbind();
			  });
		    //详情 渲染 数据
		   $("#particulars_A").off("click").click(function(){	
			  var data= dialing.qryGetDetailInfoByResNo(phoneNo, s2, resNo);
     // 06186010001 消费者(CUSTTYPE_P)  06186010000/06186000015 托管/成员企业(CUSTTYPE_C/CUSTTYPE_B) 06186000000 服务公司(CUSTTYPE_S)
			  switch(data.date.base.resType){
			  case "服务公司" :
				  	$.ajax({
					url :  comm.domainList['server']+comm.UrlList["qryGetBaseInfo"],
					type : 'POST',
					data:{"resNo":resNo,"phoneNo":phoneNo},
					success : function(responseData) {
						 dialing.showdetails(2,responseData);
					}});
				  break;
			  case "消费者" :
				  	$.ajax({
						url :  comm.domainList['server']+comm.UrlList["qryGetBaseInfo"],
						type : 'POST',
						data:{"resNo":resNo,"phoneNo":phoneNo},
						success : function(responseData) {
							 dialing.showdetails(6,responseData);
						}});
				  break;
			  default :
				  //托管企业 和  成员企业 
				  	$.ajax({
						url :  comm.domainList['server']+comm.UrlList["qryGetBaseInfo"],
						type : 'POST',
						data:{"resNo":resNo,"phoneNo":phoneNo},
						success : function(responseData) {
							 dialing.showdetails(4,responseData);
						}});
				 
				  break;
			
			  
			  /*
				case "消费者" :
					//显示消费者个人信息详情
					var sex=data.date.base.sex==1?"男":"女";
					var creType="其他";//1身份证 2 护照 3 营业执照
					switch (data.date.base.creType) {
					case '1':
						creType="身份证";
						break;
					case '2':
						creType="护照";
						break;
					case '3':
						creType="营业执照";
						break;
					}
					var verifyStatus=data.date.base.verifyStatus=='Y'?"已验证":"未认证";
					var emailFlag=data.date.base.emailFlag=='Y'?"已验证":"未认证";
					var strConsumen="<td align=\"center\" class=\"nobd\">"+data.date.base.personName+"</td>";
					strConsumen+="<td align=\"center\">"+sex+"</td>";
					strConsumen+="<td align=\"center\">中国</td>";
					strConsumen+="<td align=\"center\">"+creType+"</td>";
					strConsumen+="<td align=\"center\">"+data.date.base.custId+"</td>";
					strConsumen+="<td align=\"center\">"+data.date.base.contactAddr+"</td>";
					strConsumen+="<td align=\"center\">"+verifyStatus+"</td>";
					strConsumen+="<td align=\"center\">"+data.date.base.emailAddr+"</td>";
					strConsumen+="<td align=\"center\">"+emailFlag+"</td>";
                    $("#consumer_Tr").html(strConsumen);
					
                    var strConsumer_Info="<td align=\"center\" class=\"nobd\">"+data.date.base.personName+"</td>";
                    strConsumer_Info+="<td align=\"center\">"+data.date.base.contactAddr+"</td>";
                    strConsumer_Info+="<td align=\"center\">"+data.date.base.personName+"</td>";
                    strConsumer_Info+="<td align=\"center\">"+data.date.base.phoneCode+"</td>";
                    $("#consumer_Info").html(strConsumer_Info);
                    
					  $("#xiaofeizhe").dialogr({ autoOpen: false, maximized:true, title:"来电号码:"+phoneNo+"&nbsp;&nbsp;积分卡号:"+resNo, minWidth: 500, height: 600, width: 1100 }); 
		   			     $("#xiaofeizhe").dialogr('open');
					break;
				case "托管企业" :
					//企业信息
					var strUndertaking="<td align=\"center\" class=\"nobd\">"+data.date.base.personName+"</td>";
						strUndertaking+="<td align=\"center\">"+data.date.base.contactAddr+"</td>";
						strUndertaking+="<td align=\"center\">"+data.date.base.personName+"</td>";
						strUndertaking+="<td align=\"center\">"+data.date.base.phoneCode+"</td>";
						strUndertaking+="<td align=\"center\">托管企业</td>";
					$("#undertaking_Tr").html(strUndertaking);
					var strService="<td align=\"center\" class=\"nobd\">"+data.date.S.personName+"</td>";
					strService+="<td align=\"center\">"+data.date.S.contactAddr+"</td>";
					strService+="<td align=\"center\">"+data.date.S.personName+"</td>";
					strService+="<td align=\"center\">"+data.date.S.phoneCode+"</td>";
					$("#service_Tr").html(strService);
					
					var strCorporation_Info="<td align=\"center\" class=\"nobd\">"+data.date.base.companyNameCN+"</td>";
					strCorporation_Info+="<td align=\"center\">"+data.date.base.contactAddr+"</td>";
					strCorporation_Info+="<td align=\"center\">"+data.date.base.personName+"</td>";
					strCorporation_Info+="<td align=\"center\">"+data.date.base.mobile+"</td>";
					$("#corporation_Info").html(strCorporation_Info);
					
				    $("#qiye").dialogr({ autoOpen: false, maximized:true, title:"来电号码:"+phoneNo+"&nbsp;&nbsp;企业管理号:"+resNo, minWidth: 500, height: 600, width: 1100 }); 
	   			     $("#qiye").dialogr('open');
					break;
				case "服务公司" :
					
					//服务公司信息
					var service_Tr="<td align=\"center\" class=\"nobd\">"+data.date.base.personName+"</td>";
					service_Tr+="<td align=\"center\">"+data.date.base.contactAddr+"</td>";
					service_Tr+="<td align=\"center\">"+data.date.base.personName+"</td>";
					service_Tr+="<td align=\"center\">"+data.date.base.phoneCode+"</td>";
					service_Tr+="<td align=\"center\">服务公司</td>";
					$("#services_Tr").html(service_Tr);
					var service_Info="<td align=\"center\" class=\"nobd\">"+data.date.base.companyNameCN+"</td>";
					service_Info+="<td align=\"center\">"+data.date.base.contactAddr+"</td>";
					service_Info+="<td align=\"center\">"+data.date.base.personName+"</td>";
					service_Info+="<td align=\"center\">"+data.date.base.mobile+"</td>";
					$("#service_Info").html(service_Info);
				    $("#service_Cust").dialogr({ autoOpen: false, maximized:true, title:"来电号码:"+phoneNo+"&nbsp;&nbsp;企业管理号:"+resNo, minWidth: 500, height: 600, width: 1100 }); 
	   			     $("#service_Cust").dialogr('open');
					break;
				case "成员企业" :
					//企业信息
					var strUndertaking="<td align=\"center\" class=\"nobd\">"+data.date.base.personName+"</td>";
						strUndertaking+="<td align=\"center\">"+data.date.base.contactAddr+"</td>";
						strUndertaking+="<td align=\"center\">"+data.date.base.personName+"</td>";
						strUndertaking+="<td align=\"center\">"+data.date.base.phoneCode+"</td>";
						strUndertaking+="<td align=\"center\">成员企业</td>";
					$("#undertaking_Tr").html(strUndertaking);
					var strService="<td align=\"center\" class=\"nobd\">"+data.date.S.personName+"</td>";
					strService+="<td align=\"center\">"+data.date.S.contactAddr+"</td>";
					strService+="<td align=\"center\">"+data.date.S.personName+"</td>";
					strService+="<td align=\"center\">"+data.date.S.phoneCode+"</td>";
					$("#service_Tr").html(strService);
					
					var strCorporation_Info="<td align=\"center\" class=\"nobd\">"+data.date.base.companyNameCN+"</td>";
					strCorporation_Info+="<td align=\"center\">"+data.date.base.contactAddr+"</td>";
					strCorporation_Info+="<td align=\"center\">"+data.date.base.personName+"</td>";
					strCorporation_Info+="<td align=\"center\">"+data.date.base.mobile+"</td>";
					$("#corporation_Info").html(strCorporation_Info);
					
				    $("#qiye").dialogr({ autoOpen: false, maximized:true, title:"来电号码:"+phoneNo+"&nbsp;&nbsp;企业管理号:"+resNo, minWidth: 500, height: 600, width: 1100 }); 
	   			     $("#qiye").dialogr('open');
					break;
					
				*/};
				});
			
			//消费者 div 事件 
			$("#integralAccount_A1").click(function(e) { // 积分账户
				dialing.getIntegralActInfo(phoneNo,s2,resNo,function(getIntegralActInfo){
						dialing.hideDiv(e,"integralAccount_Div1");
	        	$("#integralAccount_Div1").html("积分账户余数："+getIntegralActInfo.date.residualIntegral+" <br/>可用积分余数："+getIntegralActInfo.date.usableIntegral);
			
				});
			});
			$("#cashAccount_A1").click(function(e) { // 货币账户
				dialing.qryGetCashActInfo(phoneNo, s2, resNo, function(getCashActInfo){
				dialing.hideDiv(e,"cashAccount_Div1");
	        	$("#cashAccount_Div1").html("货币账户余额："+getCashActInfo.date.cash);
				});
			});
			$("#investAccount_A1").click(function(e) { // 投资账户
				dialing.qryGetInvestActAmount(phoneNo, s2, resNo,function(getInvestActAmount){
						dialing.hideDiv(e,"investAccount_Div1");
	        	$("#investAccount_Div1").html("投资积分总数："+getInvestActAmount.date.investIntegralTotal+"<br/>本年度投资分红总数:"+getInvestActAmount.date.dividentsTotal);
			
				});
			});
			$("#alternateCurrency_A1").click(function(e) { // 互生币账户
				dialing.qryGetHsbTransferCash(phoneNo, s2, resNo,function(getHsbTransferCash){
						dialing.hideDiv(e,"alternateCurrency_Div1");
	        	$("#alternateCurrency_Div1").html("流通币余额："+getHsbTransferCash.date.hsbOperatingIncome+"<br/>定向消费币余额："+getHsbTransferCash.date.hsbCharitableAidFund);
			
				});
			});
			//消费者 ===========结束
			
			//托管企业 事件
	        $("#integralAccount_A2").click(function(e) { // 积分账户
	        	dialing.getIntegralActInfo(phoneNo,s2,resNo,function(getIntegralActInfo){
	        		dialing.hideDiv(e,"integralAccount_Div2");
		        	$("#integralAccount_Div2").html("积分账户余数："+getIntegralActInfo.date.residualIntegral+" <br/>可用积分余数："+getIntegralActInfo.date.usableIntegral);
				
	        	});
	        	});
			$("#cashAccount_A2").click(function(e) { // 现金账户
				dialing.qryGetCashActInfo(phoneNo, s2, resNo, function(getCashActInfo){
				dialing.hideDiv(e,"cashAccount_Div2");
			   $("#cashAccount_Div2").html("货币账户余额："+getCashActInfo.date.cash);
				});
			});
			$("#investAccount_A2").click(function(e) { // 投资账户
				dialing.qryGetInvestActAmount(phoneNo, s2, resNo,function(getInvestActAmount){
				dialing.hideDiv(e,"investAccount_Div2");
               $("#investAccount_Div2").html("投资积分总数："+getInvestActAmount.date.invest+"<br/>本年度投资分红总数:"+getInvestActAmount.date.thetotalOfInvestmentDividends);
				});
			});
			$("#advanceAccount_A2").click(function(e) { // 积分预付款
				dialing.qryGetPrepaidIntegralAct(phoneNo, s2, resNo,function(getPrepaidIntegralAct){
						dialing.hideDiv(e,"advanceAccount_Div2");
				$("#advanceAccount_Div2").html("积分预付款账户余额："+getPrepaidIntegralAct.date.prepaidIntegralActBalance);
			
				});
			});
			$("#alternateCurrency_A2").click(function(e) { // 互生币账户
				dialing.qryGetHsbTransferCash(phoneNo, s2, resNo,function(getHsbTransferCash){
				dialing.hideDiv(e,"alternateCurrency_Div2");
				$("#alternateCurrency_Div2").html("流通币余额："+getHsbTransferCash.date.hsbOperatingIncome+"<br/>慈善救助基金余额："+getHsbTransferCash.date.hsbCharitableAidFund);
				});
			});
			$("#commercialService_A2").click(function(e) { // 商业账户
				dialing.qryGetCommIncBalance(phoneNo, s2, resNo,function(qryGetCommIncBalance){
						dialing.hideDiv(e,"commercialService_Div2");
				$("#commercialService_Div2").html("城市税收对接账户余额："+qryGetCommIncBalance.date.cityTaxJointActBalance);
			
				});
			});
			//托管企业 结束
			//成员企业 事件
	        $("#integralAccount_A4").click(function(e) { // 积分账户
	        	dialing.getIntegralActInfo(phoneNo,s2,resNo,function(getIntegralActInfo){
	        		dialing.hideDiv(e,"integralAccount_Div4");
		        	$("#integralAccount_Div4").html("积分账户余数："+getIntegralActInfo.date.residualIntegral+" <br/>可用积分余数："+getIntegralActInfo.date.usableIntegral);
	        	});
	        });
			$("#cashAccount_A4").click(function(e) { // 货币账户
				dialing.qryGetCashActInfo(phoneNo, s2, resNo, function(getCashActInfo){
				dialing.hideDiv(e,"cashAccount_Div4");
			   $("#cashAccount_Div4").html("货币账户余额："+getCashActInfo.date.cash);
				});
			});
			$("#alternateCurrency_A4").click(function(e) { // 互生币账户
				dialing.qryGetHsbTransferCash(phoneNo, s2, resNo,function(getHsbTransferCash){
				dialing.hideDiv(e,"alternateCurrency_Div4");
				$("#alternateCurrency_Div4").html("流通币余额："+getHsbTransferCash.date.hsbTransferCash);
				});
			});
			$("#advanceAccount_A4").click(function(e) { // 积分预付款
				dialing.qryGetPrepaidIntegralAct(phoneNo, s2, resNo,function(getPrepaidIntegralAct){
				dialing.hideDiv(e,"advanceAccount_Div4");
				$("#advanceAccount_Div4").html("积分预付款账户余额："+getPrepaidIntegralAct.date.prepaidIntegralActBalance);
				});
			});
			$("#commercialService_A4").click(function(e) { // 商业账户
				dialing.qryGetCommIncBalance(phoneNo, s2, resNo,function(qryGetCommIncBalance){
				dialing.hideDiv(e,"commercialService_Div4");
				$("#commercialService_Div4").html("城市税收对接账户余额："+qryGetCommIncBalance.date.cityTaxJointActBalance);
			});
			});
			//成员企业 结束
			
			//服务公司
		     $("#integralAccount_A3").click(function(e) { // 积分账户
		    	 dialing.getIntegralActInfo(phoneNo,s2,resNo,function(getIntegralActInfo){
		    	 dialing.hideDiv(e,"integralAccount_Div3");
		        	$("#integralAccount_Div3").html("积分账户余数："+getIntegralActInfo.date.residualIntegral+" <br/>可用积分余数："+getIntegralActInfo.date.usableIntegral);
				});
		     });
			$("#cashAccount_A3").click(function(e) { // 现金账户
				dialing.qryGetCashActInfo(phoneNo, s2, resNo, function(getCashActInfo){
									 dialing.hideDiv(e,"cashAccount_Div3");
		        	$("#cashAccount_Div3").html("货币账户余额："+getCashActInfo.date.cash);
					});
				});
			$("#investAccount_A3").click(function(e) { // 投资账户
				dialing.qryGetInvestActAmount(phoneNo, s2, resNo,function(getInvestActAmount){
				 dialing.hideDiv(e,"investAccount_Div3");
		        	$("#investAccount_Div3").html("投资积分总数："+getInvestActAmount.date.invest+"<br/>本年度投资分红总数:"+getInvestActAmount.date.totalOfInvestmentDividends);
				});
			});
			$("#advanceAccount_A3").click(function(e) { // 积分预付款
				dialing.qryGetPrepaidIntegralAct(phoneNo, s2, resNo,function(getPrepaidIntegralAct){
				 dialing.hideDiv(e,"advanceAccount_Div3");
		        	$("#advanceAccount_Div3").html("积分预付款账户余额："+getPrepaidIntegralAct.date.prepaidIntegralActBalance);
				});
			});
			$("#alternateCurrency_A3").click(function(e) { // 互生币账户
				dialing.qryGetHsbTransferCash(phoneNo, s2, resNo,function(getHsbTransferCash){
				 dialing.hideDiv(e,"alternateCurrency_Div3");
		        	$("#alternateCurrency_Div3").html("流通币余额："+getHsbTransferCash.date.hsbTransferCash+"<br/>慈善救助基金余额："+getHsbTransferCash.date.socialAssistanceFund);
				});
			});
			$("#businessAccount_A3").click(function(e) { // 商业账户
				dialing.qryGetCommIncBalance(phoneNo, s2, resNo,function(qryGetCommIncBalance){
				 dialing.hideDiv(e,"businessAccount_Div3");
		        	$("#businessAccount_Div3").html("城市税收对接账户余额："+qryGetCommIncBalance.date.cityTaxJointActBalance);
			});
			});
			//服务公司结束
			
			//通话记录保存按钮
			$("#callHistorySave_A").off("click").click(function(){
				dialing.callHistorySave(phoneNo, s2, resNo);
			});
		},
		callHistorySave:function(phoneNo,s2,resNo){ //历史通话记录保存
			var callHistoryName=$("#callHistoryName").val();//用户名称
			var callHistoryType=$("input[name='callHistoryType']:checked").val();//类型 0 运维类  1 商务类 2 诉建类
			var callHistoryRemark=$("#callHistoryRemark").val();//备注
			var callHistoryRank=$("input[name='callHistoryRank']:checked").val();//优先级  0  高  1 低
			var callerRemark={
					"callingTime":new Date(),
					"callerNum":phoneNo,
					"resNo":resNo,
					"callerName":callHistoryName,
					"type":"1",
					"busiType":callHistoryType,
					"description":callHistoryRemark,
					"level":callHistoryRank,
					"status":"1"
			};
			
			$.ajax({
				url :  comm.domainList['server']+comm.UrlList["addCallerRemark"],
				type : 'POST',
				data:callerRemark,
				success : function(responseData){
					if(responseData.code==0){
						
						callHistory_Tab.draw();
						$("#callHistoryFrom")[0].reset();
						comm.alert("保存成功!");
					}
					
				}});
		},
		hideDiv:function(e,divId){	 //隐藏其他div  显示当前层
			var currentClick = $(e.currentTarget).parent().siblings().children("div");
			//e.stopPropagation();
		     currentClick.addClass("none");
		     //$("#"+divId).toggleClass("none");
		     if( $("#"+divId).hasClass('none')){
		    	 $("#"+divId).removeClass("none");
		    	 
		     }else{
		    	 $("#"+divId).addClass("none");
		     }
			},
	    getIntegralActInfo:function(phoneNo,s2,resNo,getIntegralActInfo){ //查询积分账户
	    	$.ajax({
				url :  comm.domainList['server']+comm.UrlList["qryGetIntegralActInfo"],
				type : 'POST',
				data:{"resNo":resNo,"phoneNo":phoneNo},
				success : function(responseData) {
					getIntegralActInfo(responseData);
				}});
	    },
	    qryGetCashActInfo:function(phoneNo,s2,resNo,getCashActInfo){//现金账户信息
	    	$.ajax({
				url :  comm.domainList['server']+comm.UrlList["qryGetCashActInfo"],
				type : 'POST',
				data:{"resNo":resNo,"phoneNo":phoneNo},
				success : function(responseData) {
					getCashActInfo(responseData);
				}});
	    },
	    qryGetInvestActAmount:function(phoneNo,s2,resNo,getInvestActAmount){//投资账户信息
	    	$.ajax({
				url :  comm.domainList['server']+comm.UrlList["qryGetInvestActAmount"],
				type : 'POST',
				data:{"resNo":resNo,"phoneNo":phoneNo},
				success : function(responseData) {
					getInvestActAmount(responseData);
				}});
	    },
	    qryGetHsbTransferCash:function(phoneNo,s2,resNo,getHsbTransferCash){//互生币账户信息
	    	$.ajax({
				url :  comm.domainList['server']+comm.UrlList["qryGetHsbTransferCash"],
				type : 'POST',
				data:{"resNo":resNo,"phoneNo":phoneNo},
				success : function(responseData) {
					getHsbTransferCash(responseData);
				}});
	    },
	    qryGetPrepaidIntegralAct:function(phoneNo,s2,resNo,getPrepaidIntegralAct){//积分预付款信息
	    	$.ajax({
				url :  comm.domainList['server']+comm.UrlList["qryGetPrepaidIntegralAct"],
				type : 'POST',
				data:{"resNo":resNo,"phoneNo":phoneNo},
				success : function(responseData) {
					getPrepaidIntegralAct(responseData);
				}});
	    },
	    qryGetSaleIncBalance:function(phoneNo,s2,resNo){//销售收入账户
	    	var data=null;
	    	$.ajax({
				url :  comm.domainList['server']+comm.UrlList["qryGetSaleIncBalance"],
				type : 'POST',
				async:false,
				data:{"resNo":resNo,"phoneNo":phoneNo},
				success : function(responseData) {
					data= responseData;
				}});
	    	return data;
	    },
	    qryGetCommIncBalance:function(phoneNo,s2,resNo,getCommIncBalance){//商业服务收入账户
	    	$.ajax({
				url :  comm.domainList['server']+comm.UrlList["qryGetCommIncBalance"],
				type : 'POST',
				data:{"resNo":resNo,"phoneNo":phoneNo},
				success : function(responseData) {
					getCommIncBalance(responseData);
				}});
	    },
	    qryGetCallerRemarksByResNo:function(phoneNo,s2,resNo){//历史通话列表

			//列表显示
			if (callHistory_Tab != null) {
				$("#callHistory_Tab").DataTable().destroy();
			}
			callHistory_Tab = $("#callHistory_Tab").DataTable(
					{
						"bJQueryUI" : true,
						"bFilter" : false,
						"sPaginationType" : "full_numbers",
						"sDom" : '<""l>t<"F"fp>',
						"processing" : true,
						"serverSide" : true,
						"bAutoWidth" : false, // 自适应宽度
						"bLengthChange" : false,
						"bSort":false,
						//'bStateSave' : true,
						"oLanguage" : {
							// "sLengthMenu" : "每页显示 _MENU_条",
							"sZeroRecords" : "没有找到符合条件的数据",
							"sInfo" : "当前第 _START_ - _END_ 条　共计 _TOTAL_ 条",
							"sInfoEmpty" : "没有记录",
							"sInfoFiltered" : "(从 _MAX_ 条记录中过滤)",
							"sSearch" : "搜索：",
							"oPaginate" : {
								"sFirst" : "首页",
								"sPrevious" : "上一页",
								"sNext" : "下一页",
								"sLast" : "尾页"
							}
						},
						"ajax" : {
							"url" : comm.domainList['server']+comm.UrlList["qryGetCallerRemarksByResNo"],
							"type" : "POST",
							"data":{"resNo":resNo}
						},
						"aoColumns" : [ {
							"data" : "callingTime"
						},{ 
							"data" : "callerName"
						}, {
							"data" : "description"
						}, {
							"data" : "busiType"
						}, {
							"data" : "type"
						},{
							"data" : "status"
						}, {
							"data" : "level"
						}],
						"columnDefs" : [
										{
											"targets" : [ 3 ],
											"render" : function(data,
													type, full) {
												// 0 运维类 1 商务类 2 诉建类
												var str = "诉建类";
												if (data == '0') {
													str = "运维类";
												} else if (data == '1') {
													str = "商务类";
												}
												return str;
											}
										},
										{
											"targets" : [ 5 ],
											"render" : function(data,
													type, full) {
												return data = "0" ? "处理中"
														: "已完成";
											}
										},
										{
											"targets" : [ 6 ],
											"render" : function(data,
													type, full) {
												// 0  高  1 低
												return data == "0" ? "高"
														: "低";
											}
										}
						]
					});
	    },
	    qryGetDetailInfoByResNo:function(phoneNo,s2,resNo){//弹框详情
	    	var data="";
	    	$.ajax({
				url :  comm.domainList['server']+comm.UrlList["qryGetDetailInfoByResNo"],
				type : 'POST',
				async:false,
				data:{"resNo":resNo},
				success : function(responseData) {
					data= responseData;
				}});
	    	return data;
	    },
	   showDetails:function(index){ //根据企业类型显示详情信息
		   // 0 内部  2服务 4 企业 6  消费者
		   $(".service_icon_search").off("click").click(function(){
			   
				var strDiv=	"<div class=\"h28 border_bottom_gray\"><div class=\"w230 bc clearfix\">";
				 strDiv+="</div></div><div class=\"h28 border_bottom_gray\"><div class=\"w230 bc clearfix\">";
				 strDiv+="</div></div><div class=\"h28 border_bottom_gray\"><div class=\"w230 bc clearfix\">";
				 strDiv+="</div></div><div class=\"h28\"><div class=\"w230 bc clearfix\"></div></div>";
			   //搜索条件
			  var resNos= $("#search_input_1_"+index).val();
			  dialing.isCheckResNo(index,resNos,strDiv,function(resNo){
				$.ajax({
					url :  comm.domainList['server']+comm.UrlList["qryGetBaseInfo"],
					type : 'POST',
					data:{"resNo":resNo,"phoneNo":""},
					success : function(responseData) {
						if(responseData.date.code!=1){
							var strName=responseData.date.companyNameCN;
							var strNewName=responseData.date.companyNameCN;
							if(strName.length>15){
								strName=strName.substring(0,15)+"....";
							}
							
							var strConactPerson=responseData.date.contactPerson;
							var strNewConactPerson=responseData.date.contactPerson;
							if(strConactPerson.length>3){
								strConactPerson=strConactPerson.substring(0,3)+"....";
							}
							
							strDiv="";
							if(responseData.date.resType!="消费者"){
						    strDiv="<div class=\"h28 border_bottom_gray\"><div class=\"w230 bc clearfix\"><i class=\"service_icon service_icon_mobil mr5 fl\"></i>";
							strDiv+="<span class=\"fb orange mr10 fl\" id=\"phone_1_"+index+"\">"+responseData.date.mobile+"</span>";
							strDiv+="<span class=\"gray fl\" id=\"name_1_"+index+"\" title=\""+strNewConactPerson+"\">"+strConactPerson+"</span>";
							strDiv+="<span class=\"fr\"><a  class=\"service_icon service_icon_microphone fr ml10\" id=\"microphone_1_"+index+"\"></a>";
							strDiv+="<a class=\"service_icon service_icon_email_1 fr\" id=\"email_1_"+index+"\"></a></span></div></div>";
						    strDiv+="<div class=\"h28 border_bottom_gray\"><div class=\"w230 bc clearfix\"><i class=\"service_icon service_icon_phone mr5 fl\"></i>";
							strDiv+="<span class=\"fb orange mr10 fl\" id=\"plane_1_"+index+"\">"+responseData.date.phoneCode+"</span><a  class=\"service_icon service_icon_microphone fl\" id=\"plane_1_"+index+"_A\"></a></div>";
						    strDiv+="</div><div class=\"h28 border_bottom_gray\"><div class=\"w230 bc clearfix\" id=\"details_1_"+index+"\" >";
							strDiv+="<span class=\"fb\">"+responseData.date.resNo+"</span><span class=\"gray ml10\">"+responseData.date.resType+"</span><span class=\"gray ml10\">"+responseData.date.baseStatus+"</span></div></div><div class=\"h28\"><div class=\"w230 bc clearfix\">";
							strDiv+="<span class=\"gray fl\" id=\"custName_1_"+index+"\" title=\""+strNewName+"\">"+strName+"</span><span class=\"fr\">";
							strDiv+="<a class=\"service_icon service_icon_other fr\" id=\"custName_1_"+index+"_other\"></a>";
							/*strDiv+="<a class=\"service_icon service_icon_voice fr ml5 mr5\" id=\"custName_1_"+index+"_voice\"></a>";
							strDiv+="<a class=\"service_icon service_icon_email_2 fr\" id=\"custName_1_"+index+"_email\"></a>";*/
							strDiv+="</span></div></div>";
							}else{
								strDiv="<div class=\"h28 border_bottom_gray\"><div class=\"w200 bc clearfix\">";
								strDiv+="<i class=\"service_icon service_icon_mobil mr5 fl\"></i>";
								strDiv+="<span class=\"fb orange mr10 fl\" id=\"phone_1_"+index+"\">"+responseData.date.mobile+"</span>";
								strDiv+="<span class=\"gray fl\" id=\"name_1_"+index+"\" title=\""+strNewConactPerson+"\">"+strConactPerson+"</span>";
								strDiv+="<span class=\"fr\"><a  class=\"service_icon service_icon_microphone fr ml10\" id=\"microphone_1_"+index+"\"></a>";
								strDiv+="<a class=\"service_icon service_icon_email_1 fr\" id=\"email_1_"+index+"\"></a></span></div></div><div class=\"h28 border_bottom_gray\">";
								strDiv+="<div class=\"w200 bc clearfix\"><span class=\"fb fl\">"+responseData.date.resNo+"</span>";
								var str1="",str2="",str3="";
			/*					if(responseData.date.custName!=""){
									str1="<i class=\"fl service_icon service_icon_bd ml20\" title=\"已实名绑定\"></i>";
								}else{
									str1="<i class=\"fl service_icon service_icon_bd_disable ml20\" title=\"未实名绑定\"></i>";
								}*/
								if(responseData.date.isReg=="Y"){
									str2="<i class=\"fl service_icon service_icon_zc ml5\" title=\"已实名注册\"></i>";
								}else{
									str2="<i class=\"fl service_icon service_icon_zc_disable ml5\" title=\"未实名注册\"></i>";
								}
								//是否实名认证 (U-未申请,W-待审批,N-审批驳回,Y-审批通过。)
								if(responseData.date.isAuth=="Y"){
									str3="<i class=\"fl service_icon service_icon_rz ml5\" title=\"已实名认证\"></i>";
								}else{
									str3="<i class=\"fl service_icon service_icon_rz_disable ml5\" title=\"未实名认证\"></i>";
								}
								strDiv+=str1+str2+str3;
								strDiv+="<span class=\"gray ml20 fl\">"+responseData.date.activeStatus+"</span>";
								strDiv+="</div></div><div class=\"h28\"><div class=\"w200 bc clearfix\">";
								strDiv+="<span class=\"gray fl\" id=\"custName_1_"+index+"\" title=\""+strNewName+"\">"+strName+"</span><span class=\"fr\">";
								strDiv+="<a class=\"service_icon service_icon_other fr\" id=\"custName_1_"+index+"_other\"></a>";
								strDiv+="</span></div></div>";
							}
							
							$("#serviceContent_Div1_"+index).html(strDiv); 
							//手机号码拨号
							$("#plane_1_"+index+"_A").off("click").click(function(){
								//调用拨号盘 拨号
								dialing.answer($("#plane_1_"+index).text());
						   });
							
							//电话号码拨号
							$("#microphone_1_"+index).off("click").click(function(){
								//调用拨号盘 拨号
								dialing.answer($("#phone_1_"+index).text());
						   });
							//邮箱
							$("#email_1_"+index).off("click").click(function(){
								comm.alert("邮箱");
						   });
							//详情
							$("#custName_1_"+index+"_other").off("click").click(function(){
								dialing.showdetails(index,responseData);//渲染数据
							});
						}else if(resNo==null||resNo==""){
							comm.alert("请输入正确资源号....");
						}else{

							comm.alert("没有查到有效数据!"+responseData.date.msg);
							$("#serviceContent_Div1_"+index).html(strDiv); 
						}
					}});
			  });
		   });
	   },
	   showGroupUl:function(){
		   dialing.qryGetDetpAgents(function(responseData){
			   var strGroup="";
			   $.each(responseData.aaData,function(index,data) {   
				    strGroup+="<h3><ul class=\"clearfix\"><li><i class=\"service_icon service_icon_folder\"></i></li>";
					strGroup+="<li class=\"f14 serviceItem_text\">"+data.deptName+"</li>";
					strGroup+="<li><i class=\"serviceItem_icon icon_serviceItem_04\"></i></li></ul></h3>";
					var strUl="";
		           $.each(data.agentsUsers,function(indexs,datas) {  
		        	   if(indexs<1){
		        	   strUl+="<ul class=\"seat_list\">";
		        	   }
		        	  
		        	   strUl+="<li class=\"clearfix\"><div class=\"fl w80 clearfix\">";
				       strUl+="<span class=\"green_3 ml5\">"+datas.userName+"</span></div><div class=\"fl w90 green_4\">";
				       strUl+=datas.agentNum;
					   strUl+="</div><div class=\"fl clearfix\">";
					   strUl+="<a data=\""+datas.agentNum+"\" mark=\"telephone\" class=\"service_icon service_icon_microphone fl ml10\"></a>";
					   strUl+="<a data=\""+datas.agentNum+"\" class=\"service_icon service_icon_email_2 fl ml10\"></a>";
					   strUl+="</div></li>";
			          });
		           strUl+="</ul>";
		           strGroup+="<div class=\"tabDiv_h\">"+strUl+"</div>";
		          });

				//组的内容
				$("#serviceItem_div1").html(strGroup);
				
				//拨号的a  标签事件
				$(".tabDiv_h a").off("click").click(function(e){
					if("telephone"==$(e.currentTarget).attr("mark")){
						//模拟点击拨号盘
						$("#serviceNav_2 li").eq(0).click();
						$("#dial_input").val($(e.currentTarget).attr("data"));
						comm.alert("打电话");
					}else{
						comm.alert("发短信");
					}
					
					});
			   
		   });
	   },//查询部门分组,显示坐席人员
	   qryGetDetpAgents:function(callback){
	    	$.ajax({
				url :  comm.domainList['server']+comm.UrlList["qryGetDetpAgents"],
				type : 'POST',
				dataType: 'json',
				success : function(responseData) {
						callback(responseData);
				}});
	   },
	   getUserCache:function(responseData){
		   var resNo=responseData.user.resNo;
		   userid=resNo+"_"+responseData.user.loginId;
	   },
	   getUserId:function (callback){
			$.ajax({
				url : comm.domainList['server'] + comm.UrlList["getLoginUser"],
				type : 'POST',
				cache : false,
				success : function(responseData) {
					callback(responseData);
				}
			});
		},//验证输入是否为正确资源号
		isCheckResNo:function(index,resNos,strDiv,callbalk){
			if(resNos.length==5){
				resNos=resNos+"000000";
			}else if(resNos.length==7){
				resNos=resNos+"0000";
			}
			$.ajax({
				url : comm.domainList['server'] + comm.UrlList["qryGetResTypeByResNo"],
				type : 'POST',
				data:{"resNo":resNos},
				success : function(responseData) {
				//2服务 4 企业 6  消费者
				switch (index) {
				case 2:
					if(responseData.Code=="-1"){
						$("#serviceContent_Div1_"+index).html(strDiv); 
						dialing.showMsgDialog("企业管理号有误!");
						return;
					}else if(responseData.Type!="CUSTTYPE_S"){
						$("#serviceContent_Div1_"+index).html(strDiv); 
						dialing.showMsgDialog("服务公司的企业管理号错误!");
						return;
					}else{
						if(resNos.length!=11){
							if(resNos.length==5){
								callbalk(resNos+"000000");
							}else{
								$("#serviceContent_Div1_"+index).html(strDiv); 
								dialing.showMsgDialog("企业管理号有误!");
							}
						}else{
							callbalk(resNos);
						}
					}
					break;
				case 4:
					if(responseData.Code=="-1"){
						$("#serviceContent_Div1_"+index).html(strDiv); 
						dialing.showMsgDialog("企业管理号有误!");
						return;
					}else if(responseData.Type!="CUSTTYPE_C"&&responseData.Type!="CUSTTYPE_B"){
						$("#serviceContent_Div1_"+index).html(strDiv); 
						dialing.showMsgDialog("企业的企业管理号错误!");
						return;
					}else{
						if(resNos.length!=11){
							if(resNos.length==7){
								callbalk(resNos+"0000");
							}else{
								$("#serviceContent_Div1_"+index).html(strDiv); 
		
								dialing.showMsgDialog("企业管理号有误!");
							
								
							}
						}else{
							callbalk(resNos);
						}
					}
					break;
				case 6:
					if(responseData.Code=="-1"){
						$("#serviceContent_Div1_"+index).html(strDiv); 
						dialing.showMsgDialog("积分卡号有误!");
						return;
					}else if(responseData.Type!="CUSTTYPE_P"){
						$("#serviceContent_Div1_"+index).html(strDiv); 
						dialing.showMsgDialog("消费者积分卡号有误!");
						return;
					}else{
						if(resNos.length!=11){
							$("#serviceContent_Div1_"+index).html(strDiv); 
							dialing.showMsgDialog("积分卡号有误!");
						}else{
							callbalk(resNos);
						}
					}
					break;
	
				}
			 }
			});
		},
		showMsgDialog:function(msg){
			$("#msg_span").text(msg);
			$("#pop-upboxContent").dialog({
				title:"提示信息!",
				width:"400",
				height:"180",
				modal:true,
				buttons:{ 
				"确定":function(){
				$("#pop-upboxContent").dialog( "destroy" );
				}
				}
				});
		},
		bindfwgsUL:function(responseData){
			 //处理企业地区信息
      		 var companyAddrStr=responseData.date.applyAreaNo;
      		  
      		 var companyFullAddr = "";
	        		if(companyAddrStr!=null && companyAddrStr!=""){
	        			var companyAddrArr= new Array(); //定义一数组 
		        		companyAddrArr=companyAddrStr.split("-"); //字符分割 
		        		companyFullAddr = comm.getArea(companyAddrArr[0],"0").areaName+"-"+comm.getArea(companyAddrArr[1],"1").areaName+"-"+comm.getArea(companyAddrArr[2],"2").areaName;
	        		}
			//服务公司详细信息页签事件绑定
			$("#fwgs_ul li").each(function(i){
			 switch (i) {
				case 0:
					$(this).off("click").click(function(){
						$("#fwgs_ResNo").text(responseData.date.resNo);
						$("#fwgs_Name").text(responseData.date.companyNameCN);
						$("#fwgs_status").text(responseData.date.baseStatus);
						$("#fwgsResNo").text(responseData.date.resNo);
						$("#fwgsCreateDate").text(responseData.date.created);
						$("#fwgsCompanyNameCN").text(responseData.date.companyNameCN);
						$("#fwgsCompanyNameEN").text(responseData.date.companyNameEN);
						$("#fwgsCompanyAddr").text(companyFullAddr);
						$("#fwgsCurrencyName").html("<span class=\"red\">"+responseData.date.currencyName+"</span>");
					});
					break;
				case 1:
					$(this).off("click").click(function(){
						$("#fwgs_firmName").text(responseData.date.companyNameCN);
						$("#fwgs_companyAddr").text(responseData.date.companyAddr);
						$("#fwgs_ceoName").text(responseData.date.ceoName);
						$("#fwgs_ceoPhone").text(responseData.date.ceoPhone);
						$("#fwgs_companyType").text(responseData.date.companyType);
						$("#fwgs_busiLicenseNo").text(responseData.date.busiLicenseNo);
						$("#fwgs_orgCodeNo").text(responseData.date.orgCodeNo);
						$("#fwgs_taxNo").text(responseData.date.taxNo);
						$("#fwgs_createDate").text(responseData.date.createDate);
						$("#fwgs_endDate").text(responseData.date.endDate=="long_term"?'长期':responseData.date.endDate);
						$("#fwgs_businessScope").text(responseData.date.businessScope);
					});
					break;
				case 2:
					$(this).off("click").click(function(){
						$("#fwgs_personName").text(responseData.date.personName);
						$("#fwgs_mobile").text(responseData.date.mobile);
						$("#fwgs_contactAddr").text(responseData.date.contactAddr);
						$("#fwgs_postCode").text(responseData.date.postCode);
						$("#fwgs_webSite").text(responseData.date.webSite);
						$("#fwgs_emailAddr").text(responseData.date.emailAddr);
						$("#fwgs_phoneCode").text(responseData.date.phoneCode);
						$("#fwgs_officeFax").text(responseData.date.officeFax);
						$("#fwgs_contactDuty").text(responseData.date.contactDuty);
						$("#fwgs_officeQQ").text(responseData.date.officeQQ);
					});
					break;
				case 3:
					$(this).off("click").click(function(){
						$.ajax({
							url : comm.domainList['server'] + comm.UrlList["getBankList"],
							type : 'POST',
							cache : false,
							dataType:'json',
							data:{"resNo":responseData.date.resNo},
							success : function(data) {
								//账户类型: DR_CARD-借记卡、CR_CARD-贷记卡、PASSBOOK-存折,CORP_ACCT-对公帐号
								var str="";
								if(data.code >=0) {
						        		$.each(data.date,function(i){
						        			var acctType="借记卡";
						        			var defaultFlag="是";
											switch (data.date[i].acctType) {
											case "DR_CARD":
												acctType="借记卡";
												break;
											case "CR_CARD":
												acctType="贷记卡";
												break;
											case "PASSBOOK":
												acctType="存折";
												break;
											case "CORP_ACCT":
												acctType="对公帐号";
												break;
											}
											if(data.date[i].defaultFlag=="N"){
												defaultFlag="否";
											}
											//货币  取得是 地区平台  货币
						        			str+="<tr><td class=\"nobd\">"+data.date[i].bankAcctName+"</td>" +
											"<td>"+comm.getCache("customer","cache").currencyName+"</td>" +
											"<td>"+acctType+"</td>" +
											"<td>"+comm.getBankName(data.date[i].bankCode)+
											"</td>" +
											"<td>"+data.date[i].cityName+"</td>" +
											"<td>"+data.date[i].bankAccount+"</td>" +
											"<td>"+defaultFlag+"</td></tr>";
						        		});

					        	};
								$("#fwgs_BankList").html(str);
							}
						});
					});
					break;
				case 4:
					$(this).off("click").click(function(){
						//积分账户查询
						dialing.getIntegralActInfo("", "", responseData.date.resNo, function(data){
							var str="<div style=\"padding-bottom:3px;border-bottom:1px solid #CCC;\">积分账户余数："+data.date.residualIntegral+"</div>";
								str+="<div style=\"padding-top:3px;\"><span style=\"letter-spacing:0.2em;margin-right:-0.2em;\">可用积分数</span>："+data.date.usableIntegral+"；<span class=\"ml10\">昨日积分数</span>："+data.date.todayNewIntegral+"</div>";
							$("#integral_act_info").html(str);
							//$("#integral_act_info").html("积分账户余数： <b>"+data.date.residualIntegral+"</b>；  可用积分数： <b>"+data.date.usableIntegral+"</b>；  昨日积分数：<b>"+data.date.todayNewIntegral+"</b>");
						});
						//投资帐户
						dialing.qryGetInvestActAmount("", "", responseData.date.resNo,function(getInvestActAmount){
							var str="<div class=\"mb5\">积分投资总数："+getInvestActAmount.date.invest+"</div><div style=\"border:1px solid #CCC;padding:3px;background-color:#EFEFEF;\">";
							str+="<div style=\"padding-bottom:3px;border-bottom:1px solid #CCC;\">"+getInvestActAmount.date.investYear+"年度投资分红总数："+getInvestActAmount.date.totalOfInvestmentDividends+"</div>";
							str+="<div style=\"padding-top:3px;\">其中流通币："+getInvestActAmount.date.investHsbTransferCash+"；<span class=\"ml10\">其中慈善救助基金</span>："+getInvestActAmount.date.investSocialAssistanceFund+"</div></div>";
							$("#invest_act_info").html(str);
							//$("#invest_act_info").html("投资积分总数：<b>"+getInvestActAmount.date.invest+"</b>；  本年度投资分红总数: <b>"+getInvestActAmount.date.totalOfInvestmentDividends+"</b> ；　其中流通币：  <b>"+getInvestActAmount.date.investHsbTransferCash+" </b>其中慈善救助基金： <b>"+getInvestActAmount.date.investSocialAssistanceFund+"</b>");
							});
						//货币账户
						dialing.qryGetCashActInfo("", "", responseData.date.resNo, function(getCashActInfo){
							$("#cash_act_info").html("货币账户余额："+getCashActInfo.date.cash);
						});
						//互生币账户
						dialing.qryGetHsbTransferCash("", "", responseData.date.resNo,function(getHsbTransferCash){
							var str="<div class=\"mb5\"><span style=\"letter-spacing:1.5em;margin-right:-1.5em;\">流通币</span>："+getHsbTransferCash.date.hsbTransferCash+"</div><div>慈善救助基金："+getHsbTransferCash.date.socialAssistanceFund+"</div>";
							$("#hsb_act_info").html(str);
							//$("#hsb_act_info").html("流通币余额： <b>"+getHsbTransferCash.date.hsbTransferCash+"</b>；  慈善救助基金： <b>"+getHsbTransferCash.date.socialAssistanceFund+"</b>");
						});
						//城市税收对接账户
						dialing.qryGetCommIncBalance("", "", responseData.date.resNo,function(qryGetCommIncBalance){
					        	$("#city_tax_joint_act_info").html("税金余额："+qryGetCommIncBalance.date.cityTaxJointActBalance);
						});
					});
					break;
				case 5:
					$(this).off("click").click(function(){
						//如果报送服务公司为空
						if(responseData.date.applyResNo==""||responseData.date.applyResNo==null||responseData.date.applyResNo==comm.getCache("customer","cache").corpResNo){
							var corpResNo= comm.getCache("customer","cache").corpResNo;//当前平台资源号
							$.ajax({
								url : comm.domainList['server'] + comm.UrlList["qrygetDetailByResNos"],
								type : 'POST',
								data:{"resNo":corpResNo},
								success : function(responseDataS) {
									if(responseDataS.data!==null){
									$("#fwgs_applyContactPerson").text(responseDataS.data.contactPerson);
									$("#fwgs_applyResNo").text(responseDataS.data.resNo);
									$("#fwgs_applyCompanyNameCN").text(responseDataS.data.companyNameCN);
									$("#fwgs_applyMobile").text(responseDataS.data.mobile);
									$("#fwgs_applyContactAddr").text(responseDataS.data.contactAddr);
									}
								}});
						}else{
						$.ajax({
							url : comm.domainList['server'] + comm.UrlList["qryGetBaseInfo"],
							type : 'POST',
							data:{"resNo":responseData.date.applyResNo},
							success : function(responseDataS) {
								if(responseDataS.date!==null){
								$("#fwgs_applyContactPerson").text(responseDataS.date.contactPerson);
								$("#fwgs_applyResNo").text(responseDataS.date.resNo);
								$("#fwgs_applyCompanyNameCN").text(responseDataS.date.companyNameCN);
								$("#fwgs_applyMobile").text(responseDataS.date.mobile);
								$("#fwgs_applyContactAddr").text(responseDataS.date.contactAddr);
								}
							}});
						}
					});
					break;
				}
			 
			 });
		},
		bindqyUL:function(responseData){
			//企业详细信息页签事件绑定
			$("#qy_ul li").each(function(i){
				$("#qy_ResNo").text(responseData.date.resNo);
				$("#qy_Name").text(responseData.date.companyNameCN);
				$("#qy_status").text(responseData.date.baseStatus);
				 //处理企业地区信息
       		 var companyAddrStr=responseData.date.applyAreaNo;
       		  
       		 var companyFullAddr = "";
	        		if(companyAddrStr!=null && companyAddrStr!=""){
	        			var companyAddrArr= new Array(); //定义一数组 
		        		companyAddrArr=companyAddrStr.split("-"); //字符分割 
		        		companyFullAddr = comm.getArea(companyAddrArr[0],"0").areaName+"-"+comm.getArea(companyAddrArr[1],"1").areaName+"-"+comm.getArea(companyAddrArr[2],"2").areaName;
	        		}
			 switch (i) {
				case 0:
					$(this).off("click").click(function(){
						
						$("#qy_ResNo_").text(responseData.date.resNo);
						$("#qy_CreateDate").text(responseData.date.created);
						$("#qy_CompanyNameCN").text(responseData.date.companyNameCN);
						$("#qy_CompanyNameEN").text(responseData.date.companyNameEN);
						$("#qy_CompanyAddr").text(companyFullAddr);
						$("#qy_CurrencyName").html("<span class=\"red\">"+responseData.date.currencyName+"</span>");
					});
					break;
				case 1:
					$(this).off("click").click(function(){
						$("#qy_firmName").text(responseData.date.companyNameCN);
						$("#qy_companyAddr").text(responseData.date.companyAddr);
						$("#qy_ceoName").text(responseData.date.ceoName);
						$("#qy_ceoPhone").text(responseData.date.ceoPhone);
						$("#qy_companyType").text(responseData.date.companyType);
						$("#qy_busiLicenseNo").text(responseData.date.busiLicenseNo);
						$("#qy_orgCodeNo").text(responseData.date.orgCodeNo);
						$("#qy_taxNo").text(responseData.date.taxNo);
						$("#qy_createDate").text(responseData.date.createDate);
						$("#qy_endDate").text(responseData.date.endDate=="long_term"?'长期':responseData.date.endDate);
						$("#qy_businessScope").text(responseData.date.businessScope);
					});
					break;
				case 2:
					$(this).off("click").click(function(){
						$("#qy_personName").text(responseData.date.personName);
						$("#qy_mobile").text(responseData.date.mobile);
						$("#qy_contactAddr").text(responseData.date.contactAddr);
						$("#qy_postCode").text(responseData.date.postCode);
						$("#qy_webSite").text(responseData.date.webSite);
						$("#qy_emailAddr").text(responseData.date.emailAddr);
						$("#qy_phoneCode").text(responseData.date.phoneCode);
						$("#qy_officeFax").text(responseData.date.officeFax);
						$("#qy_contactDuty").text(responseData.date.contactDuty);
						$("#qy_officeQQ").text(responseData.date.officeQQ);
					});
					break;
				case 3:
					$(this).off("click").click(function(){
						$.ajax({
							url : comm.domainList['server'] + comm.UrlList["getBankList"],
							type : 'POST',
							cache : false,
							dataType:'json',
							data:{"resNo":responseData.date.resNo},
							success : function(data) {
								//账户类型: DR_CARD-借记卡、CR_CARD-贷记卡、PASSBOOK-存折,CORP_ACCT-对公帐号
								var str="";
								if(data.code >=0) {
						        		$.each(data.date,function(i){
						        			var acctType="借记卡";
						        			var defaultFlag="是";
											switch (data.date[i].acctType) {
											case "DR_CARD":
												acctType="借记卡";
												break;
											case "CR_CARD":
												acctType="贷记卡";
												break;
											case "PASSBOOK":
												acctType="存折";
												break;
											case "CORP_ACCT":
												acctType="对公帐号";
												break;
											}
											if(data.date[i].defaultFlag=="N"){
												defaultFlag="否";
											}
											//货币  取得是 地区平台  货币
						        			str+="<tr><td class=\"nobd\">"+data.date[i].bankAcctName+"</td>" +
											"<td>"+comm.getCache("customer","cache").currencyName+"</td>" +
											"<td>"+acctType+"</td>" +
											"<td>"+comm.getBankName(data.date[i].bankCode)+
											"</td>" +
											"<td>"+data.date[i].cityName+"</td>" +
											"<td>"+data.date[i].bankAccount+"</td>" +
											"<td>"+defaultFlag+"</td></tr>";
						        		});

					        	};
								$("#qy_BankList").html(str);
							}
						});
					});
					break;
				case 4:
					var resTypeData=responseData.date.resType;
					$(this).off("click").click(function(){
				
						
						//积分预付款
						dialing.qryGetPrepaidIntegralAct("", "",  responseData.date.resNo,function(getPrepaidIntegralAct){
							var str="<div style=\"padding-bottom:3px;border-bottom:1px solid #CCC;\">积分预付款余额："+getPrepaidIntegralAct.date.prepaidIntegralActBalance+"</div>"/*<div style="padding-top:3px;">折合预付积分数：xxx</div>"*/;
							$("#qy_prepaid_integral_act").html(str);
							//$("#qy_prepaid_integral_act").html("积分预付款账户余额：  <b>"+getPrepaidIntegralAct.date.prepaidIntegralActBalance+"</b>");
					});
						//积分账户查询
						dialing.getIntegralActInfo("", "", responseData.date.resNo, function(data){
						var str="<div style=\"padding-bottom:3px;border-bottom:1px solid #CCC;\">积分账户余数："+data.date.residualIntegral+"</div>";
						str+="<div style=\"padding-top:3px;\"><span style=\"letter-spacing:0.2em;margin-right:-0.2em;\">可用积分数</span>："+data.date.usableIntegral+"；<span class=\"ml10\">昨日积分数</span>："+data.date.todayNewIntegral+"</div>";
						$("#qy_integral_act_info").html(str);	
						//$("#qy_integral_act_info").html("积分账户余数： <b>"+data.date.residualIntegral+"</b>；  可用积分数： <b>"+data.date.usableIntegral+"</b>；  昨日积分数：<b>"+data.date.todayNewIntegral+"</b>");
						});
						if(resTypeData=="托管企业"){
							$("#investAcct").show();
							//投资帐户
							dialing.qryGetInvestActAmount("", "", responseData.date.resNo,function(getInvestActAmount){
						        var str="<div class=\"mb5\">积分投资总数："+getInvestActAmount.date.invest+"</div><div style=\"border:1px solid #CCC;padding:3px;background-color:#EFEFEF;\">";
								str+="<div style=\"padding-bottom:3px;border-bottom:1px solid #CCC;\">"+getInvestActAmount.date.investYear+"度投资分红互生币："+getInvestActAmount.date.thetotalOfInvestmentDividends+"</div><div style=\"padding-top:3px;\">其中流通币："+getInvestActAmount.date.investOperatingIncome+"；<span class=\"ml10\">其中慈善救助基金</span>："+getInvestActAmount.date.investCharitableAidFund+"</div></div>";	
								//$("#qy_invest_act_info").html("投资积分总数：<b>"+getInvestActAmount.date.invest+"</b>；  本年度投资分红总数: <b>"+getInvestActAmount.date.totalOfInvestmentDividends+"</b> ；　其中流通币：  <b>"+getInvestActAmount.date.investHsbTransferCash+" </b>其中慈善救助基金： <b>"+getInvestActAmount.date.investSocialAssistanceFund+"</b>");
								$("#qy_invest_act_info").html(str);	
							});
							
							//互生币账户
							dialing.qryGetHsbTransferCash("", "", responseData.date.resNo,function(getHsbTransferCash){
								//var hsb=getHsbTransferCash.date.hsbTransferCash>getHsbTransferCash.date.hsbOperatingIncome?getHsbTransferCash.date.hsbTransferCash:getHsbTransferCash.date.hsbOperatingIncome;
								var hsb=getHsbTransferCash.date.hsbOperatingIncome;
								var str="<div class=\"mb5\"><span style=\"letter-spacing:1.5em;margin-right:-1.5em;\">流通币余额</span>："+hsb+"</div><div>慈善救助基金："+getHsbTransferCash.date.hsbCharitableAidFund+"</div>";
								//$("#qy_hsb_act_info").html("流通币余额： <b>"+getHsbTransferCash.date.hsbTransferCash+"</b>；  慈善救助基金： <b>"+getHsbTransferCash.date.socialAssistanceFund+"</b>");
								$("#qy_hsb_act_info").html(str);
							});
						}else if(resTypeData=="成员企业"){
							$("#investAcct").hide();
							//互生币账户
							dialing.qryGetHsbTransferCash("", "", responseData.date.resNo,function(getHsbTransferCash){
								//var hsb=getHsbTransferCash.date.hsbTransferCash>getHsbTransferCash.date.hsbOperatingIncome?getHsbTransferCash.date.hsbTransferCash:getHsbTransferCash.date.hsbOperatingIncome;
								var hsb=getHsbTransferCash.date.hsbOperatingIncome;
								var str="<div class=\"mb5\"><span style=\"letter-spacing:1.5em;margin-right:-1.5em;\">流通币余额</span>："+hsb+"</div>";//<div>慈善救助基金："+getHsbTransferCash.date.hsbCharitableAidFund+"</div>";
								//$("#qy_hsb_act_info").html("流通币余额： <b>"+getHsbTransferCash.date.hsbTransferCash+"</b>；  慈善救助基金： <b>"+getHsbTransferCash.date.socialAssistanceFund+"</b>");
								$("#qy_hsb_act_info").html(str);
							});
						}
					
						//货币账户
						dialing.qryGetCashActInfo("", "", responseData.date.resNo, function(getCashActInfo){
							$("#qy_cash_act_info").html("货币账户余额："+getCashActInfo.date.cash);
						});

						//城市税收对接账户
						dialing.qryGetCommIncBalance("", "", responseData.date.resNo,function(qryGetCommIncBalance){
					        	$("#qy_city_tax_joint_act_info").html("税金余额："+qryGetCommIncBalance.date.cityTaxJointActBalance);
						});
					});
					break;
				case 5:
					$(this).off("click").click(function(){
						$.ajax({
							url : comm.domainList['server'] + comm.UrlList["qryGetBaseInfo"],
							type : 'POST',
							data:{"resNo":responseData.date.applyResNo},
							success : function(responseDataS) {
								if(responseDataS.date!==null){
								$("#qy_applyContactPerson").text(responseDataS.date.resNo);
								$("#qy_applyResNo").text(responseDataS.date.companyNameCN);
								$("#qy_applyCompanyNameCN").text(responseDataS.date.contactPerson);
								$("#qy_applyMobile").text(responseDataS.date.mobile);
								$("#qy_applyContactAddr").text(responseDataS.date.contactAddr);
								}
							}});
					});
					break;
				}
			 });
		
		},
		bindxfzUL:function(responseData){
			$("#xfz_resNo").html(responseData.date.resNo);
			//console.log(responseData);

			var strDiv="";
			var str1="",str2="",str3="";
/*			if(responseData.date.custName!=""){
				str1="<i class=\"fl service_icon service_icon_Bbd ml20\" title=\"已实名绑定\"></i>";
			}else{
				str1="<i class=\"fl service_icon service_icon_Bbd_disable ml20\" title=\"未实名绑定\"></i>";
			}*/
			if(responseData.date.isReg=="Y"){
				str2="<i class=\"fl service_icon service_icon_Bzc ml5\" title=\"已实名注册\"></i>";
			}else{
				str2="<i class=\"fl service_icon service_icon_Bzc_disable ml5\" title=\"未实名注册\"></i>";
			}
			//是否实名认证 (U-未申请,W-待审批,N-审批驳回,Y-审批通过。)
			if(responseData.date.isAuth=="Y"){
				str3="<i class=\"fl service_icon service_icon_Brz ml5\" title=\"已实名认证\"></i>";
			}else{
				str3="<i class=\"fl service_icon service_icon_Brz_disable ml5\" title=\"未实名认证\"></i>";
			}
			strDiv+=str1+str2+str3;
			strDiv+="<div class=\"fl ml10 mt5\">卡行为标识 : "+responseData.date.activeStatus+"</div>";
			$("#xfz_custNameIsNull").html(strDiv);
			//消费者详细信息页签事件绑定
			$("#xfz_ul li").each(function(i){
			 switch (i) {
				case 0:
					$(this).unbind("click").click(function(){
						
						$("#xfz_resNo_").html(responseData.date.resNo);
						$("#xfz_custName").html(responseData.date.custName);
						//（1-身份证，2-护照，3-营业执照）
						var creType="";
						switch (responseData.date.creType) {
						case '1':
							creType="身份证";
							break;
						case '2':
							creType="护照";
							break;
						case '3':
							creType="营业执照";
							break;
						}
						$("#xfz_creType").html(creType);
						$("#xfz_country").html(responseData.date.country);
						$("#xfz_creNo").html(responseData.date.creNo);
						if(responseData.date.sex=="0"){
							$("#xfz_sex").html("女");
						}else if(responseData.date.sex=="1"){
							$("#xfz_sex").html("男");
						}else{
							$("#xfz_sex").html("");
						}
						$("#xfz_creExpiryDate_").html(responseData.date.creExpiryDate=="long_term"?'长期':responseData.date.creExpiryDate);
						$("#xfz_birthAddress").html(responseData.date.birthAddress);
						$("#xfz_creVerifyOrg").html(responseData.date.creVerifyOrg);
					});
					break;
				case 1:
					$(this).off("click").click(function(){
						//积分账户
						dialing.getIntegralActInfo("","",responseData.date.resNo,function(data){
							var str="<div style=\"padding-bottom:3px;border-bottom:1px solid #CCC;\">积分账户余数："+data.date.residualIntegral+"</div>";
								str+="<div style=\"padding-top:3px;\"><div>可用积分数："+data.date.usableIntegral+"；<span class=\"ml10\">今日积分数</span>："+data.date.todayNewIntegral+"</div></div>";
								$("#xfz_integral_act_info").html(str);
								//$("#xfz_integral_act_info").html("积分账户余数： "+data.date.residualIntegral+" ；  可用积分数： "+data.date.usableIntegral+"；  昨日积分数："+data.date.todayNewIntegral+"");
						});
						// 货币账户
							dialing.qryGetCashActInfo("", "", responseData.date.resNo, function(getCashActInfo){
								$("#xfz_hb_act_info").html("货币账户余额："+getCashActInfo.date.cash+"");
						});
						// 投资账户
							dialing.qryGetInvestActAmount("", "", responseData.date.resNo,function(getInvestActAmount){
				        	var str="<div class=\"mb5\">累计积分投资总数："+getInvestActAmount.date.investIntegralTotal+"</div><div style=\"border:1px solid #CCC;padding:3px;background-color:#EFEFEF;\">";
							str+="<div style=\"padding-bottom:3px;border-bottom:1px solid #CCC;\">"+getInvestActAmount.date.investYear+"度投资分红互生币："+getInvestActAmount.date.dividentsTotal+"</div>";
							str+="<div style=\"padding-top:3px;\">其中流通币："+getInvestActAmount.date.ccyHsbTotal+"；<span class=\"ml10\">其中定向消费币</span>："+getInvestActAmount.date.dcHsbTotal+"</div></div>";
							$("#xfz_cashAccount").html(str);	
							//$("#xfz_InvestActAmount").html("累计积分投资总数： "+getInvestActAmount.date.investIntegralTotal+"   本年度投资分红互生币: "+getInvestActAmount.date.dividentsTotal+"  其中流通币：0；其中定向消费币：0");
						
						});
						// 互生币账户
							dialing.qryGetHsbTransferCash("", "", responseData.date.resNo,function(getHsbTransferCash){
								var str="<div class=\"mb5\"><span style=\"letter-spacing:1em;margin-right:-1em;\">流通币</span>："+getHsbTransferCash.date.hsbCharitableAidFund+"</div><div>定向消费币："+getHsbTransferCash.date.hsbOperatingIncome+"</div>";
								$("#xfz_hsb_act_info").html(str);
								//$("#xfz_hsb_act_info").html("流通币： "+getHsbTransferCash.date.hsbOperatingIncome+"    定向消费币："+getHsbTransferCash.date.hsbCharitableAidFund);
			
						});
					});
					break;
				case 2:
					$(this).off("click").click(function(){
						dialing.qryPersonWelfQualification(responseData.date.resNo,function(datas){
							var str="";
							if(datas.data!=null){
							var personData=JSON.parse(datas.data);
							 str="<tr><td class=\"nobd\">"+dialing.dateForMat(personData.createDate)+"</td><td>"+personData.welfNo+"</td><td>"+dialing.dateForMat(personData.startDate)+" ~ "+dialing.dateForMat(personData.expireDate)+"</td><td>"+personData.durInvalidDates+"</td></tr>";
							
							$("#personWelfQualification_tbody").html(str);
							}else{
								 str="<tr><td class=\"nobd\" colspan='4' align='center'>没有查到有效数据!</td></tr>";
							}
							$("#personWelfQualification_tbody").html(str);
						});
					});
					break;
				case 3:
					$(this).off("click").click(function(){
						dialing.qryWelfareList(responseData.date.resNo,function(datas){
							dialing.showjifenfuli(datas);
						});
						$("#qry_btn_xfz").off("click").click(function(){//查询按钮事件
							dialing.qryWelfareList(responseData.date.resNo,function(datas_){
								dialing.showjifenfuli(datas_);
							});
						});
					});
					break;
				case 4:
					$(this).off("click").click(function(){
						//列表显示
						if (RemakeCardBusiness_DateTbl != null) {
							$("#RemakeCardBusiness_DateTbl").DataTable().destroy();
						}
						RemakeCardBusiness_DateTbl = $("#RemakeCardBusiness_DateTbl").DataTable(
								{
									//"bPaginate": false, //翻页功能
									"bJQueryUI" : true,
									"bFilter" : false,
									"scrollY":"180px",//垂直限制高度，需根据页面当前情况进行设置
									"scrollCollapse": true,//垂直限制高度
									"sPaginationType" : "full_numbers",
									"sDom" : '<""l>t<"F"fp>',
									"processing" : true,
									"serverSide" : true,
									"bAutoWidth" : false, // 自适应宽度
									"bLengthChange" : false,
									"bSort":false,
									//'bStateSave' : true,
									"oLanguage" : {
										// "sLengthMenu" : "每页显示 _MENU_条",
										"sZeroRecords" : "没有找到符合条件的数据",
										"sInfo" : "当前第 _START_ - _END_ 条　共计 _TOTAL_ 条",
										"sInfoEmpty" : "没有记录",
										"sInfoFiltered" : "(从 _MAX_ 条记录中过滤)",
										"sSearch" : "搜索：",
										"oPaginate" : {
											"sFirst" : "首页",
											"sPrevious" : "上一页",
											"sNext" : "下一页",
											"sLast" : "尾页"
										}
									},
									"ajax" : {
										"url" : comm.domainList['server']+comm.UrlList["qryRemakeCardBusinessList"],
										"type" : "POST",
										"data":{"resNo":responseData.date.resNo}
									},
									"aoColumns" : [ {
										"data" : "orderNo"
									},{ 
										"data" : "tradeDate"
									}, {
										"data" : "orderStatus"
									}],
									"columnDefs" : [{
										"targets" : [ 1 ],
										"render" : function(data, type, full) {
											  return comm.formatDate(new Date(data));
										}
									},{
										"targets" : [ 2 ],
										"render" : function(data, type, full) {
											var str="";
											//'0-待付款', '1-配置中', '2-待寄送', '3-已寄送', '4-已签收', '5-已关闭
											switch (data) {
											case '0':
												str="待付款";
												break;
											case '1':
												str="配置中";
												break;
											case '2':
												str="待寄送";
												break;
											case '3':
												str="已寄送";
												break;
											case '4':
												str="已签收";
												break;
											case '5':
												str="已关闭";
												break;
											}
											return str;
										}
									}]
								});
						//RemakeCardBusiness_DateTbl.Draw();
					});
					break;
				}
			 });
		
		},
		qryPersonWelfQualification:function(resNo,getPersonWelfQualification){
		 	$.ajax({
				url :  comm.domainList['server']+comm.UrlList["qryPersonWelfQualification"],
				type : 'POST',
				data:{"resNo":resNo},
				success : function(responseData) {
					getPersonWelfQualification(responseData);
				}});
		},dateForMat:function(time){//格式化毫秒时间
			if(time!=undefined&&time!=null){
			var dd = new Date();
			dd.setTime(time);
			var s =  comm.formatDate(dd);
			return s;
			}else{
				return "";
			}
		},qryWelfareList:function(resNo,qryGetIntegrals){//查询积分福利
			var type=$("#application_type_xfz").val();
			//列表显示
			if (jifenfuliapplyDataTable != null) {
				$("#jifenfuliapplyDataTable").DataTable().destroy();
			}
			jifenfuliapplyDataTable = $("#jifenfuliapplyDataTable").DataTable(
					{
						//"bPaginate": false, //翻页功能
						"bJQueryUI" : true,
						"bFilter" : false,
						"scrollY":"50px",//垂直限制高度，需根据页面当前情况进行设置
						"scrollCollapse": true,//垂直限制高度
						"sPaginationType" : "full_numbers",
						"sDom" : '<""l>t<"F"fp>',
						"processing" : true,
						"serverSide" : true,
						"bAutoWidth" : false, // 自适应宽度
						"bLengthChange" : false,
						"bSort":false,
						//'bStateSave' : true,
						"oLanguage" : {
							// "sLengthMenu" : "每页显示 _MENU_条",
							"sZeroRecords" : "没有找到符合条件的数据",
							"sInfo" : "当前第 _START_ - _END_ 条　共计 _TOTAL_ 条",
							"sInfoEmpty" : "没有记录",
							"sInfoFiltered" : "(从 _MAX_ 条记录中过滤)",
							"sSearch" : "搜索：",
							"oPaginate" : {
								"sFirst" : "首页",
								"sPrevious" : "上一页",
								"sNext" : "下一页",
								"sLast" : "尾页"
							}
						},
						"ajax" : {
							url :  comm.domainList['server']+comm.UrlList["qryGetIntegral"],
							"type" : "POST",
							data:{"resNo":resNo,"type":type},
						},
						"aoColumns" : [ {
							"data" : "applyNo"
						},{ 
							"data" : "applyDate"
						}, {
							"data" : "applyType"
						}, {
							"data" : "status"
						}, {
							"data" :"status"
						}],
						"columnDefs" : [{
							"targets" : [ 1 ],
							"render" : function(data, type, full) {
								  return comm.formatDate(new Date(data));
							}
						},{
							"targets" : [ 2 ],
							"render" : function(data, type, full) {
								switch (data) {
								case '1':
									str="代他人申请身故保障金";
									break;
								case '2':
									str="互生意外伤害保障";
									break;
								default:
									str="医疗补贴计划";
									break;
								}
								return str;
							}
						},{//W:受理中Y:受理成功，N:驳回
							"targets" : [ 3 ],
							"render" : function(data, type, full) {
								switch (data) {
								case 'W':
									str="受理中";
									break;
								case 'Y':
									str="受理成功";
									break;
								case 'N':
									str="驳回";
									break;
								}
								return str;
							}
						},{//W:受理中Y:受理成功，N:驳回
							"targets" : [ 4 ],
							"render" : function(data, type, full) {
								if(full.applyType=="1"||full.applyType=="2"){
									return full.appAmount;
								}else {
									return full.amount;
								}
							
							}
						}/*{
							"targets" : [ 2 ],
							"render" : function(data, type, full) {
								var str="";
								//'0-待付款', '1-配置中', '2-待寄送', '3-已寄送', '4-已签收', '5-已关闭
								switch (data) {
								case '0':
									str="待付款";
									break;
								case '1':
									str="配置中";
									break;
								case '2':
									str="待寄送";
									break;
								case '3':
									str="已寄送";
									break;
								case '4':
									str="已签收";
									break;
								case '5':
									str="已关闭";
									break;
								}
								return str;
							}
						}*/]
					});
/*			$.ajax({
				url :  comm.domainList['server']+comm.UrlList["qryGetIntegral"],
				type : 'POST',
				data:{"resNo":resNo,"type":type},
				success : function(responseData) {
					qryGetIntegrals(responseData);
				}});*/
		},showjifenfuli:function(datas){
			var type=$("#application_type_xfz").val();
			var str="<tr><td class=\"nobd\" colspan='5' align='center'>没有查到有效数据!</td></tr>";
			if(datas.data!=null){
			var personData=JSON.parse(datas.data);
			if(personData.length>0){
			str="";
			}
			$.each(personData,function(i){
				str+="<tr><td class=\"nobd\">"+personData[i].applyNo+"</td>";
				str+="<td>"+dialing.dateForMat(personData[i].applyDate)+"</td>";
				//保障申请类型，1.身故保障金，2.医疗保障金
				if(personData[i].applyType=="2"){
					str+="<td>互生意外伤害保障</td>";
				}else if(personData[i].applyType=="1"){
					str+="<td>代他人申请身故保障金</td>";
				}else{
					str+="<td>互生医疗补贴计划</td>";//没有applyType
				}
				//status	申请状态,W:受理中Y:受理成功，N:驳回
				switch (personData[i].status) {
				case "Y":
					str+="<td>受理成功</td>";
					break;
				case "N":
					str+="<td>驳回</td>";
					break;
				default:
					str+="<td>受理中</td>";
					break;
				};
				if(type=="1"){
					if(personData[i].amount!=undefined){
						str+="<td>"+personData[i].amount+"</td></tr>";
						}else{
							str+="<td></td></tr>";
						}
				}else{
					if(personData[i].appAmount!=undefined){
						str+="<td>"+personData[i].appAmount+"</td></tr>";
						}else{
							str+="<td></td></tr>";
						}
				}
				
			});
			
			}
		$("#jifenfuli_tbody").html(str);
		},showdetails:function(index,responseData){ //右上角详情 提出来
			switch (index) {
			case 2:// 服务公司详情
				$("#fwgsDetail_content").dialog({
					title:"服务公司详细信息",
					width:"800",
					height:"450",
					modal:true,
					buttons:{ 
						"确定":function(){
							$(this).dialog("destroy");
						}
					}
				});
				dialing.bindfwgsUL(responseData);
				$( ".jqp-tabs_1" ).tabs();
				//触发第一个点击事件
				$("#fwgs_ul li:eq(0)").find('a').focus().click();
				$("#fwgs_ul li:eq(0)").trigger('click');
				break;

			case 4://企业详情
				$("#qyDetail_content").dialog({
					title:"企业详细信息",
					width:"800",
					height:"450",
					modal:true,
					buttons:{ 
						"确定":function(){
							$(this).dialog("destroy");
						}
					}
				});
				dialing.bindqyUL(responseData);
				$( ".jqp-tabs_2" ).tabs();
				//触发第一个点击事件
				$("#qy_ul li:eq(0)").find('a').focus().click();
				$("#qy_ul li:eq(0)").trigger('click');
				break;
			case 6://消费者详情
				$("#xfzDetail_content").dialog({
					title:"消费者详细信息",
					width:"1000",
					height:"450",
					modal:true,
					buttons:{ 
						"确定":function(){
							$(this).dialog("destroy");
						}
					}
				});
				dialing.bindxfzUL(responseData);
				
				//积分福利下拉框
				$("#application_type_xfz").selectbox({width:200}).change(function(){});
				
				$( ".jqp-tabs_3" ).tabs();
				$("#xfz_ul li:eq(0)").find('a').focus().click();
				$("#xfz_ul li:eq(0)").trigger('click');
				break;
				}
			//把最后弹出的弹出框显示在最顶层
			$(".ui-dialog").eq($(".ui-dialog").length-1).css("z-index","10000")
			
			$("#xfzDetail_content .ui-widget-content").removeClass("ui-widget-content");
			$("#qyDetail_content .ui-widget-content").removeClass("ui-widget-content");
			$("#fwgsDetail_content .ui-widget-content").removeClass("ui-widget-content");
		   
		}
		
	};
	//取当前登录userid
	dialing.getUserId(dialing.getUserCache);
	return dialing;	

});

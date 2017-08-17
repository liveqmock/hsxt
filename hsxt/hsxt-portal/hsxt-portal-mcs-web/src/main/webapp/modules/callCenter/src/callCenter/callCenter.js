define(["text!callCenterTpl/callList.html", "text!callCenterTpl/callIng.html", "text!callCenterTpl/bindCallAccepted.html", "text!callCenterTpl/callVoice.html", "callCenterDat/callCenter/callCenter","callCenterSrc/callCenter/dialing"],function(callListTpl,callIngTpl,callAcceptedTpl,callVoiceTpl,ajax,dialing){
	var scash_callListDataTable=null;
	var scash_callIngDataTable=null;
	var bindCallAccepted_DataTable=null;
	var callVoice_DataTable=null;
	
	var callCenter = {
		init: function(){
		},
		show:function(html){
			//加载中间内容
			$('.operationsArea').html(html);	
			$("#callWay").selectbox({width:90}).bind('change', function(){});
		},
		hide:function(){
			//清空中间内容
			$('.operationsArea').empty();	
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
		bindCallList:function(){//来电管理列表
			//先显示页面
			$('.operationsArea').html(callListTpl).find('div').eq(0).removeClass('none');
			$('.operationsArea').append(callIngTpl).append(callAcceptedTpl).append(callVoiceTpl);
	
			$('#bDate_Call').datepicker(comm.dateParament()); 
			$("#eDate_Call" ).datepicker(comm.dateParament());
			$("#callState").selectbox({width:100}).change(function(){});
			$("#callWay").selectbox({width:100}).change(function(){});
			

			//查询列表
			searchCallStateList();
			//绑定事件
			initClick();
			},
		bindCallIng : function(){//正在通话列表
        		$('.operationsArea').html(callIngTpl).find('div').eq(0).removeClass('none');
        		$('.operationsArea').append(callListTpl).append(callAcceptedTpl).append(callVoiceTpl);
        		//显示列表
        		qryCallIng();
        		//绑定事件
    			initClick();
        		},
		bindCallAccepted : function(){//已接来电
        		$('.operationsArea').html(callAcceptedTpl).find('div').eq(0).removeClass('none');
				$('.operationsArea').append(callListTpl).append(callIngTpl).append(callVoiceTpl);
				$("#callWay").selectbox({width:100}).change(function(){});
				$( "#bindCallBegin" ).datepicker(comm.dateParament());
				$( "#bindCallEnd" ).datepicker(comm.dateParament());
				//显示列表
				qryCallAccepted();
				//绑定事件
				initClick();
				},
		bindCallVoice : function(){//语音留言
        		$('.operationsArea').html(callVoiceTpl).find('div').eq(0).removeClass('none');
				$('.operationsArea').append(callListTpl).append(callIngTpl).append(callAcceptedTpl);
				$( "#callVoiceBegin" ).datepicker(comm.dateParament());
				$( "#callVoiceEnd" ).datepicker(comm.dateParament());
				//显示列表
				qryVoice();
				//绑定事件
				initClick();
				},
		JS_LoadFile:function(remotePath){
			    	
			    	//var localPath="E:\\lingjun\\互生线\\callcenter\\下载录音文件\\aaa1.wav";   //本地路径: 路径+语音文件名 
			       // var remotePath="G:\\HSRecordVoc\\20150605\\807\\111913-O-018677320280.wav";    // 录音文件下啊在地址
			        
			        //var remotePath="E:\\HBS.doc";    // 录音文件下啊在地址

			        var id=Test.JS_LoadFile(remotePath);   // 下载成功返回唯一 id  1000001   2 2
			        if(id<0){
			        	if(remotePath!=null){
							 var filePathArray = remotePath.split('\\');
							comm.alert(filePathArray[filePathArray.length-1]+"下载失败!");
						}
			        	 
			        }
			      
			    },
			    JS_PlayVoc:function(agentNum,remotePath){
			    	
			    	
			    	//var localPath="E:\\lingjun\\互生线\\callcenter\\下载录音文件\\aaa1.wav";   //本地路径: 路径+语音文件名 
			       // var remotePath="G:\\HSRecordVoc\\20150605\\807\\111913-O-018677320280.wav";    // 录音文件下啊在地址
			        
			        //var remotePath="E:\\HBS.doc";    // 录音文件下啊在地址

			        var id=Test.JS_PlayVoc(agentNum,remotePath);   // 下载成功返回唯一 id  1000001   2 2
			       if(id!=1){
					   comm.alert("播放失败");
			       }
			      
			    },
			    JS_StopPlay:function(agentNum,remotePath){
			    	
			    	//var localPath="E:\\lingjun\\互生线\\callcenter\\下载录音文件\\aaa1.wav";   //本地路径: 路径+语音文件名 
			       // var remotePath="G:\\HSRecordVoc\\20150605\\807\\111913-O-018677320280.wav";    // 录音文件下啊在地址
			        
			        //var remotePath="E:\\HBS.doc";    // 录音文件下啊在地址

			        var id=Test.JS_StopPlay(agentNum,remotePath);   // 下载成功返回唯一 id  1000001   2 2
			        if(id!=1){
						comm.alert("停止播放失败");
				       }
			    }
		
	};
	//单个下载
	$(document).on('click','.href_download',function(e){
		var data = $(e.currentTarget).attr('data');
		window.open(data);
		//downlondRecording(data);
	});
	
	$(document).on('click','ul li' ,function(e){	
		var target = $(e.currentTarget);
		var dataId= target.attr('data-id');
		
		switch (dataId){
			case "1":
				callCenter.bindCallList();
				break;
			case "2" : 				
				callCenter.bindCallIng();
				break;
			case "3" : 				
				callCenter.bindCallAccepted();
				break;
			case "4" : 				
				callCenter.bindCallVoice();
				break;
		}
	});
	//绑定事件
	function initClick(){
			// 来电管理查询按钮事件
	$("#callListScash").off("click").click(function(){
		searchCallStateList();
	});
			// 正在通话查询按钮事件
	$("#callIngScash").off("click").click(function(){
		qryCallIng();
	});
			// 已接来电查询按钮事件
	$("#callAcceptedScash").off("click").click(function(){
		qryCallAccepted();
	});
			// 语音留言查询按钮事件
	$("#callVoiceScash").off("click").click(function(){
		qryVoice();
	});
           //全选录音
	$("#callVoiceChecked").off("click").click(function(){
		setChecked(this.checked);
	});
		
	}
//查询来电管理列表
	function searchCallStateList () {
		//查询条件 来电状态
		var callState=$("#callState").val();
		var bDate_Call=$("#bDate_Call").val();
		var eDate_Call=$("#eDate_Call").val();
		var agentNo_Call=$("#agentNo_Call").val();
				if (agentNo_Call == "输入坐席号") {
					agentNo_Call = "";
				}
				if (callState == "--请选择--") {
					callState = "";
				}

		if (scash_callListDataTable != null) {
			$("#scash_callList").DataTable().destroy();
		}
		scash_callListDataTable = $("#scash_callList").DataTable(
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
						"url" : comm.domainList['server']+comm.UrlList["qryCallList"],
						"type" : "POST",		
						"data":{"queryType":callState,"date1":bDate_Call,"date2":eDate_Call,"agentNo":agentNo_Call}
					},
					"columns" : [ /*{
						"data" : "id"
					},*/ {
						"data" : "callingNum"
					}, {
						"data" : "agentNum"
					}, {
						"data" : "userName"
					}, {
						"data" : "createTime"
					}, {
						"data" : "duration"
					} ],
					"columnDefs" : [
								/*	{
										"targets" : [ 0 ],
										"render" : function(data, type, full) {
											return "<div class='checker'><span ><input  id=\"callList"
													+ data
													+ "\" type=\"checkbox\" value=\""
													+ data + "\"/></span></div>";
										}
									} ,	*/
								{
								"targets" : [4 ],
								"render" : function(data, type, full) {
									//return ajax.formatDate(data,"yyyy-MM-dd HH:mm:ss");
									return data;
								}
							}
					]
				});
	};
	//正在通话查询
	function qryCallIng(){
		//查询条件 坐席号
		var callIngAgentNo=$("#callIngAgentNo").val();
		if (callIngAgentNo == "输入坐席号") {
			callIngAgentNo = "";
		}
		//显示列表
		if (scash_callIngDataTable != null) {
			$("#scash_callIng_tab").DataTable().destroy();
		}
		scash_callIngDataTable = $("#scash_callIng_tab").DataTable(
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
						"url" : comm.domainList['server']+comm.UrlList["qryCallIng"],
						"type" : "POST",		
						"data":{"agentNo":callIngAgentNo}
					},
					"columns" : [ {
						"data" : "callerNum"
					}, {
						"data" : "agentNum"
					}, {
						"data" : "userName"
					}, {
						"data" : "createTime"
					} , {
						"data" : "agentNum"
					} ],
					"columnDefs" : [
								{
								"targets" : [3 ],
								"render" : function(data, type, full) {
									if(data)
									return comm.formatDate(data);
									else
										return data;
								}
							},{
								"targets" : [4],
								"render" : function(data, type, full) {
									var str='<a data="'+data+'"  class="searchCashDetailed1"  title="监听">监听</a>';
									return str;
								}
							}

					]
				});
		
		
		$("#_MonitorUser").off('click','.searchCashDetailed1').on('click','.searchCashDetailed1',function(e){//监听
			var monitor_AgentNum=dialing.gettxtnumByMac();
			var agentNum = $(e.currentTarget).attr('data');
			var title = $(e.currentTarget).attr('title');
			//返回值：1 成功，0失败
			if("监听"==title){
				var ret=	Test.JS_MonitorUser(agentNum,monitor_AgentNum);//监听
				if(ret=="1"){
					comm.alert("监听成功!");
					$(this).attr("title","取消监听");
					$(this).text("取消监听");
				}else{
					comm.alert("监听失败!");
				}
			}else{
				var ret=Test.JS_StopMonitorUser(agentNum,monitor_AgentNum);//取消监听
				if(ret=="1"){
					comm.alert("取消监听成功!");
					$(this).attr("title","监听");
					$(this).text("监听");
				}else{
					comm.alert("取消监听失败!");
				}
			}

		});
		
	};
	
	
/*	function BrowseFolder() {
	    try {
	        var Message = "Please select the folder path.";  //选择框提示信息
	        var Shell = new ActiveXObject("Shell.Application");
	       // var Folder = Shell.BrowseForFolder(0, Message, 0x0040, 0x11); //起始目录为：我的电脑
	        var Folder = Shell.BrowseForFolder(0,Message,0); //起始目录为：桌面
	        if (Folder != null) {
	            Folder = Folder.items();  // 返回 FolderItems 对象
	            Folder = Folder.item();  // 返回 Folderitem 对象
	            Folder = Folder.Path;   // 返回路径
	            if (Folder.charAt(Folder.length - 1) != "\\") {
	                Folder = Folder + "\\";
	            }
	            alert(Folder+"11111111111");
	            return Folder;
	        }
	    } catch (e) {
	        alert(e.message);
	    }
	}*/
	//已接来电列表
	function qryCallAccepted(){
		//查询条件 坐席号
		var bindCallAgentNo=$.trim($("#bindCallAgentNo").val());
		var bDate_Call=$("#bindCallBegin").val();
		var eDate_Call=$("#bindCallEnd").val();
		var laidianhaoma=$.trim($("#bindCallTelNo").val());
		var callWay = $("#callWay option:selected").val();
		if (bindCallAgentNo == "输入坐席号") {
			bindCallAgentNo = "";
		}
		if (laidianhaoma == "输入来电号码") {
			laidianhaoma = "";
		}
		if (bindCallAccepted_DataTable != null) {
			$("#bindCallAccepted_tab").DataTable().destroy();
		}
		bindCallAccepted_DataTable = $("#bindCallAccepted_tab").DataTable(
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
						"url" : comm.domainList['server']+comm.UrlList["qryCallAccepted"],
						"type" : "POST",		
						"data":{"agentNo":bindCallAgentNo,"date1":bDate_Call,"date2":eDate_Call,"callingNo":laidianhaoma,"queryType":callWay}
					},
					"columns" : [ /*{
						"data" : "id"
					},*/ {
						"data" : "callingNum"
					}, {
						"data" : "agentNum"
					}, {
						"data" : "userName"
					}, {
						"data" : "recordTime"
					}, {
						"data" : "filePath"
					}, {
						"data" : "duration"
					} , {
						"data" : "callType"
					} ],
					"columnDefs" : [
								/*	{
										"targets" : [ 0 ],
										"render" : function(data, type, full) {
											return "<div class='checker'><span ><input  id=\"bindCallAccepted"
													+ data
													+ "\" type=\"checkbox\"  value=\""
													+ data + "\"/></span></div>";
										}
									} ,	*/
								{
								"targets" : [4 ],
								"render" : function(data, type, full) {
									if(data!=null){
										 var filePathArray = data.split('\\');
										return filePathArray[filePathArray.length-1]
									}else{
									//return ajax.formatDate(data,"yyyy-MM-dd HH:mm:ss");
									return data;
									}
								}
							},{
								"targets" : [ 6 ],
								"render" : function(data, type, full) {
										if(data=="O"){
											return "呼出";
										}else{
											return "呼入";
										}
								}
							
							} ,{
								"targets" : [7],
								"render" : function(data, type, full) {
									var str='<a  class="YYXZ" filePath=\"'+full.filePath+'\" title="语音下载">下载</a>｜<a  class="YYBF" filePath=\"'+full.filePath+'\"  agentno=\"'+full.agentNum+'\" title="语音播放">播放</a>|<a  class="YYSTOP" filePath=\"'+full.filePath+'\" agentno=\"'+full.agentNum+'\" title="播放停止">停止播放</a>';
									return str;
								}
							}
					]
				});
		
		//语音下载
		$("#bindCallAccepted_tab").off('click','.YYXZ').on('click','.YYXZ',function(e){
	
			var filePath = $(e.currentTarget).attr('filePath');
			callCenter.JS_LoadFile(filePath);
		});
		
		$("#bindCallAccepted_tab").off('click','.YYBF').on('click','.YYBF',function(e){
			
			var filePath = $(e.currentTarget).attr('filePath');
			callCenter.JS_PlayVoc(callCenter.gettxtnumByMac(),filePath);
		});
		
	$("#bindCallAccepted_tab").off('click','.YYSTOP').on('click','.YYSTOP',function(e){
			var filePath = $(e.currentTarget).attr('filePath');
			callCenter.JS_StopPlay(callCenter.gettxtnumByMac(),filePath);
		});
	};
	//查询语音留言列表
	function qryVoice(){
		//查询条件 坐席号
		var callVoiceCallingNum=$("#callVoiceAgentNo").val();
		var bDate_Call=$("#callVoiceBegin").val();
		var eDate_Call=$("#callVoiceEnd").val()+" 23:59:59";
		if (callVoiceCallingNum == "输入来电号码") {
			callVoiceCallingNum = "";
		}
		//callVoice_tab
		if (callVoice_DataTable != null) {
			$("#callVoice_tab").DataTable().destroy();
		}
		callVoice_DataTable = $("#callVoice_tab").DataTable(
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
						"url" : comm.domainList['server']+comm.UrlList["qryCallVoice"],
						"type" : "POST",		
						"data":{"callingNum":callVoiceCallingNum,"date1":bDate_Call,"date2":eDate_Call}
					},
					"columns" : [ /*{
						"data" : "id"
					},*/ {
						"data" : "callingNum"
					}, {
						"data" : "createTime"
					}, {
						"data" : "duration"
					}, {
						"data" : "filePath"
					}, {
						"data" : "filePath"
					} ],
					"columnDefs" : [
									{
										"targets" : [ 3 ],
										"render" : function(data, type, full) {
											if(data!=null){
												 var filePathArray = data.split('\\');
												return filePathArray[filePathArray.length-1]
											}else{
											return data;
											}
										}
									} ,{	
								"targets" : [4],
								"render" : function(data, type, full) {
									var str="<a filePath=\""+data+"\" class=\"LYXZ\" title=\"留言下载\">下载</a>";
									return str;
								}
							}
					]
				});
		//留言下载
		$("#callVoice_tab").off('click','.LYXZ').on('click','.LYXZ',function(e){
	
			var filePath = $(e.currentTarget).attr('filePath');
			callCenter.JS_LoadFile(filePath);
		});
	};

	return callCenter;	
});
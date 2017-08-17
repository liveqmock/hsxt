define(['text!toolorderTpl/shddgl/hsjfgjcl.html',
		'text!toolorderTpl/shddgl/hsjfgjcl_cxgl_dialog.html',
		'text!toolorderTpl/shddgl/hsjfgjcl_plcxgl_dialog.html',
		'toolorderDat/toolorder'
		], function(hsjfgjclTpl, hsjfgjcl_cxgl_dialogTpl, hsjfgjcl_plcxgl_dialogTpl, dataModule){
	var self = {
		showPage : function(){
			var searchTable = null;
			var uploadGridObj = null;
			var selectUploadObj = null;
			$('#busibox').html(_.template(hsjfgjclTpl));

			/*表格数据模拟*/
			$('#qry_hsjfgjcl_btn').click(function(res){

				var seqNo = $("#seqNo").val().trim();
				var entResNo = $("#entResNo").val().trim();
				if( seqNo=="" && entResNo == "" ){
					return;
				}
				var reqParam = {
					search_seqNo	:	seqNo,
					search_entResNo	:	entResNo
				};
				searchTable = comm.getCommBsGrid("searchTable110","queryEntDeviceList",reqParam,comm.lang("toolorder"),self.detail);

			});

			/*end*/

			$('#cxgl_btn').click(function(){
				if(comm.isEmpty(searchTable)){
					return false;
				}
				var objAry = searchTable.getCheckedRowsRecords();
				if(comm.isEmpty(objAry)){
					comm.error_alert(comm.lang("toolorder").notDeviceSelectRows);
					return false;
				}
				var entResNo = objAry[0].entResNo;
				var entCustName = objAry[0].entCustName;
				var entCustId = objAry[0].entCustId;
				if(objAry.length<1){
					return ;
				}
				$('#dialogBox_cxgl').html(_.template(hsjfgjcl_cxgl_dialogTpl));

				selectUploadObj = comm.getEasyBsGrid("searchTable_selected",objAry,function(record, rowIndex, colIndex, options){
					if(colIndex == '4'){
						return comm.lang("toolorder").categoryCode[record.categoryCode];
					}
					if(colIndex == '6'){
						return '<select id="disposeType'+rowIndex+'"><option value="">-- 选择 --</option><option value="1">无故障</option><option value="2">重新配置</option><option value="3">换货</option><option value="4">维修</option></select>';
					}
					if(colIndex == '7'){
						return '<input type="text" id="disposeAmount'+rowIndex+'" name="" value="" maxlength="10" class="infobox pl5 pr5 w100" placeholder="输入维修补款金额" onkeyup="if(isNaN(value))execCommand(\'undo\')" onafterpaste="if(isNaN(value))execCommand(\'undo\')" />';
					}
				});

				/*弹出框*/
				$( "#dialogBox_cxgl" ).dialog({
					title:"重新关联/灌秘钥",
					width:"1000",
					height:"420",
					closeIcon : true,
					modal:true,
					buttons:{
						"确定":function(){
							var uploadParam = {
								targetEntResNo	:	entResNo,
								entCustName	:	entCustName,
								custIdEnt	:	entCustId,
								reqOperator	:	$.cookie('custId'),
								reqRemark	:	$("#reqRemark").val().trim()
							};
							var uploadArray = [];
							var objAry = selectUploadObj.getAllRecords();
							for(var i=0;i<objAry.length;i++){
								var obj = objAry[i];

								var str="";
								if(obj.deviceSeqNo){
									str += obj.deviceSeqNo+";";
								}else{
									str += ";";
								}
								if(obj.terminalNo){
									str += obj.terminalNo+";";
								}else{
									str += ";";
								}

								if($('#disposeType'+i).val()){
									str += $('#disposeType'+i).val() +";";
								}else{
									comm.error_alert(comm.lang("toolorder").pleaseSelectDisposeType);
									return;
								}
								if($('#disposeAmount'+i).val()){
									str += $('#disposeAmount'+i).val();
								}else{
									comm.error_alert(comm.lang("toolorder").pleaseInputDisposeAmount);
									return;
								}
								uploadArray.push(str);//obj.entResNo+","+obj.terminalNo+","+obj.deviceSeqNo+","+obj.deviceFalg);
							}
							uploadParam.listDetail = encodeURIComponent(JSON.stringify(uploadArray));

							var validTypeObj = $("#verificationMode_cxgl").attr("optionvalue");
							if(!validTypeObj){
								comm.error_alert(comm.lang("toolorder").confirmType);
								return;
							}
							comm.verifyDoublePwd($("#cxglOptUserId").val(),$("#cxglPwd").val(),validTypeObj,function(res){
								dataModule.addAfterPaidOrder(uploadParam,function(res){
									comm.alert({content:comm.lang("toolorder")[22000], callOk:function(){
										$("#dialogBox_cxgl").dialog( "destroy" );
										$('#qry_hsjfgjcl_btn').click();
									}});
								});
							});
						},
						"取消":function(){
							$( this ).dialog( "destroy" );
						}
					}
				});
				/*end*/

				/*表格数据模拟*/
				var json_1 = [{
					"th_1":"01001010000",
					"th_2":"中国移动",
					"th_4":"xxx",
					"th_5":"xxx",
					"th_6":"xxx",
					"th_7":"xxx"
				},
					{
						"th_1":"01001010001",
						"th_2":"中国联通",
						"th_4":"xxx",
						"th_5":"xxx",
						"th_6":"xxx",
						"th_7":"xxx"
					},
					{
						"th_1":"01001010002",
						"th_2":"中国电信",
						"th_4":"xxx",
						"th_5":"xxx",
						"th_6":"xxx",
						"th_7":"xxx"
					}];

				var gridObj;

				gridObj = $.fn.bsgrid.init('searchTable_1', {
					pageSizeSelect: true ,
					pageSize: 10 ,
					stripeRows: true,  //行色彩分隔
					displayBlankRows: false ,   //显示空白行
					localData:json_1,
					operate : {
						edit : function(record, rowIndex, colIndex, options){

							var link1 = null;

							if(colIndex == '7'){
								link1 = '<select><option>-- 选择 --</option><option>已损坏</option><option>重新配置</option><option>无故障</option></select>'
							}
							else if(colIndex == '8'){
								link1 = '<input type="text" id="" name="" value="" class="infobox pl5 pr5 w100" placeholder="输入维修补款金额" />';
							}

							return link1;

						}.bind(this)
					}


				});

				/*end*/


				/*下拉列表*/
				$("#verificationMode_cxgl").selectList({
					width : 180,
					optionWidth : 185,
					options:[
						{name:'密码验证',value:'1'},
						{name:'指纹验证',value:'2'},
						{name:'刷卡验证',value:'3'}
					]
				}).change(function(e){
					var val = $(this).attr('optionValue');
					switch(val){
						case '1':
							$('#fhyPwd_cxgl').removeClass('none');
							$('#verificationMode_prompt_cxgl').addClass('none');
							break;

						case '2':
							$('#fhyPwd_cxgl').addClass('none');
							$('#verificationMode_prompt_cxgl').removeClass('none');
							break;

						case '3':
							$('#fhyPwd_cxgl').addClass('none');
							$('#verificationMode_prompt_cxgl').removeClass('none');
							break;
					}
				});
				/*end*/



			});


			$('#plcxgl_btn').click(function(){
				$('#dialogBox_plcxgl').html(_.template(hsjfgjcl_plcxgl_dialogTpl));
				$('#upload_btn').click(function(){
					if($("#seqFile").val()==""){
						return;
					}
					var fileName = $("#seqFile").val();
					var extName = fileName.substring(fileName.lastIndexOf("."));
					if(extName.toLowerCase()!=".txt"){
						comm.error_alert(comm.lang("toolorder")[36276]);
						return ;
					}
					var files = ['seqFile'];
					var url = comm.domainList[comm.getProjectName()]+comm.UrlList["uploadSeqFile"];
					comm.uploadFile(url, files, function(res){
						uploadGridObj = comm.getEasyBsGrid("jqm_table",res,function(record, rowIndex, colIndex, options){
							if(colIndex==4){
								return comm.lang("toolorder").deviceFalgEnum[record.deviceFalg];
							}
						});
					},function(){});
				});
				/*弹出框*/
				$( "#dialogBox_plcxgl" ).dialog({
					title:"批量导入重新关联/灌秘钥",
					width:"820",
					height:"520",
					closeIcon : true,
					modal:true,
					buttons:{
						"确定":function(){

							if(uploadGridObj == null ){
								return;
							}

							var validTypeObj = $("#validType").attr("optionvalue");
							if(!validTypeObj){
								comm.error_alert(comm.lang("toolorder").confirmType);
								return;
							}
							var batchParam = {
								remark		:	$("#remark").val(),
								optUserId	:	$("#optUserId").val()
							};

							var objAry = uploadGridObj.getAllRecords();
							var uploadAry = [];
							for(var i=0;i<objAry.length;i++){
								var obj = objAry[i];
								if(obj.deviceFalg != 1 ){
									continue;
								}
								var str="";
								if(obj.entResNo){
									str += obj.entResNo+",";
								}else{
									str += ",";
								}
								if(obj.terminalNo){
									str += obj.terminalNo+",";
								}else{
									str += ",";
								}
								if(obj.deviceSeqNo){
									str += obj.deviceSeqNo+",";
								}else{
									str += ",";
								}
								if(obj.deviceFalg){
									str += obj.deviceFalg;
								}else{
									str += ",";
								}
								uploadAry.push(str);//obj.entResNo+","+obj.terminalNo+","+obj.deviceSeqNo+","+obj.deviceFalg);
							}
							if(uploadAry.length<1){
								comm.error_alert(comm.lang("toolorder").noRecord);
								return;
							}
							batchParam.devices = encodeURIComponent(JSON.stringify(uploadAry));


							comm.verifyDoublePwd($("#optUserId").val(),$("#pwd").val(),validTypeObj,function(res){
								dataModule.batchUpload(batchParam,function(res){
									comm.alert({content:comm.lang("toolorder")[22000], callOk:function(){
										$("#dialogBox_plcxgl").dialog( "destroy" );
										$('#qry_hsjfgjcl_btn').click();
									}});
								});
							});
						},
						"取消":function(){
							$( this ).dialog( "destroy" );
						}
					}
				});
				/*end*/


				/*下拉列表*/
				$("#validType").selectList({
					width : 180,
					optionWidth : 185,
					options:[
						{name:'密码验证',value:'1'},
						{name:'指纹验证',value:'2'},
						{name:'刷卡验证',value:'3'}
					]
				}).change(function(e){
					var val = $(this).attr('optionValue');
					switch(val){
						case '1':
							$('#fhyPwd_plcxgl').removeClass('none');
							$('#verificationMode_prompt_plcxgl').addClass('none');
							break;

						case '2':
							$('#fhyPwd_plcxgl').addClass('none');
							$('#verificationMode_prompt_plcxgl').removeClass('none');
							break;

						case '3':
							$('#fhyPwd_plcxgl').addClass('none');
							$('#verificationMode_prompt_plcxgl').removeClass('none');
							break;
					}
				});
				/*end*/



			});

		},
		detail : function(record, rowIndex, colIndex, options){
			if(colIndex==5){
				return comm.lang("toolorder").categoryCode[record.categoryCode];
			}
			var obj = searchTable.getRecord(rowIndex);
			var	link1 = $('<a>查看</a>').click(function(){
			});

			return link1;

		},
		chaKan : function(obj){

			$('#dialogBox_ck').html('<div id="bz_text"></div>').dialog({
				title : '备注信息',
				width : 500,
				modal : true,
				closeIcon : true,
				buttons : {
					'关闭' : function(){
						$(this).dialog('destroy');
					}
				}
			});

			$('#bz_text').text(obj.th_7);
		}
	};
	return self;
});
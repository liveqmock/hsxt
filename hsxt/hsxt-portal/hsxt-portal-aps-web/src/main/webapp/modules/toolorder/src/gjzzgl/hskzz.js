define(['text!toolorderTpl/gjzzgl/hskzz.html',
		'text!toolorderTpl/gjzzgl/hskzz_ck_dialog.html',
		'text!toolorderTpl/gjzzgl/hskzz_kk_dialog.html',
		'text!toolorderTpl/gjzzgl/hskzz_zzdzc_dialog.html',
		'text!toolorderTpl/gjzzgl/hskzz_zzdzc_fh_dialog.html',
		'text!toolorderTpl/gjzzgl/hskzz_hskrk_dialog.html',
		'text!toolorderTpl/gjzzgl/hskzz_dcmm_dialog.html',
		'text!toolorderTpl/gjzzgl/hskzz_ckzzd_dialog.html',
		'text!toolorderTpl/gjzzgl/hskzz_kysc_dialog.html',
		'text!toolorderTpl/gjzzgl/hskzz_kysc_fh_dialog.html',
		'toolorderDat/toolorder'
		], function(hskzzTpl, hskzz_ck_dialogTpl, hskzz_kk_dialogTpl, hskzz_zzdzc_dialogTpl, hskzz_zzdzc_fh_dialogTpl, hskzz_hskrk_dialogTpl, hskzz_dcmm_dialogTpl, hskzz_ckzzd_dialogTpl, hskzz_kysc_dialogTpl, hskzz_kysc_fh_dialogTpl,toolorder){
	var cardStylyMap = {};
	var self = {
		gridObj : null,
		showPage : function(){
			$('#busibox').html(_.template(hskzzTpl));
			//时间控件
			comm.initBeginEndTime('#search_startDate','#search_endDate');

			//**下拉列表  订单类型*/
			comm.initSelect("#orderType",comm.lang("toolorder").orderType2);

			//** 下拉列表 配置状态/制作状态 **/
			comm.initSelect("#state",comm.lang("toolorder").cardConfStatus);

			$("#hskzzQueryBtn").click(function(){
				if(!comm.queryDateVaild("search_form").form()){
					return;
				}
				var params = {
					search_startDate : $("#search_startDate").val(), //配置开始日期
					search_endDate : $("#search_endDate").val(),		//配置结束日期
					search_hsResNo : $("#hsResNo").val().trim(),			//互生号
					search_hsCustName : $("#hsCustName").val().trim(),		//客户名称
					search_type : $("#orderType").attr("optionvalue"),		//订单类型
					search_makeState : $("#state").attr("optionvalue")	//制作状态
				};
				gridObj = comm.getCommBsGrid("searchTable","hscardmade_list",params,comm.lang("toolorder"),self.detail,self.del,self.add,self.edit);

			});
		},
		detail : function(record, rowIndex, colIndex, options){
			var link1 = null;
			if(colIndex == 0){
				link1 =	$('<a>'+ record.orderNo +'</a>').click(function(e) {
					this.chaKan(record);
				} ) ;
			}else if(colIndex == 4){
				if(record.orderType=='109'){
					return "申报申购";
				}else {
					return  "新增申购";
				}
			}else if(colIndex == 5){
				return comm.lang("toolorder").orderType2[record.orderType];
			}else if(colIndex == 7){
				return comm.lang("toolorder").confStatus[record.confStatus];
			}else if(colIndex == 8){
				link1 =	$('<a>'+ comm.lang("toolorder").chakan +'</a>').click(function(e) {
					self.chaKan(record);
				}) ;
			}

			return link1;
		},
		//导出密码
		add : function(record, rowIndex, colIndex, options){
			if(colIndex == 8 && record.orderType !='105'){
				var link3 = null;
				if(record.confStatus == '3'||record.confStatus == '4'||record.confStatus == '5'
					||record.confStatus == '6'||record.confStatus == '7'){	//导出密码
					link3 = $('<a>'+comm.lang("toolorder").dcmm+'</a>').click(function(e) {
						self.dcmm(record);
					}) ;
				}
				return link3;
			}
		},
		//查看制作单
		del : function(record, rowIndex, colIndex, options){
			if(colIndex == 8){
				var link3 = null;
				if(record.confStatus == '4'||record.confStatus == '5'||record.confStatus == '6'
					||record.confStatus == '7'){	//查看制作单
					link3 = $('<a>'+comm.lang("toolorder").ckzzd+'</a>').click(function(e) {
						self.ckzzd(record);
					}) ;
				}
				return link3;
			}
		},
		edit : function(record, rowIndex, colIndex, options){
			if(colIndex == 8){
				var link2 = null;
				if(record.confStatus == '2'){  //开卡
					link2 = $('<a>'+comm.lang("toolorder").kaika+'</a>').click(function(e) {
						self.kaiKa(record);
					} ) ;
				}
				else if(record.confStatus == '3'){ //制作单作成2
					link2 = $('<a>'+comm.lang("toolorder").zzdzc+'</a>').click(function(e) {
						self.zzdzc(record);
					} ) ;
				}
				else if(record.confStatus == '4'){ //互生卡入库
					link2 = $('<a>'+comm.lang("toolorder").hskrk+'</a>').click(function(e) {
						self.hskrk(record);
					} ) ;
				}

				return link2;
			}

		},
		chaKan : function(record){
			toolorder.seachToolOrderDetail({orderNo:record.orderNo},function(res){
				var obj = res.data;
				//支付方式
				obj.pay = comm.lang("toolorder").payChannel2[res.data.order.payChannel];
				//订单状态
				obj.status = comm.lang("toolorder").orderState[res.data.order.orderStatus];
				//订单类型
				obj.type = comm.lang("toolorder").orderType2[res.data.order.orderType];
				//订单总价
				obj.orderTotal = res.data.order.orderType == '109' ? '------':comm.formatMoneyNumber(res.data.order.orderHsbAmount);

				$('#dialogBox_ck').html(_.template(hskzz_ck_dialogTpl, obj));

				/*弹出框*/
				$( "#dialogBox_ck" ).dialog({
					title:"互生卡订单详情",
					width:"800",
					closeIcon : true,
					modal:true,
					buttons:{
						"关闭":function(){
							$( this ).dialog( "destroy" );
						}
					}
				});
			})
		},
		//开卡
		kaiKa : function(record){
			$('#dialogBox_kk').html(_.template(hskzz_kk_dialogTpl));
			var remamke = record.orderType=='105'?'<br /><br />'+comm.lang("toolorder").comfirkaika2:'';
			var confirmObj = {
				imgFlag:true ,             //显示图片
				width: 400,
				imgClass: 'tips_ques' ,         //图片类名，默认tips_ques
				content : '<span class="red">'+comm.lang("toolorder").comfirkaika1+'？</span>'+remamke,
				callOk :function(){
					/*弹出框*/
					$( "#dialogBox_kk" ).dialog({
						title:comm.lang("toolorder").hskkqr, //互生卡开卡确认
						width:"400",
						height:'300',
						modal:true,
						closeIcon : true,
						buttons:{
							"确定":function(){
								//验证
								if($("#verificationMode_kk").attr("optionvalue") != "1"){
									return;
								}
								if (!self.validateData())
								{
									return;
								}
								$("#dialogBox_kk").parent().find(".ui-dialog-buttonset button:eq(0)").attr("disabled",true);
								var obj = $(this);

								var userName=$("#kkfhUserName").val();
								var dealPwd = $.trim($("#kkfhPassWord").val());	//获取复核密码
								if(userName==""||userName=="undefined"){
									comm.error_alert(comm.lang("toolorder").userName_must);
									return false;
								}
								if(dealPwd==""||dealPwd=="undefined"){
									comm.error_alert(comm.lang("toolorder").passWord_must);
									return false;
								}
								//验证双签
								comm.verifyDoublePwd($("#kkfhUserName").val(), $("#kkfhPassWord").val(), 1, function(verifyRes){

									var postData = {
										confNo : record.confNo,				 //配置单编号
										orderType:record.orderType,			//订单类型
										operNo : comm.getSysCookie('custName')	//操作员
									};
									toolorder.openCard(postData,function(res){
										comm.yes_alert(comm.lang("toolorder").openCardSuccess,null,function () {
											if(gridObj)
											{
												gridObj.refreshPage();
											}
											obj.dialog("destroy");
										});
									});
								});

								window.setTimeout(function(){$("#dialogBox_kk").parent().find(".ui-dialog-buttonset button:eq(0)").attr("disabled",false)}, 4000);
							},
							"取消":function(){
								$("#dialogBox_kk #apprRemark").tooltip().tooltip("destroy");
								$("#dialogBox_kk #verificationMode_kk").tooltip().tooltip("destroy");
								$("#dialogBox_kk #kkfhUserName").tooltip().tooltip("destroy");
								$("#dialogBox_kk #kkfhPassWord").tooltip().tooltip("destroy");
								$(this).dialog( "destroy" );
							}
						}
					});
					/*end*/

					/*下拉列表*/
					comm.initSelect("#verificationMode_kk",
						comm.lang("toolorder").verificationMode).change(function(e){
						var val = $(this).attr('optionValue');
						switch(val){
							case '1':
								$('#fhy_userName_kk, #fhy_password_kk').removeClass('none');
								$('#verificationMode_prompt_kk').addClass('none');
								break;

							case '2':
								$('#fhy_userName_kk, #fhy_password_kk').addClass('none');
								$('#verificationMode_prompt_kk').removeClass('none');
								break;

							case '3':
								$('#fhy_userName_kk, #fhy_password_kk').addClass('none');
								$('#verificationMode_prompt_kk').removeClass('none');
								break;
						}
					});

					$("#verificationMode_kk").selectListValue("1");
				}

			}

			comm.confirm(confirmObj);
		},
		validateData : function(){
			return comm.valid({
				formID : '#hskzz_kk_dialog',
				rules : {
					kkfhUserName:{
						required : true
					},
					kkfhPassWord:{
						required : true
					},
					verificationMode_kk:{
						required : true
					},
					apprRemark:{
						maxlength : 300
					}
				},
				messages : {
					kkfhUserName:{
						required : comm.lang("toolorder")[34505]
					},
					kkfhPassWord:{
						required : comm.lang("toolorder")[34506]
					},
					verificationMode_kk:{
						required : comm.lang("toolorder").confirmType
					},
					apprRemark:{
						maxlength : comm.lang("toolorder").fuheMaxLength
					}
				}
			});
		},
		//
		rkvalidateData : function(){
			return comm.valid({
				formID : '#hskzz_rk_dialog',
				rules : {
					rkfhUserName:{
						required : true
					},
					rkfhPassWord:{
						required : true
					}
				},
				messages : {
					rkfhUserName:{
						required : comm.lang("toolorder")[34505]
					},
					rkfhPassWord:{
						required : comm.lang("toolorder")[34506]
					}
				}
			});
		},
		//
		
		validateData_hsxzz_zzdzc_fh_dialog : function(){

			var validate =  $("#validateData_hsxzz_zzdzc_fh_dialog").validate({
				rules : {
					verificationMode_zzdzc:{
						required : true
					},
					zzdzcfhUserName:{
						required : false
					},
					zzdzcfhPassWord:{
						required : false
					},
					verificationMode_kk:{
						required : true
					},
					cardStyleName:{
						required : true
					}
				},
				messages : {
					verificationMode_zzdzc:{
						required : comm.lang("toolorder")[36521]
					},
					zzdzcfhUserName:{
						required : comm.lang("toolorder")[34505]
					},
					zzdzcfhPassWord:{
						required : comm.lang("toolorder")[34506]
					},
					verificationMode_kk:{
						required : comm.lang("toolorder").confirmType
					},
					cardStyleName:{
						required : comm.lang("toolorder").cardStyleName
					}
				},
				errorPlacement : function (error, element) {
					var errorElement = $(element);
					if(errorElement.attr('id')!='vaildCode'||errorElement.attr('id')!='applyReason'){
						errorElement = $(element).parent().parent();
					}
					errorElement.attr("title", $(error).text()).tooltip({
						tooltipClass: "ui-tooltip-error",
						destroyFlag : true,
						destroyTime : 3000,
						position : {
							my : "left+2 top+30",
							at : "left top"
						}
					}).tooltip("open");
					$(".ui-tooltip").css("max-width", "230px");
					$(".ui-tooltip").css("left", "750px");
				},
				success : function (element) {
					$(element).tooltip();
					$(element).tooltip("destroy");
				}
			});
			if($("#verificationMode_zzdzc").attr("optionvalue")==1){
				validate.settings.rules.kkfhUserName = {required : true};
				validate.settings.rules.kkfhPassWord = {required : true};
			}else{
				validate.settings.rules.kkfhUserName = {required : false};
				validate.settings.rules.kkfhPassWord = {required : false};
			}
			return validate;
		},
		validateData_fh : function(){
			return comm.valid({
				formID : '#hskzz_kk_dialog_fh',
				rules : {
					kkfhUserName:{
						required : true
					},
					kkfhPassWord:{
						required : true
					}
				},
				messages : {
					kkfhUserName:{
						required : comm.lang("toolorder")[34505]
					},
					kkfhPassWord:{
						required : comm.lang("toolorder")[34506]
					}
				}
			});
		},
		validateHskzz_zzdzc_dialog : function(){
			return comm.valid({
				formID : '#hskzz_zzdzc_dialog',
				rules : {
					hskty:{
						required : true
					},
					gys:{
						required : true
					}
				},
				messages : {
					hskty:{
						required : comm.lang("toolorder")[34508]
					},
					gys:{
						required : comm.lang("toolorder")[34509]
					}
				}
			});
		},
		// 设置卡样
		setCardStyle : function(cardStyleId){
			var obj = cardStylyMap[cardStyleId];
			var styleName = obj.cardStyleName;
			var fileid = obj.microPic;
			var sourceId = obj.sourceFile;
			$("#dialogBox_zzdzc #carstyle").text(styleName);
			$("#dialogBox_zzdzc #carstyleView").attr("src",comm.getFsServerUrl(fileid));
			if(comm.isNotEmpty(sourceId)){
				comm.initDownload("#dialogBox_zzdzc #downloadSource", {1000:{fileId:sourceId, docName:''}}, 1000);
			}
		},
		//制作单作成
		zzdzc : function(obj){
			var param = {
				orderNo : obj.orderNo,		//订单编号
				targetEntCustId : obj.entCustId,	//企业客户号
				confNo : obj.confNo,		//配置单编号
				targetEntResNo : obj.entResNo		//企业互生号
			};
			//查询制作单作成信息
			toolorder.productionList(param,function(res){

				var data = res.data;

				//订单数量
				data.quantity = obj.quantity;
				
				data.hsCustName = obj.custName;

				//订单类型
				data.type = comm.lang("toolorder").orderType2[obj.orderType];

				//初始化制作单作成信息页面
				$('#dialogBox_zzdzc').html(_.template(hskzz_zzdzc_dialogTpl, data));

				//初始化页面卡样式下拉列表
				var options = [];
				var stys = data.card.styles;
				cardStylyMap.length = 0;
				var styles = data.card.styles;
				for(var i = 0; i < styles.length ; i ++){
					var key = styles[i];
					cardStylyMap[key.cardStyleId] = key;
					options.push({name:key.cardStyleName, value:key.cardStyleId});
				}
				$("#hskty").selectList({
					width:120,
					borderWidth:1,
					borderColor:'#CCC',
					optionWidth:120,
					options:options
				}).change(function(e){
					self.setCardStyle($(this).attr("optionvalue"))
				});
				if(comm.isNotEmpty(data.card.cardStyleId)){
					$("#hskty").selectListValue(data.card.cardStyleId);
					self.setCardStyle(data.card.cardStyleId);
				}
				//下载确认文件
				if(comm.isNotEmpty(data.card.confirmFile)){
					comm.initDownload("#dialogBox_zzdzc #downConfirmFile", {1000:{fileId:data.card.confirmFile, docName:''}}, 1000);
				}
				//初始化 供应商选择下拉列表
				var options = [];
				var sups = data.card.suppliers;
				for(k in sups){
					var key = sups[k];
					options.push({name:key.supplierName, value:key.supplierId + ":" + key.linkMan + ":" + key.phone + ":" + key.mobile});
				}
				$("#gys").selectList({
					width:220,
					optionWidth:220,
					options:options
				}).change(function(e){
					var value = $(this).attr("optionvalue");
					var linkman = value.split(":")[1];
					var phone = value.split(":")[2];
					var mobile = value.split(":")[3];
					$("#dialogBox_zzdzc #sulinkman").text(linkman);
					$("#dialogBox_zzdzc #suphone").text(comm.navNull(phone));
					$("#dialogBox_zzdzc #sumobile").text(mobile);
				});

				//制作卡数据下载
				$("#dialogBox_zzdzc #zzksjxz").attr("href",toolorder.exportDark(obj.confNo,obj.entResNo));
			});

			//弹出框方式打开 制作单作成信息页面
			$( "#dialogBox_zzdzc" ).dialog({
				title:comm.lang("toolorder").zzdzc,
				width:"1000",
				modal:true,
				closeIcon : true,
				buttons:{
					"确定":function(){

						//验证
						if (!self.validateHskzz_zzdzc_dialog())
						{
							return;
						}
						//点击确定  初始化复核页面
						var fuheData = {
							style : $("#hskty").attr("optionvalue"),  //卡样
							supplie : $("#gys").attr("optionvalue").split(":")[0]   //供应商
						};
						//初始化复核页面
						$('#dialogBox_zzdzc_fh').html(_.template(hskzz_zzdzc_fh_dialogTpl,fuheData))

						//加载图片
						var fileid = $("#hskty").attr("optionvalue").split(":")[2];
						$("#fuheStyleImg").attr("src",comm.getFsServerUrl(fileid));
						//关闭当前页面
						$( this ).dialog( "destroy" );

						//打开复核页面
						$( "#dialogBox_zzdzc_fh" ).dialog({
							title:comm.lang("toolorder").zzdzcfh,//制作单作成复核
							width:"800",
							height:"660",
							modal:true,
							closeIcon : true,
							buttons:{
								"确定":function(){
									//验证
									if (!self.validateData_hsxzz_zzdzc_fh_dialog().form())
									{
										return;
									}
									var userName=$("#zzdzcfhUserName").val();
									var dealPwd = $.trim($("#zzdzcfhPassWord").val());	//获取复核密码
									if(userName==""||userName=="undefined"){
										comm.error_alert(comm.lang("toolorder").userName_must);
										return;
									}
									if(dealPwd==""||dealPwd=="undefined"){
										comm.error_alert(comm.lang("toolorder").passWord_must);
										return;
									}
									
									
									//验证
									if($("#verificationMode_zzdzc").attr("optionvalue") != "1"){
										return;
									}
									var params = comm.getRequestParams(params);
									var remark = $("#remark").val();
									if(comm.isNotEmpty(remark)&&remark.length >= 300){
										comm.error_alert(comm.lang("toolorder").remarkIsLength);
										return
									}
									var postData = {
										//互生卡配置单制成数据
										orderNo : obj.orderNo,				//订单编号
										confNo : obj.confNo,				//配置单编号
										supplierId : $("#fhsupplie").val(), //供应商
										cardStyleId : $("#fhstyle").val(),  //卡样
										description : $("#remark").val(),		//备注
										operNo : params.custId 					//当前操作员
									};
									var open = $(this);
									//验证双签
									comm.verifyDoublePwd($("#zzdzcfhUserName").val(), $("#zzdzcfhPassWord").val(), 1, function(verifyRes){

										//互生卡制作单作成
										toolorder.cardMark(postData,function(_res){
											comm.yes_alert(comm.lang("toolorder").zzdzcSuccess,null,function () {
												if(gridObj)
												{
													gridObj.refreshPage();
												}
												open.dialog( "destroy" );
											});
										});
									});
								},
								"取消":function(){
									$("#verificationMode_zzdzc").tooltip().tooltip("destroy");
									$('#dialogBox_zzdzc_fh').dialog( "destroy" );
								}
							}
						});
						/*end*/
						/*下拉列表*/
						comm.initSelect("#verificationMode_zzdzc",comm.lang("toolorder").verificationMode,185).change(function(e){
							var val = $(this).attr('optionValue');
							switch(val){
								case '1':
									$('#fhy_userName_zzdzc, #fhy_password_zzdzc').removeClass('none');
									$('#verificationMode_prompt_zzdzc').addClass('none');
									break;

								case '2':
									$('#fhy_userName_zzdzc, #fhy_password_zzdzc').addClass('none');
									$('#verificationMode_prompt_zzdzc').removeClass('none');
									break;

								case '3':
									$('#fhy_userName_zzdzc, #fhy_password_zzdzc').addClass('none');
									$('#verificationMode_prompt_zzdzc').removeClass('none');
									break;
							}
						});
						/*end*/
					},
					"取消":function(){
						$("#dialogBox_zzdzc #hskty").tooltip().tooltip("destroy");
						$("#dialogBox_zzdzc #gys").tooltip().tooltip("destroy");
						$( this ).dialog( "destroy" );
					}
				}
			});
		},
		//互生卡入库
		hskrk : function(obj){
			toolorder.queryCardIn({orderNo:obj.orderNo},function(res){
				obj.result = res.data;
				$('#dialogBox_hskrk').html(_.template(hskzz_hskrk_dialogTpl, obj));
				//获取操作员
				cacheUtil.searchOperByCustId(res.data.card.operNo, function(res_){
					$("#operNoName").html(comm.getOperNoName(res_));
				});
			});

			/*弹出框*/
			$( "#dialogBox_hskrk" ).dialog({
				title:comm.lang("toolorder").hskrk,
				width:"800",
				modal:true,
				closeIcon : true,
				buttons:{
					"确定":function(){
						if (!self.rkvalidateData())
						{
							return;
						}
						$("#dialogBox_hskrk").parent().find(".ui-dialog-buttonset button:eq(0)").attr("disabled",true);
						var params = comm.getRequestParams(params);

						var postData = {
							confNo : obj.confNo,				 //配置单编号
							operNo : $.cookie('custId')			//操作员
						};
						var open = $( this );
						//验证双签
						comm.verifyDoublePwd($("#rkfhUserName").val(), $("#rkfhPassWord").val(), 1, function(verifyRes){

							//卡入库
							toolorder.cardIn(postData,function(_res){
								comm.yes_alert(comm.lang("toolorder").hskrkSuccess,null,function () {
									if(gridObj)
									{
										gridObj.refreshPage();
									}
									open.dialog("destroy");
								});
							});
						});
						window.setTimeout(function(){
							$("#dialogBox_hskrk").parent().find(".ui-dialog-buttonset button:eq(0)").attr("disabled",false);
							comm.closeLoading();
						}, 5000);
					},
					"取消":function(){
						$( this ).dialog( "destroy" );
					}
				}
			});
		},
		//导出密码
		dcmm : function(record){
			$('#dialogBox_dcmm').html(_.template(hskzz_dcmm_dialogTpl));

			/*弹出框*/
			$( "#dialogBox_dcmm" ).dialog({
				title:comm.lang("toolorder").dcmm,
				width:"820",
				modal:true,
				closeIcon : true,
				buttons:{
					"导出":function(){
						window.location=$("#exportMm").attr("href");
						$( this ).dialog( "destroy" );
					},
					"取消":function(){
						$( this ).dialog( "destroy" );
					}
				}
			});
			$("#exportMm").attr("href",toolorder.exportMM(record.confNo,record.entResNo));
			//查询初始密码
			toolorder.findHscPass({confNo:record.confNo},function(res){
				if(res){
					comm.getEasyBsGrid('searchTable_mm',res.data);
				}
			});
		},
		//查看制作单
		ckzzd : function(obj){
			var param = {
				orderNo : obj.orderNo,		//订单编号
				targetEntCustId : obj.entCustId,	//企业客户号
				confNo : obj.confNo,		//配置单编号
				targetEntResNo : obj.entResNo		//企业互生号
			};
			//查询制作单作成信息
			toolorder.queryCardMark(param,function(res){

				var data = res.data;

				data.custInfo = obj;

				//订单数量
				data.quantity = obj.quantity;

				data.hsCustName = obj.custName;

				//订单类型
				data.type = comm.lang("toolorder").orderType2[obj.orderType];

				//判断迁移数据没有供应商 赋值为空  by likui 2016-05-19
				if(comm.isEmpty(data.card.supplier)){
					data.card.supplier = {
						supplierName : '',
						linkMan:'',
						mobile :'',
						phone :''
					};
				}
				$('#dialogBox_ckzzd').html(_.template(hskzz_ckzzd_dialogTpl, data));

				//卡样图
				var fileid = data.card.style.microPic;
				$("#ckzzdImg").attr("src",comm.getFsServerUrl(fileid));

				//下载源文件
				var sourceId = data.card.style.sourceFile;
				if(comm.isNotEmpty(sourceId)){
					comm.initDownload("#dialogBox_ckzzd #downloadSource", {1000:{fileId:sourceId, docName:''}}, 1000);
				}

				//下载确认文件
				if(comm.isNotEmpty(data.card.confirmFile)){
					comm.initDownload("#dialogBox_ckzzd #downConfirmFile", {1000:{fileId:data.card.confirmFile, docName:''}}, 1000);
				}

				/*弹出框*/
				$("#dialogBox_ckzzd").dialog({
					title:comm.lang("toolorder").zzdxq,//制作单详情
					width:"1000",
					modal:true,
					closeIcon : true,
					buttons:{
						"关闭":function(){
							$('#dialogBox_ckzzd').dialog( "destroy" );
						}
					}
				});
				//制作卡数据下载
				$("#dialogBox_ckzzd #zzksjxz").attr("href",toolorder.exportDark(obj.confNo,obj.entResNo));
			});
			/*end*/
		},
		xiaZhai : function(obj){}
	};
	return self;
});
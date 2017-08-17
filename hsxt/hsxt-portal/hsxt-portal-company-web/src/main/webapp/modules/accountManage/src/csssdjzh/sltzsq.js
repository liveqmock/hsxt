define(['text!accountManageTpl/csssdjzh/sltzsq/sltzsq.html',
		'text!accountManageTpl/csssdjzh/sltzsq/sltzsq_xg.html',
        "accountManageDat/csssdjzh/csssdjzh",
        "accountManageLan"
		],function(tpl,xgTpl,csssdjzh){
	return sltzsq = {
		currentTaxRate:null,//当前税率
		showPage : function(){
			
			//获取最新审批结果
 			sltzsq.getLastTaxApply(function(data){
 				$('#busibox').html(_.template(tpl,data));
 				//获取最新税率
 				sltzsq.getTaxRate();
			
				//申请提交按钮事件
				$('#sltzsqBtn').click(function(){/*
					//隐藏申请页面
					$('#sltzsq_web').addClass('none');
					//显示确认页面
					$('#sltzsq_xg_web').html(_.template(xgTpl)).removeClass('none');
					
					//展示税率
					$("#sCurrentTaxRate").html(parseFloat(sltzsq.currentTaxRate).toFixed(2)+"%");
					
					//初始化数据
		 			comm.initSelect($("#txtTaxpayerType"),comm.lang("accountManage").taxpayerTypeEnum,330,0);
		 			sltzsq.initUploadImg();
		 			
		 			//下载模版
		 			$("#fileApplyImg").change(function(){
		 				var $img=$("#imgApplyImg img");
			 			comm.initTmpPicPreview($img,$img.attr("src"));
		 			});
					
					//页面返回修改
					$('#sltzsq_return').click(function(){
						//隐藏页面
						$('#sltzsq_xg_web').html('').addClass('none');
						//显示申请页面
						$('#sltzsq_web').removeClass('none');
					});
					//提交按钮事件
					$('#sltzsq_appSubmit').click(function(){
						sltzsq.submit();
					});
				*/});
 			});
		},
		/** 获取税率 */
		getTaxRate:function(){
			var sltzsq=this;
			//获取税率
			csssdjzh.queryTax(function(rsp){
				if(rsp.data!=undefined){
					sltzsq.currentTaxRate=parseFloat(rsp.data)*100;
					$("#sTaxRate").html(sltzsq.currentTaxRate+"%");
				}else{
					sltzsq.currentTaxRate=0;
					$("#sTaxRate").html("0.00%");
				}
			});
		},
		
		//提交修改数据 
		submit : function (){
			var sltzsq = this;
			// 邮政编码验证 
			jQuery.validator.addMethod("toFixed4", function(value, element) { 
			  var tel = /^(?!0+(?:\.0+)?$)(?:[1-9]\d*|0)(?:\.\d{1,2})?$/; 
			  return this.optional(element) || (tel.test(value)); 
			}, "请最多保留4位小数"); 

			if (!sltzsq.validateData()) {
				return false;
			}
			
			var imgParam={"delFileIds":sltzsq.originalPic};	//图片参数
			var uploadUrl=comm.domainList['scsWeb']+comm.UrlList["fileupload"];//文件上传地址
			comm.uploadFile(null,["fileApplyImg"],function(imgData){//上传文件
				sltzsq.originalPic=imgData.fileApplyImg;//保存上传成功的图片
				
				var applyParam={
						"proveMaterialFile" :imgData.fileApplyImg,
						"applyTaxrate" : (parseFloat($("#txtApplyTaxrate").val()) * 0.01),
						"taxpayerType":$("#txtTaxpayerType").attr("optionValue"),
						"applyReason":$("#txtApplyReason").val(),"createdName":comm.getCookie('userName')+"("+comm.getCookie('operName')+")"
				};
				//提交申请
				csssdjzh.taxAdjustmentApply(applyParam,function(res){
						comm.alert({content: '调整税率申请提交成功！',
							callOk: function(){
								//跳转至税率调整申请查询页面
								$("#csssdjzh_sltzsq").trigger('click');
							}
						});
				});
				
			},function(err){},imgParam);
			//初始化上传控件
			sltzsq.initUploadImg();
		},
		/** 申请验证 */
		validateData : function() {
			return comm.valid({
				formID : '#sltzsq_xg_appForm',
				rules : {
					txtApplyTaxrate : {
						required : true,
						number:true,
						toFixed4:true,
						range:[0.01,99.99]
					},
					txtTaxpayerType : {
						required : true
					},
					txtApplyReason : {
						required : true,
						maxlength : 300
					},
					fileApplyImg : {
						required : true
					}
				},
				messages : {
					txtApplyTaxrate : {
						required : comm.lang("accountManage")[31040],
						number:"请正确输入税率值",
						toFixed4:"税率格式不正确，请最多保留2位小数",
						range:"取值范围在0.01%~99.99%之间"
					},
					txtTaxpayerType : {
						required : comm.lang("accountManage")[31041]
					},
					txtApplyReason : {
						required : comm.lang("accountManage")[31043],
						maxlength : comm.lang("accountManage").maxlen_error
					},
					fileApplyImg : {
						required : comm.lang("accountManage")[31042]
					}
				},
				errorPlacement : function (error, element) {
					$(element).attr("title", $(error).text()).tooltip({
						tooltipClass: "ui-tooltip-error",
						position : {
							my : "left+200 top+5",
							at : "left top"
						}
					}).tooltip("open");
					$(".ui-tooltip").css("max-width", "230px");
				},
				success : function (element) {
					$(element).tooltip();
					$(element).tooltip("destroy");
				}
			});
		},
		
		/** 初始化上传控件 */
		initUploadImg:function(){
			//$("#imgApplyImg").html("<img class=\"ml5\" style=\"vertical-align:middle;\" src=\"resources/img/noImg.gif\" width=\"81\" height=\"52\" title=\"点击查看图片\">");
			//comm.initUploadBtn(["#fileApplyImg"],["#imgApplyImg"],81,52);
			
			var btnIds = ['#fileApplyImg'];
			var imgIds = ['#imgApplyImg'];
			$("body").on("change", "", function(){
				for(var k in imgIds){
					comm.initTmpPicPreview(imgIds[k], $(imgIds[k]).children().first().attr('src'));
				}
			});
			comm.initUploadBtn(btnIds, imgIds);
		},
		/**  获取最后税率调整申请记录 */
		getLastTaxApply:function(callBack){
			csssdjzh.getLastTaxApply(function(rsp){
				callBack(rsp.data);
			});
		}
	}
});
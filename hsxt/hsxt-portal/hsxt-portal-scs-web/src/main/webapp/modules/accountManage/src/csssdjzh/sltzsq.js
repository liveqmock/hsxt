define(['text!accountManageTpl/csssdjzh/sltzsq.html',
        'text!accountManageTpl/csssdjzh/sltzsq_edit.html',
        "accountManageDat/csssdjzh/csssdjzh",
        "accountManageLan"
        ],function(sltzsqTpl,sltzsq_editTpl,csssdjzh){
	return {		
		originalPic:null,		//原图片
		currentTaxRate:null,	//当前税率
		showPage : function(){
			var self=this;
			
			//获取申请记录(最后一条)
			self.getLastTaxApply(function(data){
				$('#busibox').html(_.template(sltzsqTpl,data));
				
				//初始化数据
	 			self.getTaxRate();
	 			
				// 跳转至申请页面
			 	$('#btn_sltz_edit').click(function(){/*
			 		var nextDate=data.enableDate
			 		var currDate=comm.getCurrDate();
			 		if(currDate<nextDate){
			 			comm.alert({content:'税率调整申请已经审批通过，请下个月再申请！'});
			 			return false;  
			 		}
		 			$('#busibox').html(_.template(sltzsq_editTpl)) ;
		 			
		 			//初始化数据
		 			comm.initSelect($("#txtTaxpayerType"),comm.lang("accountManage").taxpayerTypeEnum,null,0);
		 			self.initUploadImg();
					
		 			//展示税率
		 			$("#sTaxRate").html((parseFloat(self.currentTaxRate)*100).toFixed(2)+"%");
		 			
		 			// 取消申请
		 			$('#btn_sltz_cancel').click(function(){
		 				$('#zhtz_tz').click();
		 			});
		 			
		 			// 申请税率调整
		 			$('#btn_sltz_post').click(function(){
		 				  self.submit();
		 			});

		 			// 图片放大
		 			$("#fileApplyImg").live("change",function(){
		 				var $img=$("#imgApplyImg img");
			 			comm.initTmpPicPreview($img,$img.attr("src"),"");
		 			});
			 	*/});	
			});
		},
		/** 获取税率 */
		getTaxRate:function(){
			var self=this;
			//获取税率
			csssdjzh.queryTax(function(rsp){
				var taxRate=rsp.data;
				if(taxRate==""){
					taxRate="0.00";
				}
				self.currentTaxRate=parseFloat(taxRate)*100;
				$("#sTaxRate").html((self.currentTaxRate).toFixed(2)+"%");
			});
		},
		//提交修改数据 
		submit : function (){
			var self = this;
			if (!self.validateData()) {
				return false;
			}
			
			var imgParam={"delFileIds":self.originalPic};	//图片参数
			var uploadUrl=comm.domainList['scsWeb']+comm.UrlList["fileupload"];//文件上传地址
			comm.uploadFile(null,["fileApplyImg"],function(imgData){//上传文件
				self.originalPic=imgData.fileApplyImg;//保存上传成功的图片
				
				var applyParam={
						"proveMaterialFile" :imgData.fileApplyImg,
						"applyTaxrate" :parseFloat($("#txtApplyTaxrate").val())*0.01,
						"taxpayerType":$("#txtTaxpayerType").attr("optionValue"),
						"applyReason":$("#txtApplyReason").val(),"createdName":comm.getCookie('userName')+"("+comm.getCookie('operName')+")"
				};
				//提交申请
				csssdjzh.taxAdjustmentApply(applyParam,function(res){
					 $('#zhtz_tz').click();
					 comm.alert({content:'税率调整申请提交成功！'});
				});
			},function(err){},imgParam);
			//初始化上传控件
			self.initUploadImg();
		},
		/** 申请验证 */
		validateData : function() {
			jQuery.validator.addMethod("toFixed2", function(value, element) { 
			  var tel = /^(?!0+(?:\.0+)?$)(?:[1-9]\d*|0)(?:\.\d{1,2})?$/; 
			  return this.optional(element) || (tel.test(value)); 
			}, "请最多保留2位小数"); 
			return comm.valid({
				formID : '#fTaxApply',
				rules : {
					txtApplyTaxrate : {
						required : true,
						number:true,
						toFixed2 :true,
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
						required : comm.lang("accountManage")[33048],
						number:"请正确输入税率值",
						toFixed2:"税率格式不正确，请最多保留2位小数",
						range:"取值范围在0.01~99.99之间"
					},
					txtTaxpayerType : {
						required : comm.lang("accountManage")[33049]
					},
					txtApplyReason : {
						required : comm.lang("accountManage")[33051],
						maxlength : comm.lang("accountManage").maxlen_error
					},
					fileApplyImg : {
						required : comm.lang("accountManage")[33050]
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
		/** 获取业务申请文件下载地址 */
		getRequestFile:function(){
			csssdjzh.getProofMaterialModule(function(res){
				var urlDownload=csssdjzh.getFsServerUrl(res.data);
				window.open(urlDownload,'_blank') 
			});
		},
		/** 初始化上传控件 */
		initUploadImg:function(){
			$("#imgApplyImg").html("<img class=\"ml5\" style=\"vertical-align:middle;\" src=\"resources/img/noImg.gif\" width=\"81\" height=\"52\" title=\"点击查看图片\">");
			comm.initUploadBtn(["#fileApplyImg"],["#imgApplyImg"],81,52);
		},
		/**  获取最后税率调整申请记录 */
		getLastTaxApply:function(callBack){
			csssdjzh.getLastTaxApply(function(rsp){
				callBack(rsp.data);
			});
		}
	}
}); 

 
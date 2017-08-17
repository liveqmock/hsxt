define(['text!toolmanageTpl/ckkcgl/kctj.html',
		'text!toolmanageTpl/ckkcgl/kctj_pk_dialog.html',
		'text!toolmanageTpl/ckkcgl/kctj_rkcj_dialog.html',
		'text!toolmanageTpl/ckkcgl/kctj_ckjqm_dialog.html',
		'toolmanageDat/ckkcgl/kctj',
        'toolmanageLan'
		], function(kctjTpl, kctj_pk_dialogTpl, kctj_rkcj_dialogTpl, kctj_ckjqm_dialogTpl, dataModoule){
	
var kctj_self = {
		showPage : function(){
			kctj_self.initForm();
		},
		/**
		 * 初始化界面
		 */
		initForm : function(){
			$('#busibox').html(_.template(kctjTpl));
			//工具类别
			comm.initSelect('#search_categoryCode', comm.lang("toolmanage").categoryTypes_kctj, null, null).change(function(){
				var code = $(this).attr('optionValue');
				//初始化工具名称
				if(code != ""){
					dataModoule.findToolProductAll({categoryCode:code}, function(res){
						comm.initSelect('#search_productId', res.data, null, null);
					});
				}else{
					comm.initSelect('#search_productId', {}, null, "");
				}
			});
			
			comm.initSelect('#search_productId', {}, null, "");
			
			//初始化仓库
			dataModoule.findAllWarehouseList(null, function(res){
				comm.initSelect('#search_whId', res.data, 185, null);
			});
			
			//绑定提交事件
			$('#queryBtn').click(function(){
				kctj_self.initData();
			});
		},
		/**
		 * 初始化数据
		 */
		initData : function(){
			var params = {};
			params.search_categoryCode = comm.removeNull($("#search_categoryCode").attr('optionValue'));
			params.search_productId = comm.removeNull($("#search_productId").attr('optionValue'));
			params.search_whId = comm.removeNull($("#search_whId").attr('optionValue'));
			dataModoule.findConfigToolStockList(params, this.findPosDeviceSeqNo, this.toolEnterInventory, this.toolEnterCheck);
		},
		/**
		 * 盘库
		 */
		findPosDeviceSeqNo : function(record, rowIndex, colIndex, options){
			if(colIndex == 2){
				return comm.getNameByEnumId(record['categoryCode'], comm.lang("toolmanage").categoryTypes_kctj);
			}
			return $('<a>盘库</a>').click(function(e) {
				kctj_self.panKu(record);
			}.bind(this));
		},
		/**
		 * 入库抽检
		 */
		toolEnterInventory : function(record, rowIndex, colIndex, options){
			if(colIndex == 2){
				return null;
			}
			return $('<a>入库抽检</a>').click(function(e) {
				kctj_self.rkcj(record);
			}.bind(this));
		},
		/**
		 * 查看机器码
		 */
		toolEnterCheck : function(record, rowIndex, colIndex, options){
			if(colIndex == 2){
				return null;
			}
			if(record['categoryCode'] == 'P_POS' || record['categoryCode'] == 'TABLET' ){
				return $('<a>查看机器码</a>').click(function(e) {
					kctj_self.ckjqm(record);
				}.bind(this));
			}
		},
		ckjqm : function(obj){
			obj.categoryName = comm.getNameByEnumId(obj['categoryCode'], comm.lang("toolmanage").categoryTypes_kctj);
			$('#dialogBox_ckjqm').html(_.template(kctj_ckjqm_dialogTpl, obj));
			/*弹出框*/
			$( "#dialogBox_ckjqm" ).dialog({
				title:"查看机器码",
				width:"800",
				modal:true,
				buttons:{ 
					"确定":function(){
						$( this ).dialog( "destroy" );
					}
				}
			});
			$('#searchTable_ckjqm, #searchTable_ckjqm_pt_outTab').css('min-width','751px');
			$('#searchTable_ckjqm_pt').addClass('td_nobody_r_b');
			dataModoule.findPosDeviceSeqNoDetail({batchNo:obj.batchNo}, function(res){
				$("#link_num").val(res.data.info);
				comm.getEasyBsGrid("searchTable_ckjqm", res.data.list, function(record, rowIndex, colIndex, options){
					return (options.curPage-1)*options.settings.pageSize+rowIndex+1;
				});
			});
		},	
		/**
		 * 盘库
		 */
		panKu : function(obj){
			obj.operName = comm.getoperNo();
			obj.categoryName = comm.getNameByEnumId(obj['categoryCode'], comm.lang("toolmanage").categoryTypes_kctj);
			$('#dialogBox_pk').html(_.template(kctj_pk_dialogTpl, obj));
			kctj_self.initVerificationPKMode();
			/*弹出框*/
			$( "#dialogBox_pk" ).dialog({
				title:"盘库",
				width:"800",
				height:"370",
				modal:true,
				buttons:{ 
					"确定":function(){
						if(!kctj_self.validatePKData().form()){
							return;
						}
						//验证双签
						comm.verifyDoublePwd($("#pk_double_username").val(), $("#pk_double_password").val(), 1, function(verifyRes){
							var params = {};
							params.enterNo = obj.batchNo;//入库批次号
							params.shouldQuantity = obj.shouldNum;//应存数量
							params.quantity = $("#quantity").val();//盘库数量
							dataModoule.toolEnterInventory(params, function(res){
								comm.alert({content:comm.lang("toolmanage")[22000], callOk:function(){
									kctj_self.initData();
									$('#dialogBox_pk').dialog('destroy');
								}});
							});
						});
					},
					"取消":function(){
						 $( this ).dialog( "destroy" );
					}
				}
			});
		},
		/**
		 * 初始化验证方式
		 */
		initVerificationPKMode : function(){
			comm.initSelect("#verificationMode_pk", comm.lang("common").verificationMode, 185, '1').change(function(e){
				var val = $(this).attr('optionValue');
				switch(val){
					case '1':
						$('#fhy_user_pk').removeClass('none');
						$('#verificationMode_prompt_pk').addClass('none');
						break;	
					case '2':
						$('#fhy_user_pk').addClass('none');
						$('#verificationMode_prompt_pk').removeClass('none');
						break;
					case '3':
						$('#fhy_user_pk').addClass('none');
						$('#verificationMode_prompt_pk').removeClass('none');
						break;
				}
			});
		},
		/**
		 * 表单校验-盘库
		 */
		validatePKData : function(){
			return $("#pk_form").validate({
				rules : {
					quantity : {
						required : true,
						digits : true,
					},
					pk_double_username : {
						required : true
					},
					pk_double_password : {
						required : true
					}
				},
				messages : {
					quantity : {
						required : comm.lang("toolmanage")[36234],
						digits : comm.lang("toolmanage").validDigitsNumber
					},
					pk_double_username : {
						required : comm.lang("toolmanage")[36200]
					},
					pk_double_password : {
						required : comm.lang("toolmanage")[36201]
					}
				},
				errorPlacement : function (error, element) {
					$(element).attr("title", $(error).text()).tooltip({
						tooltipClass: "ui-tooltip-error",
						destroyFlag : true,
						destroyTime : 3000,
						position : {
							my : "left+2 top+30",
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
		/**
		 * 初始化验证方式
		 */
		initVerificationCJMode : function(){
			comm.initSelect("#verificationMode_cj", comm.lang("common").verificationMode, 185, '1').change(function(e){
				var val = $(this).attr('optionValue');
				switch(val){
					case '1':
						$('#fhy_user_cj').removeClass('none');
						$('#verificationMode_prompt_cj').addClass('none');
						break;	
					case '2':
						$('#fhy_user_cj').addClass('none');
						$('#verificationMode_prompt_cj').removeClass('none');
						break;
					case '3':
						$('#fhy_user_cj').addClass('none');
						$('#verificationMode_prompt_cj').removeClass('none');
						break;
				}
			});
		},
		/**
		 * 入库抽检
		 */
		rkcj : function(obj){
			obj.categoryName = comm.getNameByEnumId(obj['categoryCode'], comm.lang("toolmanage").categoryTypes_kctj);
			$('#dialogBox_rkcj').html(_.template(kctj_rkcj_dialogTpl, obj));
			$("#cj_quantity, #cj_passQuantity").keyup(function(){
				var cj_quantity = $("#cj_quantity").val();
				var cj_passQuantity = $("#cj_passQuantity").val();
				$("#passRate").html(kctj_self.getPercentage(cj_passQuantity, cj_quantity));
			});
			kctj_self.initVerificationCJMode();
			$( "#dialogBox_rkcj" ).dialog({
				title:"入库抽检",
				width:"800",
				height:"470",
				modal:true,
				buttons:{ 
					"确定":function(){
						if(!kctj_self.validateCJData(obj.enterNum).form()){
							return;
						}
						var cj_quantity = $("#cj_quantity").val();
						var cj_passQuantity = $("#cj_passQuantity").val();
						if(parseInt(cj_passQuantity) > parseInt(cj_quantity)){
							error_alert(comm.lang("toolmanage").passRateError);
							return;
						}
						//验证双签
						comm.verifyDoublePwd($("#cj_double_username").val(), $("#cj_double_password").val(), 1, function(verifyRes){
							var params = {};
							params.enterNo = obj.batchNo;//入库批次号
							params.Quantity = $("#cj_quantity").val();//抽检数量
							params.passQuantity = $("#cj_passQuantity").val();//合格数量
							params.passRate = $("#passRate").html();//合格率
							params.remark = $("#remark").val();//备注
							dataModoule.toolEnterCheck(params, function(res){
								comm.alert({content:comm.lang("toolmanage")[22000], callOk:function(){
									kctj_self.initData();
									$('#dialogBox_rkcj').dialog('destroy');
								}});
							});
						});
					},
					"取消":function(){
						 $( this ).dialog( "destroy" );
					}
				}
			});
		},
		/**
		 * 表单校验-入库抽查
		 */
		validateCJData : function(maxNum){
			return $("#cj_form").validate({
				rules : {
					cj_quantity : {
						required : true, 
						digits : true, 
						max : maxNum, 
						min : 1
					},
					cj_passQuantity : {
						required : true, 
						digits : true, 
						max : maxNum, 
						min : 1
					},
					cj_double_username : {
						required : true
					},
					cj_double_password : {
						required : true
					},
					remark:{
						maxlength : 300
					}
				},
				messages : {
					cj_quantity : {
						required : comm.lang("toolmanage")[36235],
						digits : comm.lang("toolmanage").validDigitsNumber,
						max: comm.lang("toolmanage").cjQuantityMax,
						min: comm.lang("toolmanage").cjQuantityMin,
					},
					cj_passQuantity : {
						required : comm.lang("toolmanage")[36236],
						digits : comm.lang("toolmanage").validDigitsNumber,
						max: comm.lang("toolmanage").cjPassQuantityMax,
						min: comm.lang("toolmanage").cjPassQuantityMin,
					},
					cj_double_username : {
						required : comm.lang("toolmanage")[36200]
					},
					cj_double_password : {
						required : comm.lang("toolmanage")[36201]
					},
					remark:{
						maxlength : comm.lang("toolmanage")[36223]
					}
				},
				errorPlacement : function (error, element) {
					$(element).attr("title", $(error).text()).tooltip({
						tooltipClass: "ui-tooltip-error",
						destroyFlag : true,
						destroyTime : 3000,
						position : {
							my : "left+2 top+30",
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
		/**
		 * 计算百分比
		 * @param number1 被除数
		 * @param number2 除数
		 */
		getPercentage : function (number1, number2) {
			if(number1 == null || number2 == null || number1 == "" || number2 == ""){
				return "";
			}
			try{
				number1 = parseInt(number1);
			}catch(e){}
			try{
				number2 = parseInt(number2);
			}catch(e){}
			var reg = /^[1-9]*[1-9][0-9]*$/;
			if(!reg.test(number1) || !reg.test(number2)){
				return "";
			}
			if(number2 == 0){
				return "";
			}
			if(number1 > number2){
				return "";
			}
			var rate = number1/number2;
		    return (Math.round(rate*10000)/100.00 + "%");// 小数点后两位百分比
		}
		
	};
	return kctj_self;
});

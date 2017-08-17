define(['text!toolmanageTpl/gjpzxx/gysxx.html',
		'text!toolmanageTpl/gjpzxx/gysxx_tjgys_dialog.html',
		'text!toolmanageTpl/gjpzxx/gysxx_gygj_dialog.html',
		'text!toolmanageTpl/gjpzxx/gysxx_ck_dialog.html',
		'text!toolmanageTpl/gjpzxx/gysxx_xg_dialog.html',
        'toolmanageDat/gjpzxx/gysxx',
		'toolmanageLan'
		], function(gysxxTpl, gysxx_tjgys_dialogTpl, gysxx_gygj_dialogTpl, gysxx_ck_dialogTpl, gysxx_xg_dialogTpl, dataModoule){
	return {
		gysxx_self : null,
		showPage : function(){
			gysxx_self = this;
			this.initForm();
		},
		/**
		 * 初始化界面
		 */
		initForm : function(){
			$('#busibox').html(_.template(gysxxTpl));
			/*绑定查询按钮*/
			$('#queryBtn').click(function(){
				gysxx_self.initData();
			});
			/*绑定添加按钮*/
			$('#tjgys_btn').click(function(){
				gysxx_self.addSupplier();
			});
		},
		/**
		 * 初始化数据
		 */
		initData : function(){
			var params = {};
			params.search_supplierName = $("#search_supplierName").val().trim();
			params.search_corpName = $("#search_corpName").val().trim();
			params.search_linkMan = $("#search_linkMan").val().trim();
			dataModoule.findSupplierList(params, this.detail, this.update);
		},
		/**
		 *修改动作
		 */
		update : function(record, rowIndex, colIndex, options){
			return $('<a>修改</a>').click(function(e) {
				gysxx_self.xiuGai(record.supplierId);
			}.bind(this));
		},
		/**
		 *查看动作
		 */
		detail : function(record, rowIndex, colIndex, options){
			return $('<a>查看</a>').click(function(e) {
				gysxx_self.chaKan(record.supplierId);
			}.bind(this));
		},
		/**
		 * 修改
		 * @param supplierId 供应商编号
		 */
		xiuGai : function(supplierId){
			dataModoule.findSupplierById({supplierId:supplierId}, function(res){
				var obj = res.data;
				$('#dialogBox_xg').html(_.template(gysxx_xg_dialogTpl, obj));
				if(obj.isActive == 'Y'){
					$("#isActive_2").attr("checked", true);
				}else if(obj.isActive == 'N'){
					$("#isActive_1").attr("checked", true);
				}
				$( "#dialogBox_xg" ).dialog({
					title:"修改供应商",
					width:"900",
					modal:true,
					closeIcon : true,
					buttons:{ 
						"确定":function(){
							if(!gysxx_self.validateData("#gysxx_xg_form").form()){
								return;
							}
							var params = {};
							params.supplierId = obj.supplierId;
							params.supplierName = $("#supplierName").val();
							params.corpName = $("#corpName").val();
							params.linkMan = $("#linkMan").val();
							params.addr = $("#addr").val();
							params.phone = $("#phone").val();
							params.mobile = $("#mobile").val();
							params.fax = $("#fax").val();
							params.email = $("#email").val();
							params.website = $("#website").val();
							params.remark = $("#remark").val();
							params.isActive = comm.removeNull($('input[name="active"]:checked').val());//是否停用
							dataModoule.saveSupplier(params, function(res){
								comm.alert({content:comm.lang("toolmanage")[22000], callOk:function(){
									$('#dialogBox_xg').dialog("destroy");
									$('#dialogBox_xg').html("");
									$('#queryBtn').click();
								}});
							});
						},
						"取消":function(){
							 $( this ).dialog("destroy");
							 $('#dialogBox_xg').html("");
						}
					}
				});
			});
		},
		/**
		 * 查看
		 * @param supplierId 供应商编号
		 */
		chaKan : function(supplierId){
			dataModoule.findSupplierById({supplierId:supplierId}, function(res){
				var obj = res.data;
				obj.isActiveText = comm.getNameByEnumId(obj['isActive'], comm.lang("toolmanage").isStopEnum);
				$('#dialogBox_ck').html(_.template(gysxx_ck_dialogTpl, obj));
				$( "#dialogBox_ck" ).dialog({
					title:"供应商详情",
					width:"900",
					modal:true,
					closeIcon : true,
					buttons:{ 
						"关闭":function(){
							$(this).dialog( "destroy" );
						}
					}
				});
			});
		},
		/**
		 * 添加供应商
		 */
		addSupplier : function(){
			$('#dialogBox_tjgys').html(_.template(gysxx_tjgys_dialogTpl));
			$( "#dialogBox_tjgys" ).dialog({
				title:"添加供应商",
				width:"900",
				closeIcon : true,
				modal:true,
				buttons:{ 
					"确定":function(){
						if(!gysxx_self.validateData("#gysxx_tj_form").form()){
							return;
						}
						var params = {};
						params.supplierName = $("#supplierName").val();
						params.corpName = $("#corpName").val();
						params.linkMan = $("#linkMan").val();
						params.addr = $("#addr").val();
						params.phone = $("#phone").val();
						params.mobile = $("#mobile").val();
						params.fax = $("#fax").val();
						params.email = $("#email").val();
						params.website = $("#website").val();
						params.remark = $("#remark").val();
						params.isActive = comm.removeNull($('input[name="active"]:checked').val());//是否停用
						dataModoule.saveSupplier(params, function(res){
							comm.alert({content:comm.lang("toolmanage")[22000], callOk:function(){
								$('#dialogBox_tjgys').dialog("destroy");
								$('#dialogBox_tjgys').html("");
								$('#queryBtn').click();
							}});
						});
					},
					"取消":function(){
						 $(this).dialog("destroy");
						 $('#dialogBox_tjgys').html("");
					}
				}
			});
		},
		/**
		 * 表单校验
		 * @param formName 表单名称
		 */
		validateData : function(formName){
			 return $(formName).validate({
				rules : {
					supplierName : {
						required : true,
						rangelength:[1, 64]
					},
					corpName : {
						required : true,
						rangelength:[1, 256]
					},
					linkMan : {
						required : true,
						rangelength:[1, 64]
					},
					addr : {
						required : true,
						rangelength:[1, 255]
					},
					phone : {
						telphone:true,
						maxlength:20
					},
					mobile : {
						required : true,
						mobileNo : true
					},
					fax : {
						fax : true,
						maxlength:20
					},
					email : {
						email2 : true,
						maxlength:20
					},
					website : {
						isUrl : true,
						maxlength:32
					},
					remark : {
						maxlength:300
					}
				},
				messages : {
					supplierName : {
						required : comm.lang("toolmanage")[36240],
						rangelength:comm.lang("toolmanage")[36245]
					},
					corpName : {
						required : comm.lang("toolmanage")[36241],
						rangelength:comm.lang("toolmanage")[36246]
					},
					linkMan : {
						required : comm.lang("toolmanage")[36242],
						rangelength:comm.lang("toolmanage")[36247]
					},
					addr : {
						required : comm.lang("toolmanage")[36243],
						rangelength:comm.lang("toolmanage")[36248]
					},
					phone : {
						telphone:comm.lang("toolmanage").phoneFormError,
						maxlength:comm.lang("toolmanage")[36249]
					},
					mobile : {
						required : comm.lang("toolmanage")[36244],
						mobileNo : comm.lang("toolmanage").mobileFormError
					},
					fax : {
						fax : comm.lang("toolmanage").faxFormError,
						maxlength:comm.lang("toolmanage")[36251]
					},
					email : {
						email2 : comm.lang("toolmanage").emailFormError,
						maxlength:comm.lang("toolmanage")[36252]
					},
					website : {
						isUrl : comm.lang("toolmanage").websiteFormError,
						maxlength:comm.lang("toolmanage")[36253]
					},
					remark : {
						maxlength:comm.lang("toolmanage")[36254]
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
		}
	}	
});
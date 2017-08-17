define(['text!toolmanageTpl/wlpsxx/psfsxx.html',
		'text!toolmanageTpl/wlpsxx/psfsxx_tj_dialog.html',
		'text!toolmanageTpl/wlpsxx/psfsxx_ck_dialog.html',
		'text!toolmanageTpl/wlpsxx/psfsxx_xg_dialog.html',
        'toolmanageDat/wlpsxx/wlpsxx',
		'toolmanageLan'
		], function(psfsxxTpl, psfsxx_tj_dialogTpl, psfsxx_ck_dialogTpl, psfsxx_xg_dialogTpl, dataModoule){

	var self = {
		showPage : function(){
			this.initForm();
		},
		/**
		 * 初始化页面
		 */
		initForm : function(){
			$('#busibox').html(psfsxxTpl);
			/*按钮事件*/
			$('#queryBtn').click(function(){
				self.initData();
			});
			/*按钮事件*/
			$('#tjpsfs_btn').click(function(){
				self.showAddShipping();
			});
			self.initData();
		},
		/**
		 * 查询列表
		 */
		initData : function(){
			var params = {};
			params.search_smName = $("#search_smName").val().trim();
			dataModoule.findLogisticsList(params, this.detail, this.update, this.delFun);
		},
		/**
		 * 查看动作
		 */
		detail : function(record, rowIndex, colIndex, options){
			if(colIndex == 2){
				return '';
			}
			return $('<a data-sn="'+ record['isActive'] +'" >查看</a>').click(function(e) {
				$('#dialogBox_ck').html(_.template(psfsxx_ck_dialogTpl, record));
				$("#isActiveText").html(comm.getNameByEnumId(record['isActive'], comm.lang("toolmanage").isActiveEnum));
				$("#dialogBox_ck").dialog({
					title:"配送方式详情",
					width:"500",
					modal:true,
					buttons:{
						"关闭":function(){
							$( this ).dialog( "destroy" );
						}
					}
				});
			});
		},
		/**
		 *修改动作
		 */
		update : function(record, rowIndex, colIndex, options){
			if(colIndex == 2){
				return '';
			}
			return $('<a data-sn="'+ record['isActive'] +'" >修改</a>').click(function(e) {
				$('#dialogBox_xg').html(_.template(psfsxx_xg_dialogTpl, record));
				if(record['isActive'] == "Y"){
					$("input[type=radio][name=isActive][value=Y]").attr("checked",'checked')
				}else if(record['isActive'] == "N"){
					$("input[type=radio][name=isActive][value=N]").attr("checked",'checked')
				}

				$( "#dialogBox_xg" ).dialog({
					title:"修改配送方式",
					width:"600",
					modal:true,
					buttons:{
						"确定":function(){
							if(!self.validateData("#psfsxxxg_form").form()){
								return;
							}
							var params = $("#psfsxxxg_form"). serializeJson();
							dataModoule.modifyShipping(params, function(res){
								comm.alert({content:comm.lang("toolmanage")[22000], callOk:function(){
									$('#dialogBox_xg').dialog("destroy");
									$('#queryBtn').click();
								}});
							});
						},
						"取消":function(){
							$( this ).dialog( "destroy" );
						}
					}
				});
			});
		},
		/**
		 * 删除动作
		 */
		delFun : function(record, rowIndex, colIndex, options){
			if(colIndex == 2){
				return comm.getNameByEnumId(record['isActive'], comm.lang("toolmanage").isActiveEnum);
			}
			return $('<a data-sn="'+ record['isActive'] +'" >删除</a>').click(function(e) {
				comm.i_confirm(comm.lang("toolmanage").confirmDel, function(){
					dataModoule.removeShipping({smId:record['smId']}, function(res){
						comm.alert({content:comm.lang("toolmanage").deleteOk, callOk:function(){
							$('#queryBtn').click();
						}});
					});
				}, 400);
			});
		},
		/**
		 *显示添加
		 */
		showAddShipping : function(){
			$('#dialogBox_tj').html(_.template(psfsxx_tj_dialogTpl));
			$( "#dialogBox_tj" ).dialog({
				title:"添加配送方式",
				width:"600",
				modal:true,
				buttons:{
					"确定":function(){
						if(!self.validateData("#psfsxx_form").form()){
							return;
						}
						var params = $("#psfsxx_form"). serializeJson();
						dataModoule.addShipping(params, function(res){
							comm.alert({content:comm.lang("toolmanage")[22000], callOk:function(){
								$('#dialogBox_tj').dialog("destroy");
								self.initData();
							}});
						});
					},
					"取消":function(){
						$('#psfsxx_form').find('input,textarea,select').tooltip().tooltip( "destroy" );
						$( this ).dialog( "destroy" );
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
					smName : {
						required : true,
						rangelength:[2, 64],
					},
					sort : {
						required : true,
						digits:true,
					},
					smDesc : {
						maxlength : 200,
					}
				},
				messages : {
					smName : {
						required : comm.lang("toolmanage")[36211],
						rangelength : comm.lang("toolmanage")[36214],
					},
					sort : {
						required : comm.lang("toolmanage")[36212],
						digits:comm.lang("toolmanage").validNumber
					},
					smDesc : {
						maxlength : comm.lang("toolmanage")[36215],
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
	};
	return self;
});
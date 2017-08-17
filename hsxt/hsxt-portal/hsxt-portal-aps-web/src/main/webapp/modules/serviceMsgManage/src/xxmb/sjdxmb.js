define(['text!serviceMsgManageTpl/xxmb/sjdxmb/sjdxmb.html',
		'text!serviceMsgManageTpl/xxmb/sjdxmb/sjdxmb_xz.html',
		'text!serviceMsgManageTpl/xxmb/sjdxmb/sjdxmb_xg.html',
		'text!serviceMsgManageTpl/xxmb/sjdxmb/sjdxmb_ck.html',
        'serviceMsgManageDat/serviceMsgManage',
        'serviceMsgManageLan'], function(sjdxmbTpl, sjdxmb_xzTpl, sjdxmb_xgTpl, sjdxmb_ckTpl, dataModoule){
	return {
		sjdxmb_self : null,
		showPage : function(){
			sjdxmb_self = this;
			sjdxmb_self.initForm();
		},
		/**
		 * 初始化界面
		 */
		initForm : function(){
			$('#busibox').html(_.template(sjdxmbTpl));
			/** 查询事件 */
			$("#queryBtn").click(function(){
				sjdxmb_self.initData();
			});
			$('#xzmb_btn').click(function(){
				sjdxmb_self.xinZhen();
			});
			comm.initSelect('#search_useCustType', comm.lang("serviceMsgManage").custTypes, null, null, {name:'全部', value:''});
			comm.initSelect('#search_tplStatus', comm.lang("serviceMsgManage").tplStatus, null, null, {name:'全部', value:''});
			sjdxmb_self.initData();
		},
		/**
		 * 初始化数据
		 */
		initData : function(){
			var params = {};
			params.search_tplType = 1;
			params.search_tplName = $("#search_tplName").val();
			params.search_useCustType = $("#search_useCustType").attr('optionValue');
			params.search_tplStatus = $("#search_tplStatus").attr('optionValue');
			dataModoule.findMessageTplList(params, this.detail_1, this.detail_2, this.detail_3);
		},
		/**
		 * 查看
		 */
		detail_1 : function(record, rowIndex, colIndex, options){
			if(colIndex == 1){
				return comm.getNameByEnumId(record['custType'], comm.lang("serviceMsgManage").custTypes);
			}
			if(colIndex == 2){
				return comm.getNameByEnumId(record['buyResType'], comm.lang("serviceMsgManage").toBuyResRangeEnum);
			}
			if(colIndex == 3){
				return comm.getNameByEnumId(record['bizType'], comm.lang("serviceMsgManage").busTypes);
			}
			if(colIndex == 4){
				return comm.getNameByEnumId(record['status'], comm.lang("serviceMsgManage").tplAllStatus);
			}
			return $('<a id="'+ record['tempId'] +'" >查看</a>').click(function(e) {
				sjdxmb_self.chaKan(record);
			}.bind(this));
		},
		/**
		 * 修改
		 */
		detail_2 : function(record, rowIndex, colIndex, options){
			if(colIndex < 5){
				return null;
			}
			if(record.status == 3){//待启用
				return $('<a id="'+ record['tempId'] +'" >修改</a>').click(function(e) {
					sjdxmb_self.xiuGai(record);
				}.bind(this));
			}
		},
		/**
		 * 停用/启用
		 */
		detail_3 : function(record, rowIndex, colIndex, options){
			if(colIndex < 5){
				return null;
			}
			if(record.status == 2){//已启用
				return $('<a id="'+ record['tempId'] +'" >停用</a>').click(function(e) {
					comm.confirm({
						imgFlag:true,
						title: "温馨提示",
						content : "确认停用此模板？",
						callOk:function(){
							var params = {};
							params.optType = 0;
							params.tempId = record.tempId;
							dataModoule.startOrStopTpl(params, function(res){
								comm.alert({content:comm.lang("serviceMsgManage")[22000], callOk:function(){
									sjdxmb_self.initData();
								}});
							});
						},
						callCancel:function(){
						}
					});
				}.bind(this));
			}
			if(record.status == 3){//待启用
				return $('<a id="'+ record['tempId'] +'" >启用</a>').click(function(e) {
					comm.confirm({
						imgFlag:true,
						title: "温馨提示",
						content : "确认启用此模板？",
						callOk:function(){
							var params = {};
							params.optType = 1;
							params.tempId = record.tempId;
							dataModoule.startOrStopTpl(params, function(res){
								comm.alert({content:comm.lang("serviceMsgManage")[22000], callOk:function(){
									sjdxmb_self.initData();
								}});
							});
						},
						callCancel:function(){
						}
					});
				}.bind(this));
			}
		},
		/**
		 * 初始化change事件
		 */
		initChange : function(custType, buyResType){
			if(custType == "3"){//托管启用
				comm.initSelect('#buyResType', comm.lang("serviceMsgManage").tgToBuyResRangeEnum, 185, buyResType, {name:'', value:''}).change(function(){
					sjdxmb_self.initBusTypeSelect("");
				});
			}else if(custType == "2"){//成员启用
				comm.initSelect('#buyResType', comm.lang("serviceMsgManage").cyToBuyResRangeEnum, 185, buyResType, {name:'', value:''}).change(function(){
					sjdxmb_self.initBusTypeSelect("");
				});
			}else{
				comm.initSelect('#buyResType', {name:'', value:''}, 185, "", null);
			}
			sjdxmb_self.initBusTypeSelect("");
		},
		/**
		 * 修改
		 */
		xiuGai : function(record){
			$('#xzmbTpl').html(sjdxmb_xzTpl);
			sjdxmb_self.showTpl($('#xzmbTpl'));
			comm.liActive_add($('#xgmb'));
			
			comm.initSelect('#custType', comm.lang("serviceMsgManage").custTypes, 185, record.custType, null).change(function(){
				sjdxmb_self.initChange($(this).attr('optionValue'), "");
				sjdxmb_self.initBusTypeSelect("");
			});
			sjdxmb_self.initChange(record.custType, comm.removeNull(record.buyResType));
			sjdxmb_self.initBusTypeSelect(record.bizType);
			
			$("#tempName").val(record.tempName);
			$("#tempContent").val(record.tempContent);
			
			/*富文本框*/
			$('#tempContent').xheditor({tools:'Cut,Copy,Paste,Pastetext,|,Blocktag,Bold,Italic,Underline,Strikethrough,FontColor,|,Align,List,Outdent,Indent,|,Source,Preview',width:678,height:150});
			$(".xheBtnAbout").parent().hide();
			
			$('#xzmb_save_btn').click(function(){
				sjdxmb_self.saveData("#save_form", record.tempId);
			});
			
			$('#xzmb_back_btn').click(function(){
				sjdxmb_self.showTpl($('#sjdxmbTpl'));
				comm.liActive($('#sjdxmb'), '#xzmb, #xgmb');
			});
		},
		/**
		 * 查看
		 */
		chaKan : function(record){
			$('#ck_dialog').html(sjdxmb_ckTpl);
		
			/*弹出框*/
			$( "#ck_dialog" ).dialog({
				title:"查看模版",
				width:"800",
				modal:true,
				closeIcon : true,
				buttons:{ 
					"关闭":function(){
						$( this ).dialog( "destroy" );
					}
				}
			});
			$('#view_tempName').html(record.tempName);
			$('#view_bizType').html(comm.getNameByEnumId(record.bizType, comm.lang("serviceMsgManage").busTypes));
			$('#view_custType').html(comm.getNameByEnumId(record.custType, comm.lang("serviceMsgManage").custTypes));
			$('#view_buyResType').html(comm.getNameByEnumId(record.buyResType, comm.lang("serviceMsgManage").toBuyResRangeEnum));
			$('#view_tempContent').html(record.tempContent);
			dataModoule.findMsgTplApprHisList({search_tempId:record.tempId}, function(record_, rowIndex_, colIndex_, options_){
				if(colIndex_ == 1){
					return comm.getNameByEnumId(record_['reqStatus'], comm.lang("serviceMsgManage").reqStatus);
				}
				if(colIndex_ == 4){
					return comm.getNameByEnumId(record_['reviewResult'], comm.lang("serviceMsgManage").reviewResults);
				}
				return $('<a id="'+ record_.applyId +'" >查看</a>').click(function(e) {
					comm.alert({title:"备注",content : record_.apprRemark});
				}.bind(this));
			});
		},
		/**
		 * 新增
		 */
		xinZhen : function(){
			var that = this;
			$('#xzmbTpl').html(sjdxmb_xzTpl);
			that.showTpl($('#xzmbTpl'));
			comm.liActive_add($('#xzmb'));
			comm.initSelect('#custType', comm.lang("serviceMsgManage").custTypes, 185, null, null).change(function(){
				if($(this).attr('optionValue') == "3"){//托管启用
					comm.initSelect('#buyResType', comm.lang("serviceMsgManage").tgToBuyResRangeEnum, 185, "", {name:'', value:''}).change(function(){
						sjdxmb_self.initBusTypeSelect("");
					});
				}else if($(this).attr('optionValue') == "2"){//成员启用
					comm.initSelect('#buyResType', comm.lang("serviceMsgManage").cyToBuyResRangeEnum, 185, "", {name:'', value:''}).change(function(){
						sjdxmb_self.initBusTypeSelect("");
					});
				}else{
					comm.initSelect('#buyResType', null, 185, "", null);
				}
				sjdxmb_self.initBusTypeSelect("");
			});
			comm.initSelect('#buyResType', null, 185, "", null);
			comm.initSelect('#bizType', {}, 185, "", null);
			/*富文本框*/
			$('#tempContent').xheditor({tools:'Cut,Copy,Paste,Pastetext,|,Blocktag,Bold,Italic,Underline,Strikethrough,FontColor,|,Align,List,Outdent,Indent,|,Source,Preview',width:678,height:150});
			$(".xheBtnAbout").parent().hide();

			$('#xzmb_save_btn').click(function(){
				that.saveData("#save_form", "");
			});
			
			$('#xzmb_back_btn').click(function(){
				that.showTpl($('#sjdxmbTpl'));
				comm.liActive($('#sjdxmb'), '#xzmb, #xgmb');	
			});
		},
		showTpl : function(tplObj){
			$('#sjdxmbTpl, #xzmbTpl, #xgmbTpl, #ck_dialog').addClass('none');
			tplObj.removeClass('none');	
		},
		/**
		 * 数据校验
		 */
		validateData : function(formId){
			var validate = $(formId).validate({
				rules : {
					tempName : {
						required : true,
						rangelength:[1, 60]
					},
					custType : {
						required : true
					},
					bizType : {
						required : true
					},
					tempContent : {
						required : false
					},
					buyResType : {
						required : true
					}
				},
				messages : {
					tempName : {
						required : comm.lang("serviceMsgManage")[36601],
						rangelength : comm.lang("serviceMsgManage")[36607]
					},
					custType : {
						required : comm.lang("serviceMsgManage")[36604]
					},
					bizType : {
						required : comm.lang("serviceMsgManage")[36603]
					},
					tempContent : {
						required : comm.lang("serviceMsgManage")[36605]
					},
					buyResType : {
						required : comm.lang("serviceMsgManage").buyResTypeCantNotBeNull
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
			var custType = $.trim($("#custType").attr('optionValue'));
			if('2'== custType || '3'==custType){
				validate.settings.rules.buyResType = {required : true};
				validate.settings.messages.buyResType = {required : comm.lang("serviceMsgManage").buyResTypeCantNotBeNull};
			}else{
				validate.settings.rules.buyResType = {required : false};
				validate.settings.messages.buyResType = {required : comm.lang("serviceMsgManage").buyResTypeCantNotBeNull};
			}
			return validate;
		},
		/**
		 * 保存数据
		 */
		saveData : function(formId, tempId){
			if(!sjdxmb_self.validateData(formId).form()){
				return;
			}
			var params = {};
			params.tempId = tempId;
			params.tempType = 1;
			params.tempFmt = "txt";
			params.tempName = $("#tempName").val();
			params.bizType = comm.removeNull($("#bizType").attr('optionValue'));
			params.custType = comm.removeNull($("#custType").attr('optionValue'));
			params.buyResType = comm.removeNull($("#buyResType").attr('optionValue'));
			params.tempContent = $("#tempContent").val();
			dataModoule.saveMessageTpl(params, function(res){
				comm.alert({content:comm.lang("serviceMsgManage")[22000], callOk:function(){
					sjdxmb_self.showTpl($('#sjdxmbTpl'));
					comm.liActive($('#sjdxmb'), '#xzmb, #xgmb');
					sjdxmb_self.initData();
				}});
			});
		},
		/**
		 * 加载业务类型
		 */
		initBusTypeSelect : function(val){
			var custType = comm.removeNull($("#custType").attr('optionValue'));
			var buyResType = comm.removeNull($("#buyResType").attr('optionValue'));
			var sjTypes = comm.lang("serviceMsgManage").sjBusTypes[custType+"_"+buyResType];
			var sjCustTypes = {};
			if(sjTypes){
				for(var i=0; i<sjTypes.length; i++){
					sjCustTypes[sjTypes[i]] = comm.lang("serviceMsgManage").busTypes[sjTypes[i]];
				}
			}
			comm.initSelect('#bizType', sjCustTypes, 185, val, {name:'', value:''});
		}
	}	
});
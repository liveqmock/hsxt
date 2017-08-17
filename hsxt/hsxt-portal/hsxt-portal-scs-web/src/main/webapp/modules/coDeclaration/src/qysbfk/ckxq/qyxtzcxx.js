define(['text!coDeclarationTpl/qysbfk/ckxq/qyxtzcxx.html',
			'text!coDeclarationTpl/qysbfk/ckxq/qyxtzcxx_dialog.html',
	        'coDeclarationDat/qysbfk/ckxq/qyxtzcxx',
			'coDeclarationLan'
		],function(qyxtzcxxTpl ,qyxtzcxx_dialogTpl, dataModoule){
	var cacheData = null;
	return {	 	
		showPage : function(){
			var self = this;
			self.initData();
		 	$('#ckxq_xg').css('display','');
		 	//取消
		 	$('#ckxq_qx').click(function(){		 		
		 		if($('#ckxq_xg').text() == '保　存' ){		 			 			
		 			$('#ckxq_xg').text('修　改');
		 			$("#skqr_tj").show();
		 			//$('#qyxtzcxx_1').removeClass('none');
		 			//$('#qyxtzcxx_2').addClass('none');
		 			self.initShowForm(cacheData, false);
		 		} else {
		 			$('#cx_content_list').removeClass('none');
		 			$('#cx_content_detail').addClass('none');
		 		}		 		
		 	});
		 	
		 	//修改
		 	$('#ckxq_xg').click(function(){
		 		if ($(this).text() !='保　存'){
		 			$('#qyxtzcxx_1').addClass('none');
		 			$('#qyxtzcxx_2').removeClass('none');
		 			$(this).text('保　存');
		 			$("#skqr_tj").hide();
		 		} else {
		 			self.initChangeData();
		 		}
		 	});
		},
		/**
		 * 初始化数据
		 */
		initData: function(){
			var self = this;
			dataModoule.findDeclareByApplyId({"applyId": $("#editApplyId").val()}, function(res){
				cacheData = null;
				cacheData = res.data;
				self.initShowForm(res.data, false);
			});
		},
		/**
		 * 初始化显示页面
		 * @param data 注册信息
		 * @param isCallBack 是否是回调成功
		 */
		initShowForm : function(data, isCallBack){
			comm.delCache('coDeclaration', 'qysbfk-qyxtzcxx-info');//保存前数据
			comm.setCache('coDeclaration', 'qysbfk-qyxtzcxx-info', data);
			if(data){
				$('#ckxq_view').html('').append(_.template(qyxtzcxxTpl, data));
				//平台信息
				cacheUtil.findCacheSystemInfo(function(sysRes){
					$('#currencyText-1').html(sysRes.currencyNameCn);
					$('#currencyText-2').val(sysRes.currencyNameCn);
					//获取地区信息
					cacheUtil.syncGetRegionByCode(null, data.provinceNo, data.cityNo, "", function(resdata){
						$("#placeName-1").html(resdata);
						$("#placeName-2").val(resdata);
					});
				});
			}
			if(isCallBack){
				$('#ckxq_xg').text('修改');
				$("#skqr_tj").show();
				//$('#ckxq_xg').click();
			}
		},
		/**
		 * 表单校验
		 */
		validateData : function(){
			 var validate = $("#qyxtzcxx_fwgs_form").validate({
				rules : {
					toEntCustName : {
						required : true,
						rangelength : [2, 64]
					},
					toEntEnName : {
						//required : true,
						rangelength : [2, 128]
					}
				},
				messages : {
					toEntCustName : {
						required : comm.lang("coDeclaration")[32685],
						rangelength : comm.lang("coDeclaration")[32686]
					},
					toEntEnName : {
						//required : comm.lang("coDeclaration")[32702],
						rangelength : comm.lang("coDeclaration")[32690]
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
			return validate;
		},
		/**
		 * 表单校验
		 */
		validateViewMarkData : function(){
			return $("#qyxtzcxx_dialog_form").validate({
				rules : {
					viewMark : {
						rangelength : [0, 300]
					}
				},
				messages : {
					viewMark : {
						rangelength : comm.lang("coDeclaration")[32599]
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
		 * 初始化提交页面
		 */
		initChangeData : function(){
			var self = this;
			if(!self.validateData().form()){
				return;
			}
			var ckArray = [];//存入需要检查是否改变的对象
			$(".isChange").each(function(){
				var desc = $(this).parents()[0].previousElementSibling.innerText;
				ckArray.push({name:this.name, value:this.value, desc:desc});
			});
			var odata = comm.getCache('coDeclaration', 'qysbfk-qyxtzcxx-info');
			var ndata = comm.cloneJSON(odata);
			var trs = "";
			var chg = {};
			for(var key in ckArray){
				if(ckArray[key].name&&comm.removeNull(odata[ckArray[key].name]) != ckArray[key].value){
					trs+="<tr><td class=\"view_item\">"+ckArray[key].desc+"</td><td class=\"view_text\">"+comm.removeNull(odata[ckArray[key].name])+"</td><td class=\"view_text\">"+ckArray[key].value+"</td></tr>";
					chg[ckArray[key].name] = {"old":comm.removeNull(odata[ckArray[key].name]), "new":ckArray[key].value};
					ndata[ckArray[key].name] = ckArray[key].value;
				}
			}
 			if(trs == ""){
 				comm.warn_alert(comm.lang("coDeclaration").noUpdate);
 				return;
 			}
 			//提交
 			$('#qyxtzcxx_dialog > p').html(_.template(qyxtzcxx_dialogTpl));
 			$('#copTable tr:eq(1)').before(trs);
 			$('#qyxtzcxx_dialog').dialog({
 				width:800,
 				buttons:{
 					'确定':function(){
 						if(!self.validateViewMarkData().form()){
 							return;
 						}
 						ndata.optRemark = $("#viewMark").val();
 						ndata.changeContent = JSON.stringify(chg);
 						dataModoule.saveDeclare(ndata, function(res){
 							comm.alert({content:comm.lang("coDeclaration")[22000], callOk:function(){
 								$('#qyxtzcxx_dialog').dialog('destroy');
 								self.initShowForm(ndata, true);
 							}});
 						});
 					},
 					'取消':function(){
 						$(this).dialog('destroy');
 					}
 				}
 			});	
		}
	}
}); 

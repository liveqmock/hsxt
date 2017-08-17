define(['text!coDeclarationTpl/qyxz/qyxtzcxx_tgqy.html',
        'coDeclarationSrc/qyxz/point_choose',
        'coDeclarationDat/qyxz/qyxtzcxx_tgqy',
        'coDeclarationLan'], function(qyxtzcxx_tgqyTpl, pointChoose, dataModoule){

	/*
	 *推广互生号
	 */
	jQuery.validator.addMethod("spreadEntResNoCheck", function(value, element, param) {
		var serRegex = /^(([1-9]\d){1}|(\d[1-9]){1})([1-9]\d{2}|\d{2}[1-9]|\d[1-9]\d)([0]{6})$/;
		return this.optional(element) || serRegex.test(value);
	}, comm.langConfig['coDeclaration'].rightServiceNo);

	var tgqysb = {
		showPage: function () {
			this.initData();
		},
		/**
		 * 获取申请编号
		 */
		getApplyId: function () {
			return comm.getCache("coDeclaration", "entDeclare").applyId;
		},
		/**
		 * 初始化表单
		 */
		initForm: function (data) {
			$('#busibox').html(_.template(qyxtzcxx_tgqyTpl, data));

			//禁用积分增值设置
			$("#comboxDiv").find("input:radio").attr("disabled", true);

			comm.initProvSelect('#province_slt', {}, 70, null);
			comm.initCitySelect('#city_slt', {}, 70, null);
			// comm.initSelect1("#spreadEntResNo", {}, "", true);
			$('#refincrement').attr('disabled', true);

			/**启用资源类型*/
			comm.initSelect('#toBuyResRange', comm.lang("coDeclaration").toBuyResRangeEnum, 185).change(function (e) {
				var spreadEntResNo = $("#spreadEntResNo").val();
				var buyResRange = $("#toBuyResRange").attr('optionValue');
				tgqysb.findResNoListAndQuota(spreadEntResNo, buyResRange, null);
			});

			/**申报类型*/
			$("#toCustType").selectList({
				width: 185,
				optionWidth: 185,
				options: [
					{name: comm.lang("coDeclaration").tgEntDesc, value: '3', selected: true},
					{name: comm.lang("coDeclaration").cyEntDesc, value: '2'},
					{name: comm.lang("coDeclaration").fwEntDesc, value: '4'}
				]
			}).change(function (e) {
				var str = $(this).attr('optionValue');
				switch (str) {
					case '2':
						require(['coDeclarationSrc/qyxz/qyxtzcxx_cyqy'], function (qyxtzcxx_cyqy) {
							qyxtzcxx_cyqy.showPage();
						});
						break;
					case '4':
						require(['coDeclarationSrc/qyxz/qyxtzcxx_fwgs'], function (qyxtzcxx_fwgs) {
							qyxtzcxx_fwgs.showPage();
						});
						break;
				}
			});

			//顺序选配
			$('#sxxp1').click(function () {
				$("#select_btn").hide();
				var spreadEntResNo = $("#spreadEntResNo").val();
				var buyResRange = $("#toBuyResRange").attr('optionValue');
				tgqysb.findResNoListAndQuota(spreadEntResNo, buyResRange, null);
			});

			//人工选配
			$('#rgxp1').click(function () {
				$("#select_btn").show();
				$("#entResNo").val("");
			});

			//刷新增值点数据
			$('#refincrement').click(function () {
				var spreadEntResNo = $("#spreadEntResNo").val();
				var zzdqyglh = $.trim($("#zzdqyglh").val())||spreadEntResNo;

				if (spreadEntResNo.substring(0,2)==zzdqyglh.substring(0,2)) {
					tgqysb.findIncrement(spreadEntResNo,zzdqyglh, true);
				}else{
					var message = comm.lang("coDeclaration").resnoFormError;
					message = message.replace('serResNo', spreadEntResNo);
					comm.warn_alert(message,600);
				}
			});

			//保存
			$('#qyxtzcxx_save').click(function () {
				tgqysb.saveRegInfoStepOne(false);
			});

			//处理返回按钮
			if ($('#050100000000_subNav_050101000000').hasClass('s_hover')) {
				$('#qyxtzcxx_back').hide();
			} else {
				$('#qyxtzcxx_back').show();
			}
			$('#qyxtzcxx_back').click(function () {
				$('#050100000000_subNav_050103000000').click();
			});

			//下一步 
			$('#qyxtzcxx_next').click(function () {
				tgqysb.saveRegInfoStepOne(true);
			});

			//选择互生号
			$('#select_btn').click(function () {
				var spreadEntResNo = $("#spreadEntResNo").val();
				var buyResRange = $("#toBuyResRange").attr('optionValue');
				pointChoose.findMemberPointList(spreadEntResNo, 3, buyResRange, "#entResNo");
			});

			//获取所属国家
			cacheUtil.findCacheSystemInfo(function (sysRes) {
				$('#countryNo').val(sysRes.countryNo);
				$('#countryText').html(sysRes.countryName);
			});

			if (data) {
				$("#toBuyResRange").selectListValue(data.toBuyResRange);
				tgqysb.initPCList(data.provinceNo, data.cityNo, data.spreadEntResNo);
				tgqysb.findResNoListAndQuota(data.spreadEntResNo, data.toBuyResRange, data.toEntResNo);
				if(data.toSelectMode&&data.toSelectMode==1){
					$('#rgxp1').attr('checked',true);
					$("#select_btn").show();
				}else {
					$('#sxxp1').attr('checked',true);
				}
				dataModoule.findResNoDetail({spreadEntResNo:data.spreadEntResNo},function (result) {
					if (result.data) {
						//经营类型 0：普通 1：连锁企业
						if (result.data.businessType == 0) {
							$('#province_slt').selectEnabled(false);
							$('#city_slt').selectEnabled(false);
						}else{
							$('#province_slt').selectEnabled(true);
							$('#city_slt').selectEnabled(true);
						}
					} else {
						comm.warn_alert("[" + data.spreadEntResNo + "]" + comm.langConfig['coDeclaration'][36061]);
					}
				});
			} else {
				tgqysb.initPCList(null, null);
			}

			$("#zzdqyglh").focus(function () {
				$("#zzdqyglh").attr("title", "如因业务关系需填写下属企业管理号，须填写完并刷新增值点数据后再选择增值点，否则不需输入直接选择增值点则可。").tooltip({
					position: {
						my: "left top+33",
						at: "left top"
					}
				}).tooltip("open");
			});

			$('#spreadEntResNo_refresh').bind('click',function () {
				var regx = /^(([1-9]\d){1}|(\d[1-9]){1})([1-9]\d{2}|\d{2}[1-9]|\d[1-9]\d)([0]{6})$/;
				var spResNo = $.trim($('#spreadEntResNo').val());
				if(spResNo&&regx.test(spResNo)) {
					dataModoule.findResNoDetail({spreadEntResNo:spResNo},function (result) {
						if(result.data) {
							tgqysb.initPCList(result.data.provinceNo, result.data.cityNo);
							//经营类型 0：普通 1：连锁企业
							if(result.data.businessType==0) {
								$('#province_slt').selectEnabled(false);
								$('#city_slt').selectEnabled(false);
							}else {
								$('#province_slt').selectEnabled(true);
								$('#city_slt').selectEnabled(true);
							}
							tgqysb.findIncrement(spResNo,spResNo, false);
							var buyResRange = $("#toBuyResRange").attr('optionValue');
							if(buyResRange) {
								tgqysb.findResNoListAndQuota(spResNo, buyResRange, null);
							}
							$('#refincrement').attr('disabled', false);
							$('#zzdqyglh').val('');
						}else{
							comm.warn_alert("["+spResNo+"]"+comm.langConfig['coDeclaration'][36061])
						}
					});
				}else{
					comm.warn_alert(comm.langConfig['coDeclaration'].rightServiceNo);
				}
			});
		},
		/**
		 * 初始化数据
		 */
		initData: function () {
			var applyId = this.getApplyId();
			if (applyId == null) {
				tgqysb.initForm(null);
			} else {
				dataModoule.findDeclareByApplyId({applyId: applyId}, function (res) {
					tgqysb.initForm(res.data);
					if (res.data.toPnodeResNo) {
						$("#zzdqyglh").val(res.data.toPnodeResNo);
						var score = comm.removeNull(res.data.toInodeResNo);//1增值分配点
						var chooseId = "increment-1" + ((res.data.toInodeLorR == 0) ? "-l" : "-r");
						if (score.indexOf("R") != -1) {//3增值分配点
							chooseId = "increment-3" + ((res.data.toInodeLorR == 0) ? "-l" : "-r");
						} else if (score.indexOf("L") != -1) {//2增值分配点
							chooseId = "increment-2" + ((res.data.toInodeLorR == 0) ? "-l" : "-r");
						}
						tgqysb.findIncrement(res.data.spreadEntResNo,res.data.toPnodeResNo, false, chooseId);
					}
				});
			}
		},
		/**
		 * 初始化地区下拉
		 * @param provCode 省份代码
		 * @param cityCode 城市代码
		 */
		initPCList: function (provCode, cityCode, spreadEntResNo) {
			cacheUtil.findCacheSystemInfo(function (sysRes) {
				//初始化省份
				cacheUtil.findCacheProvinceByParent(sysRes.countryNo, function (provArray) {
					comm.initProvSelect('#province_slt', provArray, 70, provCode).change(function (e) {
						cacheUtil.findCacheCityByParent(sysRes.countryNo, $(this).attr('optionValue'), function (cityArray) {
							var cSelect = comm.initCitySelect('#city_slt', cityArray, 70, "", {name: '', value: ''});
							tgqysb.cityChange(cSelect);
						});
					});
				});
				//初始化城市
				if (provCode) {
					cacheUtil.findCacheSystemInfo(function (sysRes) {
						cacheUtil.findCacheCityByParent(sysRes.countryNo, provCode, function (cityArray) {
							var cSelect = comm.initCitySelect('#city_slt', cityArray, 70, cityCode, {name: '', value: ''});
							tgqysb.cityChange(cSelect);
						});
					});
				}
			});
		},
		/**
		 * 切换城市
		 */
		cityChange: function (cSelect) {
			cSelect.change(function (e) {
				// tgqysb.setHtmlData("", "", "", "");
			});
		},

		/**
		 * 查询企业配额数和可用互生号列表
		 * @param spreadEntResNo 服务公司互生号
		 * @param buyResRange 启用资源类型
		 * @param resNo 拟用互生号
		 */
		findResNoListAndQuota: function (spreadEntResNo, buyResRange, resNo) {
			if (comm.removeNull(buyResRange) == "" || comm.removeNull(spreadEntResNo) == "") {
				return;
			}
			var params = {};
			params.toCustType = 3;//申报类别
			params.buyResRange = buyResRange;//启用资源类型
			params.serResNo = spreadEntResNo;//服务公司互生号
			dataModoule.findResNoListAndQuota(params, function (res) {
				if (resNo) {
					tgqysb.setHtmlData(resNo, res.data.availQuota, res.data.serCustName, res.data.serCustId);
				} else {
					var chkType = $('input[name="chkType"]:checked').val();
					if (chkType == '0') {
						tgqysb.setHtmlData(res.data.defaultEntResNo, res.data.availQuota, res.data.serCustName, res.data.serCustId);
					} else {
						tgqysb.setHtmlData("", res.data.availQuota, res.data.serCustName, res.data.serCustId);
					}
				}
			});
		},
		/**
		 * 设置服务公司可用配额及管理公司信息
		 * @param resNo 拟用启用互生号
		 * @param availQuota 托管企业可用配额
		 * @param spreadEntName 所属服务公司名称
		 * @param spreadEntCustId 所属服务公司企业客户号
		 */
		setHtmlData: function (resNo, availQuota, spreadEntName, spreadEntCustId) {
			$("#entResNo").val(comm.removeNull(resNo));
			$("#availQuota").val(comm.removeNull(availQuota));
			$("#spreadEntName").val(comm.removeNull(spreadEntName));
			$("#spreadEntCustId").val(comm.removeNull(spreadEntCustId));
		},
		/**
		 * 查询消费增值信息
		 * @param spreadEntResNo 推广互生号
		 * @param subResNo 挂载互生号
		 * @param isAlert 是否显示提示
		 * @param chooseId 选中哪一个单选框
		 */
		findIncrement: function (spreadEntResNo,subResNo, isAlert, chooseId) {
			if(!spreadEntResNo) {
				comm.warn_alert(comm.lang("coDeclaration").findPointNoSerResNo);
				return;
			}
			subResNo = subResNo || spreadEntResNo;
			$("input[name='increment']").attr('checked', false);
			dataModoule.findIncrement({spreadEntResNo:spreadEntResNo,subResNo: subResNo}, function (res) {
				if (res.data) {
					if(!res.data.incrementMap||$.isEmptyObject(res.data.incrementMap)||res.data.subRelation==false){
						var message = comm.lang("coDeclaration").resnoFormError;
						message = message.replace('serResNo', spreadEntResNo);
						comm.warn_alert(message,600);
					}else {
						tgqysb.fillTable(res.data.incrementMap);
						if (isAlert) {
							comm.alert({content: comm.lang("coDeclaration").refSuccess, callOk: function () {}});
						}
						if (chooseId) {
							$("#" + chooseId).attr("checked", true);
						}
					}
				} else {
					if (isAlert) {
						comm.error_alert(comm.lang("coDeclaration").refFailed);
					}
				}
			});
		},
		/**
		 * 填充增值信息
		 */
		fillTable: function (data) {
			if (!data) {
				return;
			}
			var size = 0;
			for (var k in data) {
				size++;
			}
			if (size == 3) {
				for (var k in data) {
					if (k.indexOf("R") > 0) {
						$("#increment-3-lP").html(data[k].lP || data[k].lp);
						$("#increment-3-lCount").html(data[k].lCount || data[k].lcount);
						$("#increment-3-rP").html(data[k].rP || data[k].rp);
						$("#increment-3-rCount").html(data[k].rCount || data[k].rcount);
					} else if (k.indexOf("L") > 0) {
						$("#increment-2-lP").html(data[k].lP || data[k].lp);
						$("#increment-2-lCount").html(data[k].lCount || data[k].lcount);
						$("#increment-2-rP").html(data[k].rP || data[k].rp);
						$("#increment-2-rCount").html(data[k].rCount || data[k].rcount);
					} else {
						$("#increment-1-lP").html(data[k].lP || data[k].lp);
						$("#increment-1-lCount").html(data[k].lCount || data[k].lcount);
						$("#increment-1-rP").html(data[k].rP || data[k].rp);
						$("#increment-1-rCount").html(data[k].rCount || data[k].rcount);
					}
					$("#toPnodeResNo").val(data[k].resNo);
					$("#toPnodeCustId").val(data[k].custId);
				}
				$("#increment-1-table").hide();
				$("#increment-3-table").show();
			} else {
				for (var k in data) {
					$("#increment-lP").html(data[k].lP || data[k].lp);
					$("#increment-lCount").html(data[k].lCount || data[k].lcount);
					$("#increment-rP").html(data[k].rP || data[k].rp);
					$("#increment-rCount").html(data[k].rCount || data[k].rcount);
					$("#toPnodeResNo").val(data[k].resNo);
					$("#toPnodeCustId").val(data[k].custId);
				}
				$("#increment-1-table").show();
				$("#increment-3-table").hide();
			}
			$("#comboxDiv").find("input:radio").attr("disabled", false);
		},
		/**
		 * 表单校验
		 */
		validateData: function () {
			var validate = $("#qyxtzcxx_tgqy_form").validate({
				rules: {
					toCustType: {
						required: true
					},
					toBuyResRange: {
						required: true
					},
					toEntCustName: {
						required: true,
						// custname:true,
						rangelength: [2, 128]
					},
					entResNo: {
						required: true
					},
					province_slt: {
						required: true
					},
					city_slt: {
						required: true
					},
					spreadEntResNo: {
						required: true,
						spreadEntResNoCheck:true
					}
				},
				messages: {
					toCustType: {
						required: comm.lang("coDeclaration")[36049]
					},
					toBuyResRange: {
						required: comm.lang("coDeclaration")[36050]
					},
					toEntCustName: {
						required: comm.lang("coDeclaration")[36051],
						rangelength: comm.lang("coDeclaration")[36052]
					},
					entResNo: {
						required: comm.lang("coDeclaration")[36054]
					},
					province_slt: {
						required: comm.lang("coDeclaration")[36055]
					},
					city_slt: {
						required: comm.lang("coDeclaration")[36056]
					},
					spreadEntResNo: {
						required: comm.lang("coDeclaration")[36057]
					}
				},
				errorPlacement: function (error, element) {
					if ($(element).is(":text")) {
						$(element).attr("title", $(error).text()).tooltip({
							tooltipClass: "ui-tooltip-error",
							destroyFlag: true,
							destroyTime: 1000,
							position: {
								my: "left top+30",
								at: "left top"
							}
						}).tooltip("open");
						$(".ui-tooltip").css("max-width", "230px");
					} else {
						$(element.parent()).attr("title", $(error).text()).tooltip({
							tooltipClass: "ui-tooltip-error",
							destroyFlag: true,
							destroyTime: 1000,
							position: {
								my: "left top+30",
								at: "left top"
							}
						}).tooltip("open");
					}
				},
				success: function (element) {
					$(element).tooltip();
					$(element).tooltip("destroy");
				}
			});
			return validate;
		},
		/**
		 * 保存注册信息第一步
		 * @param autoNext 是否自动进行下一步
		 */
		saveRegInfoStepOne: function (autoNext) {
			var availQuota = $("#availQuota").html();
			if (parseInt(availQuota) == 0) {
				comm.error_alert(comm.lang("coDeclaration").notgPointNo);
				return;
			}
			if (!this.validateData().form()) {
				return;
			}
			var array = this.getPnode();//获取积分增值点设置
			if (array&&array.length>0) {
				tgqysb.saveRegInfo(autoNext, array);
			}
		},
		/**
		 * 保存注册信息
		 * @param autoNext 是否自动跳到下一步
		 * @param array 积分增值信息
		 */
		saveRegInfo: function (autoNext, array) {
			var params = {};
			params.applyId = this.getApplyId();//申请编号
			params.toEntCustName = $("#toEntCustName").val();//被申报企业名称
			params.toEntResNo = this.getToEntResNo();//被申报企业启用资源号,成员企业和托管企业时必填,服务公司不填
			params.countryNo = $("#countryNo").val();//所属国家
			params.provinceNo = $("#province_slt").attr('optionValue');
			//所属省份
			params.cityNo = $("#city_slt").attr('optionValue');//所属城市
			params.toCustType = 3;//被申报企业客户类型
			params.toBuyResRange = $("#toBuyResRange").attr('optionValue');//启用资源类型，托管企业、成员企业必填
			params.toPnodeCustId = array[0];//被申报增值节点父节点客户号
			params.toPnodeResNo = array[1];//被申报增值节点父节点互生号
			params.toInodeResNo = array[2];//被申报选择增值节点
			params.toInodeLorR = array[3];//被申报选择增值节点对应区域
			params.spreadEntResNo = $("#spreadEntResNo").val();//服务公司互生号/推广企业互生号
			params.toMResNo = this.getToMResNo();//所属管理公司互生号
			params.toSelectMode = $('input[name="chkType"]:checked').val();//0-顺序选择 1-人工选择
			dataModoule.checkValidEntResNo(params, function (result) {
				if(result.data&&result.data==true)  {//可用
					dataModoule.saveDeclare(params, function (res) {
						tgqysb.saveStep(1, res.data);
						if (autoNext) {
							$('#qygsdjxx').click();
						} else {
							comm.alert({
								content: comm.lang("coDeclaration")[22000], callOk: function () {
								}
							});
						}
					});
				}else{
					tgqysb.iConfirm(comm.lang('common')[12544],function () {
						dataModoule.saveDeclare(params, function (res) {
							tgqysb.saveStep(1, res.data);
							if (autoNext) {
								$('#qygsdjxx').click();
							} else {
								comm.alert({
									content: comm.lang("coDeclaration")[22000], callOk: function () {
									}
								});
							}
						});
					},function () {
						if(params.toSelectMode == 0) {
							$('#sxxp1').trigger('click');
						}
					});
				}
			});
		},
		/**
		 * 获取拟用企业互生号
		 */
		getToEntResNo: function () {
			return $("#entResNo").val();
		},
		/**
		 * 获取所属管理公司互生号,服务公司截取2位后补9个0
		 */
		getToMResNo: function () {
			var curPointNO = $("#spreadEntResNo").val();
			if (curPointNO == null || curPointNO.length < 2) {
				return "";
			} else {
				return curPointNO.substring(0, 2) + "000000000";
			}
		},
		/**
		 * 获取积分增值点设置
		 * @return array array[0]表示：被申报增值节点父节点客户号array[1]表示：被申报增值节点父节点互生号array[2]表示：被申报选择增值节点array[3]表示：被申报选择增值节点对应区域
		 */
		getPnode: function () {
			var val = $('input[name="increment"]:checked').val();
			if (val) {
				var toPnodeCustId = comm.removeNull($("#toPnodeCustId").val()).replace("R", "").replace("L", "");
				var toPnodeResNo = $("#toPnodeResNo").val();
				if (val == "1-L") {//选择1增值分配点左增值区
					return [toPnodeCustId, toPnodeResNo, toPnodeCustId, 0];
				} else if (val == "1-R") {//选择1增值分配点右增值区
					return [toPnodeCustId, toPnodeResNo, toPnodeCustId, 1];
				} else if (val == "2-L") {//选择2增值分配点左增值区
					return [toPnodeCustId, toPnodeResNo, toPnodeCustId + "L", 0];
				} else if (val == "2-R") {//选择2增值分配点右增值区
					return [toPnodeCustId, toPnodeResNo, toPnodeCustId + "L", 1];
				} else if (val == "3-L") {//选择3增值分配点左增值区
					return [toPnodeCustId, toPnodeResNo, toPnodeCustId + "R", 0];
				} else if (val == "3-R") {//选择3增值分配点右增值区
					return [toPnodeCustId, toPnodeResNo, toPnodeCustId + "R", 1];
				}
			}else{
				comm.warn_alert(comm.lang("coDeclaration").nosetincrement);
			}
			return null;
		},
		/**
		 * 控制步骤
		 * @param curStep 当前步骤
		 * @param applyId 申请编号
		 */
		saveStep: function (curStep, applyId) {
			var entDeclare = comm.getCache("coDeclaration", "entDeclare");
			if ((entDeclare.curStep) <= curStep) {
				entDeclare.curStep = curStep;
			}
			entDeclare.applyId = applyId;
			entDeclare.custType = 3;
		},
		iConfirm : function (text, callback1, callback2) {
			$("#ques_content").html(text);
			$("#alert_ques").dialog({
				title : "提示信息",
				width : "400",
				/*此处根据文字内容多少进行调整！*/
				modal : true,
				buttons : {
					"继续保存" : function () {
						if (callback1)callback1();
						$(this).dialog("destroy");
					},
					"重新选配" : function () {
						if (callback2)callback2();
						$(this).dialog("destroy");
					}
				}
			});
		}
	};
	return tgqysb;
}); 


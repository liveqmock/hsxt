define(['text!coDeclarationTpl/qysbxz/qyxtzcxx_cyqy.html',
        'coDeclarationDat/qysbxz/qyxtzcxx_cyqy',
        'coDeclarationSrc/qysbxz/point_choose',
        'coDeclarationLan'], function(qyxtzcxx_cyqyTpl, dataModoule, pointChoose){
	var scs_qysbxzz_qyxtzcxx_cyqy = {
		showPage: function () {
			$('#contentWidth_2').html(_.template(qyxtzcxx_cyqyTpl));
			scs_qysbxzz_qyxtzcxx_cyqy.initForm();
			scs_qysbxzz_qyxtzcxx_cyqy.initData();
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
		initForm: function () {
			scs_qysbxzz_qyxtzcxx_cyqy.showIncrement();

			//处理返回按钮
			if ($('#030200000000_subNav_030201000000').hasClass('s_hover')) {
				$('#cyqyzcxx_fh').hide();
			} else {
				$('#cyqyzcxx_fh').show();
			}
			$('#cyqyzcxx_fh').click(function () {
				$('#030200000000_subNav_030202000000').click();
			});

			//禁用积分增值设置
			$("#comboxDiv").find("input:radio").attr("disabled", true);

			//初始化启用资源类型
			comm.initSelect('#toBuyResRange', comm.lang("coDeclaration").cyToBuyResRangeEnum, 205).change(function (e) {
				scs_qysbxzz_qyxtzcxx_cyqy.showIncrement();
				scs_qysbxzz_qyxtzcxx_cyqy.findResNoListAndQuota(true, null);
			});

			/*下拉列表*/
			$("#toCustType").selectList({
				width: 205,
				optionWidth: 210,
				options: [
					{name: comm.lang("coDeclaration").tgEntDesc, value: '3'},
					{name: comm.lang("coDeclaration").cyEntDesc, value: '2', selected: true},
					{name: comm.lang("coDeclaration").fwEntDesc, value: '4'}
				]
			}).change(function (e) {
				var str = $(this).val();
				switch (str) {
					case comm.lang("coDeclaration").tgEntDesc :
						require(['coDeclarationSrc/qysbxz/qyxtzcxx_tgqy'], function (qyxtzcxx_tgqy) {
							qyxtzcxx_tgqy.showPage();
						});
						break;
					case comm.lang("coDeclaration").fwEntDesc :
						require(['coDeclarationSrc/qysbxz/qyxtzcxx_fwgs'], function (qyxtzcxx_fwgs) {
							qyxtzcxx_fwgs.showPage();
						});
						break;
				}
			});

			//顺序选配
			$('#sxxp1').click(function () {
				$("#select_btn").hide();
				scs_qysbxzz_qyxtzcxx_cyqy.findResNoListAndQuota(true, null);
			});

			//人工选配
			$('#rgxp1').click(function () {
				$("#select_btn").show();
				$("#entResNo").val("");
			});

			//刷新增值点数据
			$('#refincrement').click(function () {
				var serviceNo = comm.getPointNo();
				var zzdqyglh = $.trim($("#zzdqyglh").val()) || serviceNo;

				if (zzdqyglh.substring(0, 5) == serviceNo.substring(0, 5)) {
					scs_qysbxzz_qyxtzcxx_cyqy.findIncrement(zzdqyglh, false, true);
				} else {
					comm.warn_alert(comm.lang("coDeclaration").resnoFormError);
				}
			});

			//保存
			$('#qyxtzcxx_save').click(function () {
				scs_qysbxzz_qyxtzcxx_cyqy.saveRegInfoStepOne(false);
			});

			//判断页面是否有更新（编辑时候用）
			//$("#qyxtzcxx_cyqy_form :input").change(function() {
			//comm.setCache("coDeclaration", "updateFlag", true);
			//});

			//下一步 
			$('#qyxtzcxx_next').click(function () {
				scs_qysbxzz_qyxtzcxx_cyqy.saveRegInfoStepOne(true);
			});

			//选择互生号
			$('#select_btn').click(function () {
				var spreadEntResNo = comm.removeNull(comm.getPointNo());
				var buyResRange = $("#toBuyResRange").attr('optionValue');
				pointChoose.findMemberPointList(spreadEntResNo, 2, buyResRange, "#entResNo");
			});

			//获取地区信息
			cacheUtil.syncGetRegionByCode(comm.getCustPlace()[0], null, null, "", function (resdata) {
				$("#countryText").html(resdata);
			});

			$("#zzdqyglh").focus(function () {
				$("#zzdqyglh").attr("title", "如因业务关系需填写下属企业管理号，须填写完并刷新增值点数据后再选择增值点，否则不需输入直接选择增值点则可。").tooltip({
					position: {
						my: "left top+33",
						at: "left top"
					}
				}).tooltip("open");
			});

			scs_qysbxzz_qyxtzcxx_cyqy.initPCList(comm.getCustPlace()[1], comm.getCustPlace()[2]);
		},
		/**
		 * 初始化地区下拉
		 * @param prov 省份代码
		 * @param city 城市代码
		 */
		initPCList: function (prov, city) {
			//初始化省份
			cacheUtil.findCacheProvinceByParent(comm.getCustPlace()[0], function (provArray) {
				comm.initProvSelect('#province_slt', provArray, 70, prov).change(function (e) {
					cacheUtil.findCacheCityByParent(comm.getCustPlace()[0], $(this).attr('optionValue'), function (cityArray) {
						comm.initCitySelect('#city_slt', cityArray, 70).selectListIndex(0);
					});
				});
			});
			//初始化城市
			if (prov) {
				cacheUtil.findCacheCityByParent(comm.getCustPlace()[0], prov, function (cityArray) {
					comm.initCitySelect('#city_slt', cityArray, 70, city);
				});
			}
			//非连锁型服务公司禁用省份、城市下拉框
			if (comm.getCookieBusinessType() != "1") {
				$('#province_slt').selectEnabled(false);
				$('#city_slt').selectEnabled(false);
			}
		},
		/**
		 * 显示或隐藏积分增值信息(为正常成员企业才显示积分增值信息)
		 */
		showIncrement: function () {
			if ($("#toBuyResRange").attr('optionValue') == "5") {
				$("#incrementDiv").hide();
			} else {
				$("#incrementDiv").show();
			}
		},
		/**
		 * 初始化数据
		 */
		initData: function () {
			var applyId = scs_qysbxzz_qyxtzcxx_cyqy.getApplyId();
			if (applyId == null) {
				var entDeclare = comm.getCache("coDeclaration", "entDeclare");
				$("#sxxp1").attr("checked", "checked");
				if (entDeclare.defaultIncrement) {
					scs_qysbxzz_qyxtzcxx_cyqy.fillTable(entDeclare.defaultIncrement);
				} else {
					scs_qysbxzz_qyxtzcxx_cyqy.findIncrement("", true, false);
				}
				scs_qysbxzz_qyxtzcxx_cyqy.findResNoListAndQuota(false, null);
			} else {
				dataModoule.findDeclareByApplyId({applyId: applyId}, function (res) {
					if (res.data.toEntCustName) {
						$("#toEntCustName").val(res.data.toEntCustName);
					}
//					if(res.data.toEntEnName){
//						$("#toEntEnName").val(res.data.toEntEnName);
//					}
					if (res.data.toEntResNo) {
						$("#entResNo").val(res.data.toEntResNo);
					}
					if (res.data.toBuyResRange) {
						$("#toBuyResRange").selectListValue(res.data.toBuyResRange);
						scs_qysbxzz_qyxtzcxx_cyqy.findResNoListAndQuota(false, res.data.toEntResNo);
					}
					if (res.data.toSelectMode == 0) {//顺序选配
						$("#sxxp1").attr("checked", "checked");
					} else if (res.data.toSelectMode == 1) {//人工选配
						$("#rgxp1").attr("checked", "checked");
						$("#select_btn").show();
					}
					scs_qysbxzz_qyxtzcxx_cyqy.initPCList(res.data.provinceNo, res.data.cityNo);
					if (res.data.toPnodeResNo) {
						$("#zzdqyglh").val(res.data.toPnodeResNo);
						var score = comm.removeNull(res.data.toInodeResNo);//1增值分配点
						var chooseId = "increment-1" + ((res.data.toInodeLorR == 0) ? "-l" : "-r");
						if (score.indexOf("R") != -1) {//3增值分配点
							chooseId = "increment-3" + ((res.data.toInodeLorR == 0) ? "-l" : "-r");
						} else if (score.indexOf("L") != -1) {//2增值分配点
							chooseId = "increment-2" + ((res.data.toInodeLorR == 0) ? "-l" : "-r");
						}
						scs_qysbxzz_qyxtzcxx_cyqy.findIncrement(res.data.toPnodeResNo, false, false, chooseId);
					} else {
						scs_qysbxzz_qyxtzcxx_cyqy.findIncrement("", true, false);
					}
					scs_qysbxzz_qyxtzcxx_cyqy.showIncrement();
				});
			}
			$("#countryNo").val(comm.getCustPlace()[0]);
		},
		/**
		 * 查询消费增值信息
		 * @param resNo 企业互生号
		 * @param isDefault 是否是查询服务公司本身
		 * @param isAlert 是否显示提示
		 * @param chooseId 选中哪一个单选框
		 */
		findIncrement: function (resNo, isDefault, isAlert, chooseId) {
			$("input[name='increment']").attr('checked', false);
			if (chooseId) {
				$("#" + chooseId).attr("checked", true);
			}
			var entDeclare = comm.getCache("coDeclaration", "entDeclare");
			if (isDefault) {
				resNo = comm.getPointNo();
				if (entDeclare.defaultIncrement) {
					scs_qysbxzz_qyxtzcxx_cyqy.fillTable(entDeclare.defaultIncrement);
					if (isAlert) {
						comm.alert({
							content: comm.lang("coDeclaration").refSuccess, callOk: function () {
							}
						});
					}
				} else {
					dataModoule.findIncrement({resNo: resNo}, function (res) {
						var entDeclare = comm.getCache("coDeclaration", "entDeclare");
						if (res.data) {
							if (!res.data.incrementMap || $.isEmptyObject(res.data.incrementMap)) {
								comm.error_alert(comm.lang("coDeclaration").resnoFormError);
							} else {
								entDeclare.defaultIncrement = res.data.incrementMap;
								scs_qysbxzz_qyxtzcxx_cyqy.fillTable(entDeclare.defaultIncrement);
								if (isAlert) {
									comm.alert({
										content: comm.lang("coDeclaration").refSuccess, callOk: function () {
										}
									});
								}
							}
						} else {
							if (isAlert) {
								comm.error_alert(comm.lang("coDeclaration").refFailed);
							}
							if (entDeclare.defaultIncrement) {
								scs_qysbxzz_qyxtzcxx_cyqy.fillTable(entDeclare.defaultIncrement);
							}
						}
					});
				}
			} else {
				dataModoule.findIncrement({resNo: resNo}, function (res) {
					if (res.data) {
						if (!res.data.incrementMap || $.isEmptyObject(res.data.incrementMap)) {
							comm.error_alert(comm.lang("coDeclaration").resnoFormError);
						} else {
							scs_qysbxzz_qyxtzcxx_cyqy.fillTable(res.data.incrementMap);
							if (isAlert) {
								comm.alert({
									content: comm.lang("coDeclaration").refSuccess, callOk: function () {
									}
								});
							}
						}
					} else {
						if (isAlert) {
							comm.error_alert(comm.lang("coDeclaration").refFailed);
						}
						var entDeclare = comm.getCache("coDeclaration", "entDeclare");
						if (entDeclare.defaultIncrement) {
							scs_qysbxzz_qyxtzcxx_cyqy.fillTable(entDeclare.defaultIncrement);
						}
					}
				});
			}
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
		 * 查询企业配额数和可用互生号列表
		 * @param change 是否改变拟用互生号
		 * @param entResNo 已选的互生号
		 */
		findResNoListAndQuota: function (change, entResNo) {
			var buyResRange = $("#toBuyResRange").attr('optionValue');
			if (buyResRange == null || buyResRange == "") {
				return;
			}
			var params = {};
			params.toCustType = 2;
			params.buyResRange = buyResRange;
			dataModoule.findResNoListAndQuota(params, function (res) {
				$("#availQuota").html(res.data.availQuota);
				if (change && res.data.defaultEntResNo) {
					$("#entResNo").val(res.data.defaultEntResNo);
				} else {
					$("#entResNo").val(comm.removeNull(entResNo));
				}
			});
		},
		/**
		 * 表单校验
		 */
		validateData: function () {
			var validate = $("#qyxtzcxx_cyqy_form").validate({
				rules: {
					toCustType: {
						required: true
					},
					toBuyResRange: {
						required: true
					},
					toEntCustName: {
						required: true,
						rangelength: [2, 64]
					},
//					toEntEnName : {
//						//required : true,
//						rangelength : [2, 128]
//					},
					province_slt: {
						required: true
					},
					city_slt: {
						required: function () {
							return comm.isNotEmpty($("#province_slt").val());
						}
					},
					entResNo: {
						required: true
					}
				},
				messages: {
					toCustType: {
						required: comm.lang("coDeclaration")[32684]
					},
					toBuyResRange: {
						required: comm.lang("coDeclaration")[32692]
					},
					toEntCustName: {
						required: comm.lang("coDeclaration")[32685],
						rangelength: comm.lang("coDeclaration")[32686]
					},
//					toEntEnName : {
//						//required : comm.lang("coDeclaration")[32702],
//						rangelength : comm.lang("coDeclaration")[32690]
//					},
					province_slt: {
						required: comm.lang("coDeclaration")[32688]
					},
					city_slt: {
						required: comm.lang("coDeclaration")[32689]
					},
					entResNo: {
						required: comm.lang("coDeclaration")[32693]
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
			var chkType = $('input[name="chkType"]:checked').val();
			if (chkType == '0') {
				validate.settings.rules.entResNo = {required: true};
				validate.settings.rules.combox = {required: false};
				validate.settings.messages.entResNo = {required: comm.lang("coDeclaration")[32693]};
			} else {
				validate.settings.rules.entResNo = {required: false};
				validate.settings.rules.combox = {required: true};
				validate.settings.messages.combox = {required: comm.lang("coDeclaration")[32693]};
			}
			return validate;
		},
		/**
		 * 保存注册信息第一步
		 * @param autoNext 是否自动进行下一步
		 */
		saveRegInfoStepOne: function (autoNext) {
			var availQuota = $("#availQuota").html();
			if (availQuota == null || availQuota == "" || parseInt(availQuota) == 0) {
				comm.error_alert(comm.lang("coDeclaration")[32694]);
				return;
			}
			if (!this.validateData().form()) {
				return;
			}
			var scs_qysbxzz_qyxtzcxx_cyqy = this;
			if ($("#toBuyResRange").attr('optionValue') == "4") {//为正常成员企业才显示积分增值信息
				var array = this.getPnode();//获取积分增值点设置
				if (array && array.length > 0) {
					scs_qysbxzz_qyxtzcxx_cyqy.saveRegInfo(autoNext, array);
				}
			} else {
				scs_qysbxzz_qyxtzcxx_cyqy.saveRegInfo(autoNext, [null, null, null, null]);
			}
		},
		/**
		 * 保存注册信息
		 * @param autoNext 是否自动进行下一步
		 * @param array 积分增值信息
		 */
		saveRegInfo: function (autoNext, array) {
			var params = {};
			params.applyId = this.getApplyId();//申请编号
			params.toEntCustName = $("#toEntCustName").val();//被申报企业名称
//			params.toEntEnName = $("#toEntEnName").val();//被申报企业英文名称
			params.toEntResNo = $("#entResNo").val();//被申报企业启用资源号,成员企业和托管企业时必填,服务公司不填
			params.countryNo = $("#countryNo").val();//所属国家
			params.provinceNo = $("#province_slt").attr('optionValue');//所属省份
			params.cityNo = $("#city_slt").attr('optionValue');//所属城市
			params.toCustType = 2;//被申报企业客户类型
			params.toBuyResRange = $("#toBuyResRange").attr('optionValue');//启用资源类型，托管企业、成员企业必填
			params.toPnodeCustId = array[0];//被申报增值节点父节点客户号
			params.toPnodeResNo = array[1];//被申报增值节点父节点互生号
			params.toInodeResNo = array[2];//被申报选择增值节点
			params.toInodeLorR = array[3];//被申报选择增值节点对应区域
			params.toMResNo = this.getToMResNo();//所属管理公司互生号
			params.toSelectMode = $('input[name="chkType"]:checked').val();//互生号选配方式
			dataModoule.checkValidEntResNo(params, function (result) {
				if(result.data&&result.data==true)  {//可用
					dataModoule.saveDeclare(params, function (res) {
						scs_qysbxzz_qyxtzcxx_cyqy.saveStep(1, res.data);

						comm.delCache("coDeclaration", "updateFlag");

						if (autoNext) {
							$('#qysbxz_qygsdjxx').click();
						} else {
							comm.alert({
								content: comm.lang("coDeclaration")[21000], callOk: function () {
								}
							});
						}
					});
				}else{
					scs_qysbxzz_qyxtzcxx_cyqy.iConfirm(comm.lang('common')[12544],function () {
						dataModoule.saveDeclare(params, function (res) {
							scs_qysbxzz_qyxtzcxx_cyqy.saveStep(1, res.data);

							comm.delCache("coDeclaration", "updateFlag");

							if (autoNext) {
								$('#qysbxz_qygsdjxx').click();
							} else {
								comm.alert({
									content: comm.lang("coDeclaration")[21000], callOk: function () {
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
		 * 获取所属管理公司互生号
		 */
		getToMResNo: function () {
			var curPointNO = comm.getPointNo();
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
			} else {
				comm.error_alert(comm.lang("coDeclaration").nosetincrement);
				return null;
			}
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
			entDeclare.custType = 2;
		},
		iConfirm: function (text, callback1, callback2) {
			$("#ques_content").html(text);
			$("#alert_ques").dialog({
				title: "提示信息",
				width: "400",
				/*此处根据文字内容多少进行调整！*/
				modal: true,
				buttons: {
					"继续保存": function () {
						if (callback1)callback1();
						$(this).dialog("destroy");
					},
					"重新选配": function () {
						if (callback2)callback2();
						$(this).dialog("destroy");
					}
				}
			});
		}
	};	
	return scs_qysbxzz_qyxtzcxx_cyqy;
});
define(['text!coDeclarationTpl/qysbxz/qyxtzcxx_fwgs.html',
        'coDeclarationDat/qysbxz/qyxtzcxx_fwgs',
        'coDeclarationLan'], function(qyxtzcxx_fwgsTpl, dataModoule){
	return {
		showPage: function () {
			$('#contentWidth_2').html(_.template(qyxtzcxx_fwgsTpl));
			this.initForm();
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
		initForm: function () {
			var self = this;

			comm.initProvSelect('#province_slt', {}, 70, null);
			comm.initCitySelect('#city_slt', {}, 70, null);

			//处理返回按钮
			if ($('#030200000000_subNav_030201000000').hasClass('s_hover')) {
				$('#fwgszcxx_fh').hide();
			} else {
				$('#fwgszcxx_fh').show();
			}
			$('#fwgszcxx_fh').click(function () {
				$('#030200000000_subNav_030202000000').click();
			});

			//禁用积分增值设置
			$("#comboxDiv").find("input:radio").attr("disabled", true);

			/*下拉列表*/
			$("#toCustType").selectList({
				width: 205,
				optionWidth: 210,
				options: [
					{name: comm.lang("coDeclaration").tgEntDesc, value: '3'},
					{name: comm.lang("coDeclaration").cyEntDesc, value: '2'},
					{name: comm.lang("coDeclaration").fwEntDesc, value: '4', selected: true}
				]
			}).change(function (e) {
				var str = $(this).val();
				switch (str) {
					case comm.lang("coDeclaration").tgEntDesc :
						require(['coDeclarationSrc/qysbxz/qyxtzcxx_tgqy'], function (qyxtzcxx_tgqy) {
							qyxtzcxx_tgqy.showPage();
						});
						break;
					case comm.lang("coDeclaration").cyEntDesc :
						require(['coDeclarationSrc/qysbxz/qyxtzcxx_cyqy'], function (qyxtzcxx_cyqy) {
							qyxtzcxx_cyqy.showPage();
						});
						break;
				}
			});

			self.initPCList(null, null);

			//刷新增值点数据
			$('#refincrement').click(function () {
				var serviceNo = comm.getPointNo();
				var zzdqyglh = $.trim($("#zzdqyglh").val())||serviceNo;
				if(zzdqyglh.substring(0,5)==serviceNo.substring(0,5)) {
					self.findIncrement(zzdqyglh, false, true);
				}else {
					comm.warn_alert(comm.lang("coDeclaration").resnoFormError);
				}
			});

			//保存
			$('#qyxtzcxx_save').click(function () {
				self.saveRegInfoStepOne(false);
			});

			//判断页面是否有更新（编辑时候用）
			//$("#qyxtzcxx_fwgs_form :input").change(function() {
			//comm.setCache("coDeclaration", "updateFlag", true);
			//});

			//下一步 
			$('#qyxtzcxx_next').click(function () {
				self.saveRegInfoStepOne(true);
			});

			//设置所属国家
			var countryNo = comm.getCustPlace()[0];
			$("#countryNo").val(countryNo);
			//获取地区信息
			cacheUtil.syncGetRegionByCode(countryNo, null, null, "", function (resdata) {
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
		},
		/**
		 * 初始化地区下拉
		 * @param prov 省份代码
		 * @param city 城市代码
		 */
		initPCList: function (prov, city) {
			var self = this;

			//初始化省份
			cacheUtil.findCacheSystemInfo(function (sysRes) {
				cacheUtil.findCacheProvinceByParent(sysRes.countryNo, function (provArray) {
					comm.initProvSelect('#province_slt', provArray, 70, prov).change(function (e) {
						cacheUtil.findCacheCityByParent(sysRes.countryNo, $(this).attr('optionValue'), function (cityArray) {
							var cSelect = comm.initCitySelect('#city_slt', cityArray, 70);
							cSelect.selectListIndex(0);
							self.cityChange(cSelect);
							self.findManageEntAndQuota(true);
						});
					});
				});
				//初始化城市
				if (prov) {
					cacheUtil.findCacheSystemInfo(function (sysRes) {
						cacheUtil.findCacheCityByParent(sysRes.countryNo, prov, function (cityArray) {
							var cSelect = comm.initCitySelect('#city_slt', cityArray, 70, city);
							self.cityChange(cSelect);
							self.findManageEntAndQuota(true);
						});
					});
				}
			});
			self.findManageEntAndQuota(false);
		},
		/**
		 * 切换城市
		 */
		cityChange: function (cSelect) {
			var self = this;
			cSelect.change(function (e) {
				self.setHtmlData("", "", "");
				self.findManageEntAndQuota(true);
			});
		},
		/**
		 * 设置服务公司可用配额及管理公司信息
		 * @param availQuota 参数对象
		 * @param mangeCompanyNo 参数对象
		 * @param mangeCompanyName 参数对象
		 */
		setHtmlData: function (availQuota, mangeCompanyNo, mangeCompanyName) {
			$("#availQuota").html(availQuota);
			$("#mangeCompanyNo").html(mangeCompanyNo);
			$("#mangeCompanyName").html(mangeCompanyName);
		},
		/**
		 * 初始化数据
		 */
		initData: function () {
			var self = this;
			var applyId = this.getApplyId();
			if (applyId == null) {
				var entDeclare = comm.getCache("coDeclaration", "entDeclare");
				if (entDeclare.defaultIncrement) {
					self.fillTable(entDeclare.defaultIncrement);
				} else {
					self.findIncrement("", true, false);
				}
			} else {
				dataModoule.findDeclareByApplyId({applyId: applyId}, function (res) {
					if (res.data.toEntCustName) {
						$("#toEntCustName").val(res.data.toEntCustName);
					}
//					if (res.data.toEntEnName) {
//						$("#toEntEnName").val(res.data.toEntEnName);
//					}
					if (res.data.toEntResNo) {
						$("#entResNo").val(res.data.toEntResNo);
					}
					if (res.data.toBusinessType) {
						$("#toBusinessType_" + res.data.toBusinessType).attr("checked", true);
					}
					if (res.data.toPnodeResNo) {
						$("#zzdqyglh").val(res.data.toPnodeResNo);
						var score = comm.removeNull(res.data.toInodeResNo);//1增值分配点
						var chooseId = "increment-1" + ((res.data.toInodeLorR == 0) ? "-l" : "-r");
						if (score.indexOf("R") != -1) {//3增值分配点
							chooseId = "increment-3" + ((res.data.toInodeLorR == 0) ? "-l" : "-r");
						} else if (score.indexOf("L") != -1) {//2增值分配点
							chooseId = "increment-2" + ((res.data.toInodeLorR == 0) ? "-l" : "-r");
						}
						var toPnodeResNo = res.data.toPnodeResNo;
						var mRegex = /^(([1-9]\d){1}|(\d[1-9]){1})([0]{9})$/;
						if(!mRegex.test(toPnodeResNo)){
							self.findIncrement(res.data.toPnodeResNo, false, false, chooseId);
						}
					} else {
						self.findIncrement("", true, false);
					}
					self.initPCList(res.data.provinceNo, res.data.cityNo);
				});
			}
		},
		/**
		 * 查询消费增值信息
		 * @param resNo 企业互生号
		 * @param isDefault 是否是查询服务公司本身
		 * @param isAlert 是否显示提示
		 * @param chooseId 选中哪一个单选框
		 */
		findIncrement: function (resNo, isDefault, isAlert, chooseId) {
			var self = this;
			$("input[name='increment']").attr('checked', false);
			if (chooseId) {
				$("#" + chooseId).attr("checked", true);
			}
			var entDeclare = comm.getCache("coDeclaration", "entDeclare");
			if (isDefault) {
				resNo = comm.getPointNo();
				if (entDeclare.defaultIncrement) {
					self.fillTable(entDeclare.defaultIncrement);
					if (isAlert) {
						comm.alert({content: comm.lang("coDeclaration").refSuccess, callOk: function () {}});
					}
				} else {
					dataModoule.findIncrement({resNo: resNo}, function (res) {
						var entDeclare = comm.getCache("coDeclaration", "entDeclare");
						if (res.data) {
							if(!res.data.incrementMap||$.isEmptyObject(res.data.incrementMap)) {
								comm.error_alert(comm.lang("coDeclaration").resnoFormError);
							}else {
								entDeclare.defaultIncrement = res.data.incrementMap;
								self.fillTable(entDeclare.defaultIncrement);
								if (isAlert) {
									comm.alert({content: comm.lang("coDeclaration").refSuccess, callOk: function () {}});
								}
							}
						} else {
							if (isAlert) {
								comm.error_alert(comm.lang("coDeclaration").refFailed);
							}
							if (entDeclare.defaultIncrement) {
								self.fillTable(entDeclare.defaultIncrement);
							}
						}
					});
				}
			} else {
				dataModoule.findIncrement({resNo: resNo}, function (res) {
					if (res.data) {
						if(!res.data.incrementMap||$.isEmptyObject(res.data.incrementMap)){
							comm.error_alert(comm.lang("coDeclaration").resnoFormError);
						}else {
							self.fillTable(res.data.incrementMap);
							if (isAlert) {
								comm.alert({content: comm.lang("coDeclaration").refSuccess, callOk: function () {}});
							}
						}
					} else {
						if (isAlert) {
							comm.error_alert(comm.lang("coDeclaration").refFailed);
						}
						var entDeclare = comm.getCache("coDeclaration", "entDeclare");
						if (entDeclare.defaultIncrement) {
							self.fillTable(entDeclare.defaultIncrement);
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
		 * 查询管理公司信息和服务公司配额数
		 */
		findManageEntAndQuota: function (change) {
			var self = this;
			var countryNo = $("#countryNo").val();
			var provinceNo = $("#province_slt").attr('optionvalue');
			var cityNo = $("#city_slt").attr('optionvalue');
			if (countryNo == "" || provinceNo == "" || cityNo == "") {
				return;
			}
			var params = {};
			params.countryNo = countryNo;
			params.provinceNo = provinceNo;
			params.cityNo = cityNo;
			dataModoule.findManageEntAndQuota(params, function (res) {
				if (res.data) {
					var entName = (res.data.manageEnt) ? res.data.manageEnt.entName : "";
					var entResNo = (res.data.manageEnt) ? res.data.manageEnt.entResNo : "";
					self.setHtmlData(res.data.quota, entResNo, entName);
					var resno_rev = entResNo.substring(0, 2);//返回的互生号
					var resno_sef = comm.getPointNo().substring(0, 2);//登陆的互生号
					if (resno_rev == resno_sef) {
						$("#incrementDiv").show();
						$("#noIncrement").val("false");
					} else {
						$("#noIncrement").val("true");
						$("#incrementDiv").hide();
					}
				} else {//异常处理
					comm.warn_alert(comm.lang("coDeclaration").resNomanageEntInfo);
					self.setHtmlData("", "", "");
					$("#incrementDiv").show();
					$("#noIncrement").val("false");
				}
			});
		},
		/**
		 * 表单校验
		 */
		validateData: function () {
			var validate = $("#qyxtzcxx_fwgs_form").validate({
				rules: {
					toCustType: {
						required: true
					},
					toEntCustName: {
						required: true,
						// custname:true,
						rangelength: [2, 64]
					},
//					toEntEnName: {
//						//required : true,
//						rangelength: [2, 128]
//					},
					province_slt: {
						required: true
					},
					city_slt: {
						required: function () {
							return comm.isNotEmpty($("#province_slt").val());
						}
					},
					toBusinessType: {
						required: true
					}
				},
				messages: {
					toCustType: {
						required: comm.lang("coDeclaration")[32684]
					},
					toEntCustName: {
						required: comm.lang("coDeclaration")[32685],
						rangelength: comm.lang("coDeclaration")[32686]
					},
//					toEntEnName: {
//						//required : comm.lang("coDeclaration")[32702],
//						rangelength: comm.lang("coDeclaration")[32690]
//					},
					province_slt: {
						required: comm.lang("coDeclaration")[32688]
					},
					city_slt: {
						required: comm.lang("coDeclaration")[32689]
					},
					toBusinessType: {
						required: comm.lang("common")[22057]
					}
				},
				errorPlacement: function (error, element) {
					$(element).attr("title", $(error).text()).tooltip({
						tooltipClass: "ui-tooltip-error",
						destroyFlag: true,
						destroyTime: 3000,
						position: {
							my: "left+2 top+30",
							at: "left top"
						}
					}).tooltip("open");
					$(".ui-tooltip").css("max-width", "230px");
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
			if (parseInt(availQuota) == 0) {
				comm.error_alert(comm.lang("coDeclaration")[32695]);
				return;
			}
			if (!this.validateData().form()) {
				return;
			}
			var availQuota = $("#availQuota").html();
			var noIncrement = $("#noIncrement").val();//是否有积分增值设置
			if (availQuota == null || availQuota == "" || parseInt(availQuota) == 0) {
				comm.error_alert(comm.lang("coDeclaration")[32695]);
				return;
			}
			var self = this;
			if (noIncrement == "true") {
				self.saveRegInfo(autoNext, [null, null, null, null]);
			} else {
				var array = this.getPnode();//获取积分增值点设置
				if (array&&array.length>0) {
					self.saveRegInfo(autoNext, array);
				}
			}
		},
		/**
		 * 保存注册信息
		 * @param autoNext 是否自动进行下一步
		 * @param array 积分增值信息
		 */
		saveRegInfo: function (autoNext, array) {
			if (!this.validateData().form()) {
				return;
			}
			var self = this;
			var params = {};
			params.applyId = this.getApplyId();//申请编号
			params.toEntCustName = $("#toEntCustName").val();//被申报企业名称
//			params.toEntEnName = $("#toEntEnName").val();//被申报企业英文名称
			params.toEntResNo = "";//被申报企业启用资源号,成员企业和托管企业时必填,服务公司不填
			params.countryNo = $("#countryNo").val();//所属国家
			params.provinceNo = $("#province_slt").attr('optionValue');//所属省份
			params.cityNo = $("#city_slt").attr('optionValue');//所属城市
			params.toCustType = 4;//被申报企业客户类型
			params.toBusinessType = comm.removeNull($('input[name="toBusinessType"]:checked').val());//经营类型
			params.toBuyResRange = "";//启用资源类型，托管企业、成员企业必填
			params.toPnodeCustId = array[0];//被申报增值节点父节点客户号
			params.toPnodeResNo = array[1];//被申报增值节点父节点互生号
			params.toInodeResNo = array[2];//被申报选择增值节点
			params.toInodeLorR = array[3];//被申报选择增值节点对应区域
			params.toMResNo = $("#mangeCompanyNo").html();//所属管理公司互生号
			dataModoule.saveDeclare(params, function (res) {
				self.saveStep(1, res.data);

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
			entDeclare.custType = 4;
		}
	};
});
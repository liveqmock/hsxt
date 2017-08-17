define(['text!coDeclarationTpl/qysbfk/tab.html',
			'text!coDeclarationTpl/qysbfk/ckxq/skqr_dialog.html',
			'coDeclarationSrc/qysbfk/tgqysbfk',
			'coDeclarationSrc/qysbfk/cyqysbfk',
			'coDeclarationSrc/qysbfk/fwgssbfk',
			
			//子标签切换
			'coDeclarationSrc/qysbfk/ckxq/sbxx',
			'coDeclarationSrc/qysbfk/ckxq/qyxtzcxx',
			'coDeclarationSrc/qysbfk/ckxq/qygsdjxx',
			'coDeclarationSrc/qysbfk/ckxq/qylxxx',
			'coDeclarationSrc/qysbfk/ckxq/qyyhzhxx',
			'coDeclarationSrc/qysbfk/ckxq/qysczl',
			'coDeclarationSrc/qysbfk/ckxq/blztxx',
			'coDeclarationDat/qysbfk/tab',
	        'coDeclarationLan'
			],function(tab, skqr_dialogTpl, tgqysbfk, cyqysbfk, fwgssbfk, sbxx, qyxtzcxx, qygsdjxx, qylxxx, qyyhzhxx, qysczl, blztxx, dataModoule){
	var mcs_qysbfk_tab = {
		curPage: null,
		showPage: function () {
			$('.operationsInner').html(_.template(tab));

			$('#qysbfk_tgqysbfk').click(function (e) {
				tgqysbfk.showPage();
				comm.liActive($('#qysbfk_tgqysbfk'));
				$('#ckxq_qx').triggerWith('#qysbfk_tgqysbfk');
			});

			// 
			$('#qysbfk_cyqysbfk').click(function (e) {
				cyqysbfk.showPage();
				comm.liActive($('#qysbfk_cyqysbfk'));
				$('#ckxq_qx').triggerWith('#qysbfk_cyqysbfk');
			});
			// 
			$('#qysbfk_fwgssbfk').click(function (e) {
				fwgssbfk.showPage();
				comm.liActive($('#qysbfk_fwgssbfk'));
				$('#ckxq_qx').triggerWith('#qysbfk_fwgssbfk');
			});

			/*
			 *  子标签切换
			 */
			$('#ckxq_sbxx').click(function (e) {
				mcs_qysbfk_tab.curPage = 0;
				sbxx.showPage();
				comm.liActive($('#ckxq_sbxx'));
				$('#ckxq_xg').text('修　改');
				$("#skqr_tj").show();
			});

			$('#ckxq_qyxtzcxx').click(function (e) {
				mcs_qysbfk_tab.canGo(qyxtzcxx, $('#ckxq_qyxtzcxx'));
				$("#skqr_tj").show();
			});


			$('#ckxq_qygsdjxx').click(function (e) {
				mcs_qysbfk_tab.canGo(qygsdjxx, $('#ckxq_qygsdjxx'));
				$("#skqr_tj").show();
			});


			$('#ckxq_qylxxx').click(function (e) {
				mcs_qysbfk_tab.canGo(qylxxx, $('#ckxq_qylxxx'));
				$("#skqr_tj").show();
			});

			$('#ckxq_qyyhzhxx').click(function (e) {
				mcs_qysbfk_tab.canGo(qyyhzhxx, $('#ckxq_qyyhzhxx'));
				$("#skqr_tj").show();
			});

			$('#ckxq_qysczl').click(function (e) {
				mcs_qysbfk_tab.canGo(qysczl, $('#ckxq_qysczl'));
				$("#skqr_tj").show();
			});

			$('#ckxq_blztxx').click(function (e) {
				mcs_qysbfk_tab.canGo(blztxx, $('#ckxq_blztxx'));
				$("#skqr_tj").show();
			});


			//权限设置后默认选择规则
			var isModulesDefault = false;	//是否已有默认选择菜单
			var match = comm.findPermissionJsonByParentId("040302000000");

			//遍历资源配额一览表的子菜单设置默认选中
			for (var i = 0; i < match.length; i++) {
				//托管企业申报复核
				if (match[i].permId == '040302010000') {
					$('#qysbfk_tgqysbfk').show();
					$('#qysbfk_tgqysbfk').click();
					//已经设置默认值
					isModulesDefault = true;
				}
				//成员企业申报复核
				else if (match[i].permId == '040302020000') {
					$('#qysbfk_cyqysbfk').show();
					if (isModulesDefault == false) {
						//默认选中
						$('#qysbfk_cyqysbfk').click();
						//已经设置默认值
						isModulesDefault = true;
					}
				}
				//服务公司申报复核
				else if (match[i].permId == '040302030000') {
					$('#qysbfk_fwgssbfk').show();
					if (isModulesDefault == false) {
						//默认选中
						$('#qysbfk_fwgssbfk').click();
						//已经设置默认值
						isModulesDefault = true;
					}
				}
			}


			//审核确认
			$('#skqr_tj').click(function () {
				var ckData = sbxx.checkData();
				if (!ckData.isPass) {
					comm.warn_alert(ckData.message);
					return;
				}
				$('#skqr_dialog>p').empty().html(skqr_dialogTpl);
				$("input[name='isPass']").attr('checked', false);
				$('#skqr_dialog').dialog({
					width: 480,
					buttons: {
						'确认': function () {
							var params = {};
							params.applyId = $("#applyId").val();
							var pass = $("input[name='isPass']:checked").val();
							if (!pass) {
								comm.warn_alert('请选择审核结果!');
								return;
							}
							params.isPass = (pass == '0');
							params.apprRemark = $(this).find("#apprRemark").val();
							var win = $(this);
							dataModoule.managerReviewDeclare(params, function (res) {
								comm.alert({
									content: comm.lang("coDeclaration")[22000], callOk: function () {
										win.dialog('destroy');
										var custType = $("#custType").val();
										if (custType == "4") {
											$('#qysbfk_fwgssbfk').click();
										} else if (custType == "2") {
											$('#qysbfk_cyqysbfk').click();
										} else {
											$('#qysbfk_tgqysbfk').click();
										}
									}
								});
							});
						},
						'取消': function () {
							$(this).dialog('destroy');
						}
					}
				});
			});
		},
		/**
		 * 判断是否保存了申报信息
		 */
		canGo: function (page, menuName) {
			if (mcs_qysbfk_tab.curPage == null || sbxx.isSaveData()) {
				mcs_qysbfk_tab.showActivePage(page, menuName);
			} else {
				comm.i_confirm(comm.lang("coDeclaration").chooseResNo, function () {
					mcs_qysbfk_tab.showActivePage(page, menuName);
				}, 400);
			}
			var oldentResNo_ = comm.getCache("coDeclaration", "oldPickResNo");
			comm.setCache("coDeclaration", "pickResNo", oldentResNo_);
		},
		/**
		 * 显示tab页
		 */
		showActivePage: function (page, menuName) {
			mcs_qysbfk_tab.curPage = null;
			page.showPage();
			comm.liActive(menuName);
			$('#ckxq_xg').text('修　改');
			$('#ckxq_qx').text('返　回');
		}
	};
	return mcs_qysbfk_tab;
}); 


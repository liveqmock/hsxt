define(['text!toolorderTpl/shddgl/shgjps.html',
		'text!toolorderTpl/shddgl/shgjps_pzpsd_dialog.html',
		'text!toolorderTpl/shddgl/shgjps_ckpsd_dialog.html',
        'toolorderDat/shddgl/shgjps',
        'toolorderLan'
		], function(shgjpsTpl, shgjps_pzpsd_dialogTpl, shgjps_ckpsd_dialogTpl, dataModoule){
	var shgjps_self = {
		shgjps_data : null,
		shgjps_entInfo : {},//企业信息
		delivers : null,//收货信息
		confNos : null,//配置单编号列表
		showPage : function(){
			shgjps_self.initForm();
		},
		/**
		 * 初始化界面
		 */
		initForm : function(){
			$('#busibox').html(_.template(shgjpsTpl));
			/** 查询事件 */
			$("#queryBtn").click(function(){
				shgjps_self.initData();
			});
			$('#pzpsd_btn').click(function(){
				var records = shgjps_data.getCheckedRowsRecords();
				if(records.length == 0){
					comm.warn_alert("请至少选择一条记录.");
					return;
				}
				var confNo = new Array();
				var entCustId = "";
				for(var key in records){
					confNo.push(records[key].confNo);
					entCustId = records[key].entCustId;
				}
				shgjps_self.ckpsd(JSON.stringify(confNo), entCustId);
			});
			//初始化查询日期
			comm.initBeginEndTime('#search_startDate','#search_endDate');
		},
		/**
		 * 初始化数据
		 */
		initData : function(){
			if(!comm.queryDateVaild("search_form").form()){
				return;
			}
			var params = {};
			params.search_endDate = $("#search_endDate").val();
			params.search_startDate = $("#search_startDate").val();
			params.search_hsResNo = $("#search_hsResNo").val().trim();
			params.search_hsCustName = $("#search_hsCustName").val().trim();
			shgjps_data = dataModoule.findShipingList(params, shgjps_self.detail);
		},
		/**
		 * 详情
		 */
		detail : function(record, rowIndex, colIndex, options){
			if(colIndex == 5){
				return comm.getNameByEnumId(record['categoryCode'], comm.lang("toolorder").categoryCode);
			}
			if(colIndex == 6){
				return "待配送";
			}
			return $('<a id="'+ record['confNo'] +'" >配置配送单</a>').click(function(e) {
				shgjps_self.ckpsd(JSON.stringify([record.confNo]), record.entCustId);
			}.bind(this));
		},
		/**
		 * 查看配送单
		 */
		ckpsd : function(confNos, entCustId){
			shgjps_self.confNos = confNos;
			dataModoule.findAfterShipingData({confNo:shgjps_self.confNos}, function(res){
				var deliver = res.data.delivers;
				if(deliver){
					for(var key in deliver){
						deliver[key].addrType = comm.getNameByEnumId(deliver[key].addrType, comm.lang("toolorder").addrTypes);
					}
				}
				$('#dialogBox_ck').html(_.template(shgjps_pzpsd_dialogTpl, res.data));
				//填充物品配送清单
				comm.getEasyBsGrid("searchTable_1", res.data.details);
				delivers = res.data.delivers;
				//填充配送方式
				comm.initSelect("#smName", res.data.mothods, 150, null, null, 100, "smName", "smName");
				/*弹出框*/
				$( "#dialogBox_ck" ).dialog({
					title:"查看配送单",
					width:"1100",
					height:"700",
					modal:true,
					buttons:{
						"确定":function(){
							shgjps_self.saveData();
						},
						"取消":function(){
							$("#dialogBox_ck").dialog("destroy");
						}
					}
				});
				$('#searchTable_2_pt').addClass('td_nobody_r_b');
				shgjps_self.findEntInfo(entCustId);
			});
		},
		/**
		 * 查询企业信息
		 */
		findEntInfo : function(entCustId){
			dataModoule.findCompanyInfo({companyCustId:entCustId}, function(res){
				if(res.data){
					shgjps_entInfo = res.data;
					$("#entResNo").html(comm.removeNull(shgjps_entInfo.entResNo));
					$("#entCustName").html(comm.removeNull(shgjps_entInfo.entCustName));
					$("#contactPerson").html(comm.removeNull(shgjps_entInfo.contactPerson));
					$("#contactPhone").html(comm.removeNull(shgjps_entInfo.contactPhone));
					$("#contactAddr").html(comm.removeNull(shgjps_entInfo.contactAddr));
					$("#postCode").html(comm.removeNull(shgjps_entInfo.postCode));
				}
			});
		},
		/**
		 * 数据校验
		 */
		validateSaveData : function(){
			return $("#save_form").validate({
				rules : {
					smName : {
						required : true
					},
					trackingNo : {
						required : true,
						rangelength:[1, 20]
					},
					shippingFee : {
						required : true,
						digits:true
					}
				},
				messages : {
					smName : {
						required : comm.lang("toolorder")[36505]
					},
					trackingNo : {
						required : comm.lang("toolorder")[34519],
						rangelength:comm.lang("toolorder")[36513]
					},
					shippingFee : {
						required : comm.lang("toolorder")[34517],
						digits:comm.lang("toolorder").validNumber
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
		 * 保存数据
		 */
		saveData : function(){
			if(!shgjps_self.validateSaveData().form()){
				return;
			}
			var addr = $('input[name="addr"]:checked').val();
			if(comm.removeNull(addr) == ""){
				comm.warn_alert(comm.lang("toolorder").chooseReveAddr);
				return;
			}
			var reveObj = delivers[addr];
			var params = {};
			params.c_hsResNo = shgjps_entInfo.entResNo;
			params.c_custId = shgjps_entInfo.entCustId;
			params.c_custName = shgjps_entInfo.entCustName;
			params.c_custType = shgjps_entInfo.custType;
			params.receiver = reveObj.linkman;
			params.receiverAddr = reveObj.fullAddr;
			params.postCode = reveObj.zipCode;
			params.c_mobile = reveObj.mobile;
			params.smName = $("#smName").attr('optionValue');
			params.trackingNo = $("#trackingNo").val();
			params.shippingFee = $("#shippingFee").val();
			params.confNos = shgjps_self.confNos;
			dataModoule.addToolShippingAfter(params, function(){
				comm.alert({content:comm.lang("toolorder")[22000], callOk:function(){
					$("#dialogBox_ck").dialog("destroy");
					shgjps_self.initData();
				}});
			});
		}
	};
	return shgjps_self;
});
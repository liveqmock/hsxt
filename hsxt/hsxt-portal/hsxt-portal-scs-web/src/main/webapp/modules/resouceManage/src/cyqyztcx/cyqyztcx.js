define(['text!resouceManageTpl/cyqyztcx/cyqyztcx.html',
        "resouceManageDat/resouceManage",
        "resouceManageLan"],function(xhzzytjTpl, dataModoule){
	return {	 	
		showPage : function(){
			this.initForm();
		},
		/**
		 * 初始化界面
		 */
		initForm : function(){
			var self = this;
			$('#busibox').html(_.template(xhzzytjTpl));
			//$("#search_startDate").datepicker({dateFormat:'yy-mm-dd'});
			//$("#search_endDate").datepicker({dateFormat:'yy-mm-dd'});
			//$("#search_startDate").val("2015-01-01");
			//$("#search_endDate").val(comm.getCurrDate());
			//var monthSE=comm.getMonthSE();
			//$("#search_startDate").val(monthSE[0]);
			//$("#search_endDate").val(monthSE[1]);
			comm.initSelect('#search_status', comm.lang("resouceManage").cy_member_status);
			//查询按钮
			$("#btnQuery").click(function(){
				self.initData();
			});
			//查询按钮
			$("#qyzygl_fh").click(function(){
				$('#busibox').removeClass('none');
				$('#cx_content_detail').addClass('none');
			});
		},
		/**
		 * 初始化数据
		 */
		initData : function(){
			/*if(!this.validateData().form()){
				return;
			}*/
			var params = {};
			//params.search_startDate = $("#search_startDate").val();
			//params.search_endDate = $("#search_endDate").val();
			params.search_entResNo = $.trim($("#search_entResNo").val());
			params.search_entName = $.trim($("#search_entName").val());
			params.search_linkman = $.trim($("#search_linkman").val());
			params.search_status = $("#search_status").attr('optionValue');
			params.search_custType = 2; //统一客户类型定义 1 持卡人;2 成员企业;3 托管企业;4 服务公司;5 管理公司;6 地区平台;7 总平台;8 非持卡人;9 非互生格式化企业 11：操作员 21：POS机；22：积分刷卡器；23：消费刷卡器；24：平板；25：云台
			dataModoule.findBelongEntInfoList('tableDetail',params, this.detail, this.add, this.edit);
		},
		/**
		 * 表单校验
		 */
		validateData : function(){
			return $("#search_form").validate({
				rules : {
					search_startDate : {
						required : true,
						date : true,
						endDate : "#search_endDate"
					},
					search_endDate : {
						required : true,
						date : true
					}
				},
				messages : {
					search_startDate : {
						required : comm.lang("common")[22035],
						date : comm.lang("common")[22012],
						endDate : comm.lang("common")[22014]
					},
					search_endDate : {
						required : comm.lang("common")[22011],
						date : comm.lang("common")[22013],
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
		 * 自定义函数
		 */
		detail : function(record, rowIndex, colIndex, options){
			
			if(colIndex == 6){
				return comm.getNameByEnumId(record['pointStatus'], comm.lang("resouceManage").cy_member_status);
			}
			if(colIndex == 7){
				return  $('<a id="'+ record['entCustId'] +'" >查看</a>').click(function(e) {
					$("#resEntCustId").val(record['entCustId']);
					$('#busibox').addClass('none');
					$('#cx_content_detail').removeClass('none');
					comm.delCache("resouceManage", "entAllInfo");
					$('#ckxq_qyxtzcxx').click();
				}.bind(this));
			}
		},
		/**
		 * 注销
		 */
		add : function(record, rowIndex, colIndex, options){
			if(colIndex == 7){
				if(record['pointStatus'] == 3 || record['pointStatus'] == 4){
					return $('<a tran-type="'+record['entCustId']+'" >注销</a>').click(function(e) {
						$("#resEntCustId").val(record['entCustId']);
						comm.delCache("resouceManage", "memberQuit");
						comm.setCache("resouceManage", "memberQuit", record);
						$('#cyqyztcx_zgzx').click();  
					});
				}
			}
		},
		/**
		 * 启用
		 */
		edit : function(record, rowIndex, colIndex, options){
			if(colIndex == 7){
				if(record['pointStatus'] == 4){
					return $('<a tran-type="'+record['entCustId']+'" >启用</a>').click(function(e) {
						comm.i_confirm('确定重新启用成员企业？', function(){
							dataModoule.updateEntStatusInfo({companyCustId:record['entCustId']}, function(res){
								comm.alert({content:comm.lang("resouceManage")[22000], callOk:function(){
									$('#btnQuery').click();
								}});
							});
						}, 400);
						
					});
				}
			}
		}
	}
}); 

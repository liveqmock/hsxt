define(["text!receivablesmanageTpl/qkdcx/xtsynf.html",
		"text!receivablesmanageTpl/qkdcx/xtsynf_ck.html",
		"receivablesmanageDat/receivablesmanage",
		"receivablesmanageLan"
	],function(xtsynfTpl, xtsynf_ckTpl,receivablesmanage){
	return {
		showPage : function(){
			var self=this;
			
			$('#busibox').html(_.template(xtsynfTpl));	
			//时间控件		    
		    //comm.initBeginEndTime('#search_startDate','#search_endDate');
		    
		    /*日期控件*/
			$("#search_startDate").datepicker({
				dateFormat : 'yy-mm-dd',
				onSelect : function(dateTxt, inst){
					var d = dateTxt.replace('-', '/');
					$("#search_endDate").datepicker('option', 'minDate', new Date(d));	
				}
			});
			$("#search_endDate").datepicker({
				dateFormat : 'yy-mm-dd',
				onSelect : function(dateTxt, inst){
					var d = dateTxt.replace('-', '/');
					$("#search_startDate").datepicker('option', 'maxDate', new Date(d));	
				}
			});
			
			/** 查询事件 */
			$("#btnQuery").click(function(){
				if(!self.queryDateVaild().form()){
					return;
				}
				
				self.pageQuery();
			});
		},
		chaKan : function(obj){
			$('#busibox_ck').html(_.template(xtsynf_ckTpl,obj));	
	/*		
			$('#xtsynfTpl').addClass('none');
			$('#xtsynf_ckTpl').removeClass('none');	*/

			$('#xtsynf_ckTpl').removeClass('none');
			$('#busibox').addClass('none');
			comm.liActive_add($('#ckdd'));
			
			
			//$('#back_xtsynf').triggerWith('#xtsynf');
			//返回按钮
			$('#back_xtsynf').click(function(){
				//隐藏头部菜单
				$('#xtsynf_ckTpl').addClass('none');
				$('#busibox').removeClass('none');
				$('#ckdd').addClass("tabNone").find('a').removeClass('active');
				$('#xtsynf').find('a').addClass('active');
			});
			
		},
		/** 分页查询 */
		pageQuery:function(){
			var self=this;
			//查询参数
			var queryParam={
						"search_resNoCondition":$("#txtHsResNo").val(),"search_custNameCondition":$("#txtCustName").val(),
						"search_startDate":$("#search_startDate").val(),"search_endDate":$("#search_endDate").val()
						};
			var gridObj= receivablesmanage.annualFeeOrderPage("searchTable",queryParam,function(record, rowIndex, colIndex, options){
				
				//企业互生号
				if(colIndex==0){
					return record.order.hsResNo;
				}
				
				//企业名称
				if(colIndex==1){
					var custName = record.order.custName;
					if(record.order.custName.length >= 30){
						custName = custName.substring(0,30)+'...';
					}
					return '<span title='+record.order.custName+'>'+custName+'</span>';
				}
				
				//未缴纳费年区间
				if(colIndex==2){
					return record.areaStartDate+"~"+record.areaEndDate;
				}
				
				//欠费总额
				if(colIndex==3){
					return comm.formatMoneyNumber(parseFloat(record.order.orderOriginalAmount)-parseFloat(record.order.orderDerateAmount));
				}
				
				//折合应付本币（人民币）
				if(colIndex==4){
					return comm.formatMoneyNumber(record.order.orderCashAmount);
				}
				
				var link1 = $('<a>查看</a>').click(function(e) {
					self.chaKan(gridObj.getRecord(rowIndex));
				}.bind(self));
				return link1 ;
			}.bind(self));
		},	
		/**
		 * 验证日期的查询(开始日期、结束日期的name写死)
		 * @param formId 表单ID
		 */
		queryDateVaild : function(){
			return $("#search_form").validate({
				rules : {
					search_startDate : {
						required : true
					},
					search_endDate : {
						required : true
					}
				},
				messages : {
					search_startDate : {
						required : comm.lang("common")[10001]
					},
					search_endDate : {
						required : comm.lang("common")[10002]
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

		
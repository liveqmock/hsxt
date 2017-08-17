define(['text!invoiceTpl/qykfpcx/qykfpcx.html',
		'invoiceSrc/qyxx/qyxx_tab',
		'invoiceDat/invoice',
		'invoiceLan'
		],function(qykfpcxTpl, qyxx_tab,dataModoule){
	return qykfpcx={
		showPage : function(){
			$('#busibox').html(_.template(qykfpcxTpl))	;
			/*表格数据模拟*/
			$("#qykfpcx_btn").click(function(){
				var params=$("#qykfpcx_form").serializeJson();
				dataModoule.invoicePoolList("searchTable",params,qykfpcx.detail);
			});	
		},
		detail : function(record, rowIndex, colIndex, options){
			if(colIndex==0){
				var link1 = $('<a>' + record.resNo + '</a>').click(function(e) {
					qykfpcx.ck_qyxx(record.custId);
				   });	
				return link1;
			}else if(colIndex==2){
				return comm.formatMoneyNumber(record.openedAmount);
			}else if(colIndex==3){
				return comm.formatMoneyNumber(record.remainAmount);
			}
		},
		ck_qyxx : function(custId){
			var that = this;
			comm.liActive_add($('#ckqyxx'));
			this.showTpl($('#ckqyxxTpl'));			
			qyxx_tab.showPage(custId);
			
			$('#back_btn').click(function(){
				comm.liActive($('#qykfpcx'), '#lrfp, #ckqyxx, #ckfpxx');
				that.showTpl($('#qykfpcxTpl'));
			});
		},
		showTpl : function(tplObj){
			$('#qykfpcxTpl, #ckqyxxTpl').addClass('none');
			tplObj.removeClass('none');
		}
	}
	
});
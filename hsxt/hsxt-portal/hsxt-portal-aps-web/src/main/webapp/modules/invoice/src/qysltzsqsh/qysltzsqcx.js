define(['text!invoiceTpl/qysltzsqsh/qysltzsqcx.html',
		'text!invoiceTpl/qysltzsqsh/qysltzsqcx_ck.html',
		'invoiceDat/invoice',
		'invoiceLan'],function(qysltzsqcxTpl, qysltzsqcx_ckTpl, dataModoule){
	var qyslcxFun = {
		showPage : function(){
			$('#busibox').html(_.template(qysltzsqcxTpl));	

			/*下拉列表*/
			comm.initSelect('#search_status', comm.lang("invoice").status);
			
			$("#slsqcx_query").click(function(){
				var params=$("#slsqcx_form").serializeJson();
				$.extend(params,{"search_status":$("#search_status").attr("optionvalue")});
				dataModoule.listTaxrateChange("searchTable",params,qyslcxFun.detail);
			});
		},
		detail : function(record, rowIndex, colIndex, options){
			if(colIndex==4){
				//return comm.percentage(record.applyTaxrate);
				return (parseFloat(record.applyTaxrate)*100).toFixed(2)+"%";
			}else if(colIndex==6){
				return comm.lang("invoice").status[record.status];
			}else if(colIndex==7){
				var link1 = $('<a>查看</a>').click(function(e) {
					dataModoule.queryTaxrateChange({applyId :record.applyId},function(res){
						var obj=res.data;
						comm.groupOperInfo(obj.createdBy,function(res){
							obj.createdBy=obj.resNo+"_"+res;
							qyslcxFun.chaKan(obj);
						});
					});
				});
				return link1;
			}
		},
		chaKan : function(obj){
			$("#qysltzsqcxTpl").hide();
			$('#qysltzsqcx_operation_tpl').show();
			
			obj.currTaxrate=(parseFloat(obj.currTaxrate)*100).toFixed(2)+"%";
			obj.applyTaxrate=(parseFloat(obj.applyTaxrate)*100).toFixed(2)+"%";
			comm.liActive_add($('#ck'));
			$('#qysltzsqcx_operation_tpl').empty().html(_.template(qysltzsqcx_ckTpl, obj));
			if(obj.proveMaterialFile){
				$("#proveFile").attr("href", comm.getFsServerUrl(obj.proveMaterialFile));
			}
			
			//返回
			$("#back_qysltzsqsh").click(function(){
				//显示列表元素
				$("#qysltzsqcxTpl").show();
				//清空查看元素并隐藏
				$('#qysltzsqcx_operation_tpl').html("").hide();
				//选中查询元素
				comm.liActive_add($('#qysltzsqcx'));
				//隐藏查看
				$("#ck").addClass("tabNone");
			});
			//$('#back_qysltzsqsh').triggerWith('#qysltzsqcx');
		}	
	}
	return qyslcxFun;
});
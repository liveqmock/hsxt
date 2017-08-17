define(['text!serviceMsgManageTpl/xtfsgl/yj/yj.html',
		'text!serviceMsgManageTpl/xtfsgl/yj/yj_ck.html',
		'serviceMsgManageDat/serviceMsgManage',
        'serviceMsgManageLan'
		], function(yjTpl, yj_ckTpl, dataModoule){
	return yjxx_self = {
		showPage : function(){
			yjxx_self = this;
			this.initForm();
		},
		initForm : function(){
			$('#busibox').html(yjTpl);
			comm.initSelect('#search_status', comm.lang("serviceMsgManage").sendStatus, null, null, {name:'全部', value:''});
			comm.initSelect('#search_custType', comm.lang("serviceMsgManage").custTypes, null, null, {name:'全部', value:''});
			/** 查询事件 */
			$("#queryBtn").click(function(){
				yjxx_self.initData();
			});
		},
		initData : function(){
			var params = {};
			params.search_enthsResNo = $("#search_enthsResNo").val();
			params.search_custType = comm.removeNull($("#search_custType").attr('optionValue'));
			params.search_status = comm.removeNull($("#search_status").attr('optionValue'));
			dataModoule.findEmailByPage(params, this.detail1);
		},
		/**
		 * 查看
		 */
		detail1 : function(record, rowIndex, colIndex, options){
			if(colIndex == 2){
				return comm.getNameByEnumId(record['custType'], comm.lang("serviceMsgManage").custTypes);
			}
			if(colIndex == 3){
				return comm.getNameByEnumId(record['bizType'], comm.lang("serviceMsgManage").busTypes);
			}
			if(colIndex == 7){
				return comm.getNameByEnumId(record['status'], comm.lang("serviceMsgManage").sendStatus);
			}
			return $('<a id="'+ record.msgId +'" >查看</a>').click(function(e) {
				yjxx_self.chaKan(record);
			}.bind(this));
		},
		chaKan : function(obj){
			var that = this;
			$('#ck_dialog').html(_.template(yj_ckTpl, obj));
			/*弹出框*/
			$( "#ck_dialog" ).dialog({
				title:"查看邮件",
				width:"830",
				modal:true,
				closeIcon : true,
				buttons:{ 
					"确定":function(){
						$( this ).dialog( "destroy" );
					},
					"取消":function(){
						 $( this ).dialog( "destroy" );
					}
				}
			});
		}
	}	
});
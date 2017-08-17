define(['text!serviceMsgManageTpl/xtfsgl/sjdx/sjdx.html',
		'text!serviceMsgManageTpl/xtfsgl/sjdx/sjdx_ck.html' ,
		'serviceMsgManageDat/serviceMsgManage',
        'serviceMsgManageLan'
		], function(sjdxTpl, sjdx_ckTpl, dataModoule){
	return sjdx_self = {
		showPage : function(){
			sjdx_self = this;
			this.initForm();
		},
		initForm : function(){
			$('#busibox').html(sjdxTpl);
			comm.initSelect('#search_status', comm.lang("serviceMsgManage").sendStatus, null, null, {name:'全部', value:''});
			comm.initSelect('#search_custType', comm.lang("serviceMsgManage").custTypes, null, null, {name:'全部', value:''});
			/** 查询事件 */
			$("#queryBtn").click(function(){
				sjdx_self.initData();
			});
		},
		initData : function(){
			var params = {};
			params.search_enthsResNo = $("#search_enthsResNo").val();
			params.search_custType = comm.removeNull($("#search_custType").attr('optionValue'));
			params.search_status = comm.removeNull($("#search_status").attr('optionValue'));
			dataModoule.findNoteByPage(params, this.detail1);
		},
		/**
		 * 查看
		 */
		detail1 : function(record, rowIndex, colIndex, options){
			if(colIndex == 2){
				return comm.getNameByEnumId(record['custType'], comm.lang("serviceMsgManage").custTypes);
			}
			if(colIndex == 3){
				var bizTypeStr=comm.getNameByEnumId(record['bizType'], comm.lang("serviceMsgManage").busTypes);
				return "<span title='"+bizTypeStr+"'>"+bizTypeStr+"</span>";
			}
			if(colIndex == 7){
				return comm.getNameByEnumId(record['status'], comm.lang("serviceMsgManage").sendStatus);
			}
			return $('<a id="'+ record.msgId +'" >查看</a>').click(function(e) {
				sjdx_self.chaKan(record);
			}.bind(this));
		},
		chaKan : function(obj){
			var that = this;
			$('#ck_dialog').html(_.template(sjdx_ckTpl, obj));
			/*弹出框*/
			$( "#ck_dialog" ).dialog({
				title:"查看短信",
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
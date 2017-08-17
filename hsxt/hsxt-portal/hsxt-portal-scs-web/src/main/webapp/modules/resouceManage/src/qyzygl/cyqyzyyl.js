define(['text!resouceManageTpl/qyzygl/cyqyzyyl.html',
        "resouceManageDat/resouceManage",
        "resouceManageLan"],function(cyqyzyylTpl, dataModoule){
	return {	 	
		showPage : function(){
			this.initForm();
		},
		/**
		 * 初始化界面
		 */
		initForm : function(){
			var self = this;
			$('#busibox').html(_.template(cyqyzyylTpl));
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
			var params = {};
			params.search_custType = 2;
			params.search_status = $("#search_status").attr('optionValue');
			params.search_entResNo = $("#search_entResNo").val();
			params.search_entName = $("#search_entName").val();
			params.search_linkman = $("#search_linkman").val();
			dataModoule.findQualMainList("tableDetail",params, this.detail);
		},
		/**
		 * 自定义函数
		 */
		detail : function(record, rowIndex, colIndex, options){
			//列：成员企业状态
			if(colIndex == 6){
				return comm.lang("resouceManage").cy_member_status[record.baseStatus];//comm.getNameByEnumId(record['pointStatus'], comm.lang("resouceManage").cy_member_status);
			}
			//列：企业认证状态
			if(colIndex == 7){ 
				return comm.getNameByEnumId(record['realnameAuth'], comm.lang("resouceManage").realNameAuthSatus);
			}
			return  $('<a id="'+ record['custId'] +'" >查看</a>').click(function(e) {
				$("#resEntCustId").val(record['custId']);
				$('#busibox').addClass('none');
				$('#cx_content_detail').removeClass('none');
				comm.delCache("resouceManage", "entAllInfo");
				$('#ckxq_qyxtzcxx').click();
			}.bind(this));
		}
	}
}); 

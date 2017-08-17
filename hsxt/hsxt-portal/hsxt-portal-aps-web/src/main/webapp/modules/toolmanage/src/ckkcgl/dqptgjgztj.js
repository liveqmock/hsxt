define(['text!toolmanageTpl/ckkcgl/dqptgjgztj.html',
		'toolmanageDat/ckkcgl/dqptgjgztj',
        'toolmanageLan'], function(dqptgjgztjTpl, dataModoule){
	return {
		showPage : function(){
			this.initForm();
		},
		/**
		 * 初始化界面
		 */
		initForm : function(){
			var self = this;
			$('#infobox').html(_.template(dqptgjgztjTpl));
			comm.initSelect('#search_categoryCode', comm.lang("toolmanage").categoryCodeAll, null, null, {name:'', value:''});
			$('#queryBtn').click(function(){
				self.initData();
			});
			self.initData();
		},
		/**
		 * 初始化数据
		 */
		initData : function(){
			var params = {};
			params.search_categoryCode = comm.removeNull($("#search_categoryCode").attr('optionValue'));
			dataModoule.findStatisticPlatWhToollList(params, this.detail);
		},
		/**
		 * 详情
		 */
		detail : function(record, rowIndex, colIndex, options){
			return comm.getNameByEnumId(record['categoryCode'], comm.lang("toolmanage").categoryCodeAll);
		}	
	}	
});
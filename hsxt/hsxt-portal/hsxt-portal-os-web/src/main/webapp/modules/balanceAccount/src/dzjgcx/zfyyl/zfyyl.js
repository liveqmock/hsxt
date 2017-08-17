define([ 'text!balanceAccountTpl/dzjgcx/zfyyl/zfyyl.html',
		'balanceAccountDat/accountResult', 'balanceAccountLan' ], function(
		zfyylTpl, accountResultDat) {
	return {

		showPage : function(tabid) {
			$('#main-content > div[data-contentid="' + tabid + '"]').html(
					_.template(zfyylTpl));

			var self = this;
			self.pageQuery();
			/** 查询事件 */
			$("#zfyyl_tb_queryBtn").click(function() {
				self.pageQuery();
			});

			/* 下拉列表 */
			$("#zfyyl_tb_dzjg").selectList({
				borderWidth : 1,
				borderColor : '#CCC',
				options : [ {
					name : '所有',
					value : '',
					selected : true
				}, {
					name : '平衡',
					value : '0' 
				}, {
					name : '不平衡',
					value : '1'
				} ]
			}).change(function(e) {
				console.log($(this).val());
			});
			/* end */

			/* 日期控件 */
			$("#zfyyl_beginDate").datepicker({
				dateFormat : 'yy-mm-dd'
			});
			$("#zfyyl_endDate").datepicker({
				dateFormat : 'yy-mm-dd'
			});
			/* end */
		},
		/** 分页查询 */
		pageQuery : function() {
			var self = this;
			var type=$(".selectList-active").attr("data-Value");
			// 查询参数
			var queryParam = {
				"beginDate" : $("#zfyyl_beginDate").val(), 
				"endDate" : $("#zfyyl_endDate").val(), 
				"tcResult" : type!=undefined?type:""
			};
			accountResultDat.payAndBankList("zfyyl_ql", queryParam, function(
					record, rowIndex, colIndex, options) {
				
				if(colIndex == 9){
					return comm.getNameByEnumId(record['tcResult'], {0:'平衡',1:'不平衡'});
				}
				
			}.bind(self));
		}
	};
});
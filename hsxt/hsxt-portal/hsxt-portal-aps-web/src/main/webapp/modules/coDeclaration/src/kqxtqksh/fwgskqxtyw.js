define(['text!coDeclarationTpl/kqxtqksh/fwgskqxtyw.html',
        'coDeclarationDat/kqxtqksh/kqxtqksh',
        'coDeclarationLan'], function(fwgskqxtywTpl, dataModoule){
	return{
		showPage : function(){
			this.initForm();
		},
		/**
		 * 初始化页面
		 */
		initForm : function(){
			var self = this;
			$('#busibox').empty().html(_.template(fwgskqxtywTpl));
			//时间控件		    
		    comm.initBeginEndTime('#search_startDate','#search_endDate');
			//绑定查询事件
			$('#queryBtn').click(function(){
				self.initData();
			});
			comm.initSelect("#quickDate",comm.lang("common").quickDateEnum);
			$("#quickDate").change(function(){
				comm.quickDateChange($(this).attr("optionvalue"),'#search_startDate', '#search_endDate');
			});
		},
		/**
		 * 初始化数据
		 */
		initData : function(){
			if(!comm.queryDateVaild("search_form").form()){
				return;
			}
			var jsonParam = {
					search_resNo:$("#search_resNo").val(),
					search_entName:$("#search_entName").val(),
					search_startDate:$("#search_startDate").val(),
					search_endDate:$("#search_endDate").val(),
					search_custType:4,
				};
			cacheUtil.findProvCity();
			dataModoule.findOpenSysList(jsonParam, this.detail);
		},
		/**
		 * 查看动作
		 */
		detail : function(record, rowIndex, colIndex, options){
			if(colIndex == 4){
				var result = comm.getRegionByCode(record['countryNo'], record['provinceNo'], record['cityNo']);
				return "<span title='"+result+"'>" + result + "</span>";
			}
			return  $('<a id="'+ record['applyId'] +'" >欠款审核</a>').click(function(e) {
				$("#applyId").val(record['applyId']);
				$("#custType").val("4");
				$("#menuName").val("fwgskqxtyw");
				require(['coDeclarationSrc/kqxtqksh/sub_tab'],function(tab){
					tab.showPage();
				});	
			}.bind(this));
		}
	}	
});
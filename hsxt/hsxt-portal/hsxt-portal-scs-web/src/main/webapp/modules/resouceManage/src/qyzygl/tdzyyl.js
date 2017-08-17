define(['text!resouceManageTpl/qyzygl/tdzyyl.html',
        "resouceManageDat/resouceManage",
        "resouceManageLan"],function(tdzyylTpl, dataModoule){
	return {	 	
		showPage : function(){
			this.initForm();
		},
		/**
		 * 初始化界面
		 */
		initForm : function(){
			var self = this;
			$('#busibox').html(_.template(tdzyylTpl));
			
			comm.initProvSelect('#search_provinceCode', {}, 80, null);
			comm.initCitySelect('#search_cityCode', {}, 80, null);
			
			//初始化省份
			cacheUtil.findCacheSystemInfo(function(sysRes){
				cacheUtil.findCacheProvinceByParent(sysRes.countryNo, function(provArray){
					comm.initProvSelect('#search_provinceCode', provArray, 80, null, {name:'', value:''}).change(function(e){
						cacheUtil.findCacheCityByParent(sysRes.countryNo, $(this).attr('optionValue'), function(cityArray){
							comm.initCitySelect('#search_cityCode', cityArray, 80, "", {name:'', value:''});
						});
					});
				});
			});
			
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
			params.search_applyEntResNo=comm.getRequestParams().hsResNo;
			params.search_provinceNo = $("#search_provinceCode").attr('optionvalue');
			params.search_cityNo = $("#search_cityCode").attr('optionvalue');
			params.search_linkman = $("#search_linkman").val();
			params.search_entName = $("#search_entName").val();
			params.search_entResNo = $("#search_entResNo").val();
			params.search_custType = 4;
			cacheUtil.findProvCity();
			dataModoule.findQualMainList("tableDetail",params, this.detail);
		},
		/**
		 * 自定义函数
		 */
		detail : function(record, rowIndex, colIndex, options){
			//列：地区
			if(colIndex == 5){
				return comm.getRegionByCode(record['countryNo'], record['provinceNo'], record['cityNo']);
			}
			//列：实名认证状态
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

define(['text!infoManageTpl/zyml/xtdjxx.html',
        'infoManageDat/infoManage',
		'infoManageLan'], function(xtdjxxTpl, dataModoule){
	return {
		showPage : function(num, record, custID){
			this.initData(custID, num);
		},
		/**
		 * 初始化界面
		 */
		initForm : function(data, num){
			data.num = num;
			if(num == "tgqy" || num == "cyqy"){
				data.viewText = "启用资源类型";
				data.viewName = comm.getNameByEnumId(data.startResType, comm.lang("common").allToBuyResRangeEnum);
			}else if(num == "fwgs"){
				data.viewText = "经营类型";
				data.viewName = comm.getNameByEnumId(data.businessType, comm.lang("common").toBusinessTypes);
			}
			$('#infobox').html(_.template(xtdjxxTpl, data));
			$('#back_tgqyzyyl').click(function(){
				comm.goBackListPage("busibox2","busibox",$('#tgqyzyyl'),'#ckqyxxxx, #ck');
			});
			$('#back_cyqyzyyl').click(function(){
				comm.goBackListPage("busibox2","busibox",$('#cyqyzyyl'),'#ckqyxxxx, #ck');
			});
			$('#back_fwgszyyl').click(function(){
				comm.goBackListPage("busibox2","busibox",$('#fwgszyyl'),'#ckqyxxxx, #ck');
			});
			//获取地区信息
			cacheUtil.syncGetRegionByCode(null, data.provinceNo, data.cityNo, "", function(resdata){
				$("#placeName").html(resdata);
			});
		},
		/**
		 * 初始化数据
		 */
		initData : function(custID, num){
			var self = this;
			var entAllInfo = comm.getCache("infoManage", "entAllInfo");
			if(entAllInfo == null){
				dataModoule.resourceFindEntAllInfo({custID:custID}, function(res){
					comm.setCache("infoManage", "entAllInfo", res.data);
					self.initForm(res.data.entInfo, num);
				});
			}else{
				self.initForm(entAllInfo.entInfo, num);
			}
		}
	}	
});
define(['text!infoManageTpl/zyzhgl/xtdjxx.html',
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
			$('#infobox').html(_.template(xtdjxxTpl, data));
			
			$('#back_tgqyzy').click(function(){
				comm.goBackListPage("ent_list","busibox",$('#tgqyzy'),'#ckqyxxxx, #ck');
			});
			$('#back_cyqyzy').click(function(){
				comm.goBackListPage("ent_list","busibox",$('#cyqyzy'),'#ckqyxxxx, #ck');
			});
			$('#back_fwgszy').click(function(){
				comm.goBackListPage("ent_list","busibox",$('#fwgszy'),'#ckqyxxxx, #ck');
				
			});
			//获取地区信息
			cacheUtil.syncGetRegionByCode(null, data.provinceNo, data.cityNo, "", function(resdata){
				$("#placeName").html(resdata);
			});
			if(num == "tgqy" || num == "cyqy"){
				$("#viewText").html("启用资源类型");
				$("#viewName").html(comm.getNameByEnumId(data.startResType, comm.lang("common").allToBuyResRangeEnum));
			}else{
				$("#viewText").html("经营类型");
				$("#viewName").html(comm.getNameByEnumId(data.businessType, comm.lang("common").toBusinessTypes));
			}
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
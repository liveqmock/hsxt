define(['text!coDeclarationTpl/qysbfk/point_choose_dialog.html',
        'coDeclarationDat/qysbfk/point_choose',
        'coDeclarationLan'], function(point_choose_dialog, dataModoule){
	return {
		self : null,
		gridObj : null,
		/**
		 * 查询服务公司可用互生号
		 * @param countryNo 国家代码
		 * @param prov 省份代码
		 * @param city 城市代码
		 */
		findServicesPointList : function(countryNo, prov, city, objId){
			self = this;
			if(comm.removeNull(countryNo) == ""){
				comm.error_alert(comm.lang("coDeclaration").findPointNoCountry);
				return;
			}
			if(comm.removeNull(prov) == ""){
				comm.error_alert(comm.lang("coDeclaration").findPointNoProv);
				return;
			}
			if(comm.removeNull(city) == ""){
				comm.error_alert(comm.lang("coDeclaration").findPointNoCity);
				return;
			}
			$('#point-choose-dialog').html(point_choose_dialog);
			$("#point-choose-dialog").dialog({
				title:"拟用企业互生号选择",
				width:"820",
				top:"200",
				modal:true,
				closeIcon : true,
				buttons:{ 
					"确定":function(){
						var entResNo = $('#nyqyhsh_cyqyForm').serializeJson().entResNo;
						if(objId){
							$(objId).val(entResNo);
							comm.setCache("coDeclaration", "pickResNo",entResNo);
						}
						$(this).dialog("destroy");
					},
					"取消":function(){
						 $(this).dialog("destroy");
					}
				}
			});
			
			//查询
			$('#res-query-btn').click(function(){
				self.queryServices(countryNo, prov, city);
			});
			
			self.queryServices(countryNo, prov, city);
		},
		/**
		 * 查询服务公司可用互生号
		 */
		queryServices : function(countryNo, prov, city){
			var params = {};
			params.search_countryNo = countryNo;
			params.search_provinceNo = prov;
			params.search_cityNo = city;
			params.search_resNo = $("#search_resNo").val();
			gridObj = dataModoule.findServicesPointList(params, this.detail, this.selectRowEvent);
		},
		/**
		 * 查看备注
		 */
		detail : function(record, rowIndex, colIndex, options){
			if(colIndex == 0){
				return '<input type="radio" name="entResNo" value="' + record['entResNo'] + '" />';
			}
			return (options.curPage-1)*options.settings.pageSize+rowIndex+1;
		},
		/**
		 * 行选中事件
		 */
		selectRowEvent: function (record, rowIndex, trObj, options) {
			trObj.find('input[name="entResNo"]').attr('checked', 'checked');
		}
	}
}); 

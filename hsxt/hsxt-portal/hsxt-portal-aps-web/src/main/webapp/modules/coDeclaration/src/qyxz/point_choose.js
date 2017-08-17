define(['text!coDeclarationTpl/qyxz/point_choose_dialog.html',
        'coDeclarationDat/qyxz/point_choose',
        'coDeclarationLan'], function(point_choose_dialog, dataModoule){
	return {
		self : null,
		gridObj : null,
		/**
		 * 查询托管企业、成员企业可用互生号
		 * @param serResNo 服务公司互生号
		 * @param custType 申报类别
		 * @param buyResRange 启用资源类型
		 */
		findMemberPointList : function(serResNo, custType, buyResRange, objId){
			self = this;
			if(comm.removeNull(serResNo) == ""){
				comm.error_alert(comm.lang("coDeclaration").findPointNoSerResNo);
				return;
			}
			if(comm.removeNull(custType) == ""){
				comm.error_alert(comm.lang("coDeclaration").findPointNoCustType);
				return;
			}
			if(comm.removeNull(buyResRange) == ""){
				comm.error_alert(comm.lang("coDeclaration").findPointNoBuyRes);
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
						}
						$( this ).hide();
						$( this ).dialog("destroy");
					},
					"取消":function(){
						$( this ).hide();
						$( this ).dialog("destroy");
					}
				}
			});
			
			//查询
			$('#queryBtn').click(function(){
				self.queryMember(serResNo, custType, buyResRange);
			});
			
			self.queryMember(serResNo, custType, buyResRange);
		},
		/**
		 * 查询成员启用、托管启用可用互生号
		 */
		queryMember : function(serResNo, custType, buyResRange){
			var params = {};
			params.search_serResNo = serResNo;
			params.search_custType = custType;
			params.search_resType = buyResRange;
			params.search_resNo = $("#search_resNo").val();
			gridObj = dataModoule.findMemberPointList(params, this.detail, this.selectRowEvent);
		},
		/**
		 * 查询服务公司可用互生号
		 * @param prov 省份代码
		 * @param city 城市代码
		 */
		findServicesPointList : function(prov, city){
			self = this;
			//获取所属国家
			cacheUtil.findCacheSystemInfo(function(sysRes){
				var countryNo = sysRes.countryNo;
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
							}
							$(this).dialog("destroy");
						},
						"取消":function(){
							 $(this).dialog("destroy");
						}
					}
				});
				
				//查询
				$('#queryBtn').click(function(){
					self.queryServices(countryNo, prov, city);
				});
				
				self.queryServices(countryNo, prov, city);
			});
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

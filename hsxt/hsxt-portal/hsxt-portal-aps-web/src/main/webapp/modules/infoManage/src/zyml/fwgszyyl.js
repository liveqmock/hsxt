define(['text!infoManageTpl/zyml/fwgszyyl.html'], function(fwgszyylTpl){
	var self= {
		//self : null,
		showPage : function(){
			//self = this;
			$('#busibox').html(_.template(fwgszyylTpl));
	
			/*日期控件*/
			comm.initBeginEndTime('#search_startDate','#search_endDate');
			
			var gridObj;
			
			//导出
			$("#btn_export").click(function(){
				if(!comm.queryDateVaild("search_form").form()){
					return;
				}
				
				var param="?";
//				var custId = comm.getRequestParams()["custId"];
//				var token = comm.getRequestParams()["token"];
//				var channelType = "1";
//				param = param + "custId="+custId+"&";
//				param = param + "token="+token+"&";
//				param = param + "channelType="+channelType+"&";
				//查询条件
				var conditonParam=self.queryParam();
				for(var key in conditonParam){
					param+=key+"="+conditonParam[key]+"&";
				};
				
				window.open(comm.domainList["apsWeb"]+comm.UrlList["ent_resource_dowload"]+param);    
			});
			
			$("#searchBtn").click(function(){
				if(!comm.queryDateVaild("search_form").form()){
					return;
				}
				var params = self.queryParam();
//				gridObj = comm.getCommBsGrid("searchTable","resourcedirect_list",params,comm.lang("infoManage"),self.detail,self.add);
				gridObj = comm.getCommBsGrid("searchTable","findReportResEntInfoList",params,comm.lang("infoManage"),self.detail,self.add);
				
			});
		},
		detail : function(record, rowIndex, colIndex, options){
			if(colIndex == 0)
			{
				
				var link1 = $('<a>'+ record.hsResNo +'</a>').click(function(e) {
					comm.delCache("infoManage", "entAllInfo");
					self.ckqyxxxx(record, record.custId);
				} ) ;	
				
				return link1;
			}
			
			if(colIndex == 6)
			{
				return comm.lang("infoManage").businessTypeEnum[record.businessType];
			}
			if(colIndex == 7)
			{
				return comm.lang("infoManage").realNameAuthSatus[record.realnameAuth];
			}
			if(colIndex == 8)
			{
				return comm.lang("infoManage").resFeeChargeModeEnum[record.resFeeChargeMode];
			}
		},
		ckqyxxxx : function(record, custID){
			require(['infoManageSrc/zyml/sub_tab'], function(tab){
				tab.showPage('fwgs', null, custID);
			});
			comm.liActive_add($('#ckqyxxxx'));	
			
		},
		//查询参数
		queryParam:function(){
			var loginParm=comm.getRequestParams();
			var param=comm.getQueryParams();
			param.search_openDateBegin = comm.navNull($("#search_startDate").val());			//系统开启开始时间
			param.search_openDateEnd = comm.navNull($("#search_endDate").val());				//系统开启结束时间
			param.search_entResNo = loginParm.entResNo;			//企业互生号
			param.search_blongEntCustType = '4';			    //隶属企业的客户类型 隶属的托管企业(3)、成员企业(2)、管理公司(5)、服务公司(4)
			param.search_belongEntResNo = $("#belongEntResNo").val();		//隶属企业的互生号
			param.search_belongEntName = $("#belongEntName").val();			//隶属企业的名称
			param.search_belongEntContacts = $("#belongEntContacts").val();	//隶属企业的联系人
			return param;
		}
	};
	return self;
});
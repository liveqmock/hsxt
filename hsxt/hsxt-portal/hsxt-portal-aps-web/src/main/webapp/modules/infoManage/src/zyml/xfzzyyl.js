define(['text!infoManageTpl/zyml/xfzzyyl.html',
        'text!infoManageTpl/zyml/xfzzyyl_ck.html',
        'infoManageDat/infoManage'
        ], function(xfzzyylTpl, xfzzyyl_ckTpl,infoManage){

	var self= {
		showPage : function(){
			$('#busibox').html(_.template(xfzzyylTpl));
			//var self=this;
			var welfareType=$(".selectList-active").attr("data-Value");
			comm.initSelect('#xfzzyyl_zt', comm.lang("infoManage").custRealNameAuthSatus, null, null, {name:'全部', value:''});
			
			//时间控件		    
		    comm.initBeginEndTime('#search_startDate','#search_endDate');
						
			cacheUtil.findCacheContryAll(function(data){
				//self.query();
			});
			$('#btn_query_xfzzyyl').click(function(e){
				self.query();
			});
			
			//导出
			$("#btn_export").click(function(){
//				if(!comm.queryDateVaild("search_form").form()){
//					return;
//				}
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
				
				window.open(comm.domainList["apsWeb"]+comm.UrlList["person_resource_dowload"]+param);    
			});
		},
		//查询参数函数
		queryParam:function(){
			var param=comm.getQueryParams();
			param.search_perResNo=$.trim($('#xfzzyyl_res_no').val());
			param.search_status=comm.removeNull($("#xfzzyyl_zt").attr('optionvalue'));
			param.search_startDate=$("#search_startDate").val();
			param.search_endDate=$("#search_endDate").val();
			param.search_realName=$.trim($('#xfzzyyl_real_name').val());
			return param;
		},
		query : function(){
			//var self=this;
			if(!self.validDate()){
				return;
			}
			//查询参数
			var queryParam=self.queryParam();
			
			var gridObj= infoManage.listConsumerInfo("searchTable",queryParam,function(record, rowIndex, colIndex, options){
				if(colIndex == 0){
					return $('<a>'+record.hsResNo+'</a>').click(function(e) {
						self.chaKan(record);
					});
				}else if(colIndex == 2){//证件类型
					return comm.getNameByEnumId(record['idType'], comm.lang("infoManage").realNameCreType);
				}else if(colIndex == 5){//消费者状态
					return comm.getNameByEnumId(record['realnameAuth'], comm.lang("infoManage").custRealNameAuthSatus);
				}else if(colIndex == 6){//状态日期
					if(record.realnameAuthDate!=null&&record.realnameAuthDate!=''){
						return record.realnameAuthDate;
					}
					if(record.realnameRegDate!=null&&record.realnameRegDate!=''){
						return record.realnameRegDate;
					}
					return '';
				}else if(colIndex == 7){//互生卡状态
					return comm.getNameByEnumId(record['baseStatus'], comm.lang("infoManage").HSCardStatusEnum);
				}
			});
		},
		validDate:function(){
			var realnameStatus = $("#xfzzyyl_zt").attr('optionValue');
			var startDate = $("#search_startDate").val();
			var endDate = $("#search_endDate").val();
			if(undefined == realnameStatus || '' == realnameStatus){
				if('' != startDate || '' != endDate){
					comm.warn_alert("消费者状态为【空】或【全部】不能选择日期进行查询");
					return false;
				}
			}
			if(1 == realnameStatus || 2 == realnameStatus || 3 == realnameStatus){
				if('' == startDate && '' != endDate){
					comm.warn_alert("开始日期不能为空");
					return false;
				}
				if('' != startDate && '' == endDate){
					comm.warn_alert("结束日期不能为空");	
					return false;
				}
			}
			return true;
		},
		chaKan : function(obj){
			infoManage.queryConsumerAllInfo({'custID':obj.custId},function(res){
				require(['infoManageSrc/zyml/sub_tab_p'], function(tab){
					$("#menuId").val("#xfzzyyl");
					tab.showPage('xfz', res.data);
				});
				comm.liActive_add($('#ck'));
			});
		}
	};
	return self;
});
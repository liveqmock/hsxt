define(function () {
	return {
		//获取三级区域申请明细
		/*getCityApplyDetail:function(data,callback){
			data.custId = "1005";//$.cookie('custId');
			data.pointNo = "1005";//$.cookie('pointNo');
			data.token = "1005";//$.cookie('token');
			data.custName = "1005";//$.cookie('custName');
			comm.Request([{url:"getEntResDetail",domain:"apsWeb"},{url:"cityQuotaApplyPage",domain:"apsWeb"},{url:"cityQuotaDetail",domain:"apsWeb"}],{
				type:"POST",     //默认值，可省
				datatype: "json",  //默认值，可省
				data : data , //若不用上传参数，可省略
				async:true ,    //默认值true,若需同步改为false即可【不建议使用同步】
				success :callback	
			});
		}*/
		//获取地区平台下的管理公司
		getEntResList:function(data, callback){
			comm.requestFun("getEntResList",data,callback,comm.lang("zypeManage"));
		},
		//获取管理公司详情
		getEntResDetail:function(data, callback){
			comm.requestFun("getEntResDetail",data,callback,comm.lang("zypeManage"));
		},
		//管理公司配额分配详情
		manageAllotDetail:function(data, callback){
			comm.requestFun("manageAllotDetail",data,callback,comm.lang("zypeManage"));
		},
		//城市资源状态详情
		cityResStatusDetail:function(data, callback){
			comm.requestFun("cityResStatusDetail",data,callback,comm.lang("zypeManage"));
		},
		
		//数据资源一览表
		listTable:function(data, callback){
			comm.requestFun("listTable",data,callback,comm.lang("zypeManage"));
		},
		//资源配额查询
		resQuotaQuery:function(data, callback){
			comm.requestFun("resQuotaQuery",data,callback,comm.lang("zypeManage"));
		},
		
		//一级区域配额申请分页查询
		applyPlatQuotaPage:function(data, callback){
			comm.requestFun("applyPlatQuotaPage",data,callback,comm.lang("zypeManage"));
		},
		//一级区域配额申请查询分页查询
		applyPlatQuotaQueryPage:function(gridId, params,detail){
			return comm.getCommBsGrid(gridId,"applyPlatQuotaQueryPage",params,comm.lang("zypeManage"),detail);
		},
		//查询一级区域(地区平台)配额分配详情
		applyPlatQuotaDetail:function(data, callback){
			comm.requestFun("applyPlatQuotaDetail",data,callback,comm.lang("zypeManage"));
		},
		//查询管理公司可以进行分配或调整配额的二级区域
		getProvinceNoAllot:function(data, callback){
			comm.requestFun("getProvinceNoAllot",data,callback,comm.lang("zypeManage"));
		},
		//二级区域配额配置查询
		provinceQuotaPage:function(gridId, params,detail){
			return comm.getCommBsGrid(gridId,"provinceQuotaPage",params,comm.lang("zypeManage"),detail);
		},
		//二级区域配额配置详情
		provinceQuotaDetail:function(data, callback){
			comm.requestFun("provinceQuotaDetail",data,callback,comm.lang("zypeManage"));
		},
		//   
		//查询地区平台分配了配额的省
		allocatedQuotaProvinceQuery:function(data, callback){
			comm.requestFun("allocatedQuotaProvinceQuery",data,callback,comm.lang("zypeManage"));
		},
		//三级区域配额配置申请分页查询
		cityQuotaApplyPage:function(data, callback){
			comm.requestFun("cityQuotaApplyPage",data,callback,comm.lang("zypeManage"));
		},
		//三级区域配额配置查询分页查询
		cityQuotaApplyQeryPage:function(gridId, params,detail){
			return comm.getCommBsGrid(gridId,"cityQuotaApplyQeryPage",params,comm.lang("zypeManage"),detail);
		},
		//三级区域配额配置详情
		cityQuotaDetail:function(data, callback){
			comm.requestFun("cityQuotaDetail",data,callback,comm.lang("zypeManage"));
		},
		//一级区域配额申请
		applyPlatQuota:function(data, callback){
			comm.requestFun("applyPlatQuota",data,callback,comm.lang("zypeManage"));
		},
		//二级区域配额申请
		provinceQuotaApply:function(data, callback){
			comm.requestFun("provinceQuotaApply",data,callback,comm.lang("zypeManage"));
		},
		//二级区域配额审批
		provinceQuotaApprove:function(data, callback){
			comm.requestFun("provinceQuotaApprove",data,callback,comm.lang("zypeManage"));
		},
		//三级区域配额修改
		cityQuotaUpdate:function(data, callback){
			comm.requestFun("cityQuotaUpdate",data,callback,comm.lang("zypeManage"));
		},
		//三级区域配额初始化
		cityQuotaInit:function(data, callback){
			comm.requestFun("cityQuotaInit",data,callback,comm.lang("zypeManage"));
		},
		//三级区域配额审批
		cityQuotaApprove:function(data, callback){
			comm.requestFun("cityQuotaApprove",data,callback,comm.lang("zypeManage"));
		},
		//统计城市配额分配使用情况
		statQuotaByCity:function(data, callback){
			comm.requestFun("statQuotaByCity",data,callback,comm.lang("zypeManage"));
		}
	};
});
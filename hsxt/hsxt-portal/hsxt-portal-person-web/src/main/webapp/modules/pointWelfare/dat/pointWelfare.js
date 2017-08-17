define(function () {
	return {
		
		//获得参数列表
		getParameters : function (paraData){
			
			//json用户身份参数构造
			var jsonParam={
					custName:"张三",						//用户名
					custId 	:"0618601000620151130",		//客户号
					hsResNo :"06186010006", 			//互生号
					token 	:"bdc6f862cf491d02"			//随机token
			};
			
			//传递过来的参数继承JsonParam
			if(typeof(paraData) != "undefined" && paraData != null )
			{
				 return $.extend(jsonParam,paraData);
			}
			else 
			{
				return jsonParam;
			}
			
		},	
		
		queryConsumerInfo : function (data,callback) {
			var jsonParam = this.getParameters(data);
			comm.requestFun("queryConsumerInfo" , jsonParam, callback,comm.lang("myHsCard"));
		},
		
		
		//1.查询积分福利资格
		findWelfareQualify : function (data, callback) {
			var jsonParam = this.getParameters(data);
			comm.requestFun("findWelfareQualify" , jsonParam, callback,comm.lang("pointWelfare"));
		},
		
		//2.意外伤害保障申请
		applyAccidentSecurity : function (data, callback) {
			var jsonParam = this.getParameters(data);
			comm.requestFun("applyAccidentSecurity" , jsonParam, callback,comm.lang("pointWelfare"));
		},
		//3.互生医疗补贴计划申请
		applyMedicalSubsidies : function (data, callback) {
			var jsonParam = this.getParameters(data);
			comm.requestFun("applyMedicalSubsidies" , jsonParam, callback,comm.lang("pointWelfare"));
		},
		//4.代他人申请死亡保障金
		applyDieSecurity : function (data, callback) {
			var jsonParam = this.getParameters(data);
			comm.requestFunForHandFail("applyDieSecurity" , jsonParam, callback,comm.lang("pointWelfare"));
		},
		//5.积分福利申请明细查询
		listWelfareApply : function(params, detail){
			comm.getCommBsGrid("welfareTable", "listWelfareApply", params, comm.lang("pointWelfare"), detail);
		},
		// 6.积分福利申请详单查询
		queryWelfareApplyDetail : function (data, callback) {
			var jsonParam = this.getParameters(data);
			comm.requestFun("queryWelfareApplyDetail" , jsonParam, callback,comm.lang("myHsCard"));
		},
		//获取绑定的手机号码
		findMobileByCustId : function (jsonParam, callback) {
			comm.requestFun("findMobileByCustId" , jsonParam, callback,comm.lang("myHsCard"));
		}
		
	};
});

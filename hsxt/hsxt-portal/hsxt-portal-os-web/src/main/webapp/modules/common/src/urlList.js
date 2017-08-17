define(function() { 
		comm.UrlList= {
				
				// 上传文件
				"fileupload" : "fileController/fileUpload",
				//保存文件到BP系统中
				"saveFile":"posUpgradeController/addPosUpgradeVerInfo",
				//分页查询POS机升级信息列表
				"searchPosUpPage":"posUpgradeController/searchPosUpPage",
				//修改POS机升级信息
				"updatePosUpInfo":"posUpgradeController/updatePosUpInfo",
				//生成CRC码
				"createCrcCode":"posUpgradeController/createCrcCode",
				// 业务参数模块 begin
				//系统参数组添加
				"xtcszAdd" : "sysParamGroupController/addSysParamGroup",
					
				//系统参数组查询
				"xtcszSearch" : "sysParamGroupController/searchSysParamGroup",
				
				//系统参数组删除
				"xtcszDelete" : "sysParamGroupController/deleteSysParamGroup",
					
				//系统参数组修改
				"xtcszUpdate" : "sysParamGroupController/updateSysParamGroup",
					
				//系统参数项添加
				"xtcsxAdd" : "sysParamItemsController/addSysParamItems",
					
				//系统参数项查询
				"xtcsxSearch" : "sysParamItemsController/searchSysParamItems",
				
				//系统参数项删除
				"xtcsxDelete" : "sysParamItemsController/deleteSysParamItems",
					
				//系统参数项修改
				"xtcsxUpdate" : "sysParamItemsController/updateSysParamItems",
			    
				//协议管理添加
				"xyglAdd" : "agreeManageController/addAgreeManage",
					
				//协议管理查询
				"xyglSearch" : "agreeManageController/searchAgreeManage",
				
				//协议管理删除
				"xyglDelete" : "agreeManageController/deleteAgreeManage",
					
				//协议管理修改
				"xyglUpdate" : "agreeManageController/updateAgreeManage",
				
				//协议费用管理添加
				"xyfyglAdd" : "agreeFeeManageController/addAgreeFeeManage",
					
				//协议费用管理查询
				"xyfyglSearch" : "agreeFeeManageController/searchAgreeFeeManage",
				
				//协议费用管理删除
				"xyfyglDelete" : "agreeFeeManageController/deleteAgreeFeeManage",
					
				//协议费用管理修改
				"xyfyglUpdate" : "agreeFeeManageController/updateAgreeFeeManage",
					
				//客户参数管理添加
				"khcsglAdd" : "businessCustParamController/addBusinessCustParam",
					
				//客户参数管理查询
				"khcsglSearch" : "businessCustParamController/searchBusinessCustParam",
				
				//客户参数管理删除
				"khcsglDelete" : "businessCustParamController/deleteBusinessCustParam",
					
				//客户参数管理修改
				"khcsglUpdate" : "businessCustParamController/updateBusinessCustParam",
				// 业务参数模块  end
				
				// 权限模块begin
				// 系统操作员 管理 begin
				// 查询
				"authOperatorList" : "operator/listOper",
				// 新增
				"authOperatorAdd" : "operator/addOper",
				// 修改
				"authOperatorUpdate" : "operator/updateOper",
				// 列出可授权角色
				"authOperatorListRole" : "operator/listRole",
				// 角色授权
				"authOperatorSetRole" : "operator/setRole",
				// 系统操作员 管理  end
				
				
				//权限信息管理
				"qxxxglSearch" : "permission/listPermByPage",
				"qxxxglAdd" : "permission/addPerm",
				"qxxxglUpdate" : "permission/updatePerm",
				//权限模块end
				
				//角色信息管理
				"jsxxglSearch" : "role/listRoleByPage",
				"jsxxglUpdate" : "role/updateRole",
				"jsxxglAdd" : "role/addRole",
				"jsxxglListPerm" : "role/listPerm",
				"jsxxglSetPerm" : "role/setPerm",
				
				//角色信息管理 end
				
				// 对账中心-对账结果查询开始
				"payAndBankList":"accountResult/listPayAndBank",
				"payAndBusinessList":"accountResult/listPayAndBusiness",
				"businessAndAcList":"accountResult/listBusinessAndAc",
				"pvAndAcList":"accountResult/listPvAndAc",
				// 对账中心-对账结果查询结束
				
				// 调账-开始
				"getCheckBalances":"checkBalance/getCheckBalances",
				"getCheckBalanceDetail":"checkBalance/getCheckBalanceDetail",
				"getCheckBalanceResults":"checkBalance/getCheckBalanceResults",
				"addCheckBalanceResult":"checkBalance/addCheckBalanceResult",
				"addCheckBalance":"checkBalance/addCheckBalance",
				"getUsernameByResNo":"checkBalance/getUsernameByResNo",
				"getBalance":"checkBalance/getBalance",
				// 调账-结束
				
				//投资分红-开始
				"getInvestDividend":"investDividend/getHisInvestDividendList",
				"addDividendRate":"investDividend/addYearDividendRate",
				//投资分红-结束
				
				//login
				"getToken" : "login/getRandomToken",
				"getCheckCode": "login/getCheckCode",
				"isLogin":"login/isLogin",
				"login":"login/login",
				"END" : ""
		};
		return comm.UrlList;
});
define(function() { 
	comm.UrlList= {
		
		/**
		 * 地区平台信息维护
		 */
		//地区平台信息维护
		"platList":"plat/platList",
		//查询未初始化的地区平台信息
		"unInitPlatList":"plat/unInitPlatInfo",
		//添加平台公司信息
		"addPlatInfo":"plat/addPlatInfo",
		//修改平台公司信息
		"updatePlatInfo":"plat/updatePlatInfo",
		//同步平台公司信息到用户中心
		"syncPlatToUc":"plat/syncPlatToUc",
		//查询所有平台
		"platListAll":"plat/platListAll",
		
		/**
		 * 管理公司信息维护
		 */
		//查询管理公司信息
		"manageEntList":"manage/manageEntList",
		//添加管理公司及其与平台关系
		"addPlatMent":"manage/addPlatMent",
		//同步管理公司信息到用户中心
		"syncManageToUc":"manage/syncManageToUc",
		//同步管理公司信息到业务系统
		"syncManageToBs":"manage/syncManageToBs",
		
		
		/**
		 * 配额管理
		 */
		//查询管理公司配额统计
		"quotaStatOfMent":"quota/queryQuotaStatOfMent",
		//查询管理公司在地区平台配额统计
		"quotaStatOfPlat":"quota/queryQuotaStatOfPlat",
		//配额申请
		"applyQuota":"quota/applyQuota",
		//分页查询配额申请
		"queryQuotaApplyList":"quota/queryQuotaApplyList",
		//审批配额申请
		"apprQuotaApp":"quota/apprQuotaApp",
		//查询互生号占用情况
		"queryIdTyp":"quota/queryIdTyp",
		//查询申请配额
		"queryQuotaAppInfo":"quota/queryQuotaAppInfo",
		//同步配额数据到地区平台业务系统(BS)
		"syncQuotaAllot":"quota/syncQuotaAllot",
		//同步路由数据到总平台全局配置系统(GCS)
		"syncQuotaRoute":"quota/syncQuotaRoute",
		
		
		
        //绘制验证码
        "paintImage": "login/paintImage",
        //登录
        "signIn": "login/on",
        //校验令牌
        "checkToken": "login/check",
        //退出
        "signOut": "login/out",
        //获取菜单列表
        "queryMenuList":"menu/queryMenuList",
        //新增操作员
        "saveOperator": "operator/save",
        //修改操作员
        "editOperator": "operator/edit",
        //删除操作员
        "delOperator": "operator/delete",
        //操作员分页查询
        "queryOperatorListForPage": "operator/queryListForPage",
        //处理操作员角色关系列表
        "dealOperRoleList": "operator/dealOperRoleList",
        //修改登录密码
        "editLoginPassword": "operator/editLoginPassword",


        //新增角色
        "saveRole": "role/save",
        //修改角色
        "editRole": "role/edit",
        //删除角色
        "delRole": "role/delete",
        //角色分页查询
        "queryRoleListForPage": "role/queryListForPage",
        //根据角色查询授权菜单
        "queryMenuTree": "menu/queryMenuTree",
        //处理角色菜单关系列表
        "dealRoleMenuList": "role/dealRoleMenuList",


        //新增用户组
        "saveGroup": "group/save",
        //修改用户组
        "editGroup": "group/edit",
        //删除用户组
        "delGroup": "group/delete",
        //用户组分页查询
        "queryGroupListForPage": "group/queryListForPage",
        //用户组-组员分页查询
        "queryOperatorList": "group/queryOperatorList",
        //用户组-删除操作员关系
        "delOperatorId": "group/delOperatorId",
        //用户组-保存关联操作员
        "saveOperatorIds": "group/saveOperatorIds",
        //用户组-保存关联操作员
        "queryAllGroupList": "group/queryAllList",

        //查询增值节点位置
        "queryIncrementByResNo":"increment/queryIncrementByResNo",
        //增加增值节点
        "addIncrementNode":"increment/addIncrementNode",
        //查找节点的向上路径
        "queryParentListByResNo":"increment/queryParentListByResNo",
        //查找节点的向下路径
        "queryLongNodeByResNo":"increment/queryLongNodeByResNo",
        //条件查询互生积分分配详情列表
        "queryPointValueListPage":"pointValue/queryPointValueListPage",
        //条件查询再增值分配详情列表
        "queryBmlmListPage":"pointValue/queryBmlmListPage"

    };
    return comm.UrlList;
});
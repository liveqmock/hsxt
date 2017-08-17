define(["commSrc/comm"],function(){
	comm.langConfig['resourceManage'] ={
			realnameAuthEnum : {//实名状态:1：未实名注册、2：已实名注册（有名字和身份证）、3:已实名认证
				1 : '未实名注册',
				2 : '已实名注册',
				3 : '已实名认证'
			},
			baseStatusEnum : {//** 基本状态 1：不活跃、2：活跃、3：休眠、4：沉淀 **/
				1:'不活跃',
				2:'活跃',
				3:'休眠',
				4:'沉淀'
			}
		
	}
});
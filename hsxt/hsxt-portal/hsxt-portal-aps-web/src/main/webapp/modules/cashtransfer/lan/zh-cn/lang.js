define(["commSrc/comm"],function(){
	comm.langConfig['cashTransfer'] ={
			selectCommitMsg : '请选择要提交转账的数据',
			selectCancleMsg : '请选择要撤单的数据',
			43262 : '未查询到该交易流水号的待退款记录',
			15102 : '查询记录不存在',
			43304 :	'未查询到撤单记录', 
			commitType : {
				'1' : '单笔自动',
				'2' : '批量自动',
				'3' : '手工提交'
			},
			transStatusEnum : {
				1 : '申请中',
				2 : '付款中',
				3 : '已撤单',
				4 : '已冲正',
				5 : '银行退票',
				6 : '转账失败',
				7 : '转账退款完成',
				8 : '转账成功'
			},
			companyStateEnum : {
				1 : '消费者',
				2 : '成员企业',
				3 : '托管企业',
				4 : '服务公司',
				5 : '管理公司'
			},
			reconciliationStatus:{//对账状态
				0:"失败",
				1:"成功"
			}
	}
});
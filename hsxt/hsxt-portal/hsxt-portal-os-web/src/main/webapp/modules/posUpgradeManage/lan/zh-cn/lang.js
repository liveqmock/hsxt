define(["commSrc/comm"],function(){
	comm.langConfig["posUpgradeManage"] = {
			
			44001 : "POS机升级版本号重复，请修改版本号",
			uploadInfo : {
				name :　"文件命名格式不正确，请按提示修改命名文件",
				verson : "主版本号用6位数字命名",
				verson1 : "副主版本号用2位数字命名",
				selectType : "只能上传.so的文件类型，请重新选择！",
				select : "请选择POS机升级上传的文件",
				reSelect : "请重新选择POS机升级上传的文件",
				uploadSuccess : "上传并保存成功"
			},
			success:"修改成功",
			alertInfo:{
				required : "必填"
			},
			updateType:{
				'Y' : "是",
				'N' : "否"
			},
			mustUpType:{
				'Y' : "是",
				'N' : "否"
			},
			
			requestFailedWithCode :'未定义错误码'
		}
});

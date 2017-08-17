define(function () {
	return {
		//获得文件在文件系统服务器中的url
		getFsServerUrl : function(fileId) {
			var custId = 'custId_p25_2015_10_16'; 	// 读取 cookie 客户号
			var hsNo ='custId_p25_2015_10_16'; 	// 读取 cookie pointNo
			var token ='token_custId_p25_2015_10_16';
			
			return  comm.domainList['fsServerUrl']+fileId+"?userId="+custId+"&token="+token;
		},
		//发送消息(新加)
		sendMessage : function(data,callback){
			comm.requestFun("send_message",data,callback,comm.lang("messageCenter"));
		},
		//发送消息(修改)
		editMessage : function(data,callback){
			comm.requestFun("edit_message",data,callback,comm.lang("messageCenter"));
		},
		//查询消息详情
		findMessageById : function(data,callback){
			comm.requestFun("find_message_detail",data,callback,comm.lang("messageCenter"));
		},
		//删除消息
		delMessageById : function(data,callback){
			comm.requestFun("del_message",data,callback,comm.lang("messageCenter"));
		},
		//删除多条消息
		delMessages : function(data,callback){
			comm.requestFun("del_messages",data,callback,comm.lang("messageCenter"));
		}
	};
});
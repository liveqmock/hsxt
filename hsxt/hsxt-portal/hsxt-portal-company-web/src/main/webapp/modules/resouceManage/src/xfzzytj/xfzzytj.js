define(['text!resouceManageTpl/xfzzytj/xfzzytj.html','resouceManageLan'], function(tpl){
	return {
		showPage : function(){
			$('#busibox').html(tpl);
			
			var self = this,
			searchTable = null;
			
			var jsonParam =  {};
			// 查询资源统计
			comm.requestFun("resource_unify_search",jsonParam,function(response){
				var systemResourceUsageNumber = '0';
				var activationNumber = '0';
				var activeNumber = '0';
				var registrationAuthNumber = '0';
				var realnameAuthNumber = '0';
				$("#xtzysys").text(systemResourceUsageNumber);
				$("#jhhsks").text(activationNumber);
				$("#hyhsks").text(activeNumber);
				$("#smzcs").text(registrationAuthNumber);
				$("#smrzs").text(realnameAuthNumber);
					if(response.data){
						if(response.data[0]){
							if(response.data[0].systemResourceUsageNumber){
								systemResourceUsageNumber = response.data[0].systemResourceUsageNumber;
							}
							if(response.data[0].activationNumber){
								activationNumber = response.data[0].activationNumber;
							}
							if(response.data[0].activeNumber){
								activeNumber = response.data[0].activeNumber;
							}
							if(response.data[0].registrationAuthNumber){
								registrationAuthNumber = response.data[0].registrationAuthNumber;
							}
							if(response.data[0].realnameAuthNumber){
								realnameAuthNumber = response.data[0].realnameAuthNumber;
							}
						}
						$("#xtzysys").text(systemResourceUsageNumber);
						$("#jhhsks").text(activationNumber);
						$("#hyhsks").text(activeNumber);
						$("#smzcs").text(registrationAuthNumber);
						$("#smrzs").text(realnameAuthNumber);
					}
			},comm.lang("resourceManage"));
			
			self.queryPage();
			/*下拉列表*/
			$("#scpoint_consumer").selectList({
				options:[
					{name:'请选择',value:''},
					{name:'未实名注册',value:'1'},
					{name:'已实名注册',value:'2'},
					{name:'已实名认证',value:'3'}
				]	
			}).change(function(e){
				var v = $(this).attr('optionValue');
				
			});
			$("#scpoint_card").selectList({
				options:[
					{name:'请选择',value:''},
					{name:'不活跃',value:'1'},
					{name:'活跃',value:'2'},
					{name:'休眠',value:'3'},
					{name:'沉淀',value:'4'}
				]	
			}).change(function(e){
				var v = $(this).attr('optionValue');
			});
			/*日期控件*/
			$("#scpoint_beginDate").datepicker({dateFormat:'yy-mm-dd'});
			$("#scpoint_endDate").datepicker({dateFormat:'yy-mm-dd'});
			
			//查询单击事件
			$('#scpoint_searchBtn').click(function(){
				if (!self.validateData()) {
					return;
				}
				self.queryPage();
			});
		},
		
		
		
		queryPage : function(data){
			// 查询资源统计
/*			var obj = comm.getCommBsGrid("scpoint_result_table","consumer_resource_search",data,function(response){
			},comm.lang("resourceManage"));*/
			var data = {
					beginDate : $("#scpoint_beginDate").val(),
					endDate : $("#scpoint_endDate").val(),
					beginCard : $("#scpoint_beginCard").val(),
					endCard : $("#scpoint_endCard").val(),
					consumer : $("#scpoint_consumer").attr('optionvalue'),
					cardStatus : $("#scpoint_card").attr('optionvalue')
			}
			
			gridObj = comm.getCommBsGrid("scpoint_result_table","consumer_resource_search",data,this.detail);
		},
		
		detail : function(record, rowIndex, colIndex, options){
		    //处理显示
			if(colIndex == 1){
				return comm.lang("resourceManage").realnameAuthEnum[record.realnameAuth];
			} 
		    if(colIndex == 2){
				return comm.formatDate(record.realnameAuthDate);
			} 
		    if(colIndex == 3){
				return comm.lang("resourceManage").baseStatusEnum[record.baseStatus];
			} 
		},
		
		
		
		validateData : function(){
			return comm.valid({
				formID : '#scpoint_form',
				rules : {
					
				},
				messages : {
					
				}
			});
		}
	};
});
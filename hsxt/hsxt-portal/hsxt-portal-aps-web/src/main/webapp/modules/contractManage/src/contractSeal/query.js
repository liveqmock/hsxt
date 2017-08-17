define([
'text!contractManageTpl/contractSeal/query.html',
'contractManageDat/contract',
'text!contractManageTpl/contractSeal/seal.html',
'contractManageLan'
], 
function(queryTpl,contractAjax,htgz_dialogTpl){
	var gridObj = null;
	var self = {
		showPage : function(){
			$('#busibox').html(_.template(queryTpl));
			comm.initSelect('#sealStatus',comm.lang("contractManage").sealOpt, 114, null, {name:'全部', value:''},100);
			//查询
			$("#seal_contract_btn").click(function(){
				self.loadGrid();
			});
		},
		//加载表格
		loadGrid : function(){
			var params = {
					search_entResNo : $("#entResNo").val(),
					search_entCustName : $("#entCustName").val(),
					search_status : $("#sealStatus").attr("optionvalue")?$("#sealStatus").attr("optionvalue"):""
			};
			gridObj = comm.getCommBsGrid("seal_contract_table","find_seal_contarct_by_page",params,null,this.preView,this.seal);
		},
		//盖章
		seal : function(record, rowIndex, colIndex, options){
			if(colIndex == 1){
				return comm.lang("common").custType[record.custType];
			}else if(colIndex == 6){
				return comm.lang("contractManage").isSeal[record.sealStatus];
			}else if(colIndex == 7){
				
				var link;
				if(record.sealStatus==0){
					link = $('<a>盖章</a>').click(function(e) {
						var reqParam = {contractNo:record.contractNo,realTime:true};
						contractAjax.findContractContentBySeal(reqParam,function(res){
							res.data.contractNo = record.contractNo;
							self.gaiZhang(res.data);
						});
						
					});
					return link;
				}
				if(record.sealStatus == 1 || record.sealStatus == 3){
					link = $('<a>重新盖章</a>').click(function(e) {
						var reqParam = {contractNo:record.contractNo,realTime:true};
						contractAjax.findContractContentBySeal(reqParam,function(res){
							res.data.contractNo = record.contractNo;
							self.cxgz(res.data);
						});
					});
					return link;
				}
			}
		},
		preView:function(record, rowIndex, colIndex, options){
			var obj = gridObj.getRecord(rowIndex);
			if(colIndex == 7){
				var link = $('<a>预览</a>').click(function(e) {
						var reqParam = {contractNo:obj.contractNo};
						if(record.sealStatus == 1 || record.sealStatus == 3) {
							reqParam.realTime = false;
						}else{
							reqParam.realTime = true;
						}
						contractAjax.findContractContentBySeal(reqParam,function(res){
							res.data.contractNo = obj.contractNo;
							self.yuLan(res.data);
						});
						
				});
				return link;
			}
		},
		yuLan : function(obj){
			if(obj.sealStatus==1||obj.sealStatus==3){
				var content = obj.content;
				obj.content = content.replace('<div id="business">','<div id="business" class="pr"><div style="top:-15px;left: 58px;" class="pa"><img src="resources/img/contract.png"  draggable="false"></div>');
			}
			$('#dialogBox').html(_.template(htgz_dialogTpl,obj));	
			$('#cContent').css({"line-height":"1.5em","font-size":"16pt"});
			/*弹出框*/
			$( "#dialogBox" ).dialog({
				title:"合同预览",
				width:"1000",
				//height: "600",
				modal:true,
				closeIcon : true,
				buttons:{
					"关闭":function(){
						 $( this ).dialog( "destroy" );
					}
				}
			});
			
		},
		gaiZhang : function(obj){
			if(obj.sealStatus==1||obj.sealStatus==3){
				var content = obj.content;
				obj.content = content.replace('<div id="business">','<div id="business" class="pr"><div style="top:-15px;left: 58px;" class="pa"><img src="resources/img/contract.png"  draggable="false"></div>');
			}
			$('#dialogBox_gz').html(_.template(htgz_dialogTpl,obj));	
			$('#cContent').css({"line-height":"1.5em","font-size":"16pt"});	
				/*弹出框*/
				$( "#dialogBox_gz" ).dialog({
					title:"盖章",
					width:"1000",
					//height: "600",
					modal:true,
					closeIcon : true,
					buttons:{ 
					    "盖章":function(){
					    	var sealParam = {
					    			contractNo	:	obj.contractNo
					    	};
					    	contractAjax.sealContract(sealParam,function(res){
					    		$( '#dialogBox_gz' ).dialog( "destroy" );
					    		$("#seal_contract_btn").click();
					    	});
					    	
						},
						"关闭":function(){
							 $( this ).dialog( "destroy" );
						}
					 	
					}
				});
				
			},
			cxgz : function(obj){
				if(obj.sealStatus==1||obj.sealStatus==3){
					var content = obj.content;
					obj.content = content.replace('<div id="business">','<div id="business" class="pr"><div style="top:-15px;left: 58px;" class="pa"><img src="resources/img/contract.png"  draggable="false"></div>');
				}
				$('#dialogBox_cxgz').html(_.template(htgz_dialogTpl,obj));	
				$('#cContent').css({"line-height":"1.5em","font-size":"16pt"});
				/*弹出框*/
				$( "#dialogBox_cxgz" ).dialog({
					title:"重新盖章",
					width:"1000",
					//height: "600",
					modal:true,
					closeIcon : true,
					buttons:{ 
					    "重新盖章":function(){
					    	var reSealParam = {
					    			contractNo	:	obj.contractNo
					    	};
					    	contractAjax.sealContract(reSealParam,function(res){
					    		$( '#dialogBox_cxgz' ).dialog( "destroy" );
					    		$("#seal_contract_btn").click();
					    	});
						},
						"关闭":function(){
							 $( this ).dialog( "destroy" );
						}
					 	
					}
				});
			}
	};
	return self;	
});
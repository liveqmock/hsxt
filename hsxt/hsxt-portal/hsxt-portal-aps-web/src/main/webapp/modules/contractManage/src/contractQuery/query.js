define([
'text!contractManageTpl/contractQuery/query.html',
'text!contractManageTpl/contractQuery/print.html',
'contractManageDat/contract',
'contractManageLan'
], 
function(queryTpl,printTpl,contractAjax){
	var gridObj = null;
	var self = {
		showPage : function(){
			$('#busibox').html(_.template(queryTpl));
			
			//查询
			$("#search_contract_btn").click(function(){
				self.loadGrid();
			});
		},
		//加载表格
		loadGrid : function(){
			var params = {
					search_entResNo : $("#entResNo").val(),
					search_entCustName : $("#entCustName").val(),
					search_linkMan : $("#linkMan").val()
			};
			gridObj = comm.getCommBsGrid("search_contract_table","find_contarct_by_page",params,null,this.print);
		},
		//打印
		print : function(record, rowIndex, colIndex, options){
			if(colIndex == 4){
				return comm.lang("common").custType[record.custType];
			}else if(colIndex == 6){
				var printCount = 0;
				if(comm.isNotEmpty(record.printCount) && record.printCount > 0){
					printCount = 1;
				}
				return comm.lang("contractManage").isPrint[printCount];
			}else if(colIndex == 7){
				/*
				var link = $('<a>'+comm.lang("contractManage").print+'</a>').click(function(e) {
					var url = 'modules/contractManage/tpl/contractQuery/print.html?contractNo='+record.contractNo;
					var windowName = comm.lang("contractManage").printWindow;
					window.open(url,windowName,'left=0,top=0,width=1000px,height=600px,alwaysRaised=yes,toolbar=no,menubar=no,scrollbars=no,resizable=no,location=no,status=no,statusbars=no');
				}.bind(this));
				return link;
				*/
				return $('<a>' + comm.lang("contractManage").print + '</a>').click(function (e) {
					var reqParam = {contractNo: record.contractNo, realTime: false};
					contractAjax.findContractContentBySeal(reqParam, function (res) {
						self.daYin(res.data, record.contractNo);
					});
				});
			}
		},
		daYin : function(obj,contractNo){
			if(obj.sealStatus==1||obj.sealStatus==3){
				var content = obj.content;
				obj.content = content.replace('<div id="business">','<div id="business" class="pr"><div style="top:-15px;left: 58px;" class="pa"><img src="resources/img/contract.png"  draggable="false"></div>');
			}
			$('#dialogBox').html(_.template(printTpl,obj));	
			$('#cContent').css({"width":"780px","line-height":"1.5em","font-size":"16pt"});
			/*弹出框*/
			$( "#dialogBox" ).dialog({
				title:"打印合同",
				width:"900",
				height: "600",
				modal:true,
				closeIcon : true,
				buttons:{
					"打印":function(){
						/*var bdhtml=window.document.body.innerHTML;
						var sprnstr="<!--startprint-->";
						var eprnstr="<!--endprint-->";
						var prnhtml=bdhtml.substring(bdhtml.indexOf(sprnstr)+17);
						prnhtml=prnhtml.substring(0,prnhtml.indexOf(eprnstr));
						var myWindow=window.open('','','');
						myWindow.document.write(window.document.documentElement.outerHTML);
						myWindow.document.body.innerHTML = prnhtml;
						myWindow.focus();
						myWindow.print();*/
						var prnhtml =$('#cContent').parent().html();
						var myWindow = window.open('print.html');
						if($.browser.msie) {
							myWindow.onload = new function(){
								myWindow.document .body.innerHTML = prnhtml;
								myWindow.print();
							}; 
						}else{
							myWindow.onload = function(){
								myWindow.document .body.innerHTML = prnhtml;
								myWindow.print();
							}; 
						}
						
						var reqParam = {contractNo:contractNo};
						contractAjax.printContract(reqParam,function(res){
							
						});
						$( this ).dialog( "destroy" );
						$('#search_contract_btn').click();
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
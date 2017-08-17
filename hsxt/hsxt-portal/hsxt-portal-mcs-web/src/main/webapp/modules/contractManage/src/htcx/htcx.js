define(['text!contractManageTpl/htcx/htcx.html',
        'text!contractManageTpl/htcx/print.html',
        'contractManageDat/htcx'], function(htcxTpl,printTpl,dataModoule){
	var htcxPage =  {
		showPage : function(){
			$('#busibox').html(_.template(htcxTpl));
			
			$('#queryBtn').click(function(){
				htcxPage.query();
			});
		},
		query : function(){
			var jsonParam = {
                search_companyResNo:$("#search_entResNo").val(),
                search_entCustName:$("#search_entCustName").val(),
                search_linkman:$("#search_linkman").val(),
			};
			dataModoule.findContractList("searchTable",jsonParam, this.detail,this.detail2);
		},
		detail : function(record, rowIndex, colIndex, options){
			if(colIndex == 4){
				return comm.getNameByEnumId(record['custType'], comm.lang("contractManage").custTypeEnum);
			}
			if(colIndex == 5){
				if(record['printCount'] && record['printCount'] > 0){
					return "已打印";
				}else{
					return "未打印";
				}
			}
			if(colIndex == 6){
				var link =  $('<a data-sn="'+ record['entResNo'] +'" >预览</a>').click(function(e) {
					
					var reqParam = {contractNo:record.contractNo,realTime:false};
					dataModoule.findContractContentBySeal(reqParam,function(res){
						//res.data.contractNo = obj.contractNo;
						htcxPage.yulan(res.data,record.contractNo);
					});
					
					var data_sn=$(this).attr('data-sn');
					$('#cx_content_list').addClass('none');
					$('#cx_content_detail').removeClass('none');						
					$('#ckxq_sbxx').click();
				});
				return link;
			}
			
		},
		detail2 : function(record, rowIndex, colIndex, options){
			if(colIndex == 6){
				var link =  $('<a data-sn="'+ record['entResNo'] +'" >打印</a>').click(function(e) {
					
					var reqParam = {contractNo:record.contractNo,realTime:false};
					dataModoule.findContractContentBySeal(reqParam,function(res){
						//res.data.contractNo = obj.contractNo;
						htcxPage.daYin(res.data,record.contractNo);
					});
					
					var data_sn=$(this).attr('data-sn');
					$('#cx_content_list').addClass('none');
					$('#cx_content_detail').removeClass('none');						
					$('#ckxq_sbxx').click();
				});
				return link;
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
						dataModoule.printContract(reqParam,function(res){
							/*bdhtml=window.document.body.innerHTML;
							sprnstr="<!--startprint-->";
							eprnstr="<!--endprint-->";
							prnhtml=bdhtml.substring(bdhtml.indexOf(sprnstr)+17);
							prnhtml=prnhtml.substring(0,prnhtml.indexOf(eprnstr));
							window.document.body.innerHTML=prnhtml;
							window.print();*/
						});
						$( this ).dialog( "destroy" );
						$('#queryBtn').click();
					},
					"关闭":function(){ 
						 $( this ).dialog( "destroy" );
					}
				}
			});
		},
		yulan : function(obj,contractNo){
			if(obj.sealStatus==1){
				var content = obj.content;
				obj.content = content.replace('<div id="business">','<div id="business" class="pr"><div style="top:-15px;left: 58px;" class="pa"><img src="resources/img/contract.png"  draggable="false"></div>');
			}
			$('#dialogBox').html(_.template(printTpl,obj));	
			$('#cContent').css({"width":"780px","line-height":"1.5em","font-size":"16pt"});
			/*弹出框*/
			$( "#dialogBox" ).dialog({
				title:"合同预览",
				width:"900",
				height: "600",
				modal:true,
				closeIcon : true,
				buttons:{
					"关闭":function(){ 
						 $( this ).dialog( "destroy" );
					}
				}
			});
		}
	}	
	
	return htcxPage;
});
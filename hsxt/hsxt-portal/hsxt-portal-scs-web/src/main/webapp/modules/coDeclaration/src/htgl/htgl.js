define(['text!coDeclarationTpl/htgl/htgl.html',
        'text!coDeclarationTpl/htgl/print.html',
        'coDeclarationDat/htgl/htgl',
        'coDeclarationLan'],function(htglTpl, printTpl, dataModoule){
	return {	 	
		showPage : function(){
			var self = this;
			$('#contentWidth_2').html(_.template(htglTpl));
			$('#queryBtn').click(function(){
				self.query();
			});
		},
		query : function(){
			var jsonParam = {
                search_entResNo:$("#search_entResNo").val(),
                search_entCustName:$("#search_entCustName").val(),
                search_linkman:$("#search_linkman").val(),
			};
			dataModoule.findContractList(jsonParam, this.detail2,this.detail);
		},
		detail : function(record, rowIndex, colIndex, options){
			if(colIndex == 4){
				return comm.getNameByEnumId(record['custType'], comm.lang("coDeclaration").custTypeEnum);
			}
			if(colIndex == 6){
				if(record['printCount'] && record['printCount'] > 0){
					return "已打印";
				}else{
					return "未打印";
				}
			}
			if(colIndex == 7){
				var link =  $('<a data-sn="'+ record['entResNo'] +'" >打印</a>').click(function(e) {
					var data_sn=$(this).attr('data-sn');
					$('#cx_content_list').addClass('none');
					$('#cx_content_detail').removeClass('none');						
					$('#ckxq_sbxx').click();
					
					var reqParam = {contractNo : record.contractNo};
					dataModoule.findContractContentByPrint(reqParam,function(res){
						var obj = res.data;
						if(record.sealStatus==1||record.sealStatus==3){
							var content = obj.content;
							obj.content = content.replace('<div id="business">','<div id="business" class="pr"><div style="top:-15px;left: 58px;" class="pa"><img src="resources/img/contract.png"  draggable="false"></div>');
						}
						$('#dialogBox').html(_.template(printTpl,obj));	
						$('#cContent strong').css({"font-weight":"bold"});
						$('#cContent').css({"width":"780px","line-height":"1.5em","font-size":"16pt"});
						$( "#dialogBox" ).dialog({
							title:"打印合同",
							width:"1000",
							modal:true,
							closeIcon : true,
							buttons:{
								"打印":function(){
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
									dataModoule.printContract(reqParam,function(res){
										/*bdhtml=window.document.body.innerHTML;
				                         sprnstr="<!--startprint-->";
				                         eprnstr="<!--endprint-->";
				                         prnhtml=bdhtml.substring(bdhtml.indexOf(sprnstr)+17);
				                         prnhtml=prnhtml.substring(0,prnhtml.indexOf(eprnstr));
				                         window.document.body.innerHTML=prnhtml;
				                         window.print();*/
									});
								},
								"关闭":function(){
									 $( this ).dialog( "destroy" );
								}
							}
						});
					});
				});
				return link;
			}
		},
		detail2 : function(record, rowIndex, colIndex, options){
			if(colIndex == 7){
				var link =  $('<a data-sn="'+ record['entResNo'] +'" >预览</a>').click(function(e) {
					var data_sn=$(this).attr('data-sn');
					$('#cx_content_list').addClass('none');
					$('#cx_content_detail').removeClass('none');						
					$('#ckxq_sbxx').click();
					
					var reqParam = {contractNo : record.contractNo};
					dataModoule.findContractContentByPrint(reqParam,function(res){
						var obj = res.data;
						if(record.sealStatus==1||record.sealStatus==3){
							var content = obj.content;
							obj.content = content.replace('<div id="business">','<div id="business" class="pr"><div style="top:-15px;left: 58px;" class="pa"><img src="resources/img/contract.png"  draggable="false"></div>');
						}
						$('#dialogBox').html(_.template(printTpl,obj));	
						$('#cContent strong').css({"font-weight":"bold"});
						$('#cContent').css({"width":"780px","line-height":"1.5em","font-size":"16pt"});
						$( "#dialogBox" ).dialog({
							title:"合同预览",
							width:"1000",
							modal:true,
							closeIcon : true,
							buttons:{
								"关闭":function(){
									 $( this ).dialog( "destroy" );
								}
							}
						});
					});
				});
				return link;
			}
		}
	}
}); 

define(['text!systemManageTpl/yhzgl/yhzgl.html',
        'systemManageDat/systemManage'],function(yhzglTpl,systemManage){
	var yhzglPage = {
		loginInfo : null,
		gridObj : null,
		showPage : function(){
			
			
			$('#busibox').html(_.template(yhzglTpl)) ;			 
			//Todo...
		 	
		 	//添加
		 	$('#btn_tjyhz').triggerWith('#xtyhgl_xgyhz');
		 	$("#updateFlag").val("2");
		 	
		 	//获取用户登录信息
		 	loginInfo = comm.getRequestParams();
		 	
			var params = {
					search_entCustId : 	loginInfo.entCustId 		//企业客户号
			};
			gridObj = comm.getCommBsGrid("detailTable","list_entgroup",params,comm.lang("systemManage"),yhzglPage.detail,yhzglPage.del,yhzglPage.edit);
			
				
		},
		edit : function(record, rowIndex, colIndex, options){
			if(colIndex == 4){
				
				var link1 =  $('<a >'+comm.lang("systemManage").update+'</a>').click(function(e) {							
					
					$('#xtyhgl_xgyhz').click();
					
					$("#groupName").val(record.groupName);
					$("#groupDesc").val(record.groupDesc);
					$("#groupId").val(record.groupId);
					$("#entCustId").val(record.entCustId);
					$("#updateFlag").val("1");
					
				});
				return   link1 ;
			}
  		} ,
  		del : function(record, rowIndex, colIndex, options){
  			if(colIndex == 4){
  				
  				var link1 =  $('<a >'+comm.lang("systemManage").del+'</a>').click(function(e) {							
  					
  					var confirmObj = {	
  							imgFlag:true,
  							width:400,
  							content : comm.lang("systemManage").delContent,	
  							
  							callOk :function(){
  								
  								systemManage.deleteEntGroup({
  									adminCustId : loginInfo.entCustId,
  									groupId : record.groupId
  								},function(res){
  									if(gridObj){
  										gridObj.refreshPage();
  									}
  								});
  							}
  					}
  					comm.confirm(confirmObj);
  					
  				});
  				return   link1 ;
  			}
  		} ,
  		detail :  function(record, rowIndex, colIndex, options){
  			if(colIndex == 2){
  				var names = "";
  				var opers = record.opers;
  				if(opers && opers.length > 0){
  					for(var i = 0; i < opers.length ; i++){
  						names = names + opers[i].realName + ",";
  					}
  					names = names.substring(0,names.length - 1);
  				}
  				return names;
  			}
  			else if(colIndex == 4){
  				
  				var link1 =  $('<a >'+comm.lang("systemManage").groupUser + '</a>').click(function(e) {							
  					
  					require(['systemManageSrc/yhzgl/tjwhyh'], function(tab){
  						
  						$('#xtyhgl_tjwhzy').click();
  						
  						tab.showPage(record);
  						
  					});	
  					
  				});
  				return   link1 ;
  			}
  		}
	};
	return yhzglPage
}); 

 
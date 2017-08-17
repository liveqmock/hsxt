define(['text!infoManageTpl/smrzsp/xfzsmrzsp.html',
        'infoManageDat/infoManage',
        'commDat/common'], function(xfzsmrzspTpl,infoManage,common){
	var xfzsmrzspPage = {
		gridObj : null,
		showPage : function(){
			
			$('#busibox').html(_.template(xfzsmrzspTpl));
			
			//时间控件		    
		    comm.initBeginEndTime('#search_startDate','#search_endDate');
			/*end*/
			
			/*下拉列表*/
			comm.initSelect("#status",comm.lang("infoManage").realNameStatuEnum, null, null, {name:'全部', value:''});
			
			
			$("#searchBtn").click(function(){
				if(!comm.queryDateVaild("search_form").form()){
					return;
				}
				var params = {
						search_entResNo : $("#searchEntResNo").val(),			//消费者互生号
						search_entName : $("#searchentName").val(),			//消费者名称
						search_endDate : $("#search_endDate").val(), 	//结束时间
						search_startDate : $("#search_startDate").val(), //开始时间
						search_status : comm.removeNull($("#status").attr("optionvalue"))//状态
				};
				xfzsmrzspPage.gridObj = comm.getCommBsGrid("searchTable","perrealnameidentific_apprlist",params,comm.lang("infoManage"),xfzsmrzspPage.detail,xfzsmrzspPage.query);
				
			});
		},
		detail : function(record, rowIndex, colIndex, options){
			if(colIndex == 4)
			{
				return comm.lang("infoManage").realNameStatuEnum[record.status];
			}
			else if(colIndex == 6)
			{
				
				var link1 = null;
				if(record.status == '0'){
					link1 = $('<a>'+comm.lang("infoManage").apprval+'</a>').click(function(e) {

						xfzsmrzspPage.shenPi(record);
						
					}.bind(this) ) ;
				}
				else if(record.status == '2' || record.status == '3'){
					link1 = $('<a>'+comm.lang("infoManage").query+'</a>').click(function(e) {

						xfzsmrzspPage.chaKan(record);
						
					}.bind(this) ) ;
				}
				else if(record.status == '1'){
					link1 = $('<a>'+comm.lang("infoManage").fuhe+'</a>').click(function(e) {

						xfzsmrzspPage.spfh(record);
						
					}.bind(this) ) ;
				}
				
				return link1;
			}
			else if(colIndex == 7)
			{
				var link =  $('<a>'+comm.lang("infoManage").workTaskRefuseAccept+'</a>').click(function(e) {
                    comm.confirm({
                            imgFlag : true,
                            width:500,
                            imgClass : 'tips_i',
                            content : comm.lang("infoManage").wtCusteRealNameRefuseComfirm,
                            callOk : function(){
                                    var param={"bizNo":record.applyId};
                                    common.workTaskRefuseAccept(param,function(rsp){
                                            //成功
                                            comm.alert({imgClass: 'tips_yes' ,content:comm.lang("infoManage")[22000],callOk:function(){
                                            	xfzsmrzspPage.gridObj.refreshPage();
                                            }});
                                    });
                            }        
                    });
	            }.bind(this));
				
	            return link;
			}
		},
		query : function(record, rowIndex, colIndex, options){
			if(colIndex == 7){
				 return comm.workflow_operate(comm.lang("infoManage").workTaskHangUp, comm.lang("infoManage").wtCusteRealNamePauseComfirm, function(){
                     require(["workoptSrc/gdgq"],function(gdgq){
                             gdgq.guaQi(record.applyId,xfzsmrzspPage.gridObj);
                     });
                 });
			}
		},
		
		shenPi : function(obj){
			xfzsmrzspPage.requireSubTab(obj);
		},
		chaKan : function(obj){
			xfzsmrzspPage.requireSubTab(obj);
		},
		spfh : function(obj){
			xfzsmrzspPage.requireSubTab(obj);
		},
		requireSubTab : function(record){
			infoManage.queryDetailPerRealNameIdentific({applyId : record.applyId},function(res){
				var obj = res.data;
				obj.status = record.status;
				obj.applyId = record.applyId;
				require(['infoManageSrc/smrzsp/sub_tab'], function(tab){
					
					tab.showPage(obj);
					
				});	
			})
		}
		
	};
	return xfzsmrzspPage
});
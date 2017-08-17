define(['text!certificateManageTpl/zsffrz/zsffrz.html',
		'certificateManageLan'
		], function(ffrzTpl){
	return {
		chaKanObj : null,
		showPage : function(obj,btnId){
			
			$('#busibox_ck').html(_.template(ffrzTpl, obj));
			comm.liActive_add($('#ck'));
			$("#busibox").addClass("none");
			$("#zsffrzTpl").removeClass("none");
			$('#busibox_ck').removeClass("none");
			var self = this;
			
			/*表格数据模拟*/
			var chaKanParam = {
					search_certificateNo	:	obj.certificateNo
			}
			chaKanObj = comm.getCommBsGrid("zsffrzTable","find_third_certificate_give_out_recode",chaKanParam,comm.lang("certificateManage"),self.detail);
			/*end*/	
			//$('#back_zsffrz_btn').triggerWith('#'+btnId);
			//返回按钮
			$('#back_zsffrz_btn').click(function(){
				//隐藏头部菜单
				$('#zsffrzTpl').addClass('none');
				$('#busibox').removeClass('none');
				$('#ck').addClass("tabNone").find('a').removeClass('active');
				$('#'+btnId).find('a').addClass('active');
				$('#busibox_ck').addClass("none");
			});
			//
		},
		detail : function(record, rowIndex, colIndex, options){
			if (colIndex == 1) {
                return record.certificateNo ? record.certificateNo.substring(11, record.certificateNo.length) : '';
            }
			if(colIndex == 2){
				return comm.lang("certificateManage").sealStatusEnum[record.sealStatus];
			}
			if(colIndex == 5){
				return comm.lang("certificateManage").printStatusEnum[record.isPrint];
			}
			var obj = chaKanObj.getRecord(rowIndex); 
			
			if(colIndex == 8){
				if(obj.sendRemark){
					var link1 = $('<a>查看</a>').click(function(e) {
						comm.alert(obj.sendRemark);
					});
					return link1;
				}
			}
			else if(colIndex == 9){
				if(obj.recRemark){
					var link1 = $('<a>查看</a>').click(function(e) {
						comm.alert(obj.recRemark);
					});
					return link1;
				}
			}
			return "";
			
		},
		chaKan_1 : function(){},
		chaKan_2 : function(){}
	}	
});
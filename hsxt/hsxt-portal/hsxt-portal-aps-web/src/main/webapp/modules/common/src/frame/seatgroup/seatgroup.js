/**
 *坐席分组查询
 */
define(["text!commTpl/frame/seatgroup/seatgroup.html"
], function(seatgroup){
    return {
        showPage : function(zxfz_list){

            var self = this;

            //查询分组坐席
            $("#serviceContent_2_2").html(_.template(seatgroup, {list:zxfz_list.BusinessAgentReport}));
            comm.group_accordion();//坐席分组手风琴效果
        }
    }
});


getSession();
$(function() {
	
	$('#tabs').tabs();
	
	//change 事件
	var vals = $("input[name^='v_']");
	vals.change(function() {
		var currInputValue = $(this).val();
		
		if($(this).attr('name') == "v_day"){
			if(currInputValue == '?'){
				$('input[name="v_week"]').val("*");
				
			}else{
				
				$('input[name="v_week"]').val("?");
			}
		}
		if($(this).attr('name') == "v_week"){
			if(currInputValue == '?'){
				$('input[name="v_day"]').val("*");
				
			}else{
				
				$('input[name="v_day"]').val("?");
			}
			
		}
		var item = [];
		vals.each(function() {
			item.push(this.value);
		});
		$("#exp").val(item.join(" "));
		$("#exp").change();
	});
	
	
	$("input[id^='cron']").change(function(){
		
		var split = "/";
		var p = $(this).parent().parent();
		if(p.find('span:contains("-")').length > 0){
			split = "-";
		}else if(p.find('span:contains("#")').length > 0){
			split = "#";
		}
		var inputs = p.find("input[type='text']");
		
		var inputRadio = p.find("input[type='radio']");
		var name = $(inputRadio).attr('name');
		
		var startMin = $(inputs[0]).attr('data-min');
		var startMax = $(inputs[0]).attr('data-max');
		
		var endMin = $(inputs[1]).attr('data-min');
		var endMax = $(inputs[1]).attr('data-max');

		var start = $(inputs[0]).val();
		var end = $(inputs[1]).val();
		
		if(start.validator(/^\d+$/) && end.validator(/^\d+$/)){
		
			if(parseInt(start,10) > startMax){
				$(inputs[0]).val(startMax);
				start = startMax;
				message("第一个参数必须小于等于" + startMax + "！");
			}
			
			if(parseInt(start,10) < startMin){
				$(inputs[0]).val(startMin);
				start = startMin;
				message("第一个参数必须大于等于"+ startMin +"！");
			}
			
			if(parseInt(end,10) > endMax){
				$(inputs[1]).val(endMax);
				end = endMax;
				message("第二个参数必须小于等于" + endMax + "！");
			}
			
			if(parseInt(end,10) < parseInt(endMin,10)){
				$(inputs[1]).val(parseInt(endMin,10));
				end = endMin;
				message("第二个参数必须大于等于"+ endMin +"！");
			}
			
			inputRadio[0].checked = true;
			
			var item = $("input[name=v_" + name + "]");
			item.val(start + split + end);
			item.change();
			
		}else{
			message("输入字符不合法，只能输入数字");
			return;
		}
	});
	
	//本月最后一个星期几
	$("#weekStart_2").change(function(){
		
		var $this = $(this);
		var p = $this.parent().parent();
		var inputRadio = p.find("input[type='radio']");
		var name = inputRadio.attr('name');
		
		var startMin = $this.attr('data-min');
		var startMax = $this.attr('data-max');
		
		var start = $this.val();
		
		if(start.validator(/^\d+$/)){
			
			if(parseInt(start,10) > startMax){
				$this.val(startMax);
				start = startMax;
				message("第一个参数必须小于等于" + startMax + "！");
			}
			if(parseInt(start,10) < startMin){
				$this.val(startMin);
				start = startMin;
				message("第一个参数必须大于等于"+ startMin +"！");
			}
			
			inputRadio[0].checked = true;
			
			var item = $("input[name=v_" + name + "]");
			item.val(start+"L");
			item.change();
		}else{
			message("输入字符不合法，只能输入数字");
			return;
		}
	});
	
	
	//本月最后一个星期几
	$("#dayStart_2").change(function(){
		
		var $this = $(this);
		var p = $this.parent().parent();
		var inputRadio = p.find("input[type='radio']");
		var name = inputRadio.attr('name');
		
		var startMin = $this.attr('data-min');
		var startMax = $this.attr('data-max');
		
		var start = $this.val();
		
		if(start.validator(/^\d+$/)){
			
			if(parseInt(start,10) > startMax){
				$this.val(startMax);
				start = startMax;
				message("第一个参数必须小于等于" + startMax + "！");
			}
			if(parseInt(start,10) < startMin){
				$this.val(startMin);
				start = startMin;
				message("第一个参数必须大于等于"+ startMin +"！");
			}
			
			inputRadio[0].checked = true;
			
			var item = $("input[name=v_" + name + "]");
			item.val(start+"W");
			item.change();
		}else{
			message("输入字符不合法，只能输入数字");
			return;
		}
	});
	
	
	
	var secList = $(".secList").children();
	$("#sec_appoint").click(function(){
		if(this.checked){
			secList.eq(0).change();
		}
	});

	secList.change(function() {
		var sec_appoint = $("#sec_appoint").prop("checked");
		if (sec_appoint) {
			var vals = [];
			secList.each(function() {
				if (this.checked) {
					vals.push(this.value);
				}
			});
			var val = "*";
			if (vals.length > 0 && vals.length < 59) {
				val = vals.join(","); 
			}else if(vals.length == 59){
				val = "*";
			}
			var item = $("input[name=v_sec]");
			item.val(val);
			item.change();
		}
	});
	
	var minList = $(".minList").children();
	$("#min_appoint").click(function(){
		if(this.checked){
			minList.eq(0).change();
		}
	});
	
	minList.change(function() {
		var min_appoint = $("#min_appoint").prop("checked");
		if (min_appoint) {
			var vals = [];
			minList.each(function() {
				if (this.checked) {
					vals.push(this.value);
				}
			});
			var val = "*";
			if (vals.length > 0 && vals.length < 59) {
				val = vals.join(",");
			}else if(vals.length == 59){
				val = "*";
			}
			var item = $("input[name=v_min]");
			item.val(val);
			item.change();
		}
	});
	
	var hrList = $(".hrList").children();
	$("#hr_appoint").click(function(){
		console.log(123);
		if(this.checked){
			hrList.eq(0).change();
		}
	});
	
	hrList.change(function() {
		var hr_appoint = $("#hr_appoint").prop("checked");
		if (hr_appoint) {
			var vals = [];
			hrList.each(function() {
				if (this.checked) {
					vals.push(this.value);
				}
			});
			var val = "*";
			if (vals.length > 0 && vals.length < 24) {
				val = vals.join(",");
			}else if(vals.length == 24){
				val = "*";
			}
			var item = $("input[name=v_hr]");
			item.val(val);
			item.change();
		}
	});
	
	var dayList = $(".dayList").children();
	$("#day_appoint").click(function(){
		if(this.checked){
			dayList.eq(0).change();
		}
	});
	
	dayList.change(function() {
		var day_appoint = $("#day_appoint").prop("checked");
		if (day_appoint) {
			var vals = [];
			dayList.each(function() {
				if (this.checked) {
					vals.push(this.value);
				}
			});
			var val = "?";
			if (vals.length > 0 && vals.length < 31) {
				val = vals.join(",");
			}else if(vals.length == 31){
				val = "*";
			}
			var item = $("input[name=v_day]");
			item.val(val);
			item.change();
		}
	});
	
	var monthList = $(".monthList").children();
	$("#month_appoint").click(function(){
		if(this.checked){
			monthList.eq(0).change();
		}
	});
	
	monthList.change(function() {
		var month_appoint = $("#month_appoint").prop("checked");
		if (month_appoint) {
			var vals = [];
			monthList.each(function() {
				if (this.checked) {
					vals.push(this.value);
				}
			});
			var val = "*";
			if (vals.length > 0 && vals.length < 12) {
				val = vals.join(",");
			}else if(vals.length == 12){
				val = "*";
			}
			var item = $("input[name=v_month]");
			item.val(val);
			item.change();
		}
	});
	
	var weekList = $(".weekList").children();
	$("#week_appoint").click(function(){
		if(this.checked){
			weekList.eq(0).change();
		}
	});
	
	weekList.change(function() {
		var week_appoint = $("#week_appoint").prop("checked");
		if (week_appoint) {
			var vals = [];
			weekList.each(function() {
				if (this.checked) {
					vals.push(this.value);
				}
			});
			var val = "?";
			if (vals.length > 0 && vals.length < 7) {
				val = vals.join(",");
			}else if(vals.length == 7){
				val = "*";
			}
			var item = $("input[name=v_week]");
			item.val(val);
			item.change();
		}
	});
});


/**
 * 设置为*
 */
function everyTime(dom) {
	var item = $("input[name=v_" + dom.name + "]");
	item.val("*");
	item.change();
}

/**
 * 不指定
 */
function unAppoint(dom) {
	var name = dom.name;
	var val = "?";
	if (name == "year"){
		
		val = "";
	}
	var item = $("input[name=v_" + name + "]");
	item.val(val);
	item.change();
}


function appoint(dom) {

}


/**
 * 周期
 */
function cycle(dom) {
	var name = dom.name;
	var ns = $(dom).parent().parent().find(".input-group-addon");
	var start = ns.eq(2).children().val();
	var end = ns.eq(4).children().val();
	var item = $("input[name=v_" + name + "]");
	item.val(start + "-" + end);
	item.change();
}

/**
 * 从开始
 */
function startOn(dom) {
	var name = dom.name;
	var ns = $(dom).parent().parent().find(".input-group-addon");
	var start = ns.eq(2).children().val();
	var end = ns.eq(4).children().val();
	var item = $("input[name=v_" + name + "]");
	item.val(start + "/" + end);
	item.change();
}

function lastDay(dom){
	var item = $("input[name=v_" + dom.name + "]");
	item.val("L");
	item.change();
}

function weekOfDay(dom){
	var name = dom.name;
	var ns = $(dom).parent().parent().find("input[type='text']");
	var start = ns.eq(0).val();
	var end = ns.eq(0).val();
	
	var item = $("input[name=v_" + name + "]");
	item.val(start + "#" + end);
	item.change();
}

function lastWeek(dom){
	
	var ns = $(dom).parent().parent().find("input[type='text']");
	var start = ns.eq(0).val();
	
	var item = $("input[name=v_" + dom.name + "]");
	item.val(start+"L");
	item.change();
}

function workDay(dom) {
	var name = dom.name;
	var ns = $(dom).parent().parent().find("input[type='text']");
	var start = ns.eq(0).val();
	
	var item = $("input[name=v_" + name + "]");
	item.val(start + "W");
	item.change();
}


function btnFan() {
    //获取参数中表达式的值
    var txt = $("#exp").val();
    if (txt) {
        var regs = txt.split(' ');
        $("input[name=v_sec]").val(regs[0]);
        $("input[name=v_min]").val(regs[1]);
        $("input[name=v_hr]").val(regs[2]);
        $("input[name=v_day]").val(regs[3]);
        $("input[name=v_month]").val(regs[4]);
        $("input[name=v_week]").val(regs[5]);

        initObj(regs[0], "sec");
        initObj(regs[1], "min");
        initObj(regs[2], "hr");
        initDay(regs[3]);
        initMonth(regs[4]);
        initWeek(regs[5]);

        if (regs.length > 6) {
            $("input[name=v_year]").val(regs[6]);
            initYear(regs[6]);
        }
        
        listNextFireTimes('exp');
    }
}


function initObj(strVal, strid) {
    var ary = null;
    var objRadio = $("input[name='" + strid + "'");
    if (strVal == "*") {
        objRadio.eq(0).attr("checked", "checked");
    } else if (strVal.split('-').length > 1) {
        ary = strVal.split('-');
        objRadio.eq(1).attr("checked", "checked");
        $("#cron_" + strid + "Start_0").val(ary[0]);
        $("#cron_" + strid + "End_0").val(ary[1]);
    } else if (strVal.split('/').length > 1) {
        ary = strVal.split('/');
        objRadio.eq(2).attr("checked", "checked");
        $("#cron_" + strid + "Start_1").val(ary[0]);
        $("#cron_" + strid + "End_1").val(ary[1]);
    } else {
        objRadio.eq(3).attr("checked", "checked");
        if (strVal != "?") {
            ary = strVal.split(",");
            for (var i = 0; i < ary.length; i++) {
                $("." + strid + "List input[value='" + ary[i] + "']").attr("checked", "checked");
            }
        } else {
        	$("." + strid + "List").children().each(function() {
				if (this.checked) {
					this.checked = false;
				}
			});
        }
    }
}

function initDay(strVal) {
    var ary = null;
    var objRadio = $("input[name='day'");
    if (strVal == "*") {
        objRadio.eq(0).attr("checked", "checked");
    } else if (strVal.split('-').length > 1) {
        ary = strVal.split('-');
        objRadio.eq(1).attr("checked", "checked");
        
        $("#cron_dayStart_0").val(ary[0]);
        $("#cron_dayEnd_0").val(ary[1]);
    } else if (strVal.split('/').length > 1) {
        ary = strVal.split('/');
        objRadio.eq(2).attr("checked", "checked");
        
        $("#cron_dayStart_1").val(ary[0]);
        $("#cron_dayEnd_1").val(ary[1]);
    } else if (strVal.split('W').length > 1) {
        ary = strVal.split('W');
        objRadio.eq(3).attr("checked", "checked");
        
        $("#dayStart_2").val(ary[0]);
    } else if (strVal == "L") {
        objRadio.eq(4).attr("checked", "checked");
    } else if (strVal == "?") {
        objRadio.eq(5).attr("checked", "checked");
    } else {
        objRadio.eq(6).attr("checked", "checked");
        ary = strVal.split(",");
        for (var i = 0; i < ary.length; i++) {
            $(".dayList input[value='" + ary[i] + "']").attr("checked", "checked");
        }
    }
}

function initMonth(strVal) {
    var ary = null;
    var objRadio = $("input[name='month'");
    
    if (strVal == "*") {
        objRadio.eq(0).attr("checked", "checked");
        
    } else if (strVal.split('-').length > 1) {
        ary = strVal.split('-');
        objRadio.eq(1).attr("checked", "checked");
       
        $("#cron_monthStart_0").val(ary[0]);
        $("#cron_monthEnd_0").val(ary[1]);
    } else if (strVal.split('/').length > 1) {
        ary = strVal.split('/');
        objRadio.eq(2).attr("checked", "checked");
       
        $("#cron_monthStart_1").val(ary[0]);
        $("#cron_monthEnd_1").val(ary[1]);
    } else {
        objRadio.eq(3).attr("checked", "checked");

        ary = strVal.split(",");
        for (var i = 0; i < ary.length; i++) {
            $(".monthList input[value='" + ary[i] + "']").attr("checked", "checked");
        }
    }
}

function initWeek(strVal) {
    var ary = null;
    var objRadio = $("input[name='week'");
    if (strVal == "*") {
        objRadio.eq(0).attr("checked", "checked");
    } else if (strVal.split('-').length > 1) {
        ary = strVal.split('-');
        objRadio.eq(1).attr("checked", "checked");
        
        $("#cron_weekStart_0").val(ary[0]);
        $("#cron_weekEnd_0").val(ary[1]);
    } else if (strVal.split('#').length > 1) {
        ary = strVal.split('#');
        objRadio.eq(2).attr("checked", "checked");
       
        $("#cron_weekStart_1").val(ary[0]);
        $("#cron_weekEnd_1").val(ary[1]);
    } else if (strVal.split('L').length > 1) {
        ary = strVal.split('L');
        objRadio.eq(3).attr("checked", "checked");
        
        $("#weekStart_2").val(ary[0]);
    } else if (strVal == "?") {
        objRadio.eq(4).attr("checked", "checked");
    } else {
        objRadio.eq(5).attr("checked", "checked");
        ary = strVal.split(",");
        for (var i = 0; i < ary.length; i++) {
            $(".weekList input[value='" + ary[i] + "']").attr("checked", "checked");
        }
    }
}

function initYear(strVal) {
    var ary = null;
    var objRadio = $("input[name='year'");
    if (strVal == "*") {
        objRadio.eq(1).attr("checked", "checked");
    } else if (strVal.split('-').length > 1) {
        ary = strVal.split('-');
        objRadio.eq(2).attr("checked", "checked");
       
        $("#cron_yearStart_0").val(ary[0]);
        $("#cron_yearEnd_0").val(ary[1]);
    }
}

function message(info){
	
	console.log(info);
	var data = {};
	data.code = 0;
	data.message = info;
	messageBuilder(data, 'success');
}


/*
 * 复制
 */
function copyToClipBoard(id){
	
	var e = $("#" + id);
	
	//window.clipboardData.clearData();

	//window.clipboardData.setData("Text", $("#" + id).val());
	
	 e.select(); //选择对象 
	 //js=e.createTextRange();
        document.execCommand("Copy"); //执行浏览器复制命令

	message("复制成功！");
}

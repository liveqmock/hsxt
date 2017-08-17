define(['text!resourceQuotaTpl/sub/yjzypesqsp/yjzypesqsp.html',
    'text!resourceQuotaTpl/sub/yjzypesqsp/yjzypesqsp_view.html',
    'text!resourceQuotaTpl/sub/yjzypesqsp/yjzypesqsp_opt.html',
    'text!resourceQuotaTpl/sub/yjzypesqsp/yjzypesqsp_xh.html',
    'zrender', 'zrender/shape/Rectangle', 'zrender/shape/Line', 'zrender/shape/Text',
    'resourceQuotaDat/quota'
], function (yjzypesqspTpl, yjzypesqsp_viewTpl, yjzypesqsp_optTpl, yjzypesqsp_xhTpl, zrender, RectangleShape, LineShape, TextSharp, quota) {
    var resQuotaGrid = null;

    var resQuotaAppr = {
        offset: 0.5,
        canvasWidth: 842,
        canvasHeight: 580,
        textArr: [],
        textObject: [],
        rectangle: {},
        selectedFirstText: null,
        selectedLastText: null,
        maxLength: 999,   //最多可申请数量
        haoDuanTemp: [],
        haoDuan: [],      //号段
        apprList: null, //申请互生号清单
        colorList: ['#FF2D2D', '#FF95CA', '#FF44FF', '#613030', '#4A4AFF', '#00E3E3', '#02C874', '#007500', '#F9F900', '#3D7878', '#000079', '#600030'],
        platList: [],
        showPage: function (tabid) {
            $('#main-content > div[data-contentid="' + tabid + '"]').html(_.template(yjzypesqspTpl));

            /*日期控件*/
            $("#yjzypesqsp_tb_timeRange_start").datepicker({
                dateFormat: 'yy-mm-dd',
                onSelect: function (dateTxt, inst) {
                    var d = dateTxt.replace('-', '/');
                    $("#yjzypesqsp_tb_timeRange_end").datepicker('option', 'minDate', new Date(d));
                }
            });
            $("#yjzypesqsp_tb_timeRange_end").datepicker({
                dateFormat: 'yy-mm-dd',
                onSelect: function (dateTxt, inst) {
                    var d = dateTxt.replace('-', '/');
                    $("#yjzypesqsp_tb_timeRange_start").datepicker('option', 'maxDate', new Date(d));
                }
            });
            /*end*/

            $("#yjzypesqsp_tb_zt").selectList({
                borderWidth: 1,
                borderColor: '#CCC',
                options: [
                    {name: '待复核', value: '0'},
                    {name: '审批通过', value: '1'},
                    {name: '审批驳回', value: '2'}
                ]
            }).change(function (e) {
                //console.log($(this).val() );
                //$("#yjzypesqsp_tb_zt").val($(this).val());
            });

            resQuotaAppr.loadGrid();
            resQuotaAppr.opt_query();
            resQuotaAppr.initSelectAllPlatList('#yjzypesqsp_tb_dqpt');
        },

        //加载表格
        loadGrid: function () {
            resQuotaGrid = comm.getCommBsGrid("yjzypesqsp_ql", {domain: 'gpf_res', url: 'queryQuotaApplyList'}, {}, resQuotaAppr.opt_view, resQuotaAppr.opt_appr, resQuotaAppr.opt_sync_route, resQuotaAppr.opt_sync_allot);
        },
        //查询
        opt_query: function () {
            $("#yjzypesqsp_tb_cx").click(function () {
                var params = {
                    startDate: $("#yjzypesqsp_tb_timeRange_start").val(),
                    endDate: $("#yjzypesqsp_tb_timeRange_end").val(),
                    platNo: resQuotaAppr.getValue($("#yjzypesqsp_tb_dqpt").attr("optionvalue")),
                    entCustName: $("#yjzypesqsp_tb_glgs").val(),
                    status: resQuotaAppr.getValue($("#yjzypesqsp_tb_zt").attr("optionvalue"))
                };
                resQuotaGrid.search(params);
            });
        },
        getValue: function (obj) {
            if (obj == null) {
                return "";
            } else {
                return obj;
            }
        },

        opt_view: function (obj, rowIndex, colIndex, options) {
            if (colIndex == 4) {
                return comm.lang("quota").status[obj.status];
            } else if (colIndex == 5) {
                return $('<a>查看</a>').click(function (e) {
                    /*end*/
                    quota.queryQuotaAppInfo({applyId: obj.applyId}, function (info) {
                        if (info) {
                            $('#yjzypesqspDlg').empty().html(_.template(yjzypesqsp_viewTpl, info));
                            quota.queryQuotaAppInfo({applyId: obj.applyId}, function (response) {
                                if (response) {
                                    /*表单值初始化*/
                                	$('#yjzypesqsp_view_glgs').text(response.entCustName);
                                	$('#yjzypesqsp_view_glgshsh').text(response.entResNo);
                                	$('#yjzypesqsp_view_dqpt').text(response.platName);
                                	$('#yjzypesqsp_view_jhnpes').text(response.initQuota?response.initQuota:'');
                                	$('#yjzypesqsp_view_yfppes').text(response.assigned?response.assigned:'');
                                	$('#yjzypesqsp_view_bcsqpes').text(response.applyNum);
                                	if(response.applyRange != null){
                             	       $('#yjzypesqsp_view_pehd').text(response.applyRange);
                             	    }
                                	$('#yjzypesqsp_view_sqsj').text(response.reqTime);
                                	if(response.reqOptName != null){
                             	       $('#yjzypesqsp_view_sqczy').text(response.reqOptName);
                             	    }
                                }
                            });	
                            /*弹出框*/
                            $("#yjzypesqspDlg").dialog({
                                title: "查看",
                                width: "1000",
                                modal: true,
                                closeIcon: true,
                                buttons: {
                                    "关闭": function () {
                                        $(this).dialog("destroy");
                                    }
                                }
                            });
                        } else {
                            comm.alert({
                                imgClass: "tips_warn",
                                content: "查询结果为空"
                            });
                        }
                    });

                    /*end*/
                }.bind(this));
            }
        },
        opt_appr: function (obj, rowIndex, colIndex, options) {
            if (colIndex == 5 && obj.status == 0) {
                var link = $('<a>审批</a>').click(function (e) {
                    var that = resQuotaAppr;
                    $('#yjzypesqspDlg').html(yjzypesqsp_optTpl);

                    quota.queryIdTyp({manageEntResNo: obj.entResNo, applyId: obj.applyId}, function (response) {
                        if (response) {
                            resQuotaAppr.textObject = response;
                        }
                    });

                    quota.queryQuotaAppInfo({applyId: obj.applyId}, function (response) {
                        if (response) {
                            /*表单值初始化*/
                            $('#yjzypesqsp_opt_glgs').text(response.entCustName);
                            $('#yjzypesqsp_opt_glgshsh').text(response.entResNo);
                            $('#yjzypesqsp_opt_dqpt').text(response.platName);
                            $('#yjzypesqsp_opt_jhnpes').text(response.initQuota);
                            $('#yjzypesqsp_opt_yfppes').text(response.assigned);
                            $('#yjzypesqsp_opt_bcsqpes').text(response.applyNum);
                            if(response.applyRange != null){
                            	$('#yjzypesqsp_opt_pehd').text(response.applyRange);
                            }
                            $('#yjzypesqsp_opt_sqsj').text(response.reqTime);
                            if(response.reqOptName != null){
                            	$('#yjzypesqsp_opt_sqczy').text(response.reqOptName);
                            }
                        }
                    });

                    /*弹出框*/
                    $("#yjzypesqspDlg").dialog({
                        title: "一级资源配额审批",
                        width: "1000",
                        height: "600",
                        modal: true,
                        closeIcon: true,
                        buttons: {
                            "确定": function () {

                                if ($('input[name="yjzypesqsp_opt_spjg"]:checked').val() == true && !resQuotaAppr.validate()) {
                                    return;
                                }
                                else {
                                    var loginInfo = comm.getLoginInfo();
                                    var params = {
                                        applyId: obj.applyId,
                                        isPass: $('input[name="yjzypesqsp_opt_spjg"]:checked').val(),
                                        apprNum: $("#yjzypesqsp_opt_pfpes").val(),
                                        apprRange: $("#yjzypesqsp_opt_pfhd").val(),
                                        resNoList: that.apprList,
                                        apprRemark: $("#jsxxgl_opt_bz").val(),
                                        apprOptId: loginInfo.operatorId,
                                        apprOptName: loginInfo.name
                                    };
                                    quota.apprQuotaApp(params, function (response) {
                                        if (response) {
                                            comm.yes_alert("操作成功");
                                            $("#yjzypesqspDlg").dialog("destroy");
                                            $('#yjzypesqsp_tb_cx').trigger('click');
                                        }
                                    });
                                }
                            },
                            "取消": function () {
                                $(this).dialog("destroy");
                            }
                        }
                    });
                    /*end*/

//					$('#yjzypepz_opt_glgshsh').text(obj.entResNo);
//					$('#yjzypepz_opt_glgsmc').text(obj.entCustName);
//					$('#yjzypepz_opt_jhnpezs').text(obj.initQuota);
//					$('#yjzypepz_opt_dsppezs').text(obj.approving);
//					comm.getCommBsGrid("yjzypepz_opt_ql",{domain:'gpf_res',url:'quotaStatOfPlat'},{entResNo:obj.entResNo});

//					var platData = null;
//					quota.quotaStatOfPlat({entResNo:obj.entResNo}, function(response){
//						if(response){
//							platData = response;
//						}
//					});
//					/*下拉列表*/
//					
//					var optionData = [];
//					$.each(platData, function(n, value){
//						optionData[n] = {name : value.platName, value : n, ysypes : value.assigned, wsypes : value.available};
//					});

//					$("#yjzypepz_opt_dqpt").selectList({
//						width:250,
//						optionWidth:255,
//						options:optionData
//					}).change(function(e){
//						var optionValue = $(this).attr('optionValue');
//						var jhs = $('#yjzypepz_opt_jhnpezs').text()*1; 
//			 			that.maxLength =  jhs- optionData[optionValue].ysypes;//-optionData[optionValue].wsypes ;
//			 			
//						$('#bcsqpes_zd').text('最多可申请'+( that.maxLength )+'个');
//						$('#yjzypepz_opt_ysypes').val(optionData[optionValue].ysypes);
//						$('#yjzypepz_opt_wsypes').val(optionData[optionValue].wsypes);
//						$('#yjzypepz_opt_dqpt_no').val(optionValue);
//						//清空输入框
//						$('#yjzypesqsp_opt_pfpes,#yjzypepz_opt_sqhksypes,#yjzypesqsp_opt_pfhd').val('');
//						
//					});
                    /*end*/


                    /*end*/

                    $('#yjzypesqsp_opt_pfpes').blur(function () {
                        var bcpfpes = $(this).val() * 1;

                        if (bcpfpes != '0') {

                            if (bcpfpes > $('#yjzypesqsp_opt_wsypes').val()) {
                                comm.alert({
                                    imgFlag: true,
                                    imgClass: 'tips_i',
                                    content: '您本次批复的配额数超过了未使用配额数！'
                                });
                                return;
                            }
//							else {
//								
//								$('#yjzypepz_opt_sqhksypes').val( wsypes +bcsqpes );
//								
//							}

                            /*
                             if(bcsqpes <= wsypes){
                             $('#yjzypepz_opt_sqhksypes').val(bcsqpes);
                             }
                             else{
                             comm.alert({
                             imgFlag : true,
                             imgClass : 'tips_i',
                             content : '您本次申请的配额数超过了未使用配额数！'
                             });
                             return;
                             } */
                        }
//						else{
//							$('#yjzypepz_opt_sqhksypes').val('');	
//						}
                    });

                    //加载选号页面
                    $('#zypesp_xh').off().click(function (e) {

                        if ($('.ui-tooltip') && $('.ui-tooltip').length) {
                            $("#yjzypesqsp_opt_pfhd").tooltip("destroy");
                            $("#yjzypesqsp_opt_pfpes").tooltip("destroy");
                        }

                        $('#yjzypesqsp_xhDlg').html(yjzypesqsp_xhTpl);


                        /*弹出框*/
                        $("#yjzypesqsp_xhDlg").dialog({
                            title: "批复配额号段",
                            width: "1000",
                            height: "680",
                            modal: true,
                            closeIcon: true,
                            buttons: {
                                "确定": function () {
                                    var parentDialog = this;
                                    if (!that.getSelectHSNum().count) {
                                        comm.alert({imgClass: 'tips_i', content: '批复号段为空，请选择号段'});
                                        return;
                                    }

                                    //所申请的互生号
                                    //console.log( that.getSelectHSNum().hsNumbers ) ;

                                    var haoDuanContent = '';
                                    $.each(that.haoDuan, function (key, el) {
                                        haoDuanContent += that.genHSNo(el[0]) + '~' + that.genHSNo(el[1]) + ';';
                                    });
                                    that.apprList = '';
                                    $.each(that.getSelectHSNum().hsNumbers, function (hsNos) {
                                        that.apprList += ',' + this;
                                    });
                                    if (that.apprList) {
                                        that.apprList = that.apprList.substr(1);
                                    }
                                    console.log('apprList=' + that.apprList);

                                    comm.confirm({
                                        imgClass: 'tips_i',
                                        imgFlag: true,
                                        title: '批复配额号段',
                                        width: 600,
                                        height: 400,
                                        //content: ( that.selectedFirstText.style.id < that.selectedLastText.style.id )?  that.selectedFirstText.style.id +  ' ~ ' + that.selectedLastText.style.id :  that.selectedLastText.style.id +  ' ~ ' + that.selectedFirstText.style.id,
                                        content: haoDuanContent,
                                        callOk: function () {
                                            //设置号段数量和数值
                                            $('#yjzypesqsp_opt_pfpes').val($('#xh_pfesl').text());
//											 $('#yjzypepz_opt_sqhksypes').val( $('#yjzypepz_opt_wsypes').val()*1+$('#yjzypesqsp_opt_pfpes').val()*1 );
                                            $('#yjzypesqsp_opt_pfhd').val(haoDuanContent);

                                            //清除选取记录
                                            that.selectedFirstText = null;
                                            that.selectedLastText = null;
                                            that.haoDuan = [];
                                            $(parentDialog).dialog("destroy");
                                        }
                                    });

                                },
                                "取消": function () {
                                    $(this).dialog("destroy");
                                }
                            }
                        });

                        //初始画面
                        $('#zr_number').css({'height': 580, 'width': 840});
                        that.zr_number = zrender.init(document.getElementById('zr_number'));
                        //创建表格
                        that.createGrid();
                        //创建表格中文本
                        that.createGirdText();

                        //初始化表格内容
                        that.initGirdText();

                        //创建选择框
                        that.createRectangle();

                        that.addHaoDuan();

                        var platColorIdContent = '';
                        $.each(resQuotaAppr.platList, function (key, el) {
                            platColorIdContent += '<li class="pb10"><span style="width:28px;height:17px;display:inline-block;vertical-align:middle;background:' + resQuotaAppr.colorList[key] + ';"></span>' + this + '</li>'
                        });
                        platColorIdContent += '<li class="pb10"><span style="width:28px;height:17px;display:inline-block;vertical-align:middle;background:#ffffff;"></span>未分配</li>';
                        platColorIdContent += '<li class="pb10"><span style="width:28px;height:17px;display:inline-block;vertical-align:middle;background:#c0c0c0;"></span>申请额</li>';
                        $('#platColorId').html(platColorIdContent);
                    });
                }.bind(this));
                return link;
            }
        },
        //同步配额
        opt_sync_allot: function (obj, rowIndex, colIndex, options) {
            if (colIndex == 5 && obj.status == 1 && (obj.allotSync == null || obj.allotSync == false)) {
                var link = $('<a>同步配额</a>').click(function (e) {
                    comm.confirm({
                        width: 500,
                        imgFlag: true,
                        imgClass: 'tips_ques',
                        content: '确定同步配额信息到地区平台？',
                        callOk: function () {
                            quota.syncQuotaAllot({applyId: obj.applyId}, function (resp) {
                                if (resp) {
                                    comm.yes_alert("操作成功");
                                    $('#yjzypesqsp_tb_cx').trigger('click');
                                }
                            });
                        }
                    });

                }.bind(this));
                return link;
            }
        },
        //同步路由
        opt_sync_route: function (obj, rowIndex, colIndex, options) {
            if (colIndex == 5 && obj.status == 1 && (obj.routeSync == null || obj.routeSync == false)) {
                var link = $('<a>同步路由</a>').click(function (e) {
                    comm.confirm({
                        width: 500,
                        imgFlag: true,
                        imgClass: 'tips_ques',
                        content: '确定同步路由信息到总平台？',
                        callOk: function () {
                            quota.syncQuotaRoute({applyId: obj.applyId}, function (resp) {
                                if (resp) {
                                    comm.yes_alert("操作成功");
                                    $('#yjzypesqsp_tb_cx').trigger('click');
                                }
                            });
                        }
                    });

                }.bind(this));
                return link;
            }
        },
        //生成互生号
        genHSNo: function (num) {
            var hsNoPrefix = $('#yjzypesqsp_opt_glgshsh').text().substr(0, 2),
                hsNumbers = '';
            //生成字段
            var zero = '';
            if (num.toString().length == 1) {
                zero = '00';
            } else if (num.toString().length == 2) {
                zero = '0';
            }
            return hsNoPrefix + zero + num + '000000';
        },

        createGrid: function () {
            this.zr_number.clear();
            //创建表格
            //rows
            for (var i = 0; i <= 34; i++) {
                this.zr_number.addShape(new LineShape({
                    style: {
                        xStart: this.offset,
                        yStart: 17 * i + this.offset,
                        xEnd: this.canvasWidth + this.offset,
                        yEnd: 17 * i + this.offset,
                        lineType: 'solid',
                        strokeColor: '#000',
                        lineWidth: 1
                    }
                }));
            }
            //cols
            for (var i = 0; i <= 30; i++) {
                var xStart, lineType = 'solid';

                xStart = (i != 30) ? i * 28 + this.offset : i * 28 - this.offset;

                this.zr_number.addShape(new LineShape({
                    style: {
                        xStart: xStart,
                        yStart: this.offset,
                        xEnd: xStart,
                        yEnd: this.canvasHeight - 2 - this.offset,
                        lineType: lineType,
                        strokeColor: '#000',
                        lineWidth: 1
                    }
                }));
            }

        },

        //创建表格内文本框
        createGirdText: function () {
            for (var i = 0; i <= 33; i++) {//行
                // 编辑区域
                var rectArr2 = [];
                for (var j = 0; j <= 30; j++) {
                    if (( j + 1) + i * 30 > 999) {
                        break;
                    }

                    var rect2 = new RectangleShape({
                        //zlevel:0,
                        style: {
                            id: ( j + 1) + i * 30,
                            x: j * 28 + 1,
                            y: i * 17 + 1,
                            width: 26,
                            height: 15,
                            brushType: 'fill',
                            color: '#fff',
                            strokeColor: '#fff',
                            lineWidth: 1,
                            text: ( j + 1) + i * 30,
                            textFont: '12px 宋体',
                            textPosition: 'inside',
                            textAlign: 'center',
                            textBaseline: 'middle',
                            textColor: '#000'
                        },
                        hoverable: false,
                        rowIndex: i,
                        colIndex: j
                    });
                    rectArr2.push(rect2);
                    this.zr_number.addShape(rect2);


                }
                resQuotaAppr.textArr[i] = rectArr2;
            }
        },
        //初始化表格内容文本
        initGirdText: function (obj) {
             var textObj = obj ? obj : resQuotaAppr.textObject,
                textTypeId = null,
                textBgColor = null,
                usedCount = 0;
            resQuotaAppr.platList = [];
            for (var i = 0; i <= 33; i++) {
                // 编辑区域
                for (var j = 0; j < 30; j++) {
                    if ( (j+1) + i * 30 > 999) {
                        break;
                    }
                    //根据数据type类型，设置不同色彩
                    /*  type
                     *  0、未分配
                     *  1、申请额
                     *  2、中国大陆
                     *  3、中国香港
                     *  4、中国台湾
                     *  5、中国澳门
                     */

                    switch (textObj[j + i * 30].type) {
                        case 0 : //未分配
                            textTypeId = 0;
                            textBgColor = '#ffffff';
                            break;
                        case 1 ://申请额
                            textTypeId = 1;
                            textBgColor = '#c0c0c0';
                            break;
                        default :
                            var platName = textObj[j + i * 30 ].platName;
                            if (resQuotaAppr.platList.indexOf(platName) == -1) {
                                resQuotaAppr.platList.push(platName);
                            }
                            textBgColor = resQuotaAppr.colorList[resQuotaAppr.platList.indexOf(platName)];
                            textTypeId = textObj[j+ i * 30].type;
                            usedCount++;
                            break;
                    }
                    this.textArr[i][j].style.type = textTypeId;
                    this.textArr[i][j].style.color = textBgColor;
                }
            }
            //设置已分配 数量
            $('#xh_yfpsl').text(usedCount);
            $('#xh_wfpsl').text(999 - usedCount);

        },
        //设置格子选中状态
        setSelectStatus: function (firstId, lastId) {
            var runFlag = true,
                firstColIndex = this.selectedFirstText.colIndex,
                lastColIndex = this.selectedLastText.colIndex;
            if (firstId <= lastId) {
                if (firstId == lastId && this.getSelectHSNum().count >= this.maxLength) {
                    //选单个，超计划额
                    return;
                }

                // if (this.selectedLastText && this.selectedLastText.style.type){
                // this.rectangle.moveFlag = false;
                // runFlag = false;
                // return;
                // }
                //判断是否第一列，
                for (var i = this.selectedFirstText.rowIndex; i <= this.selectedLastText.rowIndex; i++) {
                    if (!runFlag) break;
                    if (firstColIndex % 30 == 0) {
                        //从第1列开始
                        for (var j = 0; j <= 29; j++) {
                            if (( j + 1) + i * 30 > 999) {
                                break;
                            }
                            ;
                            //已分配
                            if (this.textArr[i][j].style.type > 1) {
                                //this.rectangle.moveFlag = false;
                                runFlag = false;
                                break;
                            }
                            if (this.textArr[i][j].style.id >= firstId && this.textArr[i][j].style.id <= lastId && this.textArr[i][j].style.type < 1) {
                                //未选
                                this.textArr[i][j].style.color = "#c0c0c0";
                                this.textArr[i][j].style.type = 1;
                            }
                            //超过申请额
                            if (this.getSelectHSNum().count >= this.maxLength) {
                                runFlag = false;
                                break;
                            }
                        }
                    } else {
                        //从其它列开始，分两步，先选第一行，再选二行以后的行

                        if (i == this.selectedFirstText.rowIndex) {
                            for (var j = this.selectedFirstText.colIndex; j <= 29; j++) {
                                if (( j + 1) + i * 30 > 999) {
                                    break;
                                }
                                ;
                                //已分配
                                if (this.textArr[i][j].style.type > 1) {
                                    //this.rectangle.moveFlag = false;
                                    runFlag = false;
                                    break;
                                }

                                if (this.textArr[i][j].style.id >= firstId && this.textArr[i][j].style.id <= lastId && this.textArr[i][j].style.type < 1) {
                                    //未选
                                    this.textArr[i][j].style.color = "#c0c0c0";
                                    this.textArr[i][j].style.type = 1;
                                }
                                //超过申请额
                                if (this.getSelectHSNum().count >= this.maxLength) {
                                    runFlag = false;
                                    break;
                                }
                            }
                        } else {
                            for (var j = 0; j <= 29; j++) {
                                if (( j + 1) + i * 30 > 999) {
                                    break;
                                }
                                ;
                                //已分配
                                if (this.textArr[i][j].style.type > 1) {
                                    //this.rectangle.moveFlag = false;
                                    runFlag = false;
                                    break;
                                }

                                if (this.textArr[i][j].style.id >= firstId && this.textArr[i][j].style.id <= lastId && this.textArr[i][j].style.type < 1) {
                                    //未选
                                    this.textArr[i][j].style.color = "#c0c0c0";
                                    this.textArr[i][j].style.type = 1;

                                }
                                //超过申请额
                                if (this.getSelectHSNum().count >= this.maxLength) {
                                    runFlag = false;
                                    break;
                                }
                            }

                        }
                    }
                }
            } else if (firstId > lastId) {
                //[取消选择] 从下向上，从右向左选
                for (var i = this.selectedFirstText.rowIndex; i >= this.selectedLastText.rowIndex; i--) {
                    //if (!runFlag) break;
                    if (firstColIndex % 29 == 0) {
                        //从第1列开始
                        for (var j = 29; j >= 0; j--) {
                            if (( j + 1) + i * 30 > 999) {
                                break;
                            }
                            ;
                            //console.log( this.textArr[i][j].style.type )
                            //已分配
                            if (this.textArr[i][j].style.type > 1) {
                                //runFlag = false;
                                //break;
                            }

                            if (this.textArr[i][j].style.id <= firstId && this.textArr[i][j].style.id >= lastId && this.textArr[i][j].style.type == 1) {
                                //未选
                                this.textArr[i][j].style.color = "#fff";
                                this.textArr[i][j].style.type = 0;

                            }
                        }
                    } else {
                        //从其它列开始，分两步，先选第一行，再选二行以后的行
                        if (i == this.selectedFirstText.rowIndex) {
                            for (var j = this.selectedFirstText.colIndex; j >= 0; j--) {
                                if (( j + 1) + i * 30 > 999) {
                                    break;
                                }
                                ;
                                //已分配
                                if (this.textArr[i][j].style.type > 1) {
                                    //runFlag = false;
                                    //break;
                                }
                                if (this.textArr[i][j].style.id <= firstId && this.textArr[i][j].style.id >= lastId && this.textArr[i][j].style.type == 1) {
                                    //未选
                                    this.textArr[i][j].style.color = "#fff";
                                    this.textArr[i][j].style.type = 0;

                                }
                            }
                        } else {
                            for (var j = 30; j >= 0; j--) {
                                if (( j + 1) + i * 30 > 999) {
                                    break;
                                }
                                ;
                                //已分配
                                if (this.textArr[i][j].style.type > 1) {
                                    //this.rectangle.moveFlag = false;
                                    runFlag = false;
                                    break;
                                }
                                if (this.textArr[i][j].style.id <= firstId && this.textArr[i][j].style.id >= lastId && this.textArr[i][j].style.type == 1) {
                                    //未选
                                    this.textArr[i][j].style.color = "#fff";
                                    this.textArr[i][j].style.type = 0;

                                }
                            }
                        }
                    }
                }
                this.changHaoDuan();
            }

        },
        addHaoDuan: function (firstId, lastId) {

            //待处理
            var start, end, self = this;
            this.haoDuanTemp = [];
            for (var i = 0; i <= 33; i++) {
                for (var j = 0; j < 30; j++) {
                    if (( j + 1) + i * 30 > 999) {
                        break;
                    }
                    ;
                    this.haoDuanTemp.push(this.textArr[i][j].style.type);
                }
            }
            self.haoDuan = [];
            for (var i = 0; i < 999; i++) {
                if ((this.haoDuanTemp[i - 1] != 1 ) && this.haoDuanTemp[i] == 1 && this.haoDuanTemp[i + 1] == 1) {
                    start = i + 1;
                } else if (this.haoDuanTemp[i - 1] == 1 && this.haoDuanTemp[i] == 1 && this.haoDuanTemp[i + 1] != 1) {
                    end = i + 1;
                    var exists = false;
                    $.each(self.haoDuan, function (key, el) {
                        if (el.sort().toString() == [start, end].sort().toString()) {
                            exists = true;
                            return true;
                        }
                    });
                    if (!exists) {
                        self.haoDuan.push([start, end]);
                    }
                } else if ((this.haoDuanTemp[i - 1] != 1 ) && this.haoDuanTemp[i] == 1 && this.haoDuanTemp[i + 1] != 1) {
                    self.haoDuan.push([i + 1, i + 1]);
                }
            }

        },

        changHaoDuan: function () {

            var self = this,
                lastId = this.selectedFirstText.style.id,
                firstId = this.selectedLastText.style.id;
            $.each(this.haoDuan, function (key, el) {
                if (!el || !el.length) return false;
                if (firstId <= el[0] && lastId >= el[1]) {
                    //全包围某一个
                    self.haoDuan.splice(key, 1);
                } else if (firstId >= el[0] && firstId <= el[1]) {
                    //debugger
                    self.haoDuan[key][1] = firstId - 1;
                }
            });
            if (!self.getSelectHSNum().count) {
                self.haoDuan = [];
            }

        },

        //清除格子选中状态
        delSelectStatus: function () {
            for (var i = 0; i <= 33; i++) {
                for (var j = 0; j <= 30; j++) {
                    if (( j + 1) + i * 30 > 999) {
                        break;
                    }
                    ;
                    if (this.textArr[i][j].style.type <= 1) {
                        //未选
                        this.textArr[i][j].style.color = "#fff";
                        this.textArr[i][j].style.type = 0;
                    }
                }
            }
        },
        getSelectHSNum: function () {
            var selectObj = {
                count: 0,
                hsNumbers: [],
                startNoFlag: false
            };
            for (var i = 0; i <= 33; i++) {
                for (var j = 0; j < 30; j++) {
                    if ((j+1) + i * 30 > 999) {
                        break;
                    }
                    if (resQuotaAppr.textArr[i][j].style.type == 1) {
                        //设置起号
                        if (!selectObj.startNoFlag) {
                            $('#xh_qshsl').text(resQuotaAppr.textArr[i][j].style.id);
                            selectObj.startNoFlag = true;
                        }
                        selectObj.count++;
                        if (selectObj.count <= this.maxLength) {
                            this.tips('close');
                        }
                        selectObj.hsNumbers.push(this.genHSNo(this.textArr[i][j].style.id));
                    }
                }
            }
            $('#xh_pfesl').text(selectObj.count);
            if (!selectObj.count) {
                $('#xh_qshsl').text('');
            }
            return selectObj;
        },

        tips: function (order) {
            return;
            var tips = $('#zr_number').tooltip({
                position: {
                    my: "center top",
                    at: "center top"
                }
            });
            if (order == 'open') {
                $('#zr_number').attr('title', "超出可申请配额总数");
            } else {
                $('#zr_number').attr('title') && $('#zr_number').removeAttr('title');

            }
            tips.tooltip(order);
        },

        //	创建选择框
        createRectangle: function () {
            //创建选择框
            resQuotaAppr.rectangle.rect = new RectangleShape({
                zlevel: 100,
                style: {
                    x: 0,
                    y: 0,
                    width: 0,
                    height: 0,
                    brushType: 'stroke',
                    strokeColor: '#626262',
                    lineWidth: 1 + resQuotaAppr.offset,
                    lineType: 'dashed'
                },
                hoverable: false
            });
            resQuotaAppr.zr_number.addShape(resQuotaAppr.rectangle.rect);


            $('#zr_number canvas:eq(0)').mousedown(function (e) {
                if (e.button == 1) {
                    return;
                }
                //如果已选择，且鼠标在所选的某一矩形内
                var currPos = resQuotaAppr.getPos($('#zr_number canvas:eq(0)'), e);

                for (var i = 0; i <= 33; i++) {
                    for (var j = 0; j < 30; j++) {
                        if (!resQuotaAppr.textArr[i][j]) {
                            break;
                        }
                        var classP1 = {x: resQuotaAppr.textArr[i][j].style.x, y: resQuotaAppr.textArr[i][j].style.y},
                            classP2 = {x: resQuotaAppr.textArr[i][j].style.x + resQuotaAppr.textArr[i][j].style.width, y: resQuotaAppr.textArr[i][j].style.y},
                            classP3 = {x: resQuotaAppr.textArr[i][j].style.x + resQuotaAppr.textArr[i][j].style.width, y: resQuotaAppr.textArr[i][j].style.y + resQuotaAppr.textArr[i][j].style.height},
                            classP4 = {x: resQuotaAppr.textArr[i][j].style.x, y: resQuotaAppr.textArr[i][j].style.y + resQuotaAppr.textArr[i][j].style.height},
                            oldColor = resQuotaAppr.textArr[i][j].style.color;

                        if (resQuotaAppr.pointInPoly(currPos, [classP1, classP2, classP3, classP4])) {


                            resQuotaAppr.selectedFirstText = resQuotaAppr.textArr[i][j];

                            //如果已申请，则不选中
                            if (resQuotaAppr.textArr[i][j].style.type > 0) {
                                return;
                            }
                            resQuotaAppr.rectangle.moveFlag = 2;
                            //self.textArr[i][j].style.color = "#c0c0c0" ;
                            $.extend(resQuotaAppr.rectangle.rect.style, {
                                x: resQuotaAppr.textArr[i][j].style.x,
                                y: resQuotaAppr.textArr[i][j].style.y,
                                width: resQuotaAppr.textArr[i][j].style.width,
                                height: resQuotaAppr.textArr[i][j].style.height,
                                opacity: 1
                            });
                            //清除所有选中
                            //self.delSelectStatus();

                            //选中一个
                            //self.selectedFirstText.style.color="#c0c0c0" ;
                            //self.selectedFirstText.style.type=1;
                            //清空申请数量
                            //$('#xh_pfesl').text(0);
                        } else {
                            if (e.button != 2 && !resQuotaAppr.textArr[i][j].style.type) {
                                resQuotaAppr.textArr[i][j].style.color = '#fff';
                                resQuotaAppr.textArr[i][j].selected = false;
                            }
                        }
                    }
                }
                resQuotaAppr.rectangle.x = currPos.x;
                resQuotaAppr.rectangle.y = currPos.y;
                if (e.button != 2) {
                    resQuotaAppr.rectangle.selectCount = 0;
                }
                resQuotaAppr.zr_number.render();

            });

            $('#zr_number canvas:eq(0)').mousemove(function (e, o) {
                if (!resQuotaAppr.rectangle.moveFlag || e.button == 2) {
                    return;
                }
                var currPos = resQuotaAppr.getPos($('#zr_number canvas:eq(0)'), e);
                var posX = currPos.x, posY = currPos.y,
                    deltaX = posX - resQuotaAppr.rectangle.x, deltaY = posY - resQuotaAppr.rectangle.y;
                if (resQuotaAppr.rectangle.moveFlag == 2) {
                    //框选
                    $.extend(resQuotaAppr.rectangle.rect.style, {
                        x: resQuotaAppr.rectangle.x,
                        y: resQuotaAppr.rectangle.y,
                        width: posX - resQuotaAppr.rectangle.x,
                        height: posY - resQuotaAppr.rectangle.y
                    });
                    //框选，改变选中的背景色
                    for (var i = 0; i <= 33; i++) {
                        for (var j = 0; j <= 30; j++) {
                            if (!resQuotaAppr.textArr[i][j]) {
                                break;
                            }
                            var classP1 = {x: resQuotaAppr.textArr[i][j].style.x, y: resQuotaAppr.textArr[i][j].style.y},
                                classP2 = {x: resQuotaAppr.textArr[i][j].style.x + resQuotaAppr.textArr[i][j].style.width, y: resQuotaAppr.textArr[i][j].style.y},
                                classP3 = {x: resQuotaAppr.textArr[i][j].style.x + resQuotaAppr.textArr[i][j].style.width, y: resQuotaAppr.textArr[i][j].style.y + resQuotaAppr.textArr[i][j].style.height},
                                classP4 = {x: resQuotaAppr.textArr[i][j].style.x, y: resQuotaAppr.textArr[i][j].style.y + resQuotaAppr.textArr[i][j].style.height};

                            if (resQuotaAppr.pointInPoly(currPos, [classP1, classP2, classP3, classP4])) {
                                if (resQuotaAppr.textArr[i][j] != resQuotaAppr.selectedLastText) {
                                    resQuotaAppr.selectedLastText = resQuotaAppr.textArr[i][j];

                                    //超过申请额
                                    if (resQuotaAppr.getSelectHSNum().count >= resQuotaAppr.maxLength && resQuotaAppr.selectedLastText.style.id > resQuotaAppr.selectedFirstText.style.id) {
                                        resQuotaAppr.rectangle.moveFlag = false;
                                        resQuotaAppr.tips('open');
                                        return;
                                    } else {
                                        resQuotaAppr.setSelectStatus(resQuotaAppr.selectedFirstText.style.id, resQuotaAppr.selectedLastText.style.id);
                                    }
                                }
                            }
                        }
                    }
                }
                resQuotaAppr.zr_number.render();
            });
            $('#zr_number canvas:eq(0)').mouseup(function (e) {
                resQuotaAppr.getPos($('#zr_number canvas:eq(0)'), e);
               /* var currPos = resQuotaAppr.getPos($('#zr_number canvas:eq(0)'), e),
                    classP1, classP2, classP3, classP4;
                for (var i = 0; i < resQuotaAppr.personCount; i++) {
                    for (var j = 0; j < 31; j++) {
                        classP1 = {x: resQuotaAppr.textArr[i][j].style.x, y: resQuotaAppr.textArr[i][j].style.y} ,
                            classP2 = {x: resQuotaAppr.textArr[i][j].style.x + resQuotaAppr.textArr[i][j].style.width, y: resQuotaAppr.textArr[i][j].style.y} ,
                            classP3 = {x: resQuotaAppr.textArr[i][j].style.x + resQuotaAppr.textArr[i][j].style.width, y: resQuotaAppr.textArr[i][j].style.y + resQuotaAppr.textArr[i][j].style.height} ,
                            classP4 = {x: resQuotaAppr.textArr[i][j].style.x, y: resQuotaAppr.textArr[i][j].style.y + resQuotaAppr.textArr[i][j].style.height};
                        //判断选中的数量
                        if (resQuotaAppr.textArr[i][j].selected) {
                            resQuotaAppr.rectangle.selectCount += 1;
                        }

                        if (resQuotaAppr.pointInPoly(currPos, [classP1, classP2, classP3, classP4])) {
                            var dd = resQuotaAppr.selectedFirstText == resQuotaAppr.textArr[i][j];
                            if (resQuotaAppr.rectangle.moveFlag == 2) {
                                if (resQuotaAppr.selectedFirstText == resQuotaAppr.textArr[i][j]) {
                                    //可框选标志
                                    resQuotaAppr.textArr[i][j].selected = true;
                                }
                            }
                        } else {
                            //self.textArr[i][j].selected = undefined ;
                        }
                    }
                }*/
                //取消选择标志
                resQuotaAppr.rectangle.moveFlag = 0;
                $.extend(resQuotaAppr.rectangle.rect.style, {
                    x: 0,
                    y: 0,
                    width: 0,
                    height: 0,
                    opacity: 0
                });
                //设置号段
                resQuotaAppr.addHaoDuan();
                resQuotaAppr.selectedLastText = null;
                resQuotaAppr.selectedFirstText = null;

                resQuotaAppr.zr_number.render();
            });
        },
        getPos: function (canvas, evt) {
            var rect = canvas[0].getBoundingClientRect();
            return {
                x: evt.clientX - rect.left * (canvas[0].width / rect.width),
                y: evt.clientY - rect.top * (canvas[0].height / rect.height)
            };
        },
        pointInPoly: function (pt, poly) {
            for (var c = false, i = -1, l = poly.length, j = l - 1; ++i < l; j = i)
                ((poly[i].y <= pt.y && pt.y < poly[j].y) || (poly[j].y <= pt.y && pt.y < poly[i].y))
                && (pt.x < (poly[j].x - poly[i].x) * (pt.y - poly[i].y) / (poly[j].y - poly[i].y) + poly[i].x)
                && (c = !c);
            return c;
        },
        isOverLap: function (p1, p2, q1, q2) {
            var pMaxX = Math.max(p1.x, p2.x);
            var pMaxY = Math.max(p1.y, p2.y);
            var pMinX = Math.min(p1.x, p2.x);
            var pMinY = Math.min(p1.y, p2.y);
            var qMinX = Math.min(q1.x, q2.x);
            var qMinY = Math.min(q1.y, q2.y);
            var qMaxX = Math.max(q1.x, q2.x);
            var qMaxY = Math.max(q1.y, q2.y);
            return !(pMaxX < qMinX || pMaxY < qMinY || qMaxX < pMinX || qMaxY < pMinY);
            //如果矩形1中最大的X轴点都小于矩形2的最小的X轴，那肯定两个矩形不重叠，后同...
        },
        validate: function () {
            return comm.valid({
                formID: '#yjzypesqsp_optForm',
                left: 200,
                rules: {
                    yjzypesqsp_opt_pfpes: {
                        required: true
                    },
                    yjzypesqsp_opt_pfhd: {
                        required: true
                    }
                },
                messages: {
                    yjzypesqsp_opt_pfpes: {
                        required: '必填'
                    },
                    yjzypesqsp_opt_pfhd: {
                        required: '必填'
                    }
                }
            });
        },
        /**
         * 初始化下拉框
         * @param objId 需要绑定的下拉框元素
         * @param width 选择框宽度（可选参数）
         * @param defaultVal 默认值（可选参数）
         * @param defaultOptions 默认选项
         */
        initSelectAllPlatList: function (objId, width, defaultVal, defaultOptions) {
            var objArray = null;
            quota.platListAll(null, function (response) {
                objArray = response;
            });
            var options = [];
            if (defaultOptions) {
                options.push(defaultOptions);
            }
            for (key in objArray) {
                options.push({name: objArray[key].entCustName, value: objArray[key].platNo});
            }
            var content = {options: options};
            if (width) {
                content.width = width;
                content.optionWidth = width;
            }
            var select = $(objId).selectList(content);
            if (defaultVal != null && defaultVal != undefined) {
                select.selectListValue(defaultVal);
            }
            return select;
        }
    };
    return resQuotaAppr;
}); 

 
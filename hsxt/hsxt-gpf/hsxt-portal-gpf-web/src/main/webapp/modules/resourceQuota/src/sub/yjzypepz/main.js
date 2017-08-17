define(['text!resourceQuotaTpl/sub/yjzypepz/yjzypepz.html',
    'text!resourceQuotaTpl/sub/yjzypepz/yjzypepz_view.html',
    'text!resourceQuotaTpl/sub/yjzypepz/yjzypepz_opt.html',
    'text!resourceQuotaTpl/sub/yjzypepz/yjzypepz_xh.html',
    'zrender', 'zrender/shape/Rectangle', 'zrender/shape/Line', 'zrender/shape/Text',
    'resourceQuotaDat/quota'
], function (yjzypepzTpl, yjzypepz_viewTpl, yjzypepz_optTpl, yjzypepz_xhTpl, zrender, RectangleShape, LineShape, TextSharp, quota) {
    var quotaStatOfMentGrid = null;
    //var quotaStatOfPlatGrid = null;
    var resourceQuota = {
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
        applyList: null, //申请互生号清单
        colorList: ['#FF2D2D', '#FF95CA', '#FF44FF', '#613030', '#4A4AFF', '#00E3E3', '#02C874', '#007500', '#F9F900', '#3D7878', '#000079', '#600030'],
        platList: [],
        showPage: function (tabid) {
            $('#main-content > div[data-contentid="' + tabid + '"]').html(_.template(yjzypepzTpl));

            resourceQuota.loadQuotaStatOfMentGrid();
            resourceQuota.opt_query_quotaStat();
        },
        //加载表格
        loadQuotaStatOfMentGrid: function () {
            quotaStatOfMentGrid = comm.getCommBsGrid("yjzypepz_ql", {domain: 'gpf_res', url: 'quotaStatOfMent'}, {}, resourceQuota.opt_apply, resourceQuota.opt_view);
        },
        //查询
        opt_query_quotaStat: function () {
            $("#yjzypepz_tb_cx").click(function () {
                var params = {
                    entResNo: $("#search_entResNo").val(), //地区平台互生号
                    entCustName: $("#search_entCustName").val()//地区平台名称
                };
                quotaStatOfMentGrid.search(params);
            });
        },

        opt_view: function (obj) {
            var viewLink = $('<a>查看</a>').click(function (e) {
                $('#yjzypepzDlg').html(yjzypepz_viewTpl);

                /*弹出框*/
                $("#yjzypepzDlg").dialog({
                    title: "查看一级资源配额",
                    width: "1000",
                    height: "500",
                    modal: true,
                    closeIcon: true,
                    buttons: {
                        "配额申请": function () {
                            $(this).dialog("destroy");
                            resourceQuota.opt_view_apply(obj);
                        },
                        "关闭": function () {
                            $(this).dialog("destroy");
                        }
                    }
                });

                $('#yjzypepz_view_glgshsh').text(obj.entResNo);
                $('#yjzypepz_view_glgsmc').text(obj.entCustName);
                $('#yjzypepz_view_jhnpezs').text(obj.initQuota);
                $('#yjzypepz_view_dsppezs').text(obj.approving);
                comm.getCommBsGrid("yjzypepz_view_ql", {domain: 'gpf_res', url: 'quotaStatOfPlat'}, {entResNo: obj.entResNo});
            }.bind(this));
            return viewLink;
        },
        opt_view_apply: function (obj) {

            var that = resourceQuota;
            $('#yjzypepzDlg').html(yjzypepz_optTpl);

            quota.queryIdTyp({manageEntResNo: obj.entResNo}, function (response) {
                if (response) {
                    resourceQuota.textObject = response;
                }
            });

            /*弹出框*/
            $("#yjzypepzDlg").dialog({
                title: "一级资源配额申请",
                width: "1000",
                height: "600",
                modal: true,
                closeIcon: true,
                buttons: {
                    "确定": function () {
                        if (!that.validate()) {
                            return;
                        }
                        else {
                            var loginInfo = comm.getLoginInfo();
                            var params = {
                                entResNo: $('#yjzypepz_opt_glgshsh').text(),
                                entCustName: $('#yjzypepz_opt_glgsmc').text(),
                                platNo: $('#yjzypepz_opt_dqpt_no').val(),
                                applyType: 1,//增加
                                applyNum: $('#yjzypepz_opt_bcsqpes').val(),
                                applyRange: $('#yjzypepz_opt_sqpehd').val(),
                                applyList: that.applyList,
                                reqOptId: loginInfo.operatorId,
                                reqOptName: loginInfo.name
                            };
                            quota.applyQuota(params, function (response) {
                                if (response) {
                                    comm.yes_alert("操作成功");
                                    $('#yjzypepzDlg').dialog("destroy");
                                    $('#yjzypepz_tb_cx').trigger('click');
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

            $('#yjzypepz_opt_glgshsh').text(obj.entResNo);
            $('#yjzypepz_opt_glgsmc').text(obj.entCustName);
            $('#yjzypepz_opt_jhnpezs').text(obj.initQuota);
            $('#yjzypepz_opt_dsppezs').text(obj.approving);
            comm.getCommBsGrid("yjzypepz_opt_ql", {domain: 'gpf_res', url: 'quotaStatOfPlat'}, {entResNo: obj.entResNo});

            var platData = null;
            quota.quotaStatOfPlat({entResNo: obj.entResNo}, function (response) {
                if (response) {
                    platData = response;
                }
            });

            /*下拉列表*/
            var optionData = [];
            $.each(platData, function (n, value) {
                optionData[n] = {name: value.platName, value: n, ysypes: value.assigned, jhnpes: value.initQuota, ptdm: value.platNo};
            });

            $("#yjzypepz_opt_dqpt").selectList({
                width: 250,
                optionWidth: 255,
                options: optionData
            }).change(function (e) {
                var optionValue = $(this).attr('optionValue');
                var jhs = optionData[optionValue].jhnpes;
                that.maxLength = jhs - optionData[optionValue].ysypes;//-optionData[optionValue].wsypes ;

                $('#bcsqpes_zd').text('最多可申请' + ( that.maxLength ) + '个');
                $('#yjzypepz_opt_jhnpes').val(optionData[optionValue].jhnpes);
                $('#yjzypepz_opt_ysypes').val(optionData[optionValue].ysypes);
                $('#yjzypepz_opt_ksqpes').val(that.maxLength);
                $('#yjzypepz_opt_dqpt_no').val(optionData[optionValue].ptdm);
                //清空输入框
                $('#yjzypepz_opt_bcsqpes,#yjzypepz_opt_sqhksypes,#yjzypepz_opt_sqpehd').val('');
            });

            $('#yjzypepz_opt_bcsqpes').blur(function () {
                var jhes = $('#yjzypepz_opt_jhnpezs').text() * 1,
//				    wsypes = $('#yjzypepz_opt_ksqpes').val()*1 ,
                    ysypes = $('#yjzypepz_opt_ysypes').val() * 1,
                    bcsqpes = $(this).val() * 1;

                if (jhes == '') {
                    comm.alert({
                        imgFlag: true,
                        imgClass: 'tips_i',
                        content: '请先选择地区平台！'
                    });
                    return;
                }

                if (bcsqpes != '0') {

                    if (jhes < ysypes + bcsqpes) {
                        comm.alert({
                            imgFlag: true,
                            imgClass: 'tips_i',
                            content: '您本次申请的配额数超过了未使用配额数！'
                        });
                        return;
                    }
//					else {
//						
//						$('#yjzypepz_opt_sqhksypes').val( wsypes +bcsqpes );
//						
//					}

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
//				else{
//					$('#yjzypepz_opt_sqhksypes').val('');	
//				}
            });

            //加载选号页面
            $('#zypesq_xh').off().click(function (e) {
                if (!$('#yjzypepz_opt_dqpt').val()) {
                    comm.alert({imgClass: 'tips_i', content: '请选择地区平台'});
                    return;
                }

                if ($('.ui-tooltip') && $('.ui-tooltip').length) {
                    $("#yjzypepz_opt_sqpehd").tooltip("destroy");
                    $("#yjzypepz_opt_bcsqpes").tooltip("destroy");
                }

                $('#yjzypepz_xhDlg').html(yjzypepz_xhTpl);

                /*弹出框*/
                $("#yjzypepz_xhDlg").dialog({
                    title: "申请配额号段",
                    width: "1000",
                    height: "680",
                    modal: true,
                    closeIcon: true,
                    buttons: {
                        "确定": function () {
                            var parentDialog = this;
                            if (!that.getSelectHSNum().count) {
                                comm.alert({imgClass: 'tips_i', content: '申请号段为空，请选择号段'});
                                return;
                            }

                            //所申请的互生号
                            //console.log( that.getSelectHSNum().hsNumbers ) ;

                            var haoDuanContent = '';
                            $.each(that.haoDuan, function (key, el) {
                                haoDuanContent += that.genHSNo(el[0]) + '~' + that.genHSNo(el[1]) + ';';
                            });
                            that.applyList = '';
                            $.each(that.getSelectHSNum().hsNumbers, function (hsNos) {
                                that.applyList += ',' + this;
                            });
                            if (that.applyList) {
                                that.applyList = that.applyList.substr(1);
                            }
                            console.log('applyList=' + that.applyList);

                            comm.confirm({
                                imgClass: 'tips_i',
                                imgFlag: true,
                                title: '申请配额号段',
                                width: 600,
                                height: 400,
                                //content: ( that.selectedFirstText.style.id < that.selectedLastText.style.id )?  that.selectedFirstText.style.id +  ' ~ ' + that.selectedLastText.style.id :  that.selectedLastText.style.id +  ' ~ ' + that.selectedFirstText.style.id,
                                content: haoDuanContent,
                                callOk: function () {
                                    //设置号段数量和数值
                                    $('#yjzypepz_opt_bcsqpes').val($('#xh_sqesl').text());
//									 $('#yjzypepz_opt_sqhksypes').val( $('#yjzypepz_opt_ksqpes').val()*1+$('#yjzypepz_opt_bcsqpes').val()*1 );
                                    $('#yjzypepz_opt_sqpehd').val(haoDuanContent);

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
                //创建选择框
                that.createRectangle();
                //初始化表格内容
                that.initGirdText();

                var platColorIdContent = '';
                $.each(resourceQuota.platList, function (key, el) {
                    platColorIdContent += '<li class="pb10"><span style="width:28px;height:17px;display:inline-block;vertical-align:middle;background:' + resourceQuota.colorList[key] + ';"></span>' + this + '</li>'
                });
                platColorIdContent += '<li class="pb10"><span style="width:28px;height:17px;display:inline-block;vertical-align:middle;background:#ffffff;"></span>未分配</li>';
                platColorIdContent += '<li class="pb10"><span style="width:28px;height:17px;display:inline-block;vertical-align:middle;background:#c0c0c0;"></span>申请额</li>';
                $('#platColorId').html(platColorIdContent);
            });
        },
        opt_apply: function (obj) {
            if(obj.available&&obj.available>0) {
                return $('<a>配额申请</a>').click(function (e) {
                    resourceQuota.opt_view_apply(obj);
                }.bind(this));
            }
        },
        //生成互生号
        genHSNo: function (num) {
            var hsNoPrefix = $('#yjzypepz_opt_glgshsh').text().substr(0, 2),
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
            for (var i = 0; i <= 33; i++) {
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
                this.textArr[i] = rectArr2;
            }
        },
        //初始化表格内容文本
        initGirdText: function (obj) {
            var textObj = obj ? obj : resourceQuota.textObject,
                textTypeId = null,
                textBgColor = null,
                usedCount = 0;
            resourceQuota.platList = [];
            for (var i = 0; i <= 33; i++) {
                // 编辑区域
                for (var j = 0; j < 30; j++) {
                    if (( j + 1) + i * 30 > 999) {
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
                        case 0 :
                            textTypeId = 0;
                            textBgColor = '#ffffff';
                            break;
                        case 1 :
                            textTypeId = 1;
                            textBgColor = '#c0c0c0';
                            break;
                        default :
                            platName = textObj[j + i * 30].platName;
                            if (resourceQuota.platList.indexOf(platName) == -1) {
                                resourceQuota.platList.push(platName);
                            }
                            textBgColor = resourceQuota.colorList[resourceQuota.platList.indexOf(platName)];
                            textTypeId = textObj[j + i * 30].type;
                            //textBgColor = '#ff00ff';
                            usedCount++;
                            break;
                    }
                    resourceQuota.textArr[i][j].style.type = textTypeId;
                    resourceQuota.textArr[i][j].style.color = textBgColor;
                }
            }
            //设置已分配 数量
            $('#xh_yfpsl').text(usedCount);
            $('#xh_wfpsl').text(999 - usedCount);

        },
        //设置格子选中状态
        setSelectStatus: function (firstId, lastId) {
            var runFlag = true,
                firstColIndex = resourceQuota.selectedFirstText.colIndex,
                lastColIndex = resourceQuota.selectedLastText.colIndex;
            if (firstId <= lastId) {
                if (firstId == lastId && resourceQuota.getSelectHSNum().count >= resourceQuota.maxLength) {
                    //选单个，超计划额
                    return;
                }

                //判断是否第一列，
                for (var i = resourceQuota.selectedFirstText.rowIndex; i <= resourceQuota.selectedLastText.rowIndex; i++) {
                    if (!runFlag) break;
                    if (firstColIndex % 30 == 0) {
                        //从第1列开始
                        for (var j = 0; j < 30; j++) {
                            if (( j + 1) + i * 30 > 999) {
                                break;
                            }
                            //已分配
                            if (resourceQuota.textArr[i][j].style.type > 1) {
                                //this.rectangle.moveFlag = false;
                                runFlag = false;
                                break;
                            }
                            if (resourceQuota.textArr[i][j].style.id >= firstId && resourceQuota.textArr[i][j].style.id <= lastId && resourceQuota.textArr[i][j].style.type < 1) {
                                //未选
                                resourceQuota.textArr[i][j].style.color = "#c0c0c0";
                                resourceQuota.textArr[i][j].style.type = 1;
                            }
                            //超过申请额
                            if (resourceQuota.getSelectHSNum().count >= resourceQuota.maxLength) {
                                runFlag = false;
                                break;
                            }
                        }
                    } else {
                        //从其它列开始，分两步，先选第一行，再选二行以后的行

                        if (i == resourceQuota.selectedFirstText.rowIndex) {
                            for (var j = resourceQuota.selectedFirstText.colIndex; j < 30; j++) {
                                if (( j + 1) + i * 30 > 999) {
                                    break;
                                }
                                //已分配
                                if (resourceQuota.textArr[i][j].style.type > 1) {
                                    //this.rectangle.moveFlag = false;
                                    runFlag = false;
                                    break;
                                }

                                if (resourceQuota.textArr[i][j].style.id >= firstId && resourceQuota.textArr[i][j].style.id <= lastId && resourceQuota.textArr[i][j].style.type < 1) {
                                    //未选
                                    resourceQuota.textArr[i][j].style.color = "#c0c0c0";
                                    resourceQuota.textArr[i][j].style.type = 1;
                                }
                                //超过申请额
                                if (resourceQuota.getSelectHSNum().count >= resourceQuota.maxLength) {
                                    runFlag = false;
                                    break;
                                }
                            }
                        } else {
                            for (var j = 0; j < 30; j++) {
                                if (( j + 1) + i * 30 > 999) {
                                    break;
                                }
                                //已分配
                                if (resourceQuota.textArr[i][j].style.type > 1) {
                                    //this.rectangle.moveFlag = false;
                                    runFlag = false;
                                    break;
                                }

                                if (resourceQuota.textArr[i][j].style.id >= firstId && resourceQuota.textArr[i][j].style.id <= lastId && resourceQuota.textArr[i][j].style.type < 1) {
                                    //未选
                                    resourceQuota.textArr[i][j].style.color = "#c0c0c0";
                                    resourceQuota.textArr[i][j].style.type = 1;

                                }
                                //超过申请额
                                if (resourceQuota.getSelectHSNum().count >= resourceQuota.maxLength) {
                                    runFlag = false;
                                    break;
                                }
                            }

                        }
                    }
                }
            } else if (firstId > lastId) {
                //[取消选择] 从下向上，从右向左选
                for (var i = resourceQuota.selectedFirstText.rowIndex; i >= resourceQuota.selectedLastText.rowIndex; i--) {
                    //if (!runFlag) break;
                    if (firstColIndex % 29 == 0) {
                        //从第1列开始
                        for (var j = 29; j >= 0; j--) {
                            if (( j + 1) + i * 30 > 999) {
                                break;
                            }
                            //console.log( this.textArr[i][j].style.type )
                            //已分配
                            if (resourceQuota.textArr[i][j].style.type > 1) {
                                //runFlag = false;
                                //break;
                            }

                            if (resourceQuota.textArr[i][j].style.id <= firstId && resourceQuota.textArr[i][j].style.id >= lastId && resourceQuota.textArr[i][j].style.type == 1) {
                                //未选
                                resourceQuota.textArr[i][j].style.color = "#fff";
                                resourceQuota.textArr[i][j].style.type = 0;

                            }
                        }
                    } else {
                        //从其它列开始，分两步，先选第一行，再选二行以后的行
                        if (i == resourceQuota.selectedFirstText.rowIndex) {
                            for (var j = resourceQuota.selectedFirstText.colIndex; j >= 0; j--) {
                                if (( j + 1) + i * 30 > 999) {
                                    break;
                                }
                                //已分配
                                if (resourceQuota.textArr[i][j].style.type > 1) {
                                    //runFlag = false;
                                    //break;
                                }
                                if (resourceQuota.textArr[i][j].style.id <= firstId && resourceQuota.textArr[i][j].style.id >= lastId && resourceQuota.textArr[i][j].style.type == 1) {
                                    //未选
                                    resourceQuota.textArr[i][j].style.color = "#fff";
                                    resourceQuota.textArr[i][j].style.type = 0;

                                }
                            }
                        } else {
                            for (var j = 30; j >= 0; j--) {
                                if (( j + 1) + i * 30 > 999) {
                                    break;
                                }
                                //已分配
                                if (resourceQuota.textArr[i][j].style.type > 1) {
                                    //this.rectangle.moveFlag = false;
                                    runFlag = false;
                                    break;
                                }
                                if (resourceQuota.textArr[i][j].style.id <= firstId && this.textArr[i][j].style.id >= lastId && this.textArr[i][j].style.type == 1) {
                                    //未选
                                    resourceQuota.textArr[i][j].style.color = "#fff";
                                    resourceQuota.textArr[i][j].style.type = 0;
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
            var start, end;
            resourceQuota.haoDuanTemp = [];
            for (var i = 0; i <= 33; i++) {
                for (var j = 0; j < 30; j++) {
                    if (( j + 1) + i * 30 > 999) {
                        break;
                    }
                    resourceQuota.haoDuanTemp.push(resourceQuota.textArr[i][j].style.type);
                }
            }
            resourceQuota.haoDuan = [];
            for (var i = 0; i < 999; i++) {
                if ((resourceQuota.haoDuanTemp[i - 1] != 1 ) && resourceQuota.haoDuanTemp[i] == 1 && resourceQuota.haoDuanTemp[i + 1] == 1) {
                    start = i + 1;
                } else if (resourceQuota.haoDuanTemp[i - 1] == 1 && resourceQuota.haoDuanTemp[i] == 1 && resourceQuota.haoDuanTemp[i + 1] != 1) {
                    end = i + 1;
                    var exists = false;
                    $.each(resourceQuota.haoDuan, function (key, el) {
                        if (el.sort().toString() == [start, end].sort().toString()) {
                            exists = true;
                            return true;
                        }
                    });
                    if (!exists) {
                        resourceQuota.haoDuan.push([start, end]);
                    }
                } else if ((resourceQuota.haoDuanTemp[i - 1] != 1 ) && resourceQuota.haoDuanTemp[i] == 1 && resourceQuota.haoDuanTemp[i + 1] != 1) {
                    resourceQuota.haoDuan.push([i + 1, i + 1]);
                }
            }

        },

        changHaoDuan: function () {

            var lastId = resourceQuota.selectedFirstText.style.id,
                firstId = resourceQuota.selectedLastText.style.id;
            $.each(resourceQuota.haoDuan, function (key, el) {
                if (!el || !el.length) return false;
                if (firstId <= el[0] && lastId >= el[1]) {
                    //全包围某一个
                    resourceQuota.haoDuan.splice(key, 1);
                } else if (firstId >= el[0] && firstId <= el[1]) {
                    //debugger
                    resourceQuota.haoDuan[key][1] = firstId - 1;
                }
            });
            if (!resourceQuota.getSelectHSNum().count) {
                resourceQuota.haoDuan = [];
            }

        },

        //清除格子选中状态
        delSelectStatus: function () {
            for (var i = 0; i <= 33; i++) {
                for (var j = 0; j <= 30; j++) {
                    if (( j + 1) + i * 30 > 999) {
                        break;
                    }
                    if (resourceQuota.textArr[i][j].style.type <= 1) {
                        //未选
                        resourceQuota.textArr[i][j].style.color = "#fff";
                        resourceQuota.textArr[i][j].style.type = 0;
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
                    if (( j + 1) + i * 30 > 999) {
                        break;
                    }
                    if (this.textArr[i][j].style.type == 1) {
                        //设置起号
                        if (!selectObj.startNoFlag) {
                            $('#xh_qshsl').text(resourceQuota.textArr[i][j].style.id);
                            selectObj.startNoFlag = true;
                        }
                        selectObj.count++;
                        if (selectObj.count <= resourceQuota.maxLength) {
                            resourceQuota.tips('close');
                        }
                        selectObj.hsNumbers.push(resourceQuota.genHSNo(resourceQuota.textArr[i][j].style.id));
                    }
                }
            }
            $('#xh_sqesl').text(selectObj.count);
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
            resourceQuota.rectangle.rect = new RectangleShape({
                zlevel: 100,
                style: {
                    x: 0,
                    y: 0,
                    width: 0,
                    height: 0,
                    brushType: 'stroke',
                    strokeColor: '#626262',
                    lineWidth: 1 + resourceQuota.offset,
                    lineType: 'dashed'
                },
                hoverable: false
            });
            resourceQuota.zr_number.addShape(resourceQuota.rectangle.rect);


            $('#zr_number canvas:eq(0)').mousedown(function (e) {
                if (e.button == 1) {
                    return;
                }
                //如果已选择，且鼠标在所选的某一矩形内
                var currPos = resourceQuota.getPos($('#zr_number canvas:eq(0)'), e);

                for (var i = 0; i <= 33; i++) {
                    for (var j = 0; j <= 30; j++) {
                        if (!resourceQuota.textArr[i][j]) {
                            break;
                        }
                        var classP1 = {x: resourceQuota.textArr[i][j].style.x, y: resourceQuota.textArr[i][j].style.y},
                            classP2 = {x: resourceQuota.textArr[i][j].style.x + resourceQuota.textArr[i][j].style.width, y: resourceQuota.textArr[i][j].style.y},
                            classP3 = {x: resourceQuota.textArr[i][j].style.x + resourceQuota.textArr[i][j].style.width, y: resourceQuota.textArr[i][j].style.y + resourceQuota.textArr[i][j].style.height},
                            classP4 = {x: resourceQuota.textArr[i][j].style.x, y: resourceQuota.textArr[i][j].style.y + resourceQuota.textArr[i][j].style.height},
                            oldColor = resourceQuota.textArr[i][j].style.color;

                        if (resourceQuota.pointInPoly(currPos, [classP1, classP2, classP3, classP4])) {


                            resourceQuota.selectedFirstText = resourceQuota.textArr[i][j];

                            //如果已申请，则不选中
                            if (resourceQuota.textArr[i][j].style.type > 0) {
                                return;
                            }
                            resourceQuota.rectangle.moveFlag = 2;
                            //resourceQuota.textArr[i][j].style.color = "#c0c0c0" ;
                            $.extend(resourceQuota.rectangle.rect.style, {
                                x: resourceQuota.textArr[i][j].style.x,
                                y: resourceQuota.textArr[i][j].style.y,
                                width: resourceQuota.textArr[i][j].style.width,
                                height: resourceQuota.textArr[i][j].style.height,
                                opacity: 1
                            });
                            //清除所有选中
                            //resourceQuota.delSelectStatus();

                            //选中一个
                            //resourceQuota.selectedFirstText.style.color="#c0c0c0" ;
                            //resourceQuota.selectedFirstText.style.type=1;
                            //清空申请数量
                            //$('#xh_sqesl').text(0);
                        } else {
                            if (e.button != 2 && !resourceQuota.textArr[i][j].style.type) {
                                resourceQuota.textArr[i][j].style.color = '#fff';
                                resourceQuota.textArr[i][j].selected = false;
                            }
                        }
                    }
                }
                resourceQuota.rectangle.x = currPos.x;
                resourceQuota.rectangle.y = currPos.y;
                if (e.button != 2) {
                    resourceQuota.rectangle.selectCount = 0;
                }
                resourceQuota.zr_number.render();

            });

            $('#zr_number canvas:eq(0)').mousemove(function (e, o) {
                if (!resourceQuota.rectangle.moveFlag || e.button == 2) {
                    return;
                }
                var currPos = resourceQuota.getPos($('#zr_number canvas:eq(0)'), e);
                var posX = currPos.x, posY = currPos.y,
                    deltaX = posX - resourceQuota.rectangle.x, deltaY = posY - resourceQuota.rectangle.y;
                if (resourceQuota.rectangle.moveFlag == 2) {
                    //框选
                    $.extend(resourceQuota.rectangle.rect.style, {
                        x: resourceQuota.rectangle.x,
                        y: resourceQuota.rectangle.y,
                        width: posX - resourceQuota.rectangle.x,
                        height: posY - resourceQuota.rectangle.y
                    });
                    //框选，改变选中的背景色
                    for (var i = 0; i <= 33; i++) {
                        for (var j = 0; j <= 30; j++) {
                            if (!resourceQuota.textArr[i][j]) {
                                break;
                            }
                            var classP1 = {x: resourceQuota.textArr[i][j].style.x, y: resourceQuota.textArr[i][j].style.y},
                                classP2 = {x: resourceQuota.textArr[i][j].style.x + resourceQuota.textArr[i][j].style.width, y: resourceQuota.textArr[i][j].style.y},
                                classP3 = {x: resourceQuota.textArr[i][j].style.x + resourceQuota.textArr[i][j].style.width, y: resourceQuota.textArr[i][j].style.y + resourceQuota.textArr[i][j].style.height},
                                classP4 = {x: resourceQuota.textArr[i][j].style.x, y: resourceQuota.textArr[i][j].style.y + resourceQuota.textArr[i][j].style.height};

                            if (resourceQuota.pointInPoly(currPos, [classP1, classP2, classP3, classP4])) {
                                if (resourceQuota.textArr[i][j] != resourceQuota.selectedLastText) {
                                    resourceQuota.selectedLastText = resourceQuota.textArr[i][j];

                                    //超过申请额
                                    if (resourceQuota.getSelectHSNum().count >= resourceQuota.maxLength && resourceQuota.selectedLastText.style.id > resourceQuota.selectedFirstText.style.id) {
                                        resourceQuota.rectangle.moveFlag = false;
                                        resourceQuota.tips('open');
                                        return;
                                    } else {

                                        resourceQuota.setSelectStatus(resourceQuota.selectedFirstText.style.id, resourceQuota.selectedLastText.style.id);
                                    }
                                }
                            }
                        }
                    }
                }
                resourceQuota.zr_number.render();
            });
            $('#zr_number canvas:eq(0)').mouseup(function (e) {
                resourceQuota.getPos($('#zr_number canvas:eq(0)'), e);
                /*var currPos = resourceQuota.getPos($('#zr_number canvas:eq(0)'), e),
                    classP1, classP2, classP3, classP4;
                for (var i = 0; i < 34; i++) {
                    for (var j = 0; j < 30; j++) {
                        classP1 = {x: resourceQuota.textArr[i][j].style.x, y: resourceQuota.textArr[i][j].style.y} ,
                            classP2 = {x: resourceQuota.textArr[i][j].style.x + resourceQuota.textArr[i][j].style.width, y: resourceQuota.textArr[i][j].style.y} ,
                            classP3 = {x: resourceQuota.textArr[i][j].style.x + resourceQuota.textArr[i][j].style.width, y: resourceQuota.textArr[i][j].style.y + resourceQuota.textArr[i][j].style.height} ,
                            classP4 = {x: resourceQuota.textArr[i][j].style.x, y: resourceQuota.textArr[i][j].style.y + resourceQuota.textArr[i][j].style.height};
                        //判断选中的数量
                        if (resourceQuota.textArr[i][j].selected) {
                            resourceQuota.rectangle.selectCount += 1;
                        }

                        if (resourceQuota.pointInPoly(currPos, [classP1, classP2, classP3, classP4])) {
                            var dd = resourceQuota.selectedFirstText == resourceQuota.textArr[i][j];
                            if (resourceQuota.rectangle.moveFlag == 2) {
                                if (resourceQuota.selectedFirstText == resourceQuota.textArr[i][j]) {
                                    //可框选标志
                                    resourceQuota.textArr[i][j].selected = true;
                                }
                            }
                        } else {
                            //resourceQuota.textArr[i][j].selected = undefined ;
                        }
                    }
                }*/
                //取消选择标志
                resourceQuota.rectangle.moveFlag = 0;
                $.extend(resourceQuota.rectangle.rect.style, {
                    x: 0,
                    y: 0,
                    width: 0,
                    height: 0,
                    opacity: 0
                });
                //设置号段
                resourceQuota.addHaoDuan();
                resourceQuota.selectedLastText = null;
                resourceQuota.selectedFirstText = null;

                resourceQuota.zr_number.render();
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
                formID: '#yjzypepz_optForm',
                left: 200,
                rules: {
                    yjzypepz_opt_bcsqpes: {
                        required: true
                    },
                    yjzypepz_opt_sqpehd: {
                        required: true
                    }
                },
                messages: {
                    yjzypepz_opt_bcsqpes: {
                        required: '必填'
                    },
                    yjzypepz_opt_sqpehd: {
                        required: '必填'
                    }
                }
            });
        }
    };
    return resourceQuota;
}); 

 
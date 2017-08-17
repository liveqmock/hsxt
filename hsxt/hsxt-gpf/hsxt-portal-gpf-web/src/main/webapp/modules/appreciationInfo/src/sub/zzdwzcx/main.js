define(["text!appreciationInfoTpl/sub/zzdwzcx/zzdwzcx_opt.html",
    "text!appreciationInfoTpl/sub/zzdwzcx/zzdwzcx_view.html",
    "text!appreciationInfoTpl/sub/zzdwzcx/zzdwzcx_parent_list.html",
    "appreciationInfoDat/zzdwzcx/zzdwzcx",
    "appreciationInfoLan"
], function (zzdwzcx_optTpl, zzdwzcx_viewTpl,zzdwzcx_parent_listTpl, incrementAjax) {
    var zzdwzcx = {
        showPage: function (tabid) {

            $('#main-content > div[data-contentid="' + tabid + '"]').empty().html(zzdwzcx_viewTpl);
            zzdwzcx.queryNodes({resNo: '00000000001'});
            //查询增值节点位置
            $('#zzdwzcx_tb_cz').click(function () {
                var resNo = $('#zzdwzcx_tb_resNo').val();
                if (resNo && resNo.length == 11) {
                    zzdwzcx.queryNodes({resNo: resNo});
                } else {
                    comm.alert({
                        imgClass: 'tips_warn',
                        content: '请输入正确的企业互生号！'
                    });
                }
            });
            //查询节点的向上路径
            $('#zzdwzcx_tb_czjddxslj').click(function () {
                var resNo = $('#zzdwzcx_tb_resNo').val();
                if (resNo && resNo.length == 11) {
                    incrementAjax.queryParentListByResNo(function (list) {
                        $('#zzdwzcxDlg').empty().html(_.template(zzdwzcx_parent_listTpl,{list:list})).dialog({
                            title: '增值系统--节点的向上路径',
                            width: 300,
                            height: 340,
                            modal: true,
                            closeIcon: true,
                            buttons: {
                                "关闭": function () {
                                    $(this).dialog('destroy');
                                }
                            }
                        });
                    },{resNo:resNo});
                } else {
                    comm.alert({
                        imgClass: 'tips_warn',
                        content: '请输入正确的企业互生号！'
                    });
                }
            });

            //查询节点的向下左边最长的路径
            $('#zzdwzcx_tb_czjddxxzbzclj').click(function () {
                var resNo = $('#zzdwzcx_tb_resNo').val();
                if (resNo && resNo.length == 11) {
                    incrementAjax.queryLongNodeByResNo(function (list) {
                        $('#zzdwzcxDlg').empty().html(_.template(zzdwzcx_parent_listTpl,{list:list})).dialog({
                            title: '增值系统--节点的向下左边最长的路径',
                            width: 300,
                            height: 340,
                            modal: true,
                            closeIcon: true,
                            buttons: {
                                "关闭": function () {
                                    $(this).dialog('destroy');
                                }
                            }
                        });
                    },{resNo:resNo,area:'left'});
                } else {
                    comm.alert({
                        imgClass: 'tips_warn',
                        content: '请输入正确的企业互生号！'
                    });
                }
            });

            //查询节点的向下右边最长的路径
            $('#zzdwzcx_tb_czjddxxybzclj').click(function () {
                var resNo = $('#zzdwzcx_tb_resNo').val();
                if (resNo && resNo.length == 11) {
                    incrementAjax.queryLongNodeByResNo(function (list) {
                        $('#zzdwzcxDlg').empty().html(_.template(zzdwzcx_parent_listTpl,{list:list})).dialog({
                            title: '增值系统--节点的向下右边最长的路径',
                            width: 300,
                            height: 340,
                            modal: true,
                            closeIcon: true,
                            buttons: {
                                "关闭": function () {
                                    $(this).dialog('destroy');
                                }
                            }
                        });
                    },{resNo:resNo,area:'right'});
                } else {
                    comm.alert({
                        imgClass: 'tips_warn',
                        content: '请输入正确的企业互生号！'
                    });
                }
            });

            //查询上一节点
            $('#zzdwzcx_tb_syjd').click(function () {
                var parent = $('#appreciationInfoTree .level1').attr('parent');
                if (parent) {
                    zzdwzcx.queryNodes({resNo: parent});
                }
            });
            //增值节点刷新服务
            $('#zzdwzcx_tb_zzjdsxfw').click(function () {
                var harr = $('#appreciationInfoTree .level1 .ptArw1 + h2');
                if(harr&&harr.length>0) {
                    var resNo = harr[0].title;
                    if (resNo) {
                        zzdwzcx.queryNodes({resNo: resNo});
                    }
                }
            });
            //新增增值节点
            $('#zzdwzcx_tb_xzzzjd').click(function () {
                $('#zzdwzcxDlg').empty().html(zzdwzcx_optTpl).dialog({
                    title: '增值系统--新增增值节点',
                    width: 500,
                    height: 340,
                    modal: true,
                    closeIcon: true,
                    buttons: {
                        "确定": function () {
                            if(zzdwzcx.validate()) {
                                var params = $('#zzdwzcx_optForm').serializeJson();
                                if(!params.area) {
                                    comm.alert({
                                        imgClass: 'tips_warn',
                                        content: '请选择增值区域！'
                                    });
                                    return;
                                }
                                if(!params.flag) {
                                    comm.alert({
                                        imgClass: 'tips_warn',
                                        content: '请选择是否跨库！'
                                    });
                                    return;
                                }else{
                                    var perApp = params.appCustId.substring(0, 2);
                                    var perPop = params.popCustId.substring(0, 2);
                                    if((perApp==perPop&&params.flag==0)||(perApp!=perPop&&params.flag==1)) {
                                        comm.alert({
                                            imgClass: 'tips_warn',
                                            content: '请正确选择是否跨库！'
                                        });
                                        return;
                                    }
                                }
                                incrementAjax.addIncrementNode(function (result) {
                                    if(result) {
                                        comm.alert({
                                            imgClass: 'tips_yes',
                                            content: '操作成功！'
                                        });
                                        $('#zzdwzcxDlg').dialog('destroy');
                                    }
                                },params);
                            }
                        },
                        "取消": function () {
                            $(this).dialog('destroy');
                        }
                    }
                });

                //增值区域
                $("#select_area").selectList({
                    borderColor: '#CCC',
                    options: [
                        {name: '左区域', value: 'left'},
                        {name: '右区域', value: 'right'}
                    ]
                }).change(function () {
                    $('#area').val($(this).attr('optionValue'));
                });

                //是否跨库
                $("#select_flag").selectList({
                    borderColor: '#CCC',
                    options: [
                        {name: '跨库', value: '0'},
                        {name: '非跨库', value: '1'}
                    ]
                }).change(function () {
                    $('#flag').val($(this).attr('optionValue'));
                });

            });
            //节点点击事件
            $('#appreciationInfoTree').on('click', '.valueAddedInfo', function () {
                var resNo = $(this).find('.ptArw1 + h2')[0].title;
                if (resNo) {
                    zzdwzcx.queryNodes({resNo: resNo});
                }
            });

        },
        queryNodes: function (params) {
            incrementAjax.queryIncrementByResNo(function (map) {
                if (map) {
                    var startNode;
                    for (var custId in map) {
                        if ((params.resNo.length == 11 && custId.length <= 19 && params.resNo == map[custId].resNo) || (params.resNo.length >= 19 && params.resNo == custId)) {
                            startNode = map[custId];
                        }
                    }
                    var viewTpl;
                    if (startNode) {
                        viewTpl = '<ul class="clearfix"><li class="level1" parent="' + startNode.parent + '">' +
                            '<div class="valueAddedInfo '+zzdwzcx.selectColor(startNode)+'" style="cursor: pointer"><ul class="VAInfoList">' +
                            '<li class="clearfix">' +
                            '<i class="ptArw1"></i>' +
                            '<h2 title="' + startNode.custId + '">' + startNode.custId + '</h2>' +
                            '</li>' +
                            '<li class="clearfix">' +
                            '<i class="ptArw2"></i>' +
                            '<h2 title="再' + (startNode.refValue || 0) + '#' + (startNode.baseValue || 0) + '">再' + (startNode.refValue || 0) + '#' + (startNode.baseValue || 0) + '</h2>' +
                            '</li>' +
                            '<li class="clearfix">' +
                            '<i class="ptArw3"></i>' +
                            '<h2 title="左' + (startNode.lP || startNode.lp || 0) + '#' + (startNode.lCount || startNode.lcount || 0) + '">左' + (startNode.lP || startNode.lp || 0) + '#' + (startNode.lCount || startNode.lcount || 0) + '</h2>' +
                            '</li>' +
                            '<li class="clearfix">' +
                            '<i class="ptArw4"></i>' +
                            '<h2 title="右' + (startNode.rP || startNode.rp || 0) + '#' + (startNode.rCount || startNode.rcount || 0) + '">右' + (startNode.rP || startNode.rp || 0) + '#' + (startNode.rCount || startNode.rcount || 0) + '</h2>' +
                            '</li></ul></div>';
                        viewTpl = viewTpl + zzdwzcx.drawTree(startNode, map, startNode.level - 1) + '</li></ul>';
                    }
                    $('#appreciationInfoTree').empty().html(viewTpl);
                }
            }, params);
        },
        drawTree: function (parent, map, startLevel) {
            var view;
            if (parent && (parent.left || parent.right)) {
                var node = (map[parent.left] || map[parent.right]);
                if (node) {
                    view = '<ul>';
                    view = view + '<li class="level' + (node.level - startLevel) + ' ' + (map[parent.left] ? '' : 'noInfo') + '">' +
                        '<div class="clearfix">' +
                        '<div class="lvArrow' + (node.level - startLevel) + ' fr">' +
                        '<div class="aLineBtRt"></div>' +
                        '<div class="aLineTpLt"></div>' +
                        '</div>' +
                        '</div>';

                    if (parent.left && map[parent.left]) {
                        view = view + '<div class="valueAddedInfo '+zzdwzcx.selectColor(map[parent.left])+'" style="cursor: pointer"><ul class="VAInfoList">' +
                            '<li class="clearfix">' +
                            '<i class="ptArw1"></i>' +
                            '<h2 title="' + parent.left + '">' + parent.left + '</h2>' +
                            '</li>' +
                            '<li class="clearfix">' +
                            '<i class="ptArw2"></i>' +
                            '<h2 title="再' + (map[parent.left].refValue || 0) + '#' + (map[parent.left].baseValue || 0) + '">再' + (map[parent.left].refValue || 0) + '#' + (map[parent.left].baseValue || 0) + '</h2>' +
                            '</li>' +
                            '<li class="clearfix">' +
                            '<i class="ptArw3"></i>' +
                            '<h2 title="左' + (map[parent.left].lP || map[parent.left].lp || 0) + '#' + (map[parent.left].lCount || map[parent.left].lcount || 0) + '">左' + (map[parent.left].lP || map[parent.left].lp || 0) + '#' + (map[parent.left].lCount || map[parent.left].lcount || 0) + '</h2>' +
                            '</li>' +
                            '<li class="clearfix">' +
                            '<i class="ptArw4"></i>' +
                            '<h2 title="右' + (map[parent.left].rP || map[parent.left].rp || 0) + '#' + (map[parent.left].rCount || map[parent.left].rcount || 0) + '">右' + (map[parent.left].rP || map[parent.left].rp || 0) + '#' + (map[parent.left].rCount || map[parent.left].rcount || 0) + '</h2>' +
                            '</li>' +
                            '</ul></div>';

                        view = view + zzdwzcx.drawTree(map[parent.left], map, startLevel);
                    }
                    view = view + '</li>';


                    view = view + '<li class="level' + (node.level - startLevel) + ' ' + (map[parent.right] ? '' : 'noInfo') + '">' +
                        '<div class="clearfix">' +
                        '<div class="lvArrow' + (node.level - startLevel) + ' fl">' +
                        '<div class="aLineBtLt"></div>' +
                        '<div class="aLineTpRt"></div>' +
                        '</div>' +
                        '</div>';

                    if (parent.right && map[parent.right]) {
                        view = view + '<div class="valueAddedInfo '+zzdwzcx.selectColor(map[parent.right])+'" style="cursor: pointer"><ul class="VAInfoList">' +
                            '<li class="clearfix">' +
                            '<i class="ptArw1"></i>' +
                            '<h2 title="' + parent.right + '">' + parent.right + '</h2>' +
                            '</li>' +
                            '<li class="clearfix">' +
                            '<i class="ptArw2"></i>' +
                            '<h2 title="再' + (map[parent.right].refValue || 0) + '#' + (map[parent.right].baseValue || 0) + '">再' + (map[parent.right].refValue || 0) + '#' + (map[parent.right].baseValue || 0) + '</h2>' +
                            '</li>' +
                            '<li class="clearfix">' +
                            '<i class="ptArw3"></i>' +
                            '<h2 title="左' + (map[parent.right].lP || map[parent.right].lp || 0) + '#' + (map[parent.right].lCount || map[parent.right].lcount || 0) + '">左' + (map[parent.right].lP || map[parent.right].lp || 0) + '#' + (map[parent.right].lCount || map[parent.right].lcount || 0) + '</h2>' +
                            '</li>' +
                            '<li class="clearfix">' +
                            '<i class="ptArw4"></i>' +
                            '<h2 title="右' + (map[parent.right].rP || map[parent.right].rp || 0) + '#' + (map[parent.right].rCount || map[parent.right].rcount || 0) + '">右' + (map[parent.right].rP || map[parent.right].rp || 0) + '#' + (map[parent.right].rCount || map[parent.right].rcount || 0) + '</h2>' +
                            '</li>' +
                            '</ul></div>';

                        view = view + zzdwzcx.drawTree(map[parent.right], map, startLevel);
                    }
                    view = view + '</li>';
                    view = view + '</ul>';
                }

            }

            return view || '';

        },
        selectColor:function(node){
            if(node.isActive=='Y'){
                var sRegx = /^(([1-9]\d)|(\d[1-9]))(([1-9]\d{2})|(\d{2}[1-9])|(\d[1-9]\d))([0]{6})$/;
                var tRegx = /^(([1-9]\d)|(\d[1-9]))(([1-9]\d{2})|(\d{2}[1-9])|(\d[1-9]\d))(([1-9]\d)|(\d[1-9]))([0]{4})$/;
                var mRegx = /^(([1-9]\d)|(\d[1-9]))(([1-9]\d{2})|(\d{2}[1-9])|(\d[1-9]\d))([0]{2})(([1-9]\d{3})|(\d{3}[1-9])|(\d{2}[1-9]\d)|(\d[1-9]\d{2}))$/;
                if(sRegx.test(node.resNo)) return 'binary_tree_s';
                if(tRegx.test(node.resNo)) return 'binary_tree_t';
                if(mRegx.test(node.resNo)) return 'binary_tree_m';
            }else{
                return 'binary_tree_invalid';
            }

        },
        validate: function () {
            return comm.valid({
                formID: '#zzdwzcx_optForm',
                left: 200,
                rules: {
                    appCustId: {
                        required: true
                    },
                    popNo: {
                        required: true
                    },
                    popCustId: {
                        required: true
                    }
                },
                messages: {
                    appCustId: {
                        required: '必填'
                    },
                    popNo: {
                        required: '必填'
                    },
                    popCustId: {
                        required: '必填'
                    }
                }
            });
        }
    };


    return zzdwzcx;
});
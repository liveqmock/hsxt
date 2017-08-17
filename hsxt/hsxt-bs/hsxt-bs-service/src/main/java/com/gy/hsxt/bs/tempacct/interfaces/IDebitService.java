/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.bs.tempacct.interfaces;

import com.gy.hsxt.bs.bean.tempacct.Debit;
import com.gy.hsxt.bs.bean.tempacct.DebitQuery;
import com.gy.hsxt.bs.bean.tempacct.DebitSum;
import com.gy.hsxt.bs.common.interfaces.IBaseService;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.exception.HsException;

import java.util.List;

/**
 * 临账业务接口层
 *
 * @Package :com.gy.hsxt.bs.tempacct.interfaces
 * @ClassName : IDebitService
 * @Description : 临账业务接口层
 * 包括创建/修改/查询临账记录，审核临账，临账统计，不动款与临账互转业务
 * @Author : chenm
 * @Date : 2015/12/21 10:24
 * @Version V3.0.0.0
 */
public interface IDebitService extends IBaseService<Debit> {

    /**
     * 修改临账状态
     *
     * @param debit 实体
     * @param appr 是否审核
     * @return int
     * @throws HsException
     */
    int modifyDebitStatus(Debit debit,boolean appr) throws HsException;

    /**
     * 分页查询临账录入复核/退款复核列表
     *
     * @param page       分页对象
     * @param debitQuery 查询实体
     * @return list
     * @throws HsException
     */
    List<Debit> queryTaskListForPage(Page page, DebitQuery debitQuery) throws HsException;

    /**
     * 分页查询临账统计结果
     * <p/>
     * 根据收款账户名称分类统计
     *
     * @param page       分页对象
     * @param debitQuery 查询实体
     * @return list
     * @throws HsException
     */
    List<DebitSum> queryDebitSumListForPage(Page page, DebitQuery debitQuery) throws HsException;

    /**
     * 查询临账统计详情
     *
     * @param receiveAccountName 收款账户名称
     * @param debitQuery 查询实体
     * @return list
     * @throws HsException
     */
    List<Debit> queryDebitSumDetail(String receiveAccountName,DebitQuery debitQuery) throws HsException;

    /**
     * 不动款与临账互转
     *
     * @param debit        临账实体
     * @param dblOptCustId 双签操作员
     * @throws HsException
     */
    void turnDebit(Debit debit, String dblOptCustId) throws HsException;

    /**
     * 不动款统计
     *
     * @return sum
     * @throws HsException
     */
    String frozenDebitSum() throws HsException;
}

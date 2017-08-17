/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.api.tool;

import java.util.List;

import com.gy.hsxt.bs.bean.base.ApprInfo;
import com.gy.hsxt.bs.bean.base.Operator;
import com.gy.hsxt.bs.bean.tool.CardStyle;
import com.gy.hsxt.bs.bean.tool.ToolCategory;
import com.gy.hsxt.bs.bean.tool.ToolProduct;
import com.gy.hsxt.bs.bean.tool.ToolProductUpDown;
import com.gy.hsxt.bs.bean.tool.pageparam.ToolProductVo;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;

/**
 * 工具产品API接口
 * 
 * @Package: com.gy.hsxt.bs.api.tool
 * @ClassName: IBSToolProductService
 * @Description: TODO
 * @author: likui
 * @date: 2015年9月29日 下午4:23:14
 * @company: gyist
 * @version V3.0.0
 */
public interface IBSToolProductService {
    /**
     * 查询所有工具类别
     * 
     * @Desc:
     * @author: likui
     * @created: 2015年9月29日 下午5:21:22
     * @return
     * @return : List<ToolCategory>
     * @version V3.0.0
     */
    public List<ToolCategory> queryToolCategoryAll();

    /**
     * 添加工具产品
     * 
     * @Desc:
     * @author: likui
     * @created: 2015年9月29日 下午5:21:27
     * @param bean
     *            工具产品参数实体
     * @throws HsException
     * @return : void
     * @version V3.0.0
     */
    public void addToolProduct(ToolProduct bean) throws HsException;


    /**
     * 修改工具产品
     *
     * @Desc:
     * @author: likui
     * @created: 2015年9月29日 下午5:21:27
     * @param bean
     *            工具产品参数实体
     * @throws HsException
     * @return : void
     * @version V3.0.0
     */
    public void updateToolProduct(ToolProduct bean) throws HsException;

    /**
     * 根据id查询工具产品
     * 
     * @Desc:
     * @author: likui
     * @created: 2015年9月29日 下午5:21:32
     * @param productId
     *            工具产品id
     * @return
     * @return : ToolProduct
     * @version V3.0.0
     */
    public ToolProduct queryToolProductById(String productId);

    /**
     * 分页查询工具产品
     * 
     * @Desc:
     * @author: likui
     * @created: 2015年9月29日 下午5:21:41
     * @param vo
     *            查询条件参数实体
     * @param page
     *            分页参数实体
     * @return
     * @return : PageData<ToolProduct>
     * @version V3.0.0
     */
    public PageData<ToolProduct> queryToolProductPage(ToolProductVo vo, Page page);

    /**
     * 根据条件或查询所有工具
     * 
     * @Desc:
     * @author: likui
     * @created: 2015年9月29日 下午5:21:58
     * @param categoryCode
     *            工具类别代码
     * @return
     * @return : List<ToolProduct>
     * @version V3.0.0
     */
    public List<ToolProduct> queryToolProduct(String categoryCode);

    /**
     * 根据条件或查询所有工具(不管工具状态)
     *
     * @Desc:
     * @author: likui
     * @created: 2015年9月29日 下午5:21:58
     * @param categoryCode
     *            工具类别代码
     * @return
     * @return : List<ToolProduct>
     * @version V3.0.0
     */
    public List<ToolProduct> selectToolProductNotByStatus( String categoryCode);

    /**
     * 添加互生卡样
     * 
     * @Desc:
     * @author: likui
     * @created: 2015年9月29日 下午5:22:45
     * @param bean
     *            卡样参数实体
     * @throws HsException
     * @return : void
     * @version V3.0.0
     */
    public void addCardStyle(CardStyle bean) throws HsException;

    /**
     * 根据id查询互生卡样详情
     * 
     * @Desc:
     * @author: likui
     * @created: 2015年9月29日 下午5:22:50
     * @param cardStyleId
     *            卡样id
     * @return
     * @return : CardStyle
     * @version V3.0.0
     */
    public CardStyle queryCardStyleById(String cardStyleId);

    /**
     * 分页查询互生卡样
     * 
     * @Desc:
     * @author: likui
     * @created: 2015年9月29日 下午5:22:56
     * @param cardStyleName
     *            卡样名称
     * @param exeCustId
     *            受理人客户号
     * @param page
     *            分页参数实体
     * @return
     * @return : PageData<CardStyle>
     * @version V3.0.0
     */
    public PageData<CardStyle> queryCardStylePage(String cardStyleName, String exeCustId, Page page);

    /**
     * 互生卡样的启用/停用
     * 
     * @Desc:
     * @author: likui
     * @created: 2015年9月29日 下午5:23:02
     * @param bean
     *            卡样参数实体
     * @throws HsException
     * @return : void
     * @version V3.0.0
     */
    public void cardStyleEnableOrStop(CardStyle bean) throws HsException;

    /**
     * 修改卡标准样为默认
     * 
     * @Description:
     * @author: likui
     * @created: 2015年10月30日 下午5:59:49
     * @param cardStyleId
     *            卡样id
     * @throws HsException
     * @return : void
     * @version V3.0.0
     */
    public void updateCardStyleToDefault(String cardStyleId) throws HsException;

    /**
     * 互生卡样的启用/停用审批
     * 
     * @Desc:
     * @author: likui
     * @created: 2015年9月29日 下午5:23:07
     * @param bean
     *            卡样参数实体
     * @throws HsException
     * @return : void
     * @version V3.0.0
     */
    public void cardStyleAppr(CardStyle bean) throws HsException;

    /**
     * 申请工具下架
     * 
     * @param productId
     *            工具产品编号
     * @param downReason
     *            下架原因
     * @param operator
     *            操作员信息
     * @throws HsException
     */
    void applyToolProductToDown(String productId, String downReason, Operator operator) throws HsException;

    /**
     * 申请调价
     * 
     * @param productId
     *            产品编号
     * @param applyPrice
     *            申请调整价格
     * @param operator
     *            操作员信息
     * @throws HsException
     */
    void applyChangePrice(String productId, String applyPrice, Operator operator) throws HsException;

    /**
     * 工具上架审批
     * 
     * @param apprInfo
     *            审批信息
     * @throws HsException
     */
    void apprToolProductForUp(ApprInfo apprInfo) throws HsException;

    /**
     * 工具下架审批
     * 
     * @param apprInfo
     *            审批信息
     * @throws HsException
     */
    void apprToolProductForDown(ApprInfo apprInfo) throws HsException;

    /**
     * 分页查询工具上架申请记录，包含首次上架申请和价格调整申请
     * 
     * @param productName
     *            产品名称，支持模糊
     * @param categoryName
     *            产品类别，支持模糊
     * @param exeCustId
     *            审批经办人
     * @param page
     *            分页参数
     * @return
     */
    public PageData<ToolProductUpDown> queryToolUpForApprListPage(String productName, String categoryName,
            String exeCustId, Page page);

    /**
     * 分页查询工具下架申请记录
     * 
     * @param productName
     *            产品名称，支持模糊
     * @param categoryName
     *            产品类别，支持模糊
     * @param exeCustId
     *            审批经办人
     * @param page
     *            分页参数
     * @return
     */
    public PageData<ToolProductUpDown> queryToolDownForApprListPage(String productName, String categoryName,
            String exeCustId, Page page);

    /**
     * 根据申报请编号查询工具上下架申请记录
     * 
     * @param applyId
     * @return 工具上下架申请记录
     */
    public ToolProductUpDown queryToolProductUpDownById(String applyId);

    /**
     * 根据产品编号查询工具上下架申请记录
     * @param productId 产品编号
     * @return
     */
    ToolProductUpDown queryToolProductUpDownByProductId(String productId);
}

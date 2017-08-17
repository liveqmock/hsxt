/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.bs.tool.mapper;

import com.gy.hsxt.bs.bean.tool.ToolCategory;
import com.gy.hsxt.bs.bean.tool.ToolProduct;
import com.gy.hsxt.bs.bean.tool.pageparam.ToolProductVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 工具产品Mapper
 *
 * @version V3.0.0
 * @Package: com.hsxt.bs.btool.mapper
 * @ClassName: ToolProductMapper
 * @Description: TODO
 * @author: likui
 * @date: 2015年9月29日 上午11:22:58
 * @company: gyist
 */
@Repository(value = "toolProductMapper")
public interface ToolProductMapper {

    /**
     * 查询所有的工具类别
     *
     * @return : List<ToolCategory>
     * @Desc:
     * @author: likui
     * @created: 2015年9月29日 下午5:10:22
     * @version V3.0.0
     */
    public List<ToolCategory> selectToolCategoryAll();

    /**
     * 插入工具产品
     *
     * @param entity
     * @return : void
     * @Desc:
     * @author: likui
     * @created: 2015年9月29日 下午5:10:28
     * @version V3.0.0
     */
    public void insertToolProduct(ToolProduct entity);

    /**
     * 查询最大工具最大id
     *
     * @return : String
     * @Desc:
     * @author: likui
     * @created: 2015年10月10日 下午2:14:06
     * @version V3.0.0
     */
    public String selectMaxProductId();

    /**
     * 根据id查询工具产品
     *
     * @param productId
     * @return : ToolProduct
     * @Desc:
     * @author: likui
     * @created: 2015年9月29日 下午5:10:37
     * @version V3.0.0
     */
    public ToolProduct selectToolProductById(String productId);

    /**
     * 分页查询工具产品
     *
     * @param vo
     * @return : List<ToolProduct>
     * @Desc:
     * @author: likui
     * @created: 2015年9月29日 下午5:10:43
     * @version V3.0.0
     */
    public List<ToolProduct> selectToolProductListPage(ToolProductVo vo);

    /**
     * 查询所有工具或根据类别查询工具
     *
     * @param categoryCode
     * @return : List<ToolProduct>
     * @Desc:
     * @author: likui
     * @created: 2015年9月29日 下午5:11:04
     * @version V3.0.0
     */
    public List<ToolProduct> selectToolProduct(@Param("categoryCode") String categoryCode);

    /**
     * 根据工具类别查询工具，不管工具的状态
     *
     * @Description:
     * @author: likui
     * @date: 2016/5/9 16:01
     * @param: categoryCode
     * @return: List<ToolProduct>
     * @company: gyist
     * @version V3.0.0
     */
    public List<ToolProduct> selectToolProductNotByStatus(@Param("categoryCode") String categoryCode);

    /**
     * 根据类别代码查询工具
     *
     * @param code
     * @return : List<ToolProduct>
     * @Desc:
     * @author: likui
     * @created: 2015年10月22日 上午9:48:12
     * @version V3.0.0
     */
    public List<ToolProduct> selectToolProductByCode(String[] code);

    /**
     * 工具产品价格或状态修改
     *
     * @param productId    产品编号，必须有
     * @param status       申请或审批状态，必须有
     * @param lastApplyId  最新申请编号，申请时有
     * @param price        更新价格，申请价格调整审批通过后有
     * @param enableStatus 上下架状态，审批通过时有
     * @return
     */
    public int updateToolProduct(@Param("productId") String productId, @Param("status") String status,
                                 @Param("lastApplyId") String lastApplyId, @Param("price") String price,
                                 @Param("enableStatus") String enableStatus);


    /**
     * 根据工具编号修改工具
     *
     * @param entity
     * @return
     */
    public int updateToolProductById(ToolProduct entity);

    // /**
    // * 修改工具价格
    // *
    // * @Desc:
    // * @author: likui
    // * @created: 2015年9月29日 下午5:11:12
    // * @param price
    // * @param productId
    // * @return
    // * @return : int
    // * @version V3.0.0
    // */
    // public int updateToolProductPrice(@Param("price") String price,
    // @Param("productId") String productId);

    /**
     * 查询可以申购的工具
     *
     * @param custType
     * @return : List<ToolProduct>
     * @Desc:
     * @author: likui
     * @created: 2015年9月29日 下午5:11:23
     * @version V3.0.0
     */
    public List<ToolProduct> selectMayBuyToolProduct(@Param("custType") Integer custType,
                                                     @Param("categoryCode") String categoryCode);

    /**
     * 查询申报时需要配置的工具
     *
     * @param resFeeId
     * @param :        @return
     * @return : List<ToolProduct>
     * @Desc:
     * @author: likui
     * @created: 2015年10月12日 下午3:20:27
     * @version V3.0.0
     */
    public List<ToolProduct> selectApplyToolProduct(@Param("resFeeId") String resFeeId);

    // 测试(查询申报时需要配置的工具)
    public List<ToolProduct> selectApplyToolProductTest();

    /**
     * 查询可以购买的数量
     *
     * @param entCustId
     * @param categoryCode
     * @return : Integer
     * @Desc:
     * @author: likui
     * @created: 2015年9月29日 下午5:11:29
     * @version V3.0.0
     */
    public Integer selectMayBuyToolNum(@Param("entCustId") String entCustId, @Param("categoryCode") String categoryCode);


    /**
     * 根据工具名称查询工具
     *
     * @Desc:
     * @author: likui
     * @created: 2016/6/7 16:59
     * @param: productName
     * @return: ToolProduct
     * @company: gyist
     * @version V3.0.0
     */
    public ToolProduct queryToolProductByName(String productName);

    /**
     * 查询企业资源段购买
     *
     * @Desc:
     * @author: likui
     * @created: 2016/6/12 18:01
     * @param: entCustId
     * @param: confStatus
     * @return: Integer
     * @company: gyist
     * @version V3.0.0
     */
    public Integer selectResourceSegmentBuy(@Param("entCustId") String entCustId, @Param("confStatus") int[] confStatus);
}

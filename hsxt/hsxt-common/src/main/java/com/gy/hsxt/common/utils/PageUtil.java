/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */

package com.gy.hsxt.common.utils;

import java.util.ArrayList;
import java.util.List;

import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;

/**
 * 数据分页工具类
 * 当数据库表数据量较大（大于10w），查询结果总记录数较小（小于1w）时，查询一次数据库，然后内存分页性能更优。
 * @Package: com.gy.hsxt.common.utils
 * @ClassName: PageUtil
 * @Description: 根据分页配置，返回当前页数据
 * 
 * @author: lvyan
 * @date: 2015-12-9 下午1:59:36
 * @version V1.0
 */
public class PageUtil {

    /**
     * 获取分页数据
     * 
     * @param pageConfig
     *            分页参数
     * @param dataList
     *            总记录集
     * @return 分页后单页数据
     */
    public static PageData subPage(Page pageConfig, List dataList) {
        if (dataList == null)
        {
            return new PageData(0, new ArrayList());
        }
        // 总记录数
        int size = dataList.size();
        if (pageConfig == null)
        {
            return new PageData(size, dataList);
        }
        // 分页数据集起始位置
        int fromIndex = getBegin(pageConfig);
        if (fromIndex >= size)
        { // 起始位置大于总记录数,返回空集
            return new PageData(size, new ArrayList());
        }else if (fromIndex < 0)
        { // 起始位置小于0，返回全集
            return new PageData(size, dataList);
        }
        // 分页数据集截止位置
        int toIndex = getEnd(pageConfig);
        // 如果截止位置大于总记录数,使用总记录数作为截止数据
        toIndex = toIndex > size ? size : toIndex;
        return new PageData(size, dataList.subList(fromIndex, toIndex));
    }

    /**
     * 获取当前页数据起始位置
     * @return
     */
    public static int getBegin(Page pageConfig) {
        return (pageConfig.getCurPage() - 1) * pageConfig.getPageSize();
    }

    /**
     * 获取当前页数据截止位置
     * @return
     */
    public static int getEnd(Page pageConfig) {
        return pageConfig.getCurPage() * pageConfig.getPageSize();
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub

    }

}

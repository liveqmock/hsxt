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

package com.gy.hsxt.gpf.res.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gy.hsxt.gpf.res.bean.MentInfo;
import com.gy.hsxt.gpf.res.bean.PlatInfo;
import com.gy.hsxt.gpf.res.bean.PlatMent;
import org.springframework.stereotype.Repository;

/**
 * 
 * @Package: com.gy.hsxt.gpf.res.mapper
 * @ClassName: InitMapper
 * @Description: 总平台配额管理平台与管理公司初始化Mapper
 * 
 * @author: yangjianguo
 * @date: 2015-12-8 上午9:23:42
 * @version V1.0
 */
@Repository
public interface InitMapper {

    /**
     * 添加平台信息
     * 
     * @param plat
     *            平台信息
     */
    public void addPlatInfo(PlatInfo plat);

    /**
     * 修改平台信息
     * 
     * @param plat
     *            平台信息
     */
    public void updatePlatInfo(PlatInfo plat);

    /**
     * 分页查询平台信息
     * 
     * @param entResNo
     *            平台企业互生号
     * @param entCustName
     *            平台名称
     * @return 平台信息列表
     */
    public List<PlatInfo> queryPlatInfoListPage(@Param("entResNo") String entResNo,
            @Param("entCustName") String entCustName);

    /**
     * 添加管理公司
     * 
     * @param mentInfo
     *            管理公司信息
     */
    public int addMentInfo(MentInfo mentInfo);

    /**
     * 分页查询管理公司
     * 
     * @param entResNo
     *            管理公司互生号
     * @param entCustName
     *            管理公司名称
     * @return 管理公司列表
     */
    public List<PlatMent> queryMentInfoListPage(@Param("entResNo") String entResNo,
            @Param("entCustName") String entCustName);

    /**
     * 是否存在平台与管理公司关系
     * 
     * @param platNo
     *            平台代码
     * @param entResNo
     *            管理公司互生号
     * @return true存在，false则不存在
     */
    public boolean isExistPlatMent(@Param("platNo") String platNo, @Param("entResNo") String entResNo);

    /**
     * 平台与管理公司是否违反单向一对多原则
     * 
     * @param platNo
     *            平台代码
     * @param entResNo
     *            管理公司互生号
     * @return true违反，false则不违反
     */
    public boolean violateSingleOne2Many(@Param("platNo") String platNo, @Param("entResNo") String entResNo);

    /**
     * 关联平台与管理公司
     * 
     * @param platMent
     *            平台与管理公司关系
     */
    public void addPlatMent(PlatMent platMent);

    /**
     * 查询平台与管理公司关系
     * 
     * @param platNo
     *            平台代码
     * @param entResNo
     *            管理公司互生号
     * @return 平台与管理公司关系列表
     */
    public List<PlatMent> queryPlatMent(@Param("platNo") String platNo, @Param("entResNo") String entResNo);

    /**
     * 更新地区平台开户同步标识
     * 
     * @param platNo
     *            平台代码
     */
    public void updatePlatInfoSync(String platNo);

    /**
     * 更新管理公司开户同步标识
     * 
     * @param platNo
     *            平台代码
     * @param entResNo
     *            管理公司互生号
     * @param ucSync
     *            用户中心同步标识
     * 
     * @param bsSync
     *            业务系统同步标识
     */
    public void updatePlatMentSync(@Param("platNo") String platNo, @Param("entResNo") String entResNo,
            @Param("ucSync") boolean ucSync, @Param("bsSync") boolean bsSync);

    /**
     * 通过平台代码查询平台信息
     * 
     * @param platNo
     *            平台代码
     * @return 平台信息
     */
    public PlatInfo getPlatInfoById(String platNo);

    /**
     * 根据管理互生号查询管理公司信息
     * 
     * @param mResNo
     *            管理公司互生号
     * @return 管理公司信息
     */
    public MentInfo getMentInfoById(String mResNo);

    /**
     * 查询所有平台代码
     * 
     * @return 所有平台代码
     */
    public List<String> queryPlatNoList();

    /**
     * 查询管理公司信息
     * 
     * @param platNo
     *            平台代码
     * @param entResNo
     *            管理公司互生号
     * @return 管理公司信息
     */
    public PlatMent queryPlatMentById(@Param("platNo") String platNo, @Param("entResNo") String entResNo);

    public List<PlatInfo> platListAll();

    public boolean isExistMent(@Param("entResNo") String entResNo);

    public int queryMentInitQuota(@Param("entResNo") String entResNo, @Param("platNo") String platNo);

}

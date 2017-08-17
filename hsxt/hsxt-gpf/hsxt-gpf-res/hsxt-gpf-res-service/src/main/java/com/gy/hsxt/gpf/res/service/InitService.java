/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.gpf.res.service;

import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.gpf.gcs.api.IGCSRouteRuleService;
import com.gy.hsxt.gpf.gcs.bean.Plat;
import com.gy.hsxt.gpf.res.RESErrorCode;
import com.gy.hsxt.gpf.res.api.IRESInitService;
import com.gy.hsxt.gpf.res.bean.MentInfo;
import com.gy.hsxt.gpf.res.bean.PlatInfo;
import com.gy.hsxt.gpf.res.bean.PlatMent;
import com.gy.hsxt.gpf.res.common.PageContext;
import com.gy.hsxt.gpf.res.interfaces.ISyncDataService;
import com.gy.hsxt.gpf.res.mapper.InitMapper;
import com.gy.hsxt.gpf.res.mapper.QuotaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @Package: com.gy.hsxt.gpf.res.service
 * @ClassName: InitService
 * @Description: 初始化管理接口实现
 * 
 * @author: xiaofl
 * @date: 2015-12-17 下午5:04:14
 * @version V1.0
 */
@Service
public class InitService implements IRESInitService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Resource
    IGCSRouteRuleService iGCSRouteRuleService;

    @Resource
    private QuotaMapper quotaMapper;

    @Resource
    private InitMapper initMapper;

    @Resource
    private ISyncDataService syncDataService;

    /**
     * 查询未初始化的平台信息
     * 
     * @param page
     *            分页信息
     * @return 未初始化的平台信息列表
     * @throws HsException
     */
    @Override
    public PageData<PlatInfo> queryUnInitPlatInfo(Page page) throws HsException {
        if (null == page)
        {
            throw new HsException(RespCode.PARAM_ERROR.getCode(), "参数错误");
        }
        PageData<PlatInfo> pageData = null;
        List<Plat> platList = iGCSRouteRuleService.findRegionPlats();// 所有地区平台
        if (null != platList && platList.size() > 0)
        {
            List<String> platNoList = initMapper.queryPlatNoList();// 已初始化平台
            List<PlatInfo> unInitPlatInfo = new ArrayList<PlatInfo>();// 未初始化非总平台
            for (Plat plat : platList)
            {
                if (!platNoList.contains(plat.getPlatNo()))
                {
                    PlatInfo platInfo = new PlatInfo();
                    platInfo.setPlatNo(plat.getPlatNo());
                    unInitPlatInfo.add(platInfo);
                }
            }

            if (unInitPlatInfo.size() > 0)
            {
                pageData = new PageData<PlatInfo>();
                pageData.setCount(unInitPlatInfo.size());
                int fromIndex = (page.getCurPage() - 1) * page.getPageSize();
                int toIndex = (fromIndex + page.getPageSize() - 1) > unInitPlatInfo.size() ? unInitPlatInfo.size()
                        : (fromIndex + page.getPageSize() - 1);
                pageData.setResult(unInitPlatInfo.subList(fromIndex, toIndex));
            }
        }
        return pageData;
    }

    /**
     * 添加平台公司信息
     * 
     * @param platInfo
     *            平台公司信息
     * @throws HsException
     */
    @Override
    @Transactional
    public void addPlatInfo(PlatInfo platInfo) throws HsException {
        if (null == platInfo)
        {
            throw new HsException(RespCode.PARAM_ERROR.getCode(), "参数错误");
        }
        try
        {
            platInfo.setEntResNo("00000000" + platInfo.getPlatNo());
            // 保存平台公司信息
            initMapper.addPlatInfo(platInfo);

            // 同步初始化平台公司信息到地区平台
            syncDataService.syncPlatInfo(platInfo);
        }
        catch (Exception e)
        {
            log.error("保存平台公司信息失败[param=" + platInfo + "]", e);
            throw new HsException(RESErrorCode.RES_SAVE_PLAT_INFO_ERROR.getCode(), "保存平台公司信息失败[param=" + platInfo + "]"
                    + e);
        }
    }

    /**
     * 查询平台公司信息
     * 
     * @param platNo
     *            平台代码
     * @param entCustName
     *            平台名称
     * @param page
     *            分页信息
     * @return 平台公司信息列表
     */
    public PageData<PlatInfo> queryPlatInfo(String platNo, String entCustName, Page page) {
        if (null == page)
        {
            throw new HsException(RespCode.PARAM_ERROR.getCode(), "参数错误");
        }
        PageContext.setPage(page);
        PageData<PlatInfo> pageData = null;

        List<PlatInfo> list = initMapper.queryPlatInfoListPage(platNo, entCustName);

        if (null != list && list.size() > 0)
        {
            pageData = new PageData<PlatInfo>();
            pageData.setResult(list);
            pageData.setCount(PageContext.getPage().getCount());
        }
        return pageData;

    }

    /**
     * 添加管理公司信息
     * 
     * @param mentInfo
     *            管理公司信息
     * @throws HsException
     */
    @Override
    @Transactional
    public void addMentInfo(MentInfo mentInfo) throws HsException {

        if (null == mentInfo)
        {
            throw new HsException(RespCode.PARAM_ERROR.getCode(), "参数错误");
        }
        try
        {
            // 保存管理公司信息
            initMapper.addMentInfo(mentInfo);

            // 生成管理公司下级999个服务公司互生号
            List<String> serEntResNoList = this.genSerEntResNo(mentInfo.getEntResNo());
            quotaMapper.initQuota(serEntResNoList);
        }
        catch (Exception e)
        {
            log.error("保存管理公司信息失败[param=" + mentInfo + "]", e);
            throw new HsException(RESErrorCode.RES_SAVE_MENT_INFO_ERROR.getCode(), "保存管理公司信息失败[param=" + mentInfo + "]"
                    + e);
        }
    }

    /**
     * 查询管理公司信息
     * 
     * @param entResNo
     *            管理公司互生号
     * @param entCustName
     *            管理公司名称
     * @param page
     *            分页信息
     * @return 管理公司信息列表
     */
    @Override
    public PageData<MentInfo> queryMentInfo(String entResNo, String entCustName, Page page) {
        if (null == page)
        {
            throw new HsException(RespCode.PARAM_ERROR.getCode(), "参数错误");
        }
        PageContext.setPage(page);
        PageData<MentInfo> pageData = null;

        List<MentInfo> list = null;// initMapper.queryMentInfoListPage(entResNo,
                                   // entCustName);

        if (null != list && list.size() > 0)
        {
            pageData = new PageData<MentInfo>();
            pageData.setResult(list);
            pageData.setCount(PageContext.getPage().getCount());
        }
        return pageData;
    }

    /**
     * 添加平台与管理公司关系
     * 
     * @param platMent
     *            平台与管理公司关系
     * @throws HsException
     */
    @Override
    @Transactional
    public void addPlatMent(PlatMent platMent) throws HsException {
        if (null == platMent)
        {
            throw new HsException(RespCode.PARAM_ERROR.getCode(), "参数错误");
        }
        String platNo = platMent.getPlatNo();
        String entResNo = platMent.getEntResNo();
        boolean isExist = initMapper.isExistPlatMent(platNo, entResNo);
        if (isExist)
        {// 此平台与管理公司关系已在存
            throw new HsException(RESErrorCode.RES_EXIST_PLAT_MENT_RELATIONSHIP.getCode(), "该平台与管理公司关系已在存");
        }

        // 是否违反单向一对多
        // 1.单独通过平台代码和管理公司互生号都能查到记录，即平台代码和管理公司互生号都存在记录
        // 2.平台代码不存在，但管理公司互生号存在，并且管理公司互生号对应的平台已关联多个管理公司
        // 3.管理公司互生号不存在，但平台代码存在，并且平台代码对应的管理公司关联多个平台
        boolean violateSingleOne2Many = initMapper.violateSingleOne2Many(platNo, entResNo);
        if (violateSingleOne2Many)
        {
            throw new HsException(RESErrorCode.RES_VIOLATE_SINGLE_ONE_TO_MANY.getCode(), "平台与管理公司关系违反单向一对多");
        }
        try
        {
            // 保存平台与管理公司关系
            initMapper.addPlatMent(platMent);

            // 向平台同步初始化管理公司信息
//            MentInfo mentInfo = initMapper.getMentInfoById(entResNo);
            // syncDataService.syncPlatMent(platNo, mentInfo);
        }
        catch (Exception e)
        {
            log.error("保存平台与管理公司关系失败[param=" + platMent + "]", e);
            throw new HsException(RESErrorCode.RES_SAVE_MENT_INFO_ERROR.getCode(), "保存平台与管理公司关系失败[param=" + platMent
                    + "]" + e);
        }
    }

    /**
     * 查询平台与管理公司关系
     * 
     * @param platNo
     *            平台
     * @param entResNo
     *            管理公司公司互生号
     * @return 返回平台与管理公司关系
     */
    @Override
    public List<PlatMent> queryPlatMent(String platNo, String entResNo) {
        return initMapper.queryPlatMent(platNo, entResNo);
    }

    /**
     * 手动同步初始化平台公司信息到地区平台
     * 
     * @param platNo
     *            平台代码
     * @throws HsException
     */
    @Override
    public void syncPlatInfo(String platNo) throws HsException {
        PlatInfo platInfo = initMapper.getPlatInfoById(platNo);
        syncDataService.syncPlatInfo(platInfo);
    }

    /**
     * 手动同步向平台同步初始化管理公司信息
     * 
     * @param platNo
     *            平台代码
     * @param mentNo
     *            管理公司互生号
     * @throws HsException
     */
    @Override
    public void syncPlatMent(String platNo, String mentNo) throws HsException {
        MentInfo mentInfo = initMapper.getMentInfoById(mentNo);
        // syncDataService.syncPlatMent(platNo, mentInfo);
    }

    // 生成管理公司下级999个服务公司互生号
    private List<String> genSerEntResNo(String mentEntResNo) {
        List<String> serEntResNoList = new ArrayList<String>();
        String mentEntResNoPre = mentEntResNo.substring(0, 2);
        String serResNo = "";
        for (int i = 1; i < 1000; i++)
        {
            if (i < 10)
            {
                serResNo = mentEntResNoPre + "00" + i + "000000";
            }
            else if (i < 100)
            {
                serResNo = mentEntResNoPre + "0" + i + "000000";
            }
            else
            {
                serResNo = mentEntResNoPre + i + "000000";
            }
            serEntResNoList.add(serResNo);
        }
        return serEntResNoList;
    }

}

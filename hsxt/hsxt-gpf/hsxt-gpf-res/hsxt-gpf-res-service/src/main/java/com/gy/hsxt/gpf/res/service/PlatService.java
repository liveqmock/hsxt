/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.gpf.res.service;

import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.gpf.gcs.api.IGCSRouteRuleService;
import com.gy.hsxt.gpf.gcs.bean.Plat;
import com.gy.hsxt.gpf.res.RESErrorCode;
import com.gy.hsxt.gpf.res.bean.PlatInfo;
import com.gy.hsxt.gpf.res.common.PageContext;
import com.gy.hsxt.gpf.res.interfaces.IPlatService;
import com.gy.hsxt.gpf.res.interfaces.ISyncDataService;
import com.gy.hsxt.gpf.res.mapper.InitMapper;
import com.gy.hsxt.gpf.um.bean.GridData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

import static com.gy.hsxt.common.utils.StringUtils.isBlank;

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
public class PlatService implements IPlatService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Resource
    private InitMapper initMapper;

    @Resource
    private IGCSRouteRuleService iGCSRouteRuleService;

    @Resource
    private ISyncDataService syncDataService;

    /**
     * 查询平台公司信息
     * 
     * @param entResNo
     *            平台企业互生号
     * @param entCustName
     *            平台名称
     * @param page
     *            分页信息
     * @return 平台公司信息列表
     */
    public GridData<PlatInfo> queryPlatInfo(String entResNo, String entCustName, Page page) {
        if (null == page)
        {
            throw new HsException(RespCode.PARAM_ERROR);
        }
        PageContext.setPage(page);
        List<PlatInfo> list = initMapper.queryPlatInfoListPage(entResNo, entCustName);
        return GridData.bulid(true, page.getCount(), page.getCurPage(), list);
    }

    /**
     * 查询未初始化的平台信息
     * 
     * @return 未初始化的平台信息列表
     * @throws HsException
     */
    @Override
    public List<PlatInfo> queryUnInitPlatInfo() throws HsException {
        List<PlatInfo> unInitPlatInfo = new ArrayList<PlatInfo>();// 未初始化非总平台
        List<Plat> platList = iGCSRouteRuleService.findRegionPlats();// 所有地区平台

        if (null != platList && platList.size() > 0)
        {
            List<String> platNoList = initMapper.queryPlatNoList();// 已初始化平台
            for (Plat plat : platList)
            {
                if (!platNoList.contains(plat.getPlatNo()))
                {
                    PlatInfo platInfo = new PlatInfo();
                    platInfo.setPlatNo(plat.getPlatNo());
                    platInfo.setEntResNo("00000000" + plat.getPlatNo());
                    platInfo.setEntCustName(plat.getPlatNameCn());
                    unInitPlatInfo.add(platInfo);
                }
            }
        }
        return unInitPlatInfo;
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
    public void addPlatInfo(final PlatInfo platInfo) throws HsException {
        if (null == platInfo || isBlank(platInfo.getPlatNo()) || isBlank(platInfo.getPlatNo())
                || isBlank(platInfo.getEntResNo()) || isBlank(platInfo.getEntCustName())
                || isBlank(platInfo.getEmailA()) || isBlank(platInfo.getEmailA())

        )
        {
            throw new HsException(RespCode.PARAM_ERROR);
        }
        try
        {
            platInfo.setEntResNo("00000000" + platInfo.getPlatNo());
            // 保存平台公司信息
            initMapper.addPlatInfo(platInfo);
        }
        catch (Exception e)
        {
            log.error("保存平台公司信息失败[param=" + platInfo + "]", e);
            throw new HsException(RESErrorCode.RES_SAVE_PLAT_INFO_ERROR.getCode(), "保存平台公司信息失败[param=" + platInfo + "]"
                    + e);
        }

        Thread t = new Thread() {
            @Override
            public void run() {
                try
                {
                    // 同步初始化平台公司信息到地区平台用户中心
                    syncDataService.syncPlatInfo(platInfo);
                }
                catch (HsException e)
                {
                    log.error("同步初始化平台公司信息到地区平台用户中心[param=" + platInfo + "]", e);
                }
            }
        };
        t.start();
    }

    /**
     * 修改平台公司信息
     * 
     * @param platInfo
     *            平台公司信息
     * @throws HsException
     */
    @Override
    public void updatePlatInfo(PlatInfo platInfo) throws HsException {
        if (null == platInfo || isBlank(platInfo.getPlatNo()) || isBlank(platInfo.getPlatNo())
                || isBlank(platInfo.getEntResNo()) || isBlank(platInfo.getEntCustName())
                || isBlank(platInfo.getEmailA()) || isBlank(platInfo.getEmailA())

        )
        {
            throw new HsException(RespCode.PARAM_ERROR);
        }
        try
        {
            // 保存平台公司信息
            initMapper.updatePlatInfo(platInfo);
        }
        catch (Exception e)
        {
            log.error("保存平台公司信息失败[param=" + platInfo + "]", e);
            throw new HsException(RESErrorCode.RES_SAVE_PLAT_INFO_ERROR.getCode(), "保存平台公司信息失败[param=" + platInfo + "]"
                    + e);
        }
    }

    /**
     * 同步平台公司信息到用户中心
     * 
     * @param platNo
     *            平台代码
     * @throws HsException
     */
    @Override
    public void syncPlatToUc(String platNo) throws HsException {
        PlatInfo platInfo = initMapper.getPlatInfoById(platNo);
        // 同步初始化平台公司信息到地区平台
        syncDataService.syncPlatInfo(platInfo);
    }

    /**
     * 查询所有已初始化平台
     * 
     * @return
     */
    @Override
    public List<PlatInfo> platListAll() {
        return initMapper.platListAll();
    }
}

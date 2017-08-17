/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.gpf.res.service;

import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.HsResNoUtils;
import com.gy.hsxt.gpf.res.RESErrorCode;
import com.gy.hsxt.gpf.res.bean.MentInfo;
import com.gy.hsxt.gpf.res.bean.PlatMent;
import com.gy.hsxt.gpf.res.bean.PlatMents;
import com.gy.hsxt.gpf.res.common.PageContext;
import com.gy.hsxt.gpf.res.interfaces.IManageService;
import com.gy.hsxt.gpf.res.interfaces.ISyncDataService;
import com.gy.hsxt.gpf.res.mapper.InitMapper;
import com.gy.hsxt.gpf.res.mapper.QuotaMapper;
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
public class ManageService implements IManageService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Resource
    private InitMapper initMapper;

    @Resource
    private QuotaMapper quotaMapper;

    @Resource
    private ISyncDataService syncDataService;

    /**
     * 添加平台与管理公司关系
     * 
     * @param platMents
     *            平台与管理公司关系
     * @throws HsException
     */
    @Override
    @Transactional
    public void addPlatMents(PlatMents platMents) throws HsException {
        if (null == platMents || null == platMents.getPlatMentList())
        {
            throw new HsException(RespCode.PARAM_ERROR);
        }
        // 取第一条记录
        PlatMent platMent1 = platMents.getPlatMentList().get(0);

        // 管理公司已分配配额
        int initedQuota = initMapper.queryMentInitQuota(platMent1.getEntResNo(), null);
        int applyInitQuota = 0;
        for (PlatMent platMent : platMents.getPlatMentList())
        {
            applyInitQuota += platMent.getInitQuota();
        }

        if (initedQuota + applyInitQuota > 999)
        {// 已初始化加申请的配额大于999
            throw new HsException(RESErrorCode.RES_MENT_QUOTA_EXCCED);
        }

        // 创建管理公司
        MentInfo mentInfo = new MentInfo(platMent1.getEntResNo(), platMent1.getEntCustName());
        mentInfo.setCreatedOptId(platMent1.getCreatedOptId());
        mentInfo.setCreatedOptName(platMent1.getCreatedOptName());
        mentInfo.setUpdatedOptId(platMent1.getUpdatedOptId());
        mentInfo.setUpdatedOptName(platMent1.getUpdatedOptName());
        this.addMentInfo(mentInfo);

        // 添加管理公司与地区平台关系
        for (PlatMent platMent : platMents.getPlatMentList())
        {
            this.addPlatMent(platMent);
        }
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

        if (null == mentInfo || isBlank(mentInfo.getEntResNo()) || !HsResNoUtils.isManageResNo(mentInfo.getEntResNo())
                || isBlank(mentInfo.getEntCustName()))
        {
            throw new HsException(RespCode.PARAM_ERROR);
        }
        if (initMapper.isExistMent(mentInfo.getEntResNo()))
        {// 该管理公司信息已存在
            throw new HsException(RESErrorCode.RES_MENT_EXIST);
        }
        try
        {
            // 保存管理公司信息
            int row= initMapper.addMentInfo(mentInfo);
            if(row != 1){
                throw new HsException(RESErrorCode.RES_SAVE_MENT_INFO_ERROR, "保存管理公司信息失败[param=" + mentInfo + "]"); 
            }
            // 生成管理公司下级999个服务公司互生号
            List<String> serEntResNoList = this.genSerEntResNo(mentInfo.getEntResNo());
            quotaMapper.initQuota(serEntResNoList);
        }catch (HsException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            log.error("保存管理公司信息失败[param=" + mentInfo + "]", e);
            throw new HsException(RESErrorCode.RES_SAVE_MENT_INFO_ERROR, "保存管理公司信息失败[param=" + mentInfo + "]"
                    + e);
        }
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

    /**
     * 添加平台与管理公司关系
     * 
     * @param platMent
     *            平台与管理公司关系
     * @throws HsException
     */
    @Override
    @Transactional
    public void addPlatMent(final PlatMent platMent) throws HsException {
        if (null == platMent)
        {
            throw new HsException(RespCode.PARAM_ERROR);
        }
        String platNo = platMent.getPlatNo();
        String entResNo = platMent.getEntResNo();
        boolean isExist = initMapper.isExistPlatMent(platNo, entResNo);
        if (isExist)
        {// 此平台与管理公司关系已在存
            throw new HsException(RESErrorCode.RES_EXIST_PLAT_MENT_RELATIONSHIP);
        }

        // 是否违反单向一对多
        // 1.单独通过平台代码和管理公司互生号都能查到记录，即平台代码和管理公司互生号都存在记录
        // 2.平台代码不存在，但管理公司互生号存在，并且管理公司互生号对应的平台已关联多个管理公司
        // 3.管理公司互生号不存在，但平台代码存在，并且平台代码对应的管理公司关联多个平台
        boolean violateSingleOne2Many = initMapper.violateSingleOne2Many(platNo, entResNo);
        if (violateSingleOne2Many)
        {// 平台与管理公司关系违反单向一对多
            throw new HsException(RESErrorCode.RES_VIOLATE_SINGLE_ONE_TO_MANY);
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

        Thread syncUcThread = new Thread() {
            @Override
            public void run() {
                try
                {
                    // 同步管理公司信息到地区平台用户中心
                    syncDataService.syncManageToUc(platMent);
                }
                catch (Exception e)
                {
                    log.error("同步管理公司信息到UC失败[param=" + platMent + "]", e);
                }
            }
        };
        try{
            syncUcThread.start();
        }catch(Exception e){
            log.error("另启线程同步管理公司信息到UC失败[param=" + platMent + "]", e);
        }

        Thread syncBsThread = new Thread() {
            @Override
            public void run() {
                try
                {
                    // 同步管理公司信息到地区平台业务系统
                    syncDataService.syncManageToBs(platMent);
                }
                catch (Exception e)
                {
                    log.error("同步管理公司信息到BS失败[param=" + platMent + "]", e);
                }
            }
        };
        try{
            syncBsThread.start();
        }catch(Exception e){
            log.error("另启线程同步管理公司信息到BS失败[param=" + platMent + "]", e);
        }
    }

    /**
     * 查询管理公司列表
     * 
     * @param entResNo
     *            管理公司互生号
     * @param entCustName
     *            管理公司名称
     * @param page
     *            分页
     * @return
     */
    @Override
    public GridData<PlatMent> queryManageList(String entResNo, String entCustName, Page page) {
        PageContext.setPage(page);
        List<PlatMent> list = initMapper.queryMentInfoListPage(entResNo, entCustName);
        return GridData.bulid(true, page.getCount(), page.getCurPage(), list);
    }

    /**
     * 同步管理公司信息到地区平台用户中心
     * 
     * @param platNo
     *            平台代码
     * @param entResNo
     *            管理公司互生号
     */
    @Override
    public void syncManageToUc(String platNo, String entResNo) {
        PlatMent platMent = initMapper.queryPlatMentById(platNo, entResNo);
        syncDataService.syncManageToUc(platMent);
    }

    /**
     * 同步管理公司信息到地区平台业务系统
     * 
     * @param platNo
     *            平台代码
     * @param entResNo
     *            管理公司互生号
     */
    @Override
    public void syncManageToBs(String platNo, String entResNo) {
        PlatMent platMent = initMapper.queryPlatMentById(platNo, entResNo);
        syncDataService.syncManageToBs(platMent);
    }
}

/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.gpf.gcs.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gy.hsxt.gpf.gcs.bean.ResNoRoute;
import com.gy.hsxt.gpf.gcs.common.Constant;
import com.gy.hsxt.gpf.gcs.interfaces.IResNoRouteService;
import com.gy.hsxt.gpf.gcs.interfaces.IVersionService;
import com.gy.hsxt.gpf.gcs.mapper.ResNoRouteMapper;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-gpf-gcs-service
 * 
 *  Package Name    : com.gy.hsxt.gpf.gcs.service
 * 
 *  File Name       : ResNoRouteService.java
 * 
 *  Creation Date   : 2015-7-6
 * 
 *  Author          : xiaofl
 * 
 *  Purpose         : 个人路由接口实现
 * 
 * 
 *  History         : TODO
 * 
 * </PRE>
 ***************************************************************************/
@Service("resNoRouteService")
public class ResNoRouteService implements IResNoRouteService {

    @Autowired
    private IVersionService veresionService;

    @Autowired
    private ResNoRouteMapper resNoRouteMapper;

    @Override
    @Transactional
    public int addResNoRoute(ResNoRoute resNoRoute) {
        long version = veresionService.addOrUpdateVersion(Constant.GL_RESNO_ROUTE);
        resNoRoute.setVersion(version);
        return resNoRouteMapper.addResNoRoute(resNoRoute);
    }

    @Override
    @Transactional
    public boolean deleteResNoRoute(String resNo) {
        long version = veresionService.addOrUpdateVersion(Constant.GL_RESNO_ROUTE);
        ResNoRoute resNoRoute = new ResNoRoute(resNo);
        resNoRoute.setVersion(version);
        return resNoRouteMapper.deleteResNoRoute(resNoRoute);
    }

    @Override
    @Transactional
    public boolean updateResNoRoute(ResNoRoute resNoRoute) {
        long version = veresionService.addOrUpdateVersion(Constant.GL_RESNO_ROUTE);
        resNoRoute.setVersion(version);
        return resNoRouteMapper.updateResNoRoute(resNoRoute);
    }

    @Override
    public List<ResNoRoute> queryResNoRoute(ResNoRoute resNoRoute) {
        return resNoRouteMapper.queryResNoRouteListPage(resNoRoute);
    }

    @Override
    public boolean existResNoRoute(String resNo) {
        boolean isExist = "1".equals(resNoRouteMapper.existResNoRoute(resNo));
        return isExist;
    }

    @Override
    public ResNoRoute queryResNoRouteWithPK(String resNo) {
        return resNoRouteMapper.queryResNoRouteWithPK(resNo);
    }

    @Override
    public List<ResNoRoute> queryResNoRoute4SyncData(Long version) {
        return resNoRouteMapper.queryResNoRoute4SyncData(version);
    }

    @Override
    @Transactional
    public void batchInsert(List<ResNoRoute> resNoRouteList) {
        long version = veresionService.addOrUpdateVersion(Constant.GL_RESNO_ROUTE);
        for (ResNoRoute resNoRoute : resNoRouteList)
        {
            resNoRoute.setVersion(version);
        }
        resNoRouteMapper.batchInsert(resNoRouteList);
    }

    /**
     * 批量保存或更新资源号路由关系列表
     * 
     * @param resNoList
     * @param platNo
     */
    @Override
    @Transactional
    public void syncAddRouteRule(List<String> resNoList, String platNo) {
        for (int i = 0; i < resNoList.size(); i++)
        {
            resNoList.set(i, resNoList.get(i).substring(0, 5));
        }
        List<String> existResNos = resNoRouteMapper.findExistResNo(resNoList);
        if (existResNos == null || existResNos.isEmpty())
        {// 新增的资源路由关系
            List<ResNoRoute> resNoRouteList = new ArrayList<ResNoRoute>();
            for (String resNo : resNoList)
            {
                ResNoRoute resNoRoute = new ResNoRoute();
                resNoRoute.setPlatNo(platNo);
                resNoRoute.setResNo(resNo);
                resNoRouteList.add(resNoRoute);
            }
            batchInsert(resNoRouteList);
        }
        else
        { // 有一部分或者全部资源号是已经存在的路由关系记录
            long version = veresionService.addOrUpdateVersion(Constant.GL_RESNO_ROUTE);
            List<ResNoRoute> updateList = new ArrayList<ResNoRoute>();
            for (String resNo : existResNos)
            {
                ResNoRoute resNoRoute = new ResNoRoute();
                resNoRoute.setPlatNo(platNo);
                resNoRoute.setResNo(resNo);
                resNoRoute.setDelFlag(false);
                resNoRoute.setVersion(version);
                updateList.add(resNoRoute);
            }
            resNoRouteMapper.batchUpdate(updateList); // 批量更新资源路由关系

            List<ResNoRoute> insertList = new ArrayList<ResNoRoute>();
            for (String resNo : resNoList)
            {
                if (!existResNos.contains(resNo))
                {
                    ResNoRoute resNoRoute = new ResNoRoute();
                    resNoRoute.setPlatNo(platNo);
                    resNoRoute.setResNo(resNo);
                    resNoRoute.setDelFlag(false);
                    resNoRoute.setVersion(version);
                    insertList.add(resNoRoute);
                }
            }
            if (!insertList.isEmpty())
            {
                resNoRouteMapper.batchInsert(insertList); // 批量插入新的资源路由关系
            }
        }
    }

    @Override
    public List<ResNoRoute> getResNoRouteList() {
        return resNoRouteMapper.getResNoRouteList();
    }

    @Override
    @Transactional
    public void batchDelResNoRoute(List<String> resNoList) {
        // 删除资源号路由关系时需要更新版本号才能同步
        long version = veresionService.addOrUpdateVersion(Constant.GL_RESNO_ROUTE);
        List<ResNoRoute> resNoRouteList = new ArrayList<ResNoRoute>();
        for (String resNo : resNoList)
        {
            ResNoRoute resNoRoute = new ResNoRoute();
            resNoRoute.setVersion(version);
            resNoRoute.setResNo(resNo);
            resNoRouteList.add(resNoRoute);
        }
        resNoRouteMapper.batchDelete(resNoRouteList);
    }
}

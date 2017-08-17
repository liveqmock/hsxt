package com.gy.hsxt.ps.reconciliation.service;

import com.alibaba.fastjson.JSON;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.DateUtil;
import com.gy.hsxt.ps.api.IPsReconciliationService;
import com.gy.hsxt.ps.common.*;
import com.gy.hsxt.ps.distribution.bean.Alloc;
import com.gy.hsxt.ps.distribution.bean.PointPage;
import com.gy.hsxt.ps.reconciliation.handle.CheckFileRunnable;
import com.gy.hsxt.ps.reconciliation.mapper.ReconciliationMapper;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @version v0.0.1
 * @description 查询对账服务接口实现类
 * @createDate 2015-7-27 上午10:14:12
 * @Company 深圳市归一科技研发有限公司
 */
@Service
public class ReconciliationService implements IPsReconciliationService {
	
    @Autowired
    private ReconciliationMapper reconciliationMapper;

    @Autowired
    private ThreadPoolTaskExecutor jobExecutor;


    // 账务明细对账
    @Override
    public void balanceAccount(String runDate) throws HsException {

        StringBuffer sb = new StringBuffer();

        String filePath = PropertyConfigurer.getProperty("ps.checkFilePath")+DateUtil.DateToString(new Date(), DateUtil.DATE_FORMAT) +File.separator;

        //文件明细路径
        String writePathName = filePath + "PS_AC_" + DateUtil.DateToString(new Date(), DateUtil.DATE_FORMAT)+ "_DET_";

        //CHK文件路径
        String writePathNameChk = filePath+ "PS_AC_" + DateUtil.DateToString(new Date(), DateUtil.DATE_FORMAT)+ "_CHK";

        //查询正常积分单数据分页
        PointPage pp = this.countDayOfEnd(runDate);

        // 获取总共页数
        int pageSize = pp.getPageCount();

        //CHK文件，第一行总文件数
        sb.append(pageSize);
        // 换行
        sb.append("\r\n");

        // 设置线程数
        int nThreads = Constants.BAT_NTHREADS;

        // 创建一个可重用固定线程数的线程池
        //ExecutorService pool = Executors.newFixedThreadPool(nThreads);

        int i = 0;
        while (i < pageSize) {
            // 循环设置页数
            pp.setRow(i);
            List<Alloc> list = reconciliationMapper.queryDayOfEnd(pp);

            //chk文件，第二行开始为文件明细及明细文件笔数
            sb.append(writePathName + (i+1)+"|"+list.size());

            //换行
            sb.append("\r\n");

            // 将线程放入池中执行，写明细文件
            jobExecutor.execute(new CheckFileRunnable(list, writePathName +(i+1)));
            i++;
        }
        // 关闭线程池
       // PsTools.shutdown(pool);


        //写文件开始
        try {
            sb.append("end");
            File file = new File(writePathNameChk);
            //写CHK文件
            FileUtils.writeStringToFile(file, sb.toString(), "UTF-8");
        } catch (IOException e) {
             PsException.psThrowException(new Throwable().getStackTrace()[0], RespCode.PS_WRITE_FILE_ERROR.getCode(), "写文件出现异常",e);
        }
        //写文件结束
    }


    /**
     * 查询积分订单总数
     *
     * @return PointPage 返回分页对象信息
     */
    public PointPage countDayOfEnd(String runDate) {
        // 创建分页对象
        PointPage pp = new PointPage();
        // 查询可分配积分单数量
        int pageSum = reconciliationMapper.countDayOfEnd(runDate);
        if (pageSum > 0) {
            // 判断如果有积分单,则设置积分单总数
            pp.setPageSum(pageSum);
        } else {
            PsException.psExceptionNotThrow(new Throwable().getStackTrace()[0], RespCode.PS_ORDER_NOT_FOUND.getCode(),"对账数据没有查寻到，原订单不存在没有找到" );
        }
        return pp;
    }
}

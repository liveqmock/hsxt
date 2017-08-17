package com.gy.hsxt.ao.batch;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.gy.hsxt.ao.bean.BuyHsb;
import com.gy.hsxt.ao.disconf.AoConfig;
import com.gy.hsxt.ao.file.bean.CheckInfo;
import com.gy.hsxt.ao.file.bean.DataInfo;
import com.gy.hsxt.ao.file.callable.CheckFileCallable;
import com.gy.hsxt.ao.file.callable.subcall.BuyHsbDataCallable;
import com.gy.hsxt.ao.mapper.BuyHsbMapper;
import com.gy.hsxt.common.utils.DateUtil;

@Service
public class BatchBuyHsbFileGenerateService extends AbstractBatchService {

    /**
     * 兑换互生币mapper
     */
    @Autowired
    BuyHsbMapper buyHsbMapper;

    /**
     * 业务执行方法
     * 
     * @param executeId
     *            任务执行编号
     * @param scanDate
     *            扫描日期
     * @throws Exception
     */
    @Override
    public void execute(String executeId, String scanDate) throws Exception {
        // 扫描日期
        Date scan = DateUtil.StringToDate(scanDate, DateUtil.DATE_FORMAT);
        // 校验文件名
        String checkFileName = AoConfig.AO_GP_PREFIX + scanDate + AoConfig.FILE_CHECK_TAIL;

        List<BuyHsb> netBuyHsbList = buyHsbMapper.findNetPayBuyHsb(scan, DateUtil.addDays(scan, 1));
        // 文件夹路径
        String dirPath = this.getAoConfig().joinPreDirPath(scanDate); // 文件路径
        int maxLineNum = this.getAoConfig().getSumRow();

        int count = netBuyHsbList.size();// 网银兑换互生币记录数
        int fileNum = (count + maxLineNum - 1) / maxLineNum;// 计算要生成多少个文档

        CheckInfo checkInfo = CheckInfo.bulid(fileNum, dirPath, checkFileName).setNeed(true);// 校验文件信息

        CheckFileCallable checkFileCallable = CheckFileCallable.bulid(this.getApplicationContext(), executeId,
                checkInfo);// 构建校验文件回调

        if (CollectionUtils.isNotEmpty(netBuyHsbList))
        {

            for (int i = 1; i <= fileNum; i++)
            {
                int startNo = (i - 1) * maxLineNum;// 业务编号段 开始

                int endNo = i * maxLineNum;// 业务编号段 结束
                // 如果实际记录数不足一整段，结束编号为记录最大编号
                if (endNo > netBuyHsbList.size())
                {
                    endNo = netBuyHsbList.size();
                }

                List<BuyHsb> subList = netBuyHsbList.subList(startNo, endNo);// 文件数据

                // 文件名称
                String txtName = AoConfig.AO_GP_PREFIX + scanDate + "_DET_" + i;

                DataInfo<BuyHsb> dataInfo = DataInfo.bulid(dirPath, txtName, subList);// 数据文件信息
                // 网银购买互生币回调
                BuyHsbDataCallable buyHsbDataCallable = BuyHsbDataCallable.bulid(this.getApplicationContext(),
                        executeId, dataInfo, checkInfo);
                // 执行回调
                this.getThreadPoolTaskExecutorSupport().submit(checkFileCallable, buyHsbDataCallable);
            }
        }
        else
        {
            this.getThreadPoolTaskExecutorSupport().execute(checkFileCallable);
        }
    }
}

package com.gy.hsxt.bs.bm.service;

import com.gy.hsxt.bs.bean.bm.BmlmDetail;
import com.gy.hsxt.bs.bean.bm.MlmTotal;
import com.gy.hsxt.bs.bm.interfaces.IBmlmDetailService;
import com.gy.hsxt.bs.bm.interfaces.IBsFssNotifyService;
import com.gy.hsxt.bs.bm.interfaces.IMlmTotalService;
import com.gy.hsxt.bs.disconf.FileConfig;
import com.gy.hsxt.common.utils.DateUtil;
import com.gy.hsxt.common.utils.MD5Utils;
import com.gy.hsxt.gpf.fss.bean.FileDetail;
import com.gy.hsxt.gpf.fss.bean.LocalNotify;
import com.gy.hsxt.gpf.fss.constant.FssNotifyType;
import com.gy.hsxt.gpf.fss.constant.FssPurpose;
import com.gy.hsxt.gpf.fss.utils.NotifyNoUtil;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Package :com.gy.hsxt.bs.bm.service
 * @ClassName : FssNotifyServiceTest
 * @Description : TODO
 * @Author : chenm
 * @Date : 2015/11/7 15:15
 * @Version V3.0.0.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:test/spring-global.xml")
public class BsFssNotifyServiceTest {

    @Resource
    IBsFssNotifyService bsFssNotifyService;

    @Test
    public void testMlmDataNotify() throws Exception {
        String now = DateUtil.DateTimeToString(new Date());
        LocalNotify localNotify = new LocalNotify();
        localNotify.setPurpose(FssPurpose.BM_MLM.getCode());
        localNotify.setFromPlat("000");
        localNotify.setFromSys("BM");
        localNotify.setToPlat("011");
        localNotify.setToSys("BS");
        localNotify.setNotifyNo(NotifyNoUtil.outNotifyNo(localNotify));
        localNotify.setAllPass(1);
        localNotify.setNotifyTime(now);
        localNotify.setNotifyType(FssNotifyType.LOCAL_BACK.getTypeNo());
        localNotify.setFileCount(1);
        localNotify.setAllCompleted(1);
        localNotify.setCompletedTime(now);
        localNotify.setCreateTime(now);

        List<FileDetail> details = new ArrayList<>();

        FileDetail detail = new FileDetail();
        detail.setIsPass(1);
        detail.setNotifyNo(localNotify.getNotifyNo());
        detail.setFileName("mlm20160117.BM.out");
        detail.setIsLocal(1);
        detail.setFileSize(543);
        detail.setUnit("B");
        detail.setSource("F:/upload/BM/2016/0117/mlm/01001/mlm20160117.BM.out");
        detail.setTarget("F:/upload/BM/2016/0117/mlm/01001/mlm20160117.BM.out");
        detail.setRecount(0);
        detail.setCode("906c4e2b32aa7455ca3978ffa187f3dd");
        details.add(detail);

        localNotify.setDetails(details);

        bsFssNotifyService.mlmDataNotify(localNotify);

    }

    @Test
    public void testBmlmDataNotify() throws Exception {
        String now = DateUtil.DateTimeToString(new Date());
        LocalNotify localNotify = new LocalNotify();
        localNotify.setPurpose(FssPurpose.BM_BMLM.getCode());
        localNotify.setFromPlat("000");
        localNotify.setFromSys("BM");
        localNotify.setToPlat("011");
        localNotify.setToSys("BS");
        localNotify.setNotifyNo(NotifyNoUtil.outNotifyNo(localNotify));
        localNotify.setAllPass(1);
        localNotify.setNotifyTime(now);
        localNotify.setNotifyType(FssNotifyType.LOCAL_BACK.getTypeNo());
        localNotify.setFileCount(1);
        localNotify.setAllCompleted(1);
        localNotify.setCompletedTime(now);
        localNotify.setCreateTime(now);

        List<FileDetail> details = new ArrayList<>();

        FileDetail detail = new FileDetail();
        detail.setIsPass(1);
        detail.setNotifyNo(localNotify.getNotifyNo());
        detail.setFileName("bmlm20151031.BM.out");
        detail.setIsLocal(1);
        detail.setFileSize(557);
        detail.setUnit("B");
        detail.setSource("F:/upload/BM/2015/1031/bmlm/01001/bmlm20151031.BM.out");
        detail.setTarget("F:/upload/BM/2015/1031/bmlm/01001/bmlm20151031.BM.out");
        detail.setRecount(0);
        detail.setCode("fee4a2ce4b43ce02009dc6776fe44aff");
        details.add(detail);

        localNotify.setDetails(details);

        bsFssNotifyService.bmlmDataNotify(localNotify);
    }

    @Test
    public void testDateUtil() throws Exception {

        List<List<String>> dataList = new ArrayList<>();
        List<String> data = new ArrayList<>();
        int len = 14;
        for (int i = 0; i < len; i++) {
            data.add(String.valueOf(i));
            if (i % 3==0) {
                data.add("================");
                dataList.add(data);
                data = new ArrayList<>();
            }
        }
        if (len % 3 > 0) {
            data.add("================");
            dataList.add(data);
        }

        //形成文件
        try {
            String today = DateUtil.getCurrentDateNoSign();
            //文件校验信息列表
            List<String> fileRecords = new ArrayList<>();
            //文件校验信息
            for (int i = 0; i < dataList.size(); i++) {
                StringBuilder record = new StringBuilder();
                String path = "F:/bs/"+today+"_" + (i+1) + ".txt";
                File file = new File(path);
                FileUtils.writeLines(file,"UTF-8",dataList.get(i));
                /**
                 * 校验文件内容格式：文件名称|记录条数|MD5签名
                 */
                record.append(file.getName()).append(FileConfig.DATA_SEPARATOR) //文件名称
                        .append(dataList.get(i).size()).append(FileConfig.DATA_SEPARATOR) //记录条数
                        .append(MD5Utils.toMD5code(file)); //MD5签名

                fileRecords.add(record.toString());
            }

            /**
             * 校验文件名称格式：YYYYMMDD_CHECK.TXT
             */
            String checkPath = "F:/BS/"+today+FileConfig.FILE_CHECK_TAIL;
            File check = new File(checkPath);
            //将文件校验信息列表写入校验文件
            FileUtils.writeLines(check, FileConfig.DEFAULT_CHARSET, fileRecords);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void checkFileTest() {
        try {
            File dir = new File("F:/bs/");
            if (!ObjectUtils.isEmpty(dir.listFiles())) {
                for (File file : dir.listFiles()) {
                    System.out.println(MD5Utils.toMD5code(file));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Resource
    private IMlmTotalService mlmTotalService;
    @Resource
    private IBmlmDetailService bmlmDetailService;
    @Test
    public void queryDetail() {
        String bmlmId = "110120160115174114000000";
        BmlmDetail bmlmDetail = bmlmDetailService.queryById(bmlmId);
        System.out.println(bmlmDetail);

        String totalId = "110120160112204648000000";
        MlmTotal mlmTotal = mlmTotalService.queryById(totalId);
        System.out.println(mlmTotal);
    }
}
package com.gy.hsxt.bs;

import com.gy.hsxt.bs.batch.InvoiceTotalParseTask;
import com.gy.hsxt.bs.common.StringUtil;
import com.gy.hsxt.bs.disconf.FileConfig;
import com.gy.hsxt.common.utils.BigDecimalUtils;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Package :com.gy.hsxt.bs.bm.service
 * @ClassName : FssNotifyServiceTest
 * @Description : TODO
 * @Author : chenm
 * @Date : 2015/11/7 15:15
 * @Version V3.0.0.0
 */
public class CommonTest {

    @Test
    public void testDateUtil() throws Exception {
        double d1 = 255555.5555555;
        double d2 = 355555.5555555;
        double d3 = -155555.5555555;

        String s1 = "255555.55555559";
        String s2 = "355555.55555559";
        String s3 = "-155555.55555559";

        System.out.println(BigDecimalUtils.ceilingAdd(s1, s2) + " || " + BigDecimalUtils.floorAdd(s1, s2));
        System.out.println(BigDecimalUtils.ceilingAdd(s2, s3) + " || " + BigDecimalUtils.floorAdd(s2, s3));
        System.out.println("===============================");
        System.out.println(BigDecimalUtils.ceilingSub(s1, s2) + " || " + BigDecimalUtils.floorSub(s1, s2));
        System.out.println(BigDecimalUtils.ceilingSub(s2, s3) + " || " + BigDecimalUtils.floorSub(s2, s3));
        System.out.println("===============================");
        System.out.println(BigDecimalUtils.ceilingMul(s1, s2) + " || " + BigDecimalUtils.floorMul(s1, s2));
        System.out.println(BigDecimalUtils.ceilingMul(s2, s3) + " || " + BigDecimalUtils.floorMul(s2, s3));
        System.out.println("===============================");
        System.out.println(BigDecimalUtils.ceilingDiv(s1, s2) + " || " + BigDecimalUtils.floorDiv(s1, s2));
        System.out.println(BigDecimalUtils.ceilingDiv(s2, s3) + " || " + BigDecimalUtils.floorDiv(s2, s3));
        System.out.println("===============================");
    }

    @Test
    public void testReference() {

        List<String> strList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            strList.add(i+"");
        }

        List<String> fs1 = strList.subList(0,20);
        System.out.println(fs1);
        int maxLineNum = 6;

        int fileNum = (strList.size() + maxLineNum - 1) / maxLineNum;

        AtomicInteger integer = new AtomicInteger();
        for (int i = 1; i <= fileNum; i++) {
            int start = (i - 1) * maxLineNum;
            int end = i * maxLineNum;
            System.out.println(end);
            if (end > strList.size()) {
                end = strList.size();
            }
            List<String> fs = strList.subList(start,end);
            System.out.println(fs);
            System.out.println(integer.getAndIncrement());
        }


    }

    @Test
    public void testReadFile() throws Exception {
        String dir =   "F:" + InvoiceTotalParseTask.INVOICE_FILE_PATH + File.separator + "20160303" + File.separator;
        //读取check文件
        File check = FileUtils.getFile(dir + "20160303" + FileConfig.FILE_CHECK_TAIL);

        List<String> lines = FileUtils.readLines(check, FileConfig.DEFAULT_CHARSET);
        for (String line : lines) {
            System.out.println(line);
        }
    }

}
package com.gy.hsxt.gpf.bm.controller;

import com.gy.hsxt.gpf.bm.job.PointValueJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by ques134z-erete on 2015/4/22.
 */
@Controller
public class TestJobController {


    @Autowired
    private PointValueJob pointValueJob;

    @RequestMapping(value = "/test/bmlm", method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public String testBmlmJob() throws Exception{
//        bmlmJob.execute();
        return "test bmlm finish!";
    }
    @RequestMapping(value = "/test/mlm", method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public String testMlmJob(){
        pointValueJob.execute();
        return "test mlm finish!";
    }
}

/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.os.common.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Hex;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.gy.hsi.fs.client.FS;
import com.gy.hsi.fs.common.exception.FsException;
import com.gy.hsxt.access.web.common.bean.HttpRespEnvelope;
import com.gy.hsxt.access.web.common.constant.ASRespCode;
import com.gy.hsxt.access.web.common.utils.FileUtils;
import com.gy.hsxt.access.web.common.utils.RequestUtil;
import com.gy.hsxt.common.constant.AsConstants;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.StringUtils;
import com.gy.hsxt.uc.as.bean.enumerate.ChannelTypeEnum;

/***
 *  主要是对接文件系统 ，对文件的操作
 * 
 * @Package: com.gy.hsxt.access.web.aps.controllers.common  
 * @ClassName: CopyOfSystemRegisterController 
 * @Description: TODO
 *
 * @author: liuxy 
 * @date: 2015-10-19 下午4:22:10 
 * @version V3.0.0
 */
@Controller
@RequestMapping("fileController")
public class FileController  {
	
	/**word类型文件支持格式*/
	public static final String[] WORD_TYPES = {"doc", "docx"};
	
	/**so类型文件支持格式*/
    public static final String[] SO_TYPES = {"so"};
	
	/**zip类型文件支持格式*/
	public static final String[] ZIP_TYPES = {"zip"};
	
	/** 证书模板文件 */
	public static final String[] WEB_TYPES = {"htm","html","jpg","bmp","jpeg"};

    /***
     * 上传证件的图片
     * 
     * @param request
     * @return
     */
    @RequestMapping(value = { "/credencePicUpload" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public HttpRespEnvelope credencePicUpload(String custId, String token, HttpServletRequest request,
            HttpServletResponse response) throws IOException {

        HttpRespEnvelope hre = null;
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;

        // 检查文件是否为图片
        if (null != (hre = checkFile(multipartRequest, AsConstants.PICTURE_TYPES, AsConstants.PICTURE_SIZE)))
        {
            return hre;
        }
        return fileUpload(custId,token,request,response);

    }
    /***
     * 上传文件
     * 
     * @param request
     * @return
     */
    @RequestMapping(value = { "/fileUpload" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public HttpRespEnvelope fileUpload(String custId, String token, HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        
        HttpRespEnvelope hre = null;
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        
        Iterator<String> iter = multipartRequest.getFileNames();
        String annexType = request.getParameter("annexType");//文件类型
        List<byte[]> datas = new ArrayList<>();
        List<String> names = new ArrayList<>();
        List<String> fileSuffixs = new ArrayList<>();
        List<String> resultIds = null;
        Map<String, String> idsMap = new HashMap<>();
        
        if ((hre = checkToken(request)) != null)
        {
            return hre;
        }
        try
        {
            MultipartFile file=null;
	        while (iter.hasNext())
	        {
	            // 取得上传文件
	            file = multipartRequest.getFile(iter.next());
	            if (file != null)
	            {
	                // 取得当前上传文件的文件名称
	                String myFileName = file.getOriginalFilename();
	                // 如果名称不为“”,说明该文件存在，否则说明该文件不存在
	                if (StringUtils.isNotBlank(myFileName))
	                {
	                	//文件检查
	                    this.checkFile(file.getSize(), myFileName, file.getName(), annexType);
	                    datas.add(file.getBytes());
	                    names.add(file.getName());
	                    fileSuffixs.add(FileUtils.getFileType(myFileName));
	                }
	            }
	        }
        
            // 提交到文件服务器
            if(!datas.isEmpty()){
                resultIds = FS.getClient().uploadFileByBatch(datas, fileSuffixs, null, custId, token, String.valueOf(ChannelTypeEnum.WEB.getType()));
                for (int i = 0; i < names.size(); i++)
                {
                    idsMap.put(names.get(i), resultIds.get(i));
                    File myFile = new File("D:\\MD5File");
                    file.transferTo(myFile);
                    idsMap.put("fileMD5",this.getMd5ByFile(new File("D:\\MD5File")));
                    idsMap.put("fileSize",String.valueOf(file.getSize()));
                }
            }
            
            //删除文件
            this.deleteFile(request, custId, token);
            
            // 返回ID与服务上对应该文件ID的MAP
            hre = new HttpRespEnvelope(idsMap);
        }
        catch (FsException e)
        {
            hre = new HttpRespEnvelope(e);
            hre.setRetCode(e.getErrorCode());
        }
        catch (HsException e)
        {
            hre = new HttpRespEnvelope(e);
        }
        return hre;
    }
    
    /**
     * 检查上传的文件
     * @param fileSize 文件大小
     * @param fileType 文件类型
     * @param fileName 文件名称
     * @param annexType 允许上传的类型
     * @return
     */
    private void checkFile(long fileSize, String fileType, String fileName, String annexType) {
    	if(StringUtils.isBlank(annexType)){//默认检查是否为图片
    		if(!checkPicType(AsConstants.PICTURE_TYPES, fileType)){
        		throw new HsException(RespCode.AS_FILE_UPLOAD_TYPE_INVALID);
        	}
    	}else{
    		if("word".equals(annexType)){//检查Word类型
    			if(!checkPicType(WORD_TYPES, fileType)){
            		throw new HsException(ASRespCode.AS_FILE_UPLOAD_TYPE_INVALID);
            	}
    		}else if("zip".equals(annexType)){//检查Zip类型
    			if(!checkPicType(ZIP_TYPES, fileType)){
            		throw new HsException(ASRespCode.AS_FILE_UPLOAD_TYPE_INVALID);
            	}
    		}else if("html".equals(annexType)){//检查html类型
    			if(!checkPicType(WEB_TYPES, fileType)){
            		throw new HsException(ASRespCode.AS_FILE_UPLOAD_TYPE_INVALID);
            	}
    		}else if("so".equals(annexType)){//检查so类型
                if(!checkPicType(SO_TYPES, fileType)){
                    throw new HsException(ASRespCode.AS_FILE_UPLOAD_TYPE_INVALID);
                }
            }
    	}
    	if(fileSize > AsConstants.PICTURE_SIZE){
    		throw new HsException(RespCode.AS_FILE_UPLOAD_MAXSIZE_INVALID);
    	}
    }
    
    /**
     * 方法名称：删除文件
     * 方法描述：上传之前删除以前文件
     * @param request HttpServletRequest对象
     * @param custId 客户ID
     * @param token token
     */
    private void deleteFile(HttpServletRequest request, String custId, String token) {
        String ids = request.getParameter("delFileIds");
        if(!StringUtils.isBlank(ids)){
            String[] delFileIds = ids.split(",");
            if(delFileIds  != null && delFileIds.length > 0){
                try
                {
                    FS.getClient().deleteFile(delFileIds, custId, token, "WEB");
                }
                catch (FsException e)
                {
                }
            }
        }
    }

    /**
     * 检查上传的文件
     * 
     * @param multipartRequest
     * @param fileTypes
     * @param size
     * @return
     */
    private HttpRespEnvelope checkFile(MultipartHttpServletRequest multipartRequest, String[] fileTypes,
            long standardSize) {
        Iterator<String> iter = multipartRequest.getFileNames();
        while (iter.hasNext())
        {
            // 取得上传文件
            MultipartFile file = multipartRequest.getFile(iter.next());
            if (file != null && !StringUtils.isBlank(file.getOriginalFilename()))
            {
                if (!checkPicType(fileTypes, file.getOriginalFilename()))
                {
                    return new HttpRespEnvelope(22, file.getName());
                }
                if (file.getSize() > standardSize)
                {
                    return new HttpRespEnvelope(22, file.getName());
                }
            }
        }

        return null;

    }

    /**
     * 检查token
     * 
     * @param request
     * @return
     */
    private HttpRespEnvelope checkToken(HttpServletRequest request) {
        // 变量声明
        HttpRespEnvelope hre = null;
        String custId = request.getParameter("custId");// 当前操作用户id
        String token = request.getParameter("token"); // 安全令牌

        // 数据非空验证
        hre = RequestUtil.checkParamIsNotEmpty(new String[] { custId, "common.tonkNonEmptyPrompt" }, new String[] {
                token, "common.tonkNonEmptyPrompt" });

        if (hre != null)
        {
            return hre;
        }

        // 调用用户中心提供的dubbo接口, 进行安全令牌验证
        boolean success = true;
        // userCenterService.verifySecureToken(custId,token)

        // token验证返回
        if (!success)
        {
            hre = new HttpRespEnvelope(RespCode.AS_SECURE_TOKEN_INVALID.getCode(), "Invalid Token");
        }
        return hre;
    }

    /***
     * 检查上传图片的类型
     * 
     * @param fileName
     * @return
     */
    public boolean checkPicType(String[] fileTypes, String fileName) {

        for (String s : fileTypes)
        {
            if (fileName.toLowerCase().endsWith(s))
            {
                return true;
            }
        }
        return false;
    }
    
    public static String getMd5ByFile(File file) throws FileNotFoundException {
        FileInputStream in = new FileInputStream(file);
        long fileSize = file.length();
        byte[] md5Byte = new byte[16], finalByte = new byte[16];
        try
        {
            byte[] tempByte = null;
            for (int i = 0; i * 1024 < fileSize; i++)
            {
                tempByte = new byte[1024];
                in.read(tempByte);
                MessageDigest md5 = MessageDigest.getInstance("MD5");
                md5.update(tempByte);
                md5Byte = md5.digest();
                finalByte = xorByteArray(finalByte, md5Byte);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (null != in)
            {
                try
                {
                    in.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }

        //String crcCode = new BigInteger(1, finalByte).toString(16);
        //crcCode = org.apache.commons.lang.StringUtils.leftPad(crcCode, 32, "00");
        String crcCode = Hex.encodeHexString(finalByte);
        return crcCode;
    }

    /**
     * 参与运算的两个值需等长
     * 
     * @param a
     * @param b
     * @return
     */
    private static byte[] xorByteArray(byte[] a, byte[] b) {
        byte[] finalByte = new byte[a.length];
        for (int i = 0; i < a.length; i++)
            finalByte[i] = (byte) (a[i] ^ b[i]);
        return finalByte;
    }
    
}

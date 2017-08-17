package com.gy.hsxt.access.web.common.controller;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.disk.DiskFileItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.validation.BindException;
import org.springframework.validation.Validator;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import com.gy.hsi.fs.common.exception.FsException;
import com.gy.hsi.redis.client.api.RedisUtil;
import com.gy.hsxt.access.web.common.bean.HttpRespEnvelope;
import com.gy.hsxt.access.web.common.constant.ASRespCode;
import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.access.web.common.utils.BeanUtils;
import com.gy.hsxt.access.web.common.utils.RequestUtil;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.constant.AsConstants;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.JsonUtil;
import com.gy.hsxt.common.utils.StringUtils;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-access-web-person
 * 
 *  Package Name    : com.gy.hsxt.access.web.controller
 * 
 *  File Name       : BaseController <T>.java
 * 
 *  Creation Date   : 2015-8-14 上午11:16:45  
 *  
 *  updateUse
 * 
 *  Author          : LiZhi Peter
 * 
 *  Purpose         : 实现了CRUD功能的Controller,如果有特殊处建议子类重写回调函数处理 <br/>
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/

public abstract class BaseController<T> {
    // @Resource
    private Validator validator;

    protected static final Logger logger = LoggerFactory.getLogger(BaseController.class);

    protected String className;

    protected String entityName;

    protected T entity;

    protected String listView = null;

    protected String formView = null;
    
    @Resource
    private RedisUtil<String> changeRedisUtil;

    public static final String successView = "/share/common/success.jsp";

    public BaseController() {
        className = BeanUtils.getSuperClassGenricType(getClass()).getName();
        entityName = StringUtils.substringAfterLast(className, ".");
        entityName = entityName.substring(0, 1).toLowerCase() + entityName.substring(1, entityName.length());
        listView = "list.jsp";
        formView = "form.jsp";
    }

    public static void main(String[] args) {
        String className = BeanUtils.getSuperClassGenricType(BaseController.class).getName();
        System.out.println(className);
        String entityName = StringUtils.substringAfterLast(className, ".");
        System.out.println(entityName);
        entityName = entityName.substring(0, 1).toLowerCase() + entityName.substring(1, entityName.length());
        System.out.println(entityName);
    }

    /**
     * 获取实体服务类的实例
     * 
     * @return
     */
    protected abstract IBaseService getEntityService();

    /**
     * 初始化操作数据Validator操作
     * 
     * @param binder
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
        binder.registerCustomEditor(Integer.class, new CustomNumberEditor(Integer.class, true));
        binder.registerCustomEditor(Double.class, new CustomNumberEditor(Double.class, true));
        binder.registerCustomEditor(MultipartFile.class, new ByteArrayMultipartFileEditor());
    }

    /**
     * 根据默认的请求参数进行分页查询。 过滤条件，如：filterMap.put("search_id", 12)。
     * 可以为null,条件必须“search_”开头<br>
     * 排序条件，如：sortMap.put("sort_id", "desc"); asc为正序，desc为倒序。
     * 可以为null,条件必须“sort_”开头<br>
     * 回调函数：beforeDoList(...), afterDoList(...)
     * 
     * @param request
     *            当前的HttpServletRequest
     * @return ScrollResult<T>
     */
    @ResponseBody
    @RequestMapping(value = "/list")
    public HttpRespEnvelope doList(HttpServletRequest request, HttpServletResponse response) {
        // 声明变量
        Page page = null; // 分页对象
        PageData<?> pd = null;
        Integer no = null; // 当前页码
        Integer size = null; // 每页显示条数
        String returnJson = null; // 返回json字符串
        String totalRows = null; // 总页数
        HttpRespEnvelope hre = null; // 返回界面数据封装对象

        // 提取客户端可能传送的过滤条件
        Map<String, Object> filterMap = WebUtils.getParametersStartingWith(request, "search_");

        // 如果支持排序则则添加相关代码
        Map<String, Object> sortMap = WebUtils.getParametersStartingWith(request, "sort_");

        // 获取客户端传递每页显示条数
        String pageSize = (String) request.getParameter("pageSize");

        // 获取客户端传递当前页码
        String curPage = (String) request.getParameter("curPage");

        // 设置当前页默认第一页
        if (StringUtils.isBlank(curPage))
        {
            no = AsConstants.PAGE_NO;
        }
        else
        {
            no = Integer.parseInt(curPage);
        }

        // 设置页显示行数
        if (StringUtils.isBlank(pageSize))
        {
            size = AsConstants.PAGE_SIZE;
        }
        else
        {
            size = Integer.parseInt(pageSize);
        }

        // 构造分页对象
        page = new Page(no, size);

        // 前置执行方法子类中有特殊场景可一直
        hre = beforeList(request, filterMap, sortMap);

        // 返回信息不为空则直接直接返回
        if (hre != null)
        {
            return hre;
        }

        try
        {
        	IBaseService service = (IBaseService) request.getAttribute("serviceName");
        	if(service == null){
        		pd = this.getEntityService().findScrollResult(filterMap, sortMap, page);
        	}else{
        		Class clazz = service.getClass();
                Method method = clazz.getDeclaredMethod((String) request.getAttribute("methodName"), Map.class, Map.class, Page.class);
                pd = (PageData<?>) method.invoke(service, filterMap, sortMap, page);
        	}
        }
        catch (Exception e)
        {
            return new HttpRespEnvelope(e);
        }

        // 执行后置方法
        hre = afterList(request, response);

        if (hre != null)
        {
            return hre;
        }

        // 封装返回界面json字符串
        hre = new HttpRespEnvelope();
        if(pd != null && pd.getCount() > 0)
        {
            hre.setData(pd.getResult());
            hre.setTotalRows(pd.getCount());
        }
        hre.setCurPage(page.getCurPage());
        return hre;

    }

    /**
     * 分页查询(list.do)回调函数，该方法在执行查询之前调用。可以继续添加过滤条件和排序条件。
     * 
     * @param request
     * @param filterMap
     * @param sortMap
     */
    protected HttpRespEnvelope beforeList(HttpServletRequest request, Map<String, Object> filterMap,
            Map<String, Object> sortMap) {
        return null;
    };

    /**
     * 分页查询(list.do)回调函数，该方法在返回视图之前调用。可以继续添加返回信息。
     * 
     * @param request
     * @param response
     * @param mav
     */
    protected HttpRespEnvelope afterList(HttpServletRequest request, HttpServletResponse response) {
        return null;
    };

    /**
     * 定向到新增实体的表单界面<br/>
     * 回调函数：onCreate(...)
     * 
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "create")
    public ModelAndView doCreate(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView mav = new ModelAndView(formView);
        onCreate(request, response, mav);
        return mav;
    }

    /**
     * 新增实体的表单界面(create.do)回调函数。该方法在返回视图之前调用，可以继续添加返回信息。
     * 
     * @param request
     * @param response
     * @param mav
     */
    protected void onCreate(HttpServletRequest request, HttpServletResponse response, ModelAndView mav) {
    };

    /**
     * 从Request中绑定对象并进行校验.
     */
    protected BindException bindRequestEntity(HttpServletRequest request, T entity) throws Exception {
        ServletRequestDataBinder binder = new ServletRequestDataBinder(entity);
        initBinder(binder);
        binder.bind(request);
        BindException errors = new BindException(binder.getBindingResult());
        validator.validate(entity, errors);
        return errors;
    }

    /**
     * 保存实体(save.do)回调函数，在执行实体与Request参数绑定之前调用用。
     * 注意：由于entity可能是托管对象，对entity所做的修改都将反映到数据库。 所以有必要在此方法中进行前期的数据校验，以免发生意外。
     * 
     * @param request
     * @param entity
     * @param mav
     * @return 是否通过校验
     */
    protected String beforeBindRequestEntity(HttpServletRequest request, T entity) {
        return "";
    };

    /**
     * 保存实体(save.do)回调函数，在执行保存之前调用用。可以进行数据校验。
     * 
     * @param request
     *            HttpServletRequest
     * @param entity
     *            实体对象
     * @param errors
     *            BindException 可以添加错误信息
     * @param mav
     *            ModelAndView
     */
    protected void beforeSave(HttpServletRequest request, T entity, BindException errors) {
    };

    /**
     * 保存实体(save.do)回调函数，在返回视图之前调用用。可以继续添加返回信息。
     * 
     * @param request
     * @param response
     * @param mav
     */
    protected void afterSave(HttpServletRequest request, HttpServletResponse response, T entity) {
    };

    /**
     * 保存实体<br/>
     * 回调函数：beforeDoSave(...), afterDoSave(...)
     * 
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/save")
    public String doSave(HttpServletRequest request, HttpServletResponse response) throws Exception {

        String id = request.getParameter("id");

        // 获取客户端传递当前页码
        String callback = (String) request.getParameter("callback");

        if (StringUtils.isBlank(id))
        {

            entity = (T) BeanUtils.getSuperClassGenricType(getClass()).newInstance();
        }
        else
        {
            entity = (T) getEntityService().findById(Integer.valueOf(id));
        }

        String checkJson = beforeBindRequestEntity(request, entity);

        if (StringUtils.isNotBlank(checkJson))
        {
            return JsonUtil.createReturnJsonString(checkJson, "CW22109");
        }

        beforeBindRequestEntity(request, entity);

        BindException errors = bindRequestEntity(request, entity);

        beforeSave(request, entity, errors);

        if (errors.hasErrors())
        {
            return JsonUtil.createReturnJsonString(checkJson, "CW22109");
        }

        getEntityService().save(JsonUtil.createJsonString(entity));

        afterSave(request, response, entity);

        return JsonUtil.createReturnJsonString(checkJson, "CW22109");
    }

    /**
     * 验证安全令牌
     * 
     * @param custId
     *            当前操作用户id
     * @param token
     *            安全令牌
     * @return 成功与否
     * @throws FsException
     */
    protected HttpRespEnvelope checkSecureToken(HttpServletRequest request) {

        // 变量声明
        HttpRespEnvelope hre = null;
        String custId = request.getParameter("custId");// 当前操作用户id
        String token = request.getParameter("token"); // 安全令牌
        
        //再次尝试获取custId、token信息
        if(StringUtils.isBlank(custId)){
        	custId = request.getParameter("search_custId");// 当前操作用户id
        }
        if(StringUtils.isBlank(token)){
        	token = request.getParameter("search_token");// 安全令牌
        }
        // 数据非空验证
         RequestUtil.verifyParamsIsNotEmpty(
                new Object[] { custId,RespCode.AS_CUSTID_INVALID.getCode(),RespCode.AS_CUSTID_INVALID.getDesc()}, // 用户id非空验证
                new Object[] { token, RespCode.AS_TOKEN_INVALID.getCode(),RespCode.AS_TOKEN_INVALID.getDesc() }   // 安全令牌非空验证
                );

        // 非空验证有提示信息则返回
        if (hre != null)
        {
            return hre;
        }

        // 正常返回null
        return hre;
    }
    
    
    
    /**
     * 
     * 方法名称：验证安全令牌
     * 方法描述：每个需要登陆后操作的方法均需要先调用此方法验证。
     * @param request HttpServletRequest对象
     * @throws HsException
     */
    protected void verifySecureToken(HttpServletRequest request) throws HsException {
        //接收参数（优先获取custId，token，如果未获取到再次尝试获取search_custId、search_token信息）
        String custId = StringUtils.isBlank(request.getParameter("custId"))?request.getParameter("search_custId"):request.getParameter("custId");//获取当前操作用户id
        String token = StringUtils.isBlank(request.getParameter("token"))?request.getParameter("search_token"):request.getParameter("token");//获取安全令牌
        
        //数据非空验证
        /*RequestUtil.verifyParamsIsNotEmpty(
        	new Object[] {custId, RespCode.AS_SECURE_TOKEN_INVALID.getCode(), "用户id为空."},//用户id非空验证
        	new Object[] {token, RespCode.AS_SECURE_TOKEN_INVALID.getCode(), "安全令牌为空."}//安全令牌非空验证
        );*/
        
        //调用用户中心提供的dubbo接口, 进行安全令牌验证，不通过则抛出异常信息
        /*if (!userCenterService.verifySecureToken(custId, token)){
        	throw new HsException(RespCode.AS_SECURE_TOKEN_INVALID.getCode(), "Invalid Token");
        }*/
    }
    
    /**
     * 
     * 方法名称：验证并获取互生卡号
     * 方法描述：互生卡号非空验证，优先获取pointNo，如果未获取到再次尝试获取search_pointNo信息
     * @param request HttpServletRequest对象
     * @return 互生卡号
     * @throws HsException
     */
    protected String verifyPointNo(HttpServletRequest request) throws HsException {
    	if(!StringUtils.isBlank(request.getParameter("pointNo"))){
    		return request.getParameter("pointNo");
    	}
    	if(!StringUtils.isBlank(request.getParameter("search_pointNo"))){
    		return request.getParameter("search_pointNo");
    	}
    	throw new HsException(RespCode.SW_POINTNO_INVALID.getCode(), "互生卡号不能为空.");
    }
    
    /**
     * 
     * 方法名称：校验验证码
     * 方法描述：校验验证码
     * @param request HttpServletRequest对象
     * @return 互生卡号
     * @throws HsException
     */
    public void verifyCodes(HttpServletRequest request) {
    	String custId = request.getParameter("custId");//客户ID
    	String smsCode = request.getParameter("smsCode");//验证码
    	String codesType = request.getParameter("codesType");//验证码类别
        String key = this.changeRedisUtil.get(custId+"_"+codesType, String.class);//获取验证码
        //判断为空
        if(StringUtils.isEmpty(smsCode)){
        	throw new HsException(ASRespCode.VERIFICATION_CODE_INVALID);
        }
        //判断过期
        if(StringUtils.isEmpty(key)){
        	throw new HsException(ASRespCode.AS_VERIFICATION_PASSED_INVALID);
        }
        //判断相等
        if(!smsCode.toUpperCase().equals(key.toUpperCase())){
        	throw new HsException(ASRespCode.VERIFICATION_CODE_ERROR);
        }
    }
    
    /**
     * 验证码校验 
     * 
     * @param custId
     *            当前操作用户id
     * @param token
     *            安全令牌
     * @return 成功与否
     * @throws FsException
     */
    
    @ResponseBody
    @RequestMapping(value = "/checkVerifiedCode")
    public HttpRespEnvelope checkVerifiedCode(HttpServletRequest request) {
        
        // 变量声明
        HttpRespEnvelope hre = null;
        hre =  checkSecureToken(request);
        // 非空验证有提示信息则返回
        if (hre != null)
        {
            return hre;
        }
        
        getEntityService().checkVerifiedCode(request.getParameter("custId"),request.getParameter("verifiedCode"));
       
        
        // 正常返回null
        return hre;
    }

    /**
     * 这里这里用的是MultipartFile[] myfiles参数,所以前台就要用<input type="file"
     * name="myfiles"/>
     * 上传文件完毕后返回给前台[0`filepath],0表示上传成功(后跟上传后的文件路径),1表示失败(后跟失败描述)
     */
    /**
     * 文件上传参数
     * 
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @ResponseBody
    @RequestMapping(value = { "/fileUpload" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope fileUpload(HttpServletRequest request, HttpServletResponse response) throws IOException {

        HttpRespEnvelope hre = null;
        String originalFilename = null;
        HashMap<String, String> map = new HashMap<>();
        CommonsMultipartFile cmf = null;
        DiskFileItem dfi = null;
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;

        Iterator<String> iter = multipartRequest.getFileNames();

        while (iter.hasNext())
        {

            // 取得上传文件
            MultipartFile file = multipartRequest.getFile(iter.next());

            if (file != null)
            {
                // 取得当前上传文件的文件名称
                String myFileName = file.getOriginalFilename();
                // 如果名称不为“”,说明该文件存在，否则说明该文件不存在
                if (myFileName.trim() != "")
                {

                    originalFilename = System.currentTimeMillis() + myFileName.substring(myFileName.lastIndexOf("."));
                    String fileRealPath = "C:\\img\\img_store" + originalFilename;
                    File localFile = new File(fileRealPath);
                    file.transferTo(localFile);

             
                    
                    // 转换成DiskFileItem获取文件FieldName
                    cmf = (CommonsMultipartFile) file;
                    dfi = (DiskFileItem) cmf.getFileItem();

                    // 保存文件返回信息
                    map.put(dfi.getFieldName(), originalFilename);
                    // 初始化
                    cmf = null;
                    dfi = null;
                }
            }
        }

        if (hre == null)
        {
            hre = new HttpRespEnvelope(map);
        }

        return hre;
    }

    

}

package com.gy.hsxt.gpf.gcs.controller;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gy.hsxt.gpf.gcs.bean.SysUser;
import com.gy.hsxt.gpf.gcs.common.MD5Util;
import com.gy.hsxt.gpf.gcs.interfaces.IUserService;

@Controller
public class UserController
{
	private final Logger	LOG	= LoggerFactory.getLogger(getClass());
	
	@Resource(name = "userService")
	private IUserService	userService;
	
	/**
	 * 管理页面初始化
	 */
	@RequestMapping(value = "/user/manage")
	public void manage()
	{
		
	}
	
	/**
	 * 获取数据列表
	 * 
	 * @return 返回JSON,数据为空返回空List<SysUser>，异常返回null
	 */
	@RequestMapping(value = "/user/managePost")
	@ResponseBody
	public List<SysUser> managePost()
	{
		try
		{
			List<SysUser> list = userService.getUserNameList();
			return list;
		}
		catch (Exception e)
		{
			LOG.error("获取用户列表异常：", e);
		}
		return null;
	}
	
	/**
	 * 更新某条记录
	 * 
	 * @param user实体类
	 *            必须，非null
	 * @return 返回String类型 1成功，其他失败
	 */
	@RequestMapping(value = "/user/Upadte")
	@ResponseBody
	public String upadte(SysUser user)
	{
		try
		{
			user.setUserPwd(MD5Util.Md5_16bit(user.getUserPwd()));
			int y = userService.update(user);
			return y + "";
		}
		catch (Exception e)
		{
			LOG.error("修改某个用户异常：", e);
		}
		return 0 + "";
	}
	
	/**
	 * 插入记录
	 * 
	 * @param user
	 *            实体类 必须，非null
	 * @return 返String类型 1成功，其他失败
	 */
	@RequestMapping(value = "/user/add")
	@ResponseBody
	public String add(SysUser user)
	{
		try
		{
			user.setUserPwd(MD5Util.Md5_16bit(user.getUserPwd()));
			int y = userService.insert(user);
			return y + "";
		}
		catch (Exception e)
		{
			LOG.error("插入某个用户异常：", e);
		}
		return 0 + "";
	}
	
	/**
	 * 删除某条记录
	 * 
	 * @param userName
	 *            用户ID 必须，非null
	 * @return 返String类型 1成功，其他失败
	 */
	@RequestMapping(value = "/user/del")
	@ResponseBody
	public String del(String userName)
	{
		try
		{
			int y = userService.delete(userName);
			return y + "";
		}
		catch (Exception e)
		{
			LOG.error("删除某个用户异常：", e);
		}
		return 0 + "";
	}
}

package com.gy.hsxt.lcs.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gy.hsxt.lcs.bean.SysUser;
import com.gy.hsxt.lcs.interfaces.IUserService;
import com.gy.hsxt.lcs.mapper.UserMapper;

/***************************************************************************
 * <PRE>
 *  Project Name    : com.gy.hsxt.lcs
 * 
 *  Package Name    : com.gy.hsxt.lcs.service
 * 
 *  File Name       : UserService.java
 * 
 *  Creation Date   : 2015-7-05
 * 
 *  Author          : liuhq
 * 
 *  Purpose         : 系统管理员UserService实现类
 * 
 * 
 *  History         : TODO
 * 
 * </PRE>
 ***************************************************************************/
@Service("userService")
public class UserService implements IUserService {

    @Resource(name = "userMapper")
    private UserMapper userMapper;

    /**
     * 插入记录
     * 
     * @param user
     *            实体类 必须，非null
     * @return 返int类型 1成功，其他失败
     */
    @Transactional
    public int insert(SysUser user) {
        return userMapper.insert(user);
    }

    /**
     * 读取某个用户的信息
     * 
     * @param user
     *            实体类 必须，非null
     * @return 返回实体类SysUser
     */
    public SysUser getUserName(SysUser user) {
        return userMapper.getUserName(user);
    }

    /**
     * 获取用户列表
     * 
     * @return 返回List<SysUser>，数据为空，返回空List<SysUser>
     */
    public List<SysUser> getUserNameList() {
        return userMapper.getUserNameList();
    }

    /**
     * 更新某条记录
     * 
     * @param user
     *            实体类 必须，非null
     * @return 返int类型 1成功，其他失败
     */
    @Transactional
    public int update(SysUser user) {
        return userMapper.update(user);
    }

    /**
     * 删除某条记录
     * 
     * @param userName
     *            用户id 必须，非null
     * @return 返int类型 1成功，其他失败
     */
    @Transactional
    public int delete(String userName) {
        return userMapper.delete(userName);
    }

}

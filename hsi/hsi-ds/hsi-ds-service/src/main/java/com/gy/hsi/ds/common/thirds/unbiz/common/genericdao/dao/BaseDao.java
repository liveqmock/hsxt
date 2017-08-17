/**
 * adx-common#com.gy.hsi.ds.param.ub.generic.dao.BaseDao.java
 * 下午6:50:08 created by Darwin(Tianxin)
 */
package com.gy.hsi.ds.common.thirds.unbiz.common.genericdao.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import com.github.knightliao.apollo.db.bo.BaseObject;
import com.gy.hsi.ds.common.thirds.unbiz.common.genericdao.bo.InsertOption;
import com.gy.hsi.ds.common.thirds.unbiz.common.genericdao.operator.Match;
import com.gy.hsi.ds.common.thirds.unbiz.common.genericdao.operator.Modify;

/**
 * DAO的定义
 *
 * @author Darwin(Tianxin)
 */
public interface BaseDao<KEY extends Serializable, ENTITY extends BaseObject<KEY>> {

    /**
     * 根据主键获取对象
     *
     * @param id
     *
     * @return
     */
    ENTITY get(KEY id);

    /**
     * 根据主键集合获取结果列表
     *
     * @param keys
     *
     * @return
     */
    List<ENTITY> get(Collection<KEY> keys);

    /**
     * 保存对象
     *
     * @param entity
     *
     * @return
     */
    ENTITY create(ENTITY entity);

    /**
     * 保存对象
     *
     * @param entity
     *
     * @return
     */
    ENTITY create(ENTITY entity, InsertOption option);

    /**
     * 保存多个对象
     *
     * @param entities
     *
     * @return
     */
    int create(List<ENTITY> entities);

    /**
     * 保存多个对象并返回各对象影响行数的
     *
     * @param entities
     *
     * @return
     */
    int[] insert(List<ENTITY> entities);

    /**
     * 保存多个对象
     *
     * @param entities
     *
     * @return
     */
    int create(List<ENTITY> entities, InsertOption option);

    /**
     * 保存多个对象并返回各对象影响行数的
     *
     * @param entities
     * @param option   ## @see InsertOption
     *
     * @return
     */
    int[] insert(List<ENTITY> entities, InsertOption option);

    /**
     * 修改一个对象
     *
     * @param entity
     *
     * @return
     */
    boolean update(ENTITY entity);

    /**
     * 修改一个对象列表
     *
     * @param entities
     *
     * @return
     */
    int update(List<ENTITY> entities);
    
    public int update(Modify modify, Match... matches);
    
    public int update(List<Modify> modifies, Match... matches);

    /**
     * 获取全部对象
     *
     * @return 下午4:30:53 created by Darwin(Tianxin)
     */
    List<ENTITY> findAll();

    /**
     * 删除一个对象
     *
     * @param id
     *
     * @return
     */
    boolean delete(ENTITY entity);

    /**
     * 根据条件删除多个对象
     *
     * @param ids
     *
     * @return
     */
    int delete(Collection<ENTITY> entities);

    /**
     * 删除一个对象
     *
     * @param id
     *
     * @return
     */
    boolean delete(KEY id);

    /**
     * 根据条件删除多个对象
     *
     * @param ids
     *
     * @return
     */
    int delete(List<KEY> ids);
}

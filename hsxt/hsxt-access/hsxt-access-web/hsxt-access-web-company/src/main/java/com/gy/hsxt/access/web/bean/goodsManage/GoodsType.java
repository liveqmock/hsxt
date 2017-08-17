/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.access.web.bean.goodsManage;

/**
 * 商品分类
 * 
 * @Package: com.gy.hsxt.access.web.bean.goodsManage  
 * @ClassName: GoodsType 
 * @Description: TODO
 *
 * @author: zhangcy 
 * @date: 2015-10-13 上午10:17:36 
 * @version V3.0.0
 */
public class GoodsType {
    /** 类型id **/
    private String id;
    /** 所属父类型 **/
    private String pId;
    /** 类型名称 **/
    private String name;
    /** 当节点为非叶节点时  是否展开 **/
    private boolean open = true;
    
    public GoodsType(){}
    
    public GoodsType(String id,String pId,String name){
        this.id = id;
        this.pId = pId;
        this.name = name;
    }
    
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
 
    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public boolean isOpen() {
        return open;
    }
    public void setOpen(boolean open) {
        this.open = open;
    }
    
    
}

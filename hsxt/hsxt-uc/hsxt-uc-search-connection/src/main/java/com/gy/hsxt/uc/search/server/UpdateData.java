package com.gy.hsxt.uc.search.server;

import java.util.ArrayList;
import java.util.List;

/**
 * 更新数据
 * 
 * @Package: com.gy.hsxt.uc.search.server  
 * @ClassName: UpdateData 
 * @Description: TODO
 *
 * @author: lixuan 
 * @date: 2015-11-19 上午11:42:21 
 * @version V1.0
 */
public class UpdateData {
	List<Data> datas = new ArrayList<Data>();
	Object idValue;
	
	/**
	 * 创建对象
	 * @param idName ID名称
	 * @param idValue 存入缓存的值
	 */
	public UpdateData (Object idValue){
	
		this.idValue = idValue;
	}

	/**
	 * 设置的值
	 * @return
	 */
	public Object getIdValue() {
		return idValue;
	}

	/**
	 * 添加数据对象
	 * @param data
	 */
	public void addData(Data data){
		datas.add(data);
	}
	
	/**
	 * 获取数据
	 * @return
	 */
	public List<Data> getDatas() {
		return datas;
	}

	/**
	 * 设置数据
	 * @param datas
	 */
	public void setDatas(List<Data> datas) {
		this.datas = datas;
	}
}

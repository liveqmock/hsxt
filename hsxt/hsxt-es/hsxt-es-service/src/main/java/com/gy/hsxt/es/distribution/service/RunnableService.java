package com.gy.hsxt.es.distribution.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsxt.es.distribution.bean.Alloc;
import com.gy.hsxt.es.distribution.bean.PointPage;
import com.gy.hsxt.es.distribution.mapper.PointAllocMapper;
import com.gy.hsxt.es.points.bean.PointDetail;

@Service
public class RunnableService {

	@Autowired
	private PointAllocMapper pointAllocMapper;
	
	/**
	 * 查询积分总数
	 * @return
	 */
	public int queryPointSum(){
		 return pointAllocMapper.queryPointSum();
	}

	/**
	 * 分页查询积分
	 * @param pp
	 * @return
	 */
	public List<PointDetail> queryPoint(PointPage pp) {
		return this.pointAllocMapper.queryPoint(pp);
	}

	/**
	 * 积分分配后入库分配表
	 * @param list
	 */
	public void batPointAllocPoint(List<Alloc> list) {
		this.pointAllocMapper.insertDailyBatAlloc(list);
	}
	
	/**
	 * 互生币分配后入库分配表
	 * @param list
	 */
	public void batHsbAllocPoint(List<Alloc> list) {
		this.pointAllocMapper.insertAlloc(list);
	}

}

package com.gy.hsxt.bs.bean.bm;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 增值信息
 *
 * @Package :com.gy.hsxt.bs.bean.bm
 * @ClassName : Increment
 * @Description : 增值信息
 * @Author : chenm
 * @Date : 2015/11/10 12:29
 * @Version V3.0.0.0
 */
public class Increment implements Serializable{

	private static final long serialVersionUID = 7793519598457453803L;

	/**子节点 托管企业、成员企业*/
	private List<String> childList;
	
	/**增值节点信息*/
	private Map<String,IncVo> incrementMap;

	/**
	 * 是否为上下级关系
	 */
	private Boolean subRelation;
	
	public Increment(){

	}

	public Increment(List<String> childList, Map<String, IncVo> incrementMap){
		this.childList = childList;
		this.incrementMap = incrementMap;
	}
	
	/**
	 * 获取子节点托管企业、成员企业
	 * @return childList 子节点托管企业、成员企业
	 */
	public List<String> getChildList() {
		return childList;
	}

	/**
	 * 设置子节点托管企业、成员企业
	 * @param childList 子节点托管企业、成员企业
	 */
	public void setChildList(List<String> childList) {
		this.childList = childList;
	}

	/**
	 * 获取增值节点信息
	 * @return incrementMap 增值节点信息
	 */
	public Map<String, IncVo> getIncrementMap() {
		return incrementMap;
	}

	/**
	 * 设置增值节点信息
	 * @param incrementMap 增值节点信息
	 */
	public void setIncrementMap(Map<String, IncVo> incrementMap) {
		this.incrementMap = incrementMap;
	}


	public Boolean getSubRelation() {
		return subRelation;
	}

	public void setSubRelation(Boolean subRelation) {
		this.subRelation = subRelation;
	}

	/**
	 * 增值节点传值对象
	 * @Package :com.gy.hsxt.bs.bm.bean
	 * @ClassName : IncVo
	 * @Description : 增值节点传值对象
	 * @Author : chenm
	 * @Date : 2015/11/10 12:29
	 * @Version V3.0.0.0
	 */
	public static class IncVo implements Serializable{
		
		/**
		 * 增值点 == 子节点数
		 * 积点数 == 1000:500 的剩余值
		 */
		private static final long serialVersionUID = 1L;

		/**资源号*/
		private String resNo;

		/**
		 *企业客户号
		 */
		private String custId;

		/**左增值节点的子节点数量（不包含自身）*/
		private Integer lCount;
		
		/**右增值节点的子节点数量（不包含自身）*/
		private Integer rCount;
		
		/**左增值点*/
		private Integer lP;
		
		/**右增值点*/
		private Integer rP;

		public IncVo() {
		}

		public IncVo(String resNo, String custId, Integer lP, Integer rP, Integer lCount, Integer rCount){
			this.resNo = resNo;
			this.custId = custId;
			this.lP = lP;
			this.rP = rP;
			this.lCount = lCount;
			this.rCount = rCount;
		}

		public String getResNo() {
			return resNo;
		}

		public void setResNo(String resNo) {
			this.resNo = resNo;
		}

		public String getCustId() {
			return custId;
		}

		public void setCustId(String custId) {
			this.custId = custId;
		}

		/**
		 * 获取左增值节点的子节点数量（不包含自身）
		 * @return lCount 左增值节点的子节点数量（不包含自身）
		 */
		public Integer getLCount() {
			return lCount;
		}

		/**
		 * 设置左增值节点的子节点数量（不包含自身）
		 * @param lCount 左增值节点的子节点数量（不包含自身）
		 */
		public void setLCount(Integer lCount) {
			this.lCount = lCount;
		}

		/**
		 * 获取右增值节点的子节点数量（不包含自身）
		 * @return rCount 右增值节点的子节点数量（不包含自身）
		 */
		public Integer getRCount() {
			return rCount;
		}

		/**
		 * 设置右增值节点的子节点数量（不包含自身）
		 * @param rCount 右增值节点的子节点数量（不包含自身）
		 */
		public void setRCount(Integer rCount) {
			this.rCount = rCount;
		}

		/**
		 * 获取左增值点
		 * @return lP 左增值点
		 */
		public Integer getLP() {
			return lP;
		}

		/**
		 * 设置左增值点
		 * @param lP 左增值点
		 */
		public void setLP(Integer lP) {
			this.lP = lP;
		}

		/**
		 * 获取右增值点
		 * @return rP 右增值点
		 */
		public Integer getRP() {
			return rP;
		}

		/**
		 * 设置右增值点
		 * @param rP 右增值点
		 */
		public void setRP(Integer rP) {
			this.rP = rP;
		}

		@Override
		public String toString() {
			return JSON.toJSONString(this);
		}
	}

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}
}

	
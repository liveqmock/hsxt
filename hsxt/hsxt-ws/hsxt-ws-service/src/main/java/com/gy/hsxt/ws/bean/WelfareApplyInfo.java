package com.gy.hsxt.ws.bean;

import static com.gy.hsxt.common.utils.StringUtils.isBlank;
import static com.gy.hsxt.common.utils.StringUtils.isNotBlank;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;

import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.DateUtil;
import com.gy.hsxt.ws.common.WsErrorCode;
import com.gy.hsxt.ws.common.WsTools;

/**
 * 福利申请记录 实体
 * 
 * @Package: com.gy.hsxt.ws.bean
 * @ClassName: WelfareApplyInfo
 * @Description: TODO
 * 
 * @author: huanggy
 * @date: 2015-12-24 下午4:54:50
 * @version V1.0
 */
public class WelfareApplyInfo implements Serializable {
	/** 主键 积分福利申请流水号 */
	private String applyWelfareNo;
	
	/** 保障单号 **/
	private String welfareId;

	/** 互生号(申请人) */
	private String hsResNo;

	/** 客户号(申请人) */
	private String custId;

	/** 受益人客户号 */
	private String favoreeCustId;

	/** 身故人互生号 */
	private String deathResNo;

	/** 福利类型 0 意外伤害 1 免费医疗 2 他人身故 */
	private Integer welfareType;

	/** 身故人姓名 */
	private String diePeopleName;

	/** 申请人姓名 */
	private String proposerName;

	/** 申请人电话 */
	private String proposerPhone;

	/** 申请人证件号码 */
	private String proposerPapersNo;

	/** 医保卡号 */
	private String healthCardNo;

	/** 诊疗开始日期 */
	private Timestamp startDate;

	/** 诊疗结束日期 */
	private Timestamp endDate;

	/** 所在城市/地址 */
	private String city;

	/** 所在医院 */
	private String hospital;

	/** 申请时间 */
	private Timestamp applyDate;

	/** 医保支付金额 */
	private BigDecimal healthPayAmount;

	/** 个人支付金额 */
	private BigDecimal personalPayAmount;

	/** 互生支付金额(审批,批复金额) */
	private BigDecimal hsPayAmount;

	/** 申请状态 0 受理中 1 已受理 2 驳回 */
	private Integer approvalStatus;

	/** 说明 */
	private String explain;

	/** 标记此条记录的状态 */
	private String isactive;

	/** 创建时间，取记录创建时的系统时间 */
	private Timestamp createdDate;

	/** 由谁创建，值为用户的伪键ID */
	private String createdBy;

	/** 更新时间，取记录更新时的系统时间 */
	private Timestamp updatedDate;

	/** 由谁更新，值为用户的伪键ID */
	private String updatedBy;

	/** 互生卡正面图片路径 关联T_WS_IMG表 */
	private String hscPositivePathId;

	/** 互生卡背面图片路径 关联T_WS_IMG表 */
	private String hscReversePathId;

	/** 身份证正面图片路径 关联T_WS_IMG表 */
	private String cerPositivePathId;

	/** 身份证背面图片路径 关联T_WS_IMG表 */
	private String cerReversePathId;

	/** 本人社保卡复印件图片路径 关联T_WS_IMG表 */
	private String sscPathId;

	/** 原始收费收据复印件图片路径 关联T_WS_IMG表 */
	private String ofrPathId;

	/** 费用明细清单复印件图片路径 关联T_WS_IMG表 */
	private String cdlPathId;

	/** 门诊病历复印件图片路径 关联T_WS_IMG表 */
	private String omrPathId;

	/** 住院病历复印件图片路径 关联T_WS_IMG表 */
	private String imrPathId;

	/** 疾病诊断证明书复印件图片路径 关联T_WS_IMG表 */
	private String ddcPathId;

	/** 申请保障代理人身份证明图片路径 关联T_WS_IMG表 */
	private String aipPathId;

	/** 医疗证明图片路径 关联T_WS_IMG表 */
	private String medicalProvePathId;

	/** 死亡证明图片路径 关联T_WS_IMG表 */
	private String deathProvePathId;

	/** 代理人法定身份证明图片路径 关联T_WS_IMG表 */
	private String agentAccreditPathId;

	/** 直系亲属证明书图片路径 关联T_WS_IMG表 */
	private String ifpPathId;

	/** 户籍注销证明图片路径 关联T_WS_IMG表 */
	private String hrcPathId;

	/** 其他证明图片路径 关联T_WS_IMG表 */
	private String otherProvePathId;

	/** 医保中心的受理回执复印件 */
	private String medicalAcceptPathId;

	/** 医疗费用报销计算表复印件 */
	private String costCountPathId;

	/** 被保障人(身故人)法定身份证明 */
	private String diePeopleCerPathId;

	/** 所有的图片记录 */
	public List<ImgInfo> imgs = new ArrayList<>();

	/** 所有的图片记录 */
	public Map<String, String> imgMaps = new HashMap<String, String>();

	private static final long serialVersionUID = 1L;

	public String getApplyWelfareNo() {
		return applyWelfareNo;
	}

	public void setApplyWelfareNo(String applyWelfareNo) {
		this.applyWelfareNo = applyWelfareNo == null ? null : applyWelfareNo.trim();
	}

	public String getHsResNo() {
		return hsResNo;
	}

	public void setHsResNo(String hsResNo) {
		this.hsResNo = hsResNo == null ? null : hsResNo.trim();
	}

	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId == null ? null : custId.trim();
	}

	public String getFavoreeCustId() {
		return favoreeCustId;
	}

	public void setFavoreeCustId(String favoreeCustId) {
		this.favoreeCustId = favoreeCustId == null ? null : favoreeCustId.trim();
	}

	public String getDeathResNo() {
		return deathResNo;
	}

	public void setDeathResNo(String deathResNo) {
		this.deathResNo = deathResNo == null ? null : deathResNo.trim();
	}

	public Integer getWelfareType() {
		return welfareType;
	}

	public void setWelfareType(Integer welfareType) {
		this.welfareType = welfareType;
	}

	public String getDiePeopleName() {
		return diePeopleName;
	}

	public void setDiePeopleName(String diePeopleName) {
		this.diePeopleName = diePeopleName == null ? null : diePeopleName.trim();
	}

	public String getProposerName() {
		return proposerName;
	}

	public void setProposerName(String proposerName) {
		this.proposerName = proposerName == null ? null : proposerName.trim();
	}

	public String getProposerPhone() {
		return proposerPhone;
	}

	public void setProposerPhone(String proposerPhone) {
		this.proposerPhone = proposerPhone == null ? null : proposerPhone.trim();
	}

	public String getProposerPapersNo() {
		return proposerPapersNo;
	}

	public void setProposerPapersNo(String proposerPapersNo) {
		this.proposerPapersNo = proposerPapersNo == null ? null : proposerPapersNo.trim();
	}

	public String getHealthCardNo() {
		return healthCardNo;
	}

	public void setHealthCardNo(String healthCardNo) {
		this.healthCardNo = healthCardNo == null ? null : healthCardNo.trim();
	}

	public Timestamp getStartDate() {
		return startDate;
	}

	public void setStartDate(Timestamp startDate) {
		this.startDate = startDate;
	}

	public Timestamp getEndDate() {
		return endDate;
	}

	public void setEndDate(Timestamp endDate) {
		this.endDate = endDate;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city == null ? null : city.trim();
	}

	public String getHospital() {
		return hospital;
	}

	public void setHospital(String hospital) {
		this.hospital = hospital == null ? null : hospital.trim();
	}

	public Timestamp getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(Timestamp applyDate) {
		this.applyDate = applyDate;
	}

	public BigDecimal getHealthPayAmount() {
		return healthPayAmount;
	}

	public void setHealthPayAmount(BigDecimal healthPayAmount) {
		this.healthPayAmount = healthPayAmount;
	}

	public BigDecimal getPersonalPayAmount() {
		return personalPayAmount;
	}

	public void setPersonalPayAmount(BigDecimal personalPayAmount) {
		this.personalPayAmount = personalPayAmount;
	}

	public BigDecimal getHsPayAmount() {
		return hsPayAmount;
	}

	public void setHsPayAmount(BigDecimal hsPayAmount) {
		this.hsPayAmount = hsPayAmount;
	}

	public Integer getApprovalStatus() {
		return approvalStatus;
	}

	public void setApprovalStatus(Integer approvalStatus) {
		this.approvalStatus = approvalStatus;
	}

	public String getExplain() {
		return explain;
	}

	public void setExplain(String explain) {
		this.explain = explain == null ? null : explain.trim();
	}

	public String getIsactive() {
		return isactive;
	}

	public void setIsactive(String isactive) {
		this.isactive = isactive == null ? null : isactive.trim();
	}

	public Timestamp getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy == null ? null : createdBy.trim();
	}

	public Timestamp getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Timestamp updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy == null ? null : updatedBy.trim();
	}

	public String getHscPositivePathId() {
		return hscPositivePathId;
	}

	public void setHscPositivePathId(String hscPositivePathId) {
		this.hscPositivePathId = hscPositivePathId == null ? null : hscPositivePathId.trim();
	}

	public String getHscReversePathId() {
		return hscReversePathId;
	}

	public void setHscReversePathId(String hscReversePathId) {
		this.hscReversePathId = hscReversePathId == null ? null : hscReversePathId.trim();
	}

	public String getCerPositivePathId() {
		return cerPositivePathId;
	}

	public void setCerPositivePathId(String cerPositivePathId) {
		this.cerPositivePathId = cerPositivePathId == null ? null : cerPositivePathId.trim();
	}

	public String getCerReversePathId() {
		return cerReversePathId;
	}

	public void setCerReversePathId(String cerReversePathId) {
		this.cerReversePathId = cerReversePathId == null ? null : cerReversePathId.trim();
	}

	public String getSscPathId() {
		return sscPathId;
	}

	public void setSscPathId(String sscPathId) {
		this.sscPathId = sscPathId == null ? null : sscPathId.trim();
	}

	public String getOfrPathId() {
		return ofrPathId;
	}

	public void setOfrPathId(String ofrPathId) {
		this.ofrPathId = ofrPathId == null ? null : ofrPathId.trim();
	}

	public String getCdlPathId() {
		return cdlPathId;
	}

	public void setCdlPathId(String cdlPathId) {
		this.cdlPathId = cdlPathId == null ? null : cdlPathId.trim();
	}

	public String getOmrPathId() {
		return omrPathId;
	}

	public void setOmrPathId(String omrPathId) {
		this.omrPathId = omrPathId == null ? null : omrPathId.trim();
	}

	public String getImrPathId() {
		return imrPathId;
	}

	public void setImrPathId(String imrPathId) {
		this.imrPathId = imrPathId == null ? null : imrPathId.trim();
	}

	public String getDdcPathId() {
		return ddcPathId;
	}

	public void setDdcPathId(String ddcPathId) {
		this.ddcPathId = ddcPathId == null ? null : ddcPathId.trim();
	}

	public String getAipPathId() {
		return aipPathId;
	}

	public void setAipPathId(String aipPathId) {
		this.aipPathId = aipPathId == null ? null : aipPathId.trim();
	}

	public String getMedicalProvePathId() {
		return medicalProvePathId;
	}

	public void setMedicalProvePathId(String medicalProvePathId) {
		this.medicalProvePathId = medicalProvePathId == null ? null : medicalProvePathId.trim();
	}

	public String getDeathProvePathId() {
		return deathProvePathId;
	}

	public void setDeathProvePathId(String deathProvePathId) {
		this.deathProvePathId = deathProvePathId == null ? null : deathProvePathId.trim();
	}

	public String getAgentAccreditPathId() {
		return agentAccreditPathId;
	}

	public void setAgentAccreditPathId(String agentAccreditPathId) {
		this.agentAccreditPathId = agentAccreditPathId == null ? null : agentAccreditPathId.trim();
	}

	public String getIfpPathId() {
		return ifpPathId;
	}

	public void setIfpPathId(String ifpPathId) {
		this.ifpPathId = ifpPathId == null ? null : ifpPathId.trim();
	}

	public String getHrcPathId() {
		return hrcPathId;
	}

	public void setHrcPathId(String hrcPathId) {
		this.hrcPathId = hrcPathId == null ? null : hrcPathId.trim();
	}

	public String getOtherProvePathId() {
		return otherProvePathId;
	}

	public void setOtherProvePathId(String otherProvePathId) {
		this.otherProvePathId = otherProvePathId == null ? null : otherProvePathId.trim();
	}

	public String getMedicalAcceptPathId() {
		return medicalAcceptPathId;
	}

	public void setMedicalAcceptPathId(String medicalAcceptPathId) {
		this.medicalAcceptPathId = medicalAcceptPathId == null ? null : medicalAcceptPathId.trim();
	}

	public String getCostCountPathId() {
		return costCountPathId;
	}

	public void setCostCountPathId(String costCountPathId) {
		this.costCountPathId = costCountPathId == null ? null : costCountPathId.trim();
	}

	public String getDiePeopleCerPathId() {
		return diePeopleCerPathId;
	}

	public void setDiePeopleCerPathId(String diePeopleCerPathId) {
		this.diePeopleCerPathId = diePeopleCerPathId == null ? null : diePeopleCerPathId.trim();
	}
	
	public String getWelfareId() {
		return welfareId;
	}

	public void setWelfareId(String welfareId) {
		this.welfareId = welfareId;
	}

	public static WelfareApplyInfo buildWelfareApplyRecord(Object apply) {
		WelfareApplyInfo record = new WelfareApplyInfo();
		BeanUtils.copyProperties(apply, record);
		try {
			fillImgId(record, apply);
		} catch (Exception e) {
			throw new HsException(WsErrorCode.DATA_COVERT_ERROR.getCode(),
					WsErrorCode.DATA_COVERT_ERROR.getDesc());
		}
		record.setApplyWelfareNo(WsTools.getGUID());
		return record;
	}

	private static void fillImgId(WelfareApplyInfo record, Object apply)
			throws IllegalArgumentException, IllegalAccessException {

		Map<String, List<String>> map = new HashMap<String, List<String>>();
		Field[] fields = apply.getClass().getDeclaredFields();
		Field.setAccessible(fields, true);
		for (Field fild : fields) {
			String fildname = fild.getName();
			if (!fildname.endsWith("Path")) {
				continue;
			}
			List<String> paths = (List<String>) fild.get(apply);
			if (paths != null) {
				map.put(fildname + "Id", paths);
			}
		}

		fields = WelfareApplyInfo.class.getDeclaredFields();
		Field.setAccessible(fields, true);
		for (Field fild : fields) {
			String fildname = fild.getName();
			if (!fildname.endsWith("PathId") || !map.containsKey(fildname)) {
				continue;
			}
			String imgId = WsTools.getGUID();
			fild.set(record, imgId);
			List<String> paths = map.get(fildname);
			for (String path : paths) {
				ImgInfo imgInfo = new ImgInfo();
				imgInfo.setImgId(imgId);
				imgInfo.setImgPath(path);
				record.imgs.add(imgInfo);
			}
		}

	}

	public List<String> getImgIds() {
		List<String> imgIds = new ArrayList<>();
		Field[] fields = WelfareApplyInfo.class.getDeclaredFields();
		Field.setAccessible(fields, true);
		for (Field fild : fields) {
			String fildname = fild.getName();
			if (!fildname.endsWith("PathId")) {
				continue;
			}
			String imgId = null;
			try {
				imgId = (String) fild.get(this);
			} catch (Exception e) {
				throw new HsException(WsErrorCode.DATA_COVERT_ERROR.getCode(),
						WsErrorCode.DATA_COVERT_ERROR.getDesc());
			}
			if (isNotBlank(imgId)) {
				imgIds.add(imgId);
				imgMaps.put(fildname, imgId);
			}
		}
		return imgIds;
	}

	public WelfareApplyDetail buildWelfareApplyDetail(List<ImgInfo> imgInfos) {
		WelfareApplyDetail applyDetail = new WelfareApplyDetail();
		BeanUtils.copyProperties(this, applyDetail);
		if (this.applyDate != null) {
			applyDetail.setApplyDate(DateUtil.DateTimeToString(applyDate));
		}
		if (this.startDate != null) {
			applyDetail.setStartDate(DateUtil.DateTimeToString(startDate));
		}
		if (this.endDate != null) {
			applyDetail.setEndDate(DateUtil.DateTimeToString(endDate));
		}
		if (this.hsPayAmount != null) {
			applyDetail.setReplyAmount(hsPayAmount.toString());
		}
		Map<String, List<String>> pathsMap = bulidToMap(imgInfos);
		Field[] fields = WelfareApplyDetail.class.getDeclaredFields();
		Field.setAccessible(fields, true);
		for (Field fild : fields) {
			String fildname = fild.getName() + "Id";
			if (!fildname.endsWith("PathId")) {
				continue;
			}
			String imgId = imgMaps.get(fildname);
			if (isBlank(imgId)) {
				continue;
			}
			List<String> paths = pathsMap.get(imgId);
			if (isBlank(paths)) {
				continue;
			}
			try {
				fild.set(applyDetail, paths);
			} catch (Exception e) {
				throw new HsException(WsErrorCode.DATA_COVERT_ERROR.getCode(),
						WsErrorCode.DATA_COVERT_ERROR.getDesc());
			}
		}
		return applyDetail;
	}

	private Map<String, List<String>> bulidToMap(List<ImgInfo> imgInfos) {
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		for (ImgInfo imgInfo : imgInfos) {
			List<String> paths = map.get(imgInfo.getImgId());
			if (paths == null) {
				paths = new ArrayList<String>();
			}
			paths.add(imgInfo.getImgPath());
			map.put(imgInfo.getImgId(), paths);
		}
		return map;
	}

}
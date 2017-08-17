package com.gy.hsi.ds.param.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.baidu.disconf.core.common.constants.DisConfigTypeEnum;
import com.github.knightliao.apollo.utils.data.GsonUtils;
import com.github.knightliao.apollo.utils.io.OsUtil;
import com.github.knightliao.apollo.utils.time.DateUtils;
import com.gy.hsi.ds.common.constant.DataFormatConst;
import com.gy.hsi.ds.common.thirds.dsp.common.form.RequestListBase.Page;
import com.gy.hsi.ds.common.thirds.dsp.common.utils.DataTransfer;
import com.gy.hsi.ds.common.thirds.dsp.common.utils.ServiceUtil;
import com.gy.hsi.ds.common.thirds.ub.common.db.DaoPageResult;
import com.gy.hsi.ds.common.utils.CodeUtils;
import com.gy.hsi.ds.common.utils.CodeUtils2;
import com.gy.hsi.ds.common.utils.DiffUtils;
import com.gy.hsi.ds.common.utils.StringUtils;
import com.gy.hsi.ds.param.beans.ZkDisconfData;
import com.gy.hsi.ds.param.beans.ZkDisconfData.ZkDisconfDataItem;
import com.gy.hsi.ds.param.beans.bo.App;
import com.gy.hsi.ds.param.beans.bo.Config;
import com.gy.hsi.ds.param.beans.bo.Env;
import com.gy.hsi.ds.param.beans.vo.ConfListVo;
import com.gy.hsi.ds.param.beans.vo.MachineListVo;
import com.gy.hsi.ds.param.controller.form.ConfListForm;
import com.gy.hsi.ds.param.controller.form.ConfNewItemForm;
import com.gy.hsi.ds.param.dao.IConfigDao;
import com.gy.hsi.ds.param.service.IAppMgr;
import com.gy.hsi.ds.param.service.IConfigMgr;
import com.gy.hsi.ds.param.service.IEnvMgr;
import com.gy.hsi.ds.param.service.IZkDeployMgr;
import com.gy.hsi.ds.param.service.IZooKeeperDriver;

/**
 * @author liaoqiqi
 * @version 2014-6-16
 */
@Service
public class ConfigMgrImpl implements IConfigMgr {

	protected static final Logger LOG = Logger
			.getLogger(ConfigMgrImpl.class);

	@Autowired
	private IConfigDao configDao;

	@Autowired
	private IAppMgr appMgr;

	@Autowired
	private IEnvMgr envMgr;

	@Autowired
	private IZooKeeperDriver zooKeeperDriver;

	@Autowired
	private IZkDeployMgr zkDeployMgr;

	/**
	 * 根据APPid获取其版本列表
	 */
	@Override
	public List<String> getVersionListByAppEnv(Long appId, Long envId) {

		List<String> versionList = new ArrayList<String>();

		List<Config> configs = configDao.getConfByAppEnv(appId, envId);

		for (Config config : configs) {

			if (!versionList.contains(config.getVersion())) {
				versionList.add(config.getVersion());
			}
		}

		return versionList;
	}

	/**
	 * 配置文件的整合
	 *
	 * @param confListForm
	 *
	 * @return
	 */
	public List<File> getDisonfFileList(ConfListForm confListForm) {

		List<Config> configList = null;

		if (null == confListForm) {
			configList = configDao.getConfigList(null, null, null);
		} else {
			configList = configDao.getConfigList(confListForm.getAppId(),
					confListForm.getEnvId(), confListForm.getVersion());
		}

		// 时间作为当前文件夹
		String curTime = "tmp" + File.separator + DateUtils.format(new Date(),
				DataFormatConst.MILLIS_TIME_FORMAT);
		
		OsUtil.makeDirs(curTime);

		List<File> files = new ArrayList<File>();
		
		for (Config config : configList) {
			if (config.getConfigType().equals(DisConfigTypeEnum.FILE.getType())) {
				File file = new File(curTime, config.getConfigName());
				
				try {
					FileUtils.writeByteArrayToFile(file, config
							.getConfigValue().getBytes("UTF-8"));
				} catch (IOException e) {
					LOG.warn(e.toString());
				}

				files.add(file);
			}
		}

		return files;
	}
	
	@Override
	public String getDisonfFileListByApp(Long _appId, Long _envId, String _version) {
		Map<Long, List<File>> map = new HashMap<Long, List<File>>();

		List<Config> configList = configDao.getConfigList(_appId, _envId, _version);

		// 时间作为当前文件夹
		String curTime = "./tmp"
				+ File.separator
				+ DateUtils.format(new Date(),
						DataFormatConst.MILLIS_TIME_FORMAT);

		OsUtil.makeDirs(curTime);

		List<App> appList = appMgr.getAppList();

		Map<Long, String> appNameMap = new HashMap<Long, String>(10);

		if (null != appList) {
			for (App app : appList) {
				appNameMap.put(app.getId(), app.getAppName());
			}
		}

		List<File> files = null;
		File file = null;

		Long appId = null;

		String dirFolder = "";
		String appName = "";
		String configValue = "";

		for (Config config : configList) {
			
			if(StringUtils.isEmpty(config.getConfigValue())) {
				System.out.println("空文件内如。。。。。。。。。。。。。。。。。。。。。。。。");
			}
			
			System.out.println(config.getConfigName()+"   " + StringUtils.avoidNull(config.getConfigValue()).length());
			
			if (DisConfigTypeEnum.FILE.getType() == config.getConfigType()) {
				appId = config.getAppId();

				appName = appNameMap.get(appId);

				if (StringUtils.isEmpty(appName)) {
					continue;
				}

				dirFolder = curTime + File.separator + appName.toLowerCase();

				// 创建目录
				OsUtil.makeDirs(dirFolder);

				file = new File(dirFolder, config.getConfigName());

				try {
					configValue = CodeUtils2.unicodeToUtf8(config.getConfigValue());
					
					FileUtils.writeByteArrayToFile(file, configValue.getBytes("UTF-8"));
				} catch (IOException e) {
					LOG.warn(e.toString());
				}

				files = map.get(appId);

				if (null == files) {
					files = new ArrayList<File>(5);

					map.put(appId, files);
				}

				files.add(file);
			}
		}

		return curTime;
	}
	
	@Override
	public String getAllDisonfFileList() {
		return getDisonfFileListByApp(null, null, null);
	}

	/**
	 * 配置列表
	 */
	@Override
	public DaoPageResult<ConfListVo> getConfigList(ConfListForm confListForm,
			boolean fetchZk, final boolean getErrorMessage) {

		//
		// 数据据结果
		//
		DaoPageResult<Config> configList = configDao.getConfigList(
				confListForm.getAppId(), confListForm.getEnvId(),
				confListForm.getVersion(), confListForm.getPage());

		//
		//
		//

		final App app = appMgr.getById(confListForm.getAppId());
		final Env env = envMgr.getById(confListForm.getEnvId());

		//
		//
		//
		final boolean myFetchZk = fetchZk;
		Map<String, ZkDisconfData> zkDataMap = new HashMap<String, ZkDisconfData>();
		if (myFetchZk) {
			zkDataMap = fetchZk(app, env.getEnvName(),
					confListForm.getVersion());
		}
		final Map<String, ZkDisconfData> myzkDataMap = zkDataMap;

		//
		// 进行转换
		//
		DaoPageResult<ConfListVo> configListVo = ServiceUtil.getResult(
				configList, new DataTransfer<Config, ConfListVo>() {

					@Override
					public ConfListVo transfer(Config input) {

						String appNameString = app.getAppName();
						String envName = env.getEnvName();

						ZkDisconfData zkDisconfData = null;
						if (myzkDataMap != null
								&& myzkDataMap.keySet().contains(
										input.getConfigName())) {
							zkDisconfData = myzkDataMap.get(input
									.getConfigName());
						}
						ConfListVo configListVo = convert(input, appNameString,
								envName, zkDisconfData);

						// 列表操作不要显示值, 为了前端显示快速(只是内存里操作)
						if (!myFetchZk || !getErrorMessage) {

							// 列表 value 设置为 ""
							configListVo.setValue("");
							configListVo
									.setMachineList(new ArrayList<ZkDisconfData.ZkDisconfDataItem>());
						}

						return configListVo;
					}
				});

		return configListVo;
	}
	
	/**
	 * 配置列表
	 */
	public List<Config> getConfigList(Long appId) {

		//
		// 数据据结果
		//
		Page page = new Page();
		
		DaoPageResult<Config> configList = configDao.getConfigList(
				(appId), null, null, page);

		List<Config> configResultList = configList.getResult();

		return configResultList;
	}

	/**
	 * 通用配置，获取所有app下面的zkDataMap，非通用配置，获取指定app下面的zkDataMap
	 * 
	 * @param app
	 *            App对象
	 * @param envName
	 *            环境名称
	 * @param version
	 *            版本号
	 * @return
	 */
	private Map<String, ZkDisconfData> fetchZk(App app, String envName,
			String version) {
		Map<String, ZkDisconfData> zkDataMap = new HashMap<String, ZkDisconfData>();

		if (null != app) {
			// Long appId = app.getId();
			//
			// if (ConfigConstant.COMMON_APP_ID.equals(appId)) {
			//
			// List<App> appList = appMgr.getAppList();
			//
			// if (appList != null && appList.size() > 0) {
			// App tmp = null;
			//
			// for (int i = 0; i < appList.size(); i++) {
			// tmp = appList.get(i);
			//
			// // 非通用配置项
			// if (!ConfigConstant.COMMON_APP_ID.equals(tmp.getId())) {
			// zkDataMap.putAll(zkDeployMgr.getZkDisconfDataMap(
			// tmp.getAppName(), envName, version));
			// }
			// }
			// }
			// } else {
			// zkDataMap = zkDeployMgr.getZkDisconfDataMap(app.getAppName(),
			// envName, version);
			// }
			
			zkDataMap = zkDeployMgr.getZkDisconfDataMap(app.getAppName(),
					envName, version);
		}

		return zkDataMap;
	}

	/**
     *
     */
	private MachineListVo getZkData(List<ZkDisconfDataItem> datalist,
			Config config) {

		int errorNum = 0;
		for (ZkDisconfDataItem zkDisconfDataItem : datalist) {

			if (config.getConfigType().equals(DisConfigTypeEnum.FILE.getType())) {

				List<String> errorKeyList = compareConfig(
						zkDisconfDataItem.getValue(), config.getConfigValue());

				if (errorKeyList.size() != 0) {
					zkDisconfDataItem.setErrorList(errorKeyList);
					errorNum++;
				}
			} else {

				//
				// 配置项
				//
				if (zkDisconfDataItem.getValue().trim()
						.equals(config.getConfigValue().trim())) {
				} else {
					List<String> errorKeyList = new ArrayList<String>();
					errorKeyList.add(config.getConfigValue().trim());
					zkDisconfDataItem.setErrorList(errorKeyList);
					errorNum++;
				}
			}
		}

		MachineListVo machineListVo = new MachineListVo();
		machineListVo.setDatalist(datalist);
		machineListVo.setErrorNum(errorNum);
		machineListVo.setMachineSize(datalist.size());

		return machineListVo;
	}

	/**
	 * 转换成配置返回
	 *
	 * @param config
	 *
	 * @return
	 */
	private ConfListVo convert(Config config, String appNameString,
			String envName, ZkDisconfData zkDisconfData) {

		ConfListVo confListVo = new ConfListVo();

		confListVo.setConfigId(config.getId());
		confListVo.setAppId(config.getAppId());
		confListVo.setAppName(appNameString);
		confListVo.setEnvName(envName);
		confListVo.setEnvId(config.getEnvId());
		confListVo.setCreateTime(DateUtils.format(config.getCreateDate(),
				DataFormatConst.DISPLAY_TIME_FORMAT));
		confListVo.setModifyTime(DateUtils.format(config.getUpdateDate(),
				DataFormatConst.DISPLAY_TIME_FORMAT));
		confListVo.setKey(config.getConfigName());
		// StringEscapeUtils.escapeHtml escape
		confListVo.setValue(CodeUtils.unicodeToUtf8(config.getConfigValue()));
		confListVo.setVersion(config.getVersion());
		confListVo.setType(DisConfigTypeEnum.getByType(config.getConfigType())
				.getModelName());
		confListVo.setTypeId(config.getConfigType());

		//
		//
		//
		if (zkDisconfData != null) {

			confListVo.setMachineSize(zkDisconfData.getData().size());

			List<ZkDisconfDataItem> datalist = zkDisconfData.getData();

			MachineListVo machineListVo = getZkData(datalist, config);

			confListVo.setErrorNum(machineListVo.getErrorNum());
			confListVo.setMachineList(machineListVo.getDatalist());
			confListVo.setMachineSize(machineListVo.getMachineSize());
		}

		return confListVo;
	}

	/**
     *
     */
	private List<String> compareConfig(String zkData, String dbData) {

		List<String> errorKeyList = new ArrayList<String>();

		Properties prop = new Properties();
		try {
			prop.load(IOUtils.toInputStream(dbData));
		} catch (Exception e) {
			LOG.error(e.toString());
			errorKeyList.add(zkData);
			return errorKeyList;
		}

		Map<String, String> zkMap = GsonUtils.parse2Map(zkData);
		for (String keyInZk : zkMap.keySet()) {

			Object valueInDb = prop.get(keyInZk);
			String zkDataStr = zkMap.get(keyInZk);

			// convert zk data to utf-8
			// zkMap.put(keyInZk, CodeUtils.unicodeToUtf8(zkDataStr));

			try {

				if ((zkDataStr == null && valueInDb != null)
						|| (zkDataStr != null && valueInDb == null)) {
					errorKeyList.add(keyInZk);

				} else if(zkDataStr != null){
					boolean isEqual = true;

					if (StringUtils.isDouble(zkDataStr)
							&& StringUtils.isDouble(valueInDb.toString())) {

						if (Math.abs(Double.parseDouble(zkDataStr)
								- Double.parseDouble(valueInDb.toString())) > 0.001d) {
							isEqual = false;
						}

					} else {
						if (!zkDataStr.equals(valueInDb.toString().trim())) {
							isEqual = false;
						}
					}

					if (!isEqual) {
						errorKeyList.add(keyInZk
								+ "\t"
								+ DiffUtils.getDiffSimple(zkDataStr, valueInDb
										.toString().trim()));
					}
				}

			} catch (Exception e) {

				LOG.warn(e.toString() + " ; " + keyInZk + " ; "
						+ zkMap.get(keyInZk) + " ; " + valueInDb);
			}
		}

		return errorKeyList;
	}

	/**
	 * 根据 配置ID获取配置返回
	 */
	@Override
	public ConfListVo getConfVo(Long configId) {
		Config config = configDao.get(configId);

		App app = appMgr.getById(config.getAppId());
		Env env = envMgr.getById(config.getEnvId());

		return convert(config, app.getAppName(), env.getEnvName(), null);
	}

	/**
	 * 根据 配置ID获取ZK对比数据
	 */
	@Override
	public MachineListVo getConfVoWithZk(Long configId) {

		Config config = configDao.get(configId);

		App app = appMgr.getById(config.getAppId());
		Env env = envMgr.getById(config.getEnvId());

		DisConfigTypeEnum disConfigTypeEnum = DisConfigTypeEnum.FILE;
		
		if (config.getConfigType().equals(DisConfigTypeEnum.ITEM.getType())) {
			disConfigTypeEnum = DisConfigTypeEnum.ITEM;
		}

		ZkDisconfData zkDisconfData = zkDeployMgr.getZkDisconfData(app.getAppName(),
					env.getEnvName(), config.getVersion(), disConfigTypeEnum,
					config.getConfigName());

		if (zkDisconfData == null) {
			return new MachineListVo();
		}

		MachineListVo machineListVo = getZkData(zkDisconfData.getData(), config);
		
		return machineListVo;
	}

	/**
	 * 根据配置ID获取配置
	 */
	@Override
	public Config getConfigById(Long configId) {

		return configDao.get(configId);
	}

	/**
	 * 更新 配置项/配置文件 的值
	 */
	@SuppressWarnings("unused")
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
	public String updateItemValue(Long configId, String value) {

		Config config = getConfigById(configId);
		Date date = new Date();
		config.setUpdateDate(date);
		config.setEffectiveDate(date);
		String oldValue = config.getConfigValue();

		//
		// 配置数据库的值 encode to db
		//
		configDao.updateValue(configId, CodeUtils.utf8ToUnicode(value));

		//
		// 发送邮件通知
		//
		String toEmails = appMgr.getEmails(config.getAppId());

//		if (applicationPropertyConfig.isEmailMonitorOn() == true
//				&& !StringUtils.isEmpty(toEmails)) {
//			boolean isSendSuccess = logMailBean.sendHtmlEmail(toEmails,
//					" config update", DiffUtils.getDiff(
//							CodeUtils.unicodeToUtf8(oldValue), value,
//							config.toString(), getConfigUrlHtml(config)));
//			if (isSendSuccess) {
//				return "修改成功，邮件通知成功";
//			} else {
//				return "修改成功，邮件发送失败，请检查邮箱配置";
//			}
//		}

		return "修改成功";
	}

	/**
	 * @return
	 */
	@SuppressWarnings("unused")
	private String getConfigUrlHtml(Config config) {

		return "<br/>点击<a href='http://"
				+ "/modifyFile.html?configId=" + config.getId()
				+ "'> 这里 </a> 进入查看<br/>";
	}

	/**
	 * @param newValue
	 * @param identify
	 *
	 * @return
	 */
	@SuppressWarnings("unused")
	private String getNewValue(String newValue, String identify,
			String htmlClick) {

		String contentString = StringEscapeUtils.escapeHtml(identify) + "<br/>"
				+ htmlClick + "<br/><br/> ";

		String data = "<br/><br/><br/><span style='color:#FF0000'>New value:</span><br/>";
		contentString = contentString + data
				+ StringEscapeUtils.escapeHtml(newValue);

		return contentString;
	}

	/**
	 * 通用配置，通知所有子系统；非通用配置，通知配置所属子系统
	 * 
	 * @param configId
	 * @see com.gy.hsi.ds.param.web.IConfigMgr.config.service.ConfigMgr#notifyZookeeper(java.lang.Long)
	 */
	@Override
	public void notifyZookeeper(Long configId) {

		ConfListVo confListVo = getConfVo(configId);

		notifyZookeeper(confListVo);
	}

	/**
	 * 通知Zookeeper, 失败时不回滚数据库,通过监控来解决分布式不一致问题
	 * 
	 * @param vo
	 */
	private void notifyZookeeper(ConfListVo vo) {
		if (vo.getTypeId().equals(DisConfigTypeEnum.FILE.getType())) {

			zooKeeperDriver.notifyNodeUpdate(vo.getAppName(), vo.getEnvName(),
					vo.getVersion(), vo.getKey(),
					GsonUtils.toJson(vo.getValue()), DisConfigTypeEnum.FILE);
		} else {
			zooKeeperDriver.notifyNodeUpdate(vo.getAppName(), vo.getEnvName(),
					vo.getVersion(), vo.getKey(), vo.getValue(),
					DisConfigTypeEnum.ITEM);
		}
	}

	/**
	 * 获取配置值
	 */
	@Override
	public String getValue(Long configId) {
		return configDao.getValue(configId);
	}

	/**
	 * 新建配置
	 */
	@Override
	public void newConfig(ConfNewItemForm confNewForm,
			DisConfigTypeEnum disConfigTypeEnum) {

		Config config = new Config();

		config.setAppId(confNewForm.getAppId());
		config.setEnvId(confNewForm.getEnvId());
		config.setConfigName(confNewForm.getKey());
		config.setConfigType(disConfigTypeEnum.getType());
		config.setVersion(confNewForm.getVersion());
		config.setConfigValue(CodeUtils.utf8ToUnicode(confNewForm.getValue()));

		// 时间
		Date curTime = new Date();
		config.setCreateDate(curTime);
		config.setUpdateDate(curTime);
		config.setEffectiveDate(curTime);
		configDao.create(config);

		// 发送邮件通知
//		String toEmails = appMgr.getEmails(config.getAppId());

//		if (applicationPropertyConfig.isEmailMonitorOn() == true
//				&& !StringUtils.isEmpty(toEmails)) {
//			logMailBean.sendHtmlEmail(
//					toEmails,
//					" config new",
//					getNewValue(confNewForm.getValue(), config.toString(),
//							getConfigUrlHtml(config)));
//		}
	}

	@Override
	public void delete(Long configId) {

		configDao.delete(configId);
	}

	@Override
	public void deleteByAppId(Long appId) {
		List<Config> configList = getConfigList(appId);
		
		if(null == configList) {
			return; 
		}
		
		for(Config cfg : configList) {
			configDao.delete(cfg.getId());
		}
	}

	@Override
	public void deleteByAppIdAndConfigName(Long appId, String configName) {
		List<Config> configList = getConfigList(appId);

		if (null == configList) {
			return;
		}

		for (Config cfg : configList) {
			if (cfg.getConfigName().equals(configName)) {
				configDao.delete(cfg.getId());
			}
		}
	}

}

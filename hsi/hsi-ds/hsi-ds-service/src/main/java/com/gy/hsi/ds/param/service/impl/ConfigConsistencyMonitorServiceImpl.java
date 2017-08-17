package com.gy.hsi.ds.param.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.gy.hsi.ds.common.thirds.ub.common.db.DaoPageResult;
import com.gy.hsi.ds.param.beans.ZkDisconfData.ZkDisconfDataItem;
import com.gy.hsi.ds.param.beans.bo.App;
import com.gy.hsi.ds.param.beans.bo.Env;
import com.gy.hsi.ds.param.beans.vo.ConfListVo;
import com.gy.hsi.ds.param.controller.form.ConfListForm;
import com.gy.hsi.ds.param.service.IAppMgr;
import com.gy.hsi.ds.param.service.IConfigConsistencyMonitorService;
import com.gy.hsi.ds.param.service.IConfigMgr;
import com.gy.hsi.ds.param.service.IEnvMgr;

/**
 * http://blog.csdn.net/sd4000784/article/details/7745947 <br/>
 * http://blog.sina.com.cn/s/blog_6925c03c0101d1hi.html
 *
 * @author knightliao
 */
@Component
public class ConfigConsistencyMonitorServiceImpl implements
		IConfigConsistencyMonitorService {

	protected static final Logger LOG = LoggerFactory
			.getLogger(ConfigConsistencyMonitorServiceImpl.class);

	@Autowired
	private IAppMgr appMgr;

	@Autowired
	private IEnvMgr envMgr;

	@Autowired
	private IConfigMgr configMgr;

	// 每5分钟执行一次自动化校验
	@Scheduled(fixedDelay = 5 * 60 * 1000)
	@Override
	public void myTest() {
		LOG.info("task schedule just testing, every 5 min");
	}

	// 每30分钟执行一次自动化校验
	@Scheduled(fixedDelay = 30 * 60 * 1000)
	@Override
	public void check() {

		try {
			Thread.sleep(1000 * 10);
		} catch (InterruptedException e) {
			LOG.info("", e);
		}

		checkMgr();
	}

	/**
	 * 主check MGR
	 */
	private void checkMgr() {

		List<App> apps = appMgr.getAppList();
		List<Env> envs = envMgr.getList();

		// app
		for (App app : apps) {
			checkAppConfigConsistency(app, envs);
		}
	}

	/**
	 * 校验APP 一致性
	 */
	private void checkAppConfigConsistency(App app, List<Env> envs) {

		// env
		for (Env env : envs) {
			// version
			List<String> versionList = configMgr.getVersionListByAppEnv(
					app.getId(), env.getId());

			for (String version : versionList) {
				checkAppEnvVersionConfigConsistency(app, env, version);
			}
		}
	}

	/**
	 * 校验APP/ENV/VERSION 一致性
	 */
	private void checkAppEnvVersionConfigConsistency(App app, Env env,
			String version) {

		String monitorInfo = "monitor " + app.getAppName() + "\t"
				+ env.getEnvName() + "\t" + version;
		
		LOG.info(monitorInfo);

		//
		//
		//
		ConfListForm confiConfListForm = new ConfListForm();
		confiConfListForm.setAppId(app.getId());
		confiConfListForm.setEnvId(env.getId());
		confiConfListForm.setVersion(version);

		//
		//
		//
		DaoPageResult<ConfListVo> daoPageResult = configMgr.getConfigList(
				confiConfListForm, true, true);

		List<ConfListVo> confListVos = daoPageResult.getResult();
		List<String> errorList = new ArrayList<String>();

		for (ConfListVo confListVo : confListVos) {

			if (confListVo.getErrorNum() == 0) {
				continue;
			}

			List<ZkDisconfDataItem> zkDisconfDataItems = confListVo
					.getMachineList();

			for (ZkDisconfDataItem zkDisconfDataItem : zkDisconfDataItems) {

				if ((null == zkDisconfDataItem.getErrorList())
						|| (0 == zkDisconfDataItem.getErrorList().size())) {
					continue;
				}

				String data = zkDisconfDataItem.toString()
						+ "<br/><br/><br/><br/><br/><br/>original:"
						+ confListVo.getValue();

				LOG.warn(data);

				errorList.add(data + "<br/><br/><br/>");
			}
		}
	}
}

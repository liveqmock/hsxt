package com.gy.hsi.ds.param.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baidu.disconf.core.common.constants.Constants;
import com.baidu.disconf.core.common.constants.DisConfigTypeEnum;
import com.baidu.disconf.core.common.json.ValueVo;
import com.gy.hsi.ds.common.utils.ValuesUtils;
import com.gy.hsi.ds.param.beans.bo.Config;
import com.gy.hsi.ds.param.dao.IConfigDao;
import com.gy.hsi.ds.param.service.IConfigFetchMgr;

/**
 * @author knightliao
 */
@Service
public class ConfigFetchMgrImpl implements IConfigFetchMgr {

    protected static final Logger LOG = Logger.getLogger(ConfigFetchMgrImpl.class);

    @Autowired
    private IConfigDao configDao;

    /**
     * 根据详细参数获取配置
     */
    @Override
    public Config getConfByParameter(Long appId, Long envId, String version, String key,
            DisConfigTypeEnum disConfigTypeEnum) {
        return configDao.getByParameter(appId, envId, version, key, disConfigTypeEnum);
    }

    /**
     * 根据详细参数获取配置返回
     */
	public ValueVo getConfItemByParameter(Long appId, Long envId,
			String version, String key) {

		Config config = configDao.getByParameter(appId, envId, version, key,
				DisConfigTypeEnum.ITEM);

		if (config == null) {
			return ValuesUtils.getErrorVo("cannot find this config");
		}

		ValueVo valueVo = new ValueVo();
		valueVo.setValue(config.getConfigValue());
		valueVo.setStatus(Constants.OK);

		return valueVo;
	}
}

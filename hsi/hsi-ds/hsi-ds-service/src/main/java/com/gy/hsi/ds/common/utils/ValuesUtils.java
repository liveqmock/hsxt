package com.gy.hsi.ds.common.utils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baidu.disconf.core.common.constants.Constants;
import com.baidu.disconf.core.common.json.ValueVo;
import com.gy.hsi.common.utils.StringUtils;
import com.gy.hsi.ds.common.constant.JobConstant;

public class ValuesUtils {

	private static Logger logger = LoggerFactory.getLogger(ValuesUtils.class);

	/**
	 * @param errorMsg
	 *
	 * @return
	 */
	public static ValueVo getErrorVo(String errorMsg) {

		ValueVo confItemVo = new ValueVo();
		confItemVo.setStatus(Constants.NOTOK);
		confItemVo.setValue("");
		confItemVo.setMessage(errorMsg);

		return confItemVo;
	}

	/**
	 * 筛选掉个别key
	 * 
	 * @param servicePara
	 * @param ignoreKeys
	 * @return
	 */
	public static final String filterParams(String servicePara,
			String[] ignoreKeys) {
		Map<String, String> map = ValuesUtils.getParmMap(servicePara,
				ignoreKeys);

		return ValuesUtils.formatMap2KeyValueStr(map);
	}

	/**
	 * 将key-value格式的字串解析为Map
	 * 
	 * @param servicePara
	 * @return
	 */
	public static final Map<String, String> getParmMap(String servicePara) {
		return getParmMap(servicePara, null);
	}

	/**
	 * 将key-value格式的字串解析为Map
	 * 
	 * @param servicePara
	 * @param ignoreKeys
	 * @return
	 */
	public static final Map<String, String> getParmMap(String servicePara,
			String[] ignoreKeys) {

		Map<String, String> result = new HashMap<String, String>();

		try {
			if (!StringUtils.isEmpty(servicePara)) {
				String[] params = servicePara
						.split(JobConstant.PARA_SEPARATOR_COMMA);
				String[] param = null;

				String currString;

				for (int i = 0; i < params.length; i++) {
					currString = params[i];

					if (StringUtils.isEmpty(currString)) {
						continue;
					}

					param = currString.split(JobConstant.PARA_SEPARATOR_EQUAL);

					if (param != null && param.length == 1) {
						result.put(param[0], "");
					}

					if (param != null && param.length == 2) {
						result.put(param[0], param[1]);
					}
				}
			}

			if (null != ignoreKeys) {
				for (String key : ignoreKeys) {
					result.remove(key);
				}
			}
		} catch (Exception e) {
			logger.error("", e);
		}

		return result;
	}

	/**
	 * 将Map解析为key-value格式的字串
	 * 
	 * @param tempArgsMap
	 * @return
	 */
	public static final String formatMap2KeyValueStr(
			Map<String, String> tempArgsMap) {
		return formatMap2KeyValueStr(tempArgsMap, null, false);
	}

	/**
	 * 将Map解析为key-value格式的字串
	 * 
	 * @param tempArgsMap
	 * @param ignoreKeys
	 * @param ignoreValues
	 * @return
	 */
	public static final String formatMap2KeyValueStr(
			Map<String, String> tempArgsMap, String[] ignoreKeys,
			boolean ignoreValues) {

		if ((null == tempArgsMap) || (0 >= tempArgsMap.size())) {
			return "";
		}

		List<String> ignoreKeyList = (null == ignoreKeys) ? null : Arrays
				.asList(ignoreKeys);

		Entry<String, String> entry;

		StringBuilder tempArgs = new StringBuilder();

		Iterator<Entry<String, String>> itor = tempArgsMap.entrySet()
				.iterator();

		// 解析key-value
		while (itor.hasNext()) {
			entry = itor.next();

			String key = entry.getKey();

			if (StringUtils.isEmpty(key) || (null != ignoreKeyList)
					&& ignoreKeyList.contains(key)) {
				continue;
			}

			tempArgs.append(key).append("=");

			if (!ignoreValues) {
				String value = StringUtils.avoidNull(entry.getValue());

				tempArgs.append(value);
			}

			tempArgs.append(";");
		}

		return tempArgs.toString();
	}
}

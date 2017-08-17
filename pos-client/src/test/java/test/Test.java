/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package test;

import org.apache.commons.codec.binary.StringUtils;
import java.util.HashMap;
import java.util.Map;
import com.gy.hsxt.bs.common.enumtype.tool.CategoryCode;
import com.gy.hsxt.point.client.service.ComToAccess;
import com.gy.hsxt.point.client.service.ComToRepairAccess;
import com.gy.hsxt.point.client.util.Constants;
import com.gy.hsxt.point.client.util.Persistence;
import com.gy.hsxt.point.client.util.StringEncrypt;

public class Test {

	public static Persistence persistence = new Persistence();

	// 参数持久化
	// static PosLogger poslogger = new PosLogger();
	static ComToAccess com = new ComToAccess();

	// static ComToPosRepairAccess recom = new ComToPosRepairAccess();

	public static void main(String args[]) {

		/*
		 * byte[] b={49, 48, 0, 0, 50, 48, 0, 0, 51, 48, 0, 0, 52, 48, 0, 0, 53,48, 0, 0}; byte[] Send=new byte[20]; int j=0; int i=0; for (int len=
		 * 0; len < 5; len++) { ComToPos.InsertByte(Send, i, GetType(b, j,
		 * ConstData.POINT_LEN)); i += ConstData.POINT_LEN; j +=
		 * ConstData.POINT_LEN; } System.out.println(Send); byte[]
		 * st={48,48,49,48}; double bb=47.524*10000; System.out.println(bb);
		 * //48,48,49,48,48,48,50,48,48,48,51,48,48,48,52,48,48,48,53,48
		 * System.out.println(StringUtils.newStringUtf8(st));
		 */
		// [48, 48, 49, 48,
		// 48, 48, 50, 48,
		// 48, 48, 51, 48,
		// 48, 48, 52, 48,
		// 48, 48, 53, 48]
		// 48,48,49,48,48,48,50,48,48,48,51,48,48,48,52,48,48,48,53,48
		// [48, 48, 49, 48, 48, 48, 50, 48, 48, 48, 51, 48, 48, 48, 52, 48, 48,
		// 48, 53, 48]

		/*
		 * double[] pointRates ={10,20,30,40,50}; byte[] pointRateBodyByte = new
		 * byte[Constants.POINTRATELEN*pointRates.length]; for(int i = 0 ; i <
		 * pointRates.length ; i++){ int pointRateInt = (int)pointRates[i];
		 * byte[] test = StringUtils.getBytesUtf8(pointRateInt+"");
		 * System.arraycopy(test, 0, pointRateBodyByte,
		 * i*Constants.POINTRATELEN, test.length); }
		 * System.out.println(pointRateBodyByte);
		 */
		// [49, 48, 0, 0, 50, 48, 0, 0, 51, 48, 0, 0, 52, 48, 0, 0, 53, 48, 0,0]

		// System.arraycopy(ss, 3, pmk2, 0, 16); // [-112, 2, 0, 31, -107, -116,
		// 86, 127, 124, 15, 40, 62, 50,
		// 71, 100, 62]

		// [48, 48, 49, 48, 48, 48, 50, 48, 48, 48, 51, 48, 48, 48, 52, 48, 48,
		// 48, 53, 48]
		// [48, 48, 49, 48, 48, 48, 50, 48, 48, 48, 51, 48, 48, 48, 52, 48, 48,
		// 48, 53, 48]

		// byte[] point3 = new byte[20];
		// System.arraycopy(ss, 280, point3, 0, 20);
		// byte[] pmk3 = new byte[16];
		// System.arraycopy(ss, 3, pmk3, 0, 16);
		// System.out.println(v2);
		byte[] pointRateBodyByte = new byte[Constants.POINTRATELEN * 5];
		String[] pointRates = { "0.001", "0.002", "0.003", "0.004", "0.005" };
		for (int i = 0; i < pointRates.length; i++) {
			double pointRatedouble = Double.valueOf(pointRates[i]) * 10000;
			int pointRateInt = (int) pointRatedouble;
			System.out.println(pointRateInt);
			byte[] test = StringUtils.getBytesUtf8(pointRateInt + "");
			System.arraycopy(test, 0, pointRateBodyByte, i
					* Constants.POINTRATELEN, test.length);
		}
		System.out.println(pointRateBodyByte);
	}

	public static void wh() {
		// 1分页查询POS机维护配置单
		ComToRepairAccess recom = new ComToRepairAccess();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("search_entResNo", "11");// 互生号
		map.put("search_custName", "33");// 客户名称，也就是企业名称
		map.put("pageSize", "1");// 分页每页行数
		map.put("curPage", "1");// 第几页
		// Map reMap = recom.accessRepareOrder(map, persistence);

		// 2分页查询POS机维护配置单详细清单
		Map<String, Object> map2 = new HashMap<String, Object>();
		map2.put("confNo", "110120151217175319000001");// 配置单号
		// recom.accessRepareDetailOrder(map2, persistence);

		// 3验证设备
		Map<String, Object> map3 = new HashMap<String, Object>();
		map3.put("deviceSeqNo", "0001");
		// recom.accessVaildSecretKeyDeviceAfter(map3, persistence);

		// 4获取PMK秘钥
		Map<String, Object> map5 = new HashMap<String, Object>();
		map5.put("deviceNo", "0004");// 06001020000 //0600102000020151215
		map5.put("entCustId", "0600102000020151215");
		map5.put("machineNo", "K3202505947");
		map5.put("operCustId", "06002110000164063559726080");
		map5.put("newVersion", true);
		map5.put("confNo", "110120151217175319000000");
		recom.accessGetReparPmk(map5, persistence);

		// 5配置POS机设备与企业关联关系
		Map<String, Object> map7 = new HashMap<String, Object>();
		map7.put("entCustId", "11"); // 客户号
		map7.put("afterOrderNo", "11"); // 售后服务编号
		map7.put("confNo", "11"); // 配置单号
		map7.put("deviceSeqNo", "11"); // 设备序列号
		map7.put("terminalNo", "22");// 终端编号
		map7.put("newDeviceSeqNo", "11aa"); // 新设备序列号
		map7.put("operNo", "11aa"); // 操作员编号
		/*
		 * recom.accessConfigSecretKeyDeviceAfter(map7, persistence, poslogger,
		 * "33");
		 */
	}

	public static void sg() {
		ComToAccess com = new ComToAccess();
		// 登出
		Map<String, Object> map0 = new HashMap<String, Object>();
		map0.put("channelType", "2");// 登录类型为1，表示渠道类型，WEB（web端）
		map0.put("custId", "06002110000164063559726080");// 登录IP
		// com.accessLoginOut(map0, persistence, poslogger);

		// 0600102000020151215 企业客户号
		// 110120151217155608000000 配置单号
		// 06001020000 互生号
		// 37002107773 设备序列号
		// 1登录
		Map<String, Object> map1 = new HashMap<String, Object>();
		map1.put("channelType", "2");// 登录类型为1，表示渠道类型，WEB（web端）
		map1.put("loginIp", "192.168.12.24");// 登录IP
		map1.put("userName", "0000");// 用户
		map1.put("resNo", "06002110000");// 互生号
		map1.put("randomToken", "17cf679048b96b8c");// 随机randomToken
		String pwd = "222222";
		pwd = StringEncrypt.encrypt(pwd, "17cf679048b96b8c");
		map1.put("loginPwd", pwd);// 密码
		 com.accessLogin(map1, persistence);

		// 2分页查询申购订单
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("resNo", "");// 互生号
		map.put("custName", "");// 客户名称，也就是企业名称
		map.put("pageSize", 2);// 分页每页行数
		map.put("curPage", 1);// 第几页
		map.put("custId", "06002110000164063559726080");// 操作员客户号
		// com.accessOrder(map, persistence);

		// 3查询设备的终端编号列表
		// "06002110000164063559693312", "110120151202070553000000", "P_POS"
		Map<String, Object> map2 = new HashMap<String, Object>();
		map2.put("entCustId", "0600102000020151215");
		map2.put("confNo", "110120151217155608000000");
		map2.put("categoryCode", CategoryCode.getCode("P_POS"));
		// com.accessOrderEntNo(map2, persistence);

		// 4获取PMK秘钥
		Map<String, Object> map5 = new HashMap<String, Object>();
		map5.put("confNo", "110120151221104449000002");// 配置单号
		map5.put("entCustId", "0600102000020151215");
		map5.put("operCustId", "06002110000164063559726080");
		map5.put("machineNo", "K3202505947");
		map5.put("deviceNo", "11");
		map5.put("isNewVersion", true);
		com.accessGetPmk(map5, persistence);

		// 5修改POS机启用状态,缺少返回值，有问题，修改配置POS机关联企业状态
		Map<String, Object> map6 = new HashMap<String, Object>();
		map6.put("deviceNo", "37002107774");// 设备序列号
		map6.put("entResNo", "06001020000");// 企业互生号
		map6.put("status", "1");// 设备状态1 启用、2 停用、3 返修、4冻结 备注
		map6.put("entCustId", "0600102000020151215"); // 客户号
		map6.put("confNo", "110120151221103315000002");// 配置单号
		map6.put("terminalNo", "060010200000003"); // 终端编号 终端编号 ////
													// 平台系统给企业设备定义的4位数编号
		map6.put("operator", "06002110000164063559726080");
		// com.accessConfigToolDeviceIsUsed(map6, persistence, poslogger, "12");
	}
}

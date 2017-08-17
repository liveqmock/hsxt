package com.gy.hsxt.point.client.util;

import gnu.io.SerialPort;

/**
 * Simple to Introduction
 * 
 * @category 常量类
 * @projectName WritePOS
 * @package .ConstData.java
 * @className ConstData
 * @description 常量类
 * @author fandi
 * @createDate 2014-8-6 下午2:07:38
 * @updateUser lyh
 * @updateDate 2015-12-6 下午2:07:38
 * @updateRemark 说明本次修改内容
 * @version V3.0
 */
public class ConstData {
	public static final boolean IFDEBUG = true;

	public static final Object[][] EMPTY_CELL = null;
	// 屏幕尺寸
	public static final int HEIGHT = 450;
	public static final int WIDTH = 800;

	public static final int GAP = 7;
	public static final int TOP = 20;
	public static final String ORDER_LIST = "配置单列表";
	public static final String[] ORDER_HANDERS = { "配置单号", "订单编号", "企业名称",
			"企业编码", "数量", "终端编号" };
	public static final String[] ORDER_HANDERSREPAIR = { "订单号", " 配置单号",
			"企业名称", "企业编码", "数量", "详情" };
	public static final String[] REPARE_HANDERS = { "售后订单号", "配置单号", "是否配置",
			"终端编号","机器码" };
	public static final int TABLECOL0LEN = 160; // 列宽
	public static final int TABLECOL1LEN = 160; // 列宽
	public static final int TABLECOL2LEN = 180;
	public static final int TABLECOL3LEN = 80;
	public static final int TABLECOL4LEN = 80;
	public static final int TABLECOL5LEN = 20;

	public static final int ORDER_HEIGHT = 500;
	public static final int ORDER_WIDTH = 860;

	public static final int CHECK_TOP = 35;
	public static final int CHECKIN_WIDTH = 350;
	public static final int CHECK_HEIGHT = 300;

	// 机器号
	public static final String MachineNO = "机器号";

	public static final String OPERATOR_LABEL = "用户名";
	public static final String PASSWORD_LABEL = "密码  ";
	public static final String RES_NO_LABEL = "互生号";
	public static final String ORDER_NO_LABEL = "订单号";
	public static final String ENTER_NO_LABEL = "企业名称";
	public static final String CONFIG = "配置";
	public static final String CHECKIN = "登录";
	public static final String PRINT = "打印";
	public static final String CHECKCOM = "端口";
	public static final String SEEKORDER = "搜索";// 搜索订单号
	public static final String REFLASH = "获取订单列表";
	public static final String REPARE = "维护灌秘钥";
	public static final String LASTPAGE = "上一页";
	public static final String NEXTPAGE = "下一页";

	public static final String SEARCH_TYPE_1 = "申购订单";

	public static final String SEARCH_TYPE_2 = "维护订单";

	// 配置问题
	// 读取相对运行jar包外配置
	public static final String CONFIG_PATH = System.getProperty("user.dir")
			+ "/conf/";
	public static final String CONFIG_FILE = "pos_client.properties";
	public static final String Logo = "Logo.jpg";
	// 服务器配置
	public static final int CONFIG_HEIGHT = 420;
	public static final int CONFIG_WIDTH = 400;
	public static final String KEY_SERVER = "POS 接入层服务器";
	public static final String NOUSER_NO = "终端编号";
	public static final String REPARI_TITLE = "维护清单";
	public static final String USER_NO = "已使用终端号";
	public static final String IP_ADDRESS = "IP地址";
	public static final String PORT = "端口";
	public static final String SAVE = "保存";
	public static final String EXIT = "退出";
	public static final String PARA = "平台参数";
	public static final String TELEPHONE = "平台电话";
	public static final String URL = "平台网址";
	public static final String RESNO = "互生号   ";
	// 状态
	public static final String STATUS = "状态";
	public static final String UNCKECKIN = "未登录";
	public static final String CKECKINING = "正在登录";
	public static final String CKECKIN = "已登录";
	public static final String CKECKIN_ERR = "登录失败";
	public static final String OPEN_SERIAL_ERR = "打开端口失败";
	public static final String WRITE_SERIAL_ERR = "写端口失败";
	public static final String GET_MACHINENO = "取机器号中......";
	public static final String GET_MACHINENO_ERR = "读取机器码失败";
	public static final String GET_PMK = "从服务器获取PMK中.....";
	public static final String GET_PMK_ERR = "获取PMK失败";
	public static final String WRITE_PMK = "写PMK中.....";
	public static final String WRITE_PMK_ERR = "写PMK失败";
	public static final String CLOSE_POS = "关闭POS通讯......";
	public static final String CLOSE_POS_ERR = "关闭POS通讯失败";
	public static final String INFO_TO_SERVER = "通知服务器成功烧机......";
	public static final String INFO_TO_SERVER_ERR = "通知服务器失败";
	public static final String STR_TIME_OUT = "超时，烧机失败";
	public static final String WRITE_INFOMATION = "写企业信息中......";
	public static final String WRITE_INFOMATIONOK = "写企业信息成功";
	public static final String DONE = "烧机成功";
	public static final String POS_ERROR = "POS通讯错误";
	public static final int ERRORDEFINE = 9;
	public static final String ERRORCODE[] = { "用户名不存在", "密码错误", "订单数为零",
			"企业信息录入不完整", "订单号错误", "该POS机未入库", "机器码已存在", "烧录不成功", "此机没有售后单", };
	public static final String DEFINE99 = "服务器其他异常";
	public static final String DEFINEELSE = "未知异常";

	// 串口通讯
	public static final int OPEN_TIME_OUT = 300;
	public static final int BAUDRATE_OLD = 9600;
	public static final int BAUDRATE = 38400;
	public static final int DATABITS = SerialPort.DATABITS_8;
	public static final int STOPBITS = SerialPort.STOPBITS_1;
	public static final int PARITY = SerialPort.PARITY_NONE;
	public static final int FLOWCONTROL = SerialPort.FLOWCONTROL_NONE;
	// POS通讯协议
	public static final byte[] GET_MACHINE_NO_COMMAND = { (byte) 0x90, 0x01 };
	public static final byte[] SHUT_DOWN_COMMAND = { (byte) 0x90, 0x09 };
	public static final byte[] WRITE_COMMAND = { (byte) 0x90, 0x02 };
	public static final byte STX = 0x02;
	public static final byte ETX = 0x03;
	public static final int TIME_OUT = 10000;
	public static final int TIME_OUT_SVR = 2000;

	public static final String IN_BUF_HEAD = "(guiyiapp)";
	public static final String IN_BUF_TAIL = "(append)";

	// 模拟POS通信数据
	/*
	 * public static final byte[] PMK = { (byte) 0xa0, (byte) 0x8b, (byte) 0xe9,
	 * (byte) 0x53, (byte) 0x37, (byte) 0x11, (byte) 0x12, (byte) 0x37, (byte)
	 * 0x5f, (byte) 0x64, (byte) 0x13, (byte) 0x46, (byte) 0x51, (byte) 0x69,
	 * (byte) 0x70, (byte) 0x07 };
	 */
	/*
	 * public static final byte[] PMK = { (byte) 0xf5, (byte) 0xb6, (byte) 0xe9,
	 * (byte) 0x53, (byte) 0x15, (byte) 0x52, (byte) 0x32, (byte) 0x32, (byte)
	 * 0x69, (byte) 0x2b, (byte) 0x2b, (byte) 0x73, (byte) 0x2c, (byte) 0x03,
	 * (byte) 0x58, (byte) 0x04 }; public static final String ENT_NO =
	 * "05005030000"; public static final String POS_NO = "21"; public static
	 * final String BASE_VERSION = "0001"; public static final String
	 * CURRENCY_VERSION = "0001"; public static final String COUNTRY_VERSION =
	 * "0004";
	 * 
	 * public static final byte[] ENT_NAME = { (byte) 0xc2, (byte) 0xcc, (byte)
	 * 0xbf, (byte) 0xa8, (byte) 0xca, (byte) 0xd0, (byte) 0xcd, (byte) 0xd0,
	 * (byte) 0xb9, (byte) 0xdc, (byte) 0xc6, (byte) 0xf3, (byte) 0xd2, (byte)
	 * 0xb5 }; // public static final int ENT_NAME_LEN = 40; public static final
	 * String ENT_TEL = "0755-83344111"; // public static final int ENT_TEL_LEN
	 * = 25; public static final String ENT_URL = "www.hsxt.com"; // public
	 * static final int ENT_URL_LEN = 30; public static final String[][]
	 * CURRENCY_NAME = { { "156", "CNY" }, { "344", "HKD" }, { "446", "MOP" }, {
	 * "901", "TWD" }, { "840", "USD" }, { "978", "EUR" } }; public static final
	 * int CURRENCY_NAME_LEN = 10; public static final String COUNTRY_I_CODE =
	 * "001076002112003060004100005580006585007204008056009352010630011616012068013070014072015084016064017854018108019074020408021226022208023276024626025768026214027212028643029218030232031250032234033258034254035260036336037608038242039246040132041270042180043178044170045188046308047304048268049192050312051316052328053398054332055410056528057530058334059340060296061262062417063324064624065124066288067266068116069203070716071120072634073136074166075174076384077414078191079404080184081428082426083418084422085430086434087440088438089638090442091646092642093450094470095462096238097454098458099466100807101584102474103175104480105478106840107016108581109850110496111500112050113604114583115104116498117504118492119508120484121516122710123010124239125349126520127524128558129562130566131570132578133574134612135620136392137752138756139222140694141686142196143690144682145162146678147654148659149662150674151666152670153144154703155705156744157748158729159740160706161090162762163764164834165776166796167780168788169798170792171795172772173876174548175320176862177096178800179804180858181860182724183732184882185300186344187702188540189554190348191760192388193051194887195368196364197376198380199356200360201826202092203086204400205704206894208148209292210152211140212156213158214008215012216004217032218784219533220512221031222818223231224372225233226020227024228660229028230040231036232446233052234598235044236586237600238275239048240591241248242499243531244534245535246652247663248688249728250831251832252833"
	 * ; public static final int COUNTRY_COUNT = 251;
	 */// 与后台服务器通讯常量
		// public static final String CERTIFICATE = "keyClient";
	// public static final String KEY = "123456";
	// public static final String COMMODE = "SSL";
	// 协议
	// 客户端向服务端登录并申请所有订单编号列表（命令字：请求01，应答81H）
	// 上行
	public static final byte COMMAND_CHECKIN = 1;
	/**
	 * 用户名长度
	 */
	public static final int USER_NAME_LEN = 16;
	/**
	 * 密码
	 */
	public static final int PASS_MD5_LEN = 16;
	// 下行
	public static final byte ANSWER_CHECKIN = (byte) 0x81;
	// unsigned short 数量
	/**
	 * 订单号
	 */
	public static final int ORDERNO_LEN = 32;
	/**
	 * 企业名称
	 */
	public static final int ENT_NAME_LEN = 128;
	/**
	 * 企业编号
	 */
	public static final int ENT_NO_LEN = 11;// 修改过
	/**
	 * unsigned short 未烧数量
	 */
	// 客户端向服务端申请所有未成功POS列表（命令字：请求02，应答82H
	// 上行
	public static final byte COMMAND_GET_NEW_MACHINE_LIST = 2;
	public static final byte COMMAND_REPAIR_GET_NEW_MACHINE_LIST = 7;// 维护订单请求
	// ORDERNO_LEN = 18;
	// 下行
	public static final byte ANSWER_GET_NEW_MACHINE_LIST = (byte) 0x82;
	// unsigned short 数量
	/**
	 * POS序号
	 */
	public static final int POSINDEX_LEN = 4;
	// char POS机状态

	// 客户端向服务端申请PMK及企业信息（命令字：请求03，应答83H）
	// 上行
	public static final byte COMMAND_GET_PMK = 3;
	// 用户名 USER_NAME_LEN
	// 订单编号 ORDERNO_LEN
	// POS序号POSINDEX_LEN
	/**
	 * POS机序列号E
	 */
	public static final int POS_CODE_LEN = 11;

	/**
	 * POS机序列号E
	 */
	public static final int POS_NO_LEN = 15;

	// 下行
	public static final byte ANSWER_GET_PMK = (byte) 0x83;
	public static final int PMK_LEN = 16;
	//
	public static final int POS_INDEX_LEN = 4;
	public static final int BAS_INFO_VER_LEN = 4;
	public static final int CURRUNCY_INFO_VER_LEN = 4;
	public static final int COUNTRY_INFO_VER_LEN = 4;
	public static final int POINT_INFO_VER_LEN = 4;
	// ENT_NAME_LEN
	/**
	 * 企业电话
	 */
	public static final int ENT_TEL_LEN = 25;
	// 企业类型
	public static final int ENT_TYPE_LEN = 1;
	public static final int ENT_TYPE_POS_LEN = 16;
	/**
	 * 企业网址
	 */
	public static final int ENT_URL_LEN = 30;

	/**
	 * 货币数量
	 */
	public static final int CURRUNCY_NUMBER = 6;
	/**
	 * 货币序号
	 */
	// char CURRUNCY index
	/**
	 * 货币编号
	 */
	public static final int CURRUNCY_NO_LEN = 3;
	/**
	 * 货币名称
	 */
	public static final int CURRUNCY_NAME_LEN = 10;
	public static final int OFF_COUNTRY_SVR = 1 + PMK_LEN + ENT_NO_LEN
			+ POS_INDEX_LEN + BAS_INFO_VER_LEN + CURRUNCY_INFO_VER_LEN
			+ COUNTRY_INFO_VER_LEN + POINT_INFO_VER_LEN + ENT_NAME_LEN
			+ ENT_TYPE_LEN + CURRUNCY_NUMBER
			* (1 + CURRUNCY_NO_LEN + CURRUNCY_NAME_LEN);

	public static final int OFF_COUNTRY = 1 + PMK_LEN + POS_INDEX_LEN
			+ BAS_INFO_VER_LEN + CURRUNCY_INFO_VER_LEN + COUNTRY_INFO_VER_LEN
			+ POINT_INFO_VER_LEN + ENT_NAME_LEN + CURRUNCY_NUMBER
			* (1 + CURRUNCY_NO_LEN + CURRUNCY_NAME_LEN);
	/**
	 * 国家数量
	 */

	// unsigned short 数量
	/**
	 * 国家序号
	 */
	public static final int COUNTRY_INDEX_LEN = 3;
	/**
	 * 国家编号
	 */
	public static final int COUNTRY_NO_LEN = 3;
	/**
	 * 积分转现比率
	 */
	public static final int POINT_CASH_LEN = 12;
	/**
	 * 互生币转现比率
	 */
	public static final int HUSHENG_CASH_LEN = 12;
	/**
	 * 积分比例
	 */
	public static final int POINT_NUMBER = 5;
	public static final int POINT_LEN = 4;

	public static final byte[] FLAG = { 0x60, (byte) 0xff };

	// 客户端向服务端发送写成功消息（命令字：请求04，应答84H）
	// 上行
	public static final byte COMMAND_WRITE_OK = 4;
	// ORDERNO_LEN
	// POS序号POSINDEX_LEN
	// 下行
	public static final byte ANSWER_WRITE_OK = (byte) 0x84;
	public static final byte ANSWER_ERROR = (byte) 0x99;
	public static final byte COMMAND_GET_REPARE_PMK = 5;
	public static final byte ANSWER_GET_REPARE_PMK = (byte) 0x85;
	public static final byte COMMAND_REPARE_WRITE_OK = 6;
	public static final byte ANSWER_REPARE_WRITE_OK = (byte) 0x86;
	/*
	 * 每页显示的数量
	 */
	public static final int PAGENUMBER = 1;

}

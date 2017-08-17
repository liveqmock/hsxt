package com.gy.hsxt.point.client.entry;

import java.awt.Choice;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.alibaba.druid.util.HexBin;
import com.gy.hsxt.bs.bean.tool.resultbean.AfterDeviceDetail;
import com.gy.hsxt.bs.bean.tool.resultbean.SecretKeyOrderInfoPage;
import com.gy.hsxt.bs.common.enumtype.tool.CategoryCode;
import com.gy.hsxt.point.client.service.ComToAccess;
import com.gy.hsxt.point.client.service.ComToPos;
import com.gy.hsxt.point.client.service.ComToRepairAccess;
import com.gy.hsxt.point.client.util.ConstData;
import com.gy.hsxt.point.client.util.Persistence;
import com.gy.hsxt.point.client.util.PosUtil;
import com.gy.hsxt.point.client.util.PrintPosNo;
import com.gy.hsxt.point.client.util.StringEncrypt;

/**
 * 
 * POS客户端主界面
 * 
 * @Package: com.gy.point.client.entry
 * @ClassName: MainView
 * 
 * @author: liyh
 * @date: 2015-12-18 上午11:23:40
 * @version V3.0
 */
@SuppressWarnings("serial")
public class MainView extends JFrame implements ActionListener, ItemListener {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(MainView.class);
	// 订单面板
	private JPanel OrderListPanel;
	// 订单表格
	private JTable OrderTable;
	// 订单行号
	int OrderTableRow = -1;
	// POS机器码
	String PosIndex;
	// 登陆，密码，配置面板
	private JPanel CheckinPanel;
	// 帐号面板
	private JPanel OperatorPanel;
	// 搜索面板
	private JPanel searchParamPanel;
	// 登陆，配置面板
	private JPanel ButtonPanel;
	// 机器码面板
	private JPanel MachinePanel;
	// 状态面板
	private JPanel SattusPanel;
	// 查询面板
	private JPanel SeekPanel;
	// LOGO面板
	private JPanel LogoPanel;
	// 分页面板
	private JPanel PagePanel;
	// 帐号标签
	private JLabel OperatorLabel;
	// 密码标签
	private JLabel PasswordLabel;
	// 帐号输入文本框
	private JTextField OperatorText;
	// 操作员客户号输入文本框(登陆返回的客户号缓存在这里，界面不显示)
	private JTextField custIdText;

	// 烧机成功标志
	private JTextField posResultText;
	// 终端编号(获取秘钥的时候返回，缓存在这里，界面不显示)
	private JTextField deviceNoText;
	// 登陆返回的Token(登陆返回的Token缓存在这里，界面不显示)
	private JTextField loginTokenText;
	// 密码输入框
	private JPasswordField PasswordText;
	// 搜索企业互生号标签
	private JLabel searchResNoLabel;
	// 搜索企业互生号文本框
	private JTextField searchResNoText;
	// 搜索企业名称标签
	private JLabel searchEnterNameLabel;
	// 搜索企业名称文本框
	private JTextField searchEnterNameText;
	// 配置按钮
	private JButton ConfigButton;
	// 刷新
	private JButton CheckCom;
	// 搜索订单按钮
	private JButton SeekOrderButton;
	// 登陆按钮
	private JButton CheckinButton;
	// 打印按钮
	private JButton printButton;
	// 上一页按钮
	private JButton LastPageButton;
	// 下一页按钮
	private JButton NextPageButton;
	// COM端口选择框
	private Choice ComPortChoice;
	// 单选按钮1
	private JRadioButton searchType1;
	// 单选按钮2
	private JRadioButton searchType2;
	// 搜索分组按钮
	private ButtonGroup searchTypeGroup;
	// POS机器码标签
	private JLabel MachineNOLabel;
	// 统计一共多少条数订单
	private JLabel totalNumberLabel;
	// POS机器码文本框
	private JTextField POSCode;
	// 状态标签
	private JLabel StatusLabel;
	// 状态文本框
	private JTextField StatusTEXT;
	// LOGO标签
	private JLabel ImageLogo;
	// 登录状态
	private boolean CheckinStatus;
	// 是否为维护烧机
	private boolean IfRepare;
	// 参数持久化
	public Persistence ConfigPara = new Persistence();
	// 与申购通讯
	ComToAccess comToPosAccess = new ComToAccess();
	// 与申购通讯
	ComToRepairAccess comToRepairAccess = new ComToRepairAccess();
	// 与POS机通讯类
	ComToPos comToPOS = new ComToPos(this);
	// 初始当前页
	private int currentPage = 1;

	/**
	 * 主界面显示
	 */
	public MainView() {
		// 扩展库位置设置
		String path = null;
		File f = new File("libext");
		try {
			path = f.getAbsolutePath();
			System.setProperty("java.library.path",
					path + ";" + System.getProperty("java.library.path"));
			LOGGER.info("set library path:"
					+ System.getProperty("java.library.path"));
			java.lang.reflect.Field fieldSysPath;
			fieldSysPath = ClassLoader.class.getDeclaredField("sys_paths");
			fieldSysPath.setAccessible(true);
			fieldSysPath.set(null, null);
		} catch (Exception e) {
			LOGGER.error("初始化串口库失败！" + e);
		}
		// 命令设置
		addWindowListener(new CloseHandler(this));
		// 窗口设置
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		// 设置位置
		setLocation(screenSize.width / 2 - ConstData.WIDTH / 2,
				screenSize.height / 2 - ConstData.HEIGHT / 2);
		// 设置大小
		setSize(ConstData.ORDER_WIDTH + ConstData.CHECKIN_WIDTH + 6
				* ConstData.GAP, ConstData.ORDER_HEIGHT + 7 * ConstData.GAP);
		// 视图设置
		setLayout(null);
		// 设置不可变大小
		setResizable(false);
		// 订单列表模型
		DefaultTableModel dataModel = new DefaultTableModel(
				ConstData.EMPTY_CELL, ConstData.ORDER_HANDERS) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		// 根据模型初始表格
		OrderTable = new JTable(dataModel);
		// 设置表格列宽
		TableColumn Column = OrderTable.getColumnModel().getColumn(0);
		Column.setPreferredWidth(ConstData.TABLECOL1LEN);
		Column = OrderTable.getColumnModel().getColumn(1);
		Column.setPreferredWidth(ConstData.TABLECOL2LEN);
		Column = OrderTable.getColumnModel().getColumn(2);
		Column.setPreferredWidth(ConstData.TABLECOL3LEN);
		Column = OrderTable.getColumnModel().getColumn(3);
		Column.setPreferredWidth(ConstData.TABLECOL4LEN);
		Column = OrderTable.getColumnModel().getColumn(4);
		Column.setPreferredWidth(ConstData.TABLECOL5LEN);
		OrderTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		// 订单列表面板
		OrderListPanel = new JPanel();
		// 设置订单列表面板边框
		OrderListPanel.setBorder(new TitledBorder(new EtchedBorder(),
				ConstData.ORDER_LIST));
		// 表格滚动面板
		JScrollPane JSP = new JScrollPane(OrderTable);
		// 设置布局
		OrderListPanel.setLayout(null);
		// 表格滚动面板添加到订单面板
		OrderListPanel.add(JSP);
		// 设置表格滚动面板位置和宽度，长度
		JSP.setBounds(ConstData.GAP, ConstData.TOP, ConstData.ORDER_WIDTH - 2
				* ConstData.GAP, ConstData.ORDER_HEIGHT - ConstData.TOP
				- ConstData.GAP);
		// 订单面板添加到主界面
		add(OrderListPanel);
		// 设置订单面板位置和宽度，长度
		OrderListPanel.setBounds(0, 0, ConstData.ORDER_WIDTH,
				ConstData.ORDER_HEIGHT);
		// 添加表格事件
		OrderTable.addMouseListener(new OrderClick());
		// 初始登录用户名标签
		OperatorLabel = new JLabel(ConstData.OPERATOR_LABEL);
		// 初始登录用户名输入框
		OperatorText = new JTextField(8);
		// 初始登录密码标签
		PasswordText = new JPasswordField(8);
		// 初始操作员客户号输入框
		custIdText = new JTextField(30);
		posResultText = new JTextField(30);
		posResultText.setVisible(false);
		// 终端编号文本框
		deviceNoText = new JTextField(150);
		deviceNoText.setVisible(false);
		// 登陸的Token文本框
		loginTokenText = new JTextField(300);
		loginTokenText.setVisible(false);
		// 设置不可见，用于隐藏
		custIdText.setVisible(false);
		// 初始登录密码输入框
		PasswordLabel = new JLabel(ConstData.PASSWORD_LABEL);
		// 设置初始用户名
		// OperatorText.setText(ConfigPara.getUserName());
		// 初始帐号，密码面板
		OperatorPanel = new JPanel();
		// 设置流式布局
		OperatorPanel.setLayout(new FlowLayout());
		// 添加用户名，密码标签，输入框到面板
		OperatorPanel.add(OperatorLabel);
		OperatorPanel.add(OperatorText);
		OperatorPanel.add(PasswordLabel);
		OperatorPanel.add(PasswordText);
		OperatorPanel.add(custIdText);
		OperatorLabel.add(deviceNoText);
		OperatorLabel.add(loginTokenText);
		OperatorPanel.add(posResultText);
		// 按钮按键
		// 配置按钮
		ConfigButton = new JButton(ConstData.CONFIG);
		// 添加事件
		ConfigButton.addActionListener(this);
		// 登陆按钮
		CheckinButton = new JButton(ConstData.CHECKIN);
		// 添加事件
		CheckinButton.addActionListener(this);
		// 打印按钮
		printButton = new JButton(ConstData.PRINT);
		// 不可编辑
		printButton.setEnabled(false);
		// 添加事
		printButton.addActionListener(this);
		// 刷新
		CheckCom = new JButton(ConstData.CHECKCOM);
		CheckCom.addActionListener(this);
		// 初始COM端口下拉框
		ComPortChoice = new Choice();
		comToPOS.listPortChoices(ComPortChoice);
		// 添加事件
		ComPortChoice.addItemListener(this);
		// 初始选择配置文件中的COM端口
		ComPortChoice.select(ConfigPara.getComPort());
		// 按钮面板
		ButtonPanel = new JPanel();
		// 设置流式布局
		ButtonPanel.setLayout(new FlowLayout());
		// 添加COM端口下拉框，配置按钮，登陆按钮，到面板
		ButtonPanel.add(ComPortChoice);
		ButtonPanel.add(CheckCom);
		ButtonPanel.add(ConfigButton);
		ButtonPanel.add(CheckinButton);
		ButtonPanel.add(printButton);
		// 机器码标签
		MachineNOLabel = new JLabel(ConstData.MachineNO);
		// 机器码输入框
		POSCode = new JTextField(12);
		// 设置不可编辑
		POSCode.setEditable(false);
		// 机器码面板
		MachinePanel = new JPanel();
		// 设置流式布局
		MachinePanel.setLayout(new FlowLayout());
		// 添加 机器码标签，机器码输入框，到面板
		MachinePanel.add(MachineNOLabel);
		MachinePanel.add(POSCode);

		// 烧机状态标签
		StatusLabel = new JLabel(ConstData.STATUS);
		// 烧机状态输入框
		StatusTEXT = new JTextField(12);
		// 设置初始状态：未登陆
		StatusTEXT.setText(ConstData.UNCKECKIN);
		// 设置不可编辑
		StatusTEXT.setEditable(false);
		// 烧机状态面板
		SattusPanel = new JPanel();
		// 设置流式布局
		SattusPanel.setLayout(new FlowLayout());
		// 添加 烧机状态标签，烧机状态输入框，到面板
		SattusPanel.add(StatusLabel);
		SattusPanel.add(StatusTEXT);

		// 互生号搜索标签
		searchResNoLabel = new JLabel(ConstData.RES_NO_LABEL);
		// 互生号输入框
		searchResNoText = new JTextField(8);
		// 企业名称搜索标签
		searchEnterNameLabel = new JLabel(ConstData.ENTER_NO_LABEL);
		// 企业名称输入框
		searchEnterNameText = new JTextField(8);
		// 搜索面板
		searchParamPanel = new JPanel();
		// 设置流式布局
		searchParamPanel.setLayout(new FlowLayout());
		// 添加 互生号搜索标签，互生号输入框，订单号搜索标签，订单号输入框到面板
		searchParamPanel.add(searchResNoLabel);
		searchParamPanel.add(searchResNoText);
		searchParamPanel.add(searchEnterNameLabel);
		searchParamPanel.add(searchEnterNameText);

		// 上一页按钮
		LastPageButton = new JButton(ConstData.LASTPAGE);
		// 添加事件
		LastPageButton.addActionListener(this);
		// 初始设置不可编辑
		LastPageButton.setEnabled(false);
		// 下一页按钮
		NextPageButton = new JButton(ConstData.NEXTPAGE);
		// 添加事件
		NextPageButton.addActionListener(this);
		// 初始设置不可编辑
		NextPageButton.setEnabled(false);
		// 统计一共多少条数订单
		totalNumberLabel = new JLabel();
		// 分页面板
		PagePanel = new JPanel();
		// 设置流式布局
		PagePanel.setLayout(new FlowLayout());
		// 添加 上一页按钮，下一页按钮，到面板
		PagePanel.add(LastPageButton);
		PagePanel.add(NextPageButton);
		PagePanel.add(totalNumberLabel);
		// 申购订单，单选按钮
		searchType1 = new JRadioButton(ConstData.SEARCH_TYPE_1);
		// 默认选中
		searchType1.setSelected(true);
		// 添加事件
		searchType1.addActionListener(this);
		// 维护订单，单选按钮
		searchType2 = new JRadioButton(ConstData.SEARCH_TYPE_2);
		// 添加事件
		searchType2.addActionListener(this);
		// 单选分组
		searchTypeGroup = new ButtonGroup();
		// 添加到单选分组
		searchTypeGroup.add(searchType1);
		searchTypeGroup.add(searchType2);
		// 搜索按钮
		SeekOrderButton = new JButton(ConstData.SEEKORDER);
		// 添加事件
		SeekOrderButton.addActionListener(this);
		// 初始设置不可编辑
		SeekOrderButton.setEnabled(false);
		// 申购，维护面板
		SeekPanel = new JPanel();
		// 设置流式布局
		SeekPanel.setLayout(new FlowLayout());
		// // 添加 维护订单，单选按钮，申购订单，单选按钮，搜索按钮到面板
		SeekPanel.add(searchType1);
		SeekPanel.add(searchType2);
		SeekPanel.add(SeekOrderButton);
		// logo图片 标签
		ImageLogo = new JLabel();
		// 设置logo图片
		ImageLogo
				.setIcon(new ImageIcon(ConstData.CONFIG_PATH + ConstData.Logo));
		// 左上部分面板
		CheckinPanel = new JPanel();
		// 设置边框
		CheckinPanel.setBorder(new TitledBorder(new EtchedBorder()));
		// 设置位置和宽度，长度
		CheckinPanel.setBounds(0, 0, ConstData.CHECKIN_WIDTH,
				ConstData.CHECK_HEIGHT);
		// 设置表格布局，7行1列
		CheckinPanel.setLayout(new GridLayout(7, 1));
		// 添加帐号第一个面板
		CheckinPanel.add(OperatorPanel);
		// 添加登陆按钮第二个面板
		CheckinPanel.add(ButtonPanel);
		// 添加POS机器码第三个面板
		CheckinPanel.add(MachinePanel);
		// 添加烧机状态第四个面板
		CheckinPanel.add(SattusPanel);
		// 添加搜索第五个面板
		CheckinPanel.add(searchParamPanel);
		// 添加分页第六个面板
		CheckinPanel.add(PagePanel);
		// 添加申购，维护第七个面板
		CheckinPanel.add(SeekPanel);
		// 定义左边面板
		LogoPanel = new JPanel();
		// 设置位置，宽，长度
		LogoPanel.setBounds(ConstData.ORDER_WIDTH + 3 * ConstData.GAP, 0,
				ConstData.CHECKIN_WIDTH, ConstData.CHECK_HEIGHT + 500);
		// 添加左上部分面板
		LogoPanel.add(CheckinPanel);
		// 添加LOGO标签
		LogoPanel.add(ImageLogo);
		// 添加到主界面
		add(LogoPanel);
	}

	/**
	 * 客户端启动主方法
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		LOGGER.info("POS Client is runing");
		if ((args.length > 0)
				&& (args[0].equals("-h") || args[0].equals("-help"))) {
			System.out.println("usage: java SerialDemo [configuration File]");
			System.exit(1);
		}
		// 实例化主类
		MainView cMainView = new MainView();
		// 设置可见
		cMainView.setVisible(true);
		// 重画
		cMainView.repaint();
	}

	/**
	 * 关闭，退出，的時候需要登出，告诉服务器退出了
	 */
	private void shutdown() {
		if (custIdText.getText() != null && !"".equals(custIdText.getText())) {
			Map<String, Object> mapOut = new HashMap<String, Object>();
			mapOut.put("channelType", "2");// 登录类型为2：POS(POS机)）
			mapOut.put("custId", custIdText.getText());// 登录IP
			mapOut.put("loginToken", loginTokenText.getText());// 操作员客户号
			comToPosAccess.accessLoginOut(mapOut, ConfigPara);
		}
		System.exit(1);
	}

	/**
	 * 按钮触发事件 登录 配置服务器
	 * 
	 * @param e
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		// 登陆操作
		if (cmd.equals(ConstData.CHECKIN)) {
			if (OperatorText.getText().isEmpty()) {
				JOptionPane.showMessageDialog(null, "操作员不能为空", "错误:",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			if (PasswordText.getPassword().length == 0) {
				JOptionPane.showMessageDialog(null, "密码不能为空", "错误:",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			ConfigPara.setUserName(OperatorText.getText());
			ConfigPara.Save();
			CheckinStatus = Login();
			if (CheckinStatus) {
				SetStatus(ConstData.CKECKIN);
				CheckinButton.setEnabled(false);
				OperatorText.setEditable(false);
				PasswordText.setEditable(false);
				SeekOrderButton.setEnabled(true);
			} else {
				SetStatus(ConstData.CKECKIN_ERR);
			}
		} else if (cmd.equals(ConstData.CHECKCOM)) {
			ComPortChoice.removeAll();
			comToPOS.listPortChoices(ComPortChoice);
		} else if (cmd.equals(ConstData.PRINT)) {

			// 获取维护订单编号：15位终端编号
			String hideValue = deviceNoText.getText(); // 分割维护订单编号：15位终端编号
			String[] hideValueArray = hideValue.split(":");
			printPosNo(hideValueArray[1]);

		} else if (cmd.equals(ConstData.CONFIG)) {
			// 配置参数界面
			ConfigAccessFrame cfg;
			cfg = new ConfigAccessFrame(this);
			cfg.setVisible(true);
			this.setEnabled(false);
		} else if (cmd.equals(ConstData.SEARCH_TYPE_1)
				|| cmd.equals(ConstData.SEARCH_TYPE_2)) {
			NextPageButton.setEnabled(false);
			LastPageButton.setEnabled(false);
		} else if (cmd.equals(ConstData.LASTPAGE)) {
			// 搜索
			StatusTEXT.setText("");
			// 上一页
			byte searchType = 0;
			if (searchType1.isSelected()) {
				searchType = ConstData.COMMAND_GET_NEW_MACHINE_LIST;
			} else if (searchType2.isSelected()) {
				searchType = ConstData.COMMAND_REPAIR_GET_NEW_MACHINE_LIST;
			}
			String resNo = searchResNoText.getText();
			String orderNo = searchEnterNameText.getText();
			POSCode.setText("");
			currentPage--;
			// 查询订单
			Map<String, Object> orderMap = new HashMap<String, Object>();
			orderMap.put("resNo", resNo);// 互生号
			orderMap.put("custName", orderNo);// 客户名称，也就是企业名称
			orderMap.put("pageSize", ConfigPara.getPageNumber());// 分页每页行数
			orderMap.put("curPage", currentPage);// 第几页
			orderMap.put("custId", custIdText.getText());// 操作员客户号
			orderMap.put("loginToken", loginTokenText.getText());// 操作员客户号
			orderMap.put("channelType", "2");// 登录类型为1，表示渠道类型，WEB（web端）
			if (GetMachineList(searchType, OrderTable, orderMap, "") == 0) {
				ColseOrder();
			} else {
				NextPageButton.setEnabled(true);
				if (currentPage == 1) {
					LastPageButton.setEnabled(false);
				}
			}
		} else if (cmd.equals(ConstData.NEXTPAGE)) {
			// 搜索
			StatusTEXT.setText("");
			// 下一页
			byte searchType = 0;
			if (searchType1.isSelected()) {
				searchType = ConstData.COMMAND_GET_NEW_MACHINE_LIST;
			} else if (searchType2.isSelected()) {
				searchType = ConstData.COMMAND_REPAIR_GET_NEW_MACHINE_LIST;
			}
			String resNo = searchResNoText.getText();
			String orderNo = searchEnterNameText.getText();
			POSCode.setText("");
			currentPage++;
			int OrderNum;
			// 查询订单
			Map<String, Object> orderMap = new HashMap<String, Object>();
			orderMap.put("resNo", resNo);// 互生号
			orderMap.put("custName", orderNo);// 客户名称，也就是企业名称
			orderMap.put("pageSize", ConfigPara.getPageNumber());// 分页每页行数
			orderMap.put("curPage", currentPage);// 第几页
			orderMap.put("custId", custIdText.getText());// 操作员客户号
			orderMap.put("loginToken", loginTokenText.getText());// 操作员客户号
			orderMap.put("channelType", "2");// 登录类型为1，表示渠道类型，WEB（web端）
			if ((OrderNum = GetMachineList(searchType, OrderTable, orderMap, "")) == 0) {
				ColseOrder();
			} else {
				LastPageButton.setEnabled(true);
				if (OrderNum <= currentPage
						* Integer.valueOf(ConfigPara.getPageNumber())) {
					NextPageButton.setEnabled(false);
				}
			}
		} else if (cmd.equals(ConstData.SEEKORDER)) {
			// 搜索
			StatusTEXT.setText("");
			byte searchType = 0;
			if (searchType1.isSelected()) {
				searchType = ConstData.COMMAND_GET_NEW_MACHINE_LIST;
				IfRepare = false;
			} else if (searchType2.isSelected()) {
				searchType = ConstData.COMMAND_REPAIR_GET_NEW_MACHINE_LIST;
				IfRepare = true;
			}
			String resNo = searchResNoText.getText();
			String orderNo = searchEnterNameText.getText();
			POSCode.setText("");
			currentPage = 1;
			int OrderNum;
			// 查询订单
			Map<String, Object> orderMap = new HashMap<String, Object>();
			/*
			 * orderMap.put("resNo", resNo);// 互生号 orderMap.put("custName",
			 * orderNo);// 客户名称，也就是企业名称
			 */
			orderMap.put("resNo", resNo);// 互生号
			orderMap.put("custName", orderNo);// 客户名称，也就是企业名称
			orderMap.put("pageSize", ConfigPara.getPageNumber());// 分页每页行数
			orderMap.put("curPage", currentPage);// 第几页
			orderMap.put("custId", custIdText.getText());// 操作员客户号
			orderMap.put("loginToken", loginTokenText.getText());// 操作员客户号
			orderMap.put("channelType", "2");// 登录类型为1，表示渠道类型，WEB（web端）
			if ((OrderNum = GetMachineList(searchType, OrderTable, orderMap, "")) == 0) {
				ColseOrder();
			} else {
				LastPageButton.setEnabled(false);
				if (OrderNum > currentPage
						* Integer.valueOf(ConfigPara.getPageNumber()))
					NextPageButton.setEnabled(true);
				else
					NextPageButton.setEnabled(false);
			}

		}
	}

	/**
	 * 打印互生号+终端编号
	 * 
	 * @param posNo
	 */
	public void printPosNo(String posNo) {
		// 打印功能，要是是打印11位互生号+4位终端编号
		PrintPosNo.printEntPosNo(posNo);
	}

	/**
	 * 关闭订单列表
	 */
	void ColseOrder() {
		LastPageButton.setEnabled(false);
		NextPageButton.setEnabled(false);
		currentPage = -1;
	}

	/**
	 * 登录方法
	 * 
	 * @return
	 */
	Boolean Login() {
		SetStatus(ConstData.CKECKINING);
		setEnabled(false);
		String token = "d925b2047dded345";
		Map<String, Object> mapLogin = new HashMap<String, Object>();
		mapLogin.put("channelType", "2");// 登录类型为1，表示渠道类型，WEB（web端）
		mapLogin.put("loginIp", PosUtil.getLocalIP());// 登录IP
		mapLogin.put("userName", OperatorText.getText());// 用户
		mapLogin.put("resNo", ConfigPara.getResNo());// 互生号
		mapLogin.put("randomToken", token);// 随机randomToken
		String pwd = String.valueOf(PasswordText.getPassword());
		pwd = StringEncrypt.encrypt(pwd, token);
		mapLogin.put("loginPwd", pwd);// 密码
		// 客户号需要缓存到本地
		String custId = null;
		String loginToken = null;
		boolean rc = false;
		Map<String, Object> loginMap = comToPosAccess.accessLogin(mapLogin,
				ConfigPara);
		setEnabled(true);
		if (loginMap != null && !"null".equals(loginMap)) {
			if (loginMap.get("flag") != null) {
				rc = (boolean) loginMap.get("flag");
				if (rc) {
					// 获取登陆的操作员客户号
					custId = (String) loginMap.get("custId");
					// 获取登陆的Token
					loginToken = (String) loginMap.get("loginToken");
					// 设置到隐藏文本框
					custIdText.setText(custId);
					// 设置到隐藏文本框
					loginTokenText.setText(loginToken);
				} else {
					// 接入层异常
					JOptionPane.showMessageDialog(null,
							loginMap.get("resultDesc"), "错误:",
							JOptionPane.ERROR_MESSAGE);
				}
			} else {
				// 接入层没有响应
				JOptionPane.showMessageDialog(null, "未知错误!", "错误:",
						JOptionPane.ERROR_MESSAGE);
			}
		} else {
			// 接入层没有响应
			JOptionPane.showMessageDialog(null, "未知错误!", "错误:",
					JOptionPane.ERROR_MESSAGE);
		}
		return rc;
	}

	/**
	 * 主界面关闭
	 * 
	 * @author lyh 2015-12-5
	 */
	class CloseHandler extends WindowAdapter {
		MainView sd;

		public CloseHandler(MainView sd) {
			this.sd = sd;
		}

		public void windowClosing(WindowEvent e) {
			LOGGER.info("POS Client is down");
			sd.shutdown();
		}
	}

	/**
	 * COM端口选择触发
	 */

	public void itemStateChanged(ItemEvent e) {
		if (e.getItemSelectable() == ComPortChoice) {
			ConfigPara.setComPort(ComPortChoice.getSelectedItem().toString());
			ConfigPara.Save();
			return;
		}
	}

	class OrderClick implements MouseListener {
		/**
		 * 订单表选择触发 请求服务器POS机列表
		 */
		public void mouseClicked(MouseEvent e) {
			// 获取表格对象
			OrderTableRow = OrderTable.rowAtPoint(e.getPoint());
			boolean flag = true;
			// 新增处理，表格模型
			DefaultTableModel tableModel = (DefaultTableModel) OrderTable
					.getModel();
			// 列名称
			String colName = tableModel.getColumnName(5);
			// 列名称
			if (colName.equals("详情")) {
				flag = false;
			}
			// 列表头是：终端编号，并且选中在是第六列
			if (colName.equals("终端编号") && OrderTable.isColumnSelected(5)) {
				// 请求，获取数据
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("entCustId",
						OrderTable.getValueAt(OrderTable.getSelectedRow(), 3)); // 企业客户号，也就是企业编号
				map.put("confNo",
						OrderTable.getValueAt(OrderTable.getSelectedRow(), 0));// 配置单号
				map.put("categoryCode", CategoryCode.getCode("P_POS"));// 工具类别
				map.put("custId", custIdText.getText());// 操作员客户号
				map.put("loginToken", loginTokenText.getText());// 登陆的Token
				map.put("channelType", "2");// 登录类型为2 POS机
				flag = false;
				Object[] str1 = null;
				Object[] str2 = null;
				// 请求获取终端编号
				Map<String, Object> maps = comToPosAccess.accessOrderEntNo(map,
						ConfigPara);
				if (maps != null) {
					if (maps.get("userNo") != null) {
						str1 = (Object[]) maps.get("userNo");
					}
					if (maps.get("noUserNo") != null) {
						str2 = (Object[]) maps.get("noUserNo");
					}
				}
				// 打开终端编号界面
				PosNoFrame cfg = new PosNoFrame(MainView.this, str1, str2);
				cfg.setVisible(true);
				MainView.this.setEnabled(false);
				/**
				 * 如果未烧机数量为0则返回12.4修改
				 */
				int number = (int) OrderTable.getValueAt(OrderTableRow, 4);
				if (number <= 0) {
					LOGGER.info("No Mathine to be burn");
					return;
				}
				LOGGER.info("user select OrderNo = "
						+ (String) OrderTable.getValueAt(OrderTableRow, 0));
			} else if (colName.equals("详情") && OrderTable.isColumnSelected(5)) {
				// 获取清单列表数据
				Map<String, Object> dataMap = new HashMap<String, Object>();
				dataMap.put("confNo",
						OrderTable.getValueAt(OrderTable.getSelectedRow(), 1));
				dataMap.put("custId", custIdText.getText());// 操作员客户号
				dataMap.put("loginToken", loginTokenText.getText());// 操作员客户号
				dataMap.put("channelType", "2");// 登录类型为2，pos机
				List<AfterDeviceDetail> listResult = null;
				try {
					// 查询维护配置单
					listResult = comToRepairAccess.accessRepareDetailOrder(
							dataMap, ConfigPara);
					DetailConfigFrame cfg = new DetailConfigFrame(
							MainView.this, listResult);
					cfg.setVisible(true);
					MainView.this.setEnabled(false);
					flag = false;
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
			// 点击表格行，进行烧机
			if (flag) {
				if (JOptionPane.showConfirmDialog(null, "开始烧机，订单号："
						+ (String) OrderTable.getValueAt(OrderTableRow, 1),
						"提示:请将POS机调整为下载状态", JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {
					return;
				}
				// 重新烧，则需要设置不可见
				printButton.setEnabled(false);
				POSCode.setText("");
				setEnabled(false);
				int err;
				// 获取机器码
				err = comToPOS.GetPOSNO();
				if (err == 1) {
					SetStatus(ConstData.OPEN_SERIAL_ERR);
					setEnabled(true);
				} else if (err == 2) {
					SetStatus(ConstData.WRITE_SERIAL_ERR);
					setEnabled(true);
				} else {
					SetStatus(ConstData.GET_MACHINENO);
					setEnabled(true);
				}
			}
		}

		public void mousePressed(MouseEvent e) {
		}

		public void mouseReleased(MouseEvent e) {
		}

		public void mouseEntered(MouseEvent e) {
		}

		public void mouseExited(MouseEvent e) {
		}

	}

	/**
	 * 获取维护配置单pos机器码
	 * 
	 * @param deviceNo
	 *            15位终端编号
	 */
	public void getRepairePosNo(String deviceNo) {
		POSCode.setText("");
		setEnabled(false);
		int err;
		// 15位终端编号设置到文本框
		deviceNoText.setText(deviceNo);
		// 获取维护机器码
		err = comToPOS.GetPOSNO();
		if (err == 1) {
			SetStatus(ConstData.OPEN_SERIAL_ERR);
		} else if (err == 2) {
			SetStatus(ConstData.WRITE_SERIAL_ERR);
		} else {
			SetStatus(ConstData.GET_MACHINENO);
			setEnabled(false);
		}
	}

	/**
	 * 显示当前状态
	 */
	void SetStatus(String status) {
		StatusTEXT.setText(status);
	}

	public void GotMathineNO(String MachineNO) {
		POSCode.setText(MachineNO);
		SetStatus(ConstData.GET_PMK);
		byte[] PMK = null;
		String terminalNo = null;// 15终端编号 ,11企业互生号+4终端编号
		String entResNo = null;// 11企业互生号
		String deviceNo = null;// 4设备序列号，终端编号
		// 请求参数Map
		Map<String, Object> mapPmk = new HashMap<String, Object>();
		if (IfRepare) {
			// 维护配置单获取秘钥请求参数
			// 获取维护订单编号：15位终端编号
			String hideValue = deviceNoText.getText();
			// 分割维护订单编号：15位终端编号
			String[] hideValueArray = hideValue.split(":");
			// 获取4位终端编号
			deviceNo = hideValueArray[1].substring(11);
			mapPmk.put("confNo",
					(String) OrderTable.getValueAt(OrderTableRow, 0));// 配置单号
			mapPmk.put("entCustId",
					(String) OrderTable.getValueAt(OrderTableRow, 3));// 企业编码
			mapPmk.put("operCustId", custIdText.getText());// 操作员客户号
			mapPmk.put("machineNo", MachineNO);// 机器码
			mapPmk.put("newDeviceSeqNo", MachineNO);// 新机器码
			mapPmk.put("deviceNo", deviceNo);// 4位终端编号，这里也叫序列号
			mapPmk.put("isNewVersion", ConfigPara.ifNewVersion());// 是否新版本
			mapPmk.put("custId", custIdText.getText());// 操作员客户号
			mapPmk.put("loginToken", loginTokenText.getText());// 操作员客户号
			mapPmk.put("channelType", "2");// 登录类型为2 pos机
			// 请求获取维护配置单秘钥
			Map<String, Object> mapPmkResult = comToRepairAccess
					.accessGetReparPmk(mapPmk, ConfigPara);
			if (mapPmkResult != null) {
				PMK = (byte[]) mapPmkResult.get("pmk");
				entResNo = (String) mapPmkResult.get("entResNo");// 11 企业互生号
				deviceNo = (String) mapPmkResult.get("posNo");// 4 终端编号
				terminalNo = entResNo + deviceNo;// 15终端编号
			}
		} else {
			// 申购配置单获取秘钥请求参数
			mapPmk.put("confNo",
					(String) OrderTable.getValueAt(OrderTableRow, 0));// 配置单号
			mapPmk.put("entCustId",
					(String) OrderTable.getValueAt(OrderTableRow, 3));// 企业编码
			mapPmk.put("operCustId", custIdText.getText());// 操作员客户号
			mapPmk.put("machineNo", MachineNO);// 机器码
			mapPmk.put("deviceNo", MachineNO);// 这个参数在这里其实没用到，但是接口定义了
			mapPmk.put("isNewVersion", ConfigPara.ifNewVersion());// 是否新版本
			mapPmk.put("custId", custIdText.getText());// 操作员客户号
			mapPmk.put("loginToken", loginTokenText.getText());// 登陆Token
			mapPmk.put("channelType", "2");// 登录类型为2 pos机
			// 请求获取申购秘钥
			Map<String, Object> mapPmkResult = comToPosAccess.accessGetPmk(
					mapPmk, ConfigPara);
			if (mapPmkResult != null) {
				PMK = (byte[]) mapPmkResult.get("pmk");
				if (PMK != null) {
					terminalNo = (String) mapPmkResult.get("deviceNo");// 15终端编号
					entResNo = terminalNo.substring(0, 11);// 11 企业互生号
					deviceNo = terminalNo.substring(11);// 4 终端编号
				}
			}
			if (terminalNo != null) {
				deviceNoText.setText((String) OrderTable.getValueAt(
						OrderTableRow, 1) + ":" + terminalNo);
			}
		}
		if (PMK == null) {
			comToPOS.CloseCom();
			setEnabled(true);
			SetStatus(ConstData.GET_PMK_ERR);
			LOGGER.error("Get PMK from server error!!");
			return;
		}
		// PMK和企业编号
		byte[] tempPmk = new byte[ConstData.PMK_LEN];
		// 记录获取到的16位PMK
		System.arraycopy(PMK, 1, tempPmk, 0, ConstData.PMK_LEN);
		// bytePMK转化成字符串,用于后面修改POS机状态
		String tempPmkStr = HexBin.encode(tempPmk);
		int countryNumber = PMK[ConstData.OFF_COUNTRY_SVR] & 0xff;
		LOGGER.debug("countryNumber = " + countryNumber);
		int offPointNumber = ConstData.OFF_COUNTRY_SVR + 1 + countryNumber
				* (ConstData.COUNTRY_INDEX_LEN + ConstData.COUNTRY_NO_LEN)
				+ ConstData.POINT_CASH_LEN + ConstData.HUSHENG_CASH_LEN;
		int pointNumber = PMK[offPointNumber] & 0xff;
		LOGGER.debug("pointNumber = " + pointNumber);
		if (offPointNumber + 1 + pointNumber * ConstData.POINT_LEN != PMK.length) {
			setEnabled(true);
			SetStatus(ConstData.GET_PMK_ERR);
			LOGGER.error("Error countryNumber in PMK from server !!");
			return;
		}
		StringBuffer conarray2 = new StringBuffer();
		for (int w = 0; w < PMK.length; w++) {
			conarray2.append(PMK[w] + ",");
		}
		LOGGER.debug("Writer Before Data:" + conarray2);
		// 长度计算
		int SendLen = ConstData.WRITE_COMMAND.length // 命令字
				+ 1 // 密码序号
				+ ConstData.PMK_LEN
				+ ConstData.ENT_NO_LEN
				+ ConstData.POS_INDEX_LEN // 现在是2位 将来改4位
				+ 2// 数据长度
				+ ConstData.FLAG.length
				+ ConstData.BAS_INFO_VER_LEN
				+ ConstData.CURRUNCY_INFO_VER_LEN
				+ ConstData.COUNTRY_INFO_VER_LEN
				+ ConstData.POINT_INFO_VER_LEN
				+ ConstData.ENT_NAME_LEN
				+ ConstData.ENT_TYPE_POS_LEN
				+ ConstData.ENT_TEL_LEN
				+ ConstData.ENT_URL_LEN
				+ ConstData.CURRUNCY_NUMBER
				* (ConstData.CURRUNCY_NO_LEN + ConstData.CURRUNCY_NAME_LEN)
				+ ConstData.POINT_CASH_LEN
				+ ConstData.HUSHENG_CASH_LEN
				+ ConstData.POINT_NUMBER * ConstData.POINT_LEN
				+ 2// 国家编码个数
				+ countryNumber
				* (ConstData.COUNTRY_INDEX_LEN + ConstData.COUNTRY_NO_LEN);

		byte[] Send = new byte[SendLen];
		int i = 0; // 发送到POS机的数据指针
		int j = 1; // 从服务器接收PMK指针
		// 命令字
		i += ComToPos.InsertByte(Send, i, ConstData.WRITE_COMMAND);
		// 密码序号
		Send[i] = 0;
		i++;
		// PMK和企业编号
		System.arraycopy(PMK, j, Send, i, ConstData.PMK_LEN
				+ ConstData.ENT_NO_LEN);
		i += ConstData.PMK_LEN + ConstData.ENT_NO_LEN;
		j += ConstData.PMK_LEN + ConstData.ENT_NO_LEN;
		// POS机编码
		PosIndex = "" + (char) PMK[j] + (char) PMK[j + 1] + (char) PMK[j + 2]
				+ (char) PMK[j + 3];
		LOGGER.debug("PosIndex----------------------- = " + PosIndex);

		System.arraycopy(PMK, j, Send, i, 4);
		i += 4;
		j += 4;
		// 计算数据包长度
		int len = ConstData.FLAG.length + ConstData.BAS_INFO_VER_LEN
				+ ConstData.CURRUNCY_INFO_VER_LEN
				+ ConstData.COUNTRY_INFO_VER_LEN
				+ ConstData.POINT_INFO_VER_LEN
				+ ConstData.ENT_NAME_LEN
				+ ConstData.ENT_TYPE_POS_LEN
				+ ConstData.ENT_TEL_LEN
				+ ConstData.ENT_URL_LEN
				+ ConstData.CURRUNCY_NUMBER
				* (ConstData.CURRUNCY_NO_LEN + ConstData.CURRUNCY_NAME_LEN) // 货币
				+ ConstData.POINT_CASH_LEN + ConstData.HUSHENG_CASH_LEN
				+ ConstData.POINT_NUMBER * ConstData.POINT_LEN + 2
				+ countryNumber
				* (ConstData.COUNTRY_INDEX_LEN + ConstData.COUNTRY_NO_LEN); // 国家

		i += ComToPos.InsertByte(Send, i, ComToPos.GetBCD2(len));
		// 标识
		i += ComToPos.InsertByte(Send, i, ConstData.FLAG);
		len = ConstData.BAS_INFO_VER_LEN + ConstData.CURRUNCY_INFO_VER_LEN
				+ ConstData.COUNTRY_INFO_VER_LEN + ConstData.POINT_INFO_VER_LEN;
		// 基础信息版本号、货币代码版本号、国家代码版本号、
		System.arraycopy(PMK, j, Send, i, len);
		i += len;
		j += len;
		// 企业名称
		System.arraycopy(PMK, j, Send, i, ConstData.ENT_NAME_LEN);
		i += ConstData.ENT_NAME_LEN;
		j += ConstData.ENT_NAME_LEN;
		// 企业类型
		byte[] tmp;
		try {
			tmp = PosUtil.GetType(PMK[j]).getBytes("gbk");
		} catch (UnsupportedEncodingException e) {
			LOGGER.error("" + e);
			SetStatus(ConstData.OPEN_SERIAL_ERR);
			setEnabled(true);
			return;
		}
		ComToPos.InsertByte(Send, i, tmp);
		i += ConstData.ENT_TYPE_POS_LEN;
		j += ConstData.ENT_TYPE_LEN;
		// 电话
		ComToPos.InsertByte(Send, i, ConfigPara.getTelephoneNo());
		i += ConstData.ENT_TEL_LEN;
		// 网址
		ComToPos.InsertByte(Send, i, ConfigPara.getUrl());
		i += ConstData.ENT_URL_LEN;
		// 货币
		int pointposition = i;// pointposition 指向积分信息的位置
		i += ConstData.POINT_CASH_LEN + ConstData.HUSHENG_CASH_LEN
				+ ConstData.POINT_NUMBER * ConstData.POINT_LEN;

		for (len = 0; len < ConstData.CURRUNCY_NUMBER; len++) {
			j++;// 跳过货币序号
			System.arraycopy(PMK, j, Send, i, ConstData.CURRUNCY_NO_LEN);
			i += ConstData.CURRUNCY_NO_LEN;
			j += ConstData.CURRUNCY_NO_LEN;
			int k;
			for (k = 0; k < ConstData.CURRUNCY_NAME_LEN; k++) {
				if (PMK[j + k] == 0) {
					break;
				}
			}
			int kk;
			for (kk = 0; kk < k; kk++) {
				PMK[j + ConstData.CURRUNCY_NAME_LEN - kk - 1] = PMK[j + k - kk
						- 1];
			}
			for (; kk < ConstData.CURRUNCY_NAME_LEN; kk++) {
				PMK[j + ConstData.CURRUNCY_NAME_LEN - kk - 1] = 0;
			}
			System.arraycopy(PMK, j, Send, i, ConstData.CURRUNCY_NAME_LEN);
			i += ConstData.CURRUNCY_NAME_LEN;
			j += ConstData.CURRUNCY_NAME_LEN;
		}
		// 先写国家信息
		// 国家代码与编码个数
		i += ComToPos.InsertByte(Send, i, ComToPos.GetBCD2(countryNumber));
		j += 1; // 个数
		// 国家信息
		System.arraycopy(PMK, j, Send, i, countryNumber
				* (ConstData.COUNTRY_INDEX_LEN + ConstData.COUNTRY_NO_LEN));
		j += countryNumber
				* (ConstData.COUNTRY_INDEX_LEN + ConstData.COUNTRY_NO_LEN);

		// 写积分信息
		i = pointposition;
		ComToPos.InsertByte(Send, i,
				PosUtil.GetType(PMK, j, ConstData.POINT_CASH_LEN));
		i += ConstData.POINT_CASH_LEN;
		j += ConstData.POINT_CASH_LEN;
		ComToPos.InsertByte(Send, i,
				PosUtil.GetType(PMK, j, ConstData.POINT_CASH_LEN));
		i += ConstData.HUSHENG_CASH_LEN;
		j += ConstData.HUSHENG_CASH_LEN + 1;
		//
		StringBuffer conarrayX = new StringBuffer();
		for (int x = 0; x < Send.length; x++) {
			conarrayX.append(Send[x] + ",");
		}
		LOGGER.debug("Writer Before DataX:" + conarrayX);

		for (len = 0; len < pointNumber; len++) {
			ComToPos.InsertByte(Send, i,
					PosUtil.GetType(PMK, j, ConstData.POINT_LEN));
			i += ConstData.POINT_LEN;
			j += ConstData.POINT_LEN;
		}
		//
		StringBuffer conarrayX1 = new StringBuffer();
		for (int x1 = 0; x1 < Send.length; x1++) {
			conarrayX1.append(Send[x1] + ",");
		}
		LOGGER.debug("Writer Before DataX1:" + conarrayX1);

		for (len = 0; len < (ConstData.POINT_NUMBER - pointNumber)
				* ConstData.POINT_LEN; i++) {
			Send[i + len] = '0';
		}
		//
		StringBuffer conarrayX2 = new StringBuffer();
		for (int x2 = 0; x2 < Send.length; x2++) {
			conarrayX2.append(Send[x2] + ",");
		}
		LOGGER.debug("Writer Before DataX2:" + conarrayX2);
		// 写入秘钥数据

		int err = comToPOS.WritePos(Send);
		if (err == 1) {
			SetStatus(ConstData.OPEN_SERIAL_ERR);
			setEnabled(true);
		} else if (err == 2) {
			SetStatus(ConstData.WRITE_SERIAL_ERR);
			setEnabled(true);
		} else {
			SetStatus(ConstData.WRITE_PMK);
		}

		// 修改企业关联关系，POS启用状态，的请求参数map
		Map<String, Object> map = new HashMap<String, Object>();
		// 修改企业关联关系，POS启用状态
		if (IfRepare) {
			// 修改维护企业关联关系，POS启用状态
			// 获取维护订单编号：15位终端编号
			String hideValue = deviceNoText.getText();
			// 分割： 维护订单编号：15位终端编号
			String[] hideValueArray = hideValue.split(":");
			// 获取维护订单号
			String orderNo = hideValueArray[0];
			// 11企业互生号
			map.put("entResNo", entResNo);
			// POS机器码，旧机器码，对于重新配置，返修来说，新旧机器码是一样的，但是对于换货来说是，不一样的
			map.put("deviceSeqNo", hideValueArray[2]);
			// 设备状态1 启用、2 停用、3 返修、4冻结 备注
			map.put("deviceStatus", "1");
			// 客户号，企业编码
			map.put("entCustId",
					(String) OrderTable.getValueAt(OrderTableRow, 3));
			// 配置单号
			map.put("configNo",
					(String) OrderTable.getValueAt(OrderTableRow, 1));
			// 新设备机器码
			map.put("newDeviceSeqNo", POSCode.getText());
			// 维护订单号
			map.put("orderNo", orderNo);
			// 15终端编号
			map.put("terminalNo", terminalNo);
			// pmk
			map.put("pmk", tempPmkStr);
			// 类别POS机
			map.put("categoryCode", CategoryCode.getCode("P_POS"));
			// 操作员客户号
			map.put("operatorNo", custIdText.getText());
			// 操作员客户号
			map.put("custId", custIdText.getText());
			// 登陆Token
			map.put("loginToken", loginTokenText.getText());
			// 登录类型为2pos机
			map.put("channelType", "2");
		} else {
			// 11企业互生号
			map.put("entResNo", entResNo);
			// 4 设备序列号，终端编号
			map.put("deviceNo", deviceNo);
			// 设备状态1 启用、2 停用、3 返修、4冻结 备注
			map.put("status", "1");
			// 客户号，企业编码
			map.put("entCustId",
					(String) OrderTable.getValueAt(OrderTableRow, 3));
			// 配置单号
			map.put("confNo", (String) OrderTable.getValueAt(OrderTableRow, 0));
			// 15终端编号
			map.put("terminalNo", terminalNo);
			// pmk
			map.put("pmk", tempPmkStr);
			// 操作员客户号
			map.put("custId", custIdText.getText());
			// 操作员客户号
			map.put("loginToken", loginTokenText.getText());
			// 登录类型为2 POS机
			map.put("channelType", "2");
		}
		// 修改状态，响应客户端界面
		updatePosStatus(map);
	}

	/**
	 * 回调函数 烧POS机成功
	 */
	public void ClosePOSOK() {
		SetStatus(ConstData.INFO_TO_SERVER);
		// 获取烧机标志
		String flag = posResultText.getText();
		if (flag != null && flag.equals("true")) {
			LOGGER.info("inform POS Client Server OK!");
			SetStatus(ConstData.DONE);
			printButton.setEnabled(true);
			if (!IfRepare) {
				int num = (int) OrderTable.getValueAt(OrderTableRow, 4);
				num--;
				if (num == 0) {
					// 灌密钥成功删除当前行
					DefaultTableModel tableModel = (DefaultTableModel) OrderTable
							.getModel();
					tableModel.removeRow(OrderTableRow);
				} else {
					OrderTable.setValueAt(num, OrderTableRow, 4);
				}
			} else {
				// 灌密钥成功删除当前行
				DefaultTableModel tableModel = (DefaultTableModel) OrderTable
						.getModel();
				tableModel.removeRow(OrderTableRow);
			}
		} else {
			SetStatus(ConstData.INFO_TO_SERVER_ERR);
			LOGGER.error("inform POS Client Server error!");
		}
		setEnabled(true);
	}

	/**
	 * 烧POS机成功,修改企业关联关系，POS机启用状态
	 */
	public void updatePosStatus(Map<String, Object> map) {
		SetStatus(ConstData.INFO_TO_SERVER);
		posResultText.setText("");
		if ((IfRepare) ? comToRepairAccess.accessUpdateRepairPosStatus(map,
				ConfigPara) : comToPosAccess.accessUpdatePosStatus(map,
				ConfigPara)) {
			LOGGER.info("inform POS Client Server OK!");
			// 设置烧机成功标志
			posResultText.setText("true");
		} else {
			SetStatus(ConstData.INFO_TO_SERVER_ERR);
			LOGGER.error("inform POS Client Server error!");
			return;
		}
		// 界面响应
		setEnabled(true);
	}

	/**
	 * 获取订单列表
	 * 
	 * @param type
	 * @param table
	 * @param PageNo
	 * @param resNo
	 * @param orderNo
	 * @param roleId
	 * @param operator
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int GetMachineList(byte type, JTable table, Map<String, Object> map,
			String operator) {
		// 清除旧数据
		DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
		int number = tableModel.getRowCount();
		int i = 0;
		for (i = 0; i < number; i++) {
			tableModel.removeRow(0);
		}
		List<SecretKeyOrderInfoPage> list = new ArrayList<SecretKeyOrderInfoPage>();
		List<SecretKeyOrderInfoPage> list2 = new ArrayList<SecretKeyOrderInfoPage>();
		Integer count = 0;
		@SuppressWarnings("unused")
		String typestr = null;
		System.out.println(new String(new byte[] { type }));
		if (type == 2) {
			typestr = "2";
			tableModel.setColumnIdentifiers(ConstData.ORDER_HANDERS);
			// 设置列宽
			TableColumn Column = table.getColumnModel().getColumn(0);
			Column.setPreferredWidth(ConstData.TABLECOL0LEN);
			Column = table.getColumnModel().getColumn(1);
			Column.setPreferredWidth(ConstData.TABLECOL1LEN);
			Column = table.getColumnModel().getColumn(2);
			Column.setPreferredWidth(ConstData.TABLECOL2LEN);
			Column = table.getColumnModel().getColumn(3);
			Column.setPreferredWidth(ConstData.TABLECOL3LEN);
			Column = table.getColumnModel().getColumn(4);
			Column.setPreferredWidth(ConstData.TABLECOL4LEN);
			Column = table.getColumnModel().getColumn(5);
			Column.setPreferredWidth(ConstData.TABLECOL5LEN);
			table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			try {
				ComToAccess comToPosAccess = new ComToAccess();
				Map<String, Object> map1 = comToPosAccess.accessOrder(map,
						ConfigPara);
				totalNumberLabel.setText("");
				if (map1 != null) {
					count = (Integer) map1.get("count");
					if (count != null && count == 0) {
						JOptionPane.showMessageDialog(null, "没有申购配置单数据!",
								"提示:", JOptionPane.ERROR_MESSAGE);
					} else if (count > 0) {
						list = (List<SecretKeyOrderInfoPage>) map1.get("data");
						totalNumberLabel.setText("一共：" + count + "条");
						for (i = 0; i < list.size(); i++) {
							SecretKeyOrderInfoPage data = list.get(i);
							tableModel.addRow(new Object[] { data.getConfNo(),
									data.getOrderNo(), data.getCustName(),
									data.getEntCustId(), data.getConfingNum(),
									"详情" });
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			typestr = "7";
			tableModel.setColumnIdentifiers(ConstData.ORDER_HANDERSREPAIR);
			try {
				ComToRepairAccess comToRepairAccess = new ComToRepairAccess();
				Map<String, Object> map2 = comToRepairAccess.accessRepareOrder(
						map, ConfigPara);
				totalNumberLabel.setText("");
				if (map2 != null) {
					list2 = (List<SecretKeyOrderInfoPage>) map2.get("data");
					count = (int) map2.get("count");
					if (count == 0) {
						JOptionPane.showMessageDialog(null, "没有售后配置单数据!",
								"提示:", JOptionPane.ERROR_MESSAGE);
					} else if (count > 0) {
						totalNumberLabel.setText("一共：" + count + "条");
						for (i = 0; i < list2.size(); i++) {
							SecretKeyOrderInfoPage data = list2.get(i);
							tableModel.addRow(new Object[] { data.getOrderNo(),
									data.getConfNo(), data.getCustName(),
									data.getEntCustId(), data.getConfingNum(),
									"清单列表" });
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return count;
	}

	/**
	 * POS通讯错误
	 */
	public void POSCommunicationErr() {
		setEnabled(true);
		SetStatus(ConstData.POS_ERROR);
	}

	/**
	 * 关闭POS通讯
	 */
	public void POSWriteOK() {
		SetStatus(ConstData.CLOSE_POS);
	}

	/**
	 * 关闭POS通讯失败
	 */
	public void POSWriteCloseErr() {
		setEnabled(true);
		SetStatus(ConstData.CLOSE_POS_ERR);
	}

	/**
	 * 超时退出
	 */
	public void POSTimeOut() {
		setEnabled(true);
		SetStatus(ConstData.STR_TIME_OUT);
	}

}
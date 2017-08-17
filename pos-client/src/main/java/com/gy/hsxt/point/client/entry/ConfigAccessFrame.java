package com.gy.hsxt.point.client.entry;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import com.gy.hsxt.point.client.util.ConstData;



/**
 * Simple to Introduction
 * 
 * @category Simple to Introduction
 * @projectName WritePOS
 * @package .ConfigServer.java
 * @className ConfigServer
 * @description 接入层配置窗口
 * @author fandi
 * @createDate 2014-8-7 上午11:43:50
 * @updateUser liyh
 * @updateDate 2015-12-27 上午11:43:50
 * @updateRemark 说明本次修改内容
 * @version V3
 */
@SuppressWarnings("serial")
public class ConfigAccessFrame extends JFrame implements ActionListener {
	// 接入IP地址标签
	private JLabel KeyServerLabel;
	// 接入端口标签
	private JLabel KeyServerPortLabel;
	// 电话标签
	private JLabel telLabel;
	// 网址标签
	private JLabel urlLabel;
	// 操作员互生号标签// lyh 2015.10.27
	private JLabel resNoLabel;
   //接入IP地址文本框
	private JTextField KeyServerText;
	//接入端口文本框
	private JTextField KeyServerPortText;
	//電話文本框
	private JTextField telText;
	//网址文本框
	private JTextField urlText;
	//操作员互生号文本框lyh 2015.10.27
	private JTextField resNoText;
   //保存按钮
	private JButton SaveButton;
	//退出按鈕
	private JButton ExitButton;
    //是否新版本复选框
	private JCheckBox ifNewCheckBox;
    //1上面头部面板
	private JPanel KeyServerPanel;
	//2中间参数面板
	private JPanel paraPanel;
	//3 复选框面板
	private JPanel CheckBoxPanel;
	//4下面按钮面板
	private JPanel ButtonPanel;
    //主页面
	private MainView Onwer;
 
	 /**
	  * 构造方法
	  * @param pOnwer
	  */
	public ConfigAccessFrame(MainView pOnwer) {
		Onwer = pOnwer;
		addWindowListener(new CloseHandler(this));
        //1上面
		KeyServerLabel = new JLabel(ConstData.IP_ADDRESS);
		KeyServerPortLabel = new JLabel(ConstData.PORT);
		telLabel = new JLabel(ConstData.TELEPHONE);
		urlLabel = new JLabel(ConstData.URL);
		//2中间参数
		resNoLabel = new JLabel(ConstData.RESNO);
		KeyServerText = new JTextField(15);
		KeyServerText.setText(Onwer.ConfigPara.getClientServer());
		KeyServerPortText = new JTextField(5);
		KeyServerPortText.setText("" + Onwer.ConfigPara.getClientPort());
		telText = new JTextField(25);
		telText.setText(Onwer.ConfigPara.getTelephoneNo());
		urlText = new JTextField(25);
		urlText.setText(Onwer.ConfigPara.getUrl());
		resNoText = new JTextField(25);
		resNoText.setText(Onwer.ConfigPara.getResNo());
        //3 复选按钮
		ifNewCheckBox = new JCheckBox("是否为新版本", Onwer.ConfigPara.ifNewVersion());
       //4 下面按鈕
		SaveButton = new JButton(ConstData.SAVE);
		SaveButton.addActionListener(this);
		ExitButton = new JButton(ConstData.EXIT);
		ExitButton.addActionListener(this);
         //1 上面面板
		KeyServerPanel = new JPanel();
		KeyServerPanel.setBorder(new TitledBorder(new EtchedBorder(),
				ConstData.KEY_SERVER));
		KeyServerPanel.setLayout(new FlowLayout());
		KeyServerPanel.add(KeyServerLabel);
		KeyServerPanel.add(KeyServerText);
		KeyServerPanel.add(KeyServerPortLabel);
		KeyServerPanel.add(KeyServerPortText);
		//2 中间参数面板
		paraPanel = new JPanel();
		paraPanel
		.setBorder(new TitledBorder(new EtchedBorder(), ConstData.PARA));
		paraPanel.setLayout(new FlowLayout());
		paraPanel.add(resNoLabel);
		paraPanel.add(resNoText);
		paraPanel.add(telLabel);
		paraPanel.add(telText);
		paraPanel.add(urlLabel);
		paraPanel.add(urlText);
		//3复选框面板
		CheckBoxPanel = new JPanel();
		CheckBoxPanel.add(ifNewCheckBox);
       //4下面按鈕面板
		ButtonPanel = new JPanel();
		ButtonPanel.setLayout(new FlowLayout());
		ButtonPanel.add(SaveButton);
		ButtonPanel.add(ExitButton);
		//设置大小
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(screenSize.width / 2 - ConstData.CONFIG_WIDTH / 2,
				screenSize.height / 2 - ConstData.CONFIG_HEIGHT / 2);
		setSize(ConstData.CONFIG_WIDTH, ConstData.CONFIG_HEIGHT);
		setResizable(false);
		//网格布局，4行1列
		setLayout(new GridLayout(4, 1));
		//四个面板添加到页面
		add(KeyServerPanel);
		add(paraPanel);
		add(CheckBoxPanel);
		add(ButtonPanel);
	}

	/**
	 * 按鈕响应事件
	 */
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		//保存按钮
		if (cmd.equals(ConstData.SAVE)) {
			if (Onwer.ConfigPara.setClientServer(KeyServerText.getText())
					&& Onwer.ConfigPara.setClientPort(Integer
							.parseInt(KeyServerPortText.getText()))) {
				Onwer.ConfigPara.SetIfNew(ifNewCheckBox.isSelected());
				Onwer.ConfigPara.setTelephoneNo(telText.getText());
				Onwer.ConfigPara.setUrl(urlText.getText());
				Onwer.ConfigPara.setResNo(resNoText.getText());
				Onwer.ConfigPara.Save();
			} else {
				JOptionPane.showMessageDialog(null, "数据错误，请检查！", "错误",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			//退出按鈕
		} else if (!cmd.equals(ConstData.EXIT)) {
			return;
		}
		ShutDown();
	}

	/**
	 * 关闭处理
	 * @author lyh
	 *
	 */
	class CloseHandler extends WindowAdapter {
       ConfigAccessFrame sd;
     public CloseHandler(ConfigAccessFrame sd) {
			this.sd = sd;
		}
	  public void windowClosing(WindowEvent e) {
			ShutDown();
		}
	}

  /**
   * 关闭
   */
	void ShutDown() {
		setVisible(false);
		dispose();
		Onwer.setEnabled(true);
		Onwer.setVisible(true);
	}

}

package com.gy.hsxt.point.client.entry;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import com.gy.hsxt.point.client.util.ConstData;

/**
 * 
 * 
 * @Package: com.gy.point.client
 * @ClassName: EnerNoFrame
 * @Description: TODO
 * 
 * @author: liyh
 * @date: 2015-10-26 下午2:38:28
 * @version V3.0
 */
@SuppressWarnings("serial")
public class PosNoFrame extends JFrame implements ActionListener {

	/*
	 * private JLabel KeyServerLabel; private JLabel KeyServerPortLabel;
	 */
	// 已使用标签
	private JLabel userJLable;
	// 未使用标签
	private JLabel noUserJLable;
	// 退出按鈕
	// private JButton ExitButton;
	// 终端编号面板
	private JPanel KeyServerPanel;
	// private JPanel paraPanel;
	// 按钮面板
	private JPanel ButtonPanel;
	// 主界面
	private MainView Onwer;

	/**
	 * 构造方法
	 * 
	 * @param pOnwer
	 * @param str1
	 * @param str2
	 */
	public PosNoFrame(MainView pOnwer, Object str1[], Object str2[]) {
		Onwer = pOnwer;
		addWindowListener(new CloseHandler(this));
		// ExitButton = new JButton(ConstData.EXIT);
		// ExitButton.addActionListener(this);
		// 已使用
		userJLable = new JLabel("已使用：");
		// 未使用
		noUserJLable = new JLabel("未使用：");
		// 終端编号面板
		KeyServerPanel = new JPanel();
		KeyServerPanel.setBorder(new TitledBorder(new EtchedBorder(),
				ConstData.NOUSER_NO));
		KeyServerPanel.setLayout(new FlowLayout());
		// 赋值
		if (str1 != null && str1.length > 0) {
			KeyServerPanel.add(userJLable);
			JList<Object> list1 = new JList<Object>(str1);
			KeyServerPanel.add(new JScrollPane(list1));
		}
		ButtonPanel = new JPanel();
		ButtonPanel.setLayout(new FlowLayout());
		// ButtonPanel.add(ExitButton);

		/*
		 * paraPanel = new JPanel(); paraPanel.setBorder(new TitledBorder(new
		 * EtchedBorder(), ConstData.USER_NO)); paraPanel.setLayout(new
		 * FlowLayout()); paraPanel.setSize(400, 300);
		 */
		// 赋值
		if (str2 != null && str2.length > 0) {
			JList<Object> list1s = new JList<Object>(str2);
			JScrollPane jsp = new JScrollPane(list1s);
			KeyServerPanel.add(noUserJLable);
			KeyServerPanel.add(jsp);
		}
		// KeyServerPanel.add(ExitButton);
		// 设置大小
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(screenSize.width / 2 - ConstData.CONFIG_WIDTH / 2,
				screenSize.height / 2 - ConstData.CONFIG_HEIGHT / 2);
		setSize(320, 260);
		setResizable(false);
		// 网格布局，一行一列
		setLayout(new GridLayout(1, 1));
		add(KeyServerPanel);
		// add(paraPanel);
		// add(ButtonPanel);
	}

	/**
	 * 事件监听
	 */
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		if (cmd.equals(ConstData.SAVE)) {

		} else if (!cmd.equals(ConstData.EXIT)) {
			return;
		}
		ShutDown();
	}

	/**
	 * 关闭处理
	 * 
	 * @author lyh
	 * 
	 */
	class CloseHandler extends WindowAdapter {

		PosNoFrame sd;

		public CloseHandler(PosNoFrame sd) {
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

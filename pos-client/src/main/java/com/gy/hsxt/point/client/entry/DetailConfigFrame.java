package com.gy.hsxt.point.client.entry;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import com.gy.hsxt.bs.bean.tool.resultbean.AfterDeviceDetail;
import com.gy.hsxt.point.client.util.ConstData;

/**
 * Simple to Introduction
 * 
 * @category Simple to Introduction
 * @projectName WritePOS
 * @package .ConfigServer.java
 * @className ConfigServer
 * @description 服务器配置窗口
 * @author lyh
 * @createDate 2015-12-17 上午11:43:50
 * @updateUser lyh
 * @updateDate 2016-1-87 上午11:43:50
 * @updateRemark 说明本次修改内容
 * @version V3.0
 */
@SuppressWarnings("serial")
public class DetailConfigFrame extends JFrame implements ActionListener {
	// 表格面板
	private JPanel KeyServerPanel;
	// 主界面
	MainView onwer;
	// 表格
	private JTable OrderTable;
	int OrderTableRow = -1;

	// 构造方法
	public DetailConfigFrame(MainView pOnwer, List<AfterDeviceDetail> listObj) {
		onwer = pOnwer;
		addWindowListener(new CloseHandler(this));
		// 清单列表
		DefaultTableModel dataModel = new DefaultTableModel(
				ConstData.EMPTY_CELL, ConstData.REPARE_HANDERS) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		OrderTable = new JTable(dataModel);
		// 单选模式，只可以选择一行
		OrderTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		// 滚动条
		JScrollPane JSP = new JScrollPane(OrderTable);
		// 设置位置
		JSP.setBounds(ConstData.GAP, ConstData.TOP, ConstData.ORDER_WIDTH - 2
				* ConstData.GAP, ConstData.ORDER_HEIGHT - ConstData.TOP
				- ConstData.GAP);
		// 添加监听
		OrderTable.addMouseListener(new OrderRepaireClick());
		KeyServerPanel = new JPanel();
		KeyServerPanel.setBorder(new TitledBorder(new EtchedBorder(),
				ConstData.REPARI_TITLE));
		KeyServerPanel.setLayout(null);
		KeyServerPanel.add(JSP);
		int i = 0;
		// 清除旧数据
		DefaultTableModel tableModel = (DefaultTableModel) OrderTable
				.getModel();
		int number = tableModel.getRowCount();
		for (i = 0; i < number; i++) {
			tableModel.removeRow(0);
		}
		// 填充数据
		for (i = 0; i < listObj.size(); i++) {
			AfterDeviceDetail data = listObj.get(i);
			tableModel
					.addRow(new Object[] { data.getAfterOrderNo(),
							data.getConfNo(), data.getIsConfig(),
							data.getTerminalNo(),data.getDeviceSeqNo() });
		}
		hideTableColumn(OrderTable,4);
		// 设置大小
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((screenSize.width / 2 - ConstData.CONFIG_WIDTH / 2) - 250,
				screenSize.height / 2 - ConstData.CONFIG_HEIGHT / 2);
		setSize(820, 560);
		setResizable(false);
		// 设置布局，一行一列
		setLayout(new GridLayout(1, 1));
		// 添加到主頁面
		add(KeyServerPanel);
	}

	/**
	 * 事件
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
		DetailConfigFrame sd;

		public CloseHandler(DetailConfigFrame sd) {
			this.sd = sd;
		}

		public void windowClosing(WindowEvent e) {
			ShutDown();
		}
	}

	/**
	 * 关闭方法
	 */
	void ShutDown() {
		setVisible(false);
		dispose();
		onwer.setEnabled(true);
		onwer.setVisible(true);
	}

	class OrderRepaireClick implements MouseListener {
		public void mouseClicked(MouseEvent e) {
			// 获取表格行
			OrderTableRow = OrderTable.rowAtPoint(e.getPoint());
			if (JOptionPane.showConfirmDialog(null, "开始烧机，售后订单号："
					+ (String) OrderTable.getValueAt(OrderTableRow, 0),
					"提示:请将POS机调整为下载状态", JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {
				return;
			}
			// 调用主页面开始获取机器号，烧机
			onwer.getRepairePosNo((String) OrderTable.getValueAt(OrderTableRow,
					0) + ":" + (String) OrderTable.getValueAt(OrderTableRow, 3)+":"+(String) OrderTable.getValueAt(OrderTableRow, 4));
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

	//隐藏表格列
	private void hideTableColumn(JTable table, int column){
		table .getColumnModel().getColumn(column).setMinWidth(0);
		table .getColumnModel().getColumn(column).setMaxWidth(0);
	    table .getColumnModel().getColumn(column).setPreferredWidth(0);
	}
}

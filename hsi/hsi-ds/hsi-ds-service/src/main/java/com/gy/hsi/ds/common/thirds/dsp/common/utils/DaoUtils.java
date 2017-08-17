package com.gy.hsi.ds.common.thirds.dsp.common.utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.gy.hsi.ds.common.thirds.dsp.common.form.RequestListBase.Page;
import com.gy.hsi.ds.common.thirds.ub.common.db.DaoPage;
import com.gy.hsi.ds.common.thirds.unbiz.common.genericdao.operator.Order;

/**
 * DAO有用的Utils
 *
 * @author liaoqiqi
 */
public class DaoUtils {

    /**
	 * 将业务的Page转成Dao的Page
	 *
	 * @param page
	 *
	 * @return
	 */
	public static DaoPage daoPageAdapter(Page page) {

		DaoPage daoPage = new DaoPage();

		daoPage.setPageNo(page.getPageNo());
		daoPage.setPageSize(page.getPageSize());

		List<Order> orderList = new ArrayList<Order>();

		if (0 < page.getOrderByList().size()) {

			for (String orderBy : page.getOrderByList()) {
				Order order = new Order(orderBy, page.isAsc());
				orderList.add(order);
			}
		} else if (!StringUtils.isEmpty(page.getOrderBy())) {
			Order order = new Order(page.getOrderBy(), page.isAsc());
			orderList.add(order);
		}

		daoPage.setOrderList(orderList);

		return daoPage;
	}
}

package com.gy.apply.admin.increment;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.util.Bytes;

/**
 * Simple to Introduction
 * 
 * @category Simple to Introduction
 * @projectName apply-incurement
 * @package com.gy.apply.admin.increment.HelloHBase.java
 * @className HelloHBase
 * @description 一句话描述该类的功能
 * @author zhucy
 * @createDate 2014-7-2 上午9:29:18
 * @updateUser zhucy
 * @updateDate 2014-7-2 上午9:29:18
 * @updateRemark 说明本次修改内容
 * @version v0.0.1
 */
public class HelloHBase {
	public static void main(String[] args) throws IOException {
		Configuration conf = HBaseConfiguration.create();
		conf.set("hbase.zookeeper.quorum", "192.168.1.180");
		HBaseAdmin admin = new HBaseAdmin(conf);
		HTableDescriptor tableDescriptor = admin.getTableDescriptor(Bytes.toBytes("test"));
		byte[] name = tableDescriptor.getName();
		System.out.println(new String(name));
		HColumnDescriptor[] columnFamilies = tableDescriptor.getColumnFamilies();
		for (HColumnDescriptor d : columnFamilies) {
			System.out.println(d.getNameAsString());
		}
	}
}

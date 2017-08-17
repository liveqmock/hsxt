package com.gy.hsxt.pg.bankadapter.pingan.b2e.protocol;

import java.io.IOException;
import java.io.OutputStream;

/**
 * 自定义对象序列化接口，完成最底层对流的编码
 * OutputStream
 * @author jbli
 */
public interface IStreamWritable {

	/**
	 * 向输出流中写入自身对象
	 * @param out
	 * @throws IOException
	 */
	public void write(OutputStream out) throws IOException;
}

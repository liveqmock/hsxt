package com.gy.hsxt.rabbitmq.common.persistentlog.parent;




import java.io.IOException;
import java.io.OutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gy.hsxt.rabbitmq.common.bean.RecoveryCoordinator;
import com.gy.hsxt.rabbitmq.common.constant.ConfigConstant;
/**
 * 
* @ClassName: RecoveryOutputStream 
* @Description: 具备恢复重写功能的输出流抽象类
* @author tianxh 
* @date 2015-8-6 下午5:59:42 
*
 */
abstract public class RecoveryOutputStream extends OutputStream {
	private static final Logger log = LoggerFactory.getLogger(RecoveryOutputStream.class);
	private RecoveryCoordinator recoveryCoordinator; //恢复重写策略类

	protected OutputStream os;
	protected boolean presumedClean = true;

	private boolean isPresumedInError() {
		return (recoveryCoordinator != null && !presumedClean);
	}
	/**
	 * 
	* <p>Title: write</p> 
	* <p>Description: 持久化信息</p> 
	* @param b
	* @param off
	* @param len 
	* @see java.io.OutputStream#write(byte[], int, int)
	 */
	public void write(byte b[], int off, int len) {
		if (isPresumedInError()) {
			if (!recoveryCoordinator.isTooSoon()) {
				attemptRecovery();
			}
			return;
		}
		try {
			os.write(b, off, len);
			postSuccessfulWrite();
		} catch (IOException e) {
			postIOFailure(e);
		}
	}
	/**
	 * 
	* <p>Title: write</p> 
	* <p>Description: 持久化信息</p> 
	* @param b 
	* @see java.io.OutputStream#write(int)
	 */
	@Override
	public void write(int b) {
		if (isPresumedInError()) {
			if (!recoveryCoordinator.isTooSoon()) {
				attemptRecovery();
			}
			return;
		}
		try {
			os.write(b);
			postSuccessfulWrite();
		} catch (IOException e) {
			postIOFailure(e);
		}
	}
	/**
	 * 
	* <p>Title: flush</p> 
	* <p>Description: 清空流</p>  
	* @see java.io.OutputStream#flush()
	 */
	@Override
	public void flush() {
		if (os != null) {
			try {
				os.flush();
				postSuccessfulWrite();
			} catch (IOException e) {
				postIOFailure(e);
			}
		}
	}
	/**
	 * 
	* @Title: getDescription 
	* @Description: 文件信息描述 
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	public abstract String getDescription();
	
	/**
	 * 
	* @Title: openNewOutputStream 
	* @Description: 新建输出流
	* @param @return
	* @param @throws IOException    设定文件 
	* @return OutputStream    返回类型 
	* @throws
	 */
	public abstract OutputStream openNewOutputStream() throws IOException;
	/**
	 * 
	* @Title: postSuccessfulWrite 
	* @Description: 持久化成功则对恢复重写策略类置null
	* @param     设定文件 
	* @return void    返回类型 
	* @throws
	 */
	private void postSuccessfulWrite() {
		if (recoveryCoordinator != null) {
			recoveryCoordinator = null;
			log.info(ConfigConstant.MOUDLENAME, "[RecoveryOutputStream]",
					ConfigConstant.FUNNAME,
					"[postIOFailure],Recovered from IO failure on "
					+ getDescription());
		}
	}
	/**
	 * 
	* @Title: postIOFailure 
	* @Description: 持久化失败则启用恢复重写策略类 
	* @param @param e    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	public void postIOFailure(IOException e) {
		log.info(ConfigConstant.MOUDLENAME, "[RecoveryOutputStream]",
				ConfigConstant.FUNNAME,
				"[postIOFailure],IO failure while writing to " + getDescription());
		presumedClean = false;
		if (recoveryCoordinator == null) {
			recoveryCoordinator = new RecoveryCoordinator();
		}
	}
	/**
	 * 
	* <p>Title: close</p> 
	* <p>Description: 关闭流</p> 
	* @throws IOException 
	* @see java.io.OutputStream#close()
	 */
	@Override
	public void close() throws IOException {
		if (os != null) {
			os.close();
		}
	}
/**
 * 
* @Title: attemptRecovery 
* @Description: 关闭失败输出流，重新启用新的输出流
* @param     设定文件 
* @return void    返回类型 
* @throws
 */
	void attemptRecovery() {
		try {
			close();
		} catch (IOException e) {
			log.error(ConfigConstant.MOUDLENAME, "[RecoveryOutputStream]",
					ConfigConstant.FUNNAME,
					"[attemptRecovery],code:", e.getCause(),
					",message:", e.getMessage());
		}
		log.info(ConfigConstant.MOUDLENAME, "[RecoveryOutputStream]",
				ConfigConstant.FUNNAME,
				"[attemptRecovery],Attempting to recover from IO failure on "
				+ getDescription());
		try {
			os = openNewOutputStream();
			presumedClean = true;
		} catch (IOException e) {
			log.info(ConfigConstant.MOUDLENAME, "[RecoveryOutputStream]",
					ConfigConstant.FUNNAME,
					"[attemptRecovery],Failed to open" + getDescription());
		}
	}
}

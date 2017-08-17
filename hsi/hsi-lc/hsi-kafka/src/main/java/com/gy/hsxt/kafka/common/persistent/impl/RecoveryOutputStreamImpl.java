package com.gy.hsxt.kafka.common.persistent.impl;



import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.channels.FileChannel;

import com.gy.hsxt.kafka.common.persistent.RecoveryOutputStream;
/**
 * 
* @ClassName: RecoveryOutputStreamImpl 
* @Description: 具备恢复重写功能的输出流实现类
* @author tianxh 
* @date 2015-8-6 下午6:00:33 
*
 */
public class RecoveryOutputStreamImpl extends RecoveryOutputStream {

  private File file; //待持久化的文件
  private FileOutputStream fos; //输出流
  /**
   * 
  * <p>Title: </p> 
  * <p>Description: 构造方法</p> 
  * @param file	
  * @param append 是否追加
  * @throws FileNotFoundException
   */
  public RecoveryOutputStreamImpl(File file, boolean append)
      throws FileNotFoundException {
    this.file = file;
    fos = new FileOutputStream(file, append);
    this.os = new BufferedOutputStream(fos);
    this.presumedClean = true;
  }

  public FileChannel getChannel() {
    if (os == null) {
      return null;
    }
    return fos.getChannel();
  }

  public File getFile() {
    return file;
  }
  /**
   * 
  * <p>Title: getDescription</p> 
  * <p>Description: 获取文件信息描述</p> 
  * @return 
  * @see com.gy.hsi.lc.common.persistentlog.parent.RecoveryOutputStream#getDescription()
   */
  @Override
  public String getDescription() {
	    return "file ["+file+"]";
	  }
  /**
   * 
  * <p>Title: openNewOutputStream</p> 
  * <p>Description:新建输出流 </p> 
  * @return
  * @throws IOException 
  * @see com.gy.hsi.lc.common.persistentlog.parent.RecoveryOutputStream#openNewOutputStream()
   */
  @Override
  public OutputStream openNewOutputStream() throws IOException {
	    fos = new FileOutputStream(file, true);
	    return new BufferedOutputStream(fos);
	  }
  
/**
 * 
* <p>Title: toString</p> 
* <p>Description: 重写toString() </p> 
* @return 
* @see java.lang.Object#toString()
 */
@Override
  public String toString() {
    return "c.q.l.c.recovery.ResilientFileOutputStream@"
        + System.identityHashCode(this);
  }

}

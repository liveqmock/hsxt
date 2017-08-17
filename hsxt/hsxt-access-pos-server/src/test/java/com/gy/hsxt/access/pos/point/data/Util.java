package com.gy.hsxt.access.pos.point.data;

import java.io.File;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.lang3.math.NumberUtils;


/**
 * @author GengLian
 */
public class Util {
	
	public static final Lock GLOBAL_STORAGE_FILE2_LOCK = new ReentrantLock();
	
    /*
     * posRunCode、batchNo，最大值是6个9
     */
    public static final int POS_RUN_CODE_AND_BATCH_NO_MAX = 999999;
    

	@SuppressWarnings("unchecked")
	public static <T> T unmarshall(File file, Class<T> cls) throws Exception {
		final JAXBContext context = JAXBContext.newInstance(cls);
		final Unmarshaller unmarshaller = context.createUnmarshaller();
		return (T) unmarshaller.unmarshal(file);
	}
	
	public static void marshall(Object oldBeans, File file, Class<?> cls) throws Exception {
		final JAXBContext context = JAXBContext.newInstance(cls);
		final Marshaller marshaller = context.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		marshaller.setProperty(Marshaller.JAXB_ENCODING, org.apache.commons.lang3.CharEncoding.UTF_8);
		//
		GLOBAL_STORAGE_FILE2_LOCK.lockInterruptibly();
		try {
			marshaller.marshal(oldBeans, file);
		} finally {
			GLOBAL_STORAGE_FILE2_LOCK.unlock();
		}
	}
}

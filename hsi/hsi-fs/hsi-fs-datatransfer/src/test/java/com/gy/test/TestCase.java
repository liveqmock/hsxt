package com.gy.test;

import java.util.List;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gy.hsi.fs.mapper.vo.TFsFileMetaData;
import com.gy.hsi.fs.service.IFSDataTransferService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/spring/spring-global.xml" })
public class TestCase {

	@Autowired
	private IFSDataTransferService fsDataTransferService;

	// @Test
	public void qruery() {

		List<TFsFileMetaData> list = fsDataTransferService
				.queryTFsFileMetaDatasForDownloadTFS(0, 5000);

		for (TFsFileMetaData d : list) {
			System.out.println(d.getFileStorageId());
		}

	}
}

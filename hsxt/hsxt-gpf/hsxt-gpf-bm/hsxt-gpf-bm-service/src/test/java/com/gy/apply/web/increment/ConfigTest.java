package com.gy.apply.web.increment;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/spring-global.xml")
public class ConfigTest {

	@Value("${ftp.url}")
	private String ftpUrll;

	@Test
	public void testCreateTable() {
		System.out.println(ftpUrll);
	}
}

package junit.test;

import java.io.IOException;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestActiveMQ {

	@Test
	public void testSpringActiveMQ() throws Exception{
		ApplicationContext ioc = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-activemq.xml");
		
		System.in.read();
	}
}

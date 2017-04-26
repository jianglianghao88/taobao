package com.taotao.test;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;

import com.taotao.rest.redis.JedisClient;

public class JedisTest {

	
	@Test
	public void test00(){
		Jedis jedis = new Jedis("192.168.1.115", 6379);
		
		jedis.set("name", "jlh");
		
		String str = jedis.get("name");
		
		System.out.println(str);
	}
	
	@Test
	public void testJedisCluster(){
		
		Set<HostAndPort> nodes = new HashSet<>();
		nodes.add(new HostAndPort("192.168.1.115", 7001));
		nodes.add(new HostAndPort("192.168.1.115", 7002));
		nodes.add(new HostAndPort("192.168.1.115", 7003));
		nodes.add(new HostAndPort("192.168.1.115", 7004));
		nodes.add(new HostAndPort("192.168.1.115", 7005));
		nodes.add(new HostAndPort("192.168.1.115", 7006));
		
		JedisCluster jedisCluster = new JedisCluster(nodes);
		
		jedisCluster.set("name", "cangjingkong");
		jedisCluster.set("age", "28");
		
		String name = jedisCluster.get("name");
		String age = jedisCluster.get("age");
		
		System.out.println(name);
		System.out.println(age);
		
		jedisCluster.close();
	}
	
	@Test
	public void jedisClientTest(){
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-*.xml");
		//从容器中获得JedisClient对象
		JedisClient jedisClient = applicationContext.getBean(JedisClient.class);
		//jedisClient操作redis
		jedisClient.set("cliet10", "100000");
		String string = jedisClient.get("cliet3");
		System.out.println(string);
	}
}

package com.taotao.ftl.test;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class TestFreemarker {

	
	@Test
	public void test00() throws Exception{
		Configuration configuration = new Configuration(Configuration.getVersion());
		
		configuration.setDirectoryForTemplateLoading(new File("E:\\code\\xm\\taotao\\taotao-portal\\src\\main\\webapp\\WEB-INF\\ftl"));
	
		configuration.setDefaultEncoding("utf-8");
		
		Template template = configuration.getTemplate("jlh.ftl");
		
		Map root = new HashMap<>();
		
		root.put("jlh", "Hello Freemarker");
		
		Writer writer = new FileWriter(new File("E:\\jada\\jlh.html"));
		
		template.process(root, writer);
		
		writer.close();
	}
}

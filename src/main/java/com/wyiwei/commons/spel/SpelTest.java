package com.wyiwei.commons.spel;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import com.wyiwei.commons.comm.so.Person;

import freemarker.cache.ClassTemplateLoader;
import freemarker.cache.FileTemplateLoader;
import freemarker.cache.StringTemplateLoader;
import freemarker.cache.TemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class SpelTest {
	
	public static void main(String[] args) throws Exception {
//		SpelExample spelExp = new SpelExample();
//		spelExp.test1();
//		test1();
//		test2();
		test3();
	}
	
	/**
	 * freemarker的Template使用1（内部实现包含）
	 */
	public static void test1() {
		Person person = new Person("赵六", 23);
		String templateStr =    "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n"+
								"<BOSFXII>\n"+
								"	<Name>${name}</Name>\n"+
								"	<Age>${age}</Age>\n"+
								"</BOSFXII>";
		
         StringTemplateLoader strLoader = new StringTemplateLoader();
         strLoader.putTemplate("resp", templateStr);
         Configuration configuration = new Configuration();
         configuration.setObjectWrapper(new DefaultObjectWrapper());
         configuration.setTemplateLoader(strLoader);
         try {
             Template template = configuration.getTemplate("resp");
             StringWriter writer = new StringWriter();
             template.process(person, writer);
             System.out.println(writer.toString());
         } catch (IOException e) {
             e.printStackTrace();
         } catch (TemplateException e) {
             e.printStackTrace();
         }
		
	}
	
	/**
	 * freemarker的Template使用2（内部实现包含）
	 * @throws IOException 
	 */
	public static void test2() throws IOException {
		String tempFilePath = "D:\\Java\\workspace\\my\\Practice_WorkSpace\\aa_commons\\src\\main\\java\\com\\wyiwei\\aa_commons\\spel";
		Configuration configuration = new Configuration();
		configuration.setObjectWrapper(new DefaultObjectWrapper());
		configuration.setDirectoryForTemplateLoading(new File(tempFilePath));
		
		try {
			Template template = configuration.getTemplate("freemaker-template.tempx");
			StringWriter writer = new StringWriter();
			Map<String, Object> context = new HashMap<>();
			context.put("rqUID", UUID.randomUUID().toString());
			context.put("transCode", "A001");
			context.put("name", "赵小刚");
			context.put("age", "21");
			List<Object> parts = new ArrayList<>();
			parts.add("眼睛");
			parts.add("鼻子");
			parts.add("嘴巴");
			context.put("parts", parts);
			context.put("lastUpdated", new Date());
			template.process(context, writer);
			System.out.println(writer.toString());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TemplateException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void test3() {
		Person p = new Person("大蓝宝", 1);
		ExpressionParser parser = new SpelExpressionParser();
		Expression expression = parser.parseExpression("['name']='yuan'");
		System.out.println(expression.getValue(p));
		
	}
}

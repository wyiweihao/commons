package com.wyiwei.commons.spel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import com.wyiwei.commons.comm.so.Person;

public class SpelExample {

	public void test1() throws NoSuchMethodException, SecurityException {
		
		/** 1. 最简单的表达式 */
	    ExpressionParser parser= new SpelExpressionParser();
	    Expression exp = parser.parseExpression("'HelloWorld'");
	    String message = (String)exp.getValue();
	    System.out.println("例1：" + message);
	    
	    /** 2. SpEL支持一系列特性，例如方法调用，访问属性和调用构造器。*/
	    exp = parser.parseExpression("'HelloWorld'.concat('!')");
	    message = (String)exp.getValue();
	    System.out.println("例2：" + message);

	    /** 3. SpEL使用标准的‘.’符号支持属性嵌套和属性设值，例如：prop1.prop2.prop3. 
	     * 公共属性也可以被访问。*/
	    exp = parser.parseExpression("'HelloWorld'.bytes");
	    byte[] bytes = (byte[])exp.getValue();
	    for (byte b : bytes) {
	    	System.out.println("例3：" + (char)b);
	    }
	    //
	    Integer intVal = null;
	    exp = parser.parseExpression("'HelloWorld'.bytes.length");
	    intVal = (Integer)exp.getValue();
	    System.out.println("例4：" + intVal);
	    
	    /** 4. 使用字符串构造器而不是字符串文字。*/
	    exp = parser.parseExpression("new String('helloworld').toUpperCase()");
	    message = exp.getValue(String.class);
	    System.out.println("例5：" + message);
	    
	    /**
	     * 5.使用上下文获取
	     */
	    GregorianCalendar cldr = new GregorianCalendar();
	    cldr.set(1856,7,9);
	    Person person = new Person("NikolaTesla",cldr.getTime(), "Serbian");
	    exp = parser.parseExpression("name");// 注意这里没有用单引号（用单引号类似于直接量或字符串，不用则类似于引用或属性）
	    StandardEvaluationContext context = new StandardEvaluationContext();
	    context.setRootObject(person);
	    // 或EvaluationContext context = new StandardEvaluationContext(person);
	    String name = (String)exp.getValue(context, String.class);
	    System.out.println("例6：" + name);
	    
	    // 直接调用方法
	    // exp=parser.parseExpression("name=='NikolaTesla'");
	    exp=parser.parseExpression("name.equals('NikolaTesla')");
	    boolean yn = exp.getValue(context,Boolean.class);
	    System.out.println("例7：" + yn);
	    
	    /**
	     * 6. 数组和集合用方括号索引
	     */
	    class Simple{
	    	public List<Boolean> booleanList = new ArrayList<Boolean>();
    	}
    	Simple simple= new Simple();
    	simple.booleanList.add(true);
    	StandardEvaluationContext simpleContext= new StandardEvaluationContext(simple);
    	parser.parseExpression("booleanList[0]").setValue(simpleContext, "false");
    	yn = simple.booleanList.get(0);
    	System.out.println("例8：" + yn);// false

    	/**
    	 * 7. 在xml文件中直接使用，见spel-example.xml
    	 */
    	/**
    	 * 8. 用于@Value注解，见"代码块1"和"代码块2"
    	 */
    	/**
    	 * 9. 自动装配的方法和构造器同样可以使用@Value注解。见"代码块3"和"代码块4"
    	 */
    	
    	/** 10. 使用标准的操作符号支持关系操作符：等于，不等于，小于，小于等于，大于，大于等于。*/
    	boolean trueValue = parser.parseExpression("2==2").getValue(Boolean.class);
    	boolean falseValue = parser.parseExpression("2<-5.0").getValue(Boolean.class);
    	trueValue = parser.parseExpression("'black'<'block'").getValue(Boolean.class);
    	// 除此之外，SpEL支持‘instanceof’和基于正则表达式的‘match’操作。
    	falseValue = parser.parseExpression("'xyz' instanceof T(int)").getValue(Boolean.class);
    	trueValue = parser.parseExpression("'5.00' matches '^-?\\d+(\\.\\d{2})?$'").getValue(Boolean.class);
    	falseValue = parser.parseExpression("'5.0067' matches '^-?\\d+(\\.\\d{2})?$'").getValue(Boolean.class);
    	    
    	/** 11. 逻辑操作符，支持的逻辑操作符包括and，or和not，使用方法如下。*/
    	//--AND--
    	falseValue = parser.parseExpression("true and false").getValue(Boolean.class);
    	String expression = "isMember('NikolaTesla') and isMember('MihajloPupin')";
    	trueValue = parser.parseExpression(expression).getValue(person, Boolean.class);
    	//--OR--
    	trueValue = parser.parseExpression("true or false").getValue(Boolean.class);
    	expression = "isMember('NikolaTesla') or isMember('AlbertEinstien')";
    	trueValue = parser.parseExpression(expression).getValue(person,Boolean.class);
    	//--NOT--
    	falseValue = parser.parseExpression("!true").getValue(Boolean.class);
    	//--ANDandNOT--
    	expression = "isMember('NikolaTesla') and !isMember('MihajloPupin')";
    	falseValue = parser.parseExpression(expression).getValue(person, Boolean.class);
    	
    	/** 12. 算术操作符：
    	  *  加法运算符可以用于数字，字符串和日期。减法可用于数字和日期。乘法和除法仅可以用于。
    	  *  其他支持的数学运算包括取模（％）和指数幂（^）。使用标准的运算符优先级。这些运算符示例如下。*/
    	int two = parser.parseExpression("1+1").getValue(Integer.class); //2
    	String testString = parser.parseExpression("'test'+''+'string'").getValue(String.class); //'teststring'
    	int four = parser.parseExpression("1 - -3").getValue(Integer.class); //4
    	double d = parser.parseExpression("1000.00-1e4").getValue(Double.class); //-9000
    	int six = parser.parseExpression("-2*-3").getValue(Integer.class); //6
    	double twentyFour = parser.parseExpression("2.0*3e0*4").getValue(Double.class); //24.0
    	int minusTwo = parser.parseExpression("6/-3").getValue(Integer.class); //-2
    	double one = parser.parseExpression("8.0/4e0/2").getValue(Double.class); //1.0
    	int three = parser.parseExpression("7%4").getValue(Integer.class); //3
    	one = parser.parseExpression("8/5%2").getValue(Integer.class); //1
    	int minusTwentyOne = parser.parseExpression("1+2-3*8").getValue(Integer.class); //-21

    	/**
    	 * 13. 赋值: 属性设置是通过使用赋值运算符。这通常是在调用setValue中执行但也可以在调用getValue内。
    	 */
    	person = new Person();
    	StandardEvaluationContext personContext = new StandardEvaluationContext(person);
    	parser.parseExpression("name").setValue(personContext, "AlexanderSeovic2");
    	//alternatively
    	String name2 = parser.parseExpression("name").getValue(personContext, String.class);
    	String name1 = parser.parseExpression("name='AlexandarSeovic'").getValue(personContext, String.class);
    	System.out.println("例9：name2["+name2+"], name1["+name1+"]");

    	/** 14. 类型:
    	 * 特殊的‘T'操作符可以用来指定一个java.lang.Class的实例（'类型'）。
    	 * 静态方法调用也使用此操作符。该StandardEvaluationContext使用TypeLocator寻找类型，
    	 * StandardTypeLocator（可更换）建立在java.lang包的基础上。这意味着T（）引用在java.lang中的类型不须被完全限定，但所有其他类型的引用必须。
    	 */
    	Class dateClass = parser.parseExpression("T(java.util.Date)").getValue(Class.class);
    	Class stringClass = parser.parseExpression("T(String)").getValue(Class.class);
    	trueValue = parser.parseExpression("T(java.math.RoundingMode).CEILING < T(java.math.RoundingMode).FLOOR").getValue(Boolean.class);
    	System.out.println("例10："+ trueValue);
    	
    	/**
    	 * 15. 构造器：
    	 * 可以使用new运算符调用构造器。完全限定类名应被用于所有类型除了原始类型和字符串（如整型，浮点，等等，可以使用）。
    	 */
    	person = parser.parseExpression("new com.wyiwei.aa_commons.comm.so.Person('张三', new java.util.Date(), '中国')").getValue(Person.class);
    	personContext = new StandardEvaluationContext(person);
    	parser.parseExpression("members.add('鼻子')").getValue(personContext);
    	System.out.println("例11："+ person);
    	
    	/**
    	 * 16. 变量
    	 *	       变量可以在表达式中使用语法(#变量名)引用。变量设置使用StandardEvaluationContext的方法setVariable。
    	 */
    	person = new Person("张三", new java.util.Date(), "中国");
    	personContext = new StandardEvaluationContext(person);
    	personContext.setVariable("newName", "王麻子");
    	parser.parseExpression("name = #newName").getValue(personContext);
    	System.out.println("例12：" + person.getName()); //"MikeTesla"

    	/** 17. #this变量和集合选择 (这里也包括集合的选择使用，通常是对List和Map的选择)
         *      #this变量通常被定义和引用当前求值对象（该对象的不合格引用将被移除）。
         *      #除了返回所有被选择的元素，也可以检索第一个和最后一个值。用^[…]获得第一个匹配入口，用$[…]获得最后一个匹配入口。
         */
	    List<Integer> primes = new ArrayList<Integer>();
	    primes.addAll(Arrays.asList(2,3,5,7,11,13,17));
	    context= new StandardEvaluationContext();
	    context.setVariable("primes", primes);
	    List<Integer> primesGreaterThanTen = parser.parseExpression("#primes.?[#this>10]").getValue(context, List.class);
	    Integer singleNum = parser.parseExpression("#primes.$[true]").getValue(context, Integer.class);
	    List<Integer> primesGreaterThanTen2 = parser.parseExpression("#primes.^[#this>10]").getValue(context, List.class);
	    List<Integer> primesGreaterThanTen3 = parser.parseExpression("#primes.$[#this>10]").getValue(context, List.class);
	    System.out.println("例13：" + primesGreaterThanTen); // [11, 13, 17]
	    System.out.println("例13：" + singleNum); // 17
	    System.out.println("例13：" + primesGreaterThanTen2); // [11]
	    System.out.println("例13：" + primesGreaterThanTen3); // [17]
	    
	    /**
	     * 18. 函数：
	     *		你可以通过注册可在表达式字符串内调用的用户自定义函数来扩展SpEL。使用StandardEvaluationContext中的下列方法注册函数。
		 *		public void registerFunction(String name, Method m)所引用的Java方法实现该函数，例如如下这个有用的反转字符串方法。
	     */
		context= new StandardEvaluationContext();
		context.registerFunction("reverse",org.apache.commons.lang.StringUtils.class.getDeclaredMethod("reverse", new Class[]{String.class}));
		String helloReversed =	parser.parseExpression("#reverse('hello')").getValue(context, String.class);
		System.out.println("例14：" + helloReversed);
		
		/**
		 * 19. 三元操作符（If-Then-Else）
	     * 		可以在表达式内使用if-then-else条件逻辑三元操作符。下面是个小例子：
		 */
		String falseString = parser.parseExpression("false?'trueExp':'falseExp'").getValue(String.class);
		// 这个情况下，布尔false结果返回‘falseExp‘字符串，一个真实的例子如下：
		person = new Person();
		person.addMember("尾巴");
		context= new StandardEvaluationContext(person);
		parser.parseExpression("name").setValue(person, "王五");
		context.setVariable("queryName", "尾巴");
		expression = "isMember(#queryName)? #queryName + ' is a member of '+ name : #queryName+' is not a member of '+ name";
		String queryResultString = parser.parseExpression(expression).getValue(context, String.class);
		System.out.println("例15：" + queryResultString);
		// 下一节将看到三元操作符更短的语法Elvis操作符。(略...因为几乎用不到)

		/**
		 * 表达式模板：
		 * 表达式模板允许与一个或多个求值块复合文字文本。每个求值块有可自定义的前缀和后缀的字符定界，一个普遍的选择是使用的分隔符$（）定界。例如，
		 */
		String randomPhrase = parser.parseExpression("random number is ${T(java.lang.Math).random()}",
				new TemplateParserContext()).getValue(String.class);
		// 该字符串的求值是通过连接文字文本的‘random number is’和$()定界符内求值表达式的结果，在这里是调用的random()方法的结果。parseExpression()方法的第二个参数是ParserContext的类型。该ParserContext接口通常影响表达式如何被解析以支持模板功能。在TemplatedParserContext的定义如下所示。
		/*
		public class TemplatedParserContext implements ParserContext {
			public String getExpressionPrefix(){
				return "${";
			}
			public String getExpressionSuffix(){
				return "}";
			}
			public boolean isTemplate(){
				return true;
			}
		}
		*/

	    
	}
	
	// 代码块5
	public void test5(){
		
	}
	
	// 代码块1
	class FieldValueTestBean{
		
		@Value("#{systemProperties['user.region']}")
		private String defaultLocale;
		
		public void setDefaultLocale(String defaultLocale)
		{
			this.defaultLocale=defaultLocale;
		}
		public String getDefaultLocale()
		{
			return this.defaultLocale;
		}
	}
	// 和下面在属性的setter方法中使用等价。(代码块2)
	class PropertyValueTestBean{
		private String defaultLocale;
		
		@Value("#{systemProperties['user.region']}")
		public void setDefaultLocale(String defaultLocale)
		{
			this.defaultLocale=defaultLocale;
		}
		public String getDefaultLocale()
		{
			return this.defaultLocale;
		}
	}
	
    // 代码块3
	@SuppressWarnings("unused")
	class SimpleMovieLister{
		
		private PropertyValueTestBean testBean;
		private String local;
		
		@Autowired
		public void configure(PropertyValueTestBean testBean,
				@Value("#{systemProperties['user.region']}")String local){
			this.testBean = testBean;
			this.local = local;
		}
		//...
	}
	
	// 代码块4
	@SuppressWarnings("unused")
	public class MovieRecommender{

		private String defaultLocale;
		private SimpleMovieLister simpleMovieLister;
		@Autowired
		public MovieRecommender(SimpleMovieLister simpleMovieLister,
				@Value("#{systemProperties['user.country']}")String defaultLocale){
			this.simpleMovieLister = simpleMovieLister;
			this.defaultLocale=defaultLocale;
		}
		//...
	}

}

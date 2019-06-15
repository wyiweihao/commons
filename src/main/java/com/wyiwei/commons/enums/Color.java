package com.wyiwei.commons.enums;

public enum Color {
	
	BLUE(),// 使用常量就是调用（枚举自己调用）其构造方法，括号可省略
	RED,
	GREEN("绿色");
	
	private String color;
	
	Color(){
		
	}
	
	Color(String color){
		this.color = color;
	}
	
	public static String valueAs(String color){
		if ("01".equals(color)) {
			return GREEN.color;
		}
		return "其他颜色";
	}
}

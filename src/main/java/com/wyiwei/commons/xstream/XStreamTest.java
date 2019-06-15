package com.wyiwei.commons.xstream;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.Dom4JDriver;

public class XStreamTest {
	
	public static void main(String[] args) {
		RequestBean bean = new RequestBean();
		bean.setAge("25");
		bean.setName("张三");
		
		XStream xStream = new XStream(new Dom4JDriver());
		String xml = xStream.toXML(bean);
		
		System.out.println(xml);
		
		
		RequestBean bean2 = (RequestBean)xStream.fromXML(xml);
		System.out.println(bean2);
	}


}

package com.wyiwei.commons.document;

import java.io.File;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Node;

public class XmlHelperTest {
	
	public static void main(String[] args) throws Exception {
		
//		testGetXmlText();
		testGetXmlNodes();
//		testCreateElement();
	}
	
	/**
	 * 解析xml--获取元素文本
	 * @throws Exception
	 */
	public static void testGetXmlText()  throws Exception{
		String filePath = "src/main/java/com/wyiwei/aa_commons/document/files/test.xml";
		String xml = FileUtils.readFileToString(new File(filePath));
		Document doc = DocumentHelper.parseText(xml);
//		String txt = XmlHelper.selectTxtWithoutNamespace(doc, "/*/*/*/*[local-name()='NumTranCode']");
		String txt = XmlHelper.selectText(doc, "/*/*/node:FlowCode");
		System.out.println(txt);
	}
	
	/**
	 * 解析xml--获取元素节点
	 * @throws Exception
	 */
	public static void testGetXmlNodes()  throws Exception{
		String filePath = "src/main/java/com/wyiwei/aa_commons/document/files/test.xml";
		String xml = FileUtils.readFileToString(new File(filePath));
		Document doc = DocumentHelper.parseText(xml);
		List<Node> nodes = XmlHelper.selectNodes(doc, "/*/*/*/node:NumTranCode");
//		List<Node> nodes = XmlHelper.selectNodes(doc, "/*/*/*/node:*");
//		String txt = XmlHelper.selectText(doc, "/*/*/node:FlowCode");
		for (int i=0; i<nodes.size(); i++) {
			System.out.println(nodes.get(i).getName()+":"+nodes.get(i).getText());
		}
	}
	
	/**
	 * 拼装xml
	 * @throws Exception
	 */
	public static void testCreateElement()  throws Exception{
		String xml = XmlHelper.createElement().getDocument().asXML();
		System.out.println(xml);
	}
	
}

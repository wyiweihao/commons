package com.wyiwei.commons.document;

import java.util.List;
import java.util.Map;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Namespace;
import org.dom4j.Node;
import org.dom4j.QName;
import org.dom4j.XPath;

import com.google.common.collect.ImmutableMap;

public class XmlHelper {
	
	private final static String DEFAULT_URI = "http://www.weisen.com";
	private final static Map<String, String> URI_MAP = ImmutableMap.of("node", DEFAULT_URI);
	private final static Namespace NAMESPACE = DocumentHelper.createNamespace("", DEFAULT_URI);
	
	
	/**
	 * 不带namespace的xml解析
	 * @param node
	 * @param xPath
	 * @return
	 */
	public static String selectTxtWithoutNamespace(Node node, String xPath)
	{
		XPath x = DocumentHelper.createXPath(xPath);
		Node n = x.selectSingleNode(node);
		if (n != null)
		{
			return n.getText();
		}
		else 
		{
			return null;
		}
	}
	
	/**
	 * 带namespace的xml解析
	 * @param node
	 * @param xPath
	 * @return
	 */
	public static String selectText(Node node, String xPath)
	{
		XPath x = DocumentHelper.createXPath(xPath);
		x.setNamespaceURIs(URI_MAP);
		Node n = x.selectSingleNode(node);
		if (n != null)
		{
			return n.getText();
		}
		else 
		{
			return null;
		}
	}
	
	/**
	 * 获取单一节点
	 * @param node
	 * @param xPath
	 * @return
	 */
	public static Node selectSingleNode(Node node, String xPath)
	{
		XPath x = DocumentHelper.createXPath(xPath);
		x.setNamespaceURIs(URI_MAP);
		Node n = x.selectSingleNode(node);
		if (n != null)
		{
			return n;
		}
		else 
		{
			return null;
		}
	}
	/**
	 * 获取单一节点(带命名空间)
	 * @param node
	 * @param xPath
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<Node> selectNodes(Node node, String xPath)
	{
		XPath x = DocumentHelper.createXPath(xPath);
		x.setNamespaceURIs(URI_MAP);
		return x.selectNodes(node);
	}
	
	/**
	 * 拼装xml
	 * @param nodeName
	 * @return
	 */
	public static Element createElement()
	{
		return DocumentHelper.createDocument()
				.addElement(XmlHelper.createQName("WEISEN"))
					.addElement(XmlHelper.createQName("Cup")).addText("怡宝").getParent();
	}
	
	/**
	 * @param localName
	 * @return
	 */
	public static QName createQName(String localName){
		return DocumentHelper.createQName(localName, NAMESPACE);
//		return DocumentHelper.createQName(localName);
	}
	
}

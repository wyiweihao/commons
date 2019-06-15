package com.wyiwei.commons.tcphttp;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestClientException;

public class HttpSendUtils {

	private static Logger logger = LoggerFactory.getLogger(HttpSendUtils.class);
	/**
	 * @param reqURL
	 * @param reqMsg
	 * @return
	 * @throws MalformedURLException 
	 * @throws RestClientException
	 * @throws IOException
	 */
	public static String sendHttpMsg(String url, String reqMsg) throws MalformedURLException{ 
		NetClient client = new HttpClient();
		client.setURL(new URL(url));
		logger.debug("请求地址："+ url);
		logger.debug("请求报文：\n"+ reqMsg);
		String resp = client.execute(reqMsg);
		logger.debug("响应报文：\n" + resp);
		return resp;
	}
	
	public static String sendTCPMsg(Map<String, String> urlMapMap, String reqMsg){
		return null;
	}
}

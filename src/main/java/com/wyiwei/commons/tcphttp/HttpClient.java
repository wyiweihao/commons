package com.wyiwei.commons.tcphttp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.MessageFormat;

import com.wyiwei.commons.tcphttp.enums.HttpReqMethod;

public class HttpClient implements NetClient{

	private URL url;
	private HttpReqMethod requestMethod = HttpReqMethod.POST;// 默认POST
	private HttpURLConnection connecton;
	private int timeOut = 30000;// 默认30s
	private String reqCharset = "utf-8";// 默认
	private String respCharset = "utf-8";// 默认
	private boolean autoDisconn = true;
	

	
	@Override
	public boolean connect() {
		try {
			if (url != null) {
				connecton = (HttpURLConnection)url.openConnection();
				connecton.setDoInput(true);
				connecton.setDoOutput(true);
				connecton.setUseCaches(false);
				connecton.setRequestMethod(requestMethod.toString());
				connecton.setReadTimeout(timeOut);
				connecton.setRequestProperty("Charset", "UTF-8");// 设置文件字符集:
				connecton.setRequestProperty("Content-Type","application/json; charset=UTF-8");// 设置文件类型:
				connecton.setRequestProperty("accept","application/json");
			} else {
				throw new RuntimeException("The URL is null, Please set it!");
			}
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	public void setURL(String ip, String port, String path) {
		try {
			setUrl(new URL(MessageFormat.format("http://{0}:{1}/{2}", ip, port, path)));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public String execute(String req) {
		if (connecton == null) {
			connect();
		}
		try {
			PrintWriter reqWriter = new PrintWriter(new OutputStreamWriter(connecton.getOutputStream(), reqCharset));
			reqWriter.print(req);
			reqWriter.flush();
			reqWriter.close();
			BufferedReader respReader = new BufferedReader(new InputStreamReader(connecton.getInputStream(), respCharset));
			StringBuilder strBd = new StringBuilder();
			String tempStr = "";
			while ((tempStr = respReader.readLine()) != null && !tempStr.trim().equals("")) {
				strBd.append(tempStr);
			}
			return strBd.toString();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (autoDisconn) {
				connecton.disconnect();
				connecton = null;
			}
		}
		
		return null;
	}

	@Override
	public void setURL(URL url) {
		this.url = url;
	}

	@Override
	public void setTimeOut(int time) {
		this.timeOut = time;
	}

	public URL getUrl() {
		return url;
	}

	public void setUrl(URL url) {
		this.url = url;
	}

	public HttpReqMethod getRequestMethod() {
		return requestMethod;
	}

	public void setRequestMethod(HttpReqMethod requestMethod) {
		this.requestMethod = requestMethod;
	}

	public int getTimeOut() {
		return timeOut;
	}

	public String getReqCharset() {
		return reqCharset;
	}

	public void setReqCharset(String reqCharset) {
		this.reqCharset = reqCharset;
	}

	public String getRespCharset() {
		return respCharset;
	}

	public void setRespCharset(String respCharset) {
		this.respCharset = respCharset;
	}
	
}

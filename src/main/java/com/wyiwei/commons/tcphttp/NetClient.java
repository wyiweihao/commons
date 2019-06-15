package com.wyiwei.commons.tcphttp;

import java.net.URL;

public interface NetClient {
	public boolean connect();
	public void setURL(URL url);
	public void setURL(String ip, String port, String path);
	public void setTimeOut(int time);
	public String execute(String req);
}

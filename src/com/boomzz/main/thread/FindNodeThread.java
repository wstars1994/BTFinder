/**
 * author : 王新晨
 * date : 2018年8月1日 下午2:37:59
 */
package com.boomzz.main.thread;

import java.net.InetAddress;
import java.util.LinkedHashMap;

import com.boomzz.main.DHTUtil;
import com.boomzz.main.MyLogger;
import com.boomzz.main.packet.DHTPacketFindNode;

public class FindNodeThread extends Thread{
	
	private String nodeId = null;
	
	private String ip = null;
	
	private int port;
	
	public FindNodeThread(String nodeId, String ip, int port) {
		this.nodeId = nodeId;
		this.ip = ip;
		this.port = port;
	}

	@Override
	public void run() {
		try {
			LinkedHashMap<String, Object> requestData = DHTUtil.requestData(new DHTPacketFindNode(),nodeId,ip, port);
			if(ip.length()>15) {
				ip = InetAddress.getByName(ip).getHostAddress();
			}
			if(requestData!=null) {
				MyLogger.log(FindNodeThread.class, requestData.toString());
//				DBUtil.execute("INSERT INTO BT_DHT_NODE(NODE_ID,NODE_IP,NODE_PORT) VALUES('"+nodeId+"','"+ip+"','"+port+"')");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

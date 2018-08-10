package com.boomzz.main;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

import com.boomzz.main.util.DBUtil;
import com.boomzz.main.util.MyLogger;
import com.boomzz.main.util.SHA1;
import com.boomzz.main.util.UDPSocket;

public class DHTServerBoot {
    public static boolean isProduct = false;

    public static List<Map<String,Object>> kNode = null;
    
    public static void init() {
    	//日志格式
    	System.setProperty("java.util.logging.SimpleFormatter.format", "[%1$tF %1$tT] %4$s: %5$s %n");
    	//生产环境判断
    	Properties props=System.getProperties(); //获得系统属性集    
		String osName = props.getProperty("os.name"); //操作系统名称
		if(!osName.contains("Windows")) {
			isProduct = true;
		}
		//数据库初始化
    	DBUtil.dbInit();
    	SHA1.getRandomNodeId();
    	kNode = DBUtil.search("select * from BT_DHT_NODE");
    }
    
    public static String randomKNode(){
		Random random = new Random();
		String node = "";
		for(int i=0;i<8;i++) {
			int nextInt = random.nextInt(kNode.size());
			Map<String, Object> map = kNode.get(nextInt);
			String NODE_ID = map.get("NODE_ID").toString();
			String NODE_IP = map.get("NODE_IP").toString();;
			int NODE_PORT = Integer.parseInt(map.get("NODE_PORT").toString());
			node+=(NODE_ID+NODE_IP+NODE_PORT);
		}
		return node;
    }
    
	public static void main(String[] args) {
		init();
		MyLogger.log(DHTClientBoot.class,"----------------------服务开启----------------------");
		UDPSocket.serverBootstrap();
		MyLogger.log(DHTClientBoot.class,"----------------------服务关闭----------------------");
	}
}

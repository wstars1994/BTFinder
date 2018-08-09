package com.boomzz.main;

import java.util.Properties;

import com.boomzz.main.util.DBUtil;
import com.boomzz.main.util.DHTUtil;
import com.boomzz.main.util.MyLogger;
import com.boomzz.main.util.SHA1;
import com.boomzz.main.util.UDPSocket;

public class DHTServerBoot {
    public static boolean isProduct = false;
    
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
    	DHTUtil.NODE_ID = SHA1.getRandomNodeId();
    }
    
	public static void main(String[] args) {
		init();
		MyLogger.log(DHTClientBoot.class,"----------------------服务开启----------------------");
		UDPSocket.serverBootstrap();
		MyLogger.log(DHTClientBoot.class,"----------------------服务关闭----------------------");
	}
}

package com.boomzz.main;

import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.boomzz.main.memory.NodeMemory;
import com.boomzz.main.packet.DHTPacketFindNode;
import com.boomzz.main.thread.DelDuplicateNodeThread;
import com.boomzz.main.util.DBUtil;
import com.boomzz.main.util.DHTUtil;
import com.boomzz.main.util.MyLogger;
import com.boomzz.main.util.SHA1;

public class DHTClientBoot {
    public static boolean isProduct = false;
    private static DelDuplicateNodeThread delDuplicateNodeThread = null;
    
    public static void init() {
    	//日志格式
    	System.setProperty("java.util.logging.SimpleFormatter.format", "[%1$tF %1$tT] %4$s: %5$s %n");
    	//生产环境判断
    	Properties props=System.getProperties(); //获得系统属性集    
		String osName = props.getProperty("os.name"); //操作系统名称
		if(!osName.contains("Windows")) isProduct = true;
		//数据库初始化
    	DBUtil.dbInit();
    	//清除重复数据
    	delDuplicateNodeThread = new DelDuplicateNodeThread();
    	delDuplicateNodeThread.start();
    	DHTUtil.NODE_ID = SHA1.getRandomNodeId();
    	//添加超级节点
    	NodeMemory.addNode(null,"39.105.49.154",8091);
//    	NodeMemory.addNode(null,"dht.transmissionbt.com",6881);
//    	NodeMemory.addNode(null,"router.utorrent.com",6881);
//    	NodeMemory.addNode(null,"router.bittorrent.com",6881);
    }
    
	public static void main(String[] args) {
		try {
			DHTClientBoot.init();
			MyLogger.log(DHTClientBoot.class,"----------------------START----------------------");
			List<Map<String, Object>> nodes = NodeMemory.getNodes();
			while (true) {
				if(nodes.size()==0) break;
				Map<String, Object> n = nodes.get(0);
				String ip = n.get("ip").toString();
				int port = Integer.parseInt(n.get("port").toString());
				DHTUtil.requestData(new DHTPacketFindNode(),DHTUtil.NODE_ID,ip, port);
				nodes = NodeMemory.getNodes();
				nodes.remove(0);
			}
			MyLogger.log(DHTClientBoot.class,"----------------------OVER----------------------");
			if(delDuplicateNodeThread!=null&&delDuplicateNodeThread.isAlive()) {
				delDuplicateNodeThread.stop();
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
}

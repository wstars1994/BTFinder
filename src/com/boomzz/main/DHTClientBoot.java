/**
 * author : 王新晨
 * date : 2018年7月31日 下午4:51:59
 */
package com.boomzz.main;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Properties;

import com.boomzz.main.db.DBUtil;
import com.boomzz.main.packet.DHTPacketFindNode;

public class DHTClientBoot {

    private final static int MAX_LENGTH = 1024; // 最大接收字节长度
    private final static int PORT_NUM   = 8091;   // port号
    private static byte[] receMsgs = new byte[MAX_LENGTH];
    private static DatagramSocket datagramSocket;
    private static DatagramPacket datagramPacket;
	
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
    	DBUtil.init();
    }
    
	public static void main(String[] args) {
//		//join dht
		try {
			init();
			MyLogger.log(DHTClientBoot.class,"----------------------START----------------------");
//			//target
			MyLogger.log(DHTClientBoot.class,"准备加入 : router.bittorrent.com");
			DHTUtil.requestData(new DHTPacketFindNode(),DHTUtil.NODE_ID, "router.bittorrent.com", 6881);
			MyLogger.log(DHTClientBoot.class,"准备加入 : dht.transmissionbt.com");
			DHTUtil.requestData(new DHTPacketFindNode(),DHTUtil.NODE_ID, "dht.transmissionbt.com", 6881);
			MyLogger.log(DHTClientBoot.class,"准备加入 : router.utorrent.com");
			DHTUtil.requestData(new DHTPacketFindNode(),DHTUtil.NODE_ID, "router.utorrent.com", 6881);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		MyLogger.log(DHTClientBoot.class,"----------------------OVER----------------------");
//		while (true) {
//			try {
//				datagramSocket = new DatagramSocket(PORT_NUM);
//				datagramSocket.setSoTimeout(30*60*1000);
//				datagramPacket = new DatagramPacket(receMsgs, receMsgs.length);
//				datagramSocket.receive(datagramPacket);
////				int i=data.length-1;
////		        for(;i>0;i--) if(data[i]!=0) break;
////		        byte[] copyByte = Arrays.copyOfRange(data, 0, i+1);
////		        ByteArrayInputStream byteArry = new ByteArrayInputStream(copyByte);
////		        LinkedHashMap<String, Object> map =new LinkedHashMap<String, Object>();
////				AbstractBencode.decodeRouter(PushbackInputStream(byteArry),map);
//				MyLogger.log(DHTServerBoot.class,"DHT服务收到消息 : "+ new String(receMsgs));
//			}catch (SocketTimeoutException e) {
//				MyLogger.log(DHTServerBoot.class,"超时重新启动");
//			}catch (Exception e) {
//				e.printStackTrace();
//			}finally {
//				if (datagramSocket != null) {
//					datagramSocket.close();
//				}
//			}
//		}
	}
}

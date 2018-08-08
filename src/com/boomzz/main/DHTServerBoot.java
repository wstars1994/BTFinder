package com.boomzz.main;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketTimeoutException;
import java.util.Properties;

import com.boomzz.main.db.DBUtil;

public class DHTServerBoot {
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
		MyLogger.log(DHTClientBoot.class,"----------------------服务开启----------------------");
		while (true) {
			try {
				datagramSocket = new DatagramSocket(PORT_NUM);
				datagramSocket.setSoTimeout(30*60*1000);
				datagramPacket = new DatagramPacket(receMsgs, receMsgs.length);
				datagramSocket.receive(datagramPacket);
//				int i=data.length-1;
//		        for(;i>0;i--) if(data[i]!=0) break;
//		        byte[] copyByte = Arrays.copyOfRange(data, 0, i+1);
//		        ByteArrayInputStream byteArry = new ByteArrayInputStream(copyByte);
//		        LinkedHashMap<String, Object> map =new LinkedHashMap<String, Object>();
//				AbstractBencode.decodeRouter(PushbackInputStream(byteArry),map);
				MyLogger.log(DHTServerBoot.class,"DHT服务收到消息 : "+ new String(receMsgs));
			}catch (SocketTimeoutException e) {
				MyLogger.log(DHTServerBoot.class,"超时重新启动");
			}catch (Exception e) {
				e.printStackTrace();
			}finally {
				if (datagramSocket != null) {
					datagramSocket.close();
				}
			}
		}
	}
}

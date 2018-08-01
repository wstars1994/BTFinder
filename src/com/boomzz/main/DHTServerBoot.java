/**
 * author : 王新晨
 * date : 2018年7月31日 下午4:51:59
 */
package com.boomzz.main;

import java.io.ByteArrayInputStream;
import java.io.PushbackInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Arrays;
import java.util.LinkedHashMap;

import com.boomzz.main.bencode.AbstractBencode;
import com.boomzz.main.db.DBUtil;
import com.boomzz.main.packet.DHTPacketFindNode;

public class DHTServerBoot {

	// 定义一些常量
    private final static int MAX_LENGTH = 1024; // 最大接收字节长度
    private final static int PORT_NUM   = 8091;   // port号
    // 用以存放接收数据的字节数组
    private static byte[] receMsgs = new byte[MAX_LENGTH];
    // 数据报套接字
    private static DatagramSocket datagramSocket;
    // 用以接收数据报
    private static DatagramPacket datagramPacket;
	
	public static void main(String[] args) {
//		//join dht
		try {
			DBUtil.init();
			MyLogger.log(DHTServerBoot.class,"------------------------------------------------");
//			//target
			MyLogger.log(DHTServerBoot.class,"准备加入 : router.bittorrent.com");
			DHTUtil.requestData(new DHTPacketFindNode(),DHTUtil.NODE_ID, "router.bittorrent.com", 6881);
			MyLogger.log(DHTServerBoot.class,"准备加入 : dht.transmissionbt.com");
			DHTUtil.requestData(new DHTPacketFindNode(),DHTUtil.NODE_ID, "dht.transmissionbt.com", 6881);
			MyLogger.log(DHTServerBoot.class,"准备加入 : router.utorrent.com");
			DHTUtil.requestData(new DHTPacketFindNode(),DHTUtil.NODE_ID, "router.utorrent.com", 6881);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
//		BTLogger.log(DHTServerBoot.class,"DHT服务开起,端口 : "+PORT_NUM);

//		while (true) {
//			try {
//				datagramSocket = new DatagramSocket(PORT_NUM);
//				datagramSocket.setSoTimeout(30*60*1000);
//				datagramPacket = new DatagramPacket(receMsgs, receMsgs.length);
//				datagramSocket.receive(datagramPacket);
//				int i=data.length-1;
//		        for(;i>0;i--) if(data[i]!=0) break;
//		        byte[] copyByte = Arrays.copyOfRange(data, 0, i+1);
//		        ByteArrayInputStream byteArry = new ByteArrayInputStream(copyByte);
//		        LinkedHashMap<String, Object> map =new LinkedHashMap<String, Object>();
//				AbstractBencode.decodeRouter(PushbackInputStream(byteArry),map);
//				BTLogger.log(DHTServerBoot.class,"DHT服务收到消息 : "+ new String(receMsgs));
//			}catch (SocketTimeoutException e) {
//				BTLogger.log(DHTServerBoot.class,"超时重新启动");
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

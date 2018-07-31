/**
 * author : 王新晨
 * date : 2018年7月31日 下午4:51:59
 */
package com.boomzz.main;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

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
		try {
			datagramSocket = new DatagramSocket(PORT_NUM);
			datagramPacket = new DatagramPacket(receMsgs, receMsgs.length);
			datagramSocket.receive(datagramPacket);
			System.out.println(new String(receMsgs));
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if (datagramSocket != null) {
                datagramSocket.close();
            }
		}
	}
}

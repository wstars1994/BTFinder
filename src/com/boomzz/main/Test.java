package com.boomzz.main;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Test {

	public static String NODE_ID = "2aa9df91320ea0260502";
	
	public static void main(String[] args) {
		
		DatagramSocket socket = null;
		try {
			socket = new DatagramSocket(1080);
			socket.setSoTimeout(3000);
			String pac = DHTPacket.getFindNodeBecode(Test.NODE_ID);
			DatagramPacket request = new DatagramPacket(pac.getBytes(),pac.getBytes().length,InetAddress.getByName("dht.transmissionbt.com"),6881);
//			DatagramPacket request = new DatagramPacket(pac.getBytes(),pac.getBytes().length,InetAddress.getByName("api.boomzz.com"),6666);
			socket.send(request);
            DatagramPacket response = new DatagramPacket(new byte[1024], 1024);
            socket.receive(response);
            System.out.println(new String(response.getData()));
            socket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

package com.boomzz.main;

import java.io.ByteArrayInputStream;
import java.io.PushbackInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Test {

	public static String NODE_ID = "2aa9df91320ea0260502";
	
	public static void main(String[] args) {
		
		DatagramSocket socket = null;
		try {
			socket = new DatagramSocket(1080);
			socket.setSoTimeout(5000);
			String pac = DHTPacket.getPeersBecode("6a6e224a9f643785a53bdeebfb5b1e6e7e92f4ec".toUpperCase());
			DatagramPacket request = new DatagramPacket(pac.getBytes(),pac.getBytes().length,InetAddress.getByName("router.bittorrent.com"),6881);
//			DatagramPacket request = new DatagramPacket(pac.getBytes(),pac.getBytes().length,InetAddress.getByName("api.boomzz.com"),6666);
			socket.send(request);
            DatagramPacket response = new DatagramPacket(new byte[1024], 1024);
            socket.receive(response);
            ByteArrayInputStream byteArry = new ByteArrayInputStream(response.getData());
            PushbackInputStream stream = new PushbackInputStream(byteArry);
            int i=0;
            String string="";
            while ((i=stream.read())!=-1) {
            	string+=((char)i+"");
            }
            System.out.print(Bencode.decode(string));
            socket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

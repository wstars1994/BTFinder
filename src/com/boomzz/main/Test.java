package com.boomzz.main;

import java.io.ByteArrayInputStream;
import java.io.PushbackInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Arrays;
import java.util.HashMap;

import com.boomzz.main.bencode.BencodeProxy;

public class Test {

	public static String NODE_ID = "2aa9df91320ea0260502";
	
	public static void main(String[] args) {
		
		DatagramSocket socket = null;
		try {
			socket = new DatagramSocket();
			socket.setSoTimeout(5000);
			String pac = DHTPacket.getPeersBecode("6a6e224a9f643785a53bdeebfb5b1e6e7e92f4ea".toUpperCase());
			DatagramPacket request = new DatagramPacket(pac.getBytes(),pac.getBytes().length,InetAddress.getByName("router.bittorrent.com"),6881);
			socket.send(request);
            DatagramPacket response = new DatagramPacket(new byte[1024], 1024);
            socket.receive(response);
            byte[] data = response.getData();
            int i=data.length-1;
            for(;i>0;i--) if(data[i]!=0) break;
            byte[] copyByte = Arrays.copyOfRange(data, 0, i+1);
            ByteArrayInputStream byteArry = new ByteArrayInputStream(copyByte);
            PushbackInputStream stream = new PushbackInputStream(byteArry);
            HashMap<String, Object> map =new HashMap<String, Object>();
            BencodeProxy.process(stream,map);
            socket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

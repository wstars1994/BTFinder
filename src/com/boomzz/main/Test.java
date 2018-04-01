package com.boomzz.main;

import java.io.ByteArrayInputStream;
import java.io.PushbackInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Arrays;
import java.util.LinkedHashMap;

import com.boomzz.main.bencode.Bencode;

public class Test {

	public static String NODE_ID = "2aa9df91320ea0260502";
	
	public static void main(String[] args) {
		
		DatagramSocket socket = null;
		try {
			socket = new DatagramSocket();
			socket.setSoTimeout(5000);
			String pac = DHTPacket.getFindNodeBecode("07f733e9806302b968cbb79e80c0caf36c6bfb1e");
			DatagramPacket request = new DatagramPacket(pac.getBytes(),pac.getBytes().length,InetAddress.getByName("api.boomzz.com"),6666);
			socket.send(request);
            DatagramPacket response = new DatagramPacket(new byte[1024], 1024);
            socket.receive(response);
            byte[] data = response.getData();
            int i=data.length-1;
            for(;i>0;i--) if(data[i]!=0) break;
            byte[] copyByte = Arrays.copyOfRange(data, 0, i+1);
            ByteArrayInputStream byteArry = new ByteArrayInputStream(copyByte);
            PushbackInputStream stream = new PushbackInputStream(byteArry);
            LinkedHashMap<String, Object> map =new LinkedHashMap<String, Object>();
            Bencode.process(stream,map);
            System.out.println(map);
            LinkedHashMap<String, Object> r = (LinkedHashMap<String, Object>) map.get("r");
            String nodeStr = (String) r.get("nodes");
            String[] split = nodeStr.split("#");
            for(String str:split) {
            	System.out.println(str.split("/")[1]);
            }
            socket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

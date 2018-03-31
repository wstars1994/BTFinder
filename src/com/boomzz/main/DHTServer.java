package com.boomzz.main;

import java.io.FileWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Date;
import java.util.LinkedHashMap;

import com.boomzz.main.bencode.Bencode;

public class DHTServer {

	public static String NODE_ID = "2aa9df91320ea0260502";
	public static String LOG_PATH = "/root/dht.log";
	
	public static void main(String[] args) {
		try {
			FileWriter fileWriter = new FileWriter(LOG_PATH, true);
			while(true) {
				StringBuffer buffer = new StringBuffer();
				buffer.append("["+new Date().toLocaleString()+"] ");
				DatagramSocket socket = new DatagramSocket(6666);
				socket.setSoTimeout(1000*60*60*24);
				DatagramPacket response = new DatagramPacket(new byte[1024], 1024);
				socket.receive(response);
				buffer.append("From:"+response.getAddress()+":"+response.getPort()+" ");
				byte[] data = response.getData();
				LinkedHashMap<String, Object> map = (LinkedHashMap<String, Object>) Bencode.decode(new String(data));
				buffer.append("Msg:"+map+"\r\n");
				fileWriter.write(buffer.toString());
				String reponseBecode = DHTPacket.getReponseBecode("");
				DatagramPacket request = new DatagramPacket(reponseBecode.getBytes(),reponseBecode.getBytes().length,response.getAddress(), response.getPort());
				socket.send(request);
				socket.close();
				fileWriter.flush();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
}

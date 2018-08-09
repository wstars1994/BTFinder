package com.boomzz.main.util;

import java.io.ByteArrayInputStream;
import java.io.PushbackInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.util.Arrays;
import java.util.LinkedHashMap;

import com.boomzz.main.DHTServerBoot;
import com.boomzz.main.bencode.AbstractBencode;

public class UDPSocket {
	private final static int MAX_LENGTH = 1024; // 最大接收字节长度
    private final static int PORT_NUM   = 8091;   // port号
	
	public static PushbackInputStream request(String host,int port,byte[] requests) {
		DatagramSocket socket = null;
		try {
			socket = new DatagramSocket(PORT_NUM);
			socket.setSoTimeout(5000);
			DatagramPacket request = new DatagramPacket(requests,requests.length,InetAddress.getByName(host),port);
			socket.send(request);
            DatagramPacket response = new DatagramPacket(new byte[1024], 1024);
            socket.receive(response);
            byte[] data = response.getData();
            int i=data.length-1;
            for(;i>0;i--) if(data[i]!=0) break;
            byte[] copyByte = Arrays.copyOfRange(data, 0, i+1);
            return new PushbackInputStream(new ByteArrayInputStream(copyByte));
		}catch (SocketTimeoutException e) {
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if(socket!=null) {
				socket.close();
			}
		}
	}

	public static PushbackInputStream serverBootstrap() {
	    byte[] receMsgs = new byte[MAX_LENGTH];
	    DatagramSocket datagramSocket = null;
	    DatagramPacket datagramPacket = null;
		while (true) {
			try {
				datagramSocket = new DatagramSocket(PORT_NUM);
				datagramSocket.setSoTimeout(30*60*1000);
				datagramPacket = new DatagramPacket(receMsgs, receMsgs.length);
				datagramSocket.receive(datagramPacket);
				byte[] data = datagramPacket.getData();
				int i=data.length-1;
		        for(;i>0;i--) if(data[i]!=0) break;
		        byte[] copyByte = Arrays.copyOfRange(data, 0, i+1);
		        ByteArrayInputStream byteArry = new ByteArrayInputStream(copyByte);
		        LinkedHashMap<String, Object> map =new LinkedHashMap<String, Object>();
				AbstractBencode.decodeRouter(new PushbackInputStream(byteArry),map);
				
				MyLogger.log(DHTServerBoot.class,"DHT服务收到消息 : "+ map);
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

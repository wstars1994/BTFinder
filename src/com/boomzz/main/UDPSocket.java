package com.boomzz.main;

import java.io.ByteArrayInputStream;
import java.io.PushbackInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.util.Arrays;

public class UDPSocket {

	public static PushbackInputStream request(String host,int port,byte[] requests) {
		DatagramSocket socket = null;
		try {
			socket = new DatagramSocket(8091);
			socket.setSoTimeout(5000);
			DatagramPacket request = new DatagramPacket(requests,requests.length,InetAddress.getByName(host),port);
			socket.send(request);
            DatagramPacket response = new DatagramPacket(new byte[1024], 1024);
            socket.receive(response);
            byte[] data = response.getData();
            int i=data.length-1;
            for(;i>0;i--) if(data[i]!=0) break;
            byte[] copyByte = Arrays.copyOfRange(data, 0, i+1);
            ByteArrayInputStream byteArry = new ByteArrayInputStream(copyByte);
            return new PushbackInputStream(byteArry);
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
}

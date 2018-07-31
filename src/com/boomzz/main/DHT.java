package com.boomzz.main;

import java.io.PushbackInputStream;
import java.util.LinkedHashMap;

import com.boomzz.main.bencode.AbstractBencode;
import com.boomzz.main.packet.AbstractDHTPacket;

public class DHT {
	
	public static String NODE_ID = "abcdefghij0123456789";
	public static String TEST_INFO_HASH = "9C03E2FA8AC2F13BE279260A302DD49A63E031D9";
	
	public static LinkedHashMap<String, Object> requestData(AbstractDHTPacket abstractDHTPacket, String param,String url,int port) throws Exception{
    	byte pac[] = abstractDHTPacket.packet(param);
    	PushbackInputStream stream = UDPSocket.request(url, port, pac);
    	if(stream!=null) {
    		LinkedHashMap<String, Object> map =new LinkedHashMap<String, Object>();
    		AbstractBencode.decodeRouter(stream,map);
    		abstractDHTPacket.unpacket(map);
    		return map;
    	}else {
    		System.out.println("ERROR 请求无回应");
    	}
		return null;
	}
	
}

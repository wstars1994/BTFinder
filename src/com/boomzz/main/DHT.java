package com.boomzz.main;

import java.io.PushbackInputStream;
import java.util.LinkedHashMap;

import com.boomzz.main.bencode.AbstractBencode;
import com.boomzz.main.packet.AbstractDHTPacket;

public class DHT {
	
	public static String NODE_ID = "6669df91120ea0260503";
	public static String TEST_INFO_HASH = "9C03E2FA8AC2F13BE279260A302DD49A63E031D9";
	
	public static LinkedHashMap<String, Object> requestData(AbstractDHTPacket abstractDHTPacket, String infoHash,String url,int port) throws Exception{
    	byte pac[] = abstractDHTPacket.packet(infoHash);
    	PushbackInputStream stream = UDPSocket.request(url, port, pac);
    	if(stream!=null) {
    		LinkedHashMap<String, Object> map =new LinkedHashMap<String, Object>();
    		AbstractBencode.decodeRouter(stream,map);
    		abstractDHTPacket.unpacket(map);
    		return map;
    	}else {
    		System.out.println("E-------request error");
    	}
		return null;
	}
	
}

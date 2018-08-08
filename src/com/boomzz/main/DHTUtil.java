package com.boomzz.main;

import java.io.PushbackInputStream;
import java.util.LinkedHashMap;

import com.boomzz.main.bencode.AbstractBencode;
import com.boomzz.main.packet.AbstractDHTPacket;

public class DHTUtil {
	
	public static String NODE_ID = "8d7322c3fb1f1acd1aaa812164f39d22404f22f8"; //sha1(wstars1994)
//	public static String NODE_ID = "2c3ed5ca8440d3d186850df7326f03af6c14fad0"; //sha1(wstars1995)
	public static String TEST_INFO_HASH = "9C03E2FA8AC2F13BE279260A302DD49A63E031D9";
	
	public static void requestData(AbstractDHTPacket abstractDHTPacket, String param,String url,int port) throws Exception{
    	byte pac[] = abstractDHTPacket.reqPacket(param);
    	PushbackInputStream stream = UDPSocket.request(url, port, pac);
    	if(stream!=null) {
    		LinkedHashMap<String, Object> map =new LinkedHashMap<String, Object>();
    		AbstractBencode.decodeRouter(stream,map);
    		abstractDHTPacket.reqUnpacket(map,url,port);
    	}else {
    		MyLogger.log(DHTClientBoot.class,"× --- ["+url+":"+port+"]");
    	}
	}
	
}

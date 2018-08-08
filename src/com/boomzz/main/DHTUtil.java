package com.boomzz.main;

import java.io.PushbackInputStream;
import java.util.LinkedHashMap;

import com.boomzz.main.bencode.AbstractBencode;
import com.boomzz.main.packet.AbstractDHTPacket;

public class DHTUtil {
	
	public static String NODE_ID = null;
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

package com.boomzz.main.util;

import java.io.PushbackInputStream;
import java.util.LinkedHashMap;

import com.boomzz.main.DHTClientBoot;
import com.boomzz.main.bencode.AbstractBencode;
import com.boomzz.main.packet.AbstractDHTPacket;
import com.boomzz.main.packet.DHTPacketFindNode;

public class DHTUtil {
	
	public static String NODE_ID = null;
	
	public static void requestData(AbstractDHTPacket abstractDHTPacket, String param,String url,int port) throws Exception{
    	byte pac[] = abstractDHTPacket.requestPacket(param);
    	PushbackInputStream stream = UDPSocket.request(url, port, pac);
    	if(stream!=null) {
    		LinkedHashMap<String, Object> map =new LinkedHashMap<String, Object>();
    		AbstractBencode.decodeRouter(stream,map);
    		abstractDHTPacket.requestUnpacket(map,url,port);
    	}else {
    		MyLogger.log(DHTClientBoot.class,"× --- ["+url+":"+port+"]");
    	}
	}

	public static byte[] responseData(LinkedHashMap<String, Object> map) {
		Object object = map.get("y");
		if(object!=null&&"q".equals(object.toString())) {
			Object t = map.get("t");
			Object q = map.get("q");
			if(q!=null) {
				if("find_node".equals(q.toString())) {
					return new DHTPacketFindNode().responsePacket(t.toString());
				}
			}
		}
		return null;
	}
}

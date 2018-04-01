package com.boomzz.main;

import java.io.PushbackInputStream;
import java.util.LinkedHashMap;

import com.boomzz.main.bencode.AbstractBencode;

public class Test {

	public static String NODE_ID = "2aa9df91320ea0260502";
	public static void main(String[] args) {
        try {
        	String pac = DHTPacket.getPeersBecode("6a6e224a9f643785a53bdeebfb5b1e6e7e92f4ea".toUpperCase());
        	PushbackInputStream stream = UDPSocket.request("router.bittorrent.com", 6881, pac);
        	if(stream!=null) {
        		LinkedHashMap<String, Object> map =new LinkedHashMap<String, Object>();
        		AbstractBencode.process(stream,map);
        		System.out.println(map);
        		LinkedHashMap<String, Object> r = (LinkedHashMap<String, Object>) map.get("r");
        		String nodeStr = (String) r.get("nodes");
        		String[] split = nodeStr.split("#");
        		for(String str:split) {
        			System.out.println(str.split("/")[1]);
        		}
        	}else {
        		System.out.println("request error");
        	}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

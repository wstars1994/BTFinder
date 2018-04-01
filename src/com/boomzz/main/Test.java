package com.boomzz.main;

import java.io.PushbackInputStream;
import java.util.LinkedHashMap;

import com.boomzz.main.bencode.AbstractBencode;
import com.boomzz.main.packet.AbstractDHTPacket;
import com.boomzz.main.packet.DHTPacketPeers;

public class Test {

	public static String NODE_ID = "2aa9df91320ea0260502";

	public static void main(String[] args) {
        try {
        	
        	AbstractDHTPacket abstractDHTPacket = new DHTPacketPeers();
        	String pac = abstractDHTPacket.packet("6a6e224a9f643785a53bdeebfb5b1e6e7e92f4ea".toUpperCase());
        	PushbackInputStream stream = UDPSocket.request("router.bittorrent.com", 6881, pac);
        	if(stream!=null) {
        		LinkedHashMap<String, Object> map =new LinkedHashMap<String, Object>();
        		AbstractBencode.process(stream,map);
        		abstractDHTPacket.unpacket(map);
        	}else {
        		System.out.println("request error");
        	}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

package com.boomzz.main;

import java.io.PushbackInputStream;
import java.util.LinkedHashMap;

import com.boomzz.main.bencode.AbstractBencode;
import com.boomzz.main.packet.AbstractDHTPacket;
import com.boomzz.main.packet.DHTPacketFindNode;
import com.boomzz.main.packet.DHTPacketPeers;

public class Test {

	public static String NODE_ID = "2aa9df91320ea0260502";

	public static void main(String[] args) {
        try {
        	AbstractDHTPacket abstractDHTPacket = new DHTPacketPeers();
        	String upperCase = "9C03E2FA8AC2F13BE279260A302DD49A63E031D9";
        	byte[] hexStringToBytes = StreamUtil.hexStringToBytes(upperCase);
        	String pac = abstractDHTPacket.packet(new String(hexStringToBytes));
        	PushbackInputStream stream = UDPSocket.request("router.bittorrent.com", 6881, pac);
        	if(stream!=null) {
        		LinkedHashMap<String, Object> map =new LinkedHashMap<String, Object>();
        		AbstractBencode.process(stream,map);
        		abstractDHTPacket.unpacket(map);
        	}else {
        		System.out.println("E-------request error");
        	}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

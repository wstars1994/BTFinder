package com.boomzz.main;

import java.util.LinkedHashMap;

import com.boomzz.main.packet.DHTPacketPeers;

public class Test {

	public static String NODE_ID = "2aa9df91320ea0260502";

	public static void main(String[] args) {
        try {
        	LinkedHashMap<String, Object> requestData = DHT.requestData(new DHTPacketPeers(),"9C03E2FA8AC2F13BE279260A302DD49A63E031D9", "router.bittorrent.com", 6881);
        	if(requestData!=null) {
        		System.out.println(requestData);
        	}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

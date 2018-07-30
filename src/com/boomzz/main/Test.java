package com.boomzz.main;

import java.util.LinkedHashMap;

import com.boomzz.main.packet.DHTPacketPeers;

public class Test {

	public static void main(String[] args) {
        
//		("router.bittorrent.com", 6881),
//	    ("dht.transmissionbt.com", 6881),
//	    ("router.utorrent.com", 6881)
		//47.180.87.247:11339 VALUES
		
		String infoHash = "9C03E2FA8AC2F13BE279260A302DD49A63E031D9";
		
		try {
        	LinkedHashMap<String, Object> requestData = DHT.requestData(new DHTPacketPeers(),infoHash, "router.utorrent.com", 6881);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

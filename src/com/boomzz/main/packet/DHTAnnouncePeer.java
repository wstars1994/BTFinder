package com.boomzz.main.packet;

import java.util.LinkedHashMap;

import com.boomzz.main.DHT;
import com.boomzz.main.bencode.AbstractBencode;

public class DHTAnnouncePeer extends AbstractDHTPacket {

	private Object token = null;
	public DHTAnnouncePeer(Object token) {
		this.token = token;
	}

	@Override
	public byte[] packet(String... param) {
		LinkedHashMap<String, Object> map  = new LinkedHashMap<>();
		map.put("t", "bz");
		map.put("y", "q");
		map.put("q", "announce_peer");
		LinkedHashMap<String, Object> a  = new LinkedHashMap<>();
		a.put("id", DHT.NODE_ID);
		a.put("info_hash",param[0]);
		a.put("token",token);
		a.put("port",10756);
		map.put("a", a);
		return AbstractBencode.encodeRouter(map);
	}

	@Override
	public Object unpacket(LinkedHashMap<String, Object> map) {
		
		System.out.println(map);
		
		
		
		return null;
	}

}

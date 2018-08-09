package com.boomzz.main.packet;

import java.util.LinkedHashMap;

import com.boomzz.main.bencode.AbstractBencode;
import com.boomzz.main.util.DHTUtil;

public class DHTPacketAnnouncePeer extends AbstractDHTPacket {

	private Object token = null;
	public DHTPacketAnnouncePeer(Object token) {
		this.token = token;
	}

	@Override
	public byte[] requestPacket(String... param) {
		LinkedHashMap<String, Object> map  = new LinkedHashMap<>();
		map.put("t", "bz");
		map.put("y", "q");
		map.put("q", "announce_peer");
		LinkedHashMap<String, Object> a  = new LinkedHashMap<>();
		a.put("id", DHTUtil.NODE_ID);
		a.put("info_hash",param[0]);
		a.put("token",token);
		a.put("port",10756);
		map.put("a", a);
		return AbstractBencode.encodeRouter(map);
	}

	@Override
	public Object requestUnpacket(LinkedHashMap<String, Object> map,String oIp, int oPort) {
		
		System.out.println(map);
		
		
		
		return null;
	}

	@Override
	public byte[] responsePacket(String... param) {
		LinkedHashMap<String, Object> map  = new LinkedHashMap<>();
		map.put("t", param[0]);
		map.put("y", "r");
		LinkedHashMap<String, Object> a  = new LinkedHashMap<>();
		a.put("id",DHTUtil.NODE_ID);
		map.put("r",a);
		return AbstractBencode.encodeRouter(map);
	}

}

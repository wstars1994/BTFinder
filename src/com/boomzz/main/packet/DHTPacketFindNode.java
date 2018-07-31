package com.boomzz.main.packet;

import java.util.LinkedHashMap;

import com.boomzz.main.DHTConfig;
import com.boomzz.main.bencode.AbstractBencode;

public class DHTPacketFindNode extends AbstractDHTPacket {

	@Override
	public byte[] packet(String... param) {
		LinkedHashMap<String, Object> map  = new LinkedHashMap<>();
		map.put("t", "bz");
		map.put("y", "q");
		map.put("q", "find_node");
		LinkedHashMap<String, Object> a  = new LinkedHashMap<>();
		a.put("id", DHTConfig.NODE_ID);
		a.put("target",param[0]);
		map.put("a", a);
		return AbstractBencode.encodeRouter(map);
	}

	@Override
	public Object unpacket(LinkedHashMap<String, Object> map) {
		
		System.out.println(map);
		
		
		
		return null;
	}

}

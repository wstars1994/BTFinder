package com.boomzz.main.packet;

import java.util.LinkedHashMap;

import com.boomzz.main.DHTServer;
import com.boomzz.main.bencode.AbstractBencode;

public class DHTPacketPeers extends AbstractDHTPacket{

	@Override
	public String packet(String param) {
		LinkedHashMap<String, Object> map  = new LinkedHashMap<>();
		map.put("t", "bz");
		map.put("y", "q");
		map.put("q", "get_peers");
		LinkedHashMap<String, Object> a  = new LinkedHashMap<>();
		a.put("id", DHTServer.NODE_ID);
		a.put("info_hash",param);
		map.put("a", a);
		return AbstractBencode.encodeRouter(map);
	}

	@Override
	public Object unpacket(LinkedHashMap<String, Object> map) {
		LinkedHashMap<String, Object> r = (LinkedHashMap<String, Object>) map.get("r");
		String nodeStr = (String) r.get("nodes");
		String[] split = nodeStr.split("#");
		for(String str:split) {
			System.out.println(str.split("/")[1]);
		}
		return null;
	}

}

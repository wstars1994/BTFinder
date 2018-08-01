package com.boomzz.main.packet;

import java.util.LinkedHashMap;
import java.util.Map;

import com.boomzz.main.DHT;
import com.boomzz.main.bencode.AbstractBencode;

public class DHTPacketFindNode extends AbstractDHTPacket {

	@Override
	public byte[] packet(String... param) {
		LinkedHashMap<String, Object> map  = new LinkedHashMap<>();
		map.put("t", "bz");
		map.put("y", "q");
		map.put("q", "find_node");
		LinkedHashMap<String, Object> a  = new LinkedHashMap<>();
		a.put("id", DHT.NODE_ID);
		a.put("target",param[0]);
		map.put("a", a);
		return AbstractBencode.encodeRouter(map);
	}

	@Override
	public Object unpacket(LinkedHashMap<String, Object> map) {
		Map<String, Object> r = (Map<String, Object>) map.get("r");
		if(r!=null) {
			String nodesInfo = r.get("nodes").toString();
			String nodeArr[] = nodesInfo.split("#");
			for(String node:nodeArr) {
				String nodeId = node.split("/")[0];
				String ip = node.split("/")[1].split(":")[0];
				int port = Integer.parseInt(node.split("/")[1].split(":")[1]);
				try {
					System.out.print("nodeId : " + nodeId);
					System.out.print(" ip : " + ip);
					System.out.println(" port : " + port);
					LinkedHashMap<String, Object> requestData = DHT.requestData(new DHTPacketPing(),"1",ip, port);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		return null;
	}

}

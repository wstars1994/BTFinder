package com.boomzz.main.packet;

import java.util.LinkedHashMap;
import java.util.Map;

import com.boomzz.main.DHTClientBoot;
import com.boomzz.main.DHTServerBoot;
import com.boomzz.main.bencode.AbstractBencode;
import com.boomzz.main.memory.NodeMemory;
import com.boomzz.main.util.DBUtil;
import com.boomzz.main.util.DHTUtil;
import com.boomzz.main.util.MyLogger;

public class DHTPacketFindNode extends AbstractDHTPacket {

	@Override
	public byte[] requestPacket(String... param) {
		LinkedHashMap<String, Object> map = new LinkedHashMap<>();
		map.put("t", "bz");
		map.put("y", "q");
		map.put("q", "find_node");
		LinkedHashMap<String, Object> a = new LinkedHashMap<>();
		a.put("id", DHTUtil.NODE_ID);
		a.put("target", param[0]);
		map.put("a", a);
		return AbstractBencode.encodeRouter(map);
	}

	@Override
	public Object requestUnpacket(LinkedHashMap<String, Object> map, String oIp, int oPort) {
		try {
			Map<String, Object> r = (Map<String, Object>) map.get("r");
			MyLogger.log(DHTClientBoot.class, "√ --- [" + oIp + ":" + oPort + "] " + map.size());
			DBUtil.execute("INSERT INTO BT_DHT_NODE(NODE_ID,NODE_IP,NODE_PORT) VALUES('" + r.get("id").toString()+ "','" + oIp + "','" + oPort + "')");
			if (r != null) {
				String nodesInfo = r.get("nodes").toString();
				String nodeArr[] = nodesInfo.split("#");
				for (String node : nodeArr) {
					String nodeId = node.split("/")[0];
					String ip = node.split("/")[1].split(":")[0];
					int port = Integer.parseInt(node.split("/")[1].split(":")[1]);
					NodeMemory.addNode(nodeId, ip, port);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public byte[] responsePacket(String... param) {
		LinkedHashMap<String, Object> map = new LinkedHashMap<>();
		map.put("t",param[0]);
		map.put("y", "r");
		LinkedHashMap<String, Object> a = new LinkedHashMap<>();
		a.put("id", DHTUtil.NODE_ID);
		a.put("nodes",DHTServerBoot.randomKNode());
		map.put("r", a);
		return AbstractBencode.encodeRouter(map);
	}

}

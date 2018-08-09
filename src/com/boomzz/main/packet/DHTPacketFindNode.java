package com.boomzz.main.packet;

import java.util.LinkedHashMap;
import java.util.Map;

import com.boomzz.main.DHTClientBoot;
import com.boomzz.main.DHTUtil;
import com.boomzz.main.MyLogger;
import com.boomzz.main.bencode.AbstractBencode;
import com.boomzz.main.db.DBUtil;
import com.boomzz.main.memory.NodeMemory;

public class DHTPacketFindNode extends AbstractDHTPacket {

	@Override
	public byte[] reqPacket(String... param) {
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
	public Object reqUnpacket(LinkedHashMap<String, Object> map, String oIp, int oPort) {
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
	public byte[] repPacket(String... param) {
		return null;
	}

}

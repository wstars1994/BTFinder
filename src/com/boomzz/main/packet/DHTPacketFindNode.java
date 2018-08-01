package com.boomzz.main.packet;

import java.util.LinkedHashMap;
import java.util.Map;

import com.boomzz.main.DHTUtil;
import com.boomzz.main.bencode.AbstractBencode;
import com.boomzz.main.db.DBUtil;

public class DHTPacketFindNode extends AbstractDHTPacket {

	@Override
	public byte[] reqPacket(String... param) {
		LinkedHashMap<String, Object> map  = new LinkedHashMap<>();
		map.put("t", "bz");
		map.put("y", "q");
		map.put("q", "find_node");
		LinkedHashMap<String, Object> a  = new LinkedHashMap<>();
		a.put("id", DHTUtil.NODE_ID);
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
					LinkedHashMap<String, Object> requestData = DHTUtil.requestData(new DHTPacketPing(),"1",ip, port);
					if(requestData!=null) {
						DBUtil.execute("INSERT INTO BT_DHT_NODE(NODE_ID,NODE_IP,NODE_PORT) VALUES('"+nodeId+"','"+ip+"','"+port+"')");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		return null;
	}

	/* (non-Javadoc)
	 * @see com.boomzz.main.packet.AbstractDHTPacket#repPacket(java.lang.String[])
	 */
	@Override
	public byte[] repPacket(String... param) {
		// TODO Auto-generated method stub
		return null;
	}

}

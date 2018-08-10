package com.boomzz.main.packet;

import java.util.LinkedHashMap;
import java.util.List;

import com.boomzz.main.DHTServerBoot;
import com.boomzz.main.bencode.AbstractBencode;
import com.boomzz.main.bencode.model.ObjectBytesModel;
import com.boomzz.main.util.DHTUtil;

public class DHTPacketPeers extends AbstractDHTPacket{

	@Override
	public byte[] requestPacket(String... param) {
		LinkedHashMap<String, Object> map  = new LinkedHashMap<>();
		map.put("t", "bz");
		map.put("y", "q");
		map.put("q", "get_peers");
		LinkedHashMap<String, Object> a  = new LinkedHashMap<>();
		a.put("id", DHTUtil.NODE_ID);
		a.put("info_hash",param[0]);
		map.put("a", a);
		return AbstractBencode.encodeRouter(map);
	}

	@Override
	public Object requestUnpacket(LinkedHashMap<String, Object> map,String oIp, int oPort) {
		String y = (String) map.get("y");
		if("e".equals(y)) {
			System.out.println("ERROR:"+map.get("e"));
			System.out.println();
			return null;
		}
		System.out.println(map);
		LinkedHashMap<String, Object> r = (LinkedHashMap<String, Object>) map.get("r");
		String nodeStr = (String) r.get("nodes");
		Object valuesObj = r.get("values");
		Object token = r.get("token");
		if(valuesObj!=null) {
			List<ObjectBytesModel> values = (List<ObjectBytesModel>) valuesObj;
			for(ObjectBytesModel obm : values) {
				try {
					String arr[] = obm.getObject().toString().split(":");
//					DHTUtil.requestData(new DHTAnnouncePeer(token), DHTUtil.TEST_INFO_HASH,arr[0],Integer.parseInt(arr[1]));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		String[] split = nodeStr.split("#");
		for(String str:split) {
			
			String addr[] = str.split("/")[1].split(":");
			
			System.out.println(str.split("/")[1]);
			try {
//				DHTUtil.requestData(new DHTPacketPeers(),DHTUtil.TEST_INFO_HASH,addr[0], Integer.parseInt(addr[1]));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public byte[] responsePacket(String... param) {
		LinkedHashMap<String, Object> map = new LinkedHashMap<>();
		map.put("t",param[0]);
		map.put("y", "r");
		LinkedHashMap<String, Object> a = new LinkedHashMap<>();
		a.put("id",param[1]);
		a.put("token","tokenabc");
		a.put("nodes",DHTServerBoot.randomKNode());
		map.put("r", a);
		return AbstractBencode.encodeRouter(map);
	}

}

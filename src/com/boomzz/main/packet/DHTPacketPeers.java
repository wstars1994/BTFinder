package com.boomzz.main.packet;

import java.util.LinkedHashMap;
import java.util.List;

import com.boomzz.main.DHT;
import com.boomzz.main.bencode.AbstractBencode;
import com.boomzz.main.bencode.model.ObjectBytesModel;

public class DHTPacketPeers extends AbstractDHTPacket{

	@Override
	public byte[] packet(String... param) {
		LinkedHashMap<String, Object> map  = new LinkedHashMap<>();
		map.put("t", "bz");
		map.put("y", "q");
		map.put("q", "get_peers");
		LinkedHashMap<String, Object> a  = new LinkedHashMap<>();
		a.put("id", DHT.NODE_ID);
		a.put("info_hash",param[0]);
		map.put("a", a);
		return AbstractBencode.encodeRouter(map);
	}

	@Override
	public Object unpacket(LinkedHashMap<String, Object> map) {
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
					LinkedHashMap<String, Object> requestData = DHT.requestData(new DHTAnnouncePeer(token), DHT.TEST_INFO_HASH,arr[0],Integer.parseInt(arr[1]));
					System.out.println(requestData);
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
				LinkedHashMap<String, Object> requestData = DHT.requestData(new DHTPacketPeers(),DHT.TEST_INFO_HASH,addr[0], Integer.parseInt(addr[1]));
				if(requestData!=null)
					System.out.println("request again : " + requestData);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}

}
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
		a.put("info_hash",param);
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
		if(valuesObj!=null) {
			List<ObjectBytesModel> values = (List<ObjectBytesModel>) valuesObj;
			for(ObjectBytesModel obm : values) {
				String infoHash = "9C03E2FA8AC2F13BE279260A302DD49A63E031D9";
				try {
					String arr[] = obm.getObject().toString().split(":");
					LinkedHashMap<String, Object> requestData = DHT.requestData(new DHTAnnouncePeer(), infoHash,arr[0],Integer.parseInt(arr[1]));
					System.out.println(requestData);
				} catch (Exception e) {
					e.printStackTrace();
				}
	        	break;
			}
		}
		String[] split = nodeStr.split("#");
		for(String str:split) {
			System.out.println(str.split("/")[1]);
//			AbstractDHTPacket abstractDHTPacket = new DHTPacketPeers();
//			String upperCase = "9C03E2FA8AC2F13BE279260A302DD49A63E031D9";
//        	byte[] pac = abstractDHTPacket.packet(upperCase);
//			PushbackInputStream stream = UDPSocket.request(str.split("/")[1].split(":")[0], Integer.parseInt(str.split("/")[1].split(":")[1]), pac);
//			if(stream!=null) {
//				LinkedHashMap<String, Object> resmap =new LinkedHashMap<String, Object>();
//				try {
//					AbstractBencode.decodeRouter(stream,resmap);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//				abstractDHTPacket.unpacket(resmap);
//			}else {
//				System.out.println("E---------request error");
//				System.out.println();
//			}
		}
		return null;
	}

}

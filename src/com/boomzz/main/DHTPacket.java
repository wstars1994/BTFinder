package com.boomzz.main;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class DHTPacket {

	public static String getPingBecode(int type){
		LinkedHashMap<String, Object> map  = new LinkedHashMap<>();
		map.put("t", "bz");
		if(type==1) {
			map.put("y", "q");
			map.put("q", "ping");
			LinkedHashMap<String, Object> a  = new LinkedHashMap<>();
			a.put("id", DHTServer.NODE_ID);
			map.put("a",a);
		}else {
			map.put("y", "e");
			List<Object> list = new ArrayList<>();
			list.add(201);
			list.add("error");
			map.put("e",list);
		}
		return Bencode.encode(map);
	}
	
	public static String getFindNodeBecode(String target){
		LinkedHashMap<String, Object> map  = new LinkedHashMap<>();
		map.put("t", "bz");
		map.put("y", "q");
		map.put("q", "find_node");
		LinkedHashMap<String, Object> a  = new LinkedHashMap<>();
		a.put("id", DHTServer.NODE_ID);
		a.put("target",target);
		map.put("a", a);
		return Bencode.encode(map);
	}
	public static String getPeersBecode(String target){
		LinkedHashMap<String, Object> map  = new LinkedHashMap<>();
		map.put("t", "bz");
		map.put("y", "q");
		map.put("q", "get_peers");
		LinkedHashMap<String, Object> a  = new LinkedHashMap<>();
		a.put("id", DHTServer.NODE_ID);
		a.put("info_hash",target);
		map.put("a", a);
		return Bencode.encode(map);
	}
	
	public static String getReponseBecode(String receiveMsg){
//		LinkedHashMap<String, Object> receiveMsgMap = (LinkedHashMap<String, Object>) Bencode.decode(receiveMsg);
		LinkedHashMap<String, Object> map  = new LinkedHashMap<>();
		map.put("t", "bz");
		map.put("y", "r");
		LinkedHashMap<String, Object> a  = new LinkedHashMap<>();
		a.put("id", DHTServer.NODE_ID);
		map.put("r", a);
		return Bencode.encode(map);
	}
	
}

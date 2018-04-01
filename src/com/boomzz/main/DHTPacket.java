package com.boomzz.main;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import com.boomzz.main.bencode.AbstractBencode;

public class DHTPacket {

	public static byte[] getPingBecode(int type){
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
		return AbstractBencode.encodeRouter(map);
	}
	
	public static byte[] getFindNodeBecode(String target){
		LinkedHashMap<String, Object> map  = new LinkedHashMap<>();
		map.put("t", "bz");
		map.put("y", "q");
		map.put("q", "find_node");
		LinkedHashMap<String, Object> a  = new LinkedHashMap<>();
		a.put("id", DHTServer.NODE_ID);
		a.put("target",target);
		map.put("a", a);
		return AbstractBencode.encodeRouter(map);
	}
	
	public static byte[] getPeersBecode(String target){
		LinkedHashMap<String, Object> map  = new LinkedHashMap<>();
		map.put("t", "bz");
		map.put("y", "q");
		map.put("q", "get_peers");
		LinkedHashMap<String, Object> a  = new LinkedHashMap<>();
		a.put("id", DHTServer.NODE_ID);
		a.put("info_hash",target);
		map.put("a", a);
		return AbstractBencode.encodeRouter(map);
	}
	
	public static byte[] getReponseBecode(String receiveMsg){
		LinkedHashMap<String, Object> map  = new LinkedHashMap<>();
		map.put("t", "bz");
		map.put("y", "r");
		LinkedHashMap<String, Object> a  = new LinkedHashMap<>();
		a.put("id", DHTServer.NODE_ID);
		map.put("r", a);
		return AbstractBencode.encodeRouter(map);
	}
	
}

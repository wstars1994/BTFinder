package com.boomzz.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Bencode {
	
	public static String encode(Object object) {
		if(object instanceof String) {
			return encodeString(object.toString());
		}
		if(object instanceof Integer) {
			return encodeInteger((int)object);
		}
		if(object instanceof HashMap) {
			LinkedHashMap<String, Object> hashMap = (LinkedHashMap<String, Object>) object;
			return encodeMap(hashMap);
		}
		if(object instanceof List) {
			ArrayList<Object> list = (ArrayList<Object>) object;
			return encodeList(list);
		}
		return null;
	}
	private static String decodeStr = null;
	public static Object decode(String encodeStr) {
		Bencode.decodeStr = encodeStr;
		return decodeParse(encodeStr);
	}
	private static Object decodeParse(String encodeStr){
		if(encodeStr.equals("")||encodeStr==null) {
			return null;
		}
		String type = encodeStr.charAt(0)+"";
		try {
			Integer.parseInt(type);
			int indexOf = encodeStr.indexOf(":");
			String lengthStr = encodeStr.substring(0, indexOf);
			int length = Integer.parseInt(lengthStr);
			Bencode.decodeStr = Bencode.decodeStr.substring(indexOf+length+1,Bencode.decodeStr.length());
			return encodeStr.substring(indexOf+1, length+1+lengthStr.length());
		} catch (Exception e) { }
		if("l".equals(type)) {
			Bencode.decodeStr = Bencode.decodeStr.substring(1,encodeStr.length()-1);
			ArrayList<Object> arrayList = new ArrayList<>();
			Object decode = null;
			while((decode = decodeParse(Bencode.decodeStr))!=null) {
				arrayList.add(decode);
			}
			return arrayList;
		}
		if("i".equals(type)) {
			int indexOf = encodeStr.indexOf("e");
			Bencode.decodeStr = Bencode.decodeStr.substring(indexOf+1,Bencode.decodeStr.length());
			return encodeStr.substring(1,indexOf);
		}
		if("d".equals(type)) {
			Bencode.decodeStr = Bencode.decodeStr.substring(1,encodeStr.length()-1);
			LinkedHashMap<String, Object> hashMap = new LinkedHashMap<>();
			Object key  = null;
			while ((key = decodeParse(Bencode.decodeStr))!=null) {
				Object value = decodeParse(Bencode.decodeStr);
				hashMap.put(key.toString(), value);
			}
			return hashMap;
		}
		return null;
	}
	
	private static String encodeString(String value) {
		return value.length()+":"+value;
	}
	private static String encodeInteger(int value) {
		return "i"+value+"e";
	}
	private static String encodeMap(LinkedHashMap<String, Object> value) {
		String str = "d";
		for(Map.Entry<String, Object> m:value.entrySet()) {
			str+= encode(m.getKey())+ encode(m.getValue());
		}
		return str+"e";
	}
	private static String encodeList(ArrayList<Object> value) {
		String str = "l";
		for(Object object:value) {
			str+= encode(object);
		}
		return str + "e";
	}
	
	public static void main(String[] args) {
		LinkedHashMap<String, Object> map = new LinkedHashMap<>();
		map.put("name", "wxc");
		map.put("age", 24);
		ArrayList<Object> list = new ArrayList<>();
		list.add("2121");
		list.add(333);
		LinkedHashMap<String, Object> map2= new LinkedHashMap<>();
		map2.put("name", "wxc222");
		map2.put("age", 2422);
		list.add(map2);
		map.put("list", list);
		String encode = encode(map);
		System.out.println(encode);
		System.out.println(decode(encode));
	}
}
	
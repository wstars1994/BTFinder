package com.boomzz.main.bencode;

import java.io.PushbackInputStream;
import java.util.LinkedHashMap;

public class BencodeMap implements IBencode {

	public Object decode(PushbackInputStream stream, LinkedHashMap<String, Object> hashMap) throws Exception{
		int read = -1;
		LinkedHashMap<String, Object> newMap = null;
		if(hashMap.size()>0) {
			newMap = new LinkedHashMap<>();
		}else {
			newMap = hashMap;
		}
		while((read=stream.read())!=-1) {
			if(read==101) return newMap;
			String key = new BencodeString(read).decode(stream, newMap);
			Object value = BencodeProxy.process(stream, newMap);
			value = IBencode.specialValueDecode(key, value);
			newMap.put(key, value);
			System.out.println("---" + hashMap);
		}
		return null;
		//d3:abc4:abcde
	}
}

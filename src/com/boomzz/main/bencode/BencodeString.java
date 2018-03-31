package com.boomzz.main.bencode;

import java.io.PushbackInputStream;
import java.util.LinkedHashMap;
import java.util.Map;

public class BencodeString implements IBencode {

	public String decode(PushbackInputStream stream, LinkedHashMap<String, Object> hashMap) throws Exception{
		int read = stream.read();
		int size = Integer.parseInt(((char)read)+"");
		byte[] key = new byte[size];
		stream.skip(1);
		stream.read(key, 0, size);
		setValue(hashMap, key);
		return new String(key);
	}
	
	private void setValue(LinkedHashMap<String, Object> hashMap,byte[] values) {
		for(Map.Entry<String, Object> map:hashMap.entrySet()) {
			String key = map.getKey();
			Object object = map.getValue();
			if(object==null) {
				object = IBencode.specialaValueDecode(key, values);
			}
			map.setValue(object);
		}
	}
}

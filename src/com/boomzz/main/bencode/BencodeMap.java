package com.boomzz.main.bencode;

import java.io.PushbackInputStream;
import java.util.LinkedHashMap;

public class BencodeMap implements IBencode {

	public Object decode(PushbackInputStream stream, LinkedHashMap<String, Object> hashMap) throws Exception{
		stream.read();
		String key = (String) BencodeProxy.process(stream, hashMap);
		hashMap.put(key, null);
		System.out.println(hashMap);
		return null;
	}
}

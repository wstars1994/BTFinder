package com.boomzz.main.bencode;

import java.io.PushbackInputStream;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import com.boomzz.main.bencode.model.ObjectBytesModel;

public class Bencode {

	public static ObjectBytesModel process(PushbackInputStream stream,LinkedHashMap<String,Object> hashMap) throws Exception {
		int head = stream.read();
		IBencode bencode = null;
		switch (head) {
			case 100://d Map
				bencode = new BencodeMap();
				break;
			case 105://i int
				bencode = new BencodeInteger();
				break;
			case 108://l  list
				bencode = new BencodeList();
				break;
			default://[0-9]+       String
				bencode = new BencodeString(head);
				break;
		}
		if(bencode!=null) {
			return bencode.decode(stream, hashMap);
		}
		return null;
	}
	
	public static String encode(Object object) {
		if(object instanceof String) {
			return new BencodeString().encode(object);
		}
		if(object instanceof Integer) {
			return new BencodeInteger().encode(object);
		}
		if(object instanceof HashMap) {
			return new BencodeInteger().encode(object);
		}
		if(object instanceof List) {
			return new BencodeList().encode(object);
		}
		return null;
	}
}

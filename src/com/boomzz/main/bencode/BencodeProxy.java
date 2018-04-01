package com.boomzz.main.bencode;

import java.io.PushbackInputStream;
import java.util.LinkedHashMap;

public class BencodeProxy {

	public static Object process(PushbackInputStream stream,LinkedHashMap<String,Object> hashMap) throws Exception {
		int head = stream.read();
		IBencode bencode = null;
		switch (head) {
			case 100://d Map
				bencode = new BencodeMap();
				break;
			case 105://i int
				bencode = new BecodeInteger();
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
}

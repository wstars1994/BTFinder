package com.boomzz.main.bencode;

import java.io.IOException;
import java.io.PushbackInputStream;
import java.util.HashMap;

public class BencodeProxy {

	public static void process(PushbackInputStream stream,HashMap<String,Object> hashMap) throws IOException {
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
				bencode = new BencodeString();
				break;
		}
		if(bencode!=null) {
			bencode.decode(stream, hashMap);
		}
	}
}

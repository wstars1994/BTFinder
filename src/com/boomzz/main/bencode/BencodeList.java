package com.boomzz.main.bencode;

import java.io.PushbackInputStream;
import java.util.LinkedHashMap;

public class BencodeList implements IBencode {

	public Object decode(PushbackInputStream stream, LinkedHashMap<String, Object> hashMap) {
		return hashMap;
	}

}

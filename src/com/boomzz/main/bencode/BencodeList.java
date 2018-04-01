package com.boomzz.main.bencode;

import java.io.PushbackInputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import com.boomzz.main.bencode.model.ObjectBytesModel;

public class BencodeList implements IBencode {

	public ObjectBytesModel decode(PushbackInputStream stream, LinkedHashMap<String, Object> hashMap) {
		
		
		return null;
	}

	@Override
	public String encode(Object value) {
		ArrayList<Object> list = (ArrayList<Object>) value;
		String str = "l";
		for(Object object:list) {
			str+= Bencode.encode(object);
		}
		return str + "e";
	}

}

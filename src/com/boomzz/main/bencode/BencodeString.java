package com.boomzz.main.bencode;

import java.io.PushbackInputStream;
import java.util.LinkedHashMap;

import com.boomzz.main.bencode.model.ObjectBytesModel;

public class BencodeString implements IBencode {

	int read = -1;
	public BencodeString(int head) {
		this.read=head;
	}
	public BencodeString() { }

	public ObjectBytesModel decode(PushbackInputStream stream, LinkedHashMap<String, Object> hashMap) throws Exception{
		int num = 58;
		String numList = (char)read+"";
		while ((num=stream.read())!=58) {
			numList+=(char)num+"";
		}
		int size = Integer.parseInt(numList);
		byte[] key = new byte[size];
		stream.read(key, 0, size);
		return new ObjectBytesModel(new String(key),key);
	}
	
	@Override
	public String encode(Object value) {
		return value.toString().length()+":"+value.toString();
	}
}

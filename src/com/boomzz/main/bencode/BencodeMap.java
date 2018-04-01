package com.boomzz.main.bencode;

import java.io.PushbackInputStream;
import java.util.LinkedHashMap;
import java.util.Map;

import com.boomzz.main.bencode.model.ObjectBytesModel;

public class BencodeMap implements IBencode {

	public ObjectBytesModel decode(PushbackInputStream stream, LinkedHashMap<String, Object> hashMap) throws Exception{
		int read = -1;
		LinkedHashMap<String, Object> newMap = null;
		if(hashMap.size()>0) {
			newMap = new LinkedHashMap<>();
		}else {
			newMap = hashMap;
		}
		while((read=stream.read())!=-1) {
			if(read==101) return new ObjectBytesModel(newMap, null);
			ObjectBytesModel key = new BencodeString(read).decode(stream, newMap);
			ObjectBytesModel value = Bencode.process(stream, newMap);
			IBencode.specialValueDecode(key.getObject().toString(),value);
			newMap.put(key.getObject().toString(), value.getObject());
		}
		return null;
		//d3:abc4:abcde
	}

	@Override
	public String encode(Object object) {
		LinkedHashMap<String, Object> hashMap = (LinkedHashMap<String, Object>) object;
		String str = "d";
		for(Map.Entry<String, Object> m:hashMap.entrySet()) {
			str+= encode(m.getKey())+ encode(m.getValue());
		}
		return str+"e";
	}
}

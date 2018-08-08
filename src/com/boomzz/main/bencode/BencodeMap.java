package com.boomzz.main.bencode;

import java.io.PushbackInputStream;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;

import com.boomzz.main.bencode.model.ObjectBytesModel;

public class BencodeMap extends AbstractBencode {

	public ObjectBytesModel decode(PushbackInputStream stream, LinkedHashMap<String, Object> hashMap) throws Exception {
		int read = -1;
		LinkedHashMap<String, Object> newMap = null;
		if (hashMap.size() > 0) {
			newMap = new LinkedHashMap<>();
		} else {
			newMap = hashMap;
		}
		while ((read = stream.read()) != -1) {
			if (read == 101)
				return new ObjectBytesModel(newMap, null);
			ObjectBytesModel key = new BencodeString(read).decode(stream, newMap);
			ObjectBytesModel value = AbstractBencode.decodeRouter(stream, newMap);
			if (value != null) {
				super.specialValueDecode(key.getObject().toString(), value);
			}
			newMap.put(key.getObject().toString(), value.getObject());
		}
		return null;
	}

	@Override
	public byte[] encode(Object object) {
		LinkedHashMap<String, Object> hashMap = (LinkedHashMap<String, Object>) object;
		String str = "d";
		byte bytes[] = {};
		for(Map.Entry<String, Object> m:hashMap.entrySet()) {
			 byte[] key = AbstractBencode.encodeRouter(m.getKey());
			 byte[] value = AbstractBencode.encodeRouter(m.getValue());
			 bytes = ArrayUtils.addAll(bytes,key);
			 bytes = ArrayUtils.addAll(bytes,value);
		}
		bytes = ArrayUtils.addAll(str.getBytes(),bytes);
		bytes = ArrayUtils.addAll(bytes,"e".getBytes());
		return bytes;
	}
}

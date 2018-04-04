package com.boomzz.main.bencode;

import java.io.PushbackInputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;

import com.boomzz.main.bencode.model.ObjectBytesModel;

public class BencodeList extends AbstractBencode {

	private int read = -1;
	
	public BencodeList(int read) {
		this.read = read;
	}	

	public BencodeList() { }
	
	public ObjectBytesModel decode(PushbackInputStream stream, LinkedHashMap<String, Object> hashMap) throws Exception {
		int num = 58;
		List<ObjectBytesModel> list = new ArrayList<>();
		while ((num=stream.read())!=101) {
			stream.unread(num);
			list.add(AbstractBencode.decodeRouter(stream, hashMap));
		}
		return new ObjectBytesModel(list,null);
	}

	@Override
	public byte[] encode(Object value) {
		ArrayList<Object> list = (ArrayList<Object>) value;
		byte bytes[] = {};
		for(Object object:list) {
			byte[] encodeRouter = AbstractBencode.encodeRouter(object);
			bytes = ArrayUtils.addAll(bytes, encodeRouter);
		}
		ArrayUtils.addAll("l".getBytes(),bytes);
		ArrayUtils.addAll(bytes,"e".getBytes());
		return bytes;
	}

}

package com.boomzz.main.bencode;

import java.io.PushbackInputStream;
import java.util.LinkedHashMap;

import org.apache.commons.lang3.ArrayUtils;

import com.boomzz.main.bencode.model.ObjectBytesModel;
import com.boomzz.main.util.StreamUtil;

public class BencodeString extends AbstractBencode {

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
	public byte[] encode(Object value) {
		String str = value.toString();
		if(str.length()==40) {
			byte[] hexBytes = StreamUtil.hexStringToBytes(str);
			byte[] addAll = ArrayUtils.addAll((hexBytes.length+":").getBytes(), hexBytes);
			return addAll;
		}
		return (value.toString().length()+":"+value.toString()).getBytes();
	}
}

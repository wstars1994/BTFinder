package com.boomzz.main.bencode;

import java.io.PushbackInputStream;
import java.util.Arrays;
import java.util.LinkedHashMap;

import com.boomzz.main.StreamUtil;

public interface IBencode {

	public Object decode(PushbackInputStream stream, LinkedHashMap<String, Object> hashMap) throws Exception;
	
	public static Object specialaValueDecode(String key,byte[] values) {
		switch (key) {
			case "ip":
				//一共6个字节 前四个为IP后两个为port
				byte ip[] = Arrays.copyOfRange(values, 0, 4);
				byte port[] = Arrays.copyOfRange(values, 4, 6);
				String ipString = "";
				for(byte b:ip) {
					//byte 转为 int
					int c = b & 0xFF;
					ipString+=c+".";
				}
				if(ipString.length()>0) ipString=ipString.substring(0, ipString.length()-1);
				byte [] ports = new byte[4];
				ports[2]=ip[0];
				ports[3]=ip[1];
				return ipString+":"+StreamUtil.bytesToInt(ports);
			default:
				break;
		}
		return values;
	}
	
}

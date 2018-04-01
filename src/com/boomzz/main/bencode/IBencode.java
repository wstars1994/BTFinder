package com.boomzz.main.bencode;

import java.io.PushbackInputStream;
import java.util.Arrays;
import java.util.LinkedHashMap;

import com.boomzz.main.ConvertUtil;
import com.boomzz.main.StreamUtil;

public interface IBencode {

	public Object decode(PushbackInputStream stream, LinkedHashMap<String, Object> hashMap) throws Exception;
	
	public static Object specialValueDecode(String key,Object objValues) {
		switch (key) {
			case "ip":
				char []cs = objValues.toString().toCharArray();
				byte values[]=new byte[cs.length];
				for(int i=0;i<cs.length;i++) {
					values[i] = (byte)((int)cs[i]);
				}
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
				ports[2]=port[0];
				ports[3]=port[1];
				return ipString+":"+StreamUtil.bytesToInt(ports);
				
			case "id":
				//一共26个字节 前20个为应答者nodeID后6位为应答者IPport
				values=objValues.toString().getBytes();
				byte nodeIdBytes[] = Arrays.copyOfRange(values, 0, 20);
				ip = Arrays.copyOfRange(values, 20, 26);
				Object specialValueDecode = specialValueDecode("ip",ip);
				return ConvertUtil.bytesToHexString(nodeIdBytes).toString()+"/"+specialValueDecode;
			default:
				break;
		}
		return objValues;
	}
	
}

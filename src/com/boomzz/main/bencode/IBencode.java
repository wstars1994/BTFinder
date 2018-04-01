package com.boomzz.main.bencode;

import java.io.PushbackInputStream;
import java.util.Arrays;
import java.util.LinkedHashMap;

import com.boomzz.main.StreamUtil;
import com.boomzz.main.bencode.model.ObjectBytesModel;

public interface IBencode {

	public ObjectBytesModel decode(PushbackInputStream stream, LinkedHashMap<String, Object> hashMap) throws Exception;
	public String encode(Object object);
	
	public static void specialValueDecode(String key,ObjectBytesModel values) {
		byte value[] = values.getBytes();
		switch (key) {
			case "ip":
				//一共6个字节 前四个为IP后两个为port
				byte ip[] = Arrays.copyOfRange(value, 0, 4);
				byte port[] = Arrays.copyOfRange(value, 4, 6);
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
				values.setObject(ipString+":"+StreamUtil.bytesToInt(ports));
				break;
			case "id":
				//一共26个字节 前20个为应答者nodeID后6位为应答者IPport
				byte nodeIdBytes[] = Arrays.copyOfRange(value, 0, 20);
				values.setObject(StreamUtil.bytesToHexString(nodeIdBytes).toString());
				break;
			case "nodes":
				values.setObject("");
				//一共26个字节 前20个为应答者nodeID后6位为应答者IPport
				int length = value.length/26;  //注:单个nodeID长26
				for(int i=0;i<length;i++) {
					nodeIdBytes = Arrays.copyOfRange(value, i*26, i*26+20);
					ip = Arrays.copyOfRange(value, i*26+20, i*26+20+6);
					ObjectBytesModel model = new ObjectBytesModel(null,ip);
					specialValueDecode("ip",model);
					values.setObject(values.getObject()+StreamUtil.bytesToHexString(nodeIdBytes).toString()+"/"+model.getObject()+"#");
				}
				break;
			default:
				break;
		}
	}
	
}

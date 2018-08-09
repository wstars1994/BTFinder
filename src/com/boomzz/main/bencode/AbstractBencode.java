package com.boomzz.main.bencode;

import java.io.PushbackInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import com.boomzz.main.bencode.model.ObjectBytesModel;
import com.boomzz.main.util.DHTUtil;
import com.boomzz.main.util.StreamUtil;

/**
 * 将二进制解码
 * @author Duo Nuo
 *上午8:56:44
 */
public abstract class AbstractBencode {

	public abstract ObjectBytesModel decode(PushbackInputStream stream, LinkedHashMap<String, Object> hashMap) throws Exception;
	
	public abstract byte[] encode(Object object);
	
	public static void specialValueDecode(String key,ObjectBytesModel values) {
		if(values==null) return;
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
				//nodeId
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
			case "values":
				ArrayList<ObjectBytesModel> list = (ArrayList<ObjectBytesModel>) values.getObject();
				for(ObjectBytesModel model:list) {
					ObjectBytesModel objectBytesModel = new ObjectBytesModel(null,model.getBytes());
					specialValueDecode("ip",objectBytesModel);
					model.setObject(objectBytesModel.getObject());
				}
				break;
			default:
				break;
		}
	}
	
	public static ObjectBytesModel decodeRouter(PushbackInputStream stream,LinkedHashMap<String,Object> hashMap) throws Exception {
		int head = stream.read();
		AbstractBencode bencode = null;
		switch (head) {
			case 100://d Map
				bencode = new BencodeMap();
				break;
			case 105://i int
				bencode = new BencodeInteger();
				break;
			case 108://l  list
				bencode = new BencodeList(head);
				break;
			default://[0-9]+       String
				bencode = new BencodeString(head);
				break;
		}
		if(bencode!=null) {
			return bencode.decode(stream, hashMap);
		}
		return null;
	}
	
	public static byte[] encodeRouter(Object object) {
		if(object instanceof String) {
			return new BencodeString().encode(object);
		}
		if(object instanceof Integer) {
			return new BencodeInteger().encode(object);
		}
		if(object instanceof HashMap) {
			return new BencodeMap().encode(object);
		}
		if(object instanceof List) {
			return new BencodeList().encode(object);
		}
		return null;
	}
}

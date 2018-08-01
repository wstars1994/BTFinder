/**
 * 
 * 项目名称:[BTFounder]
 * 包:	 [com.boomzz.main.packet]
 * 类名称: [DHTPacketPing]
 * 类描述: [一句话描述该类的功能]
 * 创建人: [王新晨]
 * 创建时间:[2018年4月1日 下午4:11:32]
 * 修改人: [王新晨]
 * 修改时间:[2018年4月1日 下午4:11:32]
 * 修改备注:[说明本次修改内容]  
 * 版本:	 [v1.0]   
 * 
 */
package com.boomzz.main.packet;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import com.boomzz.main.DHTUtil;
import com.boomzz.main.bencode.AbstractBencode;

public class DHTPacketPing extends AbstractDHTPacket{

	@Override
	public byte[] reqPacket(String... param) {
		LinkedHashMap<String, Object> map  = new LinkedHashMap<>();
		map.put("t", "bz");
		if("1".equals(param[0])) {
			map.put("y", "q");
			map.put("q", "ping");
			LinkedHashMap<String, Object> a  = new LinkedHashMap<>();
			a.put("id",DHTUtil.NODE_ID);
			map.put("a",a);
		}else {
			map.put("y", "e");
			List<Object> list = new ArrayList<>();
			list.add(201);
			list.add("error");
			map.put("e",list);
		}
		return AbstractBencode.encodeRouter(map);
	}

	@Override
	public Object unpacket(LinkedHashMap<String, Object> map) {
		System.out.println("the node is good");
		
		return null;
	}

	/* (non-Javadoc)
	 * @see com.boomzz.main.packet.AbstractDHTPacket#repPacket(java.lang.String[])
	 */
	@Override
	public byte[] repPacket(String... param) {
		// TODO Auto-generated method stub
		return null;
	}


}

package com.boomzz.main.packet;

import java.util.LinkedHashMap;

public abstract class AbstractDHTPacket {

	public abstract byte[] packet(String param);
	
	public abstract Object unpacket(LinkedHashMap<String, Object> map);
	
}

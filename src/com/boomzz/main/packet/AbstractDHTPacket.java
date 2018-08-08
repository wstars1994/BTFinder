package com.boomzz.main.packet;

import java.util.LinkedHashMap;

public abstract class AbstractDHTPacket {

	/**
	 *  封装发送请求的数据包
	 */
	public abstract byte[] reqPacket(String... param);

	/**
	 * 封装响应请求的数据包
	 */
	public abstract byte[] repPacket(String... param);
	
	/**
	 * 解发送请求后响应的数据包
	 */
	public abstract Object reqUnpacket(LinkedHashMap<String, Object> map,String oIp, int oPort);

}

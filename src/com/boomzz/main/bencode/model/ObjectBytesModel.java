package com.boomzz.main.bencode.model;
public class ObjectBytesModel {

	private Object object;
	
	private byte[] bytes;
	
	public ObjectBytesModel(Object object,byte[] bytes) {
		this.object = object;
		this.bytes = bytes;
	}
	
	public Object getObject() {
		return object;
	}
	
	public void setObject(Object object) {
		this.object = object;
	}
	
	public byte[] getBytes() {
		return bytes;
	}
	
	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}
}

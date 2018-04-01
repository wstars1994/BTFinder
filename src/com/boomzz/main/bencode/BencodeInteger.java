/**
 * 
 * 项目名称:[BTFounder]
 * 包:	 [com.boomzz.main.bencode]
 * 类名称: [BecodeInteger]
 * 类描述: [一句话描述该类的功能]
 * 创建人: [王新晨]
 * 创建时间:[2018年3月31日 下午4:29:20]
 * 修改人: [王新晨]
 * 修改时间:[2018年3月31日 下午4:29:20]
 * 修改备注:[说明本次修改内容]  
 * 版本:	 [v1.0]   
 * 
 */
package com.boomzz.main.bencode;

import java.io.PushbackInputStream;
import java.util.LinkedHashMap;

import com.boomzz.main.bencode.model.ObjectBytesModel;

public class BencodeInteger extends AbstractBencode{
	@Override
	public ObjectBytesModel decode(PushbackInputStream stream, LinkedHashMap<String, Object> hashMap) throws Exception{
		int num = 58;
		String numStr = "";
		while ((num=stream.read())!=101) {
			numStr+=(char)num+"";
		}
		return new ObjectBytesModel(Integer.parseInt(numStr),null);
	}

	@Override
	public byte[] encode(Object object) {
		return ("i"+object+"e").getBytes();
	}
	
}

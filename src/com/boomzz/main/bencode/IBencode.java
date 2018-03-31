package com.boomzz.main.bencode;

import java.io.IOException;
import java.io.PushbackInputStream;
import java.util.HashMap;

public interface IBencode {

	public void decode(PushbackInputStream stream, HashMap<String, Object> hashMap) throws IOException;
	
}

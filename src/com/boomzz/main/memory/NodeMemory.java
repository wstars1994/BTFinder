/**
 * author : 王新晨
 * date : 2018年8月8日 下午12:29:42
 */
package com.boomzz.main.memory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NodeMemory {

	private static List<Map<String, Object>> reqNodesQueue = new ArrayList<>();

	public static List<Map<String, Object>> getNodes() {
		return reqNodesQueue;
	}
	
	public static void addNode(String nodeId,String ip,int port) {
		Map<String, Object> addNode = new HashMap();
		addNode.put("ip",ip);
		addNode.put("port",port);
		addNode.put("nodeId",nodeId);
		reqNodesQueue.add(addNode);
	}
}

package com.boomzz.main.thread;

import java.util.List;
import java.util.Map;

import com.boomzz.main.DHTClientBoot;
import com.boomzz.main.util.DBUtil;
import com.boomzz.main.util.DHTUtil;
import com.boomzz.main.util.MyLogger;

public class DelDuplicateNodeThread extends Thread{

	@Override
	public void run() {
		if(!DHTUtil.isProduct) return;
		while (true) {
			try {
				List<Map<String, Object>> search = DBUtil.search("select * from BT_DHT_NODE group by NODE_ID,NODE_IP,NODE_PORT HAVING COUNT(*)>1");
				if(search!=null) {
					search.forEach(n->{
						String id = n.get("ID").toString();
						String nodeId = n.get("NODE_ID").toString();
						String ip = n.get("NODE_IP").toString();
						String port = n.get("NODE_PORT").toString();
						String delSql = "delete from BT_DHT_NODE where NODE_PORT="+port+" and NODE_IP='"+ip+"' and NODE_ID='"+nodeId+"' and id != "+id;
						DBUtil.execute(delSql);
					});
				}
				MyLogger.log(DHTClientBoot.class,"----------------------清除重复节点,刷新NODEID----------------------");
				DelDuplicateNodeThread.currentThread().sleep(10*60*1000);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}

package sb.servmon.module;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MonitorDaemon implements Runnable {
	
	private Thread thread;
	private static MonitorDaemon instance;
	public static long freeMemory = Runtime.getRuntime().freeMemory();
	public static long totalMemory = Runtime.getRuntime().totalMemory();
	public static long usedMemory  = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
	public List<Map> dataList = new ArrayList<Map>();
	public int counter = 0;
	
	private MonitorDaemon() {

	}
	
	public static MonitorDaemon getInstance() {
		if ( instance == null ) {
			instance = new MonitorDaemon();
		}
		return instance;
	}

	@Override
	public void run() {
	
		counter = 0;
		while ( thread != null ) {
			
			try {

				counter++;
				Thread.sleep(1000);
				
				freeMemory = Runtime.getRuntime().freeMemory();
				totalMemory = Runtime.getRuntime().totalMemory();
				usedMemory  = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
				
				Map<String, Object> m = new HashMap<String, Object>();
				m.put("counter", counter);
				m.put("freeMemory", freeMemory);
				dataList.add(m);
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
		}
		
	}
	
	public void stop() {
		thread = null;
		dataList.clear();
	}
	
	public void start() {
		thread = new Thread(this);
		thread.start();
	}
	

}

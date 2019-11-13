package sb.servmon.module;

import java.sql.ResultSet;
import java.text.DecimalFormat;

import lebah.db.Db;
import lebah.portal.action.Command;
import lebah.portal.action.LebahModule;

public class MonitoringModule extends LebahModule {
	
	String path = "apps/monitor";
	MonitorDaemon monitor = MonitorDaemon.getInstance();

	
	public void preProcess() {
		context.put("nf", new DecimalFormat("#,###"));
		
		String serverName = request.getServerName();
		int serverPort = request.getServerPort();
        String server = serverPort != 80 ? serverName + ":" + serverPort : serverName;
        String http = request.getRequestURL().toString().substring(0, request.getRequestURL().toString().indexOf("://") + 3);
        String serverUrl = http + server;
        context.put("serverUrl", serverUrl);
        String uri = request.getRequestURI();
        String appname = uri.substring(1);
        appname = appname.substring(0, appname.indexOf("/"));
        context.put("appUrl", serverUrl.concat("/").concat(appname));   
	}

	@Override
	public String start() {
		
		try {
			getResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return path + "/start.vm";
	}
	
	@Command("getResult")
	public String getResult() throws Exception {
		
		long freeMemory = MonitorDaemon.freeMemory;
		long totalMemory = MonitorDaemon.totalMemory;
		long usedMemory  = MonitorDaemon.usedMemory;
		
		String osName = System.getProperty("os.name");
		String osVersion = System.getProperty("os.version");
		String osArch = System.getProperty("os.arch");
		
		context.put("freeMemory", freeMemory/1024);
		context.put("totalMemory", totalMemory/1024);
		context.put("usedMemory", usedMemory/1024);
		context.put("osName", osName);
		context.put("osVersion", osVersion);
		context.put("osArch", osArch);
		
		context.put("dataList", MonitorDaemon.getInstance().dataList);

		
		return path + "/result.vm";
	}

	
	@Command("getActiveConnections")
	public String getNumberOfActiveConnections() {
		int num = 0;
		Db db = null;
		try {
			db = new Db();
			ResultSet rs = db.getStatement().executeQuery("show status where variable_name = 'threads_connected'");
			if (rs.next())
				num = rs.getInt(2);
			
		} catch ( Exception e) {
			e.printStackTrace();
		} finally {
			if ( db != null ) db.close();
		}
		context.put("activeConnections", num);
		return path + "/activeConnections.vm";
	}
	
	
	@Command("getStatusUpdate")
	public String getStatusUpdate() throws Exception {
		
		long freeMemory = MonitorDaemon.freeMemory;
		long totalMemory = MonitorDaemon.totalMemory;
		long usedMemory  = MonitorDaemon.usedMemory;
		
		String osName = System.getProperty("os.name");
		String osVersion = System.getProperty("os.version");
		String osArch = System.getProperty("os.arch");
		
		context.put("freeMemory", freeMemory/1024);
		context.put("totalMemory", totalMemory/1024);
		context.put("usedMemory", usedMemory/1024);
		context.put("osName", osName);
		context.put("osVersion", osVersion);
		context.put("osArch", osArch);
		
		getNumberOfActiveConnections();
		
		return path + "/status_update.vm";
	}

}

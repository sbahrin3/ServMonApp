package sb.servmon.module;

import javax.servlet.ServletContextAttributeEvent;
import javax.servlet.ServletContextAttributeListener;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class AppListener  implements ServletContextListener, ServletContextAttributeListener {
	
	@Override
	public void attributeAdded(ServletContextAttributeEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void attributeRemoved(ServletContextAttributeEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void attributeReplaced(ServletContextAttributeEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void contextDestroyed(ServletContextEvent e) {
		System.out.println("-----------------------------");
		System.out.println("Application Context Destroyed.");
		System.out.println("-----------------------------");	
		
		MonitorDaemon.getInstance().stop();
		
	}

	@Override
	public void contextInitialized(ServletContextEvent e) {
		
		System.out.println("--------------------------------");
		System.out.println("Application Context Initialized.");
		System.out.println("--------------------------------");
		
		MonitorDaemon.getInstance().start();
		
	}

}

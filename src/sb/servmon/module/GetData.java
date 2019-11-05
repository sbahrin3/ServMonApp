package sb.servmon.module;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;

import lebah.servlets.IServlet;

public class GetData implements IServlet {

	@Override
	public void doService(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
		
		PrintWriter out = res.getWriter();
        
		int xstart = Integer.parseInt(req.getParameter("xstart"));
		int length = Integer.parseInt(req.getParameter("length"));
		
		
		List<Map> dataList = MonitorDaemon.getInstance().dataList;

		int size = dataList.size();
		int start = size - length;
		if ( start < 0 ) start = 0;
		
		JSONArray a1 = new JSONArray();
		for ( int i = start; i < size; i++ ) {
			xstart++;
			JSONArray a2 = new JSONArray();
			Map<String, Object> m = dataList.get(i);
			a2.add(xstart);
			a2.add(m.get("freeMemory"));
			a1.add(a2);
		}
		
		out.println(a1.toJSONString());
		
	}

}

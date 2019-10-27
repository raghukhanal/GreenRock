package rpc;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import external.BlackRockAPI;
/**
 * Servlet implementation class Search
 */
@WebServlet("/search")
public class Search extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static BlackRockAPI api;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Search() {
        super();
        // TODO Auto-generated constructor stub
        api = new BlackRockAPI();
        
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    
    //   http://localhost:8080/GreenRock/search?positions=WORK~50|AAPL~10|SNAP~40
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String positions = request.getParameter("positions");
		
		int start = 0;
		int curr = 0;
		List<String> str = new ArrayList<>();
		while(curr<positions.length()) {
			char c = positions.charAt(curr);
			if(!Character.isDigit(c) && !Character.isLetter(c) && c!='~') {
				str.add(positions.substring(start,curr));
				curr++;
				start = curr;
			}else {
				curr++;
			}
		}
		
		str.add(positions.substring(start));
		try {
			//positions =  URLEncoder.encode(positions, "UTF-8");
			JSONObject obj = api.readData(str);
			writeJsonObject(response,obj);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	public static void writeJsonObject(HttpServletResponse response, JSONObject obj) throws IOException {	
		response.setContentType("application/json");
		response.getWriter().print(obj);

	}

}

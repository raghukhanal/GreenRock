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

import ann.NeuralNet;
import external.BlackRockAPI;

import ann.TrainedANN;



/**
 * Servlet implementation class Search
 */
@WebServlet("/search")
public class Search extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static BlackRockAPI api;
	private static ANN Ann = new ANN();
       
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
//		for(String s:positions.split(" ")) {
//			str.add(s);
//		}
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
//		// TODO Auto-generated method stub
		doGet(request, response);
		//System.out.print(ANN.eval(2.4,1.0));
		
		
		String position = request.getParameter("position");
		
//		String positions = request.getParameter("positions");
//		
//		
//		int start = 0;
//		int curr = 0;
//		List<String> str = new ArrayList<>();
////		for(String s:positions.split(" ")) {
////			str.add(s);
////		}
//		while(curr<positions.length()) {
//			char c = positions.charAt(curr);
//			if(!Character.isDigit(c) && !Character.isLetter(c) && c!='~') {
//				str.add(positions.substring(start,curr));
//				curr++;
//				start = curr;
//			}else {
//				curr++;
//			}
//		}
//		
//		str.add(positions.substring(start));
//		try {
//			//positions =  URLEncoder.encode(positions, "UTF-8");
//			JSONObject obj = api.readData(str);
//			writeJsonObject(response,obj);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
	
	public static void writeJsonObject(HttpServletResponse response, JSONObject obj) throws IOException {	
		response.setContentType("application/json");
		response.getWriter().print(obj);

	}


}

class ANN{
	public static TrainedANN Ann = new TrainedANN();
	
	public static double eval(double PE,double PB) {
		    NeuralNet ann = Ann.getANN();
			
			double one = 0;
			
			one+=ann.getInputLayer().getNeurons().get(0).getOutEdges().get(0).getWeight()*PE;
			one+=ann.getInputLayer().getNeurons().get(1).getOutEdges().get(0).getWeight()*PB;
			one-=ann.getHiddenLayer().getNeurons().get(0).getAlpha();
			
			double two =0;
			
			two+=ann.getInputLayer().getNeurons().get(0).getOutEdges().get(1).getWeight()*PE;
			two+=ann.getInputLayer().getNeurons().get(1).getOutEdges().get(1).getWeight()*PB;
			two-=ann.getHiddenLayer().getNeurons().get(1).getAlpha();
			
			double three = 0;
			
			three+=ann.getInputLayer().getNeurons().get(0).getOutEdges().get(2).getWeight()*PE;
			three+=ann.getInputLayer().getNeurons().get(1).getOutEdges().get(2).getWeight()*PB;
			three-=ann.getHiddenLayer().getNeurons().get(2).getAlpha();
			
			
			double res =0;
			res+=one*ann.getHiddenLayer().getNeurons().get(0).getInEdges().get(0).getWeight();
			res+=one*ann.getHiddenLayer().getNeurons().get(1).getInEdges().get(0).getWeight();
			res+=one*ann.getHiddenLayer().getNeurons().get(2).getInEdges().get(0).getWeight();
			res = calculateOutput(res);
			System.out.println(one+" "+two+" "+three+" "+res);
			return res;
			
			
			
		}
		public static double calculateOutput(double netInput) {
	        return 1.5 / (1+Math.exp(-netInput));
			//return 3 / (1 + Math.exp(-2 * netInput)) - 1;
			//return netInput;
	    }

	
}

















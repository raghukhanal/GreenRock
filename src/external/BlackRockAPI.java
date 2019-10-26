package external;
import java.net.*;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;

public class BlackRockAPI {
	public JSONObject readData(List<String> positions) throws Exception {// AAPL~50|WORK~50
        //String urlString="https://www.blackrock.com/tools/hackathon/portfolio-analysis?calculateExposures=true&calculatePerfomance=true&calculateRisk=true&positions=" + a + "~" + b + "%7CWORK~10";
		String urlString="https://www.blackrock.com/tools/hackathon/portfolio-analysis?calculateExposures=true&calculatePerfomance=true&calculateRisk=true&positions=";
		//System.out.println(positions.size());
		for(int i=0;i<positions.size()-1;i++) {
        	
        	String s = positions.get(i);
        	//System.out.println(s);
        	urlString+=s+"%7C";
        }
        urlString+=positions.get(positions.size()-1);
        //System.out.println(positions.get(positions.size()-1));
        
        
        
        URL url = new URL(urlString);
        URLConnection urlConnection = url.openConnection();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
        String inputLine;
        
        StringBuilder responseBody = new StringBuilder();
        while ((inputLine = bufferedReader.readLine()) != null) {
        	
        	responseBody.append(inputLine);
            
        }
        bufferedReader.close();
        JSONObject obj = new JSONObject(responseBody.toString());
        
        JSONObject resultMap = obj.getJSONObject("resultMap");
        
        JSONArray PORTFOLIOS = resultMap.getJSONArray("PORTFOLIOS");
        JSONArray portfolios = PORTFOLIOS.getJSONObject(0).getJSONArray("portfolios");
        JSONObject exposures = portfolios.getJSONObject(0).getJSONObject("returns");
        JSONObject returnmap = exposures.getJSONObject("returnsMap");
        JSONObject userdate = returnmap.getJSONObject("20190807");
        double level = userdate.getDouble("level");
        
        
        
        //System.err.print(d);
       
        return returnmap;
        
    }

    public static void main(String[] args) throws Exception {
    	BlackRockAPI apiReader = new BlackRockAPI();
    	
        //apiReader.readData("BLK~100");
    }
    private static void getInfo() {
    	
    }
    
    

}

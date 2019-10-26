package external;
import java.net.*;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;

public class BlackRockAPI {
	public JSONObject readData(String positions) throws Exception {
        String urlString = "https://www.blackrock.com/tools/hackathon/portfolio-analysis";
        urlString="https://www.blackrock.com/tools/hackathon/portfolio-analysis?calculateExposures=true&calculatePerfomance=true&calculateRisk=true&positions=AAPL~90%7CWORK~10";

        
        URL url = new URL(urlString);
        URLConnection urlConnection = url.openConnection();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
        String inputLine;
        
        StringBuilder responseBody = new StringBuilder();
        while ((inputLine = bufferedReader.readLine()) != null) {
        	//p.write(inputLine);
        	responseBody.append(inputLine);
            //System.out.println(inputLine);
        }
        bufferedReader.close();
        JSONObject obj = new JSONObject(responseBody.toString());
        
        JSONObject resultMap = obj.getJSONObject("resultMap");
        
        JSONArray PORTFOLIOS = resultMap.getJSONArray("PORTFOLIOS");
        System.err.print(PORTFOLIOS.length());
       
        return obj;
        
    }

    public static void main(String[] args) throws Exception {
    	BlackRockAPI apiReader = new BlackRockAPI();
        apiReader.readData("BLK~100");
    }
    private static void getInfo() {
    	
    }
    
    

}

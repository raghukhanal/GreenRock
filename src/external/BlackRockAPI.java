package external;
import java.net.*;

import org.json.JSONObject;

import java.io.*;

public class BlackRockAPI {
	public JSONObject readData(String positions) throws Exception {
        String urlString = "https://www.blackrock.com/tools/hackathon/portfolio-analysis";
        urlString="https://www.blackrock.com/tools/hackathon/portfolio-analysis?calculateExposures=true&calculatePerfomance=true&calculateRisk=true&positions=AAPL~90%7CWORK~10";
//        urlString += "?positions=" + positions;
//        urlString+="&";
//        urlString += "?startDate=" + "20151212";
//        urlString += "?endDate=" + "20151220";
//        urlString += "?currency=" + "USD";
//        urlString += "?calculateRisk=" + "true";
//        urlString += "?calculateExposures=" + "true";
//        urlString+="&";
//        urlString += "?calculatePerfomance=" + "true";
//       // urlString += "?calculateExpectedReturns=" + "true";
//       // urlString += "?includeReturnsMap=" + "true";
//        //urlString+="&";
//        //urlString += "?returnsType=" + "MONTHLY";
//        urlString+="&";
//        urlString += "?positions=" + positions;
        //urlString += "?positions=" + positions;
        
        //urlString += "?timeout=" + "10000";
        
        URL url = new URL(urlString);
        URLConnection urlConnection = url.openConnection();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
        String inputLine;
        //File file = new File("/Users/qinchen/Codes/GreenRock/ex.txt");
        //PrintWriter p = new PrintWriter(file);
        
        StringBuilder responseBody = new StringBuilder();
        while ((inputLine = bufferedReader.readLine()) != null) {
        	//p.write(inputLine);
        	responseBody.append(inputLine);
            System.out.println(inputLine);
        }
        bufferedReader.close();
        JSONObject obj = new JSONObject(responseBody.toString());
        return obj;
        
    }

    public static void main(String[] args) throws Exception {
    	BlackRockAPI apiReader = new BlackRockAPI();
        apiReader.readData("BLK~100");
    }
    
    

}

package external;
import java.net.*;
import java.io.*;

public class BlackRockAPI {
	public void readData(String positions) throws Exception {
        String urlString = "https://www.blackrock.com/tools/hackathon/portfolio-analysis";
        urlString += "?positions=" + positions;
        
        urlString += "?startDate=" + "20151212";
        urlString += "?endDate=" + "20151220";
        urlString += "?currency=" + "USD";
        urlString += "?calculateRisk=" + "true";
        urlString += "?calculateExposures=" + "true";
        urlString += "?calculateExpectedReturns=" + "true";
        urlString += "?includeReturnsMap=" + "true";
        urlString += "?returnsType=" + "DAILY";
        
        urlString += "?timeout=" + "10000";
        
        URL url = new URL(urlString);
        URLConnection urlConnection = url.openConnection();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
        String inputLine;
        //File file = new File("/Users/qinchen/Codes/GreenRock/ex.txt");
        //PrintWriter p = new PrintWriter(file);
        
        
        while ((inputLine = bufferedReader.readLine()) != null) {
        	//p.write(inputLine);
            System.out.println(inputLine);
        }
        bufferedReader.close();
    }

    public static void main(String[] args) throws Exception {
    	BlackRockAPI apiReader = new BlackRockAPI();
        apiReader.readData("BLK~100");
    }
    
    

}

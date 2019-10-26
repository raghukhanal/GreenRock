package external;
import java.net.*;
import java.io.*;

public class BlackRockAPI {
	public void readData() throws Exception {
        String urlString = "https://www.blackrock.com/tools/hackathon/search-securities?identifiers=IXN,GS";
        URL url = new URL(urlString);
        URLConnection urlConnection = url.openConnection();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
        String inputLine;
        while ((inputLine = bufferedReader.readLine()) != null) {
            System.out.println(inputLine);
        }
        bufferedReader.close();
    }

    public static void main(String[] args) {
    	BlackRockAPI apiReader = new BlackRockAPI();
        try {
			apiReader.readData();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

}

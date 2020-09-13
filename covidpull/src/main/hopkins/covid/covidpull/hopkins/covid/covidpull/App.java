package hopkins.covid.covidpull;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import io.radar.sdk.Radar;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.GetRequest;
import com.opencsv.CSVWriter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import java.util.*;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws UnirestException, IOException
    {
    	String date = "2020-08-16";
    	System.out.println("What City and State? (Write in format City, State)");
    	Scanner object = new Scanner(System.in);
    	String WholeString = object.nextLine();
    	String[] statcity = WholeString.split(", ");
    	
    	
    	
    	String state = statcity[1];
    	String city = statcity[0];
    	state = state.toLowerCase();
    	city = city.toLowerCase();
    	state = state.substring(0,1).toUpperCase() + state.substring(1);
    	city = city.substring(0,1).toUpperCase() + city.substring(1);
    	
    	
    	
    	Calendar cal = Calendar.getInstance();
    	cal.add(Calendar.DATE, -31);
    	String[] finallist = new String[31];
    	for(int i = 0; i < finallist.length;i++)
    	{
    		String fin = "";
    		fin+=cal.get(Calendar.YEAR) + "-";
    		int month = cal.get(Calendar.MONTH);
    		int day = cal.get(Calendar.DATE);
    		if(month+1 < 10)
    		{
    			fin+= "0" + (month+1) + "-";
    		}
    		else
    			fin+= (month+1) + "-";
    		if(day < 10)
    		{
    			fin+= "0" + (day);
    		}
    		else
    			fin+= (day);
    		
    		
    		
    		finallist[i] = fin;
    		cal.add(Calendar.DATE,1);
    	}
    	

        CSVWriter writer = new CSVWriter(new FileWriter("output.csv"));
    	for(int i = 0; i <finallist.length;i++)
    	{
    		ArrayList<String> Dates = new ArrayList<String>();
            ArrayList<String> Confirmed  = new ArrayList<String>();
            ArrayList<String> Deaths = new ArrayList<String>();
            if(i == 0)
            {
            	 Dates.add("Dates:");
                 Deaths.add("Deaths");
                 Confirmed.add("Confirmed");
                 ArrayList<String> al = new ArrayList<String>();
                 al.addAll(Dates);
                 al.addAll(Confirmed);
                 al.addAll(Deaths);
                 String[] fin = new String[3];
         	     System.arraycopy(al.toArray(), 0, fin, 0, 3);
         	     writer.writeNext(fin);
         	     Deaths.clear();
         	     Dates.clear();
         	     Confirmed.clear();
         	     
            }
            	
    		date = finallist[i];
    		 String host = "https://covid-19-statistics.p.rapidapi.com/reports?region_province=" + state + "&iso=USA&region_name=US&city_name=" + city + "&date=" + date + "&q=US%20"+state;
    	        String charset = "UTF-8";
    	        // Headers for a request
    	        String x_rapidapi_host = "covid-19-statistics.p.rapidapi.com";
    	        String x_rapidapi_key = "0bd99f8c9dmsh6e55c4aca4b8914p112cb0jsna9125070bad4";//Type here your key
    	        // Params
    	        String s = "Pulp";
    	    // Format query for preventing encoding problems
    	        String query = String.format("s=%s",
    	         URLEncoder.encode(s, charset));
    	        HttpResponse<String> response = Unirest.get(host + "?" + query)
    	        		.header("x-rapidapi-host", x_rapidapi_host)
    	        		.header("x-rapidapi-key", x_rapidapi_key)
    	        		.asString();
    	        //System.out.println(response.getStatus());
    	        Gson gson = new GsonBuilder().setPrettyPrinting().create();
    	        JsonParser jp = new JsonParser();
    	        JsonElement je = jp.parse(response.getBody().toString());
    	        String prettyJsonString = gson.toJson(je);
    	        System.out.print(prettyJsonString);
    	        
    	        
    	        
    	        
    	        
    	       

    	        
    	      
    	        String[] lines = prettyJsonString.split("\n");
    	        for(int x = 20; x < lines.length;x++)
    	        {
    	        	String d = lines[x];
    	        	if(d.contains("\"date\""))
    	        	{
    	        		String tea = d.substring(d.indexOf(": ")+2,d.indexOf(","));
    	        		tea = tea.replaceAll("^\"+|\"+$", "");
    	        		Dates.add(tea);
    	        	}
    	        	if(d.contains("\"confirmed\""))
    	        	{
    	        		String tea = d.substring(d.indexOf(": ")+2,d.indexOf(","));
    	        		tea = tea.replaceAll("^\"+|\"+$", "");
    	        		Confirmed.add(tea);
    	        	}
    	        	if(d.contains("\"deaths\""))
    	        	{
    	        		String tea = d.substring(d.indexOf(": ")+2,d.indexOf(","));
    	        		tea = tea.replaceAll("^\"+|\"+$", "");
    	        		Deaths.add(tea);
    	        	}
    	        }

    	        //System.out.println(Dates);
    	        //System.out.println(Confirmed);
    	        //System.out.println(Deaths);
    	        ArrayList<String> al = new ArrayList<String>();
                al.addAll(Dates);
                al.addAll(Confirmed);
                al.addAll(Deaths);
                String[] fin = new String[3];
        	     System.arraycopy(al.toArray(), 0, fin, 0, 3);
        	     writer.writeNext(fin);
    	}
    	       
    	    /*String[] DatesA = new String[32];
    	    String[] ConfirmedA = new String[32];
    	    String[] DeathsA = new String[32];
    	    System.arraycopy(Dates.toArray(), 0, DatesA, 0, 32);
    	    System.arraycopy(Confirmed.toArray(), 0, ConfirmedA, 0, 32);
    	    System.arraycopy(Deaths.toArray(), 0, DeathsA, 0, 32);


    		CSVWriter writer = new CSVWriter(new FileWriter("E:\\output.csv"));
    		PrintWriter out = new PrintWriter(new FileWriter("E:\\output.csv"));
    		for(int x=0;x<DatesA.length;x++)
    		{
    			out.print(DatesA[x]);
    			out.print(ConfirmedA[x]);
    			out.print(DeathsA[x]);
    			out.print("\n");
    		}*/
    	        
    	    writer.flush();
    	    //out.close();
    	}
    
    }


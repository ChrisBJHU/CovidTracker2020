package hopkins.covid.covidpull;
import java.net.URLEncoder;
import io.radar.sdk.Radar;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.request.GetRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws Exception
    {
    	
        String host = "https://covid-19-statistics.p.rapidapi.com/reports?region_province=Alabama&iso=USA&region_name=US&city_name=Autauga&date=2020-04-16&q=US%20Alabama";
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
        System.out.println(response.getStatus());
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonParser jp = new JsonParser();
        JsonElement je = jp.parse(response.getBody().toString());
        String prettyJsonString = gson.toJson(je);
        System.out.println(prettyJsonString);
    }
}

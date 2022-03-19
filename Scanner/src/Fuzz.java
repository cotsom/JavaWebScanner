import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.LaxRedirectStrategy;

import java.io.IOException;


public class Fuzz {
    void dirScan(String url) throws IOException {
        String Url = "http://"+url+"/robots.txt";
        //HttpClient httpClient = HttpClients.createDefault();
        HttpClient httpClient = HttpClientBuilder.create().setRedirectStrategy(new LaxRedirectStrategy()).build();
        HttpGet httpGet = new HttpGet(Url);
        HttpResponse httpResponse = httpClient.execute(httpGet);
        String response = String.valueOf(httpResponse.getStatusLine());
        System.out.println(response);
        if (response.equals("HTTP/1.1 200 OK")){
            System.out.println("");
        }
    }
}

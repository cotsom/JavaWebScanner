import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.http.client.methods.HttpPost;

import java.io.IOException;


public class Fuzz {
    void dirScan(String url) throws IOException {
        String Url = "http://"+url+"/robots.txt";
        HttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(Url);
        HttpResponse httpResponse = httpClient.execute(httpGet);
        String body = EntityUtils.toString(httpResponse.getEntity());
        System.out.println(body);
    }
}

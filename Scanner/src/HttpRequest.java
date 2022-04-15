import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.LaxRedirectStrategy;

import java.io.*;
import java.net.URL;

public class HttpRequest{
    public HttpRequest(String url) throws FileNotFoundException {
        this.Url = url;
    }

    HttpClient httpClient = HttpClientBuilder.create().setRedirectStrategy(new LaxRedirectStrategy()).build();
    HttpGet httpGet;
    HttpResponse httpResponse;
    String response;
    String Url;
    String finalUrl;
    String directory;

    public void run() {
        finalUrl = Url+directory;
        httpGet = new HttpGet(finalUrl);
        try {
            httpResponse = httpClient.execute(httpGet);
        } catch (IOException e) {
            e.getStackTrace();
        }
        response = String.valueOf(httpResponse.getStatusLine());
        System.out.println("response: "+response);
        httpGet.releaseConnection();
    }


/*    String run(String url) throws IOException {
        String Url = "http://"+url;
        httpGet = new HttpGet(Url);
        httpResponse = httpClient.execute(httpGet);
        response = String.valueOf(httpResponse.getStatusLine());
        System.out.println(response);
        return response;
    }*/
}

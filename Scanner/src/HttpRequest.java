import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.LaxRedirectStrategy;

import java.io.*;
import java.net.URL;

public class HttpRequest {
    public HttpRequest(String url) throws FileNotFoundException {
        this.Url = url;
    }

    HttpClient httpClient = HttpClientBuilder.create().setRedirectStrategy(new LaxRedirectStrategy()).build();
    //HttpClient httpClient = HttpClients.createDefault();
    HttpGet httpGet;
    HttpResponse httpResponse;
    String response;
    String Url;
    String finalUrl;
    String directory;
    String subDomain;

    public void dir() {
        finalUrl = "http://" + Url + directory;
        httpGet = new HttpGet(finalUrl);
        System.out.println(finalUrl);
        try {
            httpResponse = httpClient.execute(httpGet);
        } catch (IOException e) {
            e.getStackTrace();
        }
        response = String.valueOf(httpResponse.getStatusLine());
        System.out.println("response: "+response);
        httpGet.releaseConnection();
    }

    public void sub(){
        finalUrl = "http://" + subDomain + Url;
        System.out.println(finalUrl);
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
}

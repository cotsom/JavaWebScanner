import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpOptions;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.net.URL;
import java.util.Scanner;

public class HttpRequest {
    public HttpRequest(String url) throws FileNotFoundException {
        this.Url = url;
    }

    HttpClient httpClient = HttpClientBuilder.create().build();
    HttpGet httpGet;
    HttpOptions httpOptions;
    HttpResponse httpResponse;
    String response;
    String Url;
    String finalUrl;
    String directory;
    String parameter;
    String domain;
    Scanner console = new Scanner(System.in);
    String userAnswer;

    public void dir() throws IOException {
        finalUrl = "http://" + Url + "/" + directory;
        httpGet = new HttpGet(finalUrl);
        //System.out.println(finalUrl);
        httpResponse = httpClient.execute(httpGet);
        if (httpResponse == null){
            response = "HTTP/1.1 404 Not Found";
        }else {
            response = String.valueOf(httpResponse.getStatusLine());
        }
        //System.out.println("response: "+response);
        httpGet.releaseConnection();
    }

    public void sub() throws IOException {
        finalUrl = "http://" + domain + "." + Url;
        System.out.println(finalUrl);
        httpGet = new HttpGet(finalUrl);
        httpResponse = httpClient.execute(httpGet);
        if (httpResponse == null){
            response = "HTTP/1.1 404 Not Found";
        }else {
            response = String.valueOf(httpResponse.getStatusLine());
        }
        System.out.println("response: "+response);
        httpGet.releaseConnection();

    }

    public void OPTIONSrequest() throws IOException {
        finalUrl = "http://" + Url;
        httpOptions = new HttpOptions(finalUrl);
        httpResponse = httpClient.execute(httpOptions);
        System.out.println("Allowed methods: ");
        System.out.println(httpOptions.getAllowedMethods(httpResponse));
        httpOptions.releaseConnection();
    }

    public void getAllHeaders() throws IOException {
        finalUrl = "http://" + Url;
        httpGet = new HttpGet(finalUrl);
        httpResponse = httpClient.execute(httpGet);
        System.out.println("Do you want to see all headers? Y/N");
        userAnswer = console.nextLine();

        if (!userAnswer.equals("N")) {
            System.out.println("Printing Response Header...\n");

            Header[] headers = httpResponse.getAllHeaders();
            for (Header header : headers) {
                System.out.println(header.getName()
                        + " : " + header.getValue());

            }
        }

        System.out.println("Important and interesting headers:");
        /*Important headers check*/

        try {
            String server = httpResponse.getFirstHeader("Server").getValue();
            System.out.println("Server - " + server);
            System.out.println("CVE found: https://cve.mitre.org/cgi-bin/cvekey.cgi?keyword=" + server);
        } catch (NullPointerException e) {
            System.out.println("Key 'Server' is not found!");
        }

        try {
            String CSP = httpResponse.getFirstHeader("Content-Security-Policy").getValue();
            System.out.println("Content-Security-Policy - " + CSP);
        } catch (NullPointerException e) {
            System.out.println("Key 'CSP' is not found!");
        }

        try {
            String XPB = httpResponse.getFirstHeader("x-powered-by").getValue();
            System.out.println("x-powered-by - " + XPB);
            System.out.println("CVE found: https://cve.mitre.org/cgi-bin/cvekey.cgi?keyword=" + XPB);
        } catch (NullPointerException e) {
            System.out.println("Key 'x-powered-by' is not found!");
        }

        try {
            String XBS = httpResponse.getFirstHeader("x-backend-server").getValue();
            System.out.println("x-backend-server - " + XBS);
        } catch (NullPointerException e) {
            System.out.println("Key 'x-backend-server' is not found!");
        }

        try {
            String XFO = httpResponse.getFirstHeader("X-Frame-Options").getValue();
            System.out.println("X-Frame-Options - " + XFO);
        } catch (NullPointerException e) {
            System.out.println("The anti-clickjacking X-Frame-Options header is not present");
        }


        System.out.println("\n Done");
        httpGet.releaseConnection();

        /*String body = EntityUtils.toString(httpResponse.getEntity());
        System.out.println(body);*/

    }

    public String getHtml() throws IOException {
        finalUrl = "http://" + Url + "/" + directory;
        httpGet = new HttpGet(finalUrl);
        httpResponse = httpClient.execute(httpGet);
        String body = EntityUtils.toString(httpResponse.getEntity());
        httpGet.releaseConnection();

        return body;
    }

    public String requestParam() throws IOException {
        finalUrl ="http://" + Url.replace("payload", parameter);
        //System.out.println(finalUrl);
        httpGet = new HttpGet(finalUrl);
        httpResponse = httpClient.execute(httpGet);
        String body = EntityUtils.toString(httpResponse.getEntity());
        httpGet.releaseConnection();

        return body;
    }

}

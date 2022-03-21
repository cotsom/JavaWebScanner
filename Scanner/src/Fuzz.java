import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.LaxRedirectStrategy;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;


public class Fuzz {
    Scanner console = new Scanner(System.in);

    void dirScan(String url) throws IOException {
        //---------Getting url and path to wordlist---------
        String Url;
        System.out.println("input path to your wordlist");
        String wordlist = console.nextLine();
        File file = new File(wordlist);
        if (!file.isFile()){
            System.out.println(file.getName()+" not a file");
            return;
        }
        FileInputStream input = new FileInputStream(file);

        //HttpClient httpClient = HttpClients.createDefault();
        HttpClient httpClient = HttpClientBuilder.create().setRedirectStrategy(new LaxRedirectStrategy()).build();
        //HttpGet httpGet = new HttpGet(Url);
        //HttpResponse httpResponse = httpClient.execute(httpGet);
        //String response = String.valueOf(httpResponse.getStatusLine());
        //System.out.println(response);
        //if (response.equals("HTTP/1.1 200 OK")){
        //    System.out.println("OK!");
        //}

        int i = -1;
        while((i=input.read())!=-1){
            Url = "http://"+url+"/"+(char)i;
            System.out.println(Url);
            //HttpGet httpGet = new HttpGet(Url);
            //HttpResponse httpResponse = httpClient.execute(httpGet);
            //String response = String.valueOf(httpResponse.getStatusLine());
            //System.out.println(response);
        }
    }
}
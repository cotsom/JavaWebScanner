import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.LaxRedirectStrategy;

import java.io.*;
import java.util.Scanner;


public class Fuzz extends Thread{
    Scanner console = new Scanner(System.in);

    void dirScan(String url) throws IOException {
        //---------Getting url and path to wordlist---------
        String Url = url;
        System.out.println("input path to your wordlist");
        String wordlist = console.nextLine();
        File file = new File(wordlist);
        if (!file.isFile()){
            System.out.println(file.getName()+" not a file");
            return;
        }
        //создаем объект FileReader для объекта File
        FileReader fr = new FileReader(file);
        //создаем BufferedReader с существующего FileReader для построчного считывания
        BufferedReader reader = new BufferedReader(fr);

        HttpRequest request = new HttpRequest(url);


        //if (response.equals("HTTP/1.1 200 OK")){
        //    System.out.println("OK!");
        //}

        String line = "";
        while((line = reader.readLine()) != null){
            request.directory = "/" + line;
            request.run();
        }
    }
}
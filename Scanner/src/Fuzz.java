import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.LaxRedirectStrategy;

import java.io.*;
import java.net.UnknownHostException;
import java.util.Scanner;


public class Fuzz {
    Scanner console = new Scanner(System.in);
    String Url;
    String mode;
    String wordlistPath;
    File wordlistFile;
    //создаем объект FileReader для объекта File
    FileReader fr;
    //создаем BufferedReader с существующего FileReader для построчного считывания
    BufferedReader reader;

    void settings(String url) throws IOException {
        System.out.println("Выберите режим сканирования:\ndir\nsubdomain");
        mode = console.nextLine();
        if (!(mode.equals("dir")) && !(mode.equals("subdomain"))){
            System.out.println("Выбран несуществующий режим");
            return;
        }
        this.Url = url;
        System.out.println("input path to your wordlist");
        this.wordlistPath = console.nextLine();
        this.wordlistFile = new File(wordlistPath);
        if (!wordlistFile.isFile()){
            System.out.println(wordlistFile.getName()+" not a file");
            return;
        }
        fr = new FileReader(wordlistFile);
        reader = new BufferedReader(fr);

    }

    public void dirScan() throws IOException {
        //---------Getting url and path to wordlist---------

        HttpRequest request = new HttpRequest(Url);


        //if (response.equals("HTTP/1.1 200 OK")){
        //    System.out.println("OK!");
        //}

        String line = "";
        while(true){
            try {
                if ((line = reader.readLine()) == null) break;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            request.directory = "/" + line;
            request.dir();
        }
    }

    void subdomainScan() throws IOException {
        HttpRequest request = new HttpRequest(Url);

        String line = "";
        while(true){
            try {
                if ((line = reader.readLine()) == null) break;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            request.domain = line + ".";
            try {
                request.sub();
            } catch (UnknownHostException e) {
                System.out.println("** server can't find " + line + ".google.com: NXDOMAIN");
            }
        }
    }

    /*void commonScan() throws IOException {
        HttpRequest request = new HttpRequest(Url);
        request.OPTIONSrequest();
        request.getAllHeaders();
    }*/
}
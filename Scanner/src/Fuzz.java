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
    //FileReader startFr;
    //создаем BufferedReader с существующего FileReader для построчного считывания
    BufferedReader reader;
    //BufferedReader startReader;
    //Кол-во строк в файле
    //int totalLines = 0;
    //int linesNow = 0;

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
        //startFr = new FileReader(wordlistFile);
        //startReader = new BufferedReader(startFr);
        reader = new BufferedReader(fr);
        //Count number of lines
        //while (startReader.readLine() != null) totalLines++;

    }

    public void dirScan() throws IOException {
        //---------Getting url and path to wordlist---------

        HttpRequest request = new HttpRequest(Url);


        //if (response.equals("HTTP/1.1 200 OK")){
        //    System.out.println("OK!");
        //}
        System.out.println("Searching directories...");
        String line = "";
        while(true){
            //System.out.println(linesNow + "/" + totalLines);
            try {
                if ((line = reader.readLine()) == null) break;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            request.directory = line;
            request.dir();
            if (!request.response.equals("HTTP/1.1 404 Not Found")){
                System.out.println(request.finalUrl);
            }
            //linesNow++;
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
            request.domain = line;
            try {
                request.sub();
            } catch (UnknownHostException e) {
                System.out.println("** server can't find " + line + "." + Url + ": NXDOMAIN");
            }
        }
    }
}
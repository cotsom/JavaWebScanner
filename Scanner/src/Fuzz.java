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
    String Url;
    String mode;
    String wordlistPath;
    File wordlistFile;
    //создаем объект FileReader для объекта File
    FileReader fr;
    //создаем BufferedReader с существующего FileReader для построчного считывания
    BufferedReader reader;

    void settings(String url) throws IOException {
        System.out.println("Выберите режим сканирования:\ndir\nsubdomain\ndomains");
        mode = console.nextLine();
        if (!(mode.equals("dir")) && !(mode.equals("subdomain")) && !(mode.equals("domains"))){
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

    public void dirScan() {
        //---------Getting url and path to wordlist---------
       /* String Url = url;
        System.out.println("input path to your wordlist");
        String wordlist = console.nextLine();
        File file = new File(wordlist);
        if (!file.isFile()){
            System.out.println(file.getName()+" not a file");
            return;
        }

        fr = new FileReader(file);
        reader = new BufferedReader(fr);*/

        HttpRequest request = null;
        try {
            request = new HttpRequest(Url);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }


        //if (response.equals("HTTP/1.1 200 OK")){
        //    System.out.println("OK!");
        //}

        String line = "";
        while(true){
            try {
                if (!((line = reader.readLine()) != null)) break;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            request.directory = "/" + line;
            request.dir();
        }
    }

    void subdomainScan(){
        HttpRequest request = null;
        try {
            request = new HttpRequest(Url);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        String line = "";
        while(true){
            try {
                if (!((line = reader.readLine()) != null)) break;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            request.subDomain = line + ".";
            request.sub();
        }
    }
}
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Fuzz fuzz = new Fuzz();
        CommonScan common = new CommonScan("google.com");
        Scanner console = new Scanner(System.in);

        System.out.println("Choose mode: fuzz, common");
        String mode = console.nextLine();

        if (mode.equals("fuzz")) {
            fuzz.settings("google.com");
            if (fuzz.mode.equals("dir")) {
                fuzz.dirScan();
            } else if (fuzz.mode.equals("subdomain")) {
                fuzz.subdomainScan();
            }
        } else if (mode.equals("common")) {
            common.commonScan();
        }


        //HttpPost httpPost = new HttpPost(yandexSite);
        //httpPost.setHeader("qwe", "qwe");
        //httpResponse = httpClient.execute(httpPost);
        //body = EntityUtils.toString(httpResponse.getEntity());
        //System.out.println(body);
    }
}
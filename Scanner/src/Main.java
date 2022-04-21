import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        /*HttpRequest request = new HttpRequest("vk.com");
        request.directory = "";
        request.dir();*/
        Fuzz fuzz = new Fuzz();
        fuzz.settings("yandex.ru");
        if (fuzz.mode.equals("dir")) {
            fuzz.dirScan();
        } else if (fuzz.mode.equals("subdomain")) {
            fuzz.subdomainScan();
        }
        //HttpPost httpPost = new HttpPost(yandexSite);
        //httpPost.setHeader("qwe", "qwe");
        //httpResponse = httpClient.execute(httpPost);
        //body = EntityUtils.toString(httpResponse.getEntity());
        //System.out.println(body);
    }
}
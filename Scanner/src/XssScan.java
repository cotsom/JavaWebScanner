import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Scanner;

public class XssScan {
    void settings() {
        System.out.println("Insert url address with \"payload\" instead of parameter that you want to check\n" +
                "NB: adding or removing the initial parameters that are sent along with the parameter being checked can help or hinder xss detection, try experimenting with this");
        Url = console.nextLine();
        if (!(Url.contains("payload"))){
            System.out.println("\"payload\" string not found");
            return;
        }
    }

    Scanner console = new Scanner(System.in);
    String Url;
    String startPayload = "TestStringPayload";
    String payload = "pl%22pl%3Cpl%3Epl'";
    String page;
    boolean payloadInAttribute;
    String nonFilterableCharacters = "";
    String[] xssTagsPatterns = {">)) pl<script>", "\"\"pl>pl>))))", "\">)) pl<scr<script>ipt>", "pl\"'pl\"pl\"''pl>>pl<< pl<<>pl>", "))\"==pl<pl>pl>pl<"};
    String[] xssAttributePatterns = {"pl\"", "pl\"\"", "pl'pl\"", "\"pl'pl\"))'"};
    int pointsForXss = 0;

    void check(String htmlPage){
        if (htmlPage.contains("pl\"")){
            if (!nonFilterableCharacters.contains("\"")) {
                nonFilterableCharacters += "\" ";
            }
        }
        if (htmlPage.contains("pl<")) {
            if (!nonFilterableCharacters.contains("<")) {
                nonFilterableCharacters += "< ";
            }
            pointsForXss++;
        }
        if (htmlPage.contains("pl>")) {
            if (!nonFilterableCharacters.contains(">")) {
                nonFilterableCharacters += "> ";
            }
            pointsForXss++;
        }
        if (htmlPage.contains("pl'")) {
            if (!nonFilterableCharacters.contains("'")) {
                nonFilterableCharacters += "' ";
            }
        }
    }

    void XssScan() throws IOException {
        HttpRequest request = new HttpRequest(Url);
        request.parameter = startPayload;
        page = request.requestParam();

        if (page.contains(startPayload)){
            //Check contains attribute user's input
            if (page.contains("\""+startPayload+"\"")){
                payloadInAttribute = true;
            }

            //Common xss scan
            request.parameter = payload;
            page = request.requestParam();
            check(page);
            System.out.println("the following characters are not filtered at first glance: " + nonFilterableCharacters);
            nonFilterableCharacters = "";

            if (pointsForXss >= 2){
                System.out.println("You can embed tags!");
                pointsForXss = 0;
            }

            //Searching xss in attributes
            if (payloadInAttribute){
                for (String payload : xssAttributePatterns) {
                    request.parameter = URLEncoder.encode(payload, "UTF-8");
                    page = request.requestParam();
                    check(page);
                }
                System.out.println("Scanning attribute revealed that the following characters are not filtered in attribute: " + nonFilterableCharacters);
                nonFilterableCharacters = "";
            }

            //Searching xss with tags
            for (String payload : xssTagsPatterns) {
                request.parameter = URLEncoder.encode(payload, "UTF-8");
                page = request.requestParam();
                check(page);
                if (page.contains("pl<script>")){
                    System.out.println("managed to implement tag \"<script>\", XSS possible!");
                }
            }
            System.out.println("Scanning with tags revealed that the following characters are not filtered: " + nonFilterableCharacters);
            nonFilterableCharacters = "";

        }else {
            System.out.println("something went wrong and the DOM does not contain user input.\n" +
                    "Ensure that an input tag exists on this page with the provided name attribute and implies the location of the user input on the page");
        }

    }
}

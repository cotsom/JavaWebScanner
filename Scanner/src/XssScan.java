import java.io.FileNotFoundException;
import java.io.IOException;
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
    String nonFilterableCharacters = "";
    String[] xssPatterns = {">)) <script>"}

    void XssScan() throws IOException {
        HttpRequest request = new HttpRequest(Url);
        request.parameter = startPayload;
        page = request.requestParam();

        if (page.contains(startPayload)){
            request.parameter = payload;
            page = request.requestParam();
            //System.out.println(page);
            if (page.contains("pl\"")){
                nonFilterableCharacters += "\" ";
            }
            if (page.contains("pl<")) {
                nonFilterableCharacters += "< ";
            }
            if (page.contains("pl>")) {
                nonFilterableCharacters += "> ";
            }
            if (page.contains("pl'")) {
                nonFilterableCharacters += "' ";
            }
            System.out.println("the following characters are not filtered at first glance: " + nonFilterableCharacters);

        }else {
            System.out.println("something went wrong and the DOM does not contain user input.\n" +
                    "Ensure that an input tag exists on this page with the provided name attribute and implies the location of the user input on the page");
        }

    }
}

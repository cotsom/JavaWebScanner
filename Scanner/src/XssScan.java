import java.io.FileNotFoundException;
import java.io.IOException;

public class XssScan {
    public XssScan(String Url) throws FileNotFoundException {
        this.Url = Url;
    }

    String Url;
    String startPayload = "TestStringPayload";
    String payload = "pl%22pl%3Cpl%3Epl'";
    String page;
    String nonFilterableCharacters = "";

    void XssScan() throws IOException {
        HttpRequest request = new HttpRequest(Url);
        request.directory = startPayload;
        System.out.println(Url);
        page = request.getHtml();

        if (page.contains(startPayload)){
            request.directory = payload;
            page = request.getHtml();
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

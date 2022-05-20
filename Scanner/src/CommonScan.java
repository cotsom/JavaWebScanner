import java.io.IOException;

public class CommonScan {
    public CommonScan(String Url){
        this.Url = Url;
    }
    String Url;
    String dirPayload = "TestStringPayload";
    String page404Html;
    String nonFilterableCharacters = "";
    int pointsForXss = 0;
    void commonScan() throws IOException {
        HttpRequest request = new HttpRequest(Url);
        request.OPTIONSrequest();
        request.getAllHeaders();
        request.directory = "robots.txt";
        request.dir();
        if (request.response.equals("HTTP/1.1 200 OK")){
            System.out.println("robots.txt: " + request.finalUrl);
        }else {
            System.out.println("robots.txt not found");
        }

        request.directory = dirPayload;
        page404Html = request.getHtml();
        //System.out.println(page404Html);
        if (page404Html.contains(dirPayload)){
            System.out.println("404 page contains user input");

            dirPayload = "pl%22pl%3Cpl%3Epl'";
            request.directory = dirPayload;
            page404Html = request.getHtml();
            //System.out.println(page404Html);

            check(page404Html);

            dirPayload = "%7B%7B7*7%7D%7D";
            request.directory = dirPayload;
            page404Html = request.getHtml();

            if (page404Html.contains("{{49}}")){
                System.out.println("Possible SSTI!");
            }else {
                System.out.println("SSTI doesn't seem possible");
            }

        }else {
            System.out.println("404 page doesn't contains user input");
        }
    }
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
        if (pointsForXss >= 2){
            System.out.println("XSS Possible!");
        }else {
            System.out.println("XSS doesn't seem possible");
        }
    }
}

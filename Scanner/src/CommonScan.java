import java.io.IOException;

public class CommonScan {
    public CommonScan(String Url){
        this.Url = Url;
    }
    String Url;
    String dirPayload = "TestStringPayload";
    String page404Html;
    void commonScan() throws IOException {
        HttpRequest request = new HttpRequest(Url);
        request.OPTIONSrequest();
        request.getAllHeaders();
        request.directory = "robots.txt";
        request.dir();

        request.directory = dirPayload;
        page404Html = request.getHtml();
        //System.out.println(page404Html);
        if (page404Html.contains(dirPayload)){
            System.out.println("404 page contains user input");

            dirPayload = "pl%22pl%3Cpl%3Epl'";
            request.directory = dirPayload;
            page404Html = request.getHtml();
            //System.out.println(page404Html);

            if (page404Html.contains("pl\"") || page404Html.contains("pl<") || page404Html.contains("pl>")  || page404Html.contains("pl'")){
                System.out.println("Possible XSS!");
            }else {
                System.out.println("XSS doesn't seem possible");
            }

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
}

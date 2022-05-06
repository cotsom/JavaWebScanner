import java.io.IOException;

public class CommonScan {
    public CommonScan(String Url){
        this.Url = Url;
    }
    String Url;
    void commonScan() throws IOException {
        HttpRequest request = new HttpRequest(Url);
        request.OPTIONSrequest();
        request.getAllHeaders();
    }
}

package read;

import java.io.IOException;
import java.net.*;
import java.net.http.*;
import java.net.http.HttpResponse.*;
import org.json.*;

public class DataFetcher {

    private URI uri;
    private String requestDataStr;
    private JSONObject requestData;
    private String data;

    public void setUrl(String url) {
        try {
            URI uri = new URI(url);
            this.uri = uri;
        }
        catch (URISyntaxException e) {
            ;
        }
    }

    public URI getUrl() {
        return this.uri;
    }

    public String getRequestDataStr() {
        return this.requestDataStr;
    }

    private void setRequestDataStr(String requestDataStr) {
        this.requestDataStr = requestDataStr;
        if (!requestDataStr.equals("error")) {
            JSONObject data = new JSONObject(this.requestDataStr);
            this.requestData = data;
        }
        else {
            JSONObject data = new JSONObject("{\"description\": \"\"}");
            this.requestData = data;
        }
    }

    public JSONObject getRequestData() {
        return this.requestData;
    }

    public DataFetcher(String url) {
        setUrl(url);
    }

    public void fetchData() {
        HttpRequest request = HttpRequest
            .newBuilder()
            .uri(this.uri)
            .GET()
            .build();
        HttpClient client = HttpClient
            .newHttpClient();
        try{
            HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
            setRequestDataStr(response.body());
        }
        catch (Exception e) {
            setRequestDataStr("Could not fetch data");
        }
    }

}
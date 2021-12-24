package read;

import java.net.*;
import java.net.http.*;
import org.json.JSONObject;

import exceptions.NotFoundException;

public class DataFetcher {

    private URI uri;
    private String requestDataStr;
    private JSONObject requestData;

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
        System.out.println("Parsing description...");
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
        System.out.println("Initializing data fetcher...");
        setUrl(url);
    }

    public void fetchData() throws NotFoundException {
        HttpRequest request = HttpRequest
            .newBuilder()
            .uri(this.uri)
            .GET()
            .build();
        HttpClient client = HttpClient
            .newHttpClient();
        try{
            System.out.println("Fetching data...");
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            setRequestDataStr(response.body());
        }
        catch (Exception e) {
            throw new NotFoundException();
        }
    }

}
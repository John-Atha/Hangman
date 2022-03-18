package read;

import java.io.*;
import java.net.*;
import java.net.http.*;
import java.nio.charset.Charset;

import org.json.JSONException;
import org.json.JSONObject;

import exceptions.NotFoundException;

public class DataFetcher {

    private URI uri;
    private String requestDataStr;
    private JSONObject requestData;
    private String url;

    public void setUrl(String url) {
        try {
            this.url = url;
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
            System.out.println(data);
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
//        HttpRequest request = HttpRequest
//            .newBuilder()
//            .uri(this.uri)
//            .GET()
//            .build();
//        HttpClient client = HttpClient
//            .newHttpClient();
//        try{
//            System.out.println("Fetching data from ..." + this.uri);
//            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
//            System.out.println(response);
//            System.out.println(response.body());
//            setRequestDataStr(response.body());
//        }
//        catch (Exception e) {
//            System.out.println(e);
//            throw new NotFoundException();
//        }
        try {
            String json_text = this.getJson();
            setRequestDataStr(json_text);
        }
        catch (Exception e) {
            System.out.println(e);
            throw new NotFoundException();
        }
    }

    private String getJsonText(Reader rd) throws IOException {
        StringBuilder builder = new StringBuilder();
        int q;
        while((q=rd.read())!=-1) {
            builder.append((char) q);
        }
        return builder.toString();
    }

    public String getJson() throws IOException, JSONException {
        InputStream stream = new URL(this.url).openStream();
        try {
            BufferedReader rd = new BufferedReader((new InputStreamReader(stream, Charset.forName("UTF-8"))));
            String text = getJsonText(rd);
            return text;
        }
        finally {
            stream.close();
        }
    }
}
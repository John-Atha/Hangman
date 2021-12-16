package read;

import java.net.*;
import java.net.http.*;
import java.net.http.HttpResponse.*;

public class DataFetcher {

    private URI uri;
    private String requestData;

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

    public String getRequestData() {
        return this.requestData;
    }

    public void setRequestData(String requestData) {
        this.requestData = requestData;
    }

    public DataFetcher(String url) {
        setUrl(url);
    }

    public String fetchData() {
        HttpRequest request = HttpRequest.newBuilder()
            .uri(this.uri)
            .headers("key1", "value1", "key2", "value2")
            .GET()
            .build();
        HttpClient client = HttpClient.newHttpClient();
        client.sendAsync(request, BodyHandlers.ofString())
            .thenApply(HttpResponse::body)
            .thenAccept(System.out::println)
            .join();
        return request.toString();
    }

    public static void main(String[] args) {
        String requestUrl = "https://openlibrary.org/works/OL45883W.json";
        DataFetcher fetcher = new DataFetcher(requestUrl);
        String data = fetcher.fetchData();
        System.out.println(data);
    }

}
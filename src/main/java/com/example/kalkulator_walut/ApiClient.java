package com.example.kalkulator_walut;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Duration;

public class ApiClient
{
    public HttpClient client;
    public HttpRequest request;
    public HttpResponse<String> response;
    public String url = "http://api.nbp.pl/api/exchangerates/tables/A/";
    public String startUrl = "http://api.nbp.pl/api/exchangerates/tables/A/";


    public ApiClient() throws IOException, InterruptedException {
    client = HttpClient.newHttpClient();
    setRequest(startUrl);
    response = sendRequest();
    }

    public ApiClient(String url) throws IOException, InterruptedException {
        client = HttpClient.newHttpClient();
        setRequest(url);
        response = sendRequest();
    }


    public HttpRequest getRequest()
    {
        return request;
    }

    public void setRequest(String url)
    {
        this.request = HttpRequest.newBuilder()
                .header("accept", "application/json")
                .uri(URI.create(url))
                .timeout(Duration.ofSeconds(10))
                .GET()
                .build();
    }
    public HttpResponse<String> sendRequest() throws IOException, InterruptedException {
        HttpResponse<String> resp = client.send(request, BodyHandlers.ofString());
          return resp;
    }


}

package com.kenzie.app;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class CustomHttpClient {

    //Determines URL based on user's input
    public static String getDesiredURL(ClueCriteria clueCriteria) {
        switch (clueCriteria) {
            case RANDOM:
                return "https://jservice.kenzie.academy/api/clues";
            case ASIAN_HISTORY:
                return "https://jservice.kenzie.academy/api/clues/?category=13";
            case WORLD_HISTORY:
                return "https://jservice.kenzie.academy/api/clues/?category=7";
            case SLOGANEERING:
                return "https://jservice.kenzie.academy/api/clues/?category=4";
            default:
                return "";
        }
    }

    //TODO: Write sendGET method that takes URL and returns response
    //GET request
    public static String sendGET(String URLString) {
        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create(URLString);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .GET()
                .build();
        try {
            HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
            int statusCode = httpResponse.statusCode();
            if (statusCode == 200) {
                return httpResponse.body();
            } else {
                return String.format("GET request failed: %d status code received", statusCode);
            }
        } catch (IOException | InterruptedException e) {
            return e.getMessage();
        }
    }

    //Creates a List of Clues based on the GET request
    public static ClueListDTO getClueList(String httpResponseBody) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();

        ClueListDTO clueListDTO = objectMapper.readValue(httpResponseBody, ClueListDTO.class);

        return clueListDTO;
    }
}


package com.kingcode.cashingwithconcurrentmapcache.app.translation;

import com.kingcode.cashingwithconcurrentmapcache.app.Translator;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@Slf4j
public class AzureTranslatorClient implements Translator {

    private final String url;
    private final String subscriptionKey;
    private final String region;

    public AzureTranslatorClient(
        String url,
        String subscriptionKey,
        String region
    ) {
        this.url = url;
        this.subscriptionKey = subscriptionKey;
        this.region = region;
    }

    @Override
    public String translate(String textToTranslate) {
        RestTemplate rest = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Ocp-Apim-Subscription-Key", this.subscriptionKey);
        headers.add("Ocp-Apim-Subscription-Region", this.region);
        headers.add("Content-Type", "application/json");
        ResponseEntity<String> response = rest.postForEntity(
            this.url,
            new HttpEntity<>("[{'Text': '" + textToTranslate + "'}]", headers),
            String.class
        );
        System.out.println("I am translation process in AzureTranslatorClient");
        return extractTranslationFromJson(response.getBody());
    }

    private String extractTranslationFromJson(String inputJson) {
        try {
            JSONParser parser = new JSONParser();
            JSONArray jsonArray = (JSONArray) parser.parse(inputJson);
            JSONObject jsonObject = (JSONObject) jsonArray.get(0);
            JSONArray jsonTranslation = (JSONArray) jsonObject.get("translations");
            JSONObject translation = (JSONObject) jsonTranslation.get(0);
            return (String) translation.get("text");
        } catch (ParseException ex) {
            log.warn(ex.getMessage());
            return "Translation didn't work";
        }
    }
}
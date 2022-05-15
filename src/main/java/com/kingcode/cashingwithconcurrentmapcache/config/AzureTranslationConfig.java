package com.kingcode.cashingwithconcurrentmapcache.config;

import com.kingcode.cashingwithconcurrentmapcache.app.cashe.CachingTranslations;
import com.kingcode.cashingwithconcurrentmapcache.app.Translator;
import com.kingcode.cashingwithconcurrentmapcache.app.translation.AzureTranslatorClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AzureTranslationConfig {

    private final String url;
    private final String subscriptionKey;
    private final String region;

    public AzureTranslationConfig(
        @Value("${azure_translator_client.url}") String url,
        @Value("${azure_translator_client.subscriptionKey}") String subscriptionKey,
        @Value("${azure_translator_client.region}") String region) {
        this.url = url;
        this.subscriptionKey = subscriptionKey;
        this.region = region;
    }


    @Bean
    public Translator translatorConfig() {
        AzureTranslatorClient actualAzureTranslator = new AzureTranslatorClient(
            url,
            subscriptionKey,
            region
        );
        return new CachingTranslations(actualAzureTranslator);
    }
}
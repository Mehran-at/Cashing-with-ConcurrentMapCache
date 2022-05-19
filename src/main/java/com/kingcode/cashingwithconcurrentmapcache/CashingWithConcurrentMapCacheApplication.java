package com.kingcode.cashingwithconcurrentmapcache;

import com.kingcode.cashingwithconcurrentmapcache.app.Translator;
import com.kingcode.cashingwithconcurrentmapcache.config.AzureTranslationConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CashingWithConcurrentMapCacheApplication {

    private static AzureTranslationConfig azureTranslationConfig;

    public CashingWithConcurrentMapCacheApplication(AzureTranslationConfig azureTranslationConfig) {
        CashingWithConcurrentMapCacheApplication.azureTranslationConfig = azureTranslationConfig;
    }

    public static void main(String[] args) {
        SpringApplication.run(CashingWithConcurrentMapCacheApplication.class, args);
        System.out.println(get());
    }

    public static String get() {
        Translator translator = azureTranslationConfig.translatorConfig();
        translator.translate("Hallo leute");
        return translator.translate("Hallo leute");
    }
}
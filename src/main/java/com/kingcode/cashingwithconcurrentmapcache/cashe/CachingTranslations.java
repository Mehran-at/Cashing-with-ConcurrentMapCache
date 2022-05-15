package com.kingcode.cashingwithconcurrentmapcache.cashe;

import com.google.common.hash.Hashing;

import org.springframework.cache.concurrent.ConcurrentMapCache;

import java.nio.charset.StandardCharsets;

public class CachingTranslations implements Translator {

    private final Translator translator;
    private final ConcurrentMapCache cache = new ConcurrentMapCache("translatedTexts");

    public CachingTranslations(Translator Translator) {
        this.translator = Translator;
    }

    @Override
    public String translate(String text) {
        String hashKey = makeHashKeyOf(text);
        String retrieveFromCache = retrieveTranslationFromCache(hashKey);
        if (retrieveFromCache != null) {
            return retrieveFromCache;
        }
        String translation = translator.translate(text);
        storeTranslationInCache(hashKey, translation);
        return translation;
    }

    private String makeHashKeyOf(String text) {
        return Hashing.sha256()
            .hashString(text, StandardCharsets.UTF_8)
            .toString();
    }

    private void storeTranslationInCache(String hashKey, String result) {
        cache.put(hashKey, result);
    }

    private String retrieveTranslationFromCache(String hashKey) {
        return this.cache.get(hashKey,String.class);
    }
}

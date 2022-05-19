package com.kingcode.cashingwithconcurrentmapcache.app.cashe;

import com.google.common.hash.Hashing;
import com.kingcode.cashingwithconcurrentmapcache.app.Translator;
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
            System.out.println("I found this in the cache" + retrieveFromCache);
            return retrieveFromCache;
        }
        String translation = translator.translate(text);
        storeTranslationInCache(hashKey, translation);
        System.out.println("I am cashing process in CachingTranslations");
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
        return this.cache.get(hashKey, String.class);
    }
}
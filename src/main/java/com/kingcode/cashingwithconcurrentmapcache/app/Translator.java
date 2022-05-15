package com.kingcode.cashingwithconcurrentmapcache.app;

@FunctionalInterface
public interface Translator {
    String translate(String textToTranslate);
}
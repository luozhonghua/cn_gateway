package com.github.gate.client.future;

import com.github.gate.client.rpc.ILanguageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

/**
 * luozhonghua on 2017/7/13.
 */
@Component
public class LanguageFuture {
    @Autowired
    private ILanguageService languageService;

    public CompletableFuture<String> sayChineseHelloWorld() {
        return CompletableFuture.supplyAsync(()->{
            return languageService.sayChineseHelloWorld();
        });
    }

    public CompletableFuture<String> sayEnglishHelloWorld() {
        return CompletableFuture.supplyAsync(()->{
            return languageService.sayEnglishHelloWorld();
        });
    }
}

package com.github.gate.client.rest;

import com.github.gate.client.future.LanguageFuture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.concurrent.ExecutionException;

/**
 * luozhonghua on 2017/7/9.
 */
@Controller
//@RequestMapping("")/
public class SaysomethingRest {
    @Autowired
    private LanguageFuture languageFuture;
    @RequestMapping("test")
    public @ResponseBody String test() throws ExecutionException, InterruptedException {
        return languageFuture.sayChineseHelloWorld().get()+" : "+languageFuture.sayEnglishHelloWorld().get();
    }
}

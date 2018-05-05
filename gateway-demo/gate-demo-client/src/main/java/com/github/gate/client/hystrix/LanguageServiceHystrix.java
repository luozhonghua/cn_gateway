package com.github.gate.client.hystrix;

import com.github.gate.client.rpc.ILanguageService;
import org.springframework.stereotype.Component;

/**
 * luozhonghua on 2017/7/9.
 */
@Component
public class LanguageServiceHystrix implements ILanguageService{
    public String sayChineseHelloWorld() {
        return "hystrix 哑巴！";
    }

    public String sayEnglishHelloWorld() {
        return "hystrix 哑巴！";
    }
}

package com.kairosLens.config;

import okhttp3.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.boot.test.context.TestConfiguration;

@TestConfiguration
public class TestConfig {
    @Bean
    public OkHttpClient testHttpClient() {
        return new OkHttpClient();
    }

    @Bean
    public String openAiApiKey(){
        return "dummy-key";
    }
}

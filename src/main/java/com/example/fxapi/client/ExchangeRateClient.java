package com.example.fxapi.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(name = "exchangeRateClent", url = "https://api.exchangerate.host")
public interface ExchangeRateClient {

    @GetMapping("/latest")
    Map<String, Object> getRates(
            @RequestParam("access_key") String accessKey,
            @RequestParam("base") String base,
            @RequestParam("symbols") String symbols);

}

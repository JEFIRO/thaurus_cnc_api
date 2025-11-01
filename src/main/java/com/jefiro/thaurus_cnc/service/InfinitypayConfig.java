package com.jefiro.thaurus_cnc.service;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class InfinitypayConfig {

    @Value("${infinityPay.handle}")
    private String handle;

    @Value("${infinityPay.redirect.url}")
    private String redirectUrl;

    @Value("${infinityPay.webhook.url}")
    private String webhookUrl;

}

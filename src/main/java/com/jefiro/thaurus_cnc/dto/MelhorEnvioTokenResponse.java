package com.jefiro.thaurus_cnc.dto;

// Crie este record para receber a resposta
public record MelhorEnvioTokenResponse(
        String token_type,
        Long expires_in,
        String access_token,
        String refresh_token
) {}
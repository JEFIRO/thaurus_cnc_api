package com.jefiro.thaurus_cnc.dto;

public record MelhorEnvioTokenDTO(
        String grant_type,
        Integer client_id,
        String client_secret,
        String redirect_uri,
        String code,
        String refresh_token
) {
    public MelhorEnvioTokenDTO(String grant_type, Integer client_id, String client_secret, String redirect_uri, String code, String refresh_token) {
        this.grant_type = grant_type;
        this.client_id = client_id;
        this.client_secret = client_secret;
        this.redirect_uri = redirect_uri;
        this.code = code;
        this.refresh_token = refresh_token;
    }
}

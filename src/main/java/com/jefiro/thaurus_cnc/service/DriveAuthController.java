package com.jefiro.thaurus_cnc.service;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/drive")
public class DriveAuthController {

    @GetMapping("/auth")
    public void auth(HttpServletResponse response) throws Exception {
        GoogleAuthorizationCodeFlow flow = GoogleDriveOAuth.getFlow();

        String url = flow.newAuthorizationUrl()
                .setRedirectUri(GoogleDriveOAuth.REDIRECT_URI)
                .build();

        response.sendRedirect(url);
    }

    @GetMapping("/callback")
    public String callback(@RequestParam("code") String code) throws Exception {
        GoogleAuthorizationCodeFlow flow = GoogleDriveOAuth.getFlow();

        GoogleTokenResponse tokenResponse = flow
                .newTokenRequest(code)
                .setRedirectUri(GoogleDriveOAuth.REDIRECT_URI)
                .execute();

        flow.createAndStoreCredential(tokenResponse, "user");

        return "Autorizado com sucesso!";
    }
}

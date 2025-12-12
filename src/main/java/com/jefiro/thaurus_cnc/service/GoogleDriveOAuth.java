package com.jefiro.thaurus_cnc.service;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.DriveScopes;
import org.springframework.core.io.ClassPathResource;

import java.io.InputStreamReader;
import java.util.Collections;

public class GoogleDriveOAuth {

    public static final String REDIRECT_URI = "https://www.thauruscnc.com.br/api/drive/callback";

    public static GoogleAuthorizationCodeFlow getFlow() throws Exception {
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(
                JacksonFactory.getDefaultInstance(),
                new InputStreamReader(
                        new ClassPathResource("credentials/client_secret.json").getInputStream()
                )
        );

        return new GoogleAuthorizationCodeFlow.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                JacksonFactory.getDefaultInstance(),
                clientSecrets,
                Collections.singletonList(DriveScopes.DRIVE)
        )
                .setAccessType("offline")
                .setApprovalPrompt("force")
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File("tokens")))
                .build();
    }
}


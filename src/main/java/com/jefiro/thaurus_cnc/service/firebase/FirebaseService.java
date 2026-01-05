package com.jefiro.thaurus_cnc.service.firebase;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import jakarta.annotation.PostConstruct;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;

@Service
public class FirebaseService {

    @PostConstruct
    public void init() throws IOException {

        if (!FirebaseApp.getApps().isEmpty()) {
            return;
        }

        ClassPathResource resource =
                new ClassPathResource("keys/thauruscnc-861da-firebase-adminsdk-fbsvc-a4b1f3f8aa.json");

        try (InputStream serviceAccount = resource.getInputStream()) {

            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();

            FirebaseApp.initializeApp(options);
        }
    }
}

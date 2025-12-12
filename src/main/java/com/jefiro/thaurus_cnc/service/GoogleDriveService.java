package com.jefiro.thaurus_cnc.service;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.FileContent;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.Permission;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;

@Service
public class GoogleDriveService {

    public Drive getDriveService() throws Exception {
        GoogleAuthorizationCodeFlow flow = GoogleDriveOAuth.getFlow();
        Credential credential = flow.loadCredential("user");

        return new Drive.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                JacksonFactory.getDefaultInstance(),
                credential
        ).setApplicationName("Thaurus CNC").build();
    }


    public String upload(MultipartFile multipartFile) throws Exception {
        Drive driveService = getDriveService();

        java.io.File tempFile = java.io.File.createTempFile("upload-", multipartFile.getOriginalFilename());
        multipartFile.transferTo(tempFile);

        File fileMetadata = new File();
        fileMetadata.setName(multipartFile.getOriginalFilename());

        String folderId = "1SWuJizXbFZqOlFRfFo2wHr9NCSKl5uwt";

        fileMetadata.setParents(Collections.singletonList(folderId));


        FileContent fileContent = new FileContent(multipartFile.getContentType(), tempFile);

        File uploaded = driveService.files()
                .create(fileMetadata, fileContent)
                .setFields("id")
                .execute();

        Permission permission = new Permission()
                .setRole("reader")
                .setType("anyone");

        driveService.permissions().create(uploaded.getId(), permission).execute();

        tempFile.delete();

        return uploaded.getId();
    }

    public boolean delete(String fileId) {
        try {
            Drive driveService = getDriveService();
            driveService.files().delete(fileId).execute();
            return true;
        }catch (Exception e) {
            return false;
        }
    }



}
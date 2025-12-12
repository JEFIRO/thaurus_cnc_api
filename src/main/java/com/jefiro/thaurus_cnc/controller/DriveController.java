package com.jefiro.thaurus_cnc.controller;

import com.jefiro.thaurus_cnc.service.GoogleDriveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/photo")
public class DriveController {
    @Autowired
    private GoogleDriveService googleDriveService;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            String fileId = googleDriveService.upload(file);

            String publicUrl = "https://drive.google.com/uc?export=view&id=" + fileId;

            return ResponseEntity.ok(publicUrl);

        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body("Erro ao enviar imagem: " + e.getMessage());
        }
    }

    @DeleteMapping("{fileId}")
    public ResponseEntity<?> deleteImg(String fileId) {
        boolean response = googleDriveService.delete(fileId);
        if (response) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

}

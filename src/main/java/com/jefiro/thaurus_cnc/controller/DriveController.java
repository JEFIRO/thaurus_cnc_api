package com.jefiro.thaurus_cnc.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
@RestController
@RequestMapping("/photo")
public class DriveController {
    @Value("${upload.dir}")
    private String uploadDir;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            File directory = new File(uploadDir);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            String originalName = file.getOriginalFilename();
            String ext = originalName.substring(originalName.lastIndexOf("."));

            String fileName = System.currentTimeMillis() + ext;
            Path filePath = Paths.get(uploadDir, fileName);

            Files.write(filePath, file.getBytes());

            String fileUrl = ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/images/")
                    .path(fileName)
                    .toUriString();

            return ResponseEntity.ok(Map.of("url", fileUrl));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Erro ao fazer upload");
        }
    }

    @DeleteMapping("/{fileName}")
    public ResponseEntity<?> deleteImg(@PathVariable String fileName) {
        try {
            Path filePath = Paths.get(uploadDir, fileName);
            Files.deleteIfExists(filePath);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}


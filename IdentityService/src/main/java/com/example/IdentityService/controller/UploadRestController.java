package com.example.IdentityService.controller;

import com.example.IdentityService.dto.request.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@RestController
@RequestMapping("/upload")
@Slf4j
public class UploadRestController {
    @Value("${file.upload-dir}")
    private String uploadDir;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    public ApiResponse<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            // Kiểm tra file có tồn tại không
            if (file.isEmpty()) {
                return ApiResponse.<String>builder()
                        .result("File trống")
                        .build();
            }

            // Kiểm tra định dạng file
            String contentType = file.getContentType();
            if (contentType == null || !contentType.startsWith("image/")) {
                return ApiResponse.<String>builder()
                        .result("File phải là hình ảnh")
                        .build();
            }

            // Tạo tên file mới để tránh trùng lặp
            String originalFilename = file.getOriginalFilename();
            String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            String newFilename = "product_" + System.currentTimeMillis() + extension;

            // Tạo thư mục nếu chưa tồn tại
            File directory = new File(uploadDir);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            // Lưu file
            Path filePath = Paths.get(uploadDir, newFilename);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            log.info("File uploaded successfully: {}", newFilename);

            return ApiResponse.<String>builder()
                    .result(newFilename) // Trả về tên file mới để lưu vào database
                    .build();
        } catch (IOException e) {
            log.error("Error uploading file: ", e);
            return ApiResponse.<String>builder()
                    .result("Lỗi khi upload file: " + e.getMessage())
                    .build();
        }
    }

}

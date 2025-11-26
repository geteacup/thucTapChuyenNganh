package com.example.IdentityService.controller;

import com.example.IdentityService.dto.request.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;

@RestController
@RequestMapping("/upload")
@Slf4j
public class UploadRestController {

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

            // Kiểm tra kích thước file (5MB)
            if (file.getSize() > 5 * 1024 * 1024) {
                return ApiResponse.<String>builder()
                        .result("Kích thước file không được vượt quá 5MB")
                        .build();
            }

            // Convert image to byte array and encode to Base64
            byte[] imageBytes = file.getBytes();
            String base64Image = Base64.getEncoder().encodeToString(imageBytes);

            log.info("File uploaded successfully, size: {} bytes", imageBytes.length);

            return ApiResponse.<String>builder()
                    .result(base64Image) // Trả về Base64 string để lưu vào database
                    .build();
        } catch (IOException e) {
            log.error("Error uploading file: ", e);
            return ApiResponse.<String>builder()
                    .result("Lỗi khi upload file: " + e.getMessage())
                    .build();
        }
    }

}

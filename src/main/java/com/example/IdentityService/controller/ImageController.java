package com.example.IdentityService.controller;

import com.example.IdentityService.service.TonKhoService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/images")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ImageController {
    TonKhoService tonKhoService;

    @GetMapping("/{maSP}")
    public ResponseEntity<byte[]> getImage(@PathVariable String maSP) {
        try {
            byte[] imageData = tonKhoService.getImageByProductId(maSP);

            if (imageData == null || imageData.length == 0) {
                return ResponseEntity.notFound().build();
            }

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG); // Default to JPEG, could be enhanced to detect type
            headers.setContentLength(imageData.length);
            headers.setCacheControl("max-age=3600"); // Cache for 1 hour

            return new ResponseEntity<>(imageData, headers, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}

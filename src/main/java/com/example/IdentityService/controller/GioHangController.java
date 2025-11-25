package com.example.IdentityService.controller;

import com.example.IdentityService.dto.request.ApiResponse;
import com.example.IdentityService.dto.request.DeleteItemGioHangRequest;
import com.example.IdentityService.dto.request.ChiTietGioHangRequest;
import com.example.IdentityService.dto.response.ChiTietGioHangResponse;
import com.example.IdentityService.entity.ChiTietGioHangId;
import com.example.IdentityService.service.ChiTietGioHangService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/giohang")
@Slf4j
@AllArgsConstructor
public class GioHangController {
    ChiTietGioHangService chiTietGioHangService;

    @PostMapping("/add")
    public ApiResponse<ChiTietGioHangResponse> addToCart(@RequestBody ChiTietGioHangRequest request) {
        log.info("Adding to cart for user: {}", request.getUser());
        return ApiResponse.<ChiTietGioHangResponse>builder()
                .result(chiTietGioHangService.createChiTietGioHang(request))
                .build();
    }

    @GetMapping("/plus/{maSp}")
    public ApiResponse<String> plusItem(@PathVariable String maSp, @RequestParam String userId) {
        log.info("Increasing quantity for user: {} and product: {}", userId, maSp);
        chiTietGioHangService.plusChiTietGioHang(userId, maSp);
        return ApiResponse.<String>builder()
                .result("Thanh Cong")
                .build();
    }

    @GetMapping("/minus/{maSp}")
    public ApiResponse<String> minusItem(@PathVariable String maSp, @RequestParam String userId) {
        log.info("Decreasing quantity for user: {} and product: {}", userId, maSp);
        chiTietGioHangService.minusChiTietGioHang(userId, maSp);
        return ApiResponse.<String>builder()
                .result("Thanh Cong")
                .build();
    }

    @GetMapping("/my")
    public ApiResponse<List<ChiTietGioHangResponse>> getMyCart(@RequestParam String userId) {
        log.info("Getting cart for user: {}", userId);
        return ApiResponse.<List<ChiTietGioHangResponse>>builder()
                .result(chiTietGioHangService.findAllGioHangByID(userId))
                .build();
    }

    @DeleteMapping("/{maSp}")
    public ApiResponse<String> deleteItem(@PathVariable String maSp, @RequestParam String userId) {
        log.info("Deleting item for user: {} and product: {}", userId, maSp);
        chiTietGioHangService.deleteChiTietGioHang(userId, maSp);
        return ApiResponse.<String>builder()
                .result("Thanh Cong")
                .build();
    }
}

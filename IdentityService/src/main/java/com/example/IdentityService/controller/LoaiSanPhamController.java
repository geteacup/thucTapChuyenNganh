package com.example.IdentityService.controller;

import com.example.IdentityService.dto.request.ApiResponse;
import com.example.IdentityService.dto.request.LoaiSanPhamRequest;
import com.example.IdentityService.dto.response.LoaiSanPhamResponse;
import com.example.IdentityService.service.LoaiSanPhamService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/loaisanpham")
@Slf4j
@AllArgsConstructor
public class LoaiSanPhamController {
    LoaiSanPhamService loaiSanPhamService;

    @GetMapping
    public ApiResponse<List<LoaiSanPhamResponse>> getAllLoaiSanPham() {
        return ApiResponse.<List<LoaiSanPhamResponse>>builder()
                .result(loaiSanPhamService.getAllLoaiSanPham())
                .build();
    }
    @GetMapping("/{maLoai}")
    public ApiResponse<LoaiSanPhamResponse> getLoaiSanPham(@PathVariable String maLoai){
        return ApiResponse.<LoaiSanPhamResponse>builder()
                .result(loaiSanPhamService.getLoaiSanPhamById(maLoai))
                .build();
    }
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<LoaiSanPhamResponse> createLoaiSanPham(@RequestBody LoaiSanPhamRequest loaiSanPhamRequest){
        return ApiResponse.<LoaiSanPhamResponse>builder()
                .result(loaiSanPhamService.createLoaiSanPham(loaiSanPhamRequest))
                .build();
    }
    @PutMapping("/{maLoai}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<LoaiSanPhamResponse> updateLoaiSanPham(@PathVariable String maLoai , @RequestBody LoaiSanPhamRequest loaiSanPhamRequest){
        return ApiResponse.<LoaiSanPhamResponse>builder()
                .result(loaiSanPhamService.updateLoaiSanPham(maLoai,loaiSanPhamRequest))
                .build();
    }
    @DeleteMapping("/{maLoai}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<String> deleteLoaiSanPham(@PathVariable String maLoai){
        loaiSanPhamService.deleteLoaiSanPham(maLoai);
        return ApiResponse.<String>builder()
                .result("thanh cong")
                .build();
    }
}

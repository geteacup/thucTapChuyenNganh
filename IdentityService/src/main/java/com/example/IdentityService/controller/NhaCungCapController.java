package com.example.IdentityService.controller;

import com.example.IdentityService.dto.request.ApiResponse;
import com.example.IdentityService.dto.request.LoaiSanPhamRequest;
import com.example.IdentityService.dto.request.NhaCungCapRequest;
import com.example.IdentityService.dto.response.LoaiSanPhamResponse;
import com.example.IdentityService.dto.response.NhaCungCapResponse;
import com.example.IdentityService.service.NhaCungCapService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/nhacungcap")
@Slf4j
@AllArgsConstructor
public class NhaCungCapController {
    NhaCungCapService nhacungCapService;

    @GetMapping
    public ApiResponse<List<NhaCungCapResponse>> getAllNhaCungCap() {
        return ApiResponse.<List<NhaCungCapResponse>>builder()
                .result(nhacungCapService.getAllNhaCungCap())
                .build();
    }
    @GetMapping("/{maNCC}")
    public ApiResponse<NhaCungCapResponse> getNhaCungCap(@PathVariable String maNCC){
        return ApiResponse.<NhaCungCapResponse>builder()
                .result(nhacungCapService.getNhaCungCapById(maNCC))
                .build();
    }
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<NhaCungCapResponse> createNhaCungCap(@RequestBody NhaCungCapRequest nhaCungCapRequest){
        return ApiResponse.<NhaCungCapResponse>builder()
                .result(nhacungCapService.createNhaCungCap(nhaCungCapRequest))
                .build();
    }
    @PutMapping("/{maNCC}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<NhaCungCapResponse> updateNhaCungCap(@PathVariable String maNCC , @RequestBody NhaCungCapRequest nhaCungCapRequest){
        return ApiResponse.<NhaCungCapResponse>builder()
                .result(nhacungCapService.updateNhaCungCap(maNCC,nhaCungCapRequest))
                .build();
    }
    @DeleteMapping("/{maNCC}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<String> deleteNhaCungCap(@PathVariable String maNCC){
        nhacungCapService.deleteNhaCungCap(maNCC);
        return ApiResponse.<String>builder()
                .result("thanh cong")
                .build();
    }
}

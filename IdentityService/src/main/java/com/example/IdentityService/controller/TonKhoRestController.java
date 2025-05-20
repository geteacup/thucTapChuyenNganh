package com.example.IdentityService.controller;

import com.example.IdentityService.dto.request.ApiResponse;
import com.example.IdentityService.dto.request.NhapHangRequest;
import com.example.IdentityService.dto.request.TonKhoRequest;
import com.example.IdentityService.dto.response.TonKhoResponse;
import com.example.IdentityService.entity.TonKho;
import com.example.IdentityService.service.TonKhoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tonkho")
@Slf4j
public class TonKhoRestController {
    TonKhoService tonKhoService;

    @Autowired
    public TonKhoRestController(TonKhoService tonKhoService) {
        this.tonKhoService = tonKhoService;
    }

    @PostMapping
    public ApiResponse<TonKhoResponse> postTonKho(@RequestBody TonKhoRequest request) {
        return ApiResponse.<TonKhoResponse>builder()
        .result(tonKhoService.createTonKho(request))
                .build();
    }
    @PutMapping("/nhapHang")
    public ApiResponse<String> nhapHang(@RequestBody NhapHangRequest request) {
        tonKhoService.plusTonKho(request.getMaSp(), request.getSoLuong());
        return ApiResponse.<String>builder()
                .result("thanhcong")
                .build();
    }
    @GetMapping
    public ApiResponse<List<TonKhoResponse>> getTonKho() {
        return ApiResponse.<List<TonKhoResponse>>builder()
                .result(                tonKhoService.getALlTonKho()
)
                .build();

    }

    @GetMapping("/{maSP}")
    public ApiResponse<TonKhoResponse> getTonKho(@PathVariable String maSP) {
        return ApiResponse.<TonKhoResponse>builder()
                .result(tonKhoService.getTonKhoById(maSP))
                .build();
    }
    @GetMapping("/loaisanpham/{maLoai}")
    public ApiResponse<List<TonKhoResponse>> getTonKhoByLoai(@PathVariable String maLoai) {
        return ApiResponse.<List<TonKhoResponse>>builder()
                .result(tonKhoService.getAllTonKhoByLoaiSanPham(maLoai))
                .build();
    }
    @GetMapping("/nhacungcap/{maNCC}")
    public ApiResponse<List<TonKhoResponse>> getTonKhoByNCC(@PathVariable String maNCC) {
        return ApiResponse.<List<TonKhoResponse>>builder()
                .result(tonKhoService.getAllTonKhoByNhaCungCap(maNCC))
                .build();
    }
    @PutMapping("/{maSP}")
    public ApiResponse<TonKhoResponse> updateTonKho(@PathVariable String maSP, @RequestBody TonKhoRequest request) {
        return ApiResponse.<TonKhoResponse>builder()
                .result(tonKhoService.updateTonKho(maSP,request))
                .build();
    }
    @DeleteMapping("/{maSP}")
    public ApiResponse<String> deleteTonKho(@PathVariable String maSP) {
        tonKhoService.deleteTonKho(maSP);
        return ApiResponse.<String>builder()
                .result("thanhcong")
                .build();
    }
}

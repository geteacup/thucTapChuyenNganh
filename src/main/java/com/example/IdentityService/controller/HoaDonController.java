package com.example.IdentityService.controller;

import com.example.IdentityService.dto.request.ApiResponse;
import com.example.IdentityService.dto.request.ChiTietHoaDonByGioHangRequest;
import com.example.IdentityService.dto.request.HoaDonRequest;
import com.example.IdentityService.dto.request.TrangThaiHoaDonRequest;
import com.example.IdentityService.dto.response.ChiTietHoaDonResponse;
import com.example.IdentityService.dto.response.HoaDonResponse;
import com.example.IdentityService.entity.HoaDon;
import com.example.IdentityService.service.ChiTietGioHangService;
import com.example.IdentityService.service.ChiTietHoaDonService;
import com.example.IdentityService.service.HoaDonService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hoadon")
@Slf4j
@AllArgsConstructor
public class HoaDonController {
    HoaDonService hoaDonService;
    ChiTietHoaDonService chiTietHoaDonService;
    ChiTietGioHangService chiTietGioHangService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    public ApiResponse<List<HoaDonResponse>> getAllHoadon() {
        return ApiResponse.<List<HoaDonResponse>>builder()
                .result(hoaDonService.getAllHoaDon())
                .build();
    }
    @GetMapping("/tong/{maHD}")
    public ApiResponse<String> tongTienHoaDon(@PathVariable("maHD") String maHD) {
        return ApiResponse.<String>builder()
                .result(String.valueOf(chiTietHoaDonService.tongTienHoaDon(maHD)))
                .build();
    }
    @PostMapping
    public ApiResponse<String> createHoaDon(@RequestBody HoaDonRequest hoaDonRequest) {
        String maHD = hoaDonService.createHoaDon(hoaDonRequest).getMaHD();
        ChiTietHoaDonByGioHangRequest chiTietHoaDonByGioHangRequest
                = ChiTietHoaDonByGioHangRequest.builder()
                .maHD(maHD)
                .User(hoaDonRequest.getUserKH())
                .build();
        chiTietHoaDonService.createChiTietHoaDonByGioHang(chiTietHoaDonByGioHangRequest);
        chiTietGioHangService.deleteByUser(hoaDonRequest.getUserKH());
        return ApiResponse.<String>builder()
                .result(maHD)
                .build();
    }
    @PostMapping("/settrangthai")
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    public ApiResponse<String> settrangthai(@RequestBody TrangThaiHoaDonRequest request) {
        hoaDonService.changeStatus(request);

        return ApiResponse.<String>builder()
                .result("thanh cong")
                .build();
    }

    @GetMapping("/processing")
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    public ApiResponse<List<HoaDonResponse>> getAllProcessingHoaDon(){
        return ApiResponse.<List<HoaDonResponse>>builder()
                .result(hoaDonService.getAllHoaDonByStatus("processing"))
                .build();
    }
    @GetMapping("/status/{status}")
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    public ApiResponse<List<HoaDonResponse>> getAllAcceptedHoaDon(@PathVariable String status){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        return ApiResponse.<List<HoaDonResponse>>builder()
                .result(hoaDonService.getAllHoaDonByUserAndStatus(username,status))
                .build();
    }
    @GetMapping("/{maHD}")
    public ApiResponse<HoaDonResponse> getHoaDonById(@PathVariable("maHD") String maHD) {
        return ApiResponse.<HoaDonResponse>builder()
                .result(hoaDonService.getHoaDonById(maHD))
                .build();
    }
    @GetMapping("/chitiet/{maHD}")
    public ApiResponse<List<ChiTietHoaDonResponse>> chitietHoaDon(@PathVariable("maHD") String maHD) {
        return ApiResponse.<List<ChiTietHoaDonResponse>>builder()
                .result(chiTietHoaDonService.findAllHoaDonByID(maHD))
                .build();
    }
    @GetMapping("/home/{userID}")
    public ApiResponse<List<HoaDonResponse>> hoaDonByUserKH(@PathVariable("userID") String userID) {
        return ApiResponse.<List<HoaDonResponse>>builder()
                .result(hoaDonService.getAllHoaDonByUserKH(userID))
                .build();
    }
    @GetMapping("/staff/{userID}")
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    public ApiResponse<List<HoaDonResponse>> hoaDonByUserNV(@PathVariable("userID") String userID) {
        return ApiResponse.<List<HoaDonResponse>>builder()
                .result(hoaDonService.getAllHoaDonByUserNV(userID))
                .build();
    }
}

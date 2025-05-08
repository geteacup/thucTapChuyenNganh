package com.example.IdentityService.dto.request;

import com.example.IdentityService.entity.LoaiSanPham;
import com.example.IdentityService.entity.NhaCungCap;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TonKhoRequest {
    private String maSP;
    private String anhSP;
    private String tenSP;
    private String motaSP;
    private String giaSP;
    private float daiSP;
    private float rongSP;
    private int soLuongSP;
    private String loaiSanPham;
    private String nhaCungCap;
}

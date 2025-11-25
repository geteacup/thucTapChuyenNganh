package com.example.IdentityService.dto.response;

import com.example.IdentityService.entity.LoaiSanPham;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TonKhoResponse {
    private String maSP;
    private String anhSP;
    private String tenSP;
    private String motaSP;
    private String giaSP;
    private float daiSP;
    private float rongSP;
    private int soLuongSP;
    private LoaiSanPhamResponse loaiSanPham;
    private NhaCungCapResponse nhaCungCap;
}

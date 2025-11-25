package com.example.IdentityService.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoaiSanPhamResponse {
    private String maLoai;
    private String tenLoai;
    private String motaLoai;
}

package com.example.IdentityService.dto.response;

import com.example.IdentityService.entity.ChiTietHoaDonId;
import com.example.IdentityService.entity.HoaDon;
import com.example.IdentityService.entity.TonKho;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChiTietHoaDonResponse {
    private HoaDonResponse hoaDon;
    private TonKhoResponse tonKho;
    int soLuong;
}

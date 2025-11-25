package com.example.IdentityService.dto.request;

import com.example.IdentityService.entity.ChiTietHoaDonId;
import com.example.IdentityService.entity.HoaDon;
import com.example.IdentityService.entity.TonKho;
import com.example.IdentityService.entity.User;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChiTietHoaDonRequest {
    String hoaDon;
    String tonKho;
    int soLuong;
}

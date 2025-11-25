package com.example.IdentityService.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class ChiTietHoaDon {
    @Id
    ChiTietHoaDonId chiTietHoaDonId;
    @ManyToOne
    @MapsId("maHD")
    @JoinColumn(name="maHD")
    private HoaDon hoaDon;
    @ManyToOne
    @MapsId("maSP")
    @JoinColumn(name="maSP")
    private TonKho tonKho;
    int soLuong;
}

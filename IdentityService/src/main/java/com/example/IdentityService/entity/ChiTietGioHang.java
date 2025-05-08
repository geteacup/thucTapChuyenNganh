package com.example.IdentityService.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class ChiTietGioHang {
    @Id
    private ChiTietGioHangId chiTietGioHangId;
    @ManyToOne
    @MapsId("idKH")
    @JoinColumn(name="idKH")
    private User user;
    @ManyToOne
    @MapsId("maSP")
    @JoinColumn(name="maSP")
    private TonKho tonKho;
    private int soLuong;
}

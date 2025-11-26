package com.example.IdentityService.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class TonKho {
    @Id
    private String maSP;
    @Lob
    private byte[] anhSP;
    private String tenSP;
    private String motaSP;
    private int giaSP;
    private float daiSP;
    private float rongSP;
    private int soLuongSP;
    @ManyToOne
    @JoinColumn(name = "maLoai")
    private LoaiSanPham loaiSanPham;
    @ManyToOne
    @JoinColumn(name = "maNCC")
    private NhaCungCap nhaCungCap;

}

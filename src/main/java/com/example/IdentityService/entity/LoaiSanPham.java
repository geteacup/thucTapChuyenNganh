package com.example.IdentityService.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class LoaiSanPham {
    @Id
    private String maLoai;
    private String tenLoai;
    private String motaLoai;
}

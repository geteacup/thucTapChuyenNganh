package com.example.IdentityService.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Embeddable
public class ChiTietHoaDonId implements Serializable {
    private String maHD;
    private String maSP;
}

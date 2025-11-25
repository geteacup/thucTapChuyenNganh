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
public class ChiTietGioHangId implements Serializable {
    private String idKH;
    private String maSP;
}

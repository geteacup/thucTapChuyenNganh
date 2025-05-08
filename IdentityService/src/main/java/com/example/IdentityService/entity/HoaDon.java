package com.example.IdentityService.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class HoaDon {
    @Id
    String maHD;

    @ManyToOne
    @JoinColumn(name="idKH")
    User userKH;
    @ManyToOne
    @JoinColumn(name="idNV")
    User userNV;
    String status;
    LocalDate ngayTao;
}

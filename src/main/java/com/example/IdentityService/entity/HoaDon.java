package com.example.IdentityService.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class HoaDon {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
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

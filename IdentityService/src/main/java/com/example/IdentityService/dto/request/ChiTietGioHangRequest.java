package com.example.IdentityService.dto.request;

import com.example.IdentityService.entity.ChiTietGioHangId;
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

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChiTietGioHangRequest {
    private String user;
    private String tonKho;
    private int soLuong;
}

package com.example.IdentityService.dto.response;

import com.example.IdentityService.entity.User;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HoaDonResponse {
    String maHD;
    UserResponse userKH;
    UserResponse userNV;
    String status;
    LocalDate ngayTao;
}

package com.example.IdentityService.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HoaDonRequest {
    String maHD;
    String userKH;
    String userNV;
    String status;
    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDate ngayTao;
}

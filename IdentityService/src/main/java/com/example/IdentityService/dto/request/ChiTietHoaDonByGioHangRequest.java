package com.example.IdentityService.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChiTietHoaDonByGioHangRequest {
    String User;
    String maHD;
}

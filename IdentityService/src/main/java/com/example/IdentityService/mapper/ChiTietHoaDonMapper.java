package com.example.IdentityService.mapper;

import com.example.IdentityService.dto.request.ChiTietHoaDonRequest;
import com.example.IdentityService.dto.response.ChiTietHoaDonResponse;
import com.example.IdentityService.entity.ChiTietHoaDon;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")

public interface ChiTietHoaDonMapper {

    ChiTietHoaDonResponse toChiTietHoaDonResponse(ChiTietHoaDon chiTietHoaDon);
}

package com.example.IdentityService.mapper;

import com.example.IdentityService.dto.request.ChiTietGioHangRequest;
import com.example.IdentityService.dto.response.ChiTietGioHangResponse;
import com.example.IdentityService.entity.ChiTietGioHang;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")

public interface ChiTietGioHangMapper {
    ChiTietGioHangResponse toChiTietGioHangResponse(ChiTietGioHang chiTietGioHang);
}

package com.example.IdentityService.mapper;

import com.example.IdentityService.dto.request.HoaDonRequest;
import com.example.IdentityService.dto.response.HoaDonResponse;
import com.example.IdentityService.entity.HoaDon;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
public interface HoaDonMapper {
    @Mapping(target = "userKH", ignore = true)
    @Mapping(target = "userNV", ignore = true)
    HoaDon toHoaDon(HoaDonRequest hoaDonRequest);
    HoaDonResponse toHoaDonResponse(HoaDon hoaDon);
}

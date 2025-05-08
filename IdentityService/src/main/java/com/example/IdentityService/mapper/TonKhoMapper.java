package com.example.IdentityService.mapper;

import com.example.IdentityService.dto.request.TonKhoRequest;
import com.example.IdentityService.dto.response.TonKhoResponse;
import com.example.IdentityService.entity.TonKho;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TonKhoMapper {
    @Mapping(target = "loaiSanPham", ignore = true)
    @Mapping(target = "nhaCungCap", ignore = true)
    TonKho toTonKho(TonKhoRequest tonKhoRequest);
    TonKhoResponse toTonKhoResponse(TonKho tonKho);
}

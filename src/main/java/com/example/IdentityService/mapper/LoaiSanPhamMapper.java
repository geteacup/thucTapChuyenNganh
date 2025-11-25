package com.example.IdentityService.mapper;

import com.example.IdentityService.dto.request.LoaiSanPhamRequest;
import com.example.IdentityService.dto.response.LoaiSanPhamResponse;
import com.example.IdentityService.entity.LoaiSanPham;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface LoaiSanPhamMapper {
    LoaiSanPham toLoaiSanPham(LoaiSanPhamRequest loaiSanPhamRequest);
    LoaiSanPhamResponse toLoaiSanPhamResponse(LoaiSanPham loaiSanPham);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateLoaiSanPham(@MappingTarget LoaiSanPham loaiSanPham, LoaiSanPhamRequest request);
}

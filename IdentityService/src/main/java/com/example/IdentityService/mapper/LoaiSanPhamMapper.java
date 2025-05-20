package com.example.IdentityService.mapper;

import com.example.IdentityService.dto.request.LoaiSanPhamRequest;
import com.example.IdentityService.dto.response.LoaiSanPhamResponse;
import com.example.IdentityService.entity.LoaiSanPham;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface LoaiSanPhamMapper {
    LoaiSanPham toLoaiSanPham(LoaiSanPhamRequest loaiSanPhamRequest);
    LoaiSanPhamResponse toLoaiSanPhamResponse(LoaiSanPham loaiSanPham);
    void updateLoaiSanPham(@MappingTarget LoaiSanPham loaiSanPham, LoaiSanPhamRequest request);
}

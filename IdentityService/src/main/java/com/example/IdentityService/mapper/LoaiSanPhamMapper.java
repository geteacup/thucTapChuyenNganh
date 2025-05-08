package com.example.IdentityService.mapper;

import com.example.IdentityService.dto.request.LoaiSanPhamRequest;
import com.example.IdentityService.dto.response.LoaiSanPhamResponse;
import com.example.IdentityService.entity.LoaiSanPham;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")

public interface LoaiSanPhamMapper {
    LoaiSanPham toLoaiSanPham(LoaiSanPhamRequest LoaiSanPhamRequest);
    LoaiSanPhamResponse toLoaiSanPhamResponse(LoaiSanPham LoaiSanPham);
}

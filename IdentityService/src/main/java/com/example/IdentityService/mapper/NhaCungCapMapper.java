package com.example.IdentityService.mapper;

import com.example.IdentityService.dto.request.NhaCungCapRequest;
import com.example.IdentityService.dto.response.NhaCungCapResponse;
import com.example.IdentityService.entity.NhaCungCap;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface NhaCungCapMapper {
    NhaCungCap toNhaCungCap(NhaCungCapRequest nhaCungCapRequest);
    NhaCungCapResponse toNhaCungCapResponse(NhaCungCap nhaCungCap);
}

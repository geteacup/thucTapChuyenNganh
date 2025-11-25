package com.example.IdentityService.mapper;

import com.example.IdentityService.dto.request.NhaCungCapRequest;
import com.example.IdentityService.dto.response.NhaCungCapResponse;
import com.example.IdentityService.entity.NhaCungCap;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface NhaCungCapMapper {
    NhaCungCap toNhaCungCap(NhaCungCapRequest nhaCungCapRequest);
    NhaCungCapResponse toNhaCungCapResponse(NhaCungCap nhaCungCap);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateNhaCungCap(@MappingTarget NhaCungCap nhaCungCap, NhaCungCapRequest nhaCungCapRequest);
}

package com.example.IdentityService.mapper;

import com.example.IdentityService.dto.request.TonKhoRequest;
import com.example.IdentityService.dto.response.TonKhoResponse;
import com.example.IdentityService.entity.TonKho;
import org.mapstruct.*;

import java.util.Base64;

@Mapper(componentModel = "spring")
public interface TonKhoMapper {
    @Mapping(target = "loaiSanPham", ignore = true)
    @Mapping(target = "nhaCungCap", ignore = true)
    @Mapping(target = "anhSP", source = "anhSP", qualifiedByName = "stringToByteArray")
    TonKho toTonKho(TonKhoRequest tonKhoRequest);

    @Mapping(target = "loaiSanPham", ignore = true)
    @Mapping(target = "nhaCungCap", ignore = true)
    @Mapping(target = "anhSP", source = "anhSP", qualifiedByName = "stringToByteArray")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateTonKho(TonKhoRequest tonKhoRequest, @MappingTarget TonKho tonKho);

    @Mapping(target = "anhSP", source = "anhSP", qualifiedByName = "byteArrayToString")
    TonKhoResponse toTonKhoResponse(TonKho tonKho);

    // Custom mapping methods for image conversion
    @Named("stringToByteArray")
    default byte[] stringToByteArray(String base64String) {
        if (base64String == null || base64String.isEmpty()) {
            return null;
        }
        try {
            return Base64.getDecoder().decode(base64String);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    @Named("byteArrayToString")
    default String byteArrayToString(byte[] bytes) {
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        return Base64.getEncoder().encodeToString(bytes);
    }
}

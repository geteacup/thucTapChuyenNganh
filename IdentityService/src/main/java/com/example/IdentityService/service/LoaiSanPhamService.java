package com.example.IdentityService.service;

import com.example.IdentityService.dto.request.LoaiSanPhamRequest;
import com.example.IdentityService.dto.response.LoaiSanPhamResponse;
import com.example.IdentityService.dto.response.NhaCungCapResponse;
import com.example.IdentityService.entity.LoaiSanPham;
import com.example.IdentityService.entity.NhaCungCap;
import com.example.IdentityService.mapper.LoaiSanPhamMapper;
import com.example.IdentityService.repository.LoaiSanPhamRepository;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Data
@Service
@Slf4j
public class LoaiSanPhamService {
    private final LoaiSanPhamRepository loaiSanPhamRepository;
    private final LoaiSanPhamMapper loaiSanPhamMapper;
    public LoaiSanPhamService(LoaiSanPhamRepository loaiSanPhamRepository
    , LoaiSanPhamMapper loaiSanPhamMapper) {
        this.loaiSanPhamRepository = loaiSanPhamRepository;
        this.loaiSanPhamMapper = loaiSanPhamMapper;
    }

    public List<LoaiSanPhamResponse> getAllLoaiSanPham() {
        List<LoaiSanPham> loaiSanPhams = loaiSanPhamRepository.findAll();
        List<LoaiSanPhamResponse> loaiSanPhamResponses = new ArrayList<>();
        loaiSanPhamResponses=
                loaiSanPhams.stream().map(nhaCungCap -> loaiSanPhamMapper.toLoaiSanPhamResponse(nhaCungCap)).toList();
        return loaiSanPhamResponses;
    }

    public LoaiSanPhamResponse createLoaiSanPham(LoaiSanPhamRequest loaiSanPhamRequest) {
        LoaiSanPham loaiSanPham = loaiSanPhamMapper.toLoaiSanPham(loaiSanPhamRequest);
        loaiSanPhamRepository.save(loaiSanPham);
        return loaiSanPhamMapper.toLoaiSanPhamResponse(loaiSanPham);
    }
    public void deleteLoaiSanPham(String maLoai) {
        loaiSanPhamRepository.deleteById(maLoai);
    }
    public LoaiSanPhamResponse updateLoaiSanPham(String maLoai, LoaiSanPhamRequest loaiSanPhamRequest) {
        LoaiSanPham loaiSanPham = loaiSanPhamRepository.findById(maLoai)
                .orElseThrow(() -> new RuntimeException("khong tim thay"));
        loaiSanPham = loaiSanPhamMapper.toLoaiSanPham(loaiSanPhamRequest);
        loaiSanPhamRepository.save(loaiSanPham);
        return loaiSanPhamMapper.toLoaiSanPhamResponse(loaiSanPham);
    }
}

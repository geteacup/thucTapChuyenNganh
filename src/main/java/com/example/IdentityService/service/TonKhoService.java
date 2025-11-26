package com.example.IdentityService.service;

import com.example.IdentityService.dto.request.TonKhoRequest;
import com.example.IdentityService.dto.response.TonKhoResponse;
import com.example.IdentityService.entity.LoaiSanPham;
import com.example.IdentityService.entity.NhaCungCap;
import com.example.IdentityService.entity.TonKho;
import com.example.IdentityService.exception.AppException;
import com.example.IdentityService.mapper.TonKhoMapper;
import com.example.IdentityService.repository.LoaiSanPhamRepository;
import com.example.IdentityService.repository.NhaCungCapRepository;
import com.example.IdentityService.repository.TonKhoRepository;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Data
@Service
@Slf4j
public class TonKhoService {
    TonKhoRepository tonKhoRepository;
    LoaiSanPhamRepository loaiSanPhamRepository;
    NhaCungCapRepository nhaCungCapRepository;
    TonKhoMapper tonKhoMapper;

    @Autowired
    public TonKhoService(TonKhoRepository tonKhoRepository, LoaiSanPhamRepository loaiSanPhamRepository,
            NhaCungCapRepository nhaCungCapRepository, TonKhoMapper tonKhoMapper) {
        this.tonKhoRepository = tonKhoRepository;
        this.loaiSanPhamRepository = loaiSanPhamRepository;
        this.nhaCungCapRepository = nhaCungCapRepository;
        this.tonKhoMapper = tonKhoMapper;
    }

    public List<TonKhoResponse> getALlTonKho() {
        List<TonKho> tonKhoList = tonKhoRepository.findAll();
        List<TonKhoResponse> tonKhoResponseList = new ArrayList<>();
        tonKhoResponseList = tonKhoList.stream().map(tonKho -> tonKhoMapper.toTonKhoResponse(tonKho)).toList();
        return tonKhoResponseList;
    }

    public List<TonKhoResponse> getAllTonKhoByLoaiSanPham(String maLoai) {
        LoaiSanPham loaiSanPham = loaiSanPhamRepository.findById(maLoai)
                .orElseThrow(() -> new RuntimeException("somethingworng"));
        List<TonKho> tonKhoList = tonKhoRepository.findAllByLoaiSanPham(loaiSanPham);
        return tonKhoList.stream().map(tonKhoMapper::toTonKhoResponse).toList();
    }

    public List<TonKhoResponse> getAllTonKhoByNhaCungCap(String maNCC) {
        NhaCungCap nhaCungCap = nhaCungCapRepository.findById(maNCC)
                .orElseThrow(() -> new RuntimeException("somethingworng"));
        List<TonKho> tonKhoList = tonKhoRepository.findAllByNhaCungCap(nhaCungCap);
        return tonKhoList.stream().map(tonKhoMapper::toTonKhoResponse).toList();
    }

    public TonKhoResponse getTonKhoById(String maSP) {
        TonKho tonKho = tonKhoRepository.findById(maSP)
                .orElseThrow(() -> new RuntimeException("SomethingWorng"));
        TonKhoResponse tonKhoResponse = tonKhoMapper.toTonKhoResponse(tonKho);
        return tonKhoResponse;
    }

    public TonKhoResponse createTonKho(TonKhoRequest tonKhoRequest) {
        TonKho tonKho = new TonKho();
        tonKho = tonKhoMapper.toTonKho(tonKhoRequest);
        if (tonKhoRequest.getLoaiSanPham() != null) {
            LoaiSanPham loaiSanPham = loaiSanPhamRepository.findById(tonKhoRequest.getLoaiSanPham())
                    .orElseThrow(() -> new RuntimeException(
                            "Không tìm thấy loại sản phẩm với ID: " + tonKhoRequest.getLoaiSanPham()));
            tonKho.setLoaiSanPham(loaiSanPham);
        }
        if (tonKhoRequest.getNhaCungCap() != null) {
            NhaCungCap nhaCungCap = nhaCungCapRepository.findById(tonKhoRequest.getNhaCungCap())
                    .orElseThrow(() -> new RuntimeException(
                            "Không tìm thấy nhà cung cấp với ID: " + tonKhoRequest.getLoaiSanPham()));
            ;
            tonKho.setNhaCungCap(nhaCungCap);
        }
        tonKhoRepository.save(tonKho);
        return tonKhoMapper.toTonKhoResponse(tonKho);
    }

    public void deleteTonKho(String maSP) {
        tonKhoRepository.deleteById(maSP);
    }

    public TonKhoResponse updateTonKho(String maSP, TonKhoRequest tonKhoRequest) {
        TonKho tonKho = tonKhoRepository.findById(maSP).orElseThrow(() -> new RuntimeException("not Found"));
        tonKhoMapper.updateTonKho(tonKhoRequest, tonKho);
        if (tonKhoRequest.getLoaiSanPham() != null) {
            LoaiSanPham loaiSanPham = loaiSanPhamRepository.findById(tonKhoRequest.getLoaiSanPham())
                    .orElseThrow(() -> new RuntimeException(
                            "Không tìm thấy loại sản phẩm với ID: " + tonKhoRequest.getLoaiSanPham()));
            tonKho.setLoaiSanPham(loaiSanPham);
        }
        if (tonKhoRequest.getNhaCungCap() != null) {
            NhaCungCap nhaCungCap = nhaCungCapRepository.findById(tonKhoRequest.getNhaCungCap())
                    .orElseThrow(() -> new RuntimeException(
                            "Không tìm thấy nhà cung cấp với ID: " + tonKhoRequest.getLoaiSanPham()));
            ;
            tonKho.setNhaCungCap(nhaCungCap);
        }
        tonKhoRepository.save(tonKho);
        return tonKhoMapper.toTonKhoResponse(tonKho);
    }

    public void plusTonKho(String maSP, int soLuong) {
        TonKho tonKho = tonKhoRepository.findById(maSP).orElseThrow(() -> new RuntimeException("not Found"));
        tonKho.setSoLuongSP(tonKho.getSoLuongSP() + soLuong);
        tonKhoRepository.save(tonKho);
    }

    public byte[] getImageByProductId(String maSP) {
        TonKho tonKho = tonKhoRepository.findById(maSP)
                .orElseThrow(() -> new RuntimeException("Product not found: " + maSP));
        return tonKho.getAnhSP();
    }
}

package com.example.IdentityService.service;

import com.example.IdentityService.dto.request.ChiTietGioHangRequest;
import com.example.IdentityService.dto.response.ChiTietGioHangResponse;
import com.example.IdentityService.dto.response.ChiTietHoaDonResponse;
import com.example.IdentityService.entity.*;
import com.example.IdentityService.mapper.ChiTietGioHangMapper;
import com.example.IdentityService.repository.ChiTietGioHangRepository;
import com.example.IdentityService.repository.TonKhoRepository;
import com.example.IdentityService.repository.UserRepository;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Data
@Service
@Slf4j
public class ChiTietGioHangService {
    private final ChiTietGioHangRepository chiTietGioHangRepository;
    private final UserRepository userRepository;
    private final TonKhoRepository tonKhoRepository;
    private final ChiTietGioHangMapper chiTietGioHangMapper;
    
    @Autowired
    public ChiTietGioHangService(ChiTietGioHangRepository chiTietGioHangRepository
    ,UserRepository userRepository
    ,TonKhoRepository tonKhoRepository
    ,ChiTietGioHangMapper chiTietGioHangMapper){
        this.chiTietGioHangRepository = chiTietGioHangRepository;
        this.userRepository = userRepository;
        this.tonKhoRepository = tonKhoRepository;
        this.chiTietGioHangMapper = chiTietGioHangMapper;
    }

    public List<ChiTietGioHangResponse> findAllGioHangByID(String userId){
        User user = userRepository.findById(userId)
                .orElseThrow(()->new RuntimeException("User not found"));
        List<ChiTietGioHang> chiTietGioHangList = chiTietGioHangRepository.findAllByUser(user);
        return chiTietGioHangList.stream()
                .map(chiTietGioHangMapper::toChiTietGioHangResponse)
                .toList();
    }

    public ChiTietGioHangResponse createChiTietGioHang(ChiTietGioHangRequest chiTietGioHangRequest){
        User user = userRepository.findById(chiTietGioHangRequest.getUser())
                .orElseThrow(() -> new RuntimeException("User not found"));
        ChiTietGioHangId chiTietGioHangId = ChiTietGioHangId.builder()
                .idKH(user.getId())
                .maSP(chiTietGioHangRequest.getTonKho())
                .build();
        TonKho tonKho = tonKhoRepository.findById(chiTietGioHangRequest.getTonKho())
                .orElseThrow(() -> new RuntimeException("Product not found"));
        ChiTietGioHang chiTietGioHang = ChiTietGioHang.builder()
                .chiTietGioHangId(chiTietGioHangId)
                .user(user)
                .tonKho(tonKho)
                .soLuong(chiTietGioHangRequest.getSoLuong())
                .build();
        chiTietGioHangRepository.save(chiTietGioHang);
        return chiTietGioHangMapper.toChiTietGioHangResponse(chiTietGioHang);
    }

    public void plusChiTietGioHang(String userId, String maSP){
        User user = userRepository.findById(userId)
                .orElseThrow(()->new RuntimeException("User not found"));
        ChiTietGioHangId chiTietGioHangId = ChiTietGioHangId.builder()
                .idKH(user.getId())
                .maSP(maSP)
                .build();
        ChiTietGioHang chiTietGioHang = chiTietGioHangRepository.findById(chiTietGioHangId)
                .orElseThrow(() -> new RuntimeException("Cart item not found"));
        chiTietGioHang.setSoLuong(chiTietGioHang.getSoLuong()+1);
        chiTietGioHangRepository.save(chiTietGioHang);
    }

    public void minusChiTietGioHang(String userId, String maSP){
        User user = userRepository.findById(userId)
                .orElseThrow(()->new RuntimeException("User not found"));
        ChiTietGioHangId chiTietGioHangId = ChiTietGioHangId.builder()
                .idKH(user.getId())
                .maSP(maSP)
                .build();
        ChiTietGioHang chiTietGioHang = chiTietGioHangRepository.findById(chiTietGioHangId)
                .orElseThrow(() -> new RuntimeException("Cart item not found"));
        chiTietGioHang.setSoLuong(chiTietGioHang.getSoLuong()-1);
        chiTietGioHangRepository.save(chiTietGioHang);
    }

    public void deleteChiTietGioHang(String userId, String maSP){
        User user = userRepository.findById(userId)
                .orElseThrow(()->new RuntimeException("User not found"));
        ChiTietGioHangId chiTietGioHangId = ChiTietGioHangId.builder()
                .idKH(user.getId())
                .maSP(maSP)
                .build();
        chiTietGioHangRepository.deleteById(chiTietGioHangId);
    }

    @Transactional
    public void deleteByUser(String userId){
        User user = userRepository.findById(userId)
                .orElseThrow(()->new RuntimeException("User not found"));
        chiTietGioHangRepository.deleteAllByUser(user);
    }
}

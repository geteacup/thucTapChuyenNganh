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
        List<ChiTietGioHang> chiTietGioHangList = chiTietGioHangRepository.findAllByUser(userRepository.findById(userId)
                .orElseThrow(()->new RuntimeException("somethingwrong")));
        List<ChiTietGioHangResponse> ChiTietGioHangResponses =
                chiTietGioHangList.stream().map(chiTietGioHangMapper::toChiTietGioHangResponse).toList();
        return ChiTietGioHangResponses;
    }
    public ChiTietGioHangResponse createChiTietGioHang(ChiTietGioHangRequest chiTietGioHangRequest){
        ChiTietGioHangId chiTietGioHangId = ChiTietGioHangId.builder()
                .idKH(chiTietGioHangRequest.getUser())
                .maSP(chiTietGioHangRequest.getTonKho())
                .build();
        User user = userRepository.findById(chiTietGioHangRequest.getUser())
                .orElseThrow(() -> new RuntimeException("User not found"));
        TonKho tonKho = tonKhoRepository.findById(chiTietGioHangRequest.getTonKho())
                .orElseThrow(() -> new RuntimeException("not found"));
        ChiTietGioHang chiTietGioHang = ChiTietGioHang.builder()
                .chiTietGioHangId(chiTietGioHangId)
                .user(user)
                .tonKho(tonKho)
                .soLuong(chiTietGioHangRequest.getSoLuong())
                .build();
        chiTietGioHangRepository.save(chiTietGioHang);
        return chiTietGioHangMapper.toChiTietGioHangResponse(chiTietGioHang);
    }
    public void deleteChiTietGioHang(ChiTietGioHangId chiTietGioHangId){
        chiTietGioHangRepository.deleteById(chiTietGioHangId);
    }
}

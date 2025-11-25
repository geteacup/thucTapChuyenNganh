package com.example.IdentityService.service;

import com.example.IdentityService.dto.request.ChiTietHoaDonByGioHangRequest;
import com.example.IdentityService.dto.request.ChiTietHoaDonRequest;
import com.example.IdentityService.dto.response.ChiTietHoaDonResponse;
import com.example.IdentityService.entity.*;
import com.example.IdentityService.mapper.ChiTietHoaDonMapper;
import com.example.IdentityService.repository.*;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.hibernate.internal.util.collections.ArrayHelper.forEach;

@Data
@Service
@Slf4j
public class ChiTietHoaDonService {
    private final UserRepository userRepository;
    ChiTietHoaDonRepository chiTietHoaDonRepository;
    ChiTietHoaDonMapper chiTietHoaDonMapper;
    HoaDonRepository hoaDonRepository;
    TonKhoRepository tonKhoRepository;
    ChiTietGioHangRepository chiTietGioHangRepository;
    @Autowired
    public ChiTietHoaDonService(ChiTietHoaDonRepository chiTietHoaDonRepository
    , ChiTietHoaDonMapper chiTietHoaDonMapper
    , HoaDonRepository hoaDonRepository
    , TonKhoRepository tonKhoRepository, UserRepository userRepository
    ,ChiTietGioHangRepository chiTietGioHangRepository) {
        this.chiTietHoaDonRepository = chiTietHoaDonRepository;
        this.chiTietHoaDonMapper = chiTietHoaDonMapper;
        this.hoaDonRepository = hoaDonRepository;
        this.tonKhoRepository = tonKhoRepository;
        this.userRepository = userRepository;
        this.chiTietGioHangRepository = chiTietGioHangRepository;
    }

    public List<ChiTietHoaDonResponse> findAllHoaDonByID(String maHD){
        List<ChiTietHoaDon> chiTietHoaDonList = (List<ChiTietHoaDon>) chiTietHoaDonRepository.findAllByHoaDon(
                hoaDonRepository.findById(maHD)
                        .orElseThrow(()-> new RuntimeException("somethingwrong")));
        List<ChiTietHoaDonResponse> chiTietHoaDonResponseList =
                chiTietHoaDonList.stream().map(chiTietHoaDonMapper::toChiTietHoaDonResponse).toList();
        return chiTietHoaDonResponseList;
    }
    public int tongTienHoaDon(String maHD){
        HoaDon hoaDon = hoaDonRepository.findById(maHD)
                .orElseThrow(()-> new RuntimeException("somethingwrong"));
        List<ChiTietHoaDon> chiTietHoaDonList = chiTietHoaDonRepository.findAllByHoaDon(hoaDon);
        int tongTien = 0;
        for (ChiTietHoaDon chiTietHoaDon : chiTietHoaDonList)
        {
            TonKho tonKho = chiTietHoaDon.getTonKho();
            int soLuong = chiTietHoaDon.getSoLuong();
            tongTien += tonKho.getGiaSP()*soLuong;
        }
        return tongTien;
    }
    public String createChiTietHoaDonByGioHang(ChiTietHoaDonByGioHangRequest request){
        try {
            User user = userRepository.findById(request.getUser())
                    .orElseThrow(() -> new RuntimeException("sometime"));
            List<ChiTietGioHang> chiTietGioHangList = chiTietGioHangRepository.findAllByUser(user);
            for (ChiTietGioHang chiTietGioHang : chiTietGioHangList) {
                ChiTietHoaDonRequest chiTietHoaDonRequest = ChiTietHoaDonRequest.builder()
                        .hoaDon(request.getMaHD())
                        .tonKho(chiTietGioHang.getTonKho().getMaSP())
                        .soLuong(chiTietGioHang.getSoLuong())
                        .build();
                createChiTietHoaDon(chiTietHoaDonRequest);
            }
            return "Thanh Cong";
        }
        catch (Exception e){
            return e.getMessage();
        }
    }
    public ChiTietHoaDonResponse createChiTietHoaDon(ChiTietHoaDonRequest chiTietHoaDonRequest){
        ChiTietHoaDonId chiTietHoaDonId = ChiTietHoaDonId.builder()
                .maHD(chiTietHoaDonRequest.getHoaDon())
                .maSP(chiTietHoaDonRequest.getTonKho())
                .build();
        HoaDon hoaDon = hoaDonRepository.findById(chiTietHoaDonRequest.getHoaDon())
                .orElseThrow(()-> new RuntimeException("something went wrong"));
        TonKho tonKho = tonKhoRepository.findById(chiTietHoaDonRequest.getTonKho())
                .orElseThrow(()-> new RuntimeException("something went wrong"));
        tonKho.setSoLuongSP(tonKho.getSoLuongSP()-chiTietHoaDonRequest.getSoLuong());
        tonKhoRepository.save(tonKho);
        ChiTietHoaDon chiTietHoaDon = ChiTietHoaDon.builder()
                .chiTietHoaDonId(chiTietHoaDonId)
                .tonKho(tonKho)
                .hoaDon(hoaDon)
                .soLuong(chiTietHoaDonRequest.getSoLuong())
                .build();
        chiTietHoaDonRepository.save(chiTietHoaDon);
        return chiTietHoaDonMapper.toChiTietHoaDonResponse(chiTietHoaDon);
    }
    public void deleteChiTietHoaDon(ChiTietHoaDonId maHD){
        chiTietHoaDonRepository.deleteById(maHD);
    }
}

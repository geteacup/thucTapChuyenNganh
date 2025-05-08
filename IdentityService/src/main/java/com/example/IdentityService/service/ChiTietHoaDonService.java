package com.example.IdentityService.service;

import com.example.IdentityService.dto.request.ChiTietHoaDonRequest;
import com.example.IdentityService.dto.response.ChiTietHoaDonResponse;
import com.example.IdentityService.entity.ChiTietHoaDon;
import com.example.IdentityService.entity.ChiTietHoaDonId;
import com.example.IdentityService.entity.HoaDon;
import com.example.IdentityService.entity.TonKho;
import com.example.IdentityService.mapper.ChiTietHoaDonMapper;
import com.example.IdentityService.repository.ChiTietHoaDonRepository;
import com.example.IdentityService.repository.HoaDonRepository;
import com.example.IdentityService.repository.TonKhoRepository;
import com.example.IdentityService.repository.UserRepository;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Data
@Service
@Slf4j
public class ChiTietHoaDonService {
    ChiTietHoaDonRepository chiTietHoaDonRepository;
    ChiTietHoaDonMapper chiTietHoaDonMapper;
    HoaDonRepository hoaDonRepository;
    TonKhoRepository tonKhoRepository;

    @Autowired
    public ChiTietHoaDonService(ChiTietHoaDonRepository chiTietHoaDonRepository
    ,ChiTietHoaDonMapper chiTietHoaDonMapper
    ,HoaDonRepository hoaDonRepository
    ,TonKhoRepository tonKhoRepository) {
        this.chiTietHoaDonRepository = chiTietHoaDonRepository;
        this.chiTietHoaDonMapper = chiTietHoaDonMapper;
        this.hoaDonRepository = hoaDonRepository;
        this.tonKhoRepository = tonKhoRepository;
    }

    public List<ChiTietHoaDonResponse> findAllHoaDonByID(String maHD){
        List<ChiTietHoaDon> chiTietHoaDonList = (List<ChiTietHoaDon>) chiTietHoaDonRepository.findAllByHoaDon(
                hoaDonRepository.findById(maHD)
                        .orElseThrow(()-> new RuntimeException("somethingwrong")));
        List<ChiTietHoaDonResponse> chiTietHoaDonResponseList =
                chiTietHoaDonList.stream().map(chiTietHoaDonMapper::toChiTietHoaDonResponse).toList();
        return chiTietHoaDonResponseList;
    }
    public ChiTietHoaDonResponse createChiTietHoaDon(ChiTietHoaDonRequest chiTietHoaDonRequest){
        ChiTietHoaDonId chiTietHoaDonId = ChiTietHoaDonId.builder()
                .maHD(chiTietHoaDonRequest.getHoaDon())
                .maSP(chiTietHoaDonRequest.getTonKho())
                .build();
        HoaDon hoaDon = hoaDonRepository.findById(chiTietHoaDonRequest.getHoaDon())
                .orElseThrow(()-> new RuntimeException("something went wrong"));;
        TonKho tonKho = tonKhoRepository.findById(chiTietHoaDonRequest.getTonKho())
                .orElseThrow(()-> new RuntimeException("something went wrong"));
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

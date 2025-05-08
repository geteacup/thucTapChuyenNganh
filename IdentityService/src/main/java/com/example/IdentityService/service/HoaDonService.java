package com.example.IdentityService.service;

import com.example.IdentityService.dto.request.HoaDonRequest;
import com.example.IdentityService.dto.response.HoaDonResponse;
import com.example.IdentityService.entity.HoaDon;
import com.example.IdentityService.entity.User;
import com.example.IdentityService.mapper.HoaDonMapper;
import com.example.IdentityService.repository.HoaDonRepository;
import com.example.IdentityService.repository.UserRepository;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Data
@Service
@Slf4j
public class HoaDonService {
    private final HoaDonRepository hoaDonRepository;
    private final UserRepository userRepository;
    private final HoaDonMapper hoaDonMapper;

    @Autowired
    HoaDonService(HoaDonRepository HoaDonRepository
            , UserRepository userRepository
            , HoaDonMapper hoaDonMapper) {
        this.hoaDonRepository = HoaDonRepository;
        this.userRepository = userRepository;
        this.hoaDonMapper = hoaDonMapper;
    }

    public HoaDonResponse getHoaDonById(String maHD) {
        HoaDonResponse hoaDonResponse = hoaDonMapper.toHoaDonResponse(hoaDonRepository.findById(maHD)
                .orElseThrow(()-> new RuntimeException("something went wrong")));
        return hoaDonResponse;
    }
    public HoaDonResponse createHoaDon(HoaDonRequest hoaDonRequest) {
        HoaDon hoaDon = hoaDonMapper.toHoaDon(hoaDonRequest);
        if(userRepository.existsById(hoaDonRequest.getUserKH())){
            User user = userRepository.findById(hoaDonRequest.getUserKH()).orElse(null);
            hoaDon.setUserKH(user);
        }
        if(userRepository.existsById(hoaDonRequest.getUserNV())){
            User user = userRepository.findById(hoaDonRequest.getUserKH()).orElse(null);
            hoaDon.setUserNV(user);
        }
        hoaDonRepository.save(hoaDon);
        return hoaDonMapper.toHoaDonResponse(hoaDon);
    }
    public HoaDonResponse updateHoaDon(String maHD,HoaDonRequest hoaDonRequest){
        HoaDon hoaDon = hoaDonRepository.findById(maHD).orElse(null);
        hoaDon = hoaDonMapper.toHoaDon(hoaDonRequest);
        if(userRepository.existsById(hoaDonRequest.getUserKH())){
            User user = userRepository.findById(hoaDonRequest.getUserKH()).orElse(null);
            hoaDon.setUserKH(user);
        }
        if(userRepository.existsById(hoaDonRequest.getUserNV())){
            User user = userRepository.findById(hoaDonRequest.getUserKH()).orElse(null);
            hoaDon.setUserNV(user);
        }
        hoaDonRepository.save(hoaDon);
        return hoaDonMapper.toHoaDonResponse(hoaDon);
    }
    public void changeStatus(String maHD,HoaDonRequest hoaDonRequest){
        HoaDon hoaDon = hoaDonRepository.findById(maHD)
                .orElseThrow(()-> new RuntimeException("something went wrong"));
        hoaDon.setStatus("Hoan Thanh");
        hoaDonRepository.save(hoaDon);
    }
    public void deleteHoaDonById(String maHD) {
        hoaDonRepository.deleteById(maHD);
    }
}

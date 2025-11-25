package com.example.IdentityService.service;

import com.example.IdentityService.dto.request.HoaDonRequest;
import com.example.IdentityService.dto.request.TrangThaiHoaDonRequest;
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
    public List<HoaDonResponse> getAllHoaDonByUserKH(String userKH) {
        return  hoaDonRepository.findAllByUserKH(userRepository.findById(userKH)
                        .orElseThrow(()->new RuntimeException("Somethingwrong")))
                        .stream().map(hoaDonMapper::toHoaDonResponse).toList();
    }
    public List<HoaDonResponse> getAllHoaDonByUserNV(String userNV) {
        return  hoaDonRepository.findAllByUserNV(userRepository.findById(userNV)
                        .orElseThrow(()->new RuntimeException("Somethingwrong")))
                .stream().map(hoaDonMapper::toHoaDonResponse).toList();
    }
    public List<HoaDonResponse> getAllHoaDon(){
        return hoaDonRepository.findAll().stream().map(hoaDonMapper::toHoaDonResponse).toList();
    }
    public List<HoaDonResponse> getAllHoaDonByStatus(String status) {
        return  hoaDonRepository.findAllByStatus(status)
                .stream().map(hoaDonMapper::toHoaDonResponse).toList();
    }
    public List<HoaDonResponse> getAllHoaDonByUserAndStatus(String username,String status) {
        return  hoaDonRepository.findAllByUserNVAndStatus(userRepository.findByUsername(username)
                        .orElseThrow(()->new RuntimeException("Somethingwrong")),status)
                .stream().map(hoaDonMapper::toHoaDonResponse).toList();
    }
    public HoaDonResponse createHoaDon(HoaDonRequest hoaDonRequest) {
        HoaDon hoaDon = hoaDonMapper.toHoaDon(hoaDonRequest);

        if(hoaDonRequest.getUserKH() != null){
            if(userRepository.existsById(hoaDonRequest.getUserKH())){
                User user = userRepository.findById(hoaDonRequest.getUserKH()).orElse(null);
                hoaDon.setUserKH(user);
            }
        }
        hoaDonRepository.save(hoaDon);
        return hoaDonMapper.toHoaDonResponse(hoaDon);
    }
    public void changeStatus(TrangThaiHoaDonRequest trangThaiHoaDonRequest){
        HoaDon hoaDon = hoaDonRepository.findById(trangThaiHoaDonRequest.getMaHD())
                .orElseThrow(()-> new RuntimeException("something went wrong"));
        hoaDon.setStatus(trangThaiHoaDonRequest.getStatus());
        User user = userRepository.findById(trangThaiHoaDonRequest.getUserNV())
                .orElseThrow(()-> new RuntimeException("something went wrong"));
        hoaDon.setUserNV(user);
        hoaDonRepository.save(hoaDon);
    }
    public void deleteHoaDonById(String maHD) {
        hoaDonRepository.deleteById(maHD);
    }
}

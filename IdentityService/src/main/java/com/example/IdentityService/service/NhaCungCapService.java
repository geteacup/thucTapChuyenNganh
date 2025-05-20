package com.example.IdentityService.service;

import com.example.IdentityService.dto.request.NhaCungCapRequest;
import com.example.IdentityService.dto.response.NhaCungCapResponse;
import com.example.IdentityService.entity.NhaCungCap;
import com.example.IdentityService.mapper.NhaCungCapMapper;
import com.example.IdentityService.repository.NhaCungCapRepository;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Data
@Service
@Slf4j
public class NhaCungCapService {
    private final NhaCungCapRepository nhaCungCapRepository;
    private final NhaCungCapMapper nhaCungCapMapper;

    @Autowired
    NhaCungCapService (NhaCungCapRepository nhacungcapRepository
    , NhaCungCapMapper nhacungcapMapper) {
        this.nhaCungCapRepository = nhacungcapRepository;
        this.nhaCungCapMapper = nhacungcapMapper;
    }

    public List<NhaCungCapResponse> getAllNhaCungCap() {
        List<NhaCungCap> nhaCungCaps = nhaCungCapRepository.findAll();
        List<NhaCungCapResponse> nhaCungCapResponses = new ArrayList<>();
        nhaCungCapResponses=
                nhaCungCaps.stream().map(nhaCungCap -> nhaCungCapMapper.toNhaCungCapResponse(nhaCungCap)).toList();
        return nhaCungCapResponses;
    }
    public NhaCungCapResponse getNhaCungCapById(String id) {
        return nhaCungCapMapper.toNhaCungCapResponse(nhaCungCapRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("NhaCungCap not found")));
    }
    public NhaCungCapResponse createNhaCungCap(NhaCungCapRequest nhaCungCapRequest) {
        NhaCungCap nhaCungCap = nhaCungCapMapper.toNhaCungCap(nhaCungCapRequest);
        nhaCungCapRepository.save(nhaCungCap);
        return nhaCungCapMapper.toNhaCungCapResponse(nhaCungCap);
    }
    public NhaCungCapResponse updateNhaCungCap(String maNCC,NhaCungCapRequest nhaCungCapRequest) {
        NhaCungCap nhaCungCap = nhaCungCapRepository.findById(maNCC)
                .orElseThrow(()-> new RuntimeException("NhaCungCap not found"));
        nhaCungCapMapper.updateNhaCungCap(nhaCungCap,nhaCungCapRequest);
        nhaCungCapRepository.save(nhaCungCap);
        return nhaCungCapMapper.toNhaCungCapResponse(nhaCungCap);
    }
    public void deleteNhaCungCap (String maNCC){
        nhaCungCapRepository.deleteById(maNCC);
    }
}

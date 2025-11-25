package com.example.IdentityService.repository;

import com.example.IdentityService.entity.ChiTietHoaDon;
import com.example.IdentityService.entity.ChiTietHoaDonId;
import com.example.IdentityService.entity.HoaDon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChiTietHoaDonRepository extends JpaRepository<ChiTietHoaDon, ChiTietHoaDonId> {
    List<ChiTietHoaDon> findAllByHoaDon(HoaDon hoaDon);
}

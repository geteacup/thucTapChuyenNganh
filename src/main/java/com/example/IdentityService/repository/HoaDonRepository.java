package com.example.IdentityService.repository;

import com.example.IdentityService.entity.HoaDon;
import com.example.IdentityService.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HoaDonRepository extends JpaRepository<HoaDon, String> {
    List<HoaDon> findAllByUserNV(User user);
    List<HoaDon> findAllByUserKH(User user);
    List<HoaDon> findAllByUserNVAndStatus(User user, String status);
    List<HoaDon> findAllByStatus(String status);
}

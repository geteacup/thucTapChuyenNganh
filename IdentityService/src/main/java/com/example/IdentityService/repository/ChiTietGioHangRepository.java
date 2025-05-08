package com.example.IdentityService.repository;

import com.example.IdentityService.entity.ChiTietGioHang;
import com.example.IdentityService.entity.ChiTietGioHangId;
import com.example.IdentityService.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChiTietGioHangRepository extends JpaRepository<ChiTietGioHang, ChiTietGioHangId> {
    List<ChiTietGioHang> findAllByUser(User user);
}

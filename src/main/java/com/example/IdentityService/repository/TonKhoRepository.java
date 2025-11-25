package com.example.IdentityService.repository;

import com.example.IdentityService.entity.LoaiSanPham;
import com.example.IdentityService.entity.NhaCungCap;
import com.example.IdentityService.entity.TonKho;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TonKhoRepository extends JpaRepository<TonKho, String> {
    List<TonKho> findAllByLoaiSanPham(LoaiSanPham loaiSanPham);
    List<TonKho> findAllByNhaCungCap(NhaCungCap nhaCungCap);
}

package com.example.IdentityService.repository;

import com.example.IdentityService.entity.LoaiSanPham;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoaiSanPhamRepository extends JpaRepository<LoaiSanPham, String> {
}

package com.example.IdentityService.repository;

import com.example.IdentityService.entity.TonKho;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TonKhoRepository extends JpaRepository<TonKho, String> {
}

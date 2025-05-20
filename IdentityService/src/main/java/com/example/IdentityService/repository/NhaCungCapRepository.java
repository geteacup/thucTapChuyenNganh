package com.example.IdentityService.repository;

import com.example.IdentityService.dto.request.NhaCungCapRequest;
import com.example.IdentityService.entity.NhaCungCap;
import org.mapstruct.MappingTarget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NhaCungCapRepository extends JpaRepository<NhaCungCap, String> {

}

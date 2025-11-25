package com.example.IdentityService.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class NhaCungCap {
    @Id
    String maNCC;
    String tenNCC;
    String motaNCC;
}

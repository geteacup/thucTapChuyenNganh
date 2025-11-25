package com.example.IdentityService.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL) // để chuyển bỏ các phần null khi chuyển thành json
public class ApiResponse<T> {
    private int code = 1000;
    private String message;
    private T result;
}

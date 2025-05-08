package com.example.IdentityService.controller;

import com.example.IdentityService.dto.request.ApiResponse;
import com.example.IdentityService.dto.response.TonKhoResponse;
import com.example.IdentityService.service.TonKhoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tonkho")
@Slf4j
public class TonKhoRestController {
    TonKhoService tonKhoService;

    @Autowired
    public TonKhoRestController(TonKhoService tonKhoService) {
        this.tonKhoService = tonKhoService;
    }

    @GetMapping
    public ApiResponse<List<TonKhoResponse>> getTonKho() {
        return ApiResponse.<List<TonKhoResponse>>builder()
                .result(                tonKhoService.getALlTonKho()
)
                .build();

    }
}

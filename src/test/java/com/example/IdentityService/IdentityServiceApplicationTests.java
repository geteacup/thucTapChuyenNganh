package com.example.IdentityService;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import jakarta.xml.bind.DatatypeConverter;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class IdentityServiceApplicationTests {

    @Test
    void contextLoads() {}

    @Test
    void hash() throws NoSuchAlgorithmException {
        String password = "password";

        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(password.getBytes());

        byte[] digest = md.digest();

        String md5Hash = DatatypeConverter.printHexBinary(digest);

        System.out.println("MD5 round1: " + md5Hash);

        md.update(password.getBytes());

        byte[] digest2 = md.digest();

        String md5Hash2 = DatatypeConverter.printHexBinary(digest2);

        System.out.println("MD5 round2: " + md5Hash2);
    }
}

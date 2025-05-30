package com.medical.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;

public class JwtUtil {
    private static final String SECRET_KEY = "d96380b17ebe92bd934e073e708b74003461478c09360875c6d7bee1f08b6bee264a963233420c47400c2435315a8ed55317c968a97b6dcc63131644df3ad6e16e77b2ebd199b979b3eac9d96f61358d2cb734ff1c7d4999af8d93f26be2281bcf6499763bb2ecd06bcd31691a622db5818c85e8db55dbb231bf1e0f16c21a9f8532b3e0921f1d709abebc62689df6b80bbf5f429f9c16032ab0c400202e0db74b9b5683f5965605713eb8aa82642eb41b01e2762901476856f0f256c145bfd0fb0a6c96f79b256a1ecbae378ec5f9477c33a633a6b26b784e57299252c99922ed756bc261e8bb767155ff13086aaf43baf6b69e8f8297e716b0a6de5e3219e8"; // keep this secret and secure
    private static final long EXPIRATION_TIME = 86400000; // 1 day in milliseconds

    @SuppressWarnings("deprecation")
	public static String generateToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }
}

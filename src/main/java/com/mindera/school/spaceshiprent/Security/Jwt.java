/*
package com.mindera.school.spaceshiprent.Security;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.mindera.school.spaceshiprent.dto.auth.LoginDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class Jwt {

    @Value("${SECRET}")
    private String secret;


        public String generateJWT(LoginDto login){

         return JWT.create()
                    .withSubject("Login-User")
                    .withClaim("Email", login.getEmail())
                    .withClaim("Password", login.getPassword())
                    .withIssuedAt(new Date())
                    .sign(Algorithm.HMAC256(secret));
        }

    }

*/

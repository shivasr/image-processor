package com.app.ptc.imageprocessing.util;

import com.app.ptc.imageprocessing.exception.ApplicationException;
import com.app.ptc.imageprocessing.model.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Optional;

public class JWTUtil {

    /**
     *
     * @param header
     * @return
     */
    public static User authenticateAndRetrieveUserDetails(String header) throws ApplicationException {

        if (header == null || !header.startsWith("Bearer ")) {
            throw new ApplicationException("No JWT token found in request headers");
        }

        String authToken = header.substring(7);
        try {

            DecodedJWT jwt = JWT.decode(authToken);

            User user = User.builder()
                    .tenantId(jwt.getClaim("tid").toString())
                    .clientId(jwt.getClaim("oid").toString())
                    .email(jwt.getClaim("email").toString())
                    .build();
            Optional.ofNullable(user.getClientId()).orElseThrow( () -> new ArithmeticException("Client ID is missing"));
            Optional.ofNullable(user.getTenantId()).orElseThrow(() -> new ArithmeticException("Tenant ID is missing"));
            return user;


        } catch (JWTVerificationException exception){
            //Invalid signature/claims
            throw new ApplicationException("Invalid signature/claims");
        }
    }
}

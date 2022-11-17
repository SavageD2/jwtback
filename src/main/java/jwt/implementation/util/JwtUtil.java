package jwt.implementation.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import jwt.implementation.domain.appuser.AppUser;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;


import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.stream.Collectors;

public class JwtUtil {

    static final Algorithm JWT_ALGORITHM = Algorithm.HMAC256("jwt_secret".getBytes());


    public DecodedJWT verifyAndGetDecodedJWT(String authorizationHeader){

        String token = authorizationHeader.substring("Bearer ".length());
        JWTVerifier verifier = JWT.require(JWT_ALGORITHM).build();
        DecodedJWT decodedJwt = verifier.verify(token);
        return decodedJwt;

    }

    public JWTCreator.Builder jwtCommonBase(User user, HttpServletRequest request) {
        return JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + 60 * 60 * 1000))
                .withIssuer(request.getRequestURL().toString());
    }

    public JWTCreator.Builder jwtCommonBase(AppUser appUser, HttpServletRequest request) {
        return JWT.create()
                .withSubject(appUser.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + 60 * 60 * 1000))
                .withIssuer(request.getRequestURL().toString());
    }

    public String getAccessToken(User user, HttpServletRequest request) {

        return jwtCommonBase(user, request)
                .withClaim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(JWT_ALGORITHM);

    }
}

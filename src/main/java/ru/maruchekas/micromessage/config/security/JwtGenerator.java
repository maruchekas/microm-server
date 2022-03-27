package ru.maruchekas.micromessage.config.security;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import ru.maruchekas.micromessage.exception.AccessDeniedException;
import ru.maruchekas.micromessage.exception.JwtAuthenticationException;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import static ru.maruchekas.micromessage.config.Constants.JWT_TOKEN_EXPIRED;

@Component
public class JwtGenerator {
    @Value("${jwt.secret}")
    private String SECRET_KEY;

    public String generateToken(String login) {
        Date date = Date.from(LocalDate.now().plusDays(10).atStartOfDay(ZoneId.systemDefault()).toInstant());
        return Jwts.builder()
                .setSubject(login)
                .setExpiration(date)
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | SignatureException exception) {
            throw new JwtAuthenticationException(JWT_TOKEN_EXPIRED, HttpStatus.FORBIDDEN);
        }
    }

    public String getLoginFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}
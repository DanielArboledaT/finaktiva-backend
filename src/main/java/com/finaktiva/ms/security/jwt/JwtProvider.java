package com.finaktiva.ms.security.jwt;

import com.finaktiva.ms.entity.UsuarioPrincipal;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtProvider {

    private final static Logger logger = LoggerFactory.getLogger(JwtProvider.class);

    @Value("${finaktiva.app.jwtSecret}")
    private String secret;

    @Value("${finaktiva.app.jwtExpirationMs}")
    private int expiration;

    public String generateToken(Authentication auth) {

        UsuarioPrincipal usuarioPrincipal = (UsuarioPrincipal) auth.getPrincipal();
        return Jwts.builder().setSubject(usuarioPrincipal.getUsername()).setExpiration(new Date(new Date().getTime() + expiration))
                .signWith(SignatureAlgorithm.HS512, secret).compact();

    }

    public String getUsernameFromToken(String token) {

        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();

    }

    public boolean validateToken(String token) {

        try {

            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return true;

        } catch (MalformedJwtException e) {
            logger.error("Token malformado");
        } catch (UnsupportedJwtException e) {
            logger.error("Token no soportado");
        } catch (ExpiredJwtException e) {
            logger.error("Token expirado");
        } catch (IllegalArgumentException e) {
            logger.error("Token vacío");
        } catch (SignatureException e) {
            logger.error("Fallo con la firma");
        }

        return false;

    }

}

package com.example.quotivation.security;

import com.example.quotivation.entity.User;

import com.example.quotivation.exception.ExceptionMessages;
import com.example.quotivation.exception.response.InvalidTokenException;
import com.example.quotivation.exception.response.TokenExpiredException;
import com.example.quotivation.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtProvider {

    private final Key key;
    private final CustomUserDetailsService customUserDetailsService;

    public JwtProvider(@Value("${jwt.secret}") String key, CustomUserDetailsService customUserDetailsService) {
        byte[] keyBytes = Decoders.BASE64.decode(key);
        this.key = Keys.hmacShaKeyFor(keyBytes);
        this.customUserDetailsService = customUserDetailsService;
    }

    public String createAccessToken(User user) {
        Claims claims = Jwts.claims().setSubject(String.valueOf(user.getId()));

        claims.put("id", user.getId());
        claims.put("name", user.getName());

        Date now = new Date();
        Date expiredDate = new Date(now.getTime() + 30L * 24 * 60 * 60 * 1000);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiredDate)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    public Authentication getAuthentication(String token) {
        Long userId = Long.parseLong(Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getSubject());
        UserDetails userDetails = customUserDetailsService.loadUserById(userId);

        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public boolean verifyToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
            throw new TokenExpiredException(ExceptionMessages.TOKEN_EXPIRED, HttpStatus.UNAUTHORIZED);
        } catch (SignatureException e) {
            throw new InvalidTokenException(ExceptionMessages.INVALID_TOKEN, HttpStatus.UNAUTHORIZED);
        }
    }

    public String createAccessToken(Authentication authentication) {
        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();

        Long id = principal.getUser().getId();
        String name = principal.getUser().getName();

        Claims claims = Jwts.claims().setSubject(String.valueOf(id));

        claims.put("id", id);
        claims.put("name", name);

        Date now = new Date();
        Date expiredDate = new Date(now.getTime() + 30L * 24 * 60 * 60 * 1000);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiredDate)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    public String resolveToken(HttpServletRequest request) {
        String token = getToken(request);

        if(token != null && token.startsWith("Bearer "))
            return token.substring(7);
        else if(token != null)
            return token;

        return null;
    }

    private String getToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();

        if(cookies != null) {
            for(Cookie cookie : cookies) {
                if("access_token".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }

        return request.getHeader("Authorization");
    }
}

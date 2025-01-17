package com.sbsj.dreamwing.util;

import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Base64;
import java.util.Date;
import java.util.List;

/**
 * JWT Token Provider 클래스
 * @author 정은찬
 * @since 2024.07.30
 * @version 1.0
 *
 * <pre>
 * 수정일        수정자        수정내용
 * ----------  --------    ---------------------------
 * 2024.07.30   정은찬        최초 생성
 * </pre>
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.token-validity-in-seconds}")
    private long tokenValidMillisecond;

    private final UserDetailsService userDetailsService;
    private final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes()); // SecretKey Base64로 인코딩
    }

    /**
     * JWT 토큰 생성 메서드
     * @author 정은찬
     * @param userId
     * @param roles
     * @return String
     */
    public String createToken(Long userId, List<String> roles) {
        Claims claims = Jwts.claims().setSubject(Long.toString(userId));
        claims.put("roles", roles);
        Date now = new Date();

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + tokenValidMillisecond)) // 토큰 만료일 설정
                .signWith(SignatureAlgorithm.HS256, secretKey) // 암호화
                .compact();
    }

    /**
     * JWT 토큰에서 인증 정보 조회 메서드
     * @author 정은찬
     * @param token
     * @return
     */
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUserId(token));

        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    /**
     * 유저 이름 추출 메서드
     * @author 정은찬
     * @param token
     * @return String
     */
    public String getUserId(String token) {
        String userId = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
        return userId;
    }

    /**
     * 요청 헤더에서 JWT 토큰 추출 메서드
     * @author 정은찬
     * @param request
     * @return String
     */
    public String resolveToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");

        // 가져온 Authorization Header 가 문자열이고, Bearer 로 시작해야 가져옴
        if (StringUtils.hasText(token) && token.startsWith("Bearer ")) {
            return token.substring(7);
        }

        return null;
    }

    /**
     * JWT 토큰의 유효성 검사 메서드
     * @author 정은찬
     * @param token
     * @return boolean
     */
    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);

            return !claims.getBody().getExpiration().before(new Date());
        } catch (SecurityException | MalformedJwtException | IllegalArgumentException exception) {
            logger.info("잘못된 Jwt 토큰입니다");
        } catch (ExpiredJwtException exception) {
            logger.info("만료된 Jwt 토큰입니다");
        } catch (UnsupportedJwtException exception) {
            logger.info("지원하지 않는 Jwt 토큰입니다");
        }

        return false;
    }

    /**
     * JWT토큰 만료일 반환 메서드
     * @author 정은찬
     * @param token
     * @return Date
     */
    public Date getExpiration(String token) {
        Claims claims = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody();
        return claims.getExpiration();
    }

}

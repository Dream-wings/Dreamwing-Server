package com.sbsj.dreamwing.config.security;

import com.sbsj.dreamwing.util.JwtTokenProvider;
import jakarta.servlet.FilterChain;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;

/**
 * JWT 필터 클래스
 * @author 정은찬
 * @since 2024.07.31
 *
 * <pre>
 * 수정일        		수정자       				    수정내용
 * ----------    ----------------             ---------------------------------
 *  2024.07.31     	   정은찬        		       최초 생성 및 JWT 인증 기능 구현
 * </pre>
 */
@RequiredArgsConstructor
public class JwtFilter extends GenericFilterBean {

    private static final Logger logger = LoggerFactory.getLogger(JwtFilter.class);
    private final JwtTokenProvider jwtTokenProvider;
    private final RedisTemplate<String, String> redisTemplate;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        // 요청에서 JWT 토큰 추출
        String token = jwtTokenProvider.resolveToken((HttpServletRequest) servletRequest);
        // 요청에서 URI 추출
        String requestURI = ((HttpServletRequest) servletRequest).getRequestURI();

        // 토큰이 존재하고 유효한지 검증
        if (StringUtils.hasText(token) && jwtTokenProvider.validateToken(token)) {
            // Redis에서 토큰에 해당하는 사용자의 정보를 가져옴
            String key = "JWT_TOKEN:" + jwtTokenProvider.getUserId(token);
            String storedToken = redisTemplate.opsForValue().get(key);

            // 로그인 여부 체크: Redis에 해당 사용자의 토큰이 저장되어 있는지 확인
            if(redisTemplate.hasKey(key) && storedToken != null) {
                // 토큰에서 인증 정보를 가져와 Security Context에 설정
                Authentication authentication = jwtTokenProvider.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                logger.info("Security context에 인증 정보를 저장했습니다, uri: {}", requestURI);
            }
        } else {
            // 토큰이 유효하지 않으면 로그에 해당 사실을 기록
            logger.debug("유효한 Jwt 토큰이 없습니다, uri: {}", requestURI);
        }
        // 필터 체인에서 다음 필터를 실행
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
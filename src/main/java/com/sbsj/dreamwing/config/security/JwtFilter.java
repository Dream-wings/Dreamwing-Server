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

@RequiredArgsConstructor
public class JwtFilter extends GenericFilterBean {

    private static final Logger logger = LoggerFactory.getLogger(JwtFilter.class);
    private final JwtTokenProvider jwtTokenProvider;
    private final RedisTemplate<String, String> redisTemplate;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String token = jwtTokenProvider.resolveToken((HttpServletRequest) servletRequest);
        String requestURI = ((HttpServletRequest) servletRequest).getRequestURI();

        if (StringUtils.hasText(token) && jwtTokenProvider.validateToken(token)) {

            String key = "JWT_TOKEN:" + jwtTokenProvider.getUserId(token);
            String storedToken = redisTemplate.opsForValue().get(key);

            //**로그인 여부 체크**
            if(redisTemplate.hasKey(key) && storedToken != null) {
                Authentication authentication = jwtTokenProvider.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                logger.info("Security context에 인증 정보를 저장했습니다, uri: {}", requestURI);
            }
        } else {
            logger.debug("유효한 Jwt 토큰이 없습니다, uri: {}", requestURI);
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }
}
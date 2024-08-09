package com.sbsj.dreamwing.config.security;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.ServletException;
import java.io.IOException;

/**
 * CustomAuthenticationEntryPoint 클래스
 * @apiNote 인증되지 않은 사용자가 보호된 리소스에 접근하려고 할 때, 401 Unauthorized 응답을 반환하는 역할을 수행
 * @author 정은찬
 * @since 2024.07.31
 *
 * <pre>
 * 수정일        		수정자       				    수정내용
 * ----------  ----------------             ---------------------------------
 *  2024.07.31     	정은찬        		       최초 생성 및 인증 실패 처리 기능 구현
 * </pre>
 */
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        // 인증되지 않은 사용자에게 Unauthorized 상태 코드를 반환
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
    }
}

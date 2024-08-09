package com.sbsj.dreamwing.config.security;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * CustomAccessDeniedHandler 클래스
 * @apiNote 인증된 사용자가 권한이 없는 리소스에 접근하려고 할 때, Forbidden 응답을 반환하는 역할을 수행
 * @author 정은찬
 * @since 2024.07.31
 *
 * <pre>
 * 수정일        		수정자       				    수정내용
 * ----------  ----------------             ---------------------------------
 *  2024.07.31     	정은찬        		       최초 생성 및 접근 거부 처리 기능 구현
 * </pre>
 */
@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        // 권한이 없는 사용자에게 Forbidden 상태 코드를 반환
        response.sendError(HttpServletResponse.SC_FORBIDDEN);
    }
}

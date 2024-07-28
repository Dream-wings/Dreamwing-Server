package com.sbsj.dreamwing.support.service;


import com.sbsj.dreamwing.support.dto.GetTotalSupportResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * 후원 서비스 테스트
 * @author 정은지
 * @since 2024.07.28
 * @version 1.0
 *
 * <pre>
 * 수정일        	수정자        수정내용
 * ----------  --------    ---------------------------
 * 2024.07.28  	정은지        최초 생성
 * </pre>
 */
@Slf4j
@SpringBootTest
public class SupportServiceTests {

    @Autowired
    private SupportService service;

    @Test
    @DisplayName("총 후원 금액 및 횟수 조회 서비스 테스트")
    public void testGetTotalSupport() throws Exception {
        // given

        // when
        GetTotalSupportResponseDTO response = service.getTotalSupport();

        // then
        log.info(String.valueOf(response));
        Assertions.assertNotNull(response, "조회에 실패하였습니다.");
    }
}

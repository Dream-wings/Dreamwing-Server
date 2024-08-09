package com.sbsj.dreamwing.support.service;


import com.sbsj.dreamwing.support.dto.GetSupportListResponseDTO;
import com.sbsj.dreamwing.support.dto.GetTotalSupportResponseDTO;
import com.sbsj.dreamwing.support.dto.PostSupportGiveRequestDTO;
import com.sbsj.dreamwing.support.mapper.SupportMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
 * 2024.07.28   정은지        후원 총 횟수,금액 조회/후원 리스트 조회 테스트 추가
 * 2024.07.28   임재성        모든 후원 리스트 조회 테스트 추가
 * </pre>
 */
@Slf4j
@SpringBootTest
public class SupportServiceTests {

    @Autowired
    private SupportService service;
    private SupportMapper mapper; // 매퍼 주입

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

    @Test
    @DisplayName("후원 리스트 조회 서비스 테스트")
    public void testGetSupportList() throws Exception {
        // given

        // when
        List<GetSupportListResponseDTO> response = service.getSupportList();

        // then
        log.info(String.valueOf(response));
        Assertions.assertNotNull(response, "조회에 실패하였습니다.");
    }

//    @Test
//    @DisplayName("모든 후원 리스트 조회 서비스 테스트")
//    public void testGetAllSupportList() throws Exception {
//        // given
//
//        // when
//        List<GetSupportListResponseDTO> response = service.getAllSupportList();
//
//        // then
//        log.info(String.valueOf(response));
//        Assertions.assertNotNull(response, "조회에 실패하였습니다.");
//    }


    @Test
    @DisplayName("사용자 후원 기부 테스트")
    public void testSupportGivePoints() throws Exception {
        // given
        PostSupportGiveRequestDTO dto = new PostSupportGiveRequestDTO();
        dto.setUserId(1L);
        dto.setSupportId(6L);
        //dto.setPoint(200);

        // 기부 전 사용자와 후원 항목의 초기 포인트 가져오기
        int initialUserPoints = mapper.getUserPoints(dto.getUserId());
        int initialSupportPoints = mapper.getSupportPoints(dto.getSupportId());

        // when
        boolean result = service.SupportGivePoints(dto);

        // then
        Assertions.assertTrue(result, "후원 기부에 실패하였습니다.");

        // 기부 후 사용자와 후원 항목의 포인트 가져오기
        int updatedUserPoints = mapper.getUserPoints(dto.getUserId());
        int updatedSupportPoints = mapper.getSupportPoints(dto.getSupportId());

        log.info("Initial User Points: {}", initialUserPoints);
        log.info("Updated User Points: {}", updatedUserPoints);
        log.info("Initial Support Points: {}", initialSupportPoints);
        log.info("Updated Support Points: {}", updatedSupportPoints);

//        Assertions.assertEquals(initialUserPoints - dto.getPoint(), updatedUserPoints, "사용자의 포인트 업데이트에 실패하였습니다.");
//        Assertions.assertEquals(initialSupportPoints + dto.getPoint(), updatedSupportPoints, "후원 항목의 포인트 업데이트에 실패하였습니다.");
    }

    @Test
    @DisplayName("사용자 후원 기부 - 포인트 부족 테스트")
    @Transactional
    public void testSupportGivePoints_NotEnoughPoints() throws Exception {
        // given
        PostSupportGiveRequestDTO dto = new PostSupportGiveRequestDTO();
        dto.setUserId(1L);
        dto.setSupportId(6L);
        dto.setAmount(700); // 포인트 부족하게 설정

        // 기부 전 사용자와 후원 항목의 초기 포인트 가져오기
        int initialUserPoints = mapper.getUserPoints(dto.getUserId());
        int initialSupportPoints = mapper.getSupportPoints(dto.getSupportId());

        // when
        boolean result = service.SupportGivePoints(dto);

        // then
        Assertions.assertFalse(result, "포인트가 부족한 경우에도 후원 기부가 성공하였습니다.");

        // 포인트 부족으로 인해 업데이트가 발생하지 않아야 합니다.
        int updatedUserPoints = mapper.getUserPoints(dto.getUserId());
        int updatedSupportPoints = mapper.getSupportPoints(dto.getSupportId());

        log.info("Initial User Points: {}", initialUserPoints);
        log.info("Updated User Points: {}", updatedUserPoints);
        log.info("Initial Support Points: {}", initialSupportPoints);
        log.info("Updated Support Points: {}", updatedSupportPoints);

        Assertions.assertEquals(initialUserPoints, updatedUserPoints, "포인트가 부족한 경우 사용자의 포인트가 잘못 업데이트되었습니다.");
        Assertions.assertEquals(initialSupportPoints, updatedSupportPoints, "포인트가 부족한 경우 후원 항목의 포인트가 잘못 업데이트되었습니다.");
    }
}

    // 필요한 경우 추가적인 메서드를 여기에 작성하세요.}















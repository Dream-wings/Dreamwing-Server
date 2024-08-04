package com.sbsj.dreamwing.support.mapper;

import com.sbsj.dreamwing.support.dto.GetSupportListResponseDTO;
import com.sbsj.dreamwing.support.dto.GetTotalSupportResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 후원 매퍼 테스트 클래스
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
 * 2024.07.29   임재성        후원 하기 요청 테스트 추가
 * </pre>
 */

@Slf4j
@SpringBootTest
public class SupportMapperTests {

    @Autowired
    private SupportMapper mapper;

    @Test
    @DisplayName("총 후원 금액 및 횟수 조회 매퍼 테스트")
    public void testSelectTotalSupport() {

        // given

        // when
        GetTotalSupportResponseDTO dto = mapper.selectTotalSupport();

        // then
        log.info(dto.toString());
        Assertions.assertNotNull(dto, "조회에 실패하였습니다.");
    }

    @Test
    @DisplayName("후원 리스트 조회 매퍼 테스트")
    public void testSelectSupportList() {

        // given

        // when
        List<GetSupportListResponseDTO> dto = mapper.selectSupportList();

        // then
        log.info(dto.toString());
        Assertions.assertNotNull(dto, "조회에 실패하였습니다.");
    }

//    @Test
//    @DisplayName("모든 후원 리스트 조회 매퍼 테스트")
//    public void testSelectAllSupportList() {
//
//        // given
//
//        // when
//        List<GetSupportListResponseDTO> dto = mapper.selectAllSupportList();
//
//        // then
//        log.info(dto.toString());
//        Assertions.assertNotNull(dto, "조회에 실패하였습니다.");
//    }



    ////후원하기////

    @Test
    void testGetUserPoints() {
        long userId = 1L; // 존재하는 사용자 ID로 대체
        int point = mapper.getUserPoints(userId);
        log.info("User Points: {}", point);
        assertThat(point).isGreaterThanOrEqualTo(0);
    }

    @Test
    void testUpdateUserPoints() {
        long userId = 1L; // 존재하는 사용자 ID로 대체
        int newPoints = 500;
        int result = mapper.updateUserPoints(userId, newPoints);
        int updatedPoints = mapper.getUserPoints(userId);
        log.info("Updated User Points: {}", updatedPoints);
        assertThat(result).isEqualTo(1);
    }

    @Test
    void testUpdateSupportPoints() {
        long supportId = 6L; // 존재하는 지원 항목 ID로 대체
        int currentPoint = mapper.getSupportPoints(supportId);
        int pointsToAdd = 200;
        mapper.updateSupportPoints(supportId, pointsToAdd);
        int updatedPoints = mapper.getSupportPoints(supportId);
        log.info("Updated Support Points: {}", updatedPoints);
        assertThat(updatedPoints).isEqualTo(currentPoint + pointsToAdd);
    }



}

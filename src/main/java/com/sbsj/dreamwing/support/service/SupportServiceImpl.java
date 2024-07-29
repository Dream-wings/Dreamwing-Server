package com.sbsj.dreamwing.support.service;

import com.sbsj.dreamwing.support.dto.GetSupportListResponseDTO;
import com.sbsj.dreamwing.support.dto.GetTotalSupportResponseDTO;
import com.sbsj.dreamwing.support.dto.PostSupportGiveRequestDTO;
import com.sbsj.dreamwing.support.mapper.SupportMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 후원 서비스 구현체
 * @author 정은지
 * @since 2024.07.28
 * @version 1.0
 *
 * <pre>
 * 수정일        	수정자        수정내용
 * ----------  --------    ---------------------------
 * 2024.07.28  	정은지        최초 생성
 * 2024.07.28   정은지        후원 총 횟수,금액 조회/후원 리스트 조회 추가
 * 2024.07.28   임재성        모든 후원 리스트 조회 추가
 * </pre>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SupportServiceImpl implements SupportService {

    private final SupportMapper mapper;

    @Override
    public GetTotalSupportResponseDTO getTotalSupport() throws Exception {
        return mapper.selectTotalSupport();
    }

    @Override
    public List<GetSupportListResponseDTO> getSupportList() throws Exception {
        return mapper.selectSupportList();
    }

    @Override
    public List<GetSupportListResponseDTO> getAllSupportList() throws Exception {
        return mapper.selectAllSupportList();
    }

    @Transactional
    @Override
    public boolean SupportGivePoints(PostSupportGiveRequestDTO request) throws Exception {
        // 사용자의 보유 포인트를 가져와서 확인 (보유 포인트 확인 로직 필요)
        int userPoints = mapper.getUserPoints(request.getUserId());
        if (userPoints < request.getPoint()) {
            return false; // 포인트가 부족하면 false 반환
        }

        // 사용자의 포인트 차감
        mapper.updateUserPoints(request.getUserId(), request.getPoint());

        // 기부 내역 업데이트
        mapper.updateSupportPoints(request.getSupportId(), request.getPoint());

        return true;
    }


}
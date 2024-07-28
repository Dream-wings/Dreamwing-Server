package com.sbsj.dreamwing.support.service;

import com.sbsj.dreamwing.support.dto.GetAllSupportListResponseDTO;
import com.sbsj.dreamwing.support.dto.GetSupportListResponseDTO;
import com.sbsj.dreamwing.support.dto.GetTotalSupportResponseDTO;
import com.sbsj.dreamwing.support.mapper.SupportMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
 * 2024.07.28   정은지        후원 총 횟수,금액 조회/후원 리스트 조회 추가\
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
    public List<GetAllSupportListResponseDTO> getAllSupportList() throws Exception {
        return mapper.selectAllSupportList();
    }
}

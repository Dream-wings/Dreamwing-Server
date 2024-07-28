package com.sbsj.dreamwing.support.service;


import com.sbsj.dreamwing.support.dto.GetSupportListResponseDTO;
import com.sbsj.dreamwing.support.dto.GetTotalSupportResponseDTO;

import java.util.List;

/**
 * 후원 서비스 인터페이스
 * @author 정은지
 * @since 2024.07.28
 * @version 1.0
 *
 * <pre>
 * 수정일        	수정자        수정내용
 * ----------  --------    ---------------------------
 * 2024.07.28   정은지        최초 생성
 * 2024.07.28   정은지        후원 총 횟수,금액 조회/후원 리스트 조회 추가
 * 2024.07.28   임재성        모든 후원 리스트 조회 추가
 * </pre>
 */
public interface SupportService {
    GetTotalSupportResponseDTO getTotalSupport() throws Exception;
    List<GetSupportListResponseDTO> getSupportList() throws Exception;
    List<GetSupportListResponseDTO> getAllSupportList() throws Exception;
}

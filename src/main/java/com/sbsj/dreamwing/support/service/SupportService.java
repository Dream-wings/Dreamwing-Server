package com.sbsj.dreamwing.support.service;


import com.sbsj.dreamwing.support.dto.GetDetailSupportResponseDTO;
import com.sbsj.dreamwing.support.dto.GetSupportListResponseDTO;
import com.sbsj.dreamwing.support.dto.GetTotalSupportResponseDTO;
import com.sbsj.dreamwing.support.dto.PostSupportGiveRequestDTO;

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
 * 2024.07.28   정은지        후원 총 횟수, 금액 조회/후원 리스트 조회 추가
 * 2024.07.29   임재성        모든 후원 리스트 조회 추가(페이징,무한스크롤)
 * 2024.07.29   임재성        포인트 후원하기 기능 추가
 * 2024.07.29   임재성        후원 상세페이지 조회 기능 추가
 * </pre>
 */
public interface SupportService {

    /**
     * 후원 총 횟수, 금액 조회
     * @author 정은지
     * @return GetTotalSupportResponseDTO
     * @throws Exception
     */
    GetTotalSupportResponseDTO getTotalSupport() throws Exception;

    /**
     * 후원 리스트 조회
     * @author 정은지
     * @return List<GetSupportListResponseDTO>
     * @throws Exception
     */
    List<GetSupportListResponseDTO> getSupportList() throws Exception;


    /**
     * 후원 페이지 내 후원 리스트 조회(페이징, 무한스크롤 처리)
     * @author 임재성
     * @param page
     * @param size
     * @param status
     * @param category
     * @return List
     */
    List<GetSupportListResponseDTO> getSupportListWithFilters(int page, int size, int status, int category);

    /**
     * 후원 하기
     * @author 임재성
     * @param request
     * @return boolean
     * @throws Exception
     */
    boolean SupportGivePoints(PostSupportGiveRequestDTO request) throws Exception;

    /**
     * 후원 상세페이지 조회
     * @author 임재성
     * @param supportId
     * @return GetDetailSupportResponseDTO
     * @throws Exception
     */
    GetDetailSupportResponseDTO getSupportDetail(long supportId) throws Exception;

}

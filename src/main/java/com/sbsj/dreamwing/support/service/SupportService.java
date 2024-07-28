package com.sbsj.dreamwing.support.service;


import com.sbsj.dreamwing.support.dto.GetTotalSupportResponseDTO;

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
 * </pre>
 */
public interface SupportService {
    GetTotalSupportResponseDTO getTotalSupport() throws Exception;
}

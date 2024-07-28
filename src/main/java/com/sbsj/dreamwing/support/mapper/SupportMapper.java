package com.sbsj.dreamwing.support.mapper;

import com.sbsj.dreamwing.support.dto.GetSupportListResponseDTO;
import com.sbsj.dreamwing.support.dto.GetTotalSupportResponseDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 후원 매퍼 인터페이스
 * @author 정은지
 * @since 2024.07.26
 * @version 1.0
 *
 * <pre>
 * 수정일        	수정자        수정내용
 * ----------  --------    ---------------------------
 * 2024.07.28  	정은지        최초 생성
 * 2024.07.28   정은지        후원 총 횟수,금액 조회/후원 리스트 조회 추가
 * </pre>
 */
@Mapper
public interface SupportMapper {
    public GetTotalSupportResponseDTO selectTotalSupport();
    public List<GetSupportListResponseDTO> selectSupportList();
}
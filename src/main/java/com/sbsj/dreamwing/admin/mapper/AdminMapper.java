package com.sbsj.dreamwing.admin.mapper;

import com.sbsj.dreamwing.admin.dto.UpdateVolunteerStatusRequestDTO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 관리자 매퍼 인터페이스
 * @author 정은지
 * @since 2024.07.28
 * @version 1.0
 *
 * <pre>
 * 수정일        	수정자        수정내용
 * ----------  --------    ---------------------------
 * 2024.07.28  	정은지        최초 생성
 * 2024.07.28   정은지        봉사활동 승인 기능 추가
 * </pre>
 */
@Mapper
public interface AdminMapper {
    public int updateVolunteerStatus(UpdateVolunteerStatusRequestDTO request);
}
package com.sbsj.dreamwing.volunteer.mapper;


import com.sbsj.dreamwing.volunteer.dto.PostApplyVolunteerRequestDTO;
import com.sbsj.dreamwing.volunteer.dto.VolunteerDetailDTO;
import com.sbsj.dreamwing.volunteer.dto.VolunteerListDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * 봉사 매퍼 인터페이스
 * @author 임재성
 * @since 2024.07.26
 * @version 1.0
 *
 * <pre>
 * 수정일        	수정자        수정내용
 * ----------  --------    ---------------------------
 * 2024.07.26  	임재성        최초 생성
 * 2024.07.28   임재성        봉사 모집공고 리스트 & 상세페이지 조회 메서드 추가
 * </pre>
 */

@Mapper
public interface VolunteerMapper {

    List<VolunteerListDTO> getVolunteerList();

    VolunteerDetailDTO getVolunteerDetail(long volunteerId);

    int insertVolunteerApplication(PostApplyVolunteerRequestDTO request);

    int checkIfAlreadyApplied(PostApplyVolunteerRequestDTO request);

    int deleteVolunteerApplication(PostApplyVolunteerRequestDTO request);
}



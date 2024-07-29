package com.sbsj.dreamwing.volunteer.service;


import com.sbsj.dreamwing.volunteer.dto.PostApplyVolunteerRequestDTO;
import com.sbsj.dreamwing.volunteer.dto.VolunteerListDTO;
import com.sbsj.dreamwing.volunteer.mapper.VolunteerMapper;
import com.sbsj.dreamwing.volunteer.dto.VolunteerDetailDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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


@Service
public class VolunteerServiceImpl implements VolunteerService{

    @Autowired
    private VolunteerMapper volunteerMapper;

    @Override
    public List<VolunteerListDTO> getVolunteerList() throws Exception {
        return volunteerMapper.getVolunteerList();
    }

    @Override
    public VolunteerDetailDTO getVolunteerDetail(long volunteerId) throws Exception{
        return volunteerMapper.getVolunteerDetail(volunteerId);
    }

    @Override
    @Transactional
    public boolean applyVolunteer(PostApplyVolunteerRequestDTO request) {
        // 이미 신청했는지 확인
        int count = volunteerMapper.checkIfAlreadyApplied(request);

        // 신청이 이미 되어있는 경우
        if (count > 0) {
            throw new IllegalStateException("이미 신청한 봉사입니다.");
        }

        // 봉사 신청
        int result = volunteerMapper.insertVolunteerApplication(request);
        return result > 0;
    }

}



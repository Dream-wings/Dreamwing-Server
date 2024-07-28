package com.sbsj.dreamwing.volunteer.mapper;


import com.sbsj.dreamwing.volunteer.dto.VolunteerDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface VolunteerDAO {

    VolunteerDTO getVolunteerList(long volunteerId);

    VolunteerDTO getVolunteerDetail(long volunteerId);
}

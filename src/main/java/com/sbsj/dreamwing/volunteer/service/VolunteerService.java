package com.sbsj.dreamwing.volunteer.service;


import com.sbsj.dreamwing.volunteer.dto.VolunteerDTO;

public interface VolunteerService {
    VolunteerDTO getVolunteerList(long volunteerId) throws Exception;
    VolunteerDTO getVolunteerDetail(long volunteerId) throws Exception;
}

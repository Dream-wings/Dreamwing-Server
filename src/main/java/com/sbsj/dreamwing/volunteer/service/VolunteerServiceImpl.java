package com.sbsj.dreamwing.volunteer.service;


import com.sbsj.dreamwing.volunteer.mapper.VolunteerDAO;
import com.sbsj.dreamwing.volunteer.dto.VolunteerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VolunteerServiceImpl implements VolunteerService{
    @Autowired
    private VolunteerDAO volunteerDAO;

    @Override
    public VolunteerDTO getVolunteerList(long volunteerId) throws Exception {
        return volunteerDAO.getVolunteerList(volunteerId);
    }
    @Override
    public VolunteerDTO getVolunteerDetail(long volunteerId) throws Exception{
        return volunteerDAO.getVolunteerDetail(volunteerId);
    }
}



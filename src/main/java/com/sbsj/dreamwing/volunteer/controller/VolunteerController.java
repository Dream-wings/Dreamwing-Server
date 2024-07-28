package com.sbsj.dreamwing.volunteer.controller;

import com.sbsj.dreamwing.volunteer.dto.VolunteerDTO;
import com.sbsj.dreamwing.volunteer.service.VolunteerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/volunteer")
public class VolunteerController {

    @Autowired
    private VolunteerService volunteerService;

    @GetMapping("/list")
    public VolunteerDTO getVolunteerList(@RequestParam long volunteerId) throws Exception {
        // long 타입은 기본 타입이므로 null 체크가 필요 없음
        // 따라서 null 체크를 제거했습니다.

        // Service 호출 시 인스턴스를 통해 호출
        VolunteerDTO volunteerDTO = volunteerService.getVolunteerList(volunteerId);

        return volunteerDTO;
    }

    @GetMapping("/detail")
    public VolunteerDTO getVolunteerDetail(@RequestParam long volunteerId) throws Exception {
        // long 타입은 기본 타입이므로 null 체크가 필요 없음
        // 따라서 null 체크를 제거했습니다.

        // Service 호출 시 인스턴스를 통해 호출
        VolunteerDTO volunteerDTO = volunteerService.getVolunteerList(volunteerId);

        return volunteerDTO;
    }


}
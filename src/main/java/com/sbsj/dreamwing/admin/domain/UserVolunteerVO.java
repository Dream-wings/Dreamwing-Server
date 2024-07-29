package com.sbsj.dreamwing.admin.domain;

import lombok.Data;

/**
 * 사용자 봉사활동 VO
 * @author 정은지
 * @since 2024.07.29
 * @version 1.0
 *
 * <pre>
 * 수정일        	수정자        수정내용
 * ----------  --------    ---------------------------
 * 2024.07.29  	정은지        최초 생성
 * </pre>
 */
@Data
public class UserVolunteerVO {
    private long volunteerId;
    private long userId;
    private int status;
    private int verified;
    private String imageUrl;
}
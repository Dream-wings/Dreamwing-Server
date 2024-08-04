package com.sbsj.dreamwing.user.domain;

import lombok.Data;

/**
 * 사용자 봉사 내역 정보를 담는 VO
 * @author 정은찬
 * @since 2024.08.03
 *
 * <pre>
 * 수정일             수정자                      수정내용
 * ----------  ----------------    ---------------------------------
 * 2024.08.03       정은찬                     최초 생성
 * </pre>
 */
@Data
public class MyVolunteerVO {
    private String title;
    private String volunteerEndDate;
    private int verified;
}

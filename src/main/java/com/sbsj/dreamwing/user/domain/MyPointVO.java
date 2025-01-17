package com.sbsj.dreamwing.user.domain;

import lombok.Data;

/**
 * 회원 포인트 내역 정보를 담는 VO
 * @author 정은찬
 * @since 2024.07.31
 *
 * <pre>
 * 수정일             수정자                      수정내용
 * ----------  ----------------    ---------------------------------
 * 2024.07.31       정은찬                     최초 생성
 * </pre>
 */
@Data
public class MyPointVO {
    String ActivityTitle;
    int point;
    String createdDate;
}

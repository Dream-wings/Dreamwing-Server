package com.sbsj.dreamwing.user.domain;

import java.sql.Timestamp;
import lombok.Data;
import lombok.Builder;

/**
 * 사용자 정보를 담는 VO
 * @author 정은찬
 * @since 2024.07.28
 *
 * <pre>
 * 수정일             수정자                      수정내용
 * ----------  ----------------    ---------------------------------
 * 2024.07.28       정은찬                     최초 생성
 * </pre>
 */
@Data
@Builder
public class UserVO {
    long userId;
    String loginId;
    String password;
    String name;
    String phone;
    int totalPoint;
    int withdraw;
    String profileImageUrl;
    Timestamp createdDate;
}

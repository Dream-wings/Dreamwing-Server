package com.sbsj.dreamwing.user.domain;

import java.sql.Timestamp;
import lombok.Data;
import lombok.Builder;

@Data
@Builder
public class UserVO {
    int userId;
    String loginId;
    String password;
    String name;
    String phone;
    int totalPoint;
    int withdraw;
    String profileImageUrl;
    Timestamp createdDate;
}

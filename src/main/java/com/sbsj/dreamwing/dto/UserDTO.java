package com.sbsj.dreamwing.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserDTO {
    private int user_id;
    private String login_id;
    private String password;
    private String name;
    private String phone;
    private int total_point;
    private int withdraw;

}

package com.sbsj.dreamwing.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserDTO {
    private int userId;
    private String loginId;
    private String password;
    private String name;
    private String phone;
    private String point;
    private String withdraw;

}

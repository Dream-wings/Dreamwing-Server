package com.sbsj.dreamwing.dao;

import com.sbsj.dreamwing.dto.UserDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDAO {
//    @Select("SELECT * FROM user_info WHERE userId = #{userId}")
    UserDTO getUserById(int userId);
}
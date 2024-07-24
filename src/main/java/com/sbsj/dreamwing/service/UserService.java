package com.sbsj.dreamwing.service;

import com.sbsj.dreamwing.dto.UserDTO;

public interface UserService {
    UserDTO getUserById(int userId) throws Exception;
}
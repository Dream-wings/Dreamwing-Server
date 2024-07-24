package com.sbsj.dreamwing.service;

import com.sbsj.dreamwing.dao.UserDAO;
import com.sbsj.dreamwing.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDAO userDAO;

    @Override
    public UserDTO getUserById(int userId) throws Exception {
        return userDAO.getUserById(userId);
    }
}
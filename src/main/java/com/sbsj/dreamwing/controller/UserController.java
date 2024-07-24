package com.sbsj.dreamwing.controller;

import com.sbsj.dreamwing.dto.UserDTO;
import com.sbsj.dreamwing.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/test")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/user")
    public UserDTO getUserById(@RequestParam Integer userId) throws Exception {
        if (userId == null) {
            throw new IllegalArgumentException("User ID must be provided");
        }
        UserDTO userDTO = userService.getUserById(userId);

        return userDTO;
    }
}
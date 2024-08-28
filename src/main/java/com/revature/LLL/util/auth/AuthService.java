package com.revature.LLL.util.auth;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.LLL.User.User;
import com.revature.LLL.User.UserService;

@RestController
@RequestMapping("/auth")
public class AuthService {

    private final UserService userService;

    @Autowired
    public AuthService(UserService userService) {
        this.userService = userService;
    }

    public User login(String email, String password) throws AuthenticationException {
        return userService.findByEmailAndPassword(email, password);
    }

}

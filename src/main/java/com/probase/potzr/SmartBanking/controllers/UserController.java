package com.probase.potzr.SmartBanking.controllers;


import com.probase.potzr.SmartBanking.models.requests.CreateUserRequest;
import com.probase.potzr.SmartBanking.models.requests.LoginRequest;
import com.probase.potzr.SmartBanking.models.responses.user.CreateUserResponse;
import com.probase.potzr.SmartBanking.models.responses.user.LoginResponse;
import com.probase.potzr.SmartBanking.service.AuthService;
import com.probase.potzr.SmartBanking.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private AuthService authService;

    @RequestMapping(value = "/create-new-user", method = RequestMethod.POST)
    public ResponseEntity createNewUser(@RequestBody CreateUserRequest createUserRequest)
    {
        CreateUserResponse createUserResponse = userService.createNewUser(createUserRequest);
        return ResponseEntity.ok().body(createUserResponse);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity doLogin(@RequestBody LoginRequest loginRequest)
    {
        ResponseEntity loginResponse = authService.doLogin(loginRequest);
        return loginResponse;
    }
}

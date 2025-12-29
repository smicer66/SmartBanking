package com.probase.potzr.SmartBanking.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.probase.potzr.SmartBanking.models.core.User;
import com.probase.potzr.SmartBanking.models.enums.TokenType;
import com.probase.potzr.SmartBanking.models.enums.UserStatus;
import com.probase.potzr.SmartBanking.models.requests.CreateUserRequest;
import com.probase.potzr.SmartBanking.models.requests.LoginRequest;
import com.probase.potzr.SmartBanking.models.responses.user.CreateUserResponse;
import com.probase.potzr.SmartBanking.models.responses.user.LoginResponse;
import com.probase.potzr.SmartBanking.providers.TokenProvider;
import com.probase.potzr.SmartBanking.repositories.core.ITokenRepository;
import com.probase.potzr.SmartBanking.repositories.core.IUserRepository;
import com.probase.potzr.SmartBanking.models.core.Token;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderNotFoundException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserService {

    @Autowired
    IUserRepository userRepository;

    @Autowired
    ITokenRepository tokenRepository;

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Value("${user.account.token.valid.period}")
    private int userAccountTokenValidPeriod;

    public CreateUserResponse createNewUser(CreateUserRequest createUserRequest) {
        String encodedPassword = bCryptPasswordEncoder.encode(createUserRequest.getPassword());

        User user = new User();
        user.setUsername(createUserRequest.getMobileNumber());
        user.setEmailAddress(createUserRequest.getEmailAddress());
        user.setMobileNumber(createUserRequest.getMobileNumber());
        user.setPassword(encodedPassword);
        user.setUserStatus(UserStatus.INACTIVE);
        user = (User)userRepository.save(user);


        Token token  = new Token();
        token.setTokenOwnedByUserId(user.getUserId());
        token.setToken(RandomStringUtils.randomNumeric(6));
        token.setExpiredAt(LocalDateTime.now().plusHours(userAccountTokenValidPeriod));
        token.setTokenType(TokenType.SIGNUP);
        tokenRepository.save(token);

        CreateUserResponse createUserResponse = new CreateUserResponse();
        createUserResponse.setMobileNumber(user.getMobileNumber());
        createUserResponse.setUsername(user.getUsername());

        return createUserResponse;
    }



    public User getUserByUsername(String username) {
        User user = userRepository.getUserByUsername(username);
        return user;
    }
}

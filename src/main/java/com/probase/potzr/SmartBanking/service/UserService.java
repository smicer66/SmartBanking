package com.probase.potzr.SmartBanking.service;


import com.probase.potzr.SmartBanking.models.core.User;
import com.probase.potzr.SmartBanking.models.requests.CreateUserRequest;
import com.probase.potzr.SmartBanking.models.responses.user.CreateUserResponse;
import com.probase.potzr.SmartBanking.repositories.core.ITokenRepository;
import com.probase.potzr.SmartBanking.repositories.core.IUserRepository;
import com.probase.potzr.SmartBanking.models.core.Token;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserService {

    @Autowired
    IUserRepository userRepository;

    @Autowired
    ITokenRepository tokenRepository;

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
        user = (User)userRepository.save(user);


        Token token  = new Token();
        token.setTokenOwnedByUserId(user.getUserId());
        token.setToken(RandomStringUtils.randomNumeric(6));
        token.setExpiredAt(LocalDateTime.now().plusHours(userAccountTokenValidPeriod));
        tokenRepository.save(token);

        CreateUserResponse createUserResponse = new CreateUserResponse();
        createUserResponse.setMobileNumber(user.getMobileNumber());
        createUserResponse.setUsername(user.getUsername());

        return createUserResponse;
    }
}

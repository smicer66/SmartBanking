package com.probase.potzr.SmartBanking.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.probase.potzr.SmartBanking.models.core.Customer;
import com.probase.potzr.SmartBanking.models.core.IdentificationDocument;
import com.probase.potzr.SmartBanking.models.core.User;
import com.probase.potzr.SmartBanking.models.enums.IdentificationDocumentType;
import com.probase.potzr.SmartBanking.models.enums.TokenType;
import com.probase.potzr.SmartBanking.models.enums.UserStatus;
import com.probase.potzr.SmartBanking.models.requests.CreateUserRequest;
import com.probase.potzr.SmartBanking.models.requests.CustomerIdentificationRequest;
import com.probase.potzr.SmartBanking.models.requests.LoginRequest;
import com.probase.potzr.SmartBanking.models.responses.CustomerIdentificationResponse;
import com.probase.potzr.SmartBanking.models.responses.user.CreateUserResponse;
import com.probase.potzr.SmartBanking.models.responses.user.LoginResponse;
import com.probase.potzr.SmartBanking.providers.TokenProvider;
import com.probase.potzr.SmartBanking.repositories.core.ICustomerRepository;
import com.probase.potzr.SmartBanking.repositories.core.IIdentificationDocumentRepository;
import com.probase.potzr.SmartBanking.repositories.core.ITokenRepository;
import com.probase.potzr.SmartBanking.repositories.core.IUserRepository;
import com.probase.potzr.SmartBanking.models.core.Token;
import com.probase.potzr.SmartBanking.util.UtilityHelper;
import jakarta.servlet.http.HttpServletRequest;
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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;

@Service
public class UserService {

    @Autowired
    private TokenService tokenService;

    @Autowired
    IUserRepository userRepository;

    @Autowired
    ITokenRepository tokenRepository;

    @Autowired
    ICustomerRepository customerRepository;

    @Autowired
    IIdentificationDocumentRepository identificationDocumentRepository;

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private HttpServletRequest request;

    @Value("${user.account.token.valid.period}")
    private int userAccountTokenValidPeriod;

    @Value("${path.uploads.user.profile.picture}")
    private String fileDestinationPath;



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

    public CustomerIdentificationResponse updateCustomerIdentification(CustomerIdentificationRequest customerIdentificationRequest) throws JsonProcessingException {
        User user = tokenService.getUserFromToken(request);
        MultipartFile profilePicture = customerIdentificationRequest.getIdentificationDocument();
        if(!profilePicture.isEmpty()) {
            String newFileName = null;
            try {

                Customer customer = this.customerRepository.getCustomerByUserId(user.getUserId());

                newFileName = UtilityHelper.uploadFile(profilePicture, fileDestinationPath);
                IdentificationDocument identificationDocument = new IdentificationDocument();
                identificationDocument.setIdentificationNumber(customerIdentificationRequest.getIdentificationNumber());
                identificationDocument.setIdentificationDocumentType(IdentificationDocumentType.valueOf(customerIdentificationRequest.getIdentificationDocumentType()));
                identificationDocument.setExpirationDate(customerIdentificationRequest.getExpirationDate());
                identificationDocument.setIssueDate(customerIdentificationRequest.getIssueDate());
                identificationDocument.setIssuingAuthority(customerIdentificationRequest.getIssuingAuthority());
                identificationDocument.setPlaceOfIssue(customerIdentificationRequest.getPlaceOfIssue());

                identificationDocument = (IdentificationDocument) identificationDocumentRepository.save(identificationDocument);

                customer.setIdentificationDocumentId(identificationDocument.getIdentificationDocumentId());
                this.customerRepository.save(customer);

                CustomerIdentificationResponse customerIdentificationResponse = new CustomerIdentificationResponse();
                customerIdentificationResponse.setStatusCode("0");
                customerIdentificationResponse.setMessage("Identification document updated successfully");

                return customerIdentificationResponse;
            } catch (IOException e) {
                e.printStackTrace();
                CustomerIdentificationResponse customerIdentificationResponse = new CustomerIdentificationResponse();
                customerIdentificationResponse.setStatusCode("1");
                customerIdentificationResponse.setMessage("Identification document could not be updated. Resource denial error");

                return customerIdentificationResponse;
            }
        }


        CustomerIdentificationResponse customerIdentificationResponse = new  CustomerIdentificationResponse();
        customerIdentificationResponse.setStatusCode("1");
        customerIdentificationResponse.setMessage("Identification document uploaded is invalid. Please provide a valid profile picture");
        return customerIdentificationResponse;
    }
}

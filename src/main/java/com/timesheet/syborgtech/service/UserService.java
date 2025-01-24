package com.timesheet.syborgtech.service;


import com.timesheet.syborgtech.dto.request.UserRegistrationRequest;
import com.timesheet.syborgtech.dto.response.Response;
import com.timesheet.syborgtech.exceptions.EmailAlreadyExists;
import com.timesheet.syborgtech.model.User;
import com.timesheet.syborgtech.repository.RoleRepository;
import com.timesheet.syborgtech.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;
    public Response createUser(UserRegistrationRequest userRegistrationRequest) {

        if (userRepository.findByEmail(userRegistrationRequest.getEmail()).isPresent()) {
            throw new EmailAlreadyExists("Email already exists");
        }

        if (userRepository.findByUserName(userRegistrationRequest.getUserName()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        User user = new User();
        user.setUserName(userRegistrationRequest.getUserName());
        user.setPassword(userRegistrationRequest.getPassword().trim());
        user.setFirstName(userRegistrationRequest.getFirstName());
        user.setLastName(userRegistrationRequest.getLastName());
        user.setEmail(userRegistrationRequest.getEmail());
        user.setPhoneNumber(userRegistrationRequest.getPhoneNumber());
        user.setDateOfBirth(userRegistrationRequest.getDateOfBirth());
        user.setStatus(userRegistrationRequest.getStatus());
        user.setDateOfJoining(userRegistrationRequest.getDateOfJoining());
        userRepository.save(user);
        return Response.builder().message("User Created Successfully").build();
    }

//
}


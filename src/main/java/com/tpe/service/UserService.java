package com.tpe.service;

import com.tpe.domain.Role;
import com.tpe.domain.User;
import com.tpe.domain.enums.RoleType;
import com.tpe.dto.UserRequest;
import com.tpe.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void saveUser(UserRequest userRequest) {
        User user = new User();
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setUserName(userRequest.getUserName());
        //passwordü şifreleyerek DB ye gönderelim
        String password = userRequest.getPassword();
        String encodedPassword = passwordEncoder.encode(password);//requestdeki password karmaşıklaştırıldı

        user.setPassword(encodedPassword);
        //role setlenmesi...
        Set<Role> roles=new HashSet<>();
        Role role=roleService.getRoleByType(RoleType.ROLE_ADMIN);

        user.setRoles(roles);//defaultta user a ADMIN rolunu verdik.

        userRepository.save(user);
    }

}
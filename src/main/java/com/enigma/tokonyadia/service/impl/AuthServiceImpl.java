package com.enigma.tokonyadia.service.impl;

import com.enigma.tokonyadia.dto.request.AuthRequest;
import com.enigma.tokonyadia.dto.response.UserRespon;
import com.enigma.tokonyadia.entity.Role;
import com.enigma.tokonyadia.entity.UserCredential;
import com.enigma.tokonyadia.repository.UserRepository;
import com.enigma.tokonyadia.security.JwtUtils;
import com.enigma.tokonyadia.service.AuthService;
import com.enigma.tokonyadia.service.RoleService;
import com.enigma.tokonyadia.utils.constant.Eroll;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthServiceImpl implements AuthService {
    private PasswordEncoder passwordEncoder;
    private UserRepository userRepository;
    private RoleService rollService;
    private JwtUtils jwtUtils;
    private AuthenticationManager authenticationManager;

    @Autowired
    public AuthServiceImpl(PasswordEncoder passwordEncoder, UserRepository userRepository, RoleService rollService, JwtUtils jwtUtils, AuthenticationManager authenticationManager) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.rollService = rollService;
        this.jwtUtils = jwtUtils;
        this.authenticationManager = authenticationManager;
    }
    @Override
    public UserRespon register(AuthRequest authRequest) {
        Role roleCustomer = rollService.getOrSave(Eroll.ROLE_CUSTOMER);
        String hashPassword= passwordEncoder.encode(authRequest.getPassword());
        UserCredential userCredential = UserCredential.builder()
                .email(authRequest.getEmail())
                .password(hashPassword)
                .roles(List.of(roleCustomer))
                .build();
        userRepository.saveAndFlush(userCredential);
        List<String> roles=userCredential.getRoles().stream().map(role->role.getEroll().name()).toList();
        return UserRespon.builder()
                .email(userCredential.getEmail())
                .roles(roles)
                .build();
    }

    @Override
    public UserRespon registerAdmin(AuthRequest authRequest) {
        Role roleCustomer =rollService.getOrSave(Eroll.ROLE_CUSTOMER);
        Role roleAdmin = rollService.getOrSave((Eroll.ROLE_ADMIN));
        String hasPassword =passwordEncoder.encode((authRequest.getPassword()));

        UserCredential userCredential = UserCredential.builder()
                .email(authRequest.getEmail())
                .password(hasPassword)
                .roles(List.of(roleAdmin,roleCustomer))
                .build();
        return UserRespon.builder()
                .build();
    }

    @Override
    public String login(AuthRequest request) {
        Authentication authenticationToken = new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()
        );
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        UserCredential userCredential = (UserCredential) authenticate.getPrincipal();
        return jwtUtils.generatedToken(userCredential);
    }
}

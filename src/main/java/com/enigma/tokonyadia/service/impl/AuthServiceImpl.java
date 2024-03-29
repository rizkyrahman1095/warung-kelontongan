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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
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
        String hashPassword = passwordEncoder.encode(authRequest.getPassword());
        UserCredential userCredential = UserCredential.builder()
                .email(authRequest.getEmail())
                .password(authRequest.getPassword())
                .roles(List.of(roleCustomer))
                .build();
        userRepository.saveAndFlush(userCredential);
        List<String> roles = userCredential.getRoles().stream().map(role -> role.getEroll().name()).toList();
        return UserRespon.builder()
                .email(userCredential.getEmail())
                .roles(roles)
                .build();
    }

    @Override
    public String login(AuthRequest request) {
        try {
            // user validation
            Optional<UserCredential> userCredential = userRepository.findByEmail(request.getEmail());

            if (userCredential.isEmpty()){
                return null;
            }

            Authentication authenticate = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()));
            if (!authenticate.isAuthenticated()){
                return null;
            }

            // generate token
            return jwtUtils.generatedToken(userCredential.get());
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }
}

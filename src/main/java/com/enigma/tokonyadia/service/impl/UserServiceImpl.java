package com.enigma.tokonyadia.service.impl;


import com.enigma.tokonyadia.entity.UserCredential;
import com.enigma.tokonyadia.repository.UserRepository;
import com.enigma.tokonyadia.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserCredential loadByUserId(String userId) {
        return userRepository.findById(userId).orElseThrow(()-> new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Unauthorized"));
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username).orElseThrow(()->new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Unauthorized"));
    }
}


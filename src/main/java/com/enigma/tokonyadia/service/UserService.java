package com.enigma.tokonyadia.service;

import com.enigma.tokonyadia.entity.UserCredential;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    //verivication user id
    UserCredential loadByUserId(String userId);

}

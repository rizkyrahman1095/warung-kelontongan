package com.enigma.tokonyadia.service;


import com.enigma.tokonyadia.dto.request.AuthRequest;
import com.enigma.tokonyadia.dto.response.UserRespon;

public interface AuthService {

    UserRespon register(AuthRequest authRequest);
    String login(AuthRequest request);

}

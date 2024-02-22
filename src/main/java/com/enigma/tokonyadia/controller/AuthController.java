package com.enigma.tokonyadia.controller;

import com.enigma.tokonyadia.dto.request.AuthRequest;
import com.enigma.tokonyadia.dto.response.ControllerResponse;
import com.enigma.tokonyadia.dto.response.UserRespon;
import com.enigma.tokonyadia.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AuthRequest authRequest) {
        UserRespon userRespon = authService.register(authRequest);
        ControllerResponse<UserRespon> response = ControllerResponse.<UserRespon>builder()
                .status(HttpStatus.CREATED.getReasonPhrase())
                .message("Success add new user")
                .data(userRespon)
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
        String token = authService.login(authRequest);

        ControllerResponse<?> response = ControllerResponse.builder()
                .status(HttpStatus.OK.getReasonPhrase())
                .message("Login success")
                .data(token)
                .build();

        if (token == null){
            response.setStatus("Failed");
            response.setMessage("Login failed! invalid email or password");
            response.setData(null);
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(response);
        }

        return ResponseEntity.ok(response);
    }


}

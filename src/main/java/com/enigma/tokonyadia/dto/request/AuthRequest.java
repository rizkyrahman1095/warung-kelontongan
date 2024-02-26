package com.enigma.tokonyadia.dto.request;

import com.enigma.tokonyadia.entity.UserCredential;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class AuthRequest {
private String email;
private String password;
private List<UserCredential> userCredentials;
}

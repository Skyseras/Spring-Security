package com.example.demo.controller;

import com.example.demo.config.JwtUtils;
import com.example.demo.repository.AppUserRepository;
import com.example.demo.service.AppUserService;
import com.example.demo.service.dto.AuthenticationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationManager authenticationManager;

    private final AppUserRepository appuserRepository;
    private final JwtUtils jwtUtils;
    @Autowired
    AppUserService appuserService ;
    @PostMapping("/login")
    public ResponseEntity<String> authenticate(@RequestBody AuthenticationRequest request){
        new UsernamePasswordAuthenticationToken(request.getEmail(),request.getPassword());

        final UserDetails user= appuserService.findByEmail(request.getEmail());
        if (user != null){
            final String token=jwtUtils.generateToken(user);
            return ResponseEntity.ok(token);
        }
        return ResponseEntity.badRequest().build();
    }
}

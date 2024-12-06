package com.example.onepointup.service;

import com.example.onepointup.model.User;

public interface AuthenticationService {
    public User signInAndReturnJWT(User sinInRequest);
}

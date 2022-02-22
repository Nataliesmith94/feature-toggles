package com.project.featuretoggle.domain;

import lombok.Value;

@Value
public class AuthenticationDetails {

    private final String email;

    private final String password;
}

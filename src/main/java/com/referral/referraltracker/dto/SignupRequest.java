package com.referral.referraltracker.dto;

import lombok.Data;

@Data
public class SignupRequest {
    private String name;
    private String email;
    private String password;
    private String referralCode; // optional
} 
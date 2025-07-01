package com.referral.referraltracker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReferralInfoDTO {
    private String referredUser;
    private String email;
    private String status;
} 
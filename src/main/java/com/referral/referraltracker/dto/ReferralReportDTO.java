package com.referral.referraltracker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReferralReportDTO {
    private String referrerName;
    private String referrerEmail;
    private String referredName;
    private String referredEmail;
    private String status;
} 
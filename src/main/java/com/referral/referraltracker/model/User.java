package com.referral.referraltracker.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.UUID;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue
    private UUID id;

    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    private String password;

    @Column(name = "referral_code", unique = true, nullable = false)
    private String referralCode;

    @Column(name = "referred_by")
    private String referredBy; // referral code of referrer

    @Column(name = "is_profile_complete")
    private boolean isProfileComplete = false;
} 
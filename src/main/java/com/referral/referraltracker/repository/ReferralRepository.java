package com.referral.referraltracker.repository;

import com.referral.referraltracker.model.Referral;
import com.referral.referraltracker.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ReferralRepository extends JpaRepository<Referral, UUID> {
    List<Referral> findByReferrer(User referrer);
    List<Referral> findByReferred(User referred);
} 
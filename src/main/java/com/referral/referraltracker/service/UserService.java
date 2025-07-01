package com.referral.referraltracker.service;

import com.referral.referraltracker.dto.SignupRequest;
import com.referral.referraltracker.dto.SignupResponse;
import com.referral.referraltracker.dto.ProfileCompleteRequest;
import com.referral.referraltracker.dto.ProfileCompleteResponse;
import com.referral.referraltracker.dto.ReferralInfoDTO;
import com.referral.referraltracker.dto.ReferralReportDTO;
import com.referral.referraltracker.model.User;
import com.referral.referraltracker.model.Referral;
import com.referral.referraltracker.repository.UserRepository;
import com.referral.referraltracker.repository.ReferralRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;
import java.util.List;
import java.util.ArrayList;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final ReferralRepository referralRepository;

    @Autowired
    public UserService(UserRepository userRepository, ReferralRepository referralRepository) {
        this.userRepository = userRepository;
        this.referralRepository = referralRepository;
    }

    @Transactional
    public SignupResponse signup(SignupRequest request) {
        // Check for duplicate email
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            return new SignupResponse("Email already exists", null);
        }

        // Generate unique referral code
        String referralCode;
        do {
            referralCode = generateReferralCode();
        } while (userRepository.findByReferralCode(referralCode).isPresent());

        User.UserBuilder userBuilder = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(request.getPassword())
                .referralCode(referralCode)
                .isProfileComplete(false);

        User referrer = null;
        if (request.getReferralCode() != null && !request.getReferralCode().isEmpty()) {
            Optional<User> referrerOpt = userRepository.findByReferralCode(request.getReferralCode());
            if (referrerOpt.isEmpty()) {
                return new SignupResponse("Invalid referral code", null);
            }
            referrer = referrerOpt.get();
            userBuilder.referredBy(referrer.getReferralCode());
        }

        User user = userRepository.save(userBuilder.build());

        // If referred, create referral record
        if (referrer != null) {
            Referral referral = Referral.builder()
                    .referrer(referrer)
                    .referred(user)
                    .status("pending")
                    .build();
            referralRepository.save(referral);
        }

        return new SignupResponse("Signup successful", referralCode);
    }

    private String generateReferralCode() {
        // Simple random code, can be improved
        return UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    @Transactional
    public ProfileCompleteResponse completeProfile(ProfileCompleteRequest request) {
        UUID userId;
        try {
            userId = UUID.fromString(request.getUserId());
        } catch (Exception e) {
            return new ProfileCompleteResponse("Invalid userId format");
        }
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) {
            return new ProfileCompleteResponse("User not found");
        }
        User user = userOpt.get();
        if (user.isProfileComplete()) {
            return new ProfileCompleteResponse("Profile already completed");
        }
        user.setProfileComplete(true);
        userRepository.save(user);

        // If user was referred, update referral status
        if (user.getReferredBy() != null) {
            Optional<User> referrerOpt = userRepository.findByReferralCode(user.getReferredBy());
            if (referrerOpt.isPresent()) {
                User referrer = referrerOpt.get();
                Referral referral = referralRepository.findByReferrer(referrer).stream()
                        .filter(r -> r.getReferred().getId().equals(user.getId()))
                        .findFirst().orElse(null);
                if (referral != null && !"completed".equals(referral.getStatus())) {
                    referral.setStatus("completed");
                    referralRepository.save(referral);
                }
            }
        }
        return new ProfileCompleteResponse("Profile marked as complete");
    }

    public List<ReferralInfoDTO> getReferralsByUser(String userId) {
        UUID referrerId;
        try {
            referrerId = UUID.fromString(userId);
        } catch (Exception e) {
            return new ArrayList<>();
        }
        Optional<User> referrerOpt = userRepository.findById(referrerId);
        if (referrerOpt.isEmpty()) {
            return new ArrayList<>();
        }
        User referrer = referrerOpt.get();
        List<Referral> referrals = referralRepository.findByReferrer(referrer);
        List<ReferralInfoDTO> result = new ArrayList<>();
        for (Referral r : referrals) {
            User referred = r.getReferred();
            result.add(new ReferralInfoDTO(
                referred.getName(),
                referred.getEmail(),
                r.getStatus()
            ));
        }
        return result;
    }
} 
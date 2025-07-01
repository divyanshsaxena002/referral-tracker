package com.referral.referraltracker.controller;

import com.referral.referraltracker.dto.SignupRequest;
import com.referral.referraltracker.dto.SignupResponse;
import com.referral.referraltracker.dto.ProfileCompleteRequest;
import com.referral.referraltracker.dto.ProfileCompleteResponse;
import com.referral.referraltracker.dto.ReferralInfoDTO;
import com.referral.referraltracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public SignupResponse signup(@RequestBody SignupRequest request) {
        return userService.signup(request);
    }

    @PostMapping("/complete-profile")
    public ProfileCompleteResponse completeProfile(@RequestBody ProfileCompleteRequest request) {
        return userService.completeProfile(request);
    }

    @GetMapping("/referrals/{userId}")
    public List<ReferralInfoDTO> getReferrals(@PathVariable String userId) {
        return userService.getReferralsByUser(userId);
    }

    @GetMapping("/referrals/report")
    public ResponseEntity<byte[]> getReferralReport() {
        // This endpoint has been removed as per user request.
        throw new UnsupportedOperationException("Referral report export is no longer supported.");
    }
} 
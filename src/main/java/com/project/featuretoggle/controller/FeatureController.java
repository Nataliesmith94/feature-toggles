package com.project.featuretoggle.controller;

import com.project.featuretoggle.domain.Feature;
import com.project.featuretoggle.domain.Features;
import com.project.featuretoggle.service.FeatureService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@RestController
@RequestMapping("/api/account")
public class FeatureController {

    private final FeatureService featureService;

    public FeatureController(FeatureService featureService) {
        this.featureService = featureService;
    }

    @PostMapping("/createFeature")
    public HttpStatus createNewFeature(@RequestBody Feature feature) {
        return featureService.create(feature.getFeatureName());
    }

    @GetMapping("/feature-flags")
    public Features getFeaturesEnabledForUser(HttpServletRequest request) {
        // check if session is authenticated from re
        // get accountID from the authenticated session or sign in
        UUID accountId = UUID.randomUUID();
        return featureService.getAllEnabledFeaturesForUser(accountId);
    }

//    @PostMapping("/login")
//    public Features login(HttpServletRequest request, @RequestBody AuthenticationDetails authenticationDetails) {
//        UUID accountId = accountService.authenticateUser(request, authenticationDetails);
//        return featureService.getAllEnabledFeaturesForUser(accountId);
//    }

}

package com.project.featuretoggle.controller;

import com.project.featuretoggle.domain.AuthenticationDetails;
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
import javax.servlet.http.HttpSession;
import java.util.UUID;

@RestController
@RequestMapping("/api/account")
public class FeatureController {

    private static final String SESSION_ID = "session-id";
    private final FeatureService featureService;

    public FeatureController(FeatureService featureService) {
        this.featureService = featureService;
    }

    @PostMapping("/createFeature")
    public HttpStatus createNewFeature(@RequestBody Feature feature) {
        return featureService.create(feature.getFeatureName());
    }

//    @PostMapping("/login")
//    public Features login(HttpServletRequest request, @RequestBody AuthenticationDetails authenticationDetails) {
//
//        accountService.authenticateUser(request, authenticationDetails);
//        accountService.getSessionId(sessionId);
//        //get session
//
//        // authentiate users
//    }

    @GetMapping("/feature-flags")
    public Features getFeaturesEnabledForUser(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Object attribute = request.getAttribute(SESSION_ID);
        //get accountID from the session

        UUID accountId = UUID.randomUUID();
        return featureService.getAllEnabledFeaturesForUser();

    }

}

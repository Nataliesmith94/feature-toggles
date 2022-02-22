package com.project.featuretoggle.service;

import com.project.featuretoggle.client.AccountClient;
import com.project.featuretoggle.client.FeatureClient;
import com.project.featuretoggle.domain.Feature;
import com.project.featuretoggle.domain.Features;
import com.project.featuretoggle.domain.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.UUID;

import static java.util.stream.Collectors.toList;


@Service
public class FeatureService {

    public FeatureService(AccountClient accountClient, FeatureClient featureClient) {
        this.accountClient = accountClient;
        this.featureClient = featureClient;
    }

    AccountClient accountClient;
    FeatureClient featureClient;

    public HttpStatus create(String featureName) {
        Feature feature = Feature.builder()
                .featureName(featureName)
                .enabled(false)
                .build();
        return featureClient.add(feature);

    }

    public Features getAllEnabledFeaturesForUser(UUID accountId) {
        List<Feature> allFeatures = featureClient.getAllFeatures();
        try {
            User user = accountClient.getUser(accountId);
            List<String> usersPersonalEnabledFeatures = user.getEnabledFeatures();

            List<Feature> listOfGloballyEnabledFeatures = allFeatures.stream()
                    .filter(feature -> feature.isEnabled())
                    .collect(toList());

            for (String feature: usersPersonalEnabledFeatures) {
                listOfGloballyEnabledFeatures.add(Feature.builder()
                        .featureName(feature)
                        .enabled(true).build());
            }

            return Features.builder()
                    .features(listOfGloballyEnabledFeatures)
                    .build();
        } catch (HttpClientErrorException exception) {
            throw new RuntimeException("unable get user");
        }
    }


//    public void turnOnFeature(UUID accountId, String featureName) {
//        User user = getUserData(accountId);
//        user.getEnabledFeatured();
//    }
//
//    public void turnOnFeatureForUser(UUID accountId, String featureName, boolean featureStatus) {
//        FeatureToggle featureToggle = getFeatureToggle(featureName);
//        featureToggle.setEnabled(featureStatus);
//
//        User user = new User();
//        user.enabledFeatured();
//        feature = new Feature(featureName, false);
//
//
//        if(feature1.isEnabled()) {
//            //show new service
//        } else {
//            //show old service
//        }
//    }
}


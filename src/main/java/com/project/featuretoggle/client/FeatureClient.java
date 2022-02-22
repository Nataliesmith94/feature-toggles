package com.project.featuretoggle.client;

import com.project.featuretoggle.domain.Feature;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.Arrays.asList;

@Component
public class FeatureClient {

    public List<Feature> getAllFeatures() {
        // call DB and get all features back
        // restTemplate.getForObject(urlWithSessionId, SessionEnvelope.class, sessionId);
        return createFeatureList();
    }


    public HttpStatus add(Feature feature) {
        System.out.println("Feature saved: " + feature.getFeatureName());
        //        restTemplate.postForObject(url, request, Feature.class);
        return HttpStatus.CREATED;
    }

    private List<Feature> createFeatureList() {
        return asList(
                createFeature("Feaure1", true),
                createFeature("Feaure2", true),
                createFeature("Feaure3", false),
                createFeature("Feaure4", false)
        );
    }

    private Feature createFeature(String featureName, boolean enabled) {
        return Feature.builder()
                .featureName(featureName)
                .enabled(enabled).build();
    }

}

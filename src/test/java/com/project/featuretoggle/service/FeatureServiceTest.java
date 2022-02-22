package com.project.featuretoggle.service;

import com.project.featuretoggle.client.FeatureClient;
import com.project.featuretoggle.client.AccountClient;
import com.project.featuretoggle.domain.Feature;
import com.project.featuretoggle.domain.Features;
import com.project.featuretoggle.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FeatureServiceTest {

    @Mock
    private AccountClient accountClient;

    @Mock
    private FeatureClient featureClient;

    private FeatureService featureService;

    private UUID accountId;

    @BeforeEach
    public void setup() {
        accountId = UUID.randomUUID();
        featureService = new FeatureService(accountClient, featureClient);
    }

    @Test
    public void shouldCreateNewFeatureAndDefaultToFalseAndReturn201Status() {
        when(featureClient.add(any())).thenReturn(HttpStatus.CREATED);

        HttpStatus result = featureService.create("test-feature");

        assertThat(result).isEqualTo(HttpStatus.CREATED);

        Feature expectedFeature = Feature.builder().featureName("test-feature").enabled(false).build();

        verify(featureClient, times(1)).add(expectedFeature);
        assertThat(result).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    public void shouldThrowRunTimeExceptionIfErrorIsThrownInClient() {
        when(featureClient.add(any())).thenThrow(new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR));

        Assertions.assertThrows(RuntimeException.class, () -> {
            featureService.create("test-feature");
        });
    }

    @Test
    public void shouldOnlyReturnAllGloballyEnabledFeaturesAndUsersEnabledFeatures() {
        when(accountClient.getUser(accountId)).thenReturn(createUser(asList("users-enabled-feat1", "users-enabled-feat2")));
        when(featureClient.getAllFeatures()).thenReturn(createFeatureList());

        List<Feature> allEnabledFeatures = asList(
                createFeature("Global-enabled-1", true),
                createFeature("Global-enabled-2", true),
                createFeature("users-enabled-feat1", true),
                createFeature("users-enabled-feat2", true)
        );
        Features expectedResult = Features.builder().features(allEnabledFeatures).build();

        Features result = featureService.getAllEnabledFeaturesForUser(accountId);

        assertThat(result).isEqualTo(expectedResult);

        verify(accountClient, times(1)).getUser(accountId);
        verify(featureClient, times(1)).getAllFeatures();
    }

    @Test
    public void shouldOnlyReturnAllGloballyEnabled() {
        when(accountClient.getUser(accountId)).thenReturn(createUser(Collections.emptyList()));
        when(featureClient.getAllFeatures()).thenReturn(createFeatureList());

        List<Feature> allEnabledFeatures = asList(
                createFeature("Global-enabled-1", true),
                createFeature("Global-enabled-2", true)
        );
        Features expectedResult = Features.builder().features(allEnabledFeatures).build();

        Features result = featureService.getAllEnabledFeaturesForUser(accountId);



        assertThat(result).isEqualTo(expectedResult);

        verify(accountClient, times(1)).getUser(accountId);
        verify(featureClient, times(1)).getAllFeatures();
    }

    private List<Feature> createFeatureList() {
        return asList(
                createFeature("Global-enabled-1", true),
                createFeature("Global-enabled-2", true),
                createFeature("Global-disabled-3", false),
                createFeature("Global-enabled-4", false)
        );
    }

    private Feature createFeature(String featureName, boolean enabled) {
        return Feature.builder()
                .featureName(featureName)
                .enabled(enabled).build();
    }

    private User createUser(List<String> enabledFeatures) {
       return User.builder()
                .accountId(accountId)
                .name("test")
                .roleType("user")
                .enabledFeatures(enabledFeatures).build();
    }

}
package com.project.featuretoggle.controller;

import com.project.featuretoggle.domain.Feature;
import com.project.featuretoggle.domain.Features;
import com.project.featuretoggle.service.FeatureService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import javax.servlet.http.Cookie;
import java.util.List;
import java.util.UUID;

import static java.util.Arrays.asList;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest({FeatureController.class})
@AutoConfigureWebClient
@ExtendWith(SpringExtension.class)
class featureControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private FeatureService featureService;

    private Cookie sessionIdCookie;
    private UUID accountId;
    private UUID sessionId;

    @BeforeEach
    public void setup() {
        accountId = UUID.randomUUID();
        sessionId = UUID.randomUUID();
        sessionIdCookie = new Cookie("session-id", sessionId.toString());
    }

    @Test
    public void shouldReturnOkOnPostWhenCreatingNewFeature() throws Exception {
        // Arrange
        when(featureService.create("test")).thenReturn(HttpStatus.OK);

        // Act
        mvc.perform(post("/api/account/createFeature")
                        .contentType(APPLICATION_JSON)
                        .content(String.format("{\"featureName\": \"%s\"}", "FeatureNameValue")))
                .andExpect(status().isOk());

        // Assert
        verify(featureService).create("FeatureNameValue");
    }

    @Test
    public void shouldReturnListOfEnabledFeatureFlags() throws Exception {
        // Arrange
        Features expectedReturnValue = Features.builder().features(createFeatureList()).build();
        when(featureService.getAllEnabledFeaturesForUser(any())).thenReturn(expectedReturnValue);

        // Act
        mvc.perform(get("/api/account/feature-flags")
                        .cookie(sessionIdCookie))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.features[0].featureName").value("Global-enabled-1"));

        // Assert
//        verify(featureService).getAllEnabledFeaturesForUser(accountId);
    }


    private List<Feature> createFeatureList() {
        return asList(
                createFeature("Global-enabled-1", true),
                createFeature("Global-enabled-2", true),
                createFeature("personal-enabled-3", true),
                createFeature("personal-enabled-4", true)
        );
    }

    private Feature createFeature(String featureName, boolean enabled) {
        return Feature.builder()
                .featureName(featureName)
                .enabled(enabled).build();
    }


//    private HashMap<String, Boolean> createFeatureHashMap() {
//        HashMap<String, Boolean> featureToggleMap = new HashMap<String, Boolean>();
//        featureToggleMap.put("Global-enabled-1", true);
//        featureToggleMap.put("Global-enabled-2", true);
//        featureToggleMap.put("personal-enabled-3", true);
//        featureToggleMap.put("personal-enabled-4", true);
//        return featureToggleMap;
//    }
}
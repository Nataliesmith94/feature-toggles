//package com.project.featuretoggle.service;
//
//import com.project.featuretoggle.client.AccountClient;
//import com.project.featuretoggle.domain.AuthenticationDetails;
//import com.project.featuretoggle.domain.Feature;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.websocket.Session;
//import java.util.List;
//import java.util.UUID;
//
//import static java.util.Objects.nonNull;
//
//@Service
//@RequiredArgsConstructor
//public class AccountService {
//
//    AccountClient accountClient;
//
//    public Session authenticateUser(HttpServletRequest request, AuthenticationDetails authenticationDetails) {
//        List<Feature> allFeatures = featureClient.getAllFeatures();
//        UUID accountId = accountClient.authenticateUser(authenticationDetails);
//
//        if (nonNull(accountId)) {
//            Session authenticatedSessionRequest = Session.builder
//                    .isAuthenticated(true)
//                    .accountId(accountId)
//                    .build();
//
//            UUID sessionId = sessionClient.create(authenticatedSessionRequest);
//            setSessionId(sessionId, request);
//
//            return authenticatedSessionRequest;
//        }
//
//        return Session.builder
//                .isAuthenticated(false)
//                .build();
//    }
//
//    public void setSessionId(UUID sessionId, HttpServletRequest request) {
//        request.setAttribute("session-id", sessionId);
//    }
//
//    public UUID getSessionId(HttpServletRequest request) {
//        return (UUID) request.getAttribute("session-id");
//    }
//
//}

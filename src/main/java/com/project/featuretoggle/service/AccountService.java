//package com.project.featuretoggle.service;
//
//import com.project.featuretoggle.client.AccountClient;
//import com.project.featuretoggle.domain.AuthenticationDetails;
//import com.project.featuretoggle.domain.Feature;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.HttpClientErrorException;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.websocket.Session;
//import java.util.List;
//import java.util.Objects;
//import java.util.UUID;
//
//import static java.util.Objects.nonNull;
//import static org.springframework.http.HttpStatus.UNAUTHORIZED;
//
//@Service
//@RequiredArgsConstructor
//public class AccountService {
//
//    AccountClient accountClient;
//
//    public UUID authenticateUser(HttpServletRequest request, AuthenticationDetails authenticationDetails) {
//        UUID accountId = accountClient.authenticateUser(authenticationDetails);
//
//        if (nonNull(accountId)) {
//            request.getSession().setAttribute("isAuthenticated", "true");
//            request.getSession().setAttribute("accountId", accountId);
//            // UUID sessionId = sessionClient.create(authenticatedSessionRequest);
//            //  setSessionId(sessionId, request);
//            return authenticatedSessionRequest;
//        }
//
//        throw new HttpClientErrorException(UNAUTHORIZED);
//    }
//
//    public Boolean isUserAuthenticated(HttpServletRequest request) {
//        String isAuthenticated = (String) request.getSession().getAttribute("isAuthenticated");
//        return isAuthenticated.contentEquals("true");
//    }
//
//    public void setSessionId(UUID sessionId, HttpServletRequest request) {
//        request.setAttribute("session-id", sessionId);
//    }
//
//    public UUID getSessionId(HttpServletRequest request) {
//        return (UUID) request.getAttribute("session-id");
//    }
//}

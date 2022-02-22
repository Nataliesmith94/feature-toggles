package com.project.featuretoggle.client;

import com.project.featuretoggle.domain.AuthenticationDetails;
import com.project.featuretoggle.domain.User;
import org.springframework.stereotype.Component;

import java.util.UUID;

import static java.util.Arrays.asList;

@Component
public class AccountClient {

    public User getUser(UUID accountId)  {
        System.out.println("getting user details for account: " + accountId);

        // GET ALL USERS FROM DATA BASE THEN FILTERS BY ACCOUNT ID

        return User.builder().accountId(UUID.randomUUID())
                .name("Natalie")
                .roleType("User")
                .enabledFeatures(asList("allow-to-view-docs", "allow-edit-profile")).build();
    }

    public UUID authenticateUser(AuthenticationDetails authenticationDetails) {
        // call account service with username and password to get back accountId
        // User response = restTemplate.postForObject(url, request, User.class);
        return UUID.randomUUID();
    }

//    public Session getSession(UUID sessionId) {
//        // call session service to get session information back
//        return Session.builder.build();
//    }

}

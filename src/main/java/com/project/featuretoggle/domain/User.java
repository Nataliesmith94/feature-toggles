package com.project.featuretoggle.domain;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.util.List;
import java.util.UUID;

@Value
@Builder
public class User {

    private String name;
    private UUID accountId;
    private String roleType;
    private List<String> enabledFeatures;
}

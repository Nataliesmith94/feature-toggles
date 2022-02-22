package com.project.featuretoggle.domain;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Feature {

    private final String featureName;
    private final boolean enabled;

}

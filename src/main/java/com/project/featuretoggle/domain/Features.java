package com.project.featuretoggle.domain;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class Features {
    List<Feature> features;
}

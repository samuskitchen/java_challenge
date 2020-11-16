package com.wolox.challenge.models.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GeoDTO {

    @JsonProperty("lat")
    private String latitude;

    @JsonProperty("lng")
    private String longitude;
}

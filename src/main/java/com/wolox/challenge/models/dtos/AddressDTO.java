package com.wolox.challenge.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressDTO {

    private String street;

    private String suite;

    private String city;

    private String zipcode;

    private GeoDTO geo;
}

package com.wolox.challenge.models.transforms;

import com.wolox.challenge.models.Geo;
import com.wolox.challenge.models.dtos.GeoDTO;

public class GeoTransform {

    public GeoDTO geoToGeoDTO(Geo geo){
        GeoDTO geoDTOResponse = null;

        if (null != geo){
            geoDTOResponse = GeoDTO.builder()
                    .latitude(geo.getLatitude())
                    .longitude(geo.getLongitude())
                    .build();
        }

        return geoDTOResponse;
    }
}

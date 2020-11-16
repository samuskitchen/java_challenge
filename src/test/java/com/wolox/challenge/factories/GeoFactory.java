package com.wolox.challenge.factories;

import com.wolox.challenge.models.Geo;
import com.wolox.challenge.models.dtos.GeoDTO;
import org.fluttercode.datafactory.impl.DataFactory;

public class GeoFactory {

    private static final DataFactory dataFactory = new DataFactory();

    private Long id;

    private final String latitude;

    private final String longitude;

    public GeoFactory() {
        this.id = (long) dataFactory.getNumberBetween(1, 10);
        this.latitude = dataFactory.getNumberText(10);
        this.longitude = dataFactory.getNumberText(10);
    }

    public Geo newInstance() {
        return Geo.builder().
                id(this.id)
                .latitude(this.latitude)
                .longitude(this.longitude)
                .build();
    }

    public GeoDTO newInstanceDTO() {
        return GeoDTO.builder()
                .latitude(this.latitude)
                .longitude(this.longitude)
                .build();
    }

    public void setId(Long id) {
        this.id = id;
    }
}

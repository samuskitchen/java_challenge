package com.wolox.challenge.factories;

import com.wolox.challenge.models.Address;
import com.wolox.challenge.models.Geo;
import com.wolox.challenge.models.dtos.AddressDTO;
import org.fluttercode.datafactory.impl.DataFactory;

public class AddressFactory {

    private static final DataFactory dataFactory = new DataFactory();

    private Long id;

    private final String street;

    private final String suite;

    private final String city;

    private final String zipcode;

    private final Geo geo;

    public AddressFactory() {
        this.id = (long) dataFactory.getNumberBetween(1, 10);
        this.street = dataFactory.getAddress();
        this.suite = dataFactory.getRandomText(1, 10);
        this.city = dataFactory.getCity();
        this.zipcode = dataFactory.getNumberText(6);
        this.geo = new GeoFactory().newInstance();
    }

    public Address newInstance() {
        return Address.builder()
                .id(this.id)
                .street(this.street)
                .suite(this.suite)
                .city(this.city)
                .zipcode(this.zipcode)
                .geo(this.geo)
                .build();
    }

    public AddressDTO newInstanceDTO() {
        return AddressDTO.builder()
                .street(this.street)
                .suite(this.suite)
                .city(this.city)
                .zipcode(this.zipcode)
                .geo(new GeoFactory().newInstanceDTO())
                .build();
    }

    public void setId(Long id) {
        this.id = id;
    }
}

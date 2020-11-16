package com.wolox.challenge.models.transforms;

import com.wolox.challenge.models.Address;
import com.wolox.challenge.models.dtos.AddressDTO;

public class AddressTransform {

    public static final GeoTransform geoTransform = new GeoTransform();

    public AddressDTO addressToAddressDTO(Address address){
        AddressDTO addressDTOResponse = null;

        if (null != address){
            addressDTOResponse = AddressDTO.builder()
                    .city(address.getCity())
                    .street(address.getStreet())
                    .suite(address.getSuite())
                    .zipcode(address.getZipcode())
                    .geo(geoTransform.geoToGeoDTO(address.getGeo()))
                    .build();
        }

        return addressDTOResponse;
    }

}

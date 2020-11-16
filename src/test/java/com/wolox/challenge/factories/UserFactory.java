package com.wolox.challenge.factories;

import com.wolox.challenge.models.Address;
import com.wolox.challenge.models.Company;
import com.wolox.challenge.models.User;
import com.wolox.challenge.models.dtos.UserDTO;
import org.fluttercode.datafactory.impl.DataFactory;

public class UserFactory {

    private static final DataFactory dataFactory = new DataFactory();

    private Long id;

    private Long endpointId;

    private final String name;

    private final String username;

    private final String email;

    private final Address address;

    private final String phone;

    private final String website;

    private final Company company;

    public UserFactory() {
        this.id = (long) dataFactory.getNumberBetween(1, 10);
        this.endpointId = (long) dataFactory.getNumberBetween(1, 10);
        this.name = dataFactory.getName();
        this.username = dataFactory.getRandomText(1, 10);
        this.email = dataFactory.getEmailAddress();
        this.address = new AddressFactory().newInstance();
        this.phone = dataFactory.getNumberText(10);
        this.website = dataFactory.getRandomText(1, 10);
        this.company = new CompanyFactory().newInstance();
    }

    public User newInstance() {
        return User.builder()
                .id(this.id)
                .endpointId(this.endpointId)
                .name(this.name)
                .username(this.username)
                .email(this.email)
                .address(this.address)
                .phone(this.phone)
                .website(this.website)
                .company(this.company)
                .build();
    }

    public UserDTO newInstanceDTO() {
        return UserDTO.builder()
                .id(this.endpointId)
                .name(this.name)
                .username(this.username)
                .email(this.email)
                .address(new AddressFactory().newInstanceDTO())
                .phone(this.phone)
                .website(this.website)
                .company(new CompanyFactory().newInstanceDTO())
                .build();
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setEndpointId(Long endpointId) {
        this.endpointId = endpointId;
    }
}

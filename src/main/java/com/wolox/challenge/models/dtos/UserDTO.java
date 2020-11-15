package com.wolox.challenge.models.dtos;

import lombok.Data;

@Data
public class UserDTO {

    private String name;

    private String username;

    private String email;

    private AddressDTO address;

    private String phone;

    private String website;

    private CompanyDTO company;
}

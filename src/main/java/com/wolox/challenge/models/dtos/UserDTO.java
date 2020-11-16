package com.wolox.challenge.models.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDTO {

    private Long id;

    private String name;

    private String username;

    private String email;

    private AddressDTO address;

    private String phone;

    private String website;

    private CompanyDTO company;
}

package com.wolox.challenge.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
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

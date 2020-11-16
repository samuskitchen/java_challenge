package com.wolox.challenge.models.transforms;

import com.wolox.challenge.models.User;
import com.wolox.challenge.models.dtos.UserDTO;

public class UserTransform {

    public static final CompanyTransform companyTransform = new CompanyTransform();
    public static final AddressTransform addressTransform = new AddressTransform();

    public UserDTO userToUserDTO(User user) {
        UserDTO userDTOResponse = null;

        if (null != user) {
            userDTOResponse = UserDTO.builder()
                    .id(user.getEndpointId())
                    .email(user.getEmail())
                    .name(user.getName())
                    .phone(user.getPhone())
                    .username(user.getUsername())
                    .website(user.getWebsite())
                    .address(addressTransform.addressToAddressDTO(user.getAddress()))
                    .company(companyTransform.companyToCompanyDTO(user.getCompany()))
                    .build();
        }

        return userDTOResponse;
    }
}

package com.wolox.challenge.models.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CompanyDTO {

    private String name;

    private String catchPhrase;

    private String bs;
}

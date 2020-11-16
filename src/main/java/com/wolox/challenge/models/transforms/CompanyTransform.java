package com.wolox.challenge.models.transforms;

import com.wolox.challenge.models.Company;
import com.wolox.challenge.models.dtos.CompanyDTO;

public class CompanyTransform {

    public CompanyDTO companyToCompanyDTO(Company company) {
        CompanyDTO companyDTOResponse = null;

        if (null != company) {
            companyDTOResponse = CompanyDTO.builder()
                    .bs(company.getBs())
                    .catchPhrase(company.getCatchPhrase())
                    .name(company.getName())
                    .build();
        }

        return companyDTOResponse;
    }
}
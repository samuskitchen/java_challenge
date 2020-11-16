package com.wolox.challenge.factories;

import com.wolox.challenge.models.Company;
import com.wolox.challenge.models.dtos.CompanyDTO;
import org.fluttercode.datafactory.impl.DataFactory;

public class CompanyFactory {

    private static final DataFactory dataFactory = new DataFactory();

    private Long id;

    private final String name;

    private final String catchPhrase;

    private final String bs;

    public CompanyFactory() {
        this.id = (long) dataFactory.getNumberBetween(1, 10);
        this.name = dataFactory.getName();
        this.catchPhrase = dataFactory.getRandomText(1, 20);
        this.bs = dataFactory.getRandomText(1, 10);
    }

    public Company newInstance() {
        return Company.builder()
                .id(this.id)
                .name(this.name)
                .catchPhrase(this.catchPhrase)
                .bs(this.bs)
                .build();
    }

    public CompanyDTO newInstanceDTO() {
        return CompanyDTO.builder()
                .name(this.name)
                .catchPhrase(this.catchPhrase)
                .bs(this.bs)
                .build();
    }

    public void setId(Long id) {
        this.id = id;
    }
}

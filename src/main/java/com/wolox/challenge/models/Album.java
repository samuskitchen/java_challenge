package com.wolox.challenge.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class Album {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    @JsonProperty("id")
    private Long endpointId;

    @JsonProperty("userId")
    private Long ownerEndpointId;

    private String title;
}

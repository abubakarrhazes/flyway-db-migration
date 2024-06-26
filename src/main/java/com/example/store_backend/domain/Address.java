package com.example.store_backend.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "address_tbl")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer addressId;

    @Pattern(regexp = "[A-Za-z0-9\\s-]{3,}", message = "Not a valid street no")
    private String streetNo;

    @Pattern(regexp = "[A-Za-z0-9\\s-]{3,}", message = "Not a valid building name")
    private String buildingName;

    @NotNull
    @Pattern(regexp = "[A-Za-z0-9\\s-]{3,}", message = "Not a valid locality name")
    private String locality;

    @NotNull(message = "City name cannot be null")
    @Pattern(regexp = "[A-Za-z\\s]{2,}", message = "Not a valid city name")
    private String city;

    @NotNull(message = "State name cannot be null")
    private String state;


    @ManyToOne(cascade = CascadeType.ALL)
    @JsonIgnore
    private Customer customer;


}
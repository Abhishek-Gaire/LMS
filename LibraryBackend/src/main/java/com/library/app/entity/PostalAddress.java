package com.library.app.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "postal_address")
public class PostalAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String streetName;

    @Column(nullable = false)
    private String streetNumber;

    private String zipCode;

    @Column(nullable = false)
    private String placeName;

    @Column(nullable = false)
    private String country;

    private String additionalInfo;

}

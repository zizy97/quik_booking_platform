package com.ventrek.bookingplatform.domain;

import lombok.Data;

import javax.persistence.Entity;

@Data
@Entity
public class Address extends Trace{
    private String number;
    private String city;
}

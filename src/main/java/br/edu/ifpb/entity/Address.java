package br.edu.ifpb.entity;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class Address {
	private String street;
	private String city;
	private String state;
	private String zipcode;
	private String country;
}
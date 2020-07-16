package com.github.luanneves07.ifood.registration;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "location")
public class Location {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long id;

	public Double latitude;

	public Double longitude;

	@Override
	public String toString() {
		return this.getClass().getSimpleName() + "<" + id + ">";
	}
}

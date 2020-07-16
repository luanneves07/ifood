package com.github.luanneves07.ifood.registration;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

@Entity
@Table(name = "dish")
public class Dish extends PanacheEntityBase {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long id;

	public String name;

	public BigDecimal price;

	public String description;

	@ManyToOne
	public Restaurant restaurant;

	@Override
	public String toString() {
		return this.getClass().getSimpleName() + "<" + id + ">";
	}

}

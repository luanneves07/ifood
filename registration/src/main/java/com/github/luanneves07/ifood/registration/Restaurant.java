package com.github.luanneves07.ifood.registration;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

@Entity
@Table(name = "restaurant")
public class Restaurant extends PanacheEntityBase {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long id;

	public String owner;

	public String cnpj;

	public String name;

	@OneToOne
	public Location location;

	@CreationTimestamp
	public Date creationTime;

	@UpdateTimestamp
	public Date updatedTime;

	@Override
	public String toString() {
		return this.getClass().getSimpleName() + "<" + id + ">";
	}

}

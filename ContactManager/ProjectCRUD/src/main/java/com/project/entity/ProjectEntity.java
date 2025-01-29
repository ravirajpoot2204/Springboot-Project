package com.project.entity;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Lob;

import org.hibernate.annotations.Loader;

@Entity

public class ProjectEntity {
	@javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.AUTO)
	@Id
	private int id;


	private String description;

	private String name;

	private long price;

	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getPrice() {
		return price;
	}

	public void setPrice(long price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "ProjectEntity [id=" + id + ", description=" + description + ", name=" + name + ", price=" + price + "]";
	}

	public ProjectEntity(String description, String name, long price) {
		super();
		this.description = description;
		this.name = name;
		this.price = price;
	}

	public ProjectEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	
}

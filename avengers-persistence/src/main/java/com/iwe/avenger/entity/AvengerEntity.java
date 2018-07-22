package com.iwe.avenger.entity;

public class AvengerEntity {

	private String id;
	private String name;
	private String secretIdentity;

	public AvengerEntity() {
		super();
	}

	public AvengerEntity(String id, String name, String secretIdentity) {
		super();
		this.id = id;
		this.name = name;
		this.secretIdentity = secretIdentity;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSecretIdentity() {
		return secretIdentity;
	}

	public void setSecretIdentity(String secretIdentity) {
		this.secretIdentity = secretIdentity;
	}

}

package com.dotcom.costaverde.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Controle implements Serializable  {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	private long id;
	private Timestamp lastUpdate;
	private String locado;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Timestamp getLastUpdate() {
		return lastUpdate;
	}
	public void setLastUpdate(Timestamp lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	public String getLocado() {
		return locado;
	}
	public void setLocado(String locado) {
		this.locado = locado;
	}
	

	

	
	
	

	
}

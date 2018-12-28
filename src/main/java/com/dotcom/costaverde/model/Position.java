package com.dotcom.costaverde.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Position implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	private String idPontoReferencia;
	private Timestamp data;
	private String placa;
	private String latitude;
	private String longitude;
	private Timestamp dataIni;
	private Timestamp dataFim;
	private String nome;
	private String tipo;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getIdPontoReferencia() {
		return idPontoReferencia;
	}
	public void setIdPontoReferencia(String idPontoReferencia) {
		this.idPontoReferencia = idPontoReferencia;
	}
	public Timestamp getData() {
		return data;
	}
	public void setData(Timestamp data) {
		this.data = data;
	}
	public String getPlaca() {
		return placa;
	}
	public void setPlaca(String placa) {
		this.placa = placa;
	}
	public String getLatitude() {
		return latitude.replace(",", ".");
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getLongitude() {
		return longitude.replace(",", ".");
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public Timestamp getDataIni() {
		return dataIni;
	}
	public void setDataIni(Timestamp dataIni) {
		this.dataIni = dataIni;
	}
	public Timestamp getDataFim() {
		return dataFim;
	}
	public void setDataFim(Timestamp dataFim) {
		this.dataFim = dataFim;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	public String toString(Timestamp timeStamp) {
		String strDate = timeStamp.toString();
		return strDate.substring(11, 19);
	}
	
	
}

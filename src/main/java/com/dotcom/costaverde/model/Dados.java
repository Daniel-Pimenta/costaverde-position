package com.dotcom.costaverde.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "dados")
@XmlAccessorType (XmlAccessType.FIELD)
public class Dados {

	@XmlElement(name = "idpontoreferencia")
	private String idpontoreferencia;
	
	@XmlElement(name = "Data")
	private String Data;
	
	@XmlElement(name = "Placa")
	private String Placa;
	
	@XmlElement(name = "tipo")
	private String tipo;
	
	@XmlElement(name = "Latitude")
	private String Latitude;
	
	@XmlElement(name = "Longitude")
	private String Longitude;
	
	@XmlElement(name = "nome")
	private String nome;
	
	@XmlElement(name = "dataini")
	private String dataini;
	
	@XmlElement(name = "datafim")
	private String datafim;
	
	
	public String getIdpontoreferencia() {
		return idpontoreferencia;
	}
	public void setIdpontoreferencia(String idpontoreferencia) {
		this.idpontoreferencia = idpontoreferencia;
	}
	public String getData() {
		return Data;
	}
	public void setData(String data) {
		Data = data;
	}
	public String getPlaca() {
		return Placa;
	}
	public void setPlaca(String placa) {
		Placa = placa;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getLatitude() {
		return Latitude;
	}
	public void setLatitude(String latitude) {
		Latitude = latitude;
	}
	public String getLongitude() {
		return Longitude;
	}
	public void setLongitude(String longitude) {
		Longitude = longitude;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getDataini() {
		return dataini;
	}
	public void setDataini(String dataini) {
		this.dataini = dataini;
	}
	public String getDatafim() {
		return datafim;
	}
	public void setDatafim(String datafim) {
		this.datafim = datafim;
	}


	
}

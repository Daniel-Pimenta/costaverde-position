package com.dotcom.costaverde.model;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "DocumentElement")
@XmlAccessorType (XmlAccessType.FIELD)
public class DocumentElement {
	
	@XmlElement(name = "dados")
	private List<Dados> dados = null;

	public List<Dados> getDados() {
		return dados;
	}

	public void setDados(List<Dados> dados) {
		this.dados = dados;
	}
	
	public int getSize() {
		return dados == null ? 0 : dados.size();
	}

}

package com.dotcom.costaverde.service;

import java.io.StringReader;
//import java.net.URL;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.dotcom.costaverde.model.Controle;
import com.dotcom.costaverde.model.Dados;
import com.dotcom.costaverde.model.DocumentElement;
import com.dotcom.costaverde.model.Position;
import com.dotcom.costaverde.repository.ControleRepository;
import com.dotcom.costaverde.repository.PositionRepository;

@Service
public class PositionService {

	@Autowired
	private PositionRepository pr;
	@Autowired
	private ControleRepository cr;

	@Value("${sigla.costaverde.ws.url}")
	private String url;

	@Value("${sigla.costaverde.ws.usuario}")
	private String usuario;

	@Value("${sigla.costaverde.ws.senha}")
	private String senha;
	
	@Value("${spring.profiles.active}")
	private String profile;

	private String dataIni;
	private String dataFim;
	private String dataBD;
	private java.sql.Timestamp lastUpDate;
	private boolean lock;

	private Controle controle;

	private static final Logger log = LoggerFactory.getLogger(PositionService.class);

	private JAXBContext jaxbContext;
	private DocumentElement documentElement;
	private String saidaXML;

	public List<Position> getPosition() {
		this.getPeriodo();
		Optional<Controle> optControle = cr.findById((long) 1);
		if (!optControle.isPresent()) {
			log.info("Iniciando Controle...");
      controle(true);
		} else {
			log.info("Lendo Controle...");
			controle = optControle.get();
			this.lastUpDate = controle.getLastUpdate();
			this.lock = controle.getLocado().equalsIgnoreCase("true") ? true : false;
		}
		log.info("Data Con:"+lastUpDate);
		boolean okWeb = false;
		okWeb = lastUpDate.before(toDate(this.dataIni, "yyyy-MM-dd HH:mm:ss")) ? true : okWeb;
		log.info(okWeb+"");
		okWeb = lock ? false : okWeb;
		log.info(okWeb+"");
		okWeb = optControle.isPresent() ? okWeb: true;	
		log.info(okWeb+"");	
		
		if (okWeb) {
			webService();
		}else {
      log.info("Buscando do Banco de Dados !");
		}
		List<Position> positions = pr.findMaxId(this.dataFim);
		log.info("Ok : "+positions.size());
		return positions;
	}

	public List<Position> getOnibus(String placa){
		log.info("getOnibus("+placa+")");
		List<Position> positions = pr.findOnibus(placa, this.dataFim);
		log.info("getOnibus("+positions.size()+")");
		return positions;
	}
	
	public void webService() {
		log.info("Buscando do WebService");
		StringBuffer sbUrl = new StringBuffer();
		sbUrl.append(url);
		sbUrl.append("UsuarioSigla=" + usuario + "&SenhaSigla=" + senha + "&");
		sbUrl.append("dataini=" + this.dataIni + "&");
		sbUrl.append("datafim=" + this.dataFim);
		log.info("webService(" + sbUrl.toString() + ")");
		getXML(sbUrl.toString());
		try {
			jaxbContext = JAXBContext.newInstance(DocumentElement.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			// URL newURL = new URL(sbUrl.toString());
			StringReader reader = new StringReader(this.saidaXML);
			documentElement = (DocumentElement) jaxbUnmarshaller.unmarshal(reader);
			savePosition(documentElement);
			controle(false);
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		log.info("webService(FIM)");
	}
	
	private void getXML(String uri) {
		RestTemplate rest;
		HttpHeaders headers;

		rest = new RestTemplate();
		headers = new HttpHeaders();
		headers.add("Content-Type", "application/xml");
		headers.add("Accept", "*/*");

		HttpEntity<String> requestEntity = new HttpEntity<String>("", headers);
		ResponseEntity<String> responseEntity = rest.exchange(uri, HttpMethod.GET, requestEntity, String.class);
		log.info("Status Code:"+responseEntity.getStatusCode().toString());
		saidaXML = responseEntity.getBody();
		saidaXML = saidaXML.replace("<![CDATA[", "").replace("]]>", "");
	}

	public void savePosition(DocumentElement doc) {
		log.info("SavePosition(" + doc.getSize() + ")...");
		Position pos = new Position();
		if (doc.getSize() > 0) {
			List<Dados> dados = doc.getDados();
			Iterator<Dados> iterator = dados.iterator();
			while (iterator.hasNext()) {
				Dados dado = (Dados) iterator.next();

				pos.setId(0);
				pos.setIdPontoReferencia(dado.getIdpontoreferencia());
				
				String lat = dado.getLatitude() + "0000";
				String lon = dado.getLongitude() + "0000";
				
				pos.setLatitude (lat.replace(",", ".").substring(0,7));
				pos.setLongitude(lon.replace(",", ".").substring(0,6));
				
				String placa = dado.getPlaca().trim().replace(" ", "");
				if (placa.length()==7) {
					placa = placa.substring(0, 3) + "-" + placa.substring(3);
				}
				pos.setPlaca(placa);
				pos.setNome(dado.getNome().toUpperCase());
				pos.setTipo(dado.getTipo());
				pos.setData(dado.getData().length() == 0 ? null : toDate(dado.getData(), "dd/MM/yyyy HH:mm:ss"));
				pos.setDataIni(dado.getDataini().length() == 0 ? null : toDate(dado.getDataini(), "dd/MM/yyyy HH:mm:ss"));
				pos.setDataFim(dado.getDatafim().length() == 0 ? null : toDate(dado.getDatafim(), "dd/MM/yyyy HH:mm:ss"));
				pr.save(pos);
			}
		}
		log.info("SavePosition(FIM)");
	}

	public Timestamp toDate(String strDate, String mask) {
		try {
			SimpleDateFormat sdf1 = new SimpleDateFormat(mask);
			java.util.Date utlDate;
			utlDate = sdf1.parse(strDate);
			Timestamp sqlDate = new java.sql.Timestamp(utlDate.getTime());
			return sqlDate;
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return null;
	}
	
	public void getPeriodo() {
		log.info("GetPeriodo...");
		log.info(profile);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar c = Calendar.getInstance();
		if (profile.equalsIgnoreCase("prd")) {
			c.add(Calendar.HOUR_OF_DAY, -2);
		}
		this.dataBD = pr.getHoraDB();
		this.dataFim = sdf.format(c.getTime());
		c.add(Calendar.MINUTE, -30);
		this.dataIni = sdf.format(c.getTime());
		log.info("Data Ini:" + this.dataIni);
		log.info("Data Fim:" + this.dataFim);
		log.info("Data DB :" + this.dataBD);
	}

	public void controle(boolean lock) {
		Optional<Controle> optControle = cr.findById((long) 1);
		Controle controle = optControle.get();
		controle.setLastUpdate(toDate(this.dataFim, "yyyy-MM-dd HH:mm:ss"));
		controle.setLocado(String.valueOf(lock));
		cr.save(controle);
		if (profile.equalsIgnoreCase("prd")) {
			this.lastUpDate = controle.getHoraPrd();
		}else {
			this.lastUpDate = controle.getLastUpdate();
		}
		this.lock = controle.getLocado().equalsIgnoreCase("true") ? true : false;
	}
	
}

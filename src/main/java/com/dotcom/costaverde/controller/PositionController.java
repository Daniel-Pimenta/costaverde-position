package com.dotcom.costaverde.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.dotcom.costaverde.model.Position;
import com.dotcom.costaverde.service.PositionService;

@Controller
@RequestMapping(value="/api")
@CrossOrigin(origins="*")
public class PositionController {
	
	@Autowired
	PositionService ps;
	
	Position position;
	
	ModelAndView mv;
	
	private static final Logger log = LoggerFactory.getLogger(PositionController.class);
	
	@GetMapping("/hoje")
	public ModelAndView hoje() {
		log.info("api/hoje");
		List<Position> positions = ps.getPosition();
		mv = new ModelAndView("hoje");
		mv.addObject("size",positions.size());
		mv.addObject("positions",positions);
	  return mv;
	}

	@GetMapping("/mapa")
	public ModelAndView mapa() {
		log.info("api/mapa");
		List<Position> positions = ps.getPosition();
		mv = new ModelAndView("mapaLeaflet");
		mv.addObject("positions",positions);
		mv.addObject("placa","TODOS");
	  return mv;
	}
	
	@GetMapping("/mapa/{placa}")
	public ModelAndView mapaOnibus(@PathVariable("placa") String placa) {
		log.info("api/mapa/");
		List<Position> positions = ps.getOnibus(placa);
		mv = new ModelAndView("mapaLeaflet");
		mv.addObject("positions",positions);
		mv.addObject("placa",placa);
	  return mv;
	}

	
}

package com.dotcom.costaverde.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@CrossOrigin(origins="*")
public class IndexController {

	private static final Logger log = LoggerFactory.getLogger(IndexController.class);
	
	@GetMapping("/")
	public String index() {
		log.info("/");
	  return "index";
	}
	
}

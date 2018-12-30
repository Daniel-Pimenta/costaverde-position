package com.dotcom.costaverde.service;

import org.springframework.beans.factory.annotation.Autowired;

public class ThreadService {

	private PositionService ps;
	
	public void inicio() {
		new Thread() {    
			@Override
			public void run() {
				try {
				while (true) {
					System.out.println("Acordei...");
					ps = new PositionService();
					ps.getPeriodo();
					ps.webService();
					break;
				}
				}catch(Exception e) {
					System.out.println("Deu ruim..."+e.getMessage());
				}
			}
		}.start();
	}
	
}

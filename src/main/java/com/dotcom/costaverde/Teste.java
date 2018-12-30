package com.dotcom.costaverde;

public class Teste {

	public static void main(String[] args) {
		Thread thr1;
		thr1 = Thread.currentThread();
		System.out.println ("INI");
		try { Thread.sleep(5000); } catch (InterruptedException ex) {
		    System.out.println ("Puxa, estava dormindo! Você me acordou");
		}
		thr1.interrupt();
		System.out.println ("FIM");
	}

}

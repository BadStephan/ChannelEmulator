package com.konka.wgsh.channel;

public class ParameterException extends Exception {
	
	private static final long serialVersionUID = 8772134960420665591L;
	
	private String msg;
	public ParameterException(){
		msg = "Parameter specified was illegal.";
	}
	public String toString(){
		return msg;
	}
}

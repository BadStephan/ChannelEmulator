package com.konka.wgsh.channel;

public class MainActivity {
	public static void main(String[] args){
		Thread t = new Thread(new UserFrame());
		
		t.run();
	}
}
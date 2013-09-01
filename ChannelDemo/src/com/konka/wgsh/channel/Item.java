package com.konka.wgsh.channel;

public abstract class Item {
	
	protected int index;
	protected double freq;
	protected boolean visible;
	
	public Item(int i, double f, boolean v){
		index = i;
		freq = f;
		visible = v;
	}
	
	public void setFreq(double f) {
		freq = f;
	}
	public double getFreq(){
		return freq;
	}
	public void setIndex(int i){
		index = i;
	}
	public int getIndex(){
		return index;
	}
	public void setVisible(boolean b){
		visible = b;
	}
	public boolean getVisible(){
		return visible;
	}
}

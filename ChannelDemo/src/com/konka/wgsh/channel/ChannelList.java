package com.konka.wgsh.channel;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ChannelList{
	private ChannelUnit[] chUserList;
	private final int CHANNEL_MAX = 100;

	private final String archiveName = "res/channel script.txt";
	
	private int currIndex = 0;
	private int prevIndex = currIndex;
	
	private String _history = "";
	
	class ModifyTrace{
		int flag = 0;
		int[] pos;
	}
	
	// enables a modify-flag:
	// 0 for none
	// 1 for swapped
	// 2 for deleted
	private ModifyTrace modifying = new ModifyTrace();
	
	public ChannelList(){
		initialize();
	}
	
	private void initialize() {
		chUserList = new ChannelUnit[CHANNEL_MAX];
		for(int i = 0; i < CHANNEL_MAX; i++){
			chUserList[i] = new ChannelUnit();
		}
		
		// TODO to implement frequency auto-search functionality
		
		if(!(new File(archiveName)).exists()){
			System.out.println("Auto searching ...\n");
			System.out.println("[Completed!]");
		}
		else{
			load();
		}
	}

	public void swapChannel(int l, int r){
		ChannelUnit t = chUserList[r].copy();
		
		chUserList[r].setFreq(chUserList[l].getFreq());
		chUserList[r].setVisible(chUserList[l].getVisible());
		chUserList[l].setFreq(t.getFreq());
		chUserList[l].setVisible(t.getVisible());
		
		modifying.flag = 1;
		modifying.pos = new int[2];
		modifying.pos[0] = l;
		modifying.pos[1] = r;
		
		_history += "Channel[" + l + "] and channel[" + r + "] were swapped!\n";
	}
	
	public void deleteChannel(){
		chUserList[currIndex].setVisible(false);
		
		modifying.flag = 2;
		modifying.pos = new int[1];
		modifying.pos[0] = currIndex;
		
		_history += "Channel[" + currIndex + "] was deleted!\n";
	}
	
	private void deleteChannel(int i){
		if(i != currIndex){
			chUserList[i].setVisible(false);
			
			modifying.flag = 2 ;
			modifying.pos = new int[1];
			modifying.pos[0] = i;
			
			_history += "Channel[" + i + "] was deleted!\n"; 
		}
		else{
			deleteChannel();
		}
	}
	
	public void restoreChannel(int i){
		if(!chUserList[i].getVisible()){
			chUserList[i].setVisible(true);
			
			_history += "Channel[" + i + "] was restored!\n";
		}
	}
	
	public void undo(){
		switch(modifying.flag){
		case 1:				// swapped
			int l, r;
			l = modifying.pos[0];
			r = modifying.pos[1];
			
			ChannelUnit t = chUserList[r].copy();
			chUserList[r].setFreq(chUserList[l].getFreq());
			chUserList[r].setVisible(chUserList[l].getVisible());
			chUserList[l].setFreq(t.getFreq());
			chUserList[l].setVisible(t.getVisible());
			
			_history += "Channel[" + l + "] and channel[" + r + "] were swapped!\n";
			
//			modifying = new ModifyTrace();
//			save();
			return;
			
		case 2:				// deleted
			int i = modifying.pos[0];
			chUserList[i].setVisible(true);
			
			_history += "Channel[" + i + "] was restored!\n";
			
//			modifying = new ModifyTrace();
			return;
			
			default:
				return;
		}
	}
	
	public void back(){
		if(currIndex != prevIndex){
			gotoChannel(prevIndex);
		}
	}
	
	public void gotoChannel(int i) {
		prevIndex = currIndex;
		currIndex = i;
//		play();
	}
	
	public void play(){
		// Be sure that channel of currIndex exists.
		while(!chUserList[currIndex].getVisible())
			currIndex++;
		System.out.println("µ±Ç°ÆµµÀ£º");
		System.out.println(chUserList[currIndex].info());
		
		// playing current channel
//		while(true);
	}

	private void load(){
		try {
			BufferedReader br = new BufferedReader(new FileReader(archiveName));
			
			String str;
			int i = 0;
			
			while((str = br.readLine()) != null){
				chUserList[i].stringTo(str);
				i++;
			}
			
			br.close();
		}
		catch(IOException e){
			e.printStackTrace();
		}
		
	}
	
	private void save(){
		String buffer = "";
		int i = 0;
		while(i < CHANNEL_MAX){
			buffer += chUserList[i++].toString();
		}
		
		
		try{
			FileWriter fw = new FileWriter(archiveName, /*true*/ false);
			fw.write(buffer);
			fw.close();
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public void exit(boolean s){
		if(s){
			save();
		}
	}
	
	public String toString(){
		String ret = "";
		for(int i = 0; i < chUserList.length; i++){
			if(chUserList[i].getVisible())			// Invisible channels should't be apparent.
				ret += chUserList[i].getIndex() +"\t" + chUserList[i].getFreq() + "\n";
		}
		return ret;
	}
	
	public String history(){
		return _history;
	}
	
	public void showList(){
		System.out.print(toString());
	}
	
	public boolean isChanged(){
		return modifying.flag != 0;
	}
	
	public boolean channelExist(int i){
		if(0 <= i && i < chUserList.length){
			if(chUserList[i].getVisible()){
				return true;
			}
		}
		
		return false;
	}
}


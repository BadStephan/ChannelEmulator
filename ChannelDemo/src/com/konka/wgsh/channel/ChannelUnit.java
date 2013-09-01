package com.konka.wgsh.channel;

public class ChannelUnit extends Item {

	public ChannelUnit(){
		super(0, 870.5, false);
	}
	
	public void setFreq(double f) {
		try{
			if(f < 0.01)
				throw new ParameterException();
		}
		catch(ParameterException e){
			System.out.println(e);
			
		}

		super.setFreq(f);
	}
	
	public String toString(){
		String str ;
		str = index + ":" + freq + ":" + visible + "\n";
		return str;
	}
	
	public String info(){
		String str = null;
		if(visible){
			str = "Channel: " + index + "\t\t" + "Frequency: " + freq + "\n";
		}
		return str;
	}
	
	public ChannelUnit copy(){
		ChannelUnit ret = new ChannelUnit();
		ret.index = this.index;
		ret.freq = this.freq;
		ret.visible = this.visible;
		return ret;
	}
	
	public void stringTo(String str){		// default delimiter (:)
		int start = 0, i = 0, j = 0;
		
		String[] t = new String[3];			// three variable members in ChannelUnit
		for(; j < t.length; j++){
			t[j] = "";
		}
			
		char ch;
		for(;start < str.length(); start++){
			ch = str.charAt(start);
			
			if(ch == ':'){
				i++;
				continue;
			}
			if(ch!=' ')
				t[i] += ch;			
		}
		
		index = Integer.parseInt(t[0]);	//字符窜的右边必须不能存在空格，否则解析错误。
		freq = Double.parseDouble(t[1]);
		visible = Boolean.parseBoolean(t[2]);
//		visible = (Integer.parseInt(t[2])==1?true:false);
	}
}

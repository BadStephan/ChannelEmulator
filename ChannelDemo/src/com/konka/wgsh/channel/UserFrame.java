package com.konka.wgsh.channel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.StringBuffer;

// this class can be replaced by real graphic interface
public class UserFrame implements Runnable {
	private final static int screenWide = 80;		// unit: a normal character width
	private final static String sharpLine = 	"################################################################################";
//	private final static String blankLine = 	"                                                                                ";
	private final static String menu = 			"    0.��ʾƵ���б�\n" +
												"    1.��ת��\n" +
												"    2.����\n" +
												"    3.����Ƶ��\n" +
												"    4.���б���ɾ��Ƶ��\n" +
												"    5.�ָ��б���Ƶ��\n" +
												"\n" +
												"    7.��ʾ�޸ļ�¼\n" +
												"    8.�����ϴ��޸�\n" +
												"    9.�˳�\n\n��ѡ��˵�����\n\n";
	private ChannelList list = new ChannelList();
	
//	private void printLn(){
//		System.out.println();
//	}
	public static void printLn(){
		 System.out.println();
	}
	
//	TODO ���ǵ���Ӣ��ȵĲ�ͬ���ú���������ʵ�֡��̶��п�ȡ��Ĺ���
	public static void printSharpLnFormat(Object... objs){
		String str = "";
		for(int i = 0; i < objs.length; i++)
			str += objs[i].toString();
		StringBuffer buf = new StringBuffer(screenWide);
		buf.append(sharpLine);
		int start = (screenWide-str.length())/2;
		buf.replace(start, start + str.length(), str);
		
		System.out.println(buf);
	}

	private void printHeader(){
		printLn();
		printLn();
		printLn();
		printLn();
		printSharpLnFormat();
		printSharpLnFormat("ģ��Ƶ���նˡ���������");
		printSharpLnFormat();
		printLn();
		printLn();
	}
	
	private void printMenu(){
		System.out.print(menu);
	}
	
	public void run(){
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		String str_ans = null, str_res = null;
		int ans = -1, reserved = -1;
		printHeader();
		
		while(true){
			list.play();
			
			try {
				reader.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			printMenu();
			
			try {
//				do{
					str_ans = reader.readLine();
//				}while(str_ans==null);
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(str_ans == null)
				continue;
			
			ans = Integer.parseInt(str_ans);
			switch(ans){
			// ��ʾ�б�
			case 0:
				list.showList();
				break;
				
			// ��ת��
			case 1:
				try {
					System.out.print("������ת���ĸ���Ŀ����0-99��\n");
					
//					do{
						str_ans = reader.readLine();
//					}while(str_ans==null);
						
						if(str_ans == null)
							break;
					ans = Integer.parseInt(str_ans);
					
					if(list.channelExist(ans)){
						list.gotoChannel(ans);
					}
					
					break;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			// ����
			case 2:
				list.back();
				break;
				
			// ����Ƶ��
			case 3:
				System.out.print("��������Ҫ����������Ƶ���š�0-99��:\n");
				
				try{
//					do{
						str_ans = reader.readLine();
//					}while(str_ans==null);
				} catch(IOException e){
					e.printStackTrace();
				}
				
				if(str_ans == null)
					break;
				ans = Integer.parseInt(str_ans);
				if(!list.channelExist(ans))
					break;
				
				try{
//					do{
						
						str_res = reader.readLine();
//					}while(str_res==null);
				}
				catch(IOException e){
					e.printStackTrace();
				}
				
				if(str_res == null)
					break;
				reserved = Integer.parseInt(str_res);
				if(!list.channelExist(reserved))
					break;
				
				list.swapChannel(ans, reserved);
				break;
				
			// ɾ��Ƶ��
			case 4:
				list.deleteChannel();
				break;
			case 5:
				System.out.print("��������Ҫ�ָ���Ƶ����\n");
				
				try{
					str_ans = reader.readLine();
				}
				catch(IOException e){
					e.printStackTrace();
				}
				
				if(str_ans==null)
					break;
				ans = Integer.parseInt(str_ans);
				list.restoreChannel(ans);
				break;
				
				//��ʾ�޸ļ�¼
			case 7:
				System.out.print(list.history());
				break;
			
			// �����޸�
			case 8:
				list.undo();
				break;
				
			// �˳�
			case 9:

				//TODO
				if(list.isChanged()){
					System.out.print("Ƶ���б��Ѿ����޸ģ��Ƿ񱣴��޸ļ�¼��\n����0����ʾ�����޸Ĳ��˳�����1����ʾ���沢�˳���\n");
					
					try {
//						do{
							str_ans = reader.readLine();
//						}while(true);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					if(str_ans == null)
						break;
					ans = Integer.parseInt(str_ans);
					
					list.exit(ans == 0?false:true);
				}
				
				printSharpLnFormat();
				printSharpLnFormat("ģ��Ƶ���նˡ����˳���");
				printSharpLnFormat();
				
				return;
				
				default:
			}
		}
	}
}

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
	private final static String menu = 			"    0.显示频道列表\n" +
												"    1.跳转到\n" +
												"    2.返回\n" +
												"    3.交换频道\n" +
												"    4.从列表中删除频道\n" +
												"    5.恢复列表中频道\n" +
												"\n" +
												"    7.显示修改记录\n" +
												"    8.撤销上次修改\n" +
												"    9.退出\n\n请选择菜单操作\n\n";
	private ChannelList list = new ChannelList();
	
//	private void printLn(){
//		System.out.println();
//	}
	public static void printLn(){
		 System.out.println();
	}
	
//	TODO 考虑到中英宽度的不同，该函数并不能实现“固定行宽度”的功能
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
		printSharpLnFormat("模拟频道终端【已启动】");
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
			// 显示列表
			case 0:
				list.showList();
				break;
				
			// 跳转到
			case 1:
				try {
					System.out.print("您想跳转到哪个节目？【0-99】\n");
					
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
				
			// 返回
			case 2:
				list.back();
				break;
				
			// 交换频道
			case 3:
				System.out.print("请输入需要交换的两个频道号【0-99】:\n");
				
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
				
			// 删除频道
			case 4:
				list.deleteChannel();
				break;
			case 5:
				System.out.print("请输入需要恢复的频道：\n");
				
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
				
				//显示修改记录
			case 7:
				System.out.print(list.history());
				break;
			
			// 撤销修改
			case 8:
				list.undo();
				break;
				
			// 退出
			case 9:

				//TODO
				if(list.isChanged()){
					System.out.print("频道列表已经被修改，是否保存修改记录？\n【“0”表示放弃修改并退出，“1”表示保存并退出】\n");
					
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
				printSharpLnFormat("模拟频道终端【已退出】");
				printSharpLnFormat();
				
				return;
				
				default:
			}
		}
	}
}

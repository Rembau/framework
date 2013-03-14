package org.rembau.command;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

import org.apache.log4j.Logger;
import org.rembau.command.Context;


public class CommandClient extends Thread{
	private static final Logger logger = Logger.getLogger(CommandClient.class);
	private String cmd="";
	Socket socket = null;
	
	public CommandClient(){}
	public CommandClient(String str){
		this.cmd=str;
	}
	public void run(){
		try {
			socket = new Socket("127.0.0.1",Context.CMD_S_PORT);
		} catch (UnknownHostException e) {
			System.out.println(e.getMessage());
		} catch (IOException e) {
			if(e.getMessage().indexOf("Connection refused")!=-1){
				logger.info("Server is stoped,please run \"classname start\",startup the programe.");
			}
			System.out.println(e.getMessage());
			System.exit(0);
		}
		if(cmd.length()==0){
			loop();
		} else {
			sendMessage();
		}
	}
	public void sendMessage(){
		DataOutputStream writeToSocket=null;
		BufferedReader readFromSocket=null;
		try {
			writeToSocket=new DataOutputStream(socket.getOutputStream());
			readFromSocket = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			new ShowFeedback(readFromSocket).start();
			writeToSocket.writeBytes(cmd+"\n");
			try {
				Thread.sleep(10000); 
				//pause ten seconds unless receive the receipt,then exit.
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} catch (UnknownHostException e) {
			System.out.println(e.getMessage());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		} finally{
			try {
				writeToSocket.close();
				writeToSocket=null;
				socket.close();
				socket=null;
			} catch (IOException e) {
			}
		}
	}
	public void loop(){
		DataOutputStream writeToSocket=null;
		BufferedReader readFromSocket=null;
		BufferedReader readFromSystem=null;
		try {
			writeToSocket = new DataOutputStream(socket.getOutputStream());
			readFromSocket = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			new ShowFeedback(readFromSocket).start();
			readFromSystem = new BufferedReader(new InputStreamReader(System.in));
			String cmd=readFromSystem.readLine();
			
			while(true){
				cmd=cmd.trim();
				if(!cmd.equals("")){
					writeToSocket.writeBytes(cmd+"\n");
				} else {
					System.out.print(">");
				}
				cmd =readFromSystem.readLine();
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			try {
				writeToSocket.close();
				readFromSystem.close();
				writeToSocket=null;
				readFromSystem=null;
				socket.close();
				socket=null;
			} catch (IOException e) {
			}
			System.exit(0);
		}
	}
	class ShowFeedback extends Thread{
		BufferedReader readFromSocket;
		ShowFeedback(BufferedReader readFromSocket){
			this.readFromSocket=readFromSocket;
		}
		public void run(){
			String feedbackStr;
			try {
				while((feedbackStr=readFromSocket.readLine())!=null){
					System.out.println(feedbackStr);
					if(feedbackStr.equals("user leaved!") || feedbackStr.equals("server's message:server is stoped!")){
						break;
					}
					System.out.print(">");
				}
			} catch (IOException e) {
				System.out.println(e.getMessage());
			} finally{
				try {
					readFromSocket.close();
				} catch (IOException e) {
				}
				System.exit(0);
			}
		}
	}
	public static void main(String[] args) {
		new CommandClient().run();
	}
}

package org.rembau.command;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import org.apache.log4j.Logger;
import org.rembau.Context;

public class CommandServer extends Thread{ 
	private static final Logger logger = Logger.getLogger(CommandServer.class);
	private CommandHandle ch;
	public void run(){
		ServerSocket ss=null;
		try {
			 ss =new ServerSocket(Context.CMD_S_PORT);
			 System.out.println("server is started!");
			 logger.info("server is started!");
		} catch (IOException e) {
			if(e.getMessage().indexOf("Address already in use")!=-1){
				String str ="port already in use.\n" +
						"Other program take up the port,or this program is started many time!\n" +
						"run the command \"classname status\"check up.\n" +
						"if you want run the program many time,please change the conf/extra.properties.port's value.";
				System.out.println(str);
				logger.info(str);
				System.exit(0);
			}else {
				System.out.println(e.getMessage());
				logger.info(e.getMessage());
			}
			return;
		}
		while(true){
			Socket socket=null;
			try {
				socket = ss.accept();
				if(Context.NUMOFCLIENT==0){
					new ConnectionSocket(socket).start();
					Context.NUMOFCLIENT++;
				} else{
					new DataOutputStream(socket.getOutputStream()).
						writeBytes("A user is connecting already!Remote Ip is "+socket.getRemoteSocketAddress()+".\n");
					socket.close();
				}
			} catch (IOException e1) {
				e1.printStackTrace();
				logger.info(e1.getMessage());
			}
		}
	}
	class ConnectionSocket extends Thread{
		private Socket socket;
		ConnectionSocket(Socket socket){
			this.socket=socket;
		}
		public void run(){
			BufferedReader readeFromSocket=null;
			DataOutputStream writeToSocket = null;
			try {
				readeFromSocket=new BufferedReader(new InputStreamReader(socket.getInputStream()));
				writeToSocket=new DataOutputStream(socket.getOutputStream());
				writeToSocket.writeBytes("Connect Accessed!\n");
				logger.info("Connect accessed!");
				String command="";
				while((command=readeFromSocket.readLine())!=null){
					System.out.println(command);
					logger.info("command of user:"+command);
					if(command.equals(Context.CMD_STOP)){
						writeToSocket.writeBytes("server's message:server is stoped!"+"\n");
						logger.info("server's message:server is stoped!");
						System.exit(0);
					} else if(command.equals(Context.CMD_EXIT)){
						writeToSocket.writeBytes("user leaved!\n");
						logger.info("user leaved!");
						break;
					} else {
						if(ch!=null){
							logger.info("Do not have CommandHandle yet!plase stop the server and repair.");
							ch.excute(command);
						}
						
					}
					writeToSocket.writeBytes("command of user:"+command+"\n");
				}
			} catch (IOException e) {
				System.out.println(e.getMessage());
			} finally{
				Context.NUMOFCLIENT=0;
				try {
					readeFromSocket.close();
					readeFromSocket=null;
					socket.close();
					socket=null;
				} catch (IOException e) {
				}
			}
		}
	}
	/**
	 * if you want to configuration own commands,you must inherit the Class CommandHandle and register.
	 */
	public void registerCommandHandle(CommandHandle ch){
		this.ch=ch;
	}
	public static void main(String[] args) {
		new CommandServer().start();
	}
}

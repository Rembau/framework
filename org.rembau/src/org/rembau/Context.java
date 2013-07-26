package org.rembau;

public class Context {
	public static int NUMOFCLIENT=0; //当前客户端连接数
	
	public static int CMD_S_PORT=2000;
	public static String CMD_START="start";
	public static String CMD_STOP="stop";
	public static String CMD_EXIT="exit";
	public static String CMD_EXCUTE="excute";
	public static String CMD_CLIENT="client";
	public int getNUMOFCLIENT() {
		return NUMOFCLIENT;
	}
	public void setNUMOFCLIENT(int nUMOFCLIENT) {
		NUMOFCLIENT = nUMOFCLIENT;
	}
	public int getCMD_S_PORT() {
		return CMD_S_PORT;
	}
	public void setCMD_S_PORT(int cMD_S_PORT) {
		CMD_S_PORT = cMD_S_PORT;
	}
	public String getCMD_START() {
		return CMD_START;
	}
	public void setCMD_START(String cMD_START) {
		CMD_START = cMD_START;
	}
	public String getCMD_STOP() {
		return CMD_STOP;
	}
	public void setCMD_STOP(String cMD_STOP) {
		CMD_STOP = cMD_STOP;
	}
	public String getCMD_EXIT() {
		return CMD_EXIT;
	}
	public void setCMD_EXIT(String cMD_EXIT) {
		CMD_EXIT = cMD_EXIT;
	}
	public String getCMD_EXCUTE() {
		return CMD_EXCUTE;
	}
	public void setCMD_EXCUTE(String cMD_EXCUTE) {
		CMD_EXCUTE = cMD_EXCUTE;
	}
	public String getCMD_CLIENT() {
		return CMD_CLIENT;
	}
	public void setCMD_CLIENT(String cMD_CLIENT) {
		CMD_CLIENT = cMD_CLIENT;
	}

}

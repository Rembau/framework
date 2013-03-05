package org.rembau.command;

public class CommandStopException extends Exception {
	private static final long serialVersionUID = -7632853665450509261L;
	public String getMessage(){
		return "CommandServer is stoped!";
	}
}

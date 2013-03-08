package org.rembau.command;

import org.apache.log4j.Logger;
import org.rembau.command.CommandClient;
import org.rembau.command.CommandServer;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 
 * program entrance.
 * @author Rembau
 *
 */
public class MainClass {
	private static final Logger logger = Logger.getLogger(MainClass.class);

	public static void main(String[] args) {
		new ClassPathXmlApplicationContext(
				new String[] { "classpath:applicationContext.xml" });
		Context.init(); // initialize context
		for (int i = 0; i < args.length; i++) {
			System.out.println(args[i]);
		}
		//1:no param 2:start,starttup service 3:isn't start,entry client and exit after send the command 
		if (args.length == 0) {
			new CommandClient().start();
		} else if (args[0].trim().equals(Context.CMD_START)) {
			logger.info("Server start");
			new CommandServer().start();
		} else {
			new CommandClient(args[0]).start();
		}
	}
}

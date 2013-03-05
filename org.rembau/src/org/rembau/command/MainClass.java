package org.rembau.command;

import org.apache.log4j.Logger;
import org.rembau.command.CommandClient;
import org.rembau.command.CommandServer;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 程序入口
 * 
 */
public class MainClass {
	private static final Logger logger = Logger.getLogger(MainClass.class);

	public static void main(String[] args) {
		new ClassPathXmlApplicationContext(
				new String[] { "classpath:applicationContext.xml" });
		Context.init(); // 全局上下文 初始化。
		for (int i = 0; i < args.length; i++) {
			System.out.println(args[i]);
		}
		//1:无参数，进入客户端  2：start 启动服务  3：非start，带参数进入客户端，发送成功直接退出
		if (args.length == 0) {
			new CommandClient().start();
		} else if (args[0].trim().equals(Context.CMD_START)) {
			logger.info("程序启动");
			new CommandServer().start();
		} else {
			new CommandClient(args[0]).start();
		}
	}
}

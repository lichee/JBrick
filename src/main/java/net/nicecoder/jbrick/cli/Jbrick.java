package net.nicecoder.jbrick.cli;

import net.nicecoder.jbrick.model.App;
import net.nicecoder.jbrick.server.BrickServer;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.PosixParser;

public class Jbrick {
	
	private static String DEFAULT_CONFIG_FILE = "_app.yml";
	
	public static void main(String[] args){

		App app = null;
		
		Options options = new Options();
		options.addOption("n", "new", false, "new a app");
		options.addOption("b", "build", false, "build app");
		options.addOption("s", "server", false, "server the app");
		options.addOption("w", "watch", false, "watch app changes");
		options.addOption("p", "port", true, "server port");
		options.addOption("h", "help", false, "server port");
		
		HelpFormatter help = new HelpFormatter();
		
		try{
			CommandLineParser parser = new PosixParser();
			CommandLine cmd = parser.parse(options, args);
			
			if(cmd.hasOption("b")){
				app = new App();
				app.load(DEFAULT_CONFIG_FILE);
				app.generate();
			}
			
			if(cmd.hasOption("s")){
				if(app==null){
					app = new App();
					app.load(DEFAULT_CONFIG_FILE);
				}
				int port = 8080;
				try{
					String portStr = cmd.getOptionValue("p");
					if(portStr!=null){
						port = Integer.valueOf(portStr);
					}
				}catch(Exception e){
					System.out.println("错误的数字输入");
				}
				BrickServer server = new BrickServer(port, app);

				System.out.println( "Listening on port "+port+". Hit Enter to stop.\n" );
				try { System.in.read(); } catch( Throwable t ) {};
			}
			
			if(cmd.hasOption("h")){
				help.printHelp("", options);
			}
			
		}catch(Exception e){
			e.printStackTrace();
			help.printHelp("", options);
		}
		
	}
}

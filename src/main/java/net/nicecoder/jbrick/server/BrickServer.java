package net.nicecoder.jbrick.server;

import java.io.File;
import java.io.IOException;

import net.nicecoder.jbrick.model.App;

/**
 * simple server.
 */
public class BrickServer extends NanoHTTPD
{	
	private App app;
	private int port;
	
	public BrickServer(int port,App app) throws IOException
	{
		super(port, new File(app.getOutputDir()));
		this.app = app;
		this.port = port;
	}
	
	public void service(){
		System.out.println( "Listening on port "+port+". Hit Enter to stop.\n" );
		try { System.in.read(); } catch( Throwable t ) {};
	}
}

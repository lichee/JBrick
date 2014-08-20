package net.nicecoder.jbrick;

import java.io.IOException;

import net.nicecoder.jbrick.model.App;
import net.nicecoder.jbrick.server.BrickServer;

import org.junit.Test;
import org.pegdown.PegDownProcessor;

public class AppTest {

	/*@Test
	public void test() {
		App app = new App();
		app.load("C:\\Users\\Administrator\\Desktop\\jbrick\\_app.yml");
		app.generate();
		
		try {
			BrickServer server = new BrickServer(8082, app);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println( "Listening on port "+8082+". Hit Enter to stop.\n" );
		try { System.in.read(); } catch( Throwable t ) {};
	}
	
	@Test
	public void md(){
		PegDownProcessor p = new PegDownProcessor();
		String s = p.markdownToHtml("1. yyy\n2. sgsr\n");
		System.out.println(s);
	}*/
	
}

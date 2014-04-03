package Network;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.LinkedBlockingQueue;

import Utils.*;

/**
 * Thread that handles the connection to the server and receives data from it.
 * 
 * @author Bernd
 * 
 */
public class WebSender implements Runnable {
	private LinkedBlockingQueue<Position> data;
	private URL targetURL;
	HttpURLConnection con;
	String id;

	public WebSender(String targetURL, LinkedBlockingQueue<Position> data, String id) {
		this.id = id;
		this.data = data;
		try {
			this.targetURL = new URL(targetURL);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		boolean noProblems = true;
		Position currentPos;
		
		while (noProblems) {
			try {
				currentPos = data.take();
				
				con = (HttpURLConnection) targetURL.openConnection();
				con.setRequestMethod("POST");
				con.setRequestProperty("User-Agent", "wlanScanner");
				con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
				con.setDoOutput(true);


				String urlParameters = "x="+currentPos.getX()+"&y="+currentPos.getY()+"&id="+id;

				// Send post request
				
				DataOutputStream wr = new DataOutputStream(con.getOutputStream());
				wr.writeBytes(urlParameters);
				wr.flush();
				wr.close();
				con.getResponseCode();

			} catch (IOException e) {
				System.err.println(e);
				e.printStackTrace();
				noProblems = false;
			} catch (InterruptedException e) {
				noProblems = false;
				e.printStackTrace();
			}
		}

	}
}

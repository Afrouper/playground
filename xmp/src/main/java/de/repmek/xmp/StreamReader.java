package de.repmek.xmp;

import java.io.IOException;
import java.io.InputStream;

public class StreamReader implements Runnable {

	private InputStream is;
	private boolean error;
	
	public StreamReader(InputStream is, boolean error) {
		this.is = is;
		this.error = error;
	}
	@Override
	public void run() {
		try {
			int read = 0;
			byte[] b = new byte[1024];
			while((read = is.read(b)) != -1) {
				String message = new String(b, 0, read);
				if(error) {
					System.err.println(message);
				}
				else {
					System.out.println(message);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

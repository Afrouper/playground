package de.repmek.xmp;

import java.io.File;
import java.util.logging.Logger;

public class XmpReader {

	private static Logger logger = Logger.getLogger(XmpReader.class.getName());

	public static void main(String[] args) throws Exception {
		logger.info("Start XMP Reader...");
		DirectoryChecker check = new DirectoryChecker(null);
		check.process();
	}

}

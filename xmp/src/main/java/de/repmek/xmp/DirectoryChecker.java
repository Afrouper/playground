package de.repmek.xmp;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;

public class DirectoryChecker {

	private File[] xmpFiles;

	public DirectoryChecker(File dir) {
		xmpFiles = dir.listFiles(new MyFilenameFilter());
	}
	
	public void process() {
		Arrays.stream(xmpFiles).forEach(new XmpConsumer());
	}
	
	private static final class MyFilenameFilter implements FilenameFilter {

		@Override
		public boolean accept(File dir, String name) {
			return name.toLowerCase().endsWith(".xmp");
		}
	}
}

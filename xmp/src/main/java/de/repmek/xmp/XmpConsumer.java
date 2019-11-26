package de.repmek.xmp;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

public class XmpConsumer implements Consumer<File>{

	private static final Executor executor = Executors.newFixedThreadPool(50);

	@Override
	public void accept(File xmpFile) {
		try {
			File parentFile = xmpFile.getParentFile();
			String name = xmpFile.getName().substring(0, xmpFile.getName().lastIndexOf('.'));
			File picture = new File(parentFile, name + ".NEF");
			if(!picture.exists()) {
				picture = new File(parentFile, name + ".JPG");
			}
			if(!picture.exists()) {
				return;
			}
			System.out.println("Consume: " + xmpFile.getAbsolutePath() + " with picture " + picture.getAbsolutePath());
			addExif(picture, xmpFile);
		} catch (Exception e) {
			System.err.println(xmpFile.getAbsolutePath() + ": ERROR: " + e.getMessage());
		}
	}

	private void addExif(File picture, File xmpFile) {
		try {
			Reader reader = new Reader(xmpFile);
			String latitude = reader.getLatitude();
			String longitude = reader.getLongitude();
			if(longitude != null && latitude != null) {
				writeGPS(picture, longitude, latitude);
			}
		} catch (Exception e) {
			System.err.println(xmpFile.getAbsolutePath() + ": ERROR: " + e.getMessage());
		}
	}

	private void writeGPS(File picture, String longitude, String latitude) {
		char longitudeRef = longitude.charAt(longitude.length() - 1);
		char latitudeRef = latitude.charAt(latitude.length() - 1);
		longitude = longitude.substring(0, longitude.length() - 2);
		latitude = latitude.substring(0, latitude.length() - 2);
		
		List<String> command = new ArrayList<String>();
		command.add("/usr/local/bin/exiftool");
		command.add("-P");
		command.add("-overwrite_original");
		command.add("-GPSLongitudeRef=" + longitudeRef);
		command.add("-GPSLatitudeRef=" + latitudeRef);
		command.add("-GPSLongitude=" + longitude);
		command.add("-GPSLatitude=" + latitude);
		
		executeExiftool(picture, command);
	}

	private void executeExiftool(File picture, List<String> command) {
		try {
			command.add(picture.getAbsolutePath());
			System.out.println(command);
			Process process = Runtime.getRuntime().exec(command.toArray(new String[command.size()]));
			addOutReaders(process);
			int exitValue = process.waitFor();
			System.out.println("exiftool exits with code " + exitValue);
		} catch (Exception e) {
			System.err.println(picture.getAbsolutePath() + ": ERROR executing exiftool: " + e.getMessage());
		}
	}

	private void addOutReaders(Process process) {
		StreamReader output = new StreamReader(process.getInputStream(), false);
		StreamReader error = new StreamReader(process.getErrorStream(), true);
		executor.execute(error);
		executor.execute(output);
	}
}

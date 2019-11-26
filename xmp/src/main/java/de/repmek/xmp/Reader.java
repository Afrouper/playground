package de.repmek.xmp;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.adobe.xmp.XMPException;
import com.adobe.xmp.XMPMeta;
import com.adobe.xmp.XMPMetaFactory;
import com.adobe.xmp.properties.XMPProperty;

public class Reader {

	private XMPMeta xmpMetaData;

	public Reader(File xmpFile) throws FileNotFoundException {
		xmpMetaData = parse(xmpFile);
	}

	public String getLongitude() throws XMPException {
		XMPProperty longitude = xmpMetaData.getProperty(
				"http://ns.adobe.com/exif/1.0/", "exif:GPSLongitude");
		return longitude.getValue();
	}

	public String getLatitude() throws XMPException {
		XMPProperty latitude = xmpMetaData.getProperty(
				"http://ns.adobe.com/exif/1.0/", "exif:GPSLatitude");
		return latitude.getValue();
	}

	private XMPMeta parse(File xmpFile) throws FileNotFoundException {
		BufferedInputStream is = new BufferedInputStream(new FileInputStream(
				xmpFile));
		try {
			XMPMeta meta = XMPMetaFactory.parse(is);
			return meta;
		} catch (XMPException e) {
			throw new IllegalStateException(e.getMessage(), e);
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
				}
			}
		}
	}
}

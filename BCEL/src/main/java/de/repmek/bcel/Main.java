package de.repmek.bcel;

import org.apache.bcel.classfile.ClassParser;
import org.apache.bcel.classfile.JavaClass;

/**
 * 
 * @author xgadkem
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {

			System.out.println();

			printClassVersion("C:\\TEMP\\client.jar", "de/repmek/Test.class");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void printClassVersion(String jarfile, String classfile) throws Exception {
		ClassParser cp = new ClassParser(jarfile, classfile);
		JavaClass clazz1 = cp.parse();
		System.out.println(jarfile + " -> " + classfile);
		System.out.println("Major: " + clazz1.getMajor());
		System.out.println("Minor: " + clazz1.getMinor());
	}

}

package de.repmek.jni;

public class SayHello {

	static {
		System.out.println("Load lib from classloader " + SayHello.class.getClassLoader());
		try {
			System.loadLibrary("sayHello");
		}
		catch (Throwable e) {
			System.err.println("Ignoring exception " + e);
		}
	}

	private native String sayHello(String name);

	public String say(String name) {
		return sayHello(name);
	}

	@Override
	protected void finalize() throws Throwable {
		System.err.println("I'm finalized!!!");
	}

	public static void main(String[] args) {
		try {
			SayHello hello = new SayHello();
			for (int i = 0; i < 50; ++i) {
				hello.say("Roundtrip call " + i);
			}
			System.out.println(hello.sayHello("Lutz"));
//			Thread.sleep(5000);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}

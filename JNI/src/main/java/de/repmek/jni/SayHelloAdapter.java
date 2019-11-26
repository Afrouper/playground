package de.repmek.jni;

public class SayHelloAdapter {

	private final SayHello hello = new SayHello();

	public String sayHello(String name) {
		return hello.say(name);
	}
}

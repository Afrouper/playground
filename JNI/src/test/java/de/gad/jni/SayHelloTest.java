package de.gad.jni;


import org.junit.Assert;
import org.junit.Test;

public class SayHelloTest {

    @Test
    public void testHello() throws Exception
    {
        SayHello hello = new SayHello();
        String spoken = hello.say("Lutz");
        Assert.assertEquals("Hello Lutz from the native code!", spoken);
    }
}
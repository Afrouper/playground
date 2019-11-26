#include "de_gad_jni_SayHello.h"

#include <string>

using namespace std;

JNIEXPORT jstring JNICALL Java_de_gad_jni_SayHello_sayHello(JNIEnv* env, jobject obj, jstring name) {

	const char* c_str = env->GetStringUTFChars(name, NULL);
	string c_name(c_str);

	env->ReleaseStringUTFChars(name, c_str);

	c_name = "Hello " + c_name + " from the native code!";

	return env->NewStringUTF(c_name.c_str());
}

package demo.urlClassLoader;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

// This class loader is used to load classes and resources from a search path of URLs referring to both JAR files and directories.
// which means we can:
// 1. refer to the directory that the .class file is in.
// 2. put the .class file into a JAR and refer to that.

public class SimpleURLClassLoader {
	public static void main(String[] args) {
		
		try {
			
			// using 'loadClass()' //
			
			URL url1 = new File("myLib.jar").toURI().toURL();
			URLClassLoader ucl1 = new URLClassLoader(new URL[]{url1});
			Class clazz1 = ucl1.loadClass("Foo");
			// cast the instance to its interface.
			MyInterface obj1 = (MyInterface)clazz1.newInstance();
			obj1.invoke();
			
			// using 'forName()' //
			
			URL url2 = new File("myLib.jar").toURI().toURL();
			URLClassLoader ucl2 = new URLClassLoader(new URL[]{url2});
			Class clazz2 = Class.forName("Foo", true, ucl2);
			MyInterface obj2 = (MyInterface)clazz2.newInstance();
			obj2.invoke();
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		
	}
}

package demo.urlClassLoader;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

public class SimpleURLClassLoader {
	public static void main(String[] args) {
		
		URL url;
		try {
			url = new File("myLib.jar").toURI().toURL();
			URLClassLoader ucl = new URLClassLoader(new URL[]{url});
			Class clazz = ucl.loadClass("Foo");
			Object o = clazz.newInstance();
			System.out.println(o.toString());
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

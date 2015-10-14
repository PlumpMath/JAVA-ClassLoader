package demo.urlClassLoader;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

import company.parsers.IParser;

// This class loader is used to load classes and resources from a search path of URLs referring to both JAR files and directories.
// which means we can:
// 1. refer to the directory that the .class file is in.
// 2. put the .class file into a JAR and refer to that.

public class SimpleURLClassLoader {
	public static void main(String[] args) {
		
		try {
			
			// using 'loadClass()' //
			
			URL url1 = new File("/home/eryoav/Desktop/parserLib.jar").toURI().toURL();
			URLClassLoader ucl1 = new URLClassLoader(new URL[]{url1});
			Class<IParser> clazz1 = (Class<IParser>) ucl1.loadClass("GreetingParser");
			// cast the instance to its interface.
			IParser parser1 = (IParser)clazz1.newInstance();
			System.out.println(parser1.parse("Bob")); // -> will output "Hello Bob"
			
			// using 'forName()' //
			
			URL url2 = new File("/home/eryoav/Desktop/parserLib.jar").toURI().toURL();
			URLClassLoader ucl2 = new URLClassLoader(new URL[]{url2});
			Class<IParser> clazz2 = (Class<IParser>) Class.forName("GreetingParser", true, ucl2);
			IParser parser2 = clazz2.newInstance();
			System.out.println(parser2.parse("Dan")); // -> will output "Hello Dan"
			
			System.out.printf("parser1 == parser2 %b\n", parser1 == parser2);
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		
	}
}

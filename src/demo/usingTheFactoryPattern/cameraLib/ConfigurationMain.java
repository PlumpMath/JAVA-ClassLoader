package demo.usingTheFactoryPattern.cameraLib;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;

import company.cameras.ICamera;
import company.cameras.ICameraFactory;

public class ConfigurationMain {
	public static void main(String[] args) throws IOException, ClassNotFoundException, IllegalAccessException, InstantiationException {
		
		// instantiate custom configuration class passing the first argument as the
		// configuration file location - config.json.
		Configuration configuration = Configuration.loadConfiguration(args[0]);
		
		// get the location of the external jar.
		String location = configuration.getLocation();
		URL url = new File(configuration.getLocation()).toURI().toURL();
		
		// instantiate new URLClassLoader.
		URLClassLoader ucl = new URLClassLoader(new URL[]{url});
		
		// load the factory class.
		Class<ICameraFactory> cls = (Class<ICameraFactory>) Class.forName(configuration.getFactoryType(), true, ucl);
		
		// instantiate it
		ICameraFactory cameraFactory = cls.newInstance();
		// use it to create new camera.
		ICamera camera = cameraFactory.createCamera();
		camera.takePhoto();
	}
}

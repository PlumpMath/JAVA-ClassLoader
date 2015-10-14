package demo.usingTheFactoryPattern.cameraLib;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Configuration {
	
	private String factoryType;
	private String location;
	
	public static Configuration loadConfiguration(String fileName) throws IOException {
		
		// create a String which contains all the config.json data.
		Path path = FileSystems.getDefault().getPath(fileName);
		String contents = new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
		
		// map the json format to this current Class.
		ObjectMapper mapper = new ObjectMapper();
		Configuration config = mapper.readValue(contents, Configuration.class);
		return config;
	}
	
	// getters & setters
	public String getLocation() {
		return this.location;
	}
	
	public void setLocation(String location) {
		this.location = location;
	}
	
	public String getFactoryType() {
		return this.factoryType;
	}
	
	public void setFactoryType(String factoryType) {
		this.factoryType = factoryType;
	}
}

package it.albe.jmusicman;

public class Version {
	private static final String VERSION;
	private static final String URL = "";
	
	static { // get version from JAR manifest
		String implementationVersion =  Version.class.getPackage().getImplementationVersion();
		VERSION = implementationVersion != null ? implementationVersion : "UNKNOWN-SNAPSHOT";
	}
	
	public static String asString() {
		return getVersion() + " - " + getUrl(); 
	}	

	public static String getVersion() {
		return VERSION;
	}
	
	public static String getUrl() {
		return URL;
	}
}

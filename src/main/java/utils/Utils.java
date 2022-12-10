package utils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class Utils {

	public static String read(String name) {
		try {
			byte[] bytes = Utils.class.getResourceAsStream(name).readAllBytes();
			return new String(bytes, StandardCharsets.UTF_8);
		} catch (IOException | NullPointerException ex) {
			System.err.println("Could not read file: " + name);
			ex.printStackTrace();
			System.exit(1);
			return "";
		}
	}
}

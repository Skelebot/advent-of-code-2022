package utils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class Utils {
	
	public static String readInput(String name) throws IOException {
		byte[] bytes = Utils.class.getResourceAsStream(name).readAllBytes();
		return new String(bytes, StandardCharsets.UTF_8);
	}
}

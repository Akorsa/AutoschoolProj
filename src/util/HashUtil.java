package util;

import org.apache.shiro.crypto.hash.Sha256Hash;

public class HashUtil {
	
	
	public static String simpleHash(String password) {
		  Sha256Hash sha256Hash = new Sha256Hash(password);
			String result = sha256Hash.toHex();
		  return result;
	  }
}

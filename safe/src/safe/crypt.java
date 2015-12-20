package safe;

import javax.crypto.Cipher;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;

import java.security.Key;
import java.security.InvalidKeyException;

public class crypt {

	private static String algorithm = "DES";

	private static Key key;

	private static Cipher cipher;
	
	Serialization srz = new Serialization();

	String keyName = System.getProperty("user.dir") + "/safe/key";

	String ciphName = System.getProperty("user.dir") + "/safe/cipher";

	public String encrypt(String strIn)

	throws Exception {
		
		setCipher();
		
		key = (Key) srz.deserData(keyName);
		
		cipher.init(Cipher.ENCRYPT_MODE, key);

		byte[] inputBytes = strIn.getBytes();

		cipher.doFinal(inputBytes);

		String recovered =

		new String(inputBytes);

		return recovered;

	}

	public String decrypt(String strOut)

	throws Exception {

		setCipher();
		
		key = (Key) srz.deserData(keyName);
		
		cipher.init(Cipher.DECRYPT_MODE, key);

		byte[] recoveredBytes = strOut.getBytes();

		cipher.doFinal(recoveredBytes);

		String recovered =

		new String(recoveredBytes);

		return recovered;

	}

	public void setUp() throws Exception {

		key = KeyGenerator.getInstance(algorithm).generateKey();

		srz.serData(keyName, key);

	}
	public void setCipher() throws Exception {
		cipher = Cipher.getInstance(algorithm);
	}

}

package safe;

import java.io.File;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;

public class crypt
{

	private static String full_algorithm = "AES/ECB/PKCS5PADDING";
	private static String algorithm = "AES";

	private static Key key;

	private static Cipher cipher;

	String keyName = System.getProperty("user.dir") + "/safe/key";

	String ciphName = System.getProperty("user.dir") + "/safe/cipher";

	public String encrypt(String strIn)

			throws Exception
	{
		cipher.init(Cipher.ENCRYPT_MODE, key);

		byte[] inputBytes = strIn.getBytes("UTF-8");

		byte[] encrypt = Base64.getEncoder().encode(cipher.doFinal(inputBytes));

		String recovered = new String(encrypt);

		return recovered;

	}

	public String decrypt(String strOut)

			throws Exception
	{
		cipher.init(Cipher.DECRYPT_MODE, key);

		byte[] encoded_val;

		encoded_val = Base64.getDecoder().decode(strOut.getBytes());

		byte[] recovered = cipher.doFinal(encoded_val);

		String recovered_str = new String(recovered);

		return recovered_str;

	}

	public void loadKey()
	{
		File f = new File(keyName + ".data");
		if (!f.exists())
		{
			try
			{
				key = KeyGenerator.getInstance(algorithm).generateKey();
			}
			catch (NoSuchAlgorithmException e)
			{
				e.printStackTrace();
			}
			Serialization.serData(keyName, key);
		}
		else
		{
			key = (Key) Serialization.deserData(keyName);
		}
	}

	public crypt()
	{
		try
		{
			cipher = Cipher.getInstance(full_algorithm);
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		loadKey();

	}
}
